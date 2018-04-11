package com.punj.app.ecommerce.controller.order;
/**
 * 
 */

import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.transformer.CommonMVCTransformer;
import com.punj.app.ecommerce.controller.common.transformer.OrderTransformer;
import com.punj.app.ecommerce.domains.common.Location;
import com.punj.app.ecommerce.domains.order.Order;
import com.punj.app.ecommerce.models.common.LocationBean;
import com.punj.app.ecommerce.models.order.OrderBean;
import com.punj.app.ecommerce.models.order.OrderReportBean;
import com.punj.app.ecommerce.services.OrderService;
import com.punj.app.ecommerce.services.common.CommonService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * @author admin
 *
 */
@Component
public class OrderPrintUtil {
	private static final Logger logger = LogManager.getLogger();
	private OrderService orderService;
	private MessageSource messageSource;
	private CommonService commonService;

	/**
	 * @param orderService
	 *            the orderService to set
	 */
	@Autowired
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	/**
	 * @param commonService
	 *            the commonService to set
	 */
	@Autowired
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	/**
	 * @param messageSource
	 *            the messageSource to set
	 */
	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public List<OrderReportBean> generateOrderReportBean(OrderBean orderBean, String username) {

		List<OrderReportBean> orderList = null;
		if (orderBean == null || orderBean.getOrderId() == null) {
			logger.info("The order report cannot be generated as order is not retrieved");
			return orderList;
		} else if (orderBean.getOrderId() != null && orderBean.getStatus() == null) {
			Order order = this.orderService.searchOrder(orderBean.getOrderId());
			if (order == null) {
				logger.info("The order report cannot be generated as order is not retrieved");
				return orderList;
			}
			orderBean = OrderTransformer.transformOrder(order);
		}

		Location location = this.commonService.retrieveLocationDetails(orderBean.getLocationId());
		LocationBean locationBean = CommonMVCTransformer.transformLocationDomainPartially(location, Boolean.FALSE);
		OrderReportBean orderReportBean = OrderTransformer.prepareOrderReportBean(orderBean, locationBean, username);

		if (orderReportBean != null) {
			orderList = new ArrayList<>();
			orderList.add(orderReportBean);
		}
		logger.info("The order report bean object has been generated successfully");

		return orderList;
	}

	public Object createOrderReport(OrderBean orderBean, String username, HttpSession session, String requestType) {

		BigInteger orderId = orderBean.getOrderId();
		JasperPrint orderReport = null;
		byte[] pdfBytes = null;
		// Step 1 - Check the report objects in session i.e. Report PDF or Report print object
		// Print if found
		Object sessionResult = this.retrieveOrderReportInSession(orderId, session, requestType);

		if (sessionResult == null) {
			// Step 2 - Create Report Object if step 1 fails
			// Check if data is available for order otherwise retrieve from database
			List<OrderReportBean> orderList = this.generateOrderReportBean(orderBean, username);
			logger.info("The order report object has been retrieved successfully.", orderId);

			// Step 3 - Read Report Template
			// Check if the templates has been cached already or not without data ofcourse
			// then fill the data and return
			orderReport = this.fillOrderData(orderList);
			logger.info("The order report to be printed has been retrieved for order {}.", orderId);

			// Step 4 - convert the report print data to PDF or the needed format
			pdfBytes = this.generateReportPDFData(orderReport);
			logger.info("The order PDF report has been retrieved for order {}.", orderId);

			// Step 5 - update the report in session if it is useful, we can check if cache is better option
			this.updateOrderReportInSession(orderId, pdfBytes, session, orderReport);
			logger.info("The order {} report details has been updated in session for later use ", orderId);

		} else {
			if (requestType.equals(MVCConstants.REPORT_ORDER_PRINT)) {
				orderReport = (JasperPrint) sessionResult;
			} else if (requestType.equals(MVCConstants.REPORT_ORDER_VIEW)) {
				pdfBytes = (byte[]) sessionResult;
			}
		}

		if (requestType.equals(MVCConstants.REPORT_ORDER_PRINT)) {
			return orderReport;
		} else if (requestType.equals(MVCConstants.REPORT_ORDER_VIEW)) {
			return pdfBytes;
		} else {
			return null;
		}

	}

