package com.punj.app.ecommerce.controller.order;
/**
 * 
 */

import java.math.BigInteger;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.CommonMVCTransformer;
import com.punj.app.ecommerce.controller.common.transformer.OrderTransformer;
import com.punj.app.ecommerce.domains.order.Order;
import com.punj.app.ecommerce.models.common.LocationBean;
import com.punj.app.ecommerce.models.order.OrderBean;
import com.punj.app.ecommerce.models.order.OrderBeanDTO;
import com.punj.app.ecommerce.services.OrderService;
import com.punj.app.ecommerce.services.common.CommonService;
import com.punj.app.ecommerce.services.common.dtos.LocationDTO;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class OrderPhaseController {
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

	@GetMapping(ViewPathConstants.APPROVE_ORDER_URL)
	public String approveOrder(Model model, RedirectAttributes redirectAttrs, final HttpServletRequest req, Locale locale) {
		try {
			BigInteger orderId = new BigInteger(req.getParameter(MVCConstants.ORDER_ID_PARAM));

			if (orderId.compareTo(BigInteger.ZERO) > 0) {
				this.orderService.approveOrder(orderId);
				redirectAttrs.addFlashAttribute(MVCConstants.SUCCESS,
						messageSource.getMessage("commerce.screen.order.manage.approve.success", new Object[] { orderId }, locale));
				req.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);

				logger.info("The selected purchase order has been approved successfully");

			} else {
				redirectAttrs.addFlashAttribute(MVCConstants.ALERT,
						this.messageSource.getMessage("commerce.screen.order.manage.approve.noorder", null, locale));
				logger.info("There is no order number specified for approval");
			}
		} catch (Exception e) {
			redirectAttrs.addFlashAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			logger.error("There is an error while approving purchase order", e);
		}
		return ViewPathConstants.REDIRECT_URL + ViewPathConstants.MANAGE_ORDER_URL;
	}

	@GetMapping(ViewPathConstants.DELETE_ORDER_URL)
	public String deleteOrder(Model model, final HttpServletRequest req, RedirectAttributes redirectAttrs, Locale locale) {
		try {
			BigInteger orderId = new BigInteger(req.getParameter(MVCConstants.ORDER_ID_PARAM));
			if (orderId.compareTo(BigInteger.ZERO) > 0) {
				Order order = this.orderService.searchOrder(orderId);
				if (order != null && order.getStatus().equals(MVCConstants.STATUS_CREATED)) {
					this.orderService.deleteOrder(orderId);
					redirectAttrs.addFlashAttribute(MVCConstants.SUCCESS,
							messageSource.getMessage("commerce.screen.order.manage.delete.success", new Object[] { orderId }, locale));
					req.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
					logger.info("The selected purchase order has been deleted successfully");
				} else {
					redirectAttrs.addFlashAttribute(MVCConstants.ALERT,
							this.messageSource.getMessage("commerce.screen.order.manage.delete.status", null, locale));
					logger.info("The purchase order with status Approved, Received, Paid and Closed cannot be deleted!");
				}
			} else {
				redirectAttrs.addFlashAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.order.manage.delete.noorder", null, locale));
				logger.info("There is no order number specified for deletion");
			}
		} catch (Exception e) {
			redirectAttrs.addFlashAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			logger.error("There is an error while deleting purchase order", e);
		}
		return ViewPathConstants.MANAGE_ORDER_PAGE;
	}

	private void updateOrderModelDetails(Model model, OrderBeanDTO orderBeanDTO) {

		LocationDTO locationDTO = this.commonService.retrieveLocationWithDailyStatus();
		List<LocationBean> locations = CommonMVCTransformer.transformLocationDTO(locationDTO);

		orderBeanDTO.setLocations(locations);

		model.addAttribute(MVCConstants.ORDER_BEAN_DTO, orderBeanDTO);

		logger.info("The order screen model objects has been updated successfully");
	}

	@GetMapping(ViewPathConstants.RECEIVE_ORDER_URL)
	public String retrieveOrderForReceival(Model model, final HttpServletRequest req, Locale locale) {
		try {
			BigInteger orderId = new BigInteger(req.getParameter(MVCConstants.ORDER_ID_PARAM));

			if (orderId.compareTo(BigInteger.ZERO) > 0) {
				Order order = this.orderService.searchOrder(orderId);
				if (order != null && order.getStatus().equals(MVCConstants.STATUS_APPROVED)) {
					OrderBeanDTO orderBeanDTO = new OrderBeanDTO();
					OrderBean orderBean = OrderTransformer.transformOrder(order);
					orderBeanDTO.setOrder(orderBean);
					this.updateOrderModelDetails(model, orderBeanDTO);
					logger.info("The selected purchase order has been retrieved for receival successfully");
				} else {
					model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.order.manage.receive.status", null, locale));
					logger.info("There order is not eligible for the receipt!!");
				}
			} else {
				model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.order.manage.receive.noorder", null, locale));
				logger.info("There is no order number specified for deletion");
			}

		} catch (Exception e) {
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			logger.error("There is an error while retrieving purchase order for receival", e);
		}
		return ViewPathConstants.RECEIVE_ORDER_PAGE;
	}

	@PostMapping(value = ViewPathConstants.RECEIVE_ORDER_URL, params = { MVCConstants.SAVE_ORDER_PARAM })
	public String saveOrderForReceival(@ModelAttribute @Valid OrderBeanDTO orderBeanDTO, final BindingResult bindingResult, Model model, HttpSession session,
			Locale locale, Authentication authentication) {
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			Order order = OrderTransformer.transformOrderBean(orderBeanDTO.getOrder(), userDetails.getUsername(), orderBeanDTO.getOrder().getStatus(),
					Boolean.TRUE);

			order = this.orderService.createOrder(order);

			model.addAttribute(MVCConstants.SUCCESS,
					messageSource.getMessage("commerce.screen.order.receive.save.success", new Object[] { order.getOrderId() }, locale));
			logger.info("The purchase order details has been saved after receiving successfully");
		} catch (Exception e) {
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			logger.error("There is an error while saving order details during receive", e);

		}
		return ViewPathConstants.RECEIVE_ORDER_PAGE;
	}

	@PostMapping(value = ViewPathConstants.RECEIVE_ORDER_URL, params = { MVCConstants.RECEIVE_ORDER_PARAM })
	public String receiveOrder(@ModelAttribute @Valid OrderBeanDTO orderBeanDTO, final BindingResult bindingResult, Model model, HttpSession session,
			Locale locale, Authentication authentication) {
		try {

			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			Order order = OrderTransformer.transformOrderBean(orderBeanDTO.getOrder(), userDetails.getUsername(), MVCConstants.STATUS_RECEIVED,
					Boolean.TRUE);

			order = this.orderService.createOrder(order);

			model.addAttribute(MVCConstants.SUCCESS,
					messageSource.getMessage("commerce.screen.order.receive.receive.success", new Object[] { order.getOrderId() }, locale));
			logger.info("The purchase order details has been marked as Received successfully");
		} catch (Exception e) {
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			logger.error("There is an error while saving order details during receipt", e);
		}
		return ViewPathConstants.RECEIVE_ORDER_PAGE;
	}
	
	
	@PostMapping(value = ViewPathConstants.RECEIVE_ORDER_URL, params = { MVCConstants.RECEIVE_ALL_ORDERS_PARAM })
	public String receiveAllOrder(@ModelAttribute OrderBeanDTO orderBeanDTO, Model model, HttpSession session,
			Locale locale, Authentication authentication) {
		try {

			UserDetails userDetails = (UserDetails) authentication.getPrincipal();

			Order order=OrderTransformer.transformOrderBeanAsReceivedAll(orderBeanDTO.getOrder(), userDetails.getUsername());
			order=this.orderService.createOrder(order);

			logger.info("All the purchase order items has been marked as received successfully");
			model.addAttribute(MVCConstants.SUCCESS,
					messageSource.getMessage("commerce.screen.order.receive.receive.all.success", new Object[] { order.getOrderId() }, locale));

		} catch (Exception e) {
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			logger.error("There is an error while receiving bulk orders", e);
		}
		return ViewPathConstants.RECEIVE_ORDER_PAGE;
	}	
	

}
