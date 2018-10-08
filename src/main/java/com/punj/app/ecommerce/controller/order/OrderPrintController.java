package com.punj.app.ecommerce.controller.order;
/**
 * 
 */

import java.io.IOException;
import java.math.BigInteger;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.models.financials.AccountDTO;
import com.punj.app.ecommerce.models.order.OrderBean;
import com.punj.app.ecommerce.models.order.OrderReportBean;
import com.punj.app.ecommerce.services.OrderService;
import com.punj.app.ecommerce.services.common.CommonService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class OrderPrintController {
	private static final Logger logger = LogManager.getLogger();
	private OrderService orderService;
	private MessageSource messageSource;
	private CommonService commonService;
	private OrderPrintUtil orderPrintUtil;

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
	 * @param orderPrintUtil
	 *            the orderPrintUtil to set
	 */
	@Autowired
	public void setOrderPrintUtil(OrderPrintUtil orderPrintUtil) {
		this.orderPrintUtil = orderPrintUtil;
	}

	/**
	 * @param messageSource
	 *            the messageSource to set
	 */
	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@GetMapping(value = ViewPathConstants.VIEW_ORDER_URL)
	private void viewOrderReport(HttpSession session, Locale locale, HttpServletResponse response, final HttpServletRequest req,
			Authentication authentication) {
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			OrderBean orderBean = null;

			String orderIdParam = req.getParameter(MVCConstants.ORDER_ID_PARAM);
			if (!StringUtils.isEmpty(orderIdParam)) {
				orderBean = new OrderBean();
				orderBean.setOrderId(new BigInteger(orderIdParam));

				byte[] pdfBytes = (byte[]) this.orderPrintUtil.createOrderReport(null,orderBean, userDetails.getUsername(), session, MVCConstants.REPORT_ORDER_VIEW, Boolean.FALSE);

				if (pdfBytes != null) {
					response.setContentType(MediaType.APPLICATION_PDF_VALUE);
					response.setHeader("Content-disposition", "attachment; filename=order_report.pdf");
					response.setContentLength(pdfBytes.length);
					response.getOutputStream().write(pdfBytes);
					logger.info("The PDF order report has been generated successfully");
					response.getOutputStream().flush();
				} else {
					response.setContentType(MediaType.APPLICATION_PDF_VALUE);
					response.getWriter().write("There was an error generating Order Report");
					response.getWriter().flush();
					logger.info("The PDF order report was not generated due to some issue");
				}
			} else {

				response.setContentType(MediaType.APPLICATION_PDF_VALUE);
				response.getWriter().write("There was an error generating Order Report");
				response.getWriter().flush();
				logger.info("There was no order number provided for retrieving order report!!");
			}
		} catch (IOException e) {
			logger.error("There is an error while generating report for last order report", e);
		}
	}

	@GetMapping(value = ViewPathConstants.PRINT_ORDER_URL, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public OrderBean printOrderReport(HttpSession session, HttpServletResponse response, final HttpServletRequest req, Authentication authentication) {
		OrderBean orderBean = null;
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();

			String orderIdParam = req.getParameter(MVCConstants.ORDER_ID_PARAM);
			if (!StringUtils.isEmpty(orderIdParam)) {
				orderBean = new OrderBean();
				orderBean.setOrderId(new BigInteger(orderIdParam));

				JasperPrint jasperPrint = (JasperPrint) this.orderPrintUtil.createOrderReport(null, orderBean, userDetails.getUsername(), session,
						MVCConstants.REPORT_ORDER_PRINT, Boolean.FALSE);

				if (jasperPrint != null) {
					JasperPrintManager.printReport(jasperPrint, Boolean.FALSE);
					logger.info("The {} order report has been printed successfully", orderBean.getOrderId());
				} else {
					response.setContentType(MediaType.APPLICATION_PDF_VALUE);
					response.getWriter().write("There was an error generating Order Report");
					response.getWriter().flush();

					logger.info("The order report object is not found");
				}
			} else {

				response.setContentType(MediaType.APPLICATION_PDF_VALUE);
				response.getWriter().write("There was an error generating Order Report");
				response.getWriter().flush();
				logger.info("There was no order number provided for retrieving order report!!");
			}
		} catch (JRException | IOException e) {
			logger.error("There is an error while generating report for last order", e);
		}

		return orderBean;

	}

	@PostMapping(value = ViewPathConstants.PRINT_ORDER_URL, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public OrderBean printOrderReport(@RequestBody OrderBean orderBean, HttpSession session, Authentication authentication) {
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();

			JasperPrint jasperPrint = (JasperPrint) this.orderPrintUtil.createOrderReport(null, orderBean, userDetails.getUsername(), session,
					MVCConstants.REPORT_ORDER_PRINT, Boolean.FALSE);

			if (jasperPrint != null) {
				JasperPrintManager.printReport(jasperPrint, Boolean.FALSE);
				logger.info("The {} order report has been printed successfully", orderBean.getOrderId());
			} else {
				logger.info("The order report object is not found");
			}
		} catch (JRException e) {
			logger.error("There is an error while generating report for last order", e);
		}

		return orderBean;

	}

}
