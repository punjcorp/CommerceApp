package com.punj.app.ecommerce.controller.order.returns;
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.transformer.CommonMVCTransformer;
import com.punj.app.ecommerce.controller.common.transformer.OrderReturnTransformer;
import com.punj.app.ecommerce.controller.common.transformer.OrderTransformer;
import com.punj.app.ecommerce.domains.common.Location;
import com.punj.app.ecommerce.domains.order.Order;
import com.punj.app.ecommerce.domains.order.returns.OrderReturn;
import com.punj.app.ecommerce.models.common.LocationBean;
import com.punj.app.ecommerce.models.order.OrderReportBean;
import com.punj.app.ecommerce.models.order.returns.OrderReturnBean;
import com.punj.app.ecommerce.models.order.returns.OrderReturnReportBean;
import com.punj.app.ecommerce.services.OrderReturnService;
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
public class OrderReturnPrintUtil {
	private static final Logger logger = LogManager.getLogger();
	private OrderService orderService;
	private OrderReturnService orderReturnService;
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
	 * @param orderReturnService
	 *            the orderReturnService to set
	 */
	@Autowired
	public void setOrderReturnService(OrderReturnService orderReturnService) {
		this.orderReturnService = orderReturnService;
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

	public Object createOrderReturnReport(OrderReturn orderReturn, OrderReturnBean orderReturnBean, String username, HttpSession session, String requestType) {

		BigInteger orderReturnId = orderReturnBean.getOrderReturnId();
		JasperPrint orderReturnReport = null;
		byte[] pdfBytes = null;
		// Step 1 - Check the report objects in session i.e. Report PDF or Report print object
		// Print if found
		Object sessionResult = this.retrieveOrderReturnReportInSession(orderReturnId, session, requestType);

		if (sessionResult == null && orderReturn!=null) {
			// Step 2 - Create Report Object if step 1 fails
			// Check if data is available for order otherwise retrieve from database
			List<OrderReturnReportBean> orderReturnReportBeans = this.generateOrderReturnReportBean(orderReturnBean, username);
			logger.info("The order return report object has been retrieved successfully.", orderReturnId);

			// Step 3 - Read Report Template
			// Check if the templates has been cached already or not without data ofcourse
			// then fill the data and return
			orderReturnReport = this.fillOrderData(orderReturnReportBeans);
			logger.info("The order return report to be printed has been retrieved for order return {}.", orderReturnId);

			// Step 4 - convert the report print data to PDF or the needed format
			pdfBytes = this.generateReportPDFData(orderReturnReport);
			logger.info("The order return PDF report has been retrieved for order {}.", orderReturnId);

			// Step 5 - update the report in session if it is useful, we can check if cache is better option
			this.updateReturnReportInSession(orderReturnId, pdfBytes, session, orderReturnReport);
			logger.info("The order return {} report details has been updated in session for later use ", orderReturnId);
			
			if(orderReturn!=null && pdfBytes!=null) {
				orderReturn.setOrderReturnReport(pdfBytes);
				// Step 6 - save this report in order return
				orderReturn=this.saveOrderReturnReport(orderReturn, username);
				logger.info("The order {} report details has been saved in order table", orderReturnId);

			}

		} else if (sessionResult == null && orderReturn==null) {
			orderReturn=this.orderReturnService.searchOrderReturn(orderReturnBean.getOrderReturnId());
			if(orderReturn!=null) {
				pdfBytes=orderReturn.getOrderReturnReport();
			}
			 
		}else {
			if (requestType.equals(MVCConstants.REPORT_PRINT)) {
				orderReturnReport = (JasperPrint) sessionResult;
			} else if (requestType.equals(MVCConstants.REPORT_VIEW)) {
				pdfBytes = (byte[]) sessionResult;
			}
		}

		if (requestType.equals(MVCConstants.REPORT_PRINT)) {
			return orderReturnReport;
		} else if (requestType.equals(MVCConstants.REPORT_VIEW)) {
			return pdfBytes;
		} else {
			return null;
		}

	}

	public List<OrderReturnReportBean> generateOrderReturnReportBean(OrderReturnBean orderReturnBean, String username) {

		List<OrderReturnReportBean> returnList=new ArrayList<>();
		
		OrderReturnReportBean orderReturnReportBean = null;
		if (orderReturnBean == null || orderReturnBean.getOrderReturnId() == null) {
			logger.info("The order report cannot be generated as order return is not retrieved");
			return returnList;
		} else if (orderReturnBean.getOrderReturnId() != null && orderReturnBean.getStatus() == null) {
			OrderReturn orderReturn = this.orderReturnService.searchOrderReturn(orderReturnBean.getOrderReturnId());
			if (orderReturn == null) {
				logger.info("The order return report cannot be generated as order return is not retrieved");
				return returnList;
			}
			orderReturnBean = OrderReturnTransformer.transformOrderReturn(orderReturn);
		}

		Location location = this.commonService.retrieveLocationDetails(orderReturnBean.getOrder().getLocationId());
		LocationBean locationBean = CommonMVCTransformer.transformLocationDomainPartially(location, Boolean.FALSE);
		orderReturnReportBean = OrderReturnTransformer.prepareORRB(orderReturnBean, locationBean, username);
		if(orderReturnReportBean!=null)
			returnList.add(orderReturnReportBean);
		logger.info("The order return report bean object has been generated successfully");

		return returnList;
	}

	public Object retrieveOrderReturnReportInSession(BigInteger orderReturnId, HttpSession session, String requestType) {
		Object result = null;
		// This is to print the order report
		BigInteger sessionOrderReturnId = (BigInteger) session.getAttribute(MVCConstants.LAST_ORDER_RETURN_ID_REPORT);
		if (sessionOrderReturnId != null && orderReturnId.equals(sessionOrderReturnId)) {
			if (requestType.equals(MVCConstants.REPORT_PRINT)) {
				result = session.getAttribute(MVCConstants.LAST_ORDER_RETURN_REPORT_JASPER);
			} else if (requestType.equals(MVCConstants.REPORT_VIEW)) {
				result = session.getAttribute(MVCConstants.LAST_ORDER_RETURN_REPORT_PDF);
			}

		}
		return result;
	}

	public void updateReturnReportInSession(BigInteger orderId, byte[] pdfBytes, HttpSession session, JasperPrint orderReport) {
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

	public JasperPrint fillOrderData(List<OrderReturnReportBean> orderReturnReportBeans) {

		JasperPrint orderReturnPrint = null;

		try {

			JRBeanCollectionDataSource orderReturnDS = new JRBeanCollectionDataSource(orderReturnReportBeans);

			JasperReport orderReturnReport = this.getTemplateForReport(MVCConstants.ORDER_RETURN_REPORT);

			JasperReport orderReturnHeaderReport = this.getTemplateForReport(MVCConstants.ORDER_RETURN_HEADER_REPORT);
			JasperReport orderReturnFooterReport = this.getTemplateForReport(MVCConstants.ORDER_RETURN_FOOTER_REPORT);
			JasperReport deliveryLocationReport = this.getTemplateForReport(MVCConstants.ADDRESS_REPORT);
			JasperReport supplierReport = this.getTemplateForReport(MVCConstants.SUPPLIER_REPORT);
			JasperReport supplierAddressReport = this.getTemplateForReport(MVCConstants.ADDRESS_REPORT);
			JasperReport orderReturnItemsReport = this.getTemplateForReport(MVCConstants.ORDER_RETURN_ITEMS_REPORT);
			logger.info("All the compiled reports for order return printing has been loaded.");

			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put(MVCConstants.ORDER_RETURN_HEADER_REPORT_PARAM, orderReturnHeaderReport);
			paramMap.put(MVCConstants.DELIVERY_LOCATION_REPORT_PARAM, deliveryLocationReport);
			paramMap.put(MVCConstants.SUPPLIER_REPORT_PARAM, supplierReport);
			paramMap.put(MVCConstants.SUPPLIER_ADDRESS_REPORT_PARAM, supplierAddressReport);
			paramMap.put(MVCConstants.ORDER_RETURN_ITEMS_REPORT_PARAM, orderReturnItemsReport);
			paramMap.put(MVCConstants.ORDER_RETURN_FOOTER_REPORT_PARAM, orderReturnFooterReport);
			logger.info("The parameter set for setting report data is ready");

			orderReturnPrint = JasperFillManager.fillReport(orderReturnReport, paramMap, orderReturnDS);
			logger.info("The data for order return report has been filled in templates successfully.");

		} catch (JRException e) {
			logger.error("There was an error while generating order return report", e);
		}
		return orderReturnPrint;

	}

	public JasperReport getTemplateForReport(String reportName) throws JRException {

		InputStream orderReportStream = getClass().getResourceAsStream(reportName);
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(orderReportStream);
		logger.debug("The {} compiled report has been loaded now", reportName);
		return jasperReport;
	}

	public OrderReturn saveOrderReturnReport(OrderReturn orderReturn, String username) {
		orderReturn = this.orderReturnService.createOrderReturn(orderReturn, username);
		logger.info("The order return report data has been saved successfully");
		return orderReturn;
	}
}
