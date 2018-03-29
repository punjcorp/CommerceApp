package com.punj.app.ecommerce.controller.order;
/**
 * 
 */

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.CommonMVCTransformer;
import com.punj.app.ecommerce.controller.common.transformer.OrderTransformer;
import com.punj.app.ecommerce.domains.order.Order;
import com.punj.app.ecommerce.models.common.AddressBean;
import com.punj.app.ecommerce.models.common.LocationBean;
import com.punj.app.ecommerce.models.common.SearchBean;
import com.punj.app.ecommerce.models.order.OrderBean;
import com.punj.app.ecommerce.models.order.OrderBeanDTO;
import com.punj.app.ecommerce.models.order.OrderItemBean;
import com.punj.app.ecommerce.models.supplier.SupplierBean;
import com.punj.app.ecommerce.services.OrderService;
import com.punj.app.ecommerce.services.common.CommonService;
import com.punj.app.ecommerce.services.common.dtos.LocationDTO;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class OrderController {
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

	@GetMapping(value = ViewPathConstants.ADD_ORDER_URL)
	public String addOrder(Model model, Locale locale) {
		logger.info("The add method for purchase order display screen has been called");
		try {
			OrderBeanDTO orderBeanDTO = new OrderBeanDTO();

			OrderBean orderBean = new OrderBean();
			SupplierBean supplierBean = new SupplierBean();
			AddressBean primaryAddress = new AddressBean();
			supplierBean.setPrimaryAddress(primaryAddress);
			orderBean.setSupplier(supplierBean);

			orderBeanDTO.setOrder(orderBean);
			orderBeanDTO.setItemSearch(new SearchBean());
			orderBeanDTO.setSupplierSearch(new SearchBean());

			LocationDTO locationDTO = this.commonService.retrieveLocationWithDailyStatus();
			List<LocationBean> locations = CommonMVCTransformer.transformLocationDTO(locationDTO);

			orderBeanDTO.setLocations(locations);

			model.addAttribute(MVCConstants.ORDER_BEAN_DTO, orderBeanDTO);
			logger.info("The empty purchase order object bean has been created");
		} catch (Exception e) {
			logger.error("An unknown error has occurred while creating empty purchase order.", e);
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			return ViewPathConstants.ADD_ORDER_PAGE;
		}

		return ViewPathConstants.ADD_ORDER_PAGE;

	}

	@PostMapping(value = ViewPathConstants.ADD_ORDER_URL, params = { MVCConstants.ADD_ORDER_ITEM_PARAM })
	public String addRow(@ModelAttribute OrderBeanDTO orderBeanDTO, final BindingResult bindingResult, Model model, Locale locale) {
		orderBeanDTO.getOrder().getOrderItems().add(new OrderItemBean());
		model.addAttribute(MVCConstants.ORDER_BEAN_DTO, orderBeanDTO);
		logger.info("A new empty order line item has been added to the order screen");
		return ViewPathConstants.ADD_ORDER_PAGE;
	}

	@PostMapping(value = ViewPathConstants.ADD_ORDER_URL, params = { MVCConstants.REMOVE_ORDER_ITEM_PARAM })
	public String removeRow(@ModelAttribute OrderBeanDTO orderBeanDTO, final BindingResult bindingResult, Model model, Locale locale,
			final HttpServletRequest req) {
		final Integer rowId = Integer.parseInt(req.getParameter(MVCConstants.ID_PARAM));
		orderBeanDTO.getOrder().getOrderItems().remove(rowId.intValue());
		model.addAttribute(MVCConstants.ORDER_BEAN_DTO, orderBeanDTO);
		logger.info("The selected line item has been deleted from the order");
		return ViewPathConstants.ADD_ORDER_PAGE;
	}

	@PostMapping(value = ViewPathConstants.ADD_ORDER_URL, params = { MVCConstants.SAVE_ORDER_PARAM })
	public String saveOrder(@ModelAttribute @Valid OrderBeanDTO orderBeanDTO, BindingResult bindingResult, Model model, Locale locale,
			Authentication authentication) {
		if (bindingResult.hasErrors())
			return ViewPathConstants.ADD_ORDER_PAGE;
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			OrderBean orderBean = orderBeanDTO.getOrder();
			Order order = OrderTransformer.transformOrderBean(orderBeanDTO.getOrder(), userDetails.getUsername(), MVCConstants.STATUS_CREATED);
			order = this.orderService.createOrder(order);
			logger.info("The {} order details has been saved successfully", order.getOrderId());
			orderBean.setOrderId(order.getOrderId());
			model.addAttribute(MVCConstants.ORDER_BEAN_DTO, orderBeanDTO);
			model.addAttribute(MVCConstants.SUCCESS,
					messageSource.getMessage("commerce.screen.order.add.success", new Object[] { order.getOrderId() }, locale));

		} catch (Exception e) {
			logger.error("There is an error while creating new purchase order", e);
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			return ViewPathConstants.ADD_ORDER_PAGE;
		}
		return ViewPathConstants.ADD_ORDER_PAGE;
	}

}
