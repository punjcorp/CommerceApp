package com.punj.app.ecommerce.controller.order;
/**
 * 
 */

import java.time.LocalDateTime;
import java.util.ArrayList;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.punj.app.ecommerce.domains.order.Order;
import com.punj.app.ecommerce.domains.order.OrderItem;
import com.punj.app.ecommerce.domains.order.ids.OrderItemId;
import com.punj.app.ecommerce.models.item.ItemBean;
import com.punj.app.ecommerce.models.order.OrderBean;
import com.punj.app.ecommerce.models.order.OrderItemBean;
import com.punj.app.ecommerce.models.supplier.SupplierBean;
import com.punj.app.ecommerce.services.OrderService;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class ManageOrderController {
	private static final Logger logger = LogManager.getLogger();
	private OrderService orderService;
	private MessageSource messageSource;

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

	@GetMapping(value = "/add_order")
	public String addOrder(Model model, HttpSession session) {
		logger.info("The add method for new purchase order has been called");
		try {
			OrderBean orderBean = new OrderBean();
			SupplierBean supplierBean = new SupplierBean();
			ItemBean itemBean = new ItemBean();

			model.addAttribute("orderBean", orderBean);
			model.addAttribute("supplierBean", supplierBean);
			model.addAttribute("itemBean", itemBean);
			logger.info("The empty purchase order object bean has been created");
		} catch (Exception e) {
			logger.error("An unknown error has occurred while creating empty purchase order.", e);
			return "error";
		}

		return "order/add_order";

	}

	@PostMapping(value = "/add_order", params = { "addOrderItem" })
	public String addRow(@ModelAttribute OrderBean orderBean, Model model, final BindingResult bindingResult) {
		orderBean.getOrderItems().add(new OrderItemBean());

		SupplierBean supplierBean = new SupplierBean();
		ItemBean itemBean = new ItemBean();

		model.addAttribute("supplierBean", supplierBean);
		model.addAttribute("itemBean", itemBean);

		return "order/add_order";
	}

	@PostMapping(value = "/add_order", params = { "removeOrderItem" })
	public String removeRow(@ModelAttribute OrderBean orderBean, Model model, final BindingResult bindingResult,
			final HttpServletRequest req) {
		final Integer rowId = Integer.valueOf(req.getParameter("id"));
		orderBean.getOrderItems().remove(rowId.intValue());
		SupplierBean supplierBean = new SupplierBean();
		ItemBean itemBean = new ItemBean();

		model.addAttribute("supplierBean", supplierBean);
		model.addAttribute("itemBean", itemBean);
		return "order/add_order";
	}

	@PostMapping(value = "/add_order", params = { "saveOrder" })
	public String saveSupplier(@ModelAttribute @Valid OrderBean orderBean, BindingResult bindingResult,@ModelAttribute SupplierBean supplierBean,
			@ModelAttribute ItemBean itemBean, Model model, HttpSession session,
			Locale locale, Authentication authentication) {
		if (bindingResult.hasErrors())
			return "order/add_order";
		try {

			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			Order order = new Order();
			this.updateOrderDomain(order, orderBean, userDetails);

			order = orderService.createOrder(order);
			logger.info("The order details has been saved successfully");
			
			model.addAttribute("success", messageSource.getMessage("commerce.screen.order.add.success",
					new Object[] { order.getOrderId() }, locale));

		} catch (Exception e) {
			logger.error("There is an error while creating new purchase order", e);
			return "error";
		}
		return "order/add_order";
	}

	/**
	 * This method is used to set bean details in domain object
	 * 
	 * @param order
	 * @param orderBean
	 * @param userDetails
	 */
	private void updateOrderDomain(Order order, OrderBean orderBean, UserDetails userDetails) {

		order.setSupplierId(orderBean.getSupplierId());
		order.setCreatedBy(userDetails.getUsername());
		order.setCreatedDate(LocalDateTime.now());
		order.setStatus("C");

		/**
		 * Update Order Item details
		 */
		List<OrderItemBean> orderItemBeanList = orderBean.getOrderItems();
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		OrderItem orderItem = null;
		OrderItemId orderItemId = null;

		for (OrderItemBean orderItemBean : orderItemBeanList) {
			orderItem = new OrderItem();
			orderItemId = new OrderItemId();

			// Fucking always remember this in relationships
			orderItemId.setOrder(order);

			orderItemId.setItemId(orderItemBean.getItemId());
			orderItemId.setLocation(orderItemBean.getLocationId());
			orderItem.setOrderItemId(orderItemId);

			orderItem.setOrderedQty(orderItemBean.getOrderedQty());
			orderItem.setCostAmount(orderItemBean.getCostAmount());
			orderItem.setTotalCost(orderItemBean.getTotalCost());

			orderItems.add(orderItem);

		}

		order.setOrderItems(orderItems);

		logger.info("The purchase order details has been saved in domain");

	}
}