	public Object retrieveOrderReportInSession(BigInteger orderId, HttpSession session, String requestType) {
		Object result = null;
		// This is to print the order report
		BigInteger sessionOrderId = (BigInteger)session.getAttribute(MVCConstants.LAST_ORDER_ID_REPORT);
		if (sessionOrderId!=null && orderId.equals(sessionOrderId)) {
			if (requestType.equals(MVCConstants.REPORT_ORDER_PRINT)) {
				result = session.getAttribute(MVCConstants.LAST_ORDER_REPORT_JASPER);
			} else if (requestType.equals(MVCConstants.REPORT_ORDER_VIEW)) {
				result = session.getAttribute(MVCConstants.LAST_ORDER_REPORT_PDF);
			}

		}
		return result;
	}

	public void updateOrderReportInSession(BigInteger orderId, byte[] pdfBytes, HttpSession session, JasperPrint orderReport) {
		// This is to print the order report
		session.setAttribute(MVCConstants.LAST_ORDER_ID_REPORT, orderId);
		// This is to print the order report
		session.setAttribute(MVCConstants.LAST_ORDER_REPORT_JASPER, orderReport);
		// This is to show the order report PDF in view screen
		session.setAttribute(MVCConstants.LAST_ORDER_REPORT_PDF, pdfBytes);
	}

	public byte[] generateReportPDFData(JasperPrint orderReport) {
		byte[] pdfBytes = null;
		try {
			if (orderReport != null) {
				pdfBytes = JasperExportManager.exportReportToPdf(orderReport);
				logger.info("The order report PDF array bytes has been generated");
			} else {
				logger.warn("There was some issue filling order data in report templates");
			}
		} catch (JRException e) {
			logger.error("There was an error while generating order report", e);
		}

		return pdfBytes;
	}

	public JasperPrint fillOrderData(List<OrderReportBean> orderCollection) {

		JasperPrint orderPrint = null;

		try {

			JRBeanCollectionDataSource orderDS = new JRBeanCollectionDataSource(orderCollection);

			JasperReport orderReport = this.getTemplateForOrder(MVCConstants.ORDER_REPORT);

			JasperReport orderHeaderReport = this.getTemplateForOrder(MVCConstants.ORDER_HEADER_REPORT);
			JasperReport deliveryLocationReport = this.getTemplateForOrder(MVCConstants.DELIEVERY_LOCATION_REPORT);
			JasperReport supplierReport = this.getTemplateForOrder(MVCConstants.SUPPLIER_REPORT);
			JasperReport supplierAddressReport = this.getTemplateForOrder(MVCConstants.ADDRESS_REPORT);
			JasperReport orderItemsReport = this.getTemplateForOrder(MVCConstants.ORDER_ITEMS_REPORT);
			logger.info("All the compiled reports for order printing has been loaded.");

			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put(MVCConstants.ORDER_HEADER_REPORT_PARAM, orderHeaderReport);
			paramMap.put(MVCConstants.DELIVERY_LOCATION_REPORT_PARAM, deliveryLocationReport);
			paramMap.put(MVCConstants.SUPPLIER_REPORT_PARAM, supplierReport);
			paramMap.put(MVCConstants.SUPPLIER_ADDRESS_REPORT_PARAM, supplierAddressReport);
			paramMap.put(MVCConstants.ORDER_ITEMS_REPORT_PARAM, orderItemsReport);
			logger.info("The parameter set for setting report data is ready");

			orderPrint = JasperFillManager.fillReport(orderReport, paramMap, orderDS);
			logger.info("The data for order report has been filled in templates successfully.");

		} catch (JRException e) {
			logger.error("There was an error while generating order report", e);
		}
		return orderPrint;

	}

	public JasperReport getTemplateForOrder(String reportName) throws JRException {

		InputStream orderReportStream = getClass().getResourceAsStream(reportName);
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(orderReportStream);
		logger.debug("The {} compiled report has been loaded now", reportName);
		return jasperReport;
	}

}
