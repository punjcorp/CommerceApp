package com.punj.app.ecommerce.controller.order.returns;
/**
 * 
 */

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.CommonMVCTransformer;
import com.punj.app.ecommerce.controller.common.transformer.OrderReturnTransformer;
import com.punj.app.ecommerce.controller.common.transformer.OrderTransformer;
import com.punj.app.ecommerce.domains.common.ReasonCode;
import com.punj.app.ecommerce.domains.inventory.StockReason;
import com.punj.app.ecommerce.domains.order.Order;
import com.punj.app.ecommerce.domains.order.returns.OrderReturn;
import com.punj.app.ecommerce.models.common.LocationBean;
import com.punj.app.ecommerce.models.common.validator.ValidationGroup;
import com.punj.app.ecommerce.models.inventory.InvAdjustBean;
import com.punj.app.ecommerce.models.inventory.InvReasonBean;
import com.punj.app.ecommerce.models.order.OrderBean;
import com.punj.app.ecommerce.models.order.returns.OrderReturnBean;
import com.punj.app.ecommerce.models.order.returns.OrderReturnDTO;
import com.punj.app.ecommerce.services.OrderReturnService;
import com.punj.app.ecommerce.services.OrderService;
import com.punj.app.ecommerce.services.common.CommonService;
import com.punj.app.ecommerce.services.common.dtos.LocationDTO;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class OrderReturnController {
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

	@GetMapping(value = ViewPathConstants.ADD_ORDER_RETURN_URL)
	public String showOrderReturnScreen(Model model, Locale locale, final HttpServletRequest req) {
		logger.info("The add method for purchase order return display screen has been called");
		try {

			String orderIdStr = (String) req.getParameter(MVCConstants.ORDER_ID_PARAM);

			OrderBean orderBean = null;

			if (orderIdStr != null) {
				BigInteger orderId = new BigInteger(orderIdStr);
				Order order = this.orderService.searchOrder(orderId);
				if (order != null) {
					if (order.getStatus().equals(MVCConstants.STATUS_RECEIVED)) {
						orderBean = OrderTransformer.transformOrder(order);

						this.emptyOrderBeanDTO(model, orderBean);

					} else {
						model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.order.return.order.status", null, locale));
						logger.info("The provided order is not in RECEIVED status, Hence can not be used for a Return creation!!");
					}

				} else {
					model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.order.not.found", null, locale));
					logger.info("There is no order existing in database with provided order id {}", orderId);
				}
			} else {
				model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.order.return.noorder", null, locale));
				logger.info("There was not order provided for return creation");
			}

			logger.info("The empty purchase order return object bean has been created");
		} catch (Exception e) {
			logger.error("An unknown error has occurred while creating empty purchase order return.", e);
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
		}

		return ViewPathConstants.ADD_ORDER_RETURN_PAGE;

	}

	private void emptyOrderBeanDTO(Model model, OrderBean orderBean) {
		OrderReturnDTO orderReturnDTO = new OrderReturnDTO();

		OrderReturnBean orderReturnBean = new OrderReturnBean();
		orderReturnBean.setOrderId(orderBean.getOrderId());
		orderReturnBean.setOrder(orderBean);

		orderReturnDTO.setOrderReturn(orderReturnBean);

		this.updateOrderReturnModelDetails(model, orderReturnDTO);

		logger.info("An empty Order Return Bean DTO has been created and set with needed objects");
	}

	private void updateOrderReturnModelDetails(Model model, OrderReturnDTO orderReturnDTO) {

		LocationDTO locationDTO = this.commonService.retrieveLocationWithDailyStatus();
		List<LocationBean> locations = CommonMVCTransformer.transformLocationDTO(locationDTO);

		ReasonCode reasonCodeCriteria=new ReasonCode();
		reasonCodeCriteria.setType(MVCConstants.REASON_TYPE_PO_RETURN);
		List<ReasonCode> reasonCodes = this.commonService.retrieveReasonCodes(reasonCodeCriteria);
		if(reasonCodes!=null && !reasonCodes.isEmpty()) {
			orderReturnDTO.setReasonCodes(reasonCodes);
		}

		orderReturnDTO.setLocations(locations);

		model.addAttribute(MVCConstants.ORDER_RETURN_BEAN_DTO, orderReturnDTO);

		logger.info("The order return screen model objects has been updated successfully");
	}

	@PostMapping(value = ViewPathConstants.ADD_ORDER_RETURN_URL, params = { MVCConstants.SAVE_ORDER_RETURN_PARAM })
	public String saveOrderReturn(@ModelAttribute @Validated(ValidationGroup.VGAddOrderReturn.class) OrderReturnDTO orderReturnDTO, BindingResult bindingResult, Model model,
			Locale locale, HttpSession session, Authentication authentication) {
		if (bindingResult.hasErrors()) {
			this.updateOrderReturnModelDetails(model, orderReturnDTO);
			return ViewPathConstants.ADD_ORDER_RETURN_PAGE;
		}
		try {
			this.prepareReturnDetailsForSaving(orderReturnDTO, model, session, authentication, locale, MVCConstants.STATUS_CREATED);

		} catch (Exception e) {
			logger.error("There is an error while creating new purchase order", e);
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			this.updateOrderReturnModelDetails(model, orderReturnDTO);
		}
		return ViewPathConstants.ADD_ORDER_RETURN_PAGE;
	}

	@PostMapping(value = ViewPathConstants.ADD_ORDER_RETURN_URL, params = { MVCConstants.APPROVE_ORDER_RETURN_PARAM })
	public String approveOrderReturn(@ModelAttribute @Validated(ValidationGroup.VGAddOrderReturn.class) OrderReturnDTO orderReturnDTO, BindingResult bindingResult, Model model,
			Locale locale, HttpSession session, Authentication authentication) {
		if (bindingResult.hasErrors()) {
			this.updateOrderReturnModelDetails(model, orderReturnDTO);
			return ViewPathConstants.ADD_ORDER_RETURN_PAGE;
		}
		try {
			this.prepareReturnDetailsForSaving(orderReturnDTO, model, session, authentication, locale, MVCConstants.STATUS_APPROVED);

		} catch (Exception e) {
			logger.error("There is an error while creating new purchase order", e);
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			this.updateOrderReturnModelDetails(model, orderReturnDTO);
		}
		return ViewPathConstants.ADD_ORDER_RETURN_PAGE;
	}

	private void prepareReturnDetailsForSaving(OrderReturnDTO orderReturnDTO, Model model, HttpSession session, Authentication authentication, Locale locale, String status)
			throws IOException {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String username = userDetails.getUsername();
		OrderReturnBean orderReturnBean = orderReturnDTO.getOrderReturn();
		OrderReturn orderReturn = OrderReturnTransformer.transformOrderReturnBean(orderReturnBean, username, status);
		orderReturn = this.orderReturnService.createOrderReturn(orderReturn);
		logger.info("The {} order return details has been saved successfully", orderReturn.getOrderReturnId());
		orderReturnBean.setOrderReturnId(orderReturn.getOrderReturnId());
		orderReturnBean.setStatus(orderReturn.getStatus());
		orderReturnBean.setCreatedBy(orderReturn.getCreatedBy());
		orderReturnBean.setCreatedDate(orderReturn.getCreatedDate());

		this.updateOrderReturnModelDetails(model, orderReturnDTO);

		// This section is to update order report details
		// this.orderPrintUtil.createOrderReturnReport(orderReturnBean, username, session, MVCConstants.REPORT_ORDER_VIEW);
		logger.info("The {} order return report objects has been updated successfully", orderReturn.getOrderReturnId());

		if (status.equals(MVCConstants.STATUS_APPROVED)) {
			model.addAttribute(MVCConstants.SUCCESS,
					messageSource.getMessage("commerce.screen.order.return.add.approve.success", new Object[] { orderReturn.getOrderReturnId() }, locale));
		} else {
			model.addAttribute(MVCConstants.SUCCESS, messageSource.getMessage("commerce.screen.order.return.add.success", new Object[] { orderReturn.getOrderReturnId() }, locale));
		}
	}

	/**
	 * This method is used to update the reason code details in bean object
	 * 
	 * @param invAdjustBean
	 * @param reasonCodeList
	 */
	private void updateReasonCodes(InvAdjustBean invAdjustBean, List<StockReason> reasonCodeList) {
		InvReasonBean reasonCodeBean = null;
		List<InvReasonBean> reasonCodes = null;
		if (reasonCodeList != null && !reasonCodeList.isEmpty()) {
			reasonCodes = new ArrayList<>(reasonCodeList.size());
			for (StockReason stockReason : reasonCodeList) {
				reasonCodeBean = new InvReasonBean();
				reasonCodeBean.setReasonCodeId(stockReason.getReasonCodeId());
				reasonCodeBean.setReasonCode(stockReason.getReasonCode());
				reasonCodeBean.setName(stockReason.getName());
				if (stockReason.getFromBucket() != null)
					reasonCodeBean.setFromBucketId(stockReason.getFromBucket().getBucketId());
				if (stockReason.getToBucket() != null)
					reasonCodeBean.setToBucketId(stockReason.getToBucket().getBucketId());
				reasonCodes.add(reasonCodeBean);
			}
			invAdjustBean.setReasonCodes(reasonCodes);
		}
		logger.info("The Reason Code details has been set in Inventory Adjustment object");
	}

}
