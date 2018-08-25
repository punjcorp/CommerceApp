package com.punj.app.ecommerce.controller.order.returns;
/**
 * 
 */

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.OrderReturnTransformer;
import com.punj.app.ecommerce.domains.order.Order;
import com.punj.app.ecommerce.domains.order.OrderItem;
import com.punj.app.ecommerce.domains.order.returns.OrderReturn;
import com.punj.app.ecommerce.domains.order.returns.OrderReturnDTO;
import com.punj.app.ecommerce.domains.order.returns.OrderReturnItem;
import com.punj.app.ecommerce.models.common.SearchBean;
import com.punj.app.ecommerce.models.order.returns.ReturnBeanDTO;
import com.punj.app.ecommerce.services.OrderReturnService;
import com.punj.app.ecommerce.services.OrderService;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class ManageOrderReturnController {
	private static final Logger logger = LogManager.getLogger();
	private OrderReturnService orderReturnService;
	private OrderService orderService;
	private MessageSource messageSource;

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
	 * @param messageSource
	 *            the messageSource to set
	 */
	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@GetMapping(value = ViewPathConstants.MANAGE_ORDER_RETURN_URL)
	public String showManageOrderReturn(Model model) {
		logger.info("The manage order return has been called.");
		model.addAttribute(MVCConstants.SEARCH_BEAN, new SearchBean());
		return ViewPathConstants.MANAGE_ORDER_RETURN_PAGE;
	}

	@PostMapping(ViewPathConstants.MANAGE_ORDER_RETURN_URL)
	public String searchOrder(@ModelAttribute @Valid SearchBean searchBean, BindingResult bindingResult, @RequestParam(MVCConstants.PAGE_PARAM) Optional<Integer> page, Model model,
			HttpSession session, Locale locale) {
		if (bindingResult.hasErrors() && !model.containsAttribute(MVCConstants.SUCCESS))
			return ViewPathConstants.MANAGE_ORDER_RETURN_PAGE;
		try {
			if (!model.containsAttribute(MVCConstants.SUCCESS)) {
				Pager pager = new Pager();
				if (!page.isPresent()) {
					pager.setCurrentPageNo(1);
				} else {
					pager.setCurrentPageNo(page.get());
				}

				OrderReturnDTO orderReturnsDTO = this.orderReturnService.searchOrderReturns(searchBean.getSearchText(), pager);
				if (orderReturnsDTO != null) {
					ReturnBeanDTO returnBeanDTO = OrderReturnTransformer.transformOrderReturnDTO(orderReturnsDTO);
					this.updateModelBeans(model, returnBeanDTO, searchBean);
					pager = returnBeanDTO.getPager();
				}

				model.addAttribute(MVCConstants.SUCCESS, messageSource.getMessage("commerce.screen.common.search.success",
						new Object[] { pager.getResultSize(), pager.getCurrentPageNo(), pager.getNoOfPages() }, locale));
				logger.info("The purchase order return details has been retrieved successfully.");
			} else {
				model.addAttribute("org.springframework.validation.BindingResult.searchBean", null);
			}
		} catch (Exception e) {
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.order.return.search.result.failure", null, locale));
			logger.error("There is an error while retrieving purchase order returns", e);
		}
		return ViewPathConstants.MANAGE_ORDER_RETURN_PAGE;
	}

	private void updateModelBeans(Model model, ReturnBeanDTO returnBeanDTO, SearchBean searchBean) {
		model.addAttribute(MVCConstants.ORDER_RETURNS_BEAN, returnBeanDTO);

		Pager tmpPager = returnBeanDTO.getPager();
		Pager pager = new Pager(tmpPager.getResultSize(), tmpPager.getPageSize(), tmpPager.getCurrentPageNo(), tmpPager.getMaxDisplayPage(),
				ViewPathConstants.MANAGE_ORDER_RETURN_URL);
		returnBeanDTO.setPager(pager);

		model.addAttribute(MVCConstants.PAGER, pager);
		model.addAttribute(MVCConstants.SEARCH_BEAN, searchBean);
		logger.info("All the needed details has been updated in the model for display.");
	}

	@PostMapping(value = ViewPathConstants.BULK_ORDER_RETURN_URL, params = { MVCConstants.APPROVE_ORDER_RETURNS_PARAM })
	public String approveOrders(@ModelAttribute ReturnBeanDTO orderReturnDTO, RedirectAttributes redirectAttrs, final HttpServletRequest req, Locale locale,
			Authentication authentication) {
		try {
			req.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			List<BigInteger> orderReturnIds = OrderReturnTransformer.retrieveEligibleOrderReturns(orderReturnDTO);

			if (orderReturnIds != null && !orderReturnIds.isEmpty()) {
				orderReturnIds=this.validateOrderReturnForApproval(orderReturnIds);
				if(orderReturnIds != null && !orderReturnIds.isEmpty()) {
					List<OrderReturn> orderReturns = this.orderReturnService.approveOrderReturns(orderReturnIds, userDetails.getUsername());
					redirectAttrs.addFlashAttribute(MVCConstants.SUCCESS, messageSource.getMessage("commerce.screen.order.return.approve.success", null, locale));
					logger.info("The bulk update operation for order return is completed.");
				}else {
					redirectAttrs.addFlashAttribute(MVCConstants.ALERT, messageSource.getMessage("commerce.screen.order.return.approve.valid.no.records", null, locale));
					logger.info("None of the provided order returns are eligible for approval as returned qty is more than available qty");
				}
				
			} else {
				redirectAttrs.addFlashAttribute(MVCConstants.ALERT, messageSource.getMessage("commerce.screen.order.return.approve.no.records", null, locale));
				logger.info("The bulk update operation for order return is completed.");
			}
		} catch (Exception e) {
			redirectAttrs.addFlashAttribute(MVCConstants.ALERT, messageSource.getMessage("commerce.screen.order.return.approve.failure", null, locale));
			logger.error("There is an error while approving bulk order returns basic details", e);
		}
		return ViewPathConstants.REDIRECT_URL + ViewPathConstants.MANAGE_ORDER_RETURN_URL;
	}

	@PostMapping(value = ViewPathConstants.BULK_ORDER_RETURN_URL, params = { MVCConstants.DELETE_ORDER_RETURNS_PARAM })
	public String deleteOrders(@ModelAttribute ReturnBeanDTO orderReturnDTO, RedirectAttributes redirectAttrs, final HttpServletRequest req, Locale locale,
			Authentication authentication) {
		try {
			req.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			List<BigInteger> orderReturnIds = OrderReturnTransformer.retrieveEligibleOrderReturns(orderReturnDTO);
			if (orderReturnIds != null && !orderReturnIds.isEmpty()) {
				this.orderReturnService.deleteOrderReturns(orderReturnIds, userDetails.getUsername());
				redirectAttrs.addFlashAttribute(MVCConstants.SUCCESS, messageSource.getMessage("commerce.screen.order.return.delete.success", null, locale));
				logger.info("The bulk delete operation for order returns is completed.");
			} else {
				redirectAttrs.addFlashAttribute(MVCConstants.ALERT, messageSource.getMessage("commerce.screen.order.return.delete.no.records", null, locale));
				logger.info("The bulk delete operation for order returns is completed.");
			}

		} catch (Exception e) {
			redirectAttrs.addFlashAttribute(MVCConstants.ALERT, messageSource.getMessage("commerce.screen.order.return.delete.failure", null, locale));
			logger.error("There is an error while deleting bulk order returns", e);
		}

		return ViewPathConstants.REDIRECT_URL + ViewPathConstants.MANAGE_ORDER_RETURN_URL;
	}

	private List<BigInteger> validateOrderReturnForApproval(List<BigInteger> orderReturnIds) {
		List<BigInteger> finalOrderReturnIds = new ArrayList<>();
		Boolean isValid = null;
		for (BigInteger orderReturnId : orderReturnIds) {
			isValid = this.validateReturnForApproval(orderReturnId);
			if(isValid)
				finalOrderReturnIds.add(orderReturnId);
		}
		logger.info("All the valid order returns has been filtered to be approved");
		return finalOrderReturnIds;
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
