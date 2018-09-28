package com.punj.app.ecommerce.controller.order.returns;
/**
 * 
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.models.order.OrderBean;
import com.punj.app.ecommerce.models.order.returns.OrderReturnBean;
import com.punj.app.ecommerce.services.OrderReturnService;
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
public class OrderReturnPrintController {
	private static final Logger logger = LogManager.getLogger();
	private OrderReturnService orderReturnService;
	private MessageSource messageSource;
	private CommonService commonService;
	private OrderReturnPrintUtil orderReturnPrintUtil;

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
	 * @param orderPrintUtil
	 *            the orderPrintUtil to set
	 */
	@Autowired
	public void setOrderReturnPrintUtil(OrderReturnPrintUtil orderReturnPrintUtil) {
		this.orderReturnPrintUtil = orderReturnPrintUtil;
	}

	/**
	 * @param messageSource
	 *            the messageSource to set
	 */
	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@GetMapping(value = ViewPathConstants.VIEW_ORDER_RETURN_URL)
	private void viewOrderReturnReport(HttpSession session, Locale locale, HttpServletResponse response, final HttpServletRequest req, Authentication authentication) {
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			OrderReturnBean orderReturnBean = null;

			String orderReturnIdParam = req.getParameter(MVCConstants.ORDER_RETURN_ID_PARAM);
			if (!StringUtils.isEmpty(orderReturnIdParam)) {
				orderReturnBean = new OrderReturnBean();
				orderReturnBean.setOrderReturnId(new BigInteger(orderReturnIdParam));

				byte[] pdfBytes = (byte[]) this.orderReturnPrintUtil.createOrderReturnReport(null, orderReturnBean, userDetails.getUsername(), session, MVCConstants.REPORT_VIEW);

				if (pdfBytes != null) {
					response.setContentType(MediaType.APPLICATION_PDF_VALUE);
					response.setHeader("Content-disposition", "attachment; filename=order_return_report.pdf");
					response.setContentLength(pdfBytes.length);
					response.getOutputStream().write(pdfBytes);
					logger.info("The PDF order return report has been generated successfully");
					response.getOutputStream().flush();
				} else {
					this.orderReturnPrintUtil.showEmptyReport(response);
					logger.info("The PDF order return report was not generated due to some issue");
				}
			} else {

				this.orderReturnPrintUtil.showEmptyReport(response);
				logger.info("The PDF order return report was not generated due to some issue");
			}
		} catch (IOException e) {
			logger.error("There is an error while generating report for last order return report", e);
		}
	}
	
	

	@GetMapping(value = ViewPathConstants.PRINT_ORDER_RETURN_URL, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public OrderReturnBean printOrderReturnReport(HttpSession session, HttpServletResponse response, final HttpServletRequest req, Authentication authentication) {
		OrderReturnBean orderReturnBean = null;
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();

			String orderReturnIdParam = req.getParameter(MVCConstants.ORDER_RETURN_ID_PARAM);
			if (!StringUtils.isEmpty(orderReturnIdParam)) {
				orderReturnBean = new OrderReturnBean();
				orderReturnBean.setOrderReturnId(new BigInteger(orderReturnIdParam));

				JasperPrint jasperPrint = (JasperPrint) this.orderReturnPrintUtil.createOrderReturnReport(null, orderReturnBean, userDetails.getUsername(), session, MVCConstants.REPORT_PRINT);

				if (jasperPrint != null) {
					JasperPrintManager.printReport(jasperPrint, Boolean.FALSE);
					logger.info("The {} order return report has been printed successfully", orderReturnBean.getOrderReturnId());
				} else {
					response.setContentType(MediaType.APPLICATION_PDF_VALUE);
					response.getWriter().write("There was an error generating Order return Report");
					response.getWriter().flush();

					logger.info("The order return report object is not found");
				}
			} else {

				response.setContentType(MediaType.APPLICATION_PDF_VALUE);
				response.getWriter().write("There was an error generating Order return Report");
				response.getWriter().flush();
				logger.info("There was no order number provided for retrieving order return report!!");
			}
		} catch (JRException | IOException e) {
			logger.error("There is an error while generating return report for last order", e);
		}

		return orderReturnBean;

	}

	@PostMapping(value = ViewPathConstants.PRINT_ORDER_RETURN_URL, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public OrderReturnBean printOrderReturnReport(@RequestBody OrderReturnBean orderReturnBean, HttpSession session, Authentication authentication) {
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();

			JasperPrint jasperPrint = (JasperPrint) this.orderReturnPrintUtil.createOrderReturnReport(null, orderReturnBean, userDetails.getUsername(), session, MVCConstants.REPORT_PRINT);

			if (jasperPrint != null) {
				JasperPrintManager.printReport(jasperPrint, Boolean.FALSE);
				logger.info("The {} order return report has been printed successfully", orderReturnBean.getOrderReturnId());
			} else {
				logger.info("The order return report object is not found");
			}
		} catch (JRException e) {
			logger.error("There is an error while generating return report for last order return ", e);
		}

		return orderReturnBean;

	}

}
