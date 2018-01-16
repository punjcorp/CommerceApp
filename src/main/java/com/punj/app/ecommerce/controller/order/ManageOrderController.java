package com.punj.app.ecommerce.controller.order;
/**
 * 
 */

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.RequestParam;

import com.punj.app.ecommerce.domains.order.Order;
import com.punj.app.ecommerce.domains.order.OrderDTO;
import com.punj.app.ecommerce.domains.order.OrderItem;
import com.punj.app.ecommerce.domains.order.ids.OrderItemId;
import com.punj.app.ecommerce.domains.supplier.Supplier;
import com.punj.app.ecommerce.domains.user.Address;
import com.punj.app.ecommerce.models.common.AddressBean;
import com.punj.app.ecommerce.models.item.ItemBean;
import com.punj.app.ecommerce.models.order.OrderBean;
import com.punj.app.ecommerce.models.order.OrderBeanDTO;
import com.punj.app.ecommerce.models.order.OrderItemBean;
import com.punj.app.ecommerce.models.supplier.SupplierBean;
import com.punj.app.ecommerce.services.OrderService;
import com.punj.app.ecommerce.utils.Pager;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;

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

		this.calculateOrderCost(orderBean);

		model.addAttribute("orderBean", orderBean);
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

		this.calculateOrderCost(orderBean);

		model.addAttribute("orderBean", orderBean);
		model.addAttribute("supplierBean", supplierBean);
		model.addAttribute("itemBean", itemBean);
		return "order/add_order";
	}

	@PostMapping(value = "/add_order", params = { "saveOrder" })
	public String saveOrder(@ModelAttribute @Valid OrderBean orderBean, BindingResult bindingResult,
			@ModelAttribute SupplierBean supplierBean, @ModelAttribute ItemBean itemBean, Model model,
			HttpSession session, Locale locale, Authentication authentication) {
		if (bindingResult.hasErrors())
			return "order/add_order";
		try {

			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			Order order = new Order();
			this.createOrderDomain(order, orderBean, userDetails);

			order = orderService.createOrder(order);
			logger.info("The order details has been saved successfully");

			this.calculateOrderCost(orderBean);
			model.addAttribute("orderBean", orderBean);

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
	private void updateOrderDomain(Order order, OrderBean orderBean, UserDetails userDetails, String status) {

		order.setOrderId(orderBean.getOrderId());
		
		Supplier supplier=new Supplier();
		supplier.setSupplierId(orderBean.getSupplierId());
		order.setSupplier(supplier);
		
		if (userDetails != null)
			order.setCreatedBy(userDetails.getUsername());
		order.setCreatedDate(LocalDateTime.now());
		order.setStatus(status);
		order.setEstimatedCost(orderBean.getEstimatedCost());

		order.setDiscountAmount(orderBean.getDiscountAmount());
		order.setTaxAmount(orderBean.getTaxAmount());
		order.setPaidAmount(orderBean.getPaidAmount());
		order.setTotalAmount(orderBean.getTotalAmount());

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

			orderItem.setDelieveredQty(orderItemBean.getDelieveredQty());
			orderItem.setDelieveredDate(LocalDateTime.now());

			orderItem.setActualUnitCost(orderItemBean.getCostActualAmount());
			orderItem.setCostAmount(orderItemBean.getCostAmount());
			orderItem.setDiscountAmount(orderItemBean.getDiscountAmount());
			orderItem.setTaxAmount(orderItemBean.getTaxAmount());
			orderItem.setActualTotalAmount(orderItemBean.getTotalActualAmount());

			orderItems.add(orderItem);

		}

		order.setOrderItems(orderItems);

		logger.info("The purchase order details has been saved in domain");

	}

	@GetMapping(value = "/manage_order")
	public String manageOrder(@RequestParam("orderId") Optional<String> orderId, Model model, HttpSession session) {
		logger.info("The manage method for purchase orders has been called");
		try {
			String searchText = null;
			if (orderId == null || !orderId.isPresent()) {
				orderId = null;
			} else {
				searchText = orderId.get();
			}

			Pager pager = new Pager();
			pager.setCurrentPageNo(1);

			OrderBean orderBean = new OrderBean();
			model.addAttribute("orderBean", orderBean);
			OrderDTO ordersDTO = null;

			if (orderId != null) {
				ordersDTO = orderService.searchOrder(searchText, pager);
			} else {
				ordersDTO = orderService.searchAllOrders(pager);
			}

			OrderBeanDTO orders = new OrderBeanDTO();
			this.updateOrderBeanDTO(orders, ordersDTO);

			model.addAttribute("orders", orders);

			logger.info("The purchase order details bean has been retrieved");
		} catch (Exception e) {
			logger.error("An unknown error has occurred while retrieving purchase orders.", e);
			return "error";
		}

		return "order/manage_order";

	}

	private void updateOrderBeanDTO(OrderBeanDTO orders, OrderDTO ordersDTO) {

		List<Order> orderList = ordersDTO.getOrders();
		List<OrderBean> orderBeanList = new ArrayList<OrderBean>();
		OrderBean orderBean = null;

		for (Order order : orderList) {
			orderBean = new OrderBean();
			this.updateBean(orderBean, order);
			orderBeanList.add(orderBean);
		}

		orders.setOrders(orderBeanList);
		logger.info("The purchase order details has been added to the DTO object");
	}

	private void updateBean(OrderBean orderBean, Order order) {
		orderBean.setOrderId(order.getOrderId());
		orderBean.setSupplierId(order.getSupplier().getSupplierId());
		
		SupplierBean supplierBean=new SupplierBean();
		this.updateSupplierBean(supplierBean,order.getSupplier());
		orderBean.setSupplier(supplierBean);
		
		orderBean.setCreatedBy(order.getCreatedBy());
		orderBean.setCreatedDate(order.getCreatedDate());

		orderBean.setTotalAmount(order.getTotalAmount());
		orderBean.setPaidAmount(order.getPaidAmount());
		orderBean.setDiscountAmount(order.getDiscountAmount());
		orderBean.setTaxAmount(order.getTaxAmount());
		orderBean.setEstimatedCost(order.getEstimatedCost());

		orderBean.setStatus(order.getStatus());

		logger.info("The basic details for the order has been updated in Bean object now");

	}

	/**
	 * This method is used to set the details of retrieved suppliers to Bean object
	 * so the details can be displayed on the screen
	 * 
	 * @param supplierBean
	 * @param supplier
	 */
	private void updateSupplierBean(SupplierBean supplierBean, Supplier supplier) {

		supplierBean.setSupplierId(supplier.getSupplierId());
		supplierBean.setName(supplier.getName());
		supplierBean.setEmail(supplier.getEmail());
		supplierBean.setPhone1(supplier.getPhone1());
		supplierBean.setPhone2(supplier.getPhone2());

		List<Address> supplierAddressList = supplier.getAddresses();
		List<AddressBean> supplierAddresses = new ArrayList<AddressBean>();

		AddressBean addressBean = null;
		if (supplierAddressList != null && supplierAddressList.size() > 0) {
			for (Address address : supplierAddressList) {

				addressBean = new AddressBean();

				addressBean.setAddressId(address.getAddressId());
				addressBean.setPrimary(address.getPrimary());
				addressBean.setAddress1(address.getAddress1());
				addressBean.setAddress2(address.getAddress2());
				addressBean.setCity(address.getCity());
				addressBean.setState(address.getState());
				addressBean.setCountry(address.getCountry());
				addressBean.setPincode(address.getPincode());
				addressBean.setAddressType(address.getAddressType());

				supplierAddresses.add(addressBean);
			}
		}
		supplierBean.setAddresses(supplierAddresses);

		logger.info("The supplier details has been updated in bean object now");

	}
	
	
	private void updateOrderItemsBean(OrderBean orderBean, Order order) {
		this.updateBean(orderBean, order);

		List<OrderItem> orderItems = order.getOrderItems();

		List<OrderItemBean> orderItemBeanList = new ArrayList<OrderItemBean>();
		OrderItemBean orderItemBean = null;
		for (OrderItem orderItem : orderItems) {
			orderItemBean = new OrderItemBean();
			orderItemBean.setOrderId(order.getOrderId());
			orderItemBean.setItemId(orderItem.getOrderItemId().getItemId());
			orderItemBean.setLocationId(orderItem.getOrderItemId().getLocation());
			orderItemBean.setOrderedQty(orderItem.getOrderedQty());

			orderItemBean.setCostAmount(orderItem.getCostAmount());
			orderItemBean.setTotalCost(orderItem.getTotalCost());

			orderItemBean.setDelieveredQty(orderItem.getDelieveredQty());
			orderItemBean.setDelieveredDate(orderItem.getDelieveredDate());

			orderItemBean.setCostActualAmount(orderItem.getActualUnitCost());
			orderItemBean.setTotalActualCost(orderItem.getTotalActualCost());
			orderItemBean.setDiscountAmount(orderItem.getDiscountAmount());
			orderItemBean.setTaxAmount(orderItem.getTaxAmount());
			orderItemBean.setTotalActualAmount(orderItem.getActualTotalAmount());

			orderItemBeanList.add(orderItemBean);
		}

		orderBean.setOrderItems(orderItemBeanList);
		logger.info("The basic details for the order items has been updated in Bean objects now");
	}

	private void updateOrderItem(OrderItemBean orderItemBean, OrderItem orderItem, BigInteger orderId) {
		OrderItemId orderItemId = new OrderItemId();
		orderItemId.setLocation(orderItemBean.getLocationId());
		orderItemId.setItemId(orderItemBean.getItemId());
		Order order = new Order();
		order.setOrderId(orderId);
		orderItemId.setOrder(order);

		orderItem.setOrderItemId(orderItemId);

	}

	@PostMapping("/search_order")
	public String searchOrder(@ModelAttribute OrderBean orderBean, @RequestParam("page") Optional<Integer> page,
			Model model, HttpSession session, Locale locale) {
		try {

			Pager pager = orderBean.getPager();
			pager = new Pager();
			if (page == null || !page.isPresent()) {
				pager.setCurrentPageNo(1);
			} else {
				pager.setCurrentPageNo(page.get());
			}

			OrderDTO ordersDTO = orderService.searchOrder(orderBean.getOrderId().toString(), pager);

			OrderBeanDTO orders = new OrderBeanDTO();
			this.updateOrderBeanDTO(orders, ordersDTO);

			Pager tmpPager = ordersDTO.getPager();
			pager = new Pager(tmpPager.getResultSize(), tmpPager.getPageSize(), tmpPager.getCurrentPageNo(),
					tmpPager.getMaxDisplayPage());

			model.addAttribute("orders", orders);
			model.addAttribute("orderBean", orderBean);
			model.addAttribute("pager", pager);
			model.addAttribute("success", messageSource.getMessage("commerce.screen.order.search.result",
					new Object[] { pager.getResultSize() }, locale));

			logger.info("The purchase order details has been retrieved successfully.");
		} catch (Exception e) {
			logger.error("There is an error while retrieving purchase orders", e);
			return "error";
		}
		return "order/manage_order";
	}

	@GetMapping("/edit_order")
	public String editOrder(Model model, HttpSession session, final HttpServletRequest req) {
		try {
			BigInteger orderId = new BigInteger(req.getParameter("orderId"));

			Order order = orderService.searchOrder(orderId);

			OrderBean orderBean = new OrderBean();
			this.updateOrderItemsBean(orderBean, order);

			this.calculateOrderCost(orderBean);

			SupplierBean supplierBean = new SupplierBean();
			ItemBean itemBean = new ItemBean();
			model.addAttribute("orderBean", orderBean);
			model.addAttribute("supplierBean", supplierBean);
			model.addAttribute("itemBean", itemBean);

			logger.info("The selected purchase order has been retrieved successfully");
		} catch (Exception e) {
			logger.error("There is an error while retrieving purchase order for updation", e);
			return "error";
		}
		return "order/edit_order";
	}

	@PostMapping(value = "/edit_order", params = { "addOrderItem" })
	public String addRowEdit(@ModelAttribute OrderBean orderBean, Model model, final BindingResult bindingResult) {
		orderBean.getOrderItems().add(new OrderItemBean());

		SupplierBean supplierBean = new SupplierBean();
		ItemBean itemBean = new ItemBean();

		this.calculateOrderCost(orderBean);

		model.addAttribute("orderBean", orderBean);
		model.addAttribute("supplierBean", supplierBean);
		model.addAttribute("itemBean", itemBean);
		logger.info("A new purchase order item has been added to your purchase order");

		return "order/edit_order";
	}

	@PostMapping(value = "/edit_order", params = { "removeOrderItem" })
	public String removeRowEdit(@ModelAttribute OrderBean orderBean, Model model, final BindingResult bindingResult,
			final HttpServletRequest req) {
		final Integer rowId = Integer.valueOf(req.getParameter("id"));
		OrderItemBean orderItemBean = orderBean.getOrderItems().get(rowId.intValue());
		OrderItem orderItem = new OrderItem();

		this.updateOrderItem(orderItemBean, orderItem, orderBean.getOrderId());

		orderService.deleteOrderItem(orderItem);

		orderBean.getOrderItems().remove(rowId.intValue());
		SupplierBean supplierBean = new SupplierBean();
		ItemBean itemBean = new ItemBean();

		this.calculateOrderCost(orderBean);

		model.addAttribute("orderBean", orderBean);
		model.addAttribute("supplierBean", supplierBean);
		model.addAttribute("itemBean", itemBean);

		logger.info("The selected purchase order item has been deleted now");
		return "order/edit_order";
	}

	@PostMapping(value = "/edit_order", params = { "saveOrder" })
	public String saveOrderAfterEdit(@ModelAttribute @Valid OrderBean orderBean, BindingResult bindingResult,
			@ModelAttribute SupplierBean supplierBean, @ModelAttribute ItemBean itemBean, Model model,
			HttpSession session, Locale locale, Authentication authentication) {
		if (bindingResult.hasErrors())
			return "order/edit_order";
		try {

			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			Order order = new Order();
			this.createOrderDomain(order, orderBean, userDetails);

			order = orderService.createOrder(order);
			logger.info("The order details has been saved successfully after editing");

			this.calculateOrderCost(orderBean);
			model.addAttribute("orderBean", orderBean);

			model.addAttribute("success", messageSource.getMessage("commerce.screen.order.edit.success", null, locale));

		} catch (Exception e) {
			logger.error("There is an error while updating purchase order", e);
			return "error";
		}
		return "order/edit_order";
	}

	@GetMapping("/approve_order")
	public String approveOrder(Model model, HttpSession session, final HttpServletRequest req) {
		try {
			BigInteger orderId = new BigInteger(req.getParameter("orderId"));

			orderService.approveOrder(orderId);

			logger.info("The selected purchase order has been deleted successfully");
		} catch (Exception e) {
			logger.error("There is an error while deleteing purchase order", e);
			return "error";
		}
		return "order/manage_order";
	}

	@GetMapping("/delete_order")
	public String deleteOrder(Model model, HttpSession session, final HttpServletRequest req) {
		try {
			BigInteger orderId = new BigInteger(req.getParameter("orderId"));

			orderService.deleteOrder(orderId);

			logger.info("The selected purchase order has been deleted successfully");
		} catch (Exception e) {
			logger.error("There is an error while deleteing purchase order", e);
			return "error";
		}
		return "order/manage_order";
	}

	@PostMapping(value = "/bulk_order_action", params = { "saveOrders" })
	public String saveOrders(@ModelAttribute OrderBeanDTO orders, Model model, HttpSession session, Locale locale) {
		try {

			List<OrderBean> orderBeans = orders.getOrders();

			List<String> orderIds = orders.getOrderIds();
			Map<BigInteger, Integer> idIndex = new HashMap<BigInteger, Integer>(orderIds.size());

			this.getSelectedIds(orderIds, idIndex);

			List<OrderBean> finalOrderBeans = new ArrayList<OrderBean>(idIndex.size());

			Set<BigInteger> ids = idIndex.keySet();
			Integer index = null;
			OrderBean tmpOrderBean = null;
			for (BigInteger id : ids) {
				index = idIndex.get(id);
				tmpOrderBean = orderBeans.get(index);
				tmpOrderBean.setOrderId(id);
				finalOrderBeans.add(tmpOrderBean);
			}
			logger.info("The modified list of purchase orders has been updated in beans");

			List<Order> orderList = new ArrayList<Order>(finalOrderBeans.size());
			Order order = null;
			for (OrderBean orderBean : finalOrderBeans) {
				order = new Order();
				this.createBulkOrderDomain(order, orderBean, null);
				orderList.add(order);
			}
			logger.info("The purchase order details has been updated in Domain objects");

			orderService.updateOrders(orderList);
			model.addAttribute("success", messageSource.getMessage("commerce.screen.order.edit.success", null, locale));

			logger.info("The bulk update operation for purchase order is completed.");
		} catch (Exception e) {
			logger.error("There is an error while updating bulk purchase order basic details", e);
			return "error";
		}
		return "order/manage_order";
	}

	@PostMapping(value = "/bulk_order_action", params = { "approveOrders" })
	public String approveOrders(@ModelAttribute OrderBeanDTO orders, Model model, HttpSession session, Locale locale) {
		try {

			List<OrderBean> orderBeans = orders.getOrders();

			List<String> orderIds = orders.getOrderIds();
			Map<BigInteger, Integer> idIndex = new HashMap<BigInteger, Integer>(orderIds.size());

			this.getSelectedIds(orderIds, idIndex);

			List<OrderBean> finalOrderBeans = new ArrayList<OrderBean>(idIndex.size());

			Set<BigInteger> ids = idIndex.keySet();
			Integer index = null;
			OrderBean tmpOrderBean = null;
			for (BigInteger id : ids) {
				index = idIndex.get(id);
				tmpOrderBean = orderBeans.get(index);
				tmpOrderBean.setOrderId(id);
				finalOrderBeans.add(tmpOrderBean);
			}
			logger.info("The selected list of purchase orders has been marked approved in beans");

			List<Order> orderList = new ArrayList<Order>(finalOrderBeans.size());
			Order order = null;
			for (OrderBean orderBean : finalOrderBeans) {
				order = new Order();
				this.createApprovedOrderDomain(order, orderBean, null);
				orderList.add(order);
			}
			logger.info("The purchase order details has been updated in Domain objects");

			orderService.approveOrders(orderList);
			model.addAttribute("success", messageSource.getMessage("commerce.screen.order.edit.success", null, locale));

			logger.info("The bulk update operation for purchase order is completed.");
		} catch (Exception e) {
			logger.error("There is an error while updating bulk purchase order basic details", e);
			return "error";
		}
		return "order/manage_order";
	}

	@PostMapping(value = "/bulk_order_action", params = { "deleteOrders" })
	public String deleteOrders(@ModelAttribute OrderBeanDTO orders, Model model, HttpSession session, Locale locale) {
		try {
			List<String> orderIds = orders.getOrderIds();

			Map<BigInteger, Integer> idIndexMap = new HashMap<BigInteger, Integer>(orderIds.size());

			this.getSelectedIds(orderIds, idIndexMap);

			Set<BigInteger> ids = idIndexMap.keySet();

			orderService.deleteOrders(ids);
			model.addAttribute("success",
					messageSource.getMessage("commerce.screen.order.delete.success", null, locale));

			logger.info("The bulk delete operation for purchase order is completed.");
		} catch (Exception e) {
			logger.error("There is an error while deleting bulk purchase orders", e);
			return "error";
		}
		return "order/manage_order";
	}

	private void getSelectedIds(List<String> orderIds, Map<BigInteger, Integer> idIndex) {
		String splittedData[] = null;
		for (String id : orderIds) {
			splittedData = id.split("_");
			idIndex.put(new BigInteger(splittedData[0]), new Integer(splittedData[1]));
		}

		logger.info("The order ids and list index data has been seperated");
	}

	private void calculateOrderCost(OrderBean orderBean) {
		List<OrderItemBean> orderItemList = orderBean.getOrderItems();
		BigDecimal totalCost = new BigDecimal("0.0");
		for (OrderItemBean orderItem : orderItemList) {
			totalCost = totalCost.add(orderItem.getTotalCost());
		}
		orderBean.setEstimatedCost(totalCost);
		logger.info("The total cost based on all items cost has been calculated");
	}

	@GetMapping("/receive_order")
	public String retrieveOrderForReceival(Model model, HttpSession session, final HttpServletRequest req) {
		try {
			BigInteger orderId = new BigInteger(req.getParameter("orderId"));

			Order order = orderService.searchOrder(orderId);

			OrderBean orderBean = new OrderBean();
			this.updateOrderItemsBean(orderBean, order);

			this.calculateOrderCost(orderBean);

			SupplierBean supplierBean = new SupplierBean();
			ItemBean itemBean = new ItemBean();
			model.addAttribute("orderBean", orderBean);

			logger.info("The selected purchase order has been retrieved successfully");
		} catch (Exception e) {
			logger.error("There is an error while retrieving purchase order for receival", e);
			return "error";
		}
		return "order/receive_order";
	}

	@PostMapping(value = "/receive_order", params = { "saveOrder" })
	public String saveOrderForReceival(@ModelAttribute @Valid OrderBean orderBean, BindingResult bindingResult,
			Model model, HttpSession session, Locale locale, Authentication authentication) {
		/*
		 * if (bindingResult.hasErrors()) return "order/receive_order";
		 */
		try {

			UserDetails userDetails = (UserDetails) authentication.getPrincipal();

			Order order = new Order();

			this.createApprovedOrderDomain(order, orderBean, userDetails);

			orderService.createOrder(order);

			model.addAttribute("orderBean", orderBean);

			logger.info("The purchase order details has been saved successfully");
		} catch (Exception e) {
			logger.error("There is an error while saving order details during receipt", e);
			return "error";
		}
		return "order/receive_order";
	}

	@PostMapping(value = "/receive_order", params = { "receiveOrder" })
	public String receiveOrder(@ModelAttribute @Valid OrderBean orderBean, BindingResult bindingResult, Model model,
			HttpSession session, Locale locale, Authentication authentication) {
		/*
		 * if (bindingResult.hasErrors()) return "order/receive_order";
		 */
		try {

			UserDetails userDetails = (UserDetails) authentication.getPrincipal();

			Order order = new Order();

			this.updateOrderStatusToReceive(order, orderBean, userDetails);

			orderService.createOrder(order);

			model.addAttribute("orderBean", orderBean);

			logger.info("The purchase order details has been saved successfully");
		} catch (Exception e) {
			logger.error("There is an error while saving order details during receipt", e);
			return "error";
		}
		return "order/receive_order";
	}

	@PostMapping(value = "/receive_order", params = { "receiveAllOrder" })
	public String receiveAllOrder(@ModelAttribute @Valid OrderBean orderBean, BindingResult bindingResult, Model model,
			HttpSession session, Locale locale, Authentication authentication) {
		/*
		 * if (bindingResult.hasErrors()) return "order/receive_order";
		 */
		try {

			UserDetails userDetails = (UserDetails) authentication.getPrincipal();

			Order order = new Order();

			this.receiveAllOrderItems(order, orderBean, userDetails);

			orderService.createOrder(order);

			model.addAttribute("orderBean", orderBean);

			logger.info("The purchase order details has been saved successfully");
		} catch (Exception e) {
			logger.error("There is an error while saving order details during receipt", e);
			return "error";
		}
		return "order/receive_order";
	}

	private void receiveAllOrderItems(Order order, OrderBean orderBean, UserDetails userDetails) {
		order.setOrderId(orderBean.getOrderId());
		
		Supplier supplier=new Supplier();
		supplier.setSupplierId(orderBean.getSupplierId());
		order.setSupplier(supplier);
		
		if (userDetails != null)
			order.setCreatedBy(userDetails.getUsername());
		order.setCreatedDate(LocalDateTime.now());
		order.setStatus("R");
		order.setEstimatedCost(orderBean.getEstimatedCost());

		/**
		 * Update Order Item details
		 */
		List<OrderItemBean> orderItemBeanList = orderBean.getOrderItems();
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		OrderItem orderItem = null;
		OrderItemId orderItemId = null;

		BigDecimal totalAmount = new BigDecimal("0.00");

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

			orderItem.setDelieveredQty(orderItemBean.getOrderedQty());
			orderItem.setDelieveredDate(LocalDateTime.now());

			orderItem.setActualUnitCost(orderItemBean.getCostAmount());
			orderItem.setCostAmount(orderItemBean.getTotalCost());
			orderItem.setDiscountAmount(orderItemBean.getDiscountAmount());
			orderItem.setTaxAmount(orderItemBean.getTaxAmount());
			orderItem.setActualTotalAmount(orderItemBean.getTotalCost());

			totalAmount = totalAmount.add(orderItemBean.getTotalCost());

			orderItems.add(orderItem);

		}

		order.setOrderItems(orderItems);

		order.setDiscountAmount(orderBean.getDiscountAmount());
		order.setTaxAmount(orderBean.getTaxAmount());
		order.setPaidAmount(totalAmount);
		order.setTotalAmount(totalAmount);

		logger.info("The purchase order details has been saved in domain");

	}

	private void updateOrderStatusToReceive(Order order, OrderBean orderBean, UserDetails userDetails) {
		this.updateOrderDomain(order, orderBean, userDetails, "R");
	}

	private void createApprovedOrderDomain(Order order, OrderBean orderBean, UserDetails userDetails) {
		this.updateOrderDomain(order, orderBean, userDetails, "A");
	}

	private void createOrderDomain(Order order, OrderBean orderBean, UserDetails userDetails) {
		this.updateOrderDomain(order, orderBean, userDetails, "C");

	}

	private void createBulkOrderDomain(Order order, OrderBean orderBean, UserDetails userDetails) {
		this.updateOrderDomain(order, orderBean, userDetails, "C");

	}

	@GetMapping(value = "/print_orders")
	public String printOrders(Model model, HttpSession session, Locale locale, Authentication authentication) {
		try {

			UserDetails userDetails = (UserDetails) authentication.getPrincipal();

			OrderDTO ordersDTO = orderService.findAll();

			OrderBeanDTO orders = new OrderBeanDTO();

			this.updateOrderBeanList(orders, ordersDTO);

			InputStream orderReportStream = getClass().getResourceAsStream("/reports/order/orders.jrxml");

			JasperReport jasperReport = JasperCompileManager.compileReport(orderReportStream);

			JRBeanCollectionDataSource orderDS = new JRBeanCollectionDataSource(orders.getOrders());

			InputStream orderReportStreamChild = getClass().getResourceAsStream("/reports/order/order_items.jrxml");
			JasperReport jasperReportChild = JasperCompileManager.compileReport(orderReportStreamChild);

			Map<String, Object> paramMap = new HashMap<String, Object>();

			paramMap.put("SUBREPORT_DIR", jasperReportChild);

			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, paramMap, orderDS);

			// Export PDF report
			JRPdfExporter exporter = new JRPdfExporter();

			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("employeeReport.pdf"));

			SimplePdfReportConfiguration reportConfig = new SimplePdfReportConfiguration();
			reportConfig.setSizePageToContent(true);
			reportConfig.setForceLineBreakPolicy(false);

			SimplePdfExporterConfiguration exportConfig = new SimplePdfExporterConfiguration();
			exportConfig.setMetadataAuthor("admin");
			exportConfig.setEncrypted(true);
			exportConfig.setAllowedPermissionsHint("PRINTING");

			exporter.setConfiguration(reportConfig);
			exporter.setConfiguration(exportConfig);

			exporter.exportReport();

			logger.info("The purchase order details has been saved successfully");
		} catch (Exception e) {
			logger.error("There is an error while saving order details during receipt", e);
			return "error";
		}
		return "order/manage_order";
	}

	private void updateOrderBeanList(OrderBeanDTO orders, OrderDTO ordersDTO) {

		OrderBean orderBean = null;
		List<OrderBean> orderBeanList = new ArrayList<OrderBean>();
		for (Order order : ordersDTO.getOrders()) {
			orderBean = new OrderBean();
			this.updateOrderItemsBean(orderBean, order);
			orderBeanList.add(orderBean);
		}

		orders.setOrders(orderBeanList);

		logger.info("The Order details has been updated in Order Beans");
	}

	@GetMapping("/print_order")
	public void printOrder(Model model, HttpSession session, final HttpServletRequest req,
			HttpServletResponse response) {
		// set header as pdf
		response.setContentType("application/pdf");

		try {
			// set input and output stream
			ServletOutputStream servletOutputStream = response.getOutputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			BigInteger orderId = new BigInteger(req.getParameter("orderId"));

			Order order = orderService.searchOrder(orderId);

			List<OrderBean> orderCollection = new ArrayList<OrderBean>();
			OrderBean orderBean = new OrderBean();
			this.updateOrderItemsBean(orderBean, order);
			List<AddressBean> primaryAddress=this.getSupplierAddress(orderBean.getSupplier().getAddresses());

			orderCollection.add(orderBean);

			InputStream orderReportStream = getClass().getResourceAsStream("/reports/order/purchase_order_updated.jrxml");

			JasperReport jasperReport = JasperCompileManager.compileReport(orderReportStream);

			JRBeanCollectionDataSource orderDS = new JRBeanCollectionDataSource(orderCollection);

			InputStream orderReportStreamChild = getClass()
					.getResourceAsStream("/reports/order/purchase_order_item.jrxml");
			JasperReport jasperReportChild = JasperCompileManager.compileReport(orderReportStreamChild);

			InputStream supplierReportStream = getClass()
					.getResourceAsStream("/reports/supplier/supplier.jrxml");
			JasperReport jasperSupplierReport= JasperCompileManager.compileReport(supplierReportStream);			
			
			
			InputStream supplierAddressReportStream = getClass()
					.getResourceAsStream("/reports/address/address.jrxml");
			JasperReport jasperSupplierAddressReport= JasperCompileManager.compileReport(supplierAddressReportStream);
			
			InputStream deliveryAddressReportStream = getClass()
					.getResourceAsStream("/reports/address/address.jrxml");
			JasperReport jasperDelieveryAddressReport= JasperCompileManager.compileReport(deliveryAddressReportStream);
			
			
			List<SupplierBean> supplierList=new ArrayList<SupplierBean>();
			
			supplierList.add(orderBean.getSupplier());
			
			Map<String, Object> paramMap = new HashMap<String, Object>();

			paramMap.put("ORDER_ITEM_REPORT", jasperReportChild);
			paramMap.put("SUPPLIER_REPORT", jasperSupplierReport);
			paramMap.put("SUPPLIER_ADDRESS_REPORT", jasperSupplierAddressReport);
			paramMap.put("DELIVERY_ADDRESS_REPORT", jasperDelieveryAddressReport);
			
			paramMap.put("SUPPLIER_DATA", supplierList);
			paramMap.put("SUPPLIER_ADDRESS_DATA", primaryAddress);
			paramMap.put("DELIVERY_ADDRESS_DATA", primaryAddress);
			
			

			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, paramMap, orderDS);

			// export to pdf
			JasperExportManager.exportReportToPdfStream(jasperPrint, baos);

			response.setContentLength(baos.size());
			baos.writeTo(servletOutputStream);

			logger.info("The selected purchase order has been retrieved successfully");
		} catch (Exception e) {
			logger.error("There is an error while retrieving purchase order for updation", e);
		}
	}
	
	public List<AddressBean>  getSupplierAddress(List<AddressBean> addresses) {
		List<AddressBean> primaryAddress=new ArrayList<AddressBean>();
		for(AddressBean address:addresses) {
			if(address.getPrimary().equals("Y")) {
				primaryAddress.add(address);
				break;
			}
		}
		return primaryAddress;
		
	}

}
