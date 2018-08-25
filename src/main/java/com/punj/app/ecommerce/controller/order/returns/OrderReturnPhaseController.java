package com.punj.app.ecommerce.controller.order.returns;
/**
 * 
 */

import java.math.BigInteger;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.domains.order.Order;
import com.punj.app.ecommerce.domains.order.OrderItem;
import com.punj.app.ecommerce.domains.order.returns.OrderReturn;
import com.punj.app.ecommerce.domains.order.returns.OrderReturnItem;
import com.punj.app.ecommerce.models.common.SearchBean;
import com.punj.app.ecommerce.services.OrderReturnService;
import com.punj.app.ecommerce.services.OrderService;
import com.punj.app.ecommerce.services.common.CommonService;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class OrderReturnPhaseController {
	private static final Logger logger = LogManager.getLogger();
	private OrderReturnService orderReturnService;
	private OrderService orderService;
	private MessageSource messageSource;
	private CommonService commonService;

	/**
	 * @param orderReturnService
	 *            the orderReturnService to set
	 */
	@Autowired
	public void setOrderReturnService(OrderReturnService orderReturnService) {
		this.orderReturnService = orderReturnService;
	}

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

	@GetMapping(ViewPathConstants.APPROVE_ORDER_RETURN_URL)
	public String approveOrderReturn(Model model, RedirectAttributes redirectAttrs, final HttpServletRequest req, Locale locale, Authentication authentication) {
		try {
			BigInteger orderReturnId = new BigInteger(req.getParameter(MVCConstants.ORDER_RETURN_ID_PARAM));
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();

			if (orderReturnId.compareTo(BigInteger.ZERO) > 0) {
				OrderReturn orderReturn = this.orderReturnService.searchOrderReturn(orderReturnId);
				if (orderReturn != null) {
					if (orderReturn.getStatus().equals(MVCConstants.STATUS_CREATED)) {
						if(this.validateReturnForApproval(orderReturnId)) {
							this.orderReturnService.approveOrderReturn(orderReturnId, userDetails.getUsername());
							redirectAttrs.addFlashAttribute(MVCConstants.SUCCESS,
									messageSource.getMessage("commerce.screen.order.return.manage.approve.success", new Object[] { orderReturnId }, locale));

							logger.info("The selected order return has been approved successfully");
						}else {
							redirectAttrs.addFlashAttribute(MVCConstants.ALERT,
									messageSource.getMessage("commerce.screen.order.return.manage.valid.approve.success", new Object[] { orderReturnId }, locale));

							logger.info("The selected order return has been approved successfully");
						}
						
						
					} else {
						redirectAttrs.addFlashAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.order.return.approve.status", null, locale));
						logger.info("There order return is not eligible for the approval as status is not 'created'!!");
					}
				} else {
					redirectAttrs.addFlashAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.order.return.not.found", null, locale));
					logger.info("There order return was not found in the database!!");
				}

			} else {
				redirectAttrs.addFlashAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.order.return.manage.approve.noorder", null, locale));
				logger.info("There is no order return number specified for approval");
			}
		} catch (Exception e) {
			redirectAttrs.addFlashAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			logger.error("There is an error while approving purchase order return", e);
		}
		req.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
		return ViewPathConstants.REDIRECT_URL + ViewPathConstants.MANAGE_ORDER_RETURN_URL;
	}

	@GetMapping(ViewPathConstants.DELETE_ORDER_RETURN_URL)
	public String deleteOrderReturn(Model model, final HttpServletRequest req, RedirectAttributes redirectAttrs, Locale locale) {
		try {
			BigInteger orderReturnId = new BigInteger(req.getParameter(MVCConstants.ORDER_RETURN_ID_PARAM));
			if (orderReturnId.compareTo(BigInteger.ZERO) > 0) {
				OrderReturn orderReturn = this.orderReturnService.searchOrderReturn(orderReturnId);
				if (orderReturn != null && orderReturn.getStatus().equals(MVCConstants.STATUS_CREATED)) {
					this.orderReturnService.deleteOrderReturn(orderReturnId);
					redirectAttrs.addFlashAttribute(MVCConstants.SUCCESS,
							messageSource.getMessage("commerce.screen.order.return.manage.delete.success", new Object[] { orderReturnId }, locale));
					req.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
					logger.info("The selected purchase order return has been deleted successfully");
				} else {
					redirectAttrs.addFlashAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.order.return.manage.delete.status", null, locale));
					logger.info("The purchase order return with status Approved and Closed cannot be deleted!");
				}
			} else {
				redirectAttrs.addFlashAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.order.return.manage.delete.noorder", null, locale));
				logger.info("There is no order return number specified for deletion");
			}
		} catch (Exception e) {
			redirectAttrs.addFlashAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			logger.error("There is an error while deleting purchase order return", e);
		}
		return ViewPathConstants.REDIRECT_URL + ViewPathConstants.MANAGE_ORDER_RETURN_URL;
	}

	
	private Boolean validateReturnForApproval(BigInteger orderReturnId) {
		Boolean validationFlag=Boolean.TRUE;
		
		OrderReturn orderReturn = this.orderReturnService.searchOrderReturn(orderReturnId);
		if (orderReturn != null) {
			Order order = this.orderService.searchOrder(orderReturn.getOrder().getOrderId());

			List<OrderItem> orderItems = order.getOrderItems();
			List<OrderReturnItem> orderReturnItems = orderReturn.getOrderReturnItems();
			BigInteger orderItemId=null;
			BigInteger orderReturnItemId=null;
			
			for (OrderReturnItem orderReturnItem : orderReturnItems) {
				orderReturnItemId=orderReturnItem.getItemId();
				for (OrderItem orderItem : orderItems) {
					orderItemId=orderItem.getItemId();
					if (orderReturnItemId.equals(orderItemId)) {
						if(orderReturnItem.getReturnQty().compareTo(orderItem.getDelieveredQty().subtract(orderItem.getReturnedQty()))>0)
							validationFlag=Boolean.FALSE;
						if(!validationFlag)
							break;
					}
				}
				if(!validationFlag)
					break;
			}

		}
		
		if(validationFlag) {
			logger.info("The order return is valid for approval");
			return validationFlag;
		}else {
			logger.info("The order return is not valid for approval");
			return validationFlag;
		}
		
	}
	
}
