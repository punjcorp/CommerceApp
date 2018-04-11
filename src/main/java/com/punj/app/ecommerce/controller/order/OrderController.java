package com.punj.app.ecommerce.controller.order;
/**
 * 
 */

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.CommonMVCTransformer;
import com.punj.app.ecommerce.controller.common.transformer.OrderTransformer;
import com.punj.app.ecommerce.domains.common.Location;
import com.punj.app.ecommerce.domains.order.Order;
import com.punj.app.ecommerce.domains.order.OrderItem;
import com.punj.app.ecommerce.models.common.AddressBean;
import com.punj.app.ecommerce.models.common.LocationBean;
import com.punj.app.ecommerce.models.common.SearchBean;
import com.punj.app.ecommerce.models.financials.AccountDTO;
import com.punj.app.ecommerce.models.item.ItemBean;
import com.punj.app.ecommerce.models.order.OrderBean;
import com.punj.app.ecommerce.models.order.OrderBeanDTO;
import com.punj.app.ecommerce.models.order.OrderItemBean;
import com.punj.app.ecommerce.models.order.OrderReportBean;
import com.punj.app.ecommerce.models.supplier.SupplierBean;
import com.punj.app.ecommerce.services.OrderService;
import com.punj.app.ecommerce.services.common.CommonService;
import com.punj.app.ecommerce.services.common.dtos.LocationDTO;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

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

	@GetMapping(value = ViewPathConstants.ADD_ORDER_URL)
	public String addOrder(Model model, Locale locale) {
		logger.info("The add method for purchase order display screen has been called");
		try {

			this.emptyOrderBeanDTO(model);
			logger.info("The empty purchase order object bean has been created");
		} catch (Exception e) {
			logger.error("An unknown error has occurred while creating empty purchase order.", e);
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			return ViewPathConstants.ADD_ORDER_PAGE;
		}

		return ViewPathConstants.ADD_ORDER_PAGE;

	}

	private void emptyOrderBeanDTO(Model model) {
		OrderBeanDTO orderBeanDTO = new OrderBeanDTO();

		OrderBean orderBean = new OrderBean();

		SupplierBean supplierBean = new SupplierBean();
		AddressBean primaryAddress = new AddressBean();
		supplierBean.setPrimaryAddress(primaryAddress);
		orderBean.setSupplier(supplierBean);
		orderBeanDTO.setOrder(orderBean);

		orderBeanDTO.setSupplierSearch(new SearchBean());
		this.updateOrderModelDetails(model, orderBeanDTO);

		logger.info("An empty Order Bean DTO has been created and set with needed objects");
	}

	private void updateOrderModelDetails(Model model, OrderBeanDTO orderBeanDTO) {

		LocationDTO locationDTO = this.commonService.retrieveLocationWithDailyStatus();
		List<LocationBean> locations = CommonMVCTransformer.transformLocationDTO(locationDTO);

		orderBeanDTO.setLocations(locations);

		model.addAttribute(MVCConstants.ORDER_BEAN_DTO, orderBeanDTO);

		logger.info("The order screen model objects has been updated successfully");
	}

	@PostMapping(value = ViewPathConstants.ADD_ORDER_URL, params = { MVCConstants.ADD_ORDER_ITEM_PARAM })
	public String addRow(@ModelAttribute OrderBeanDTO orderBeanDTO, final BindingResult bindingResult, Model model, Locale locale) {
		orderBeanDTO.getOrder().getOrderItems().add(new OrderItemBean());
		this.updateOrderModelDetails(model, orderBeanDTO);
		logger.info("A new empty order line item has been added to the order screen");
		return ViewPathConstants.ADD_ORDER_PAGE;
	}

	@PostMapping(value = ViewPathConstants.ADD_ORDER_URL, params = { MVCConstants.REMOVE_ORDER_ITEM_PARAM })
	public String removeRow(@ModelAttribute OrderBeanDTO orderBeanDTO, final BindingResult bindingResult, Model model, Locale locale,
			final HttpServletRequest req) {
		final Integer rowId = Integer.parseInt(req.getParameter(MVCConstants.ID_PARAM));
		orderBeanDTO.getOrder().getOrderItems().remove(rowId.intValue());
		this.updateOrderModelDetails(model, orderBeanDTO);
		logger.info("The selected line item has been deleted from the order");
		return ViewPathConstants.ADD_ORDER_PAGE;
	}

	@PostMapping(value = ViewPathConstants.ADD_ORDER_URL, params = { MVCConstants.SAVE_ORDER_PARAM })
	public String saveOrder(@ModelAttribute @Valid OrderBeanDTO orderBeanDTO, BindingResult bindingResult, Model model, Locale locale, HttpSession session,
			Authentication authentication) {
		if (bindingResult.hasErrors()) {
			this.updateOrderModelDetails(model, orderBeanDTO);
			return ViewPathConstants.ADD_ORDER_PAGE;
		}
		try {
			this.prepareOrderDetailsForSaving(orderBeanDTO, model, session, authentication, locale, MVCConstants.STATUS_CREATED);

		} catch (Exception e) {
			logger.error("There is an error while creating new purchase order", e);
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			this.updateOrderModelDetails(model, orderBeanDTO);
			return ViewPathConstants.ADD_ORDER_PAGE;
		}
		return ViewPathConstants.ADD_ORDER_PAGE;
	}

	private void prepareOrderDetailsForSaving(OrderBeanDTO orderBeanDTO, Model model, HttpSession session, Authentication authentication, Locale locale,
			String status) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String username=userDetails.getUsername();
		OrderBean orderBean = orderBeanDTO.getOrder();
		Order order = OrderTransformer.transformOrderBean(orderBean,username , status, Boolean.FALSE);
		order = this.orderService.createOrder(order);
		logger.info("The {} order details has been saved successfully", order.getOrderId());
		orderBean.setOrderId(order.getOrderId());
		orderBean.setStatus(order.getStatus());
		orderBean.setCreatedBy(order.getCreatedBy());
		orderBean.setCreatedDate(order.getCreatedDate());

		this.updateOrderModelDetails(model, orderBeanDTO);

		// This section is to update order report details
		this.orderPrintUtil.createOrderReport(orderBean, username, session, MVCConstants.REPORT_ORDER_VIEW);
		logger.info("The {} order report objects has been updated successfully", order.getOrderId());

		if (status.equals(MVCConstants.STATUS_APPROVED)) {
			model.addAttribute(MVCConstants.SUCCESS,
					messageSource.getMessage("commerce.screen.order.add.approve.success", new Object[] { order.getOrderId() }, locale));
		} else {
			model.addAttribute(MVCConstants.SUCCESS,
					messageSource.getMessage("commerce.screen.order.add.success", new Object[] { order.getOrderId() }, locale));
		}
	}



	@PostMapping(value = ViewPathConstants.ADD_ORDER_URL, params = { MVCConstants.APPROVE_ORDER_PARAM })
	public String approveOrder(@ModelAttribute @Valid OrderBeanDTO orderBeanDTO, BindingResult bindingResult, Model model, Locale locale, HttpSession session,
			Authentication authentication) {
		if (bindingResult.hasErrors()) {
			this.updateOrderModelDetails(model, orderBeanDTO);
			return ViewPathConstants.ADD_ORDER_PAGE;
		}
		try {

			this.prepareOrderDetailsForSaving(orderBeanDTO, model, session, authentication, locale, MVCConstants.STATUS_APPROVED);

		} catch (Exception e) {
			logger.error("There is an error while approving new purchase order", e);
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			this.updateOrderModelDetails(model, orderBeanDTO);
			return ViewPathConstants.ADD_ORDER_PAGE;
		}
		return ViewPathConstants.ADD_ORDER_PAGE;
	}

	private OrderReportBean generateOrderReportBean(OrderBean orderBean, String username) {

		Location location = this.commonService.retrieveLocationDetails(orderBean.getLocationId());
		LocationBean locationBean = CommonMVCTransformer.transformLocationDomainPartially(location, Boolean.FALSE);
		OrderReportBean orderReportBean = OrderTransformer.prepareOrderReportBean(orderBean, locationBean, username);

		logger.info("The order report bean object has been generated successfully");

		return orderReportBean;
	}


	@GetMapping(value = ViewPathConstants.EDIT_ORDER_URL)
	public String editOrder(Model model, final HttpServletRequest req, Locale locale) {
		logger.info("The add method for purchase order display screen has been called");
		try {
			BigInteger orderId = new BigInteger(req.getParameter(MVCConstants.ORDER_ID_PARAM));

			Order order = this.orderService.searchOrder(orderId);

			OrderBeanDTO orderBeanDTO = new OrderBeanDTO();
			OrderBean orderBean = OrderTransformer.transformOrder(order);
			orderBeanDTO.setOrder(orderBean);

			SupplierBean supplierBean = orderBean.getSupplier();
			SearchBean supplierSearch = new SearchBean();
			supplierSearch.setSearchText(supplierBean.getName());
			orderBeanDTO.setSupplierSearch(supplierSearch);

			this.updateOrderModelDetails(model, orderBeanDTO);

			logger.info("The selected purchase order has been updated successfully");
		} catch (Exception e) {
			logger.error("There is an error while retrieving purchase order for updation", e);
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			this.emptyOrderBeanDTO(model);
			return ViewPathConstants.EDIT_ORDER_PAGE;
		}
		return ViewPathConstants.EDIT_ORDER_PAGE;

	}

	@PostMapping(value = ViewPathConstants.EDIT_ORDER_URL, params = { MVCConstants.ADD_ORDER_ITEM_PARAM })
	public String addRowEdit(@ModelAttribute OrderBeanDTO orderBeanDTO, final BindingResult bindingResult, Model model, Locale locale) {
		orderBeanDTO.getOrder().getOrderItems().add(new OrderItemBean());
		this.updateOrderModelDetails(model, orderBeanDTO);
		logger.info("A new empty order line item has been added to the EDIT order screen");
		return ViewPathConstants.EDIT_ORDER_PAGE;
	}

	@PostMapping(value = ViewPathConstants.EDIT_ORDER_URL, params = { MVCConstants.REMOVE_ORDER_ITEM_PARAM })
	public String removeRowEdit(@ModelAttribute OrderBeanDTO orderBeanDTO, Model model, Locale locale, final HttpServletRequest req,
			Authentication authentication) {
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			final Integer rowId = Integer.valueOf(req.getParameter(MVCConstants.ID_PARAM));
			OrderItemBean orderItemBean = orderBeanDTO.getOrder().getOrderItems().get(rowId.intValue());
			orderItemBean.setOrderId(orderBeanDTO.getOrder().getOrderId());
			OrderItem orderItem = OrderTransformer.transformOrderItemBean(orderItemBean, null);
			this.orderService.deleteOrderItem(orderItem);
			Order order = this.orderService.updateOrderTotals(orderItem.getOrderItemId().getOrder().getOrderId(), userDetails.getUsername());
			if (order != null) {
				orderBeanDTO.getOrder().getOrderItems().remove(rowId.intValue());
				logger.info("The selected purchase order item has been deleted now");
			} else {
				logger.info("There was some issue during order item deletion");
			}
			this.updateOrderModelDetails(model, orderBeanDTO);
		} catch (Exception e) {
			logger.error("There is an error while deleting purchase order selected item", e);
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			this.emptyOrderBeanDTO(model);
			return ViewPathConstants.EDIT_ORDER_PAGE;
		}
		return ViewPathConstants.EDIT_ORDER_PAGE;
	}

	@PostMapping(value = ViewPathConstants.EDIT_ORDER_URL, params = { MVCConstants.SAVE_ORDER_PARAM })
	public String saveOrderAfterEdit(@ModelAttribute @Valid OrderBeanDTO orderBeanDTO, BindingResult bindingResult, Model model, Locale locale,
			HttpSession session, Authentication authentication) {
		if (bindingResult.hasErrors()) {
			this.updateOrderModelDetails(model, orderBeanDTO);
			return ViewPathConstants.EDIT_ORDER_PAGE;
		}
		try {
			this.prepareOrderDetailsAfterUpdates(orderBeanDTO, model, session, authentication, locale,orderBeanDTO.getOrder().getStatus());
		} catch (Exception e) {
			logger.error("There is an error while updating purchase order", e);
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			this.updateOrderModelDetails(model, orderBeanDTO);
		}
		return ViewPathConstants.EDIT_ORDER_PAGE;
	}
	
	private void prepareOrderDetailsAfterUpdates(OrderBeanDTO orderBeanDTO, Model model, HttpSession session, Authentication authentication, Locale locale, String status) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String username=userDetails.getUsername();
		OrderBean orderBean = orderBeanDTO.getOrder();
		Order order = OrderTransformer.transformOrderBean(orderBean, username, status, Boolean.TRUE);
		order = this.orderService.createOrder(order);
		logger.info("The {} order details has been saved successfully", order.getOrderId());
		orderBean.setOrderId(order.getOrderId());

		this.updateOrderModelDetails(model, orderBeanDTO);

		// This section is to update order report details
		this.orderPrintUtil.createOrderReport(orderBean, username, session, MVCConstants.REPORT_ORDER_VIEW);
		logger.info("The {} order report objects has been updated successfully", order.getOrderId());

		if (order.getStatus().equals(MVCConstants.STATUS_APPROVED)) {
			model.addAttribute(MVCConstants.SUCCESS,
					messageSource.getMessage("commerce.screen.order.edit.approve.success", new Object[] { order.getOrderId() }, locale));
		} else {
			model.addAttribute(MVCConstants.SUCCESS,
					messageSource.getMessage("commerce.screen.order.edit.save.success", new Object[] { order.getOrderId() }, locale));
		}
	}	
	
	@PostMapping(value = ViewPathConstants.EDIT_ORDER_URL, params = { MVCConstants.APPROVE_ORDER_PARAM })
	public String approveOrderAfterEdit(@ModelAttribute @Valid OrderBeanDTO orderBeanDTO, BindingResult bindingResult, Model model, Locale locale, HttpSession session,
			Authentication authentication) {
		if (bindingResult.hasErrors()) {
			this.updateOrderModelDetails(model, orderBeanDTO);
			return ViewPathConstants.EDIT_ORDER_PAGE;
		}
		try {

			this.prepareOrderDetailsAfterUpdates(orderBeanDTO, model, session, authentication, locale, MVCConstants.STATUS_APPROVED);

		} catch (Exception e) {
			logger.error("There is an error while approving new purchase order", e);
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			this.updateOrderModelDetails(model, orderBeanDTO);
		}
		return ViewPathConstants.EDIT_ORDER_PAGE;
	}	

}
