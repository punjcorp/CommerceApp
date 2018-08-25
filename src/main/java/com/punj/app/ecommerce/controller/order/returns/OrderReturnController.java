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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
import com.punj.app.ecommerce.models.order.returns.validator.OrderReturnBeanValidator;
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
	private OrderReturnBeanValidator returnValidator;
	private OrderReturnPrintUtil reportUtil;

	/**
	 * @param reportUtil
	 *            the reportUtil to set
	 */
	@Autowired
	public void setReportUtil(OrderReturnPrintUtil reportUtil) {
		this.reportUtil = reportUtil;
	}

	/**
	 * @param returnValidator
	 *            the returnValidator to set
	 */
	@Autowired
	public void setOrderReturnBeanValidator(OrderReturnBeanValidator returnValidator) {
		this.returnValidator = returnValidator;
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
		orderReturnDTO.setLocations(locations);

		ReasonCode reasonCodeCriteria = new ReasonCode();
		reasonCodeCriteria.setType(MVCConstants.REASON_TYPE_PO_RETURN);
		List<ReasonCode> reasonCodes = this.commonService.retrieveReasonCodes(reasonCodeCriteria);
		if (reasonCodes != null && !reasonCodes.isEmpty()) {
			orderReturnDTO.setReasonCodes(reasonCodes);
		}

		BigInteger orderId = orderReturnDTO.getOrderReturn().getOrderId();

		Order order = this.orderService.searchOrder(orderId);
		if (order != null) {
			if (order.getStatus().equals(MVCConstants.STATUS_RECEIVED)) {
				OrderBean orderBean = OrderTransformer.transformOrder(order);
				orderReturnDTO.getOrderReturn().setOrder(orderBean);
			}
		}

		model.addAttribute(MVCConstants.ORDER_RETURN_BEAN_DTO, orderReturnDTO);

		logger.info("The order return screen model objects has been updated successfully");
	}

	@PostMapping(value = ViewPathConstants.ADD_ORDER_RETURN_URL, params = { MVCConstants.SAVE_ORDER_RETURN_PARAM })
	public String saveOrderReturn(@ModelAttribute("returnDTO") @Validated(ValidationGroup.VGAddOrderReturn.class) OrderReturnDTO returnDTO, BindingResult bindingResult,
			Model model, Locale locale, HttpSession session, Authentication authentication) {
		if (bindingResult.hasErrors()) {
			logger.error("There are some validation errors which needs to be corrected before processing");
			this.updateOrderReturnModelDetails(model, returnDTO);
			return ViewPathConstants.ADD_ORDER_RETURN_PAGE;
		} else {
			this.returnValidator.validate(returnDTO, bindingResult);
			if (bindingResult.hasErrors()) {
				this.updateOrderReturnModelDetails(model, returnDTO);
				return ViewPathConstants.ADD_ORDER_RETURN_PAGE;
			}
		}
		try {
			this.prepareReturnDetailsForSaving(returnDTO, model, session, authentication, locale, MVCConstants.STATUS_CREATED, Boolean.FALSE);

		} catch (Exception e) {
			logger.error("There is an error while creating new purchase order", e);
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			this.updateOrderReturnModelDetails(model, returnDTO);
		}
		return ViewPathConstants.ADD_ORDER_RETURN_PAGE;
	}

	@PostMapping(value = ViewPathConstants.ADD_ORDER_RETURN_URL, params = { MVCConstants.APPROVE_ORDER_RETURN_PARAM })
	public String approveOrderReturn(@ModelAttribute("returnDTO") @Validated(ValidationGroup.VGAddOrderReturn.class) OrderReturnDTO orderReturnDTO, BindingResult bindingResult,
			Model model, Locale locale, HttpSession session, Authentication authentication) {
		if (bindingResult.hasErrors()) {
			this.updateOrderReturnModelDetails(model, orderReturnDTO);
			return ViewPathConstants.ADD_ORDER_RETURN_PAGE;
		} else {
			this.returnValidator.validate(orderReturnDTO, bindingResult);
			if (bindingResult.hasErrors()) {
				this.updateOrderReturnModelDetails(model, orderReturnDTO);
				return ViewPathConstants.ADD_ORDER_RETURN_PAGE;
			}
		}
		try {
			this.prepareReturnDetailsForSaving(orderReturnDTO, model, session, authentication, locale, MVCConstants.STATUS_APPROVED, Boolean.FALSE);

		} catch (Exception e) {
			logger.error("There is an error while creating new purchase order", e);
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			this.updateOrderReturnModelDetails(model, orderReturnDTO);
		}
		return ViewPathConstants.ADD_ORDER_RETURN_PAGE;
	}

	private void prepareReturnDetailsForSaving(OrderReturnDTO orderReturnDTO, Model model, HttpSession session, Authentication authentication, Locale locale, String status,
			Boolean isUpdate) throws IOException {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String username = userDetails.getUsername();
		OrderReturnBean orderReturnBean = orderReturnDTO.getOrderReturn();
		OrderReturn orderReturn = OrderReturnTransformer.transformOrderReturnBean(orderReturnBean, username, status);
		orderReturn = OrderReturnTransformer.updateReturnBasedOnAction(orderReturn, username, status);
		if (isUpdate)
			orderReturn = this.orderReturnService.updateOrderReturn(orderReturn, username);
		else
			orderReturn = this.orderReturnService.createOrderReturn(orderReturn, username);
		logger.info("The {} order return details has been saved successfully", orderReturn.getOrderReturnId());
		
		orderReturn=this.orderReturnService.searchOrderReturn(orderReturn.getOrderReturnId());
		Order order=this.orderService.searchOrder(orderReturn.getOrder().getOrderId());
		orderReturn.setOrder(order);
		
		orderReturnBean=OrderReturnTransformer.transformOrderReturn(orderReturn);
		orderReturnDTO.setOrderReturn(orderReturnBean);

		this.updateOrderReturnModelDetails(model, orderReturnDTO);

		// This section is to update order return report details
		this.reportUtil.createOrderReturnReport(orderReturn, orderReturnBean, username, session, MVCConstants.REPORT_VIEW);
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

	@GetMapping(ViewPathConstants.EDIT_ORDER_RETURN_URL)
	public String approveOrderReturn(Model model, RedirectAttributes redirectAttrs, final HttpServletRequest req, Locale locale, Authentication authentication) {
		try {
			BigInteger orderReturnId = new BigInteger(req.getParameter(MVCConstants.ORDER_RETURN_ID_PARAM));
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();

			if (orderReturnId.compareTo(BigInteger.ZERO) > 0) {
				OrderReturn orderReturn = this.orderReturnService.searchOrderReturn(orderReturnId);
				if (orderReturn != null) {
					if (orderReturn.getStatus().equals(MVCConstants.STATUS_CREATED)) {
						OrderReturnBean orderReturnBean = OrderReturnTransformer.transformOrderReturn(orderReturn);
						OrderReturnDTO orderReturnDTO = new OrderReturnDTO();
						orderReturnDTO.setOrderReturn(orderReturnBean);

						this.updateOrderReturnModelDetails(model, orderReturnDTO);

						logger.info("The selected order return has been retrieved successfully for editing");
					} else {
						model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.order.return.editing.status", null, locale));
						logger.info("There order return is not eligible for the editing as status is not 'created'!!");
					}
				} else {
					model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.order.return.not.found", null, locale));
					logger.info("There order return was not found in the database!!");
				}

			} else {
				model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.order.return.manage.editing.noorder", null, locale));
				logger.info("There is no order return number specified for editing");
			}
		} catch (Exception e) {
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			logger.error("There is an error while retrieving purchase order return for editing", e);
		}

		return ViewPathConstants.EDIT_ORDER_RETURN_PAGE;
	}

	@PostMapping(value = ViewPathConstants.EDIT_ORDER_RETURN_URL, params = { MVCConstants.SAVE_ORDER_RETURN_PARAM })
	public String saveOrderReturnAfterEditing(@ModelAttribute("returnDTO") @Validated(ValidationGroup.VGAddOrderReturn.class) OrderReturnDTO returnDTO, BindingResult bindingResult,
			Model model, Locale locale, HttpSession session, Authentication authentication) {
		if (bindingResult.hasErrors()) {
			logger.error("There are some validation errors which needs to be corrected before processing");
			this.updateOrderReturnModelDetails(model, returnDTO);
			return ViewPathConstants.EDIT_ORDER_RETURN_PAGE;
		} else {
			this.returnValidator.validate(returnDTO, bindingResult);
			if (bindingResult.hasErrors()) {
				this.updateOrderReturnModelDetails(model, returnDTO);
				return ViewPathConstants.ADD_ORDER_RETURN_PAGE;
			}
		}
		try {
			this.prepareReturnDetailsForSaving(returnDTO, model, session, authentication, locale, MVCConstants.STATUS_CREATED, Boolean.TRUE);

		} catch (Exception e) {
			logger.error("There is an error while saving purchase order after editing", e);
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			this.updateOrderReturnModelDetails(model, returnDTO);
		}
		return ViewPathConstants.EDIT_ORDER_RETURN_PAGE;
	}

	@PostMapping(value = ViewPathConstants.EDIT_ORDER_RETURN_URL, params = { MVCConstants.APPROVE_ORDER_RETURN_PARAM })
	public String approveOrderReturnAfterEditing(@ModelAttribute("returnDTO") @Validated(ValidationGroup.VGAddOrderReturn.class) OrderReturnDTO orderReturnDTO,
			BindingResult bindingResult, Model model, Locale locale, HttpSession session, Authentication authentication) {
		if (bindingResult.hasErrors()) {
			this.updateOrderReturnModelDetails(model, orderReturnDTO);
			return ViewPathConstants.EDIT_ORDER_RETURN_PAGE;
		} else {
			this.returnValidator.validate(orderReturnDTO, bindingResult);
			if (bindingResult.hasErrors()) {
				this.updateOrderReturnModelDetails(model, orderReturnDTO);
				return ViewPathConstants.ADD_ORDER_RETURN_PAGE;
			}
		}
		try {
			this.prepareReturnDetailsForSaving(orderReturnDTO, model, session, authentication, locale, MVCConstants.STATUS_APPROVED, Boolean.TRUE);

		} catch (Exception e) {
			logger.error("There is an error while creating new purchase order", e);
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			this.updateOrderReturnModelDetails(model, orderReturnDTO);
		}
		return ViewPathConstants.EDIT_ORDER_RETURN_PAGE;
	}

}
