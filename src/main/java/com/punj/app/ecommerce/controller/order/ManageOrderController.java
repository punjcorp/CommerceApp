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

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javamoney.moneta.Money;
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

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.domains.order.Order;
import com.punj.app.ecommerce.domains.order.OrderDTO;
import com.punj.app.ecommerce.domains.order.OrderItem;
import com.punj.app.ecommerce.domains.order.ids.OrderItemId;
import com.punj.app.ecommerce.domains.supplier.Supplier;
import com.punj.app.ecommerce.domains.user.Address;
import com.punj.app.ecommerce.models.common.AddressBean;
import com.punj.app.ecommerce.models.item.ItemBean;
import com.punj.app.ecommerce.models.order.OrderBean;
import com.punj.app.ecommerce.models.order.OrderBeansDTO;
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

	/**
	 * This method is used to set bean details in domain object
	 * 
	 * @param order
	 * @param orderBean
	 * @param userDetails
	 */
	private void updateOrderDomain(Order order, OrderBean orderBean, UserDetails userDetails, String status) {

		order.setOrderId(orderBean.getOrderId());

		Supplier supplier = new Supplier();
		supplier.setSupplierId(orderBean.getSupplierId());
		order.setSupplier(supplier);

		if (userDetails != null)
			order.setCreatedBy(userDetails.getUsername());
		order.setCreatedDate(LocalDateTime.now());
		order.setStatus(status);

		order.setLocationId(orderBean.getLocationId());

		order.setEstimatedCost(orderBean.getEstimatedCost());
		order.setDiscountAmount(orderBean.getDiscountAmount());
		order.setTaxAmount(orderBean.getTaxAmount());
		order.setPaidAmount(orderBean.getPaidAmount());
		order.setTotalAmount(orderBean.getTotalAmount());

		/**
		 * Update Order Item details
		 */
		List<OrderItemBean> orderItemBeanList = orderBean.getOrderItems();
		List<OrderItem> orderItems = new ArrayList<>();
		OrderItem orderItem = null;
		OrderItemId orderItemId = null;

		for (OrderItemBean orderItemBean : orderItemBeanList) {
			orderItem = new OrderItem();
			orderItemId = new OrderItemId();

			// Fucking always remember this in relationships
			orderItemId.setOrder(order);

			orderItemId.setItemId(orderItemBean.getItemId());
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

	@GetMapping(value = ViewPathConstants.MANAGE_ORDER_URL)
	public String manageOrder(@RequestParam(MVCConstants.ORDER_ID_PARAM) Optional<String> orderId, Model model, HttpSession session, Locale locale) {
		logger.info("The manage method for purchase orders has been called");
		try {
			String searchText = null;
			if (orderId.isPresent()) {
				searchText = orderId.get();
			}

			Pager pager = new Pager();
			pager.setCurrentPageNo(1);

			OrderBean orderBean = new OrderBean();
			model.addAttribute(MVCConstants.ORDER_BEAN, orderBean);
			OrderDTO ordersDTO = null;

			if (searchText != null) {
				ordersDTO = orderService.searchOrder(searchText, pager);
			} else {
				ordersDTO = orderService.searchAllOrders(pager);
			}

			OrderBeansDTO orders = new OrderBeansDTO();
			this.updateOrderBeansDTO(orders, ordersDTO, locale);

			model.addAttribute(MVCConstants.ORDERS_BEAN, orders);

			logger.info("The purchase order details bean has been retrieved");
		} catch (Exception e) {
			logger.error("An unknown error has occurred while retrieving purchase orders.", e);
			return ViewPathConstants.ERROR_PAGE;
		}

		return ViewPathConstants.MANAGE_ORDER_PAGE;

	}

	private void updateOrderBeansDTO(OrderBeansDTO orders, OrderDTO ordersDTO, Locale locale) {

		List<Order> orderList = ordersDTO.getOrders();
		List<OrderBean> orderBeanList = new ArrayList<>();
		OrderBean orderBean = null;

		for (Order order : orderList) {
			orderBean = new OrderBean();
			this.updateBean(orderBean, order, locale);
			orderBeanList.add(orderBean);
		}

		orders.setOrders(orderBeanList);
		logger.info("The purchase order details has been added to the DTO object");
	}

	private void updateBean(OrderBean orderBean, Order order, Locale locale) {
		orderBean.setOrderId(order.getOrderId());
		orderBean.setSupplierId(order.getSupplier().getSupplierId());

		SupplierBean supplierBean = new SupplierBean();
		this.updateSupplierBean(supplierBean, order.getSupplier());
		orderBean.setSupplier(supplierBean);

		orderBean.setLocationId(order.getLocationId());

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
	 * This method is used to set the details of retrieved suppliers to Bean object so the details can be displayed on the screen
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
		List<AddressBean> supplierAddresses = new ArrayList<>();

		AddressBean addressBean = null;
		if (supplierAddressList != null && !supplierAddressList.isEmpty()) {
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

	private void updateOrderItemsBean(OrderBean orderBean, Order order, Locale locale) {
		this.updateBean(orderBean, order, locale);

		List<OrderItem> orderItems = order.getOrderItems();

		List<OrderItemBean> orderItemBeanList = new ArrayList<>();
		OrderItemBean orderItemBean = null;

		CurrencyUnit currenyUnit = Monetary.getCurrency(locale);
		MonetaryAmount monetaryAmt = null;

		for (OrderItem orderItem : orderItems) {
			orderItemBean = new OrderItemBean();
			orderItemBean.setOrderId(order.getOrderId());
			orderItemBean.setItemId(orderItem.getOrderItemId().getItemId());
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
		orderItemId.setItemId(orderItemBean.getItemId());
		Order order = new Order();
		order.setOrderId(orderId);
		orderItemId.setOrder(order);

		orderItem.setOrderItemId(orderItemId);

	}

	@PostMapping(ViewPathConstants.SEARCH_ORDER_URL)
	public String searchOrder(@ModelAttribute OrderBean orderBean, @RequestParam(MVCConstants.PAGE_PARAM) Optional<Integer> page, Model model,
			HttpSession session, Locale locale) {
		try {

			Pager pager = new Pager();
			if (!page.isPresent()) {
				pager.setCurrentPageNo(1);
			} else {
				pager.setCurrentPageNo(page.get());
			}

			OrderDTO ordersDTO = orderService.searchOrder(orderBean.getOrderId().toString(), pager);

			OrderBeansDTO orders = new OrderBeansDTO();
			this.updateOrderBeansDTO(orders, ordersDTO, locale);

			Pager tmpPager = ordersDTO.getPager();
			pager = new Pager(tmpPager.getResultSize(), tmpPager.getPageSize(), tmpPager.getCurrentPageNo(), tmpPager.getMaxDisplayPage(),
					ViewPathConstants.SEARCH_ORDER_URL);

			model.addAttribute(MVCConstants.ORDERS_BEAN, orders);
			model.addAttribute(MVCConstants.ORDER_BEAN, orderBean);
			model.addAttribute(MVCConstants.PAGER, pager);
			model.addAttribute(MVCConstants.SUCCESS,
					messageSource.getMessage("commerce.screen.order.search.result", new Object[] { pager.getResultSize() }, locale));

			logger.info("The purchase order details has been retrieved successfully.");
		} catch (Exception e) {
			logger.error("There is an error while retrieving purchase orders", e);
			return ViewPathConstants.ERROR_PAGE;
		}
		return ViewPathConstants.MANAGE_ORDER_PAGE;
	}

	@GetMapping(ViewPathConstants.EDIT_ORDER_URL)
	public String editOrder(Model model, HttpSession session, final HttpServletRequest req, Locale locale) {
		try {
			BigInteger orderId = new BigInteger(req.getParameter(MVCConstants.ORDER_ID_PARAM));

			Order order = orderService.searchOrder(orderId);

			OrderBean orderBean = new OrderBean();
			this.updateOrderItemsBean(orderBean, order, locale);

			this.calculateOrderCost(orderBean, locale);

			SupplierBean supplierBean = new SupplierBean();
			ItemBean itemBean = new ItemBean();
			model.addAttribute(MVCConstants.ORDER_BEAN, orderBean);
			model.addAttribute(MVCConstants.SUPPLIER_BEAN, supplierBean);
			model.addAttribute(MVCConstants.ITEM_BEAN, itemBean);

			logger.info("The selected purchase order has been updated successfully");
		} catch (Exception e) {
			logger.error("There is an error while retrieving purchase order for updation", e);
			return ViewPathConstants.ERROR_PAGE;
		}
		return ViewPathConstants.EDIT_ORDER_PAGE;
	}

	@PostMapping(value = ViewPathConstants.EDIT_ORDER_URL, params = { MVCConstants.ADD_ORDER_ITEM_PARAM })
	public String addRowEdit(@ModelAttribute OrderBean orderBean, Model model, final BindingResult bindingResult, Locale locale) {
		orderBean.getOrderItems().add(new OrderItemBean());

		SupplierBean supplierBean = new SupplierBean();
		ItemBean itemBean = new ItemBean();

		this.calculateOrderCost(orderBean, locale);

		model.addAttribute(MVCConstants.ORDER_BEAN, orderBean);
		model.addAttribute(MVCConstants.SUPPLIER_BEAN, supplierBean);
		model.addAttribute(MVCConstants.ITEM_BEAN, itemBean);
		logger.info("A new purchase order item has been added to your purchase order");

		return ViewPathConstants.EDIT_ORDER_PAGE;
	}

	@PostMapping(value = ViewPathConstants.EDIT_ORDER_URL, params = { MVCConstants.REMOVE_ORDER_ITEM_PARAM })
	public String removeRowEdit(@ModelAttribute OrderBean orderBean, Model model, final BindingResult bindingResult, Locale locale,
			final HttpServletRequest req) {
		final Integer rowId = Integer.valueOf(req.getParameter(MVCConstants.ID_PARAM));
		OrderItemBean orderItemBean = orderBean.getOrderItems().get(rowId.intValue());
		OrderItem orderItem = new OrderItem();

		this.updateOrderItem(orderItemBean, orderItem, orderBean.getOrderId());

		orderService.deleteOrderItem(orderItem);

		orderBean.getOrderItems().remove(rowId.intValue());
		SupplierBean supplierBean = new SupplierBean();
		ItemBean itemBean = new ItemBean();

		this.calculateOrderCost(orderBean, locale);

		model.addAttribute(MVCConstants.ORDER_BEAN, orderBean);
		model.addAttribute(MVCConstants.SUPPLIER_BEAN, supplierBean);
		model.addAttribute(MVCConstants.ITEM_BEAN, itemBean);

		logger.info("The selected purchase order item has been deleted now");
		return ViewPathConstants.EDIT_ORDER_PAGE;
	}

	@PostMapping(value = ViewPathConstants.EDIT_ORDER_URL, params = { MVCConstants.SAVE_ORDER_PARAM })
	public String saveOrderAfterEdit(@ModelAttribute @Valid OrderBean orderBean, BindingResult bindingResult, @ModelAttribute SupplierBean supplierBean,
			@ModelAttribute ItemBean itemBean, Model model, Locale locale, Authentication authentication) {
		if (bindingResult.hasErrors())
			return ViewPathConstants.EDIT_ORDER_PAGE;
		try {

			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			Order order = new Order();
			this.createOrderDomain(order, orderBean, userDetails);

			orderService.createOrder(order);
			logger.info("The order details has been saved successfully after editing");

			this.calculateOrderCost(orderBean, locale);
			model.addAttribute(MVCConstants.ORDER_BEAN, orderBean);

			model.addAttribute(MVCConstants.SUCCESS, messageSource.getMessage("commerce.screen.order.edit.success", null, locale));

		} catch (Exception e) {
			logger.error("There is an error while updating purchase order", e);
			return ViewPathConstants.ERROR_PAGE;
		}
		return ViewPathConstants.EDIT_ORDER_PAGE;
	}

	@GetMapping(ViewPathConstants.APPROVE_ORDER_URL)
	public String approveOrder(Model model, HttpSession session, final HttpServletRequest req) {
		try {
			BigInteger orderId = new BigInteger(req.getParameter(MVCConstants.ORDER_ID_PARAM));

			orderService.approveOrder(orderId);

			logger.info("The selected purchase order has been approved successfully");
		} catch (Exception e) {
			logger.error("There is an error while deleteing purchase order", e);
			return ViewPathConstants.ERROR_PAGE;
		}
		return ViewPathConstants.MANAGE_ORDER_PAGE;
	}

	@GetMapping(ViewPathConstants.DELETE_ORDER_URL)
	public String deleteOrder(Model model, HttpSession session, final HttpServletRequest req) {
		try {
			BigInteger orderId = new BigInteger(req.getParameter(MVCConstants.ORDER_ID_PARAM));

			orderService.deleteOrder(orderId);

			logger.info("The selected purchase order has been deleted successfully");
		} catch (Exception e) {
			logger.error("There is an error while deleteing purchase order", e);
			return ViewPathConstants.ERROR_PAGE;
		}
		return ViewPathConstants.MANAGE_ORDER_PAGE;
	}

	@PostMapping(value = ViewPathConstants.BULK_ORDER_URL, params = { MVCConstants.SAVE_ORDERS_PARAM })
	public String saveOrders(@ModelAttribute OrderBeansDTO orders, Model model, HttpSession session, Locale locale) {
		try {

			List<OrderBean> orderBeans = orders.getOrders();

			List<String> orderIds = orders.getOrderIds();
			Map<BigInteger, Integer> idIndex = new HashMap<>(orderIds.size());

			this.getSelectedIds(orderIds, idIndex);

			List<OrderBean> finalOrderBeans = new ArrayList<>(idIndex.size());

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

			List<Order> orderList = new ArrayList<>(finalOrderBeans.size());
			Order order = null;
			for (OrderBean orderBean : finalOrderBeans) {
				order = new Order();
				this.createBulkOrderDomain(order, orderBean, null);
				orderList.add(order);
			}
			logger.info("The purchase order details has been updated in Domain objects");

			orderService.updateOrders(orderList);
			model.addAttribute(MVCConstants.SUCCESS, messageSource.getMessage("commerce.screen.order.edit.success", null, locale));

			logger.info("The bulk update operation for purchase order is completed.");
		} catch (Exception e) {
			logger.error("There is an error while updating bulk purchase order basic details", e);
			return ViewPathConstants.ERROR_PAGE;
		}
		return ViewPathConstants.MANAGE_ORDER_PAGE;
	}

	@PostMapping(value = ViewPathConstants.BULK_ORDER_URL, params = { MVCConstants.APPROVE_ORDERS_PARAM })
	public String approveOrders(@ModelAttribute OrderBeansDTO orders, Model model, HttpSession session, Locale locale, Authentication authentication) {
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();

			List<OrderBean> orderBeans = orders.getOrders();

			List<String> orderIds = orders.getOrderIds();
			Map<BigInteger, Integer> idIndex = new HashMap<>(orderIds.size());

			this.getSelectedIds(orderIds, idIndex);

			List<OrderBean> finalOrderBeans = new ArrayList<>(idIndex.size());

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

			List<Order> orderList = new ArrayList<>(finalOrderBeans.size());
			Order order = null;
			for (OrderBean orderBean : finalOrderBeans) {
				order = new Order();
				this.createApprovedOrderDomain(order, orderBean, null);
				orderList.add(order);
			}
			logger.info("The purchase order details has been updated in Domain objects");

			orderService.approveOrders(orderList, userDetails.getUsername());
			model.addAttribute(MVCConstants.SUCCESS, messageSource.getMessage("commerce.screen.order.approve.success", null, locale));

			logger.info("The bulk update operation for purchase order is completed.");
		} catch (Exception e) {
			logger.error("There is an error while updating bulk purchase order basic details", e);
			return ViewPathConstants.ERROR_PAGE;
		}
		return ViewPathConstants.MANAGE_ORDER_PAGE;
	}

	@PostMapping(value = ViewPathConstants.BULK_ORDER_URL, params = { MVCConstants.DELETE_ORDERS_PARAM })
	public String deleteOrders(@ModelAttribute OrderBeansDTO orders, Model model, HttpSession session, Locale locale) {
		try {
			List<String> orderIds = orders.getOrderIds();

			Map<BigInteger, Integer> idIndexMap = new HashMap<>(orderIds.size());

			this.getSelectedIds(orderIds, idIndexMap);

			Set<BigInteger> ids = idIndexMap.keySet();

			orderService.deleteOrders(ids);
			model.addAttribute(MVCConstants.SUCCESS, messageSource.getMessage("commerce.screen.order.delete.success", null, locale));

			logger.info("The bulk delete operation for purchase order is completed.");
		} catch (Exception e) {
			logger.error("There is an error while deleting bulk purchase orders", e);
			return ViewPathConstants.ERROR_PAGE;
		}
		return ViewPathConstants.MANAGE_ORDER_PAGE;
	}

	private void getSelectedIds(List<String> orderIds, Map<BigInteger, Integer> idIndex) {
		String[] splittedData = null;
		for (String id : orderIds) {
			splittedData = id.split("_");
			idIndex.put(new BigInteger(splittedData[0]), new Integer(splittedData[1]));
		}

		logger.info("The order ids and list index data has been seperated");
	}

	private void calculateOrderCost(OrderBean orderBean, Locale locale) {
		List<OrderItemBean> orderItemList = orderBean.getOrderItems();
		BigDecimal totalCost = BigDecimal.ZERO;

		for (OrderItemBean orderItem : orderItemList) {
			totalCost = totalCost.add(orderItem.getTotalCost());
		}
		orderBean.setEstimatedCost(totalCost);
		logger.info("The total cost based on all items cost has been calculated");
	}

	@GetMapping(ViewPathConstants.RECEIVE_ORDER_URL)
	public String retrieveOrderForReceival(Model model, HttpSession session, final HttpServletRequest req, Locale locale) {
		try {
			BigInteger orderId = new BigInteger(req.getParameter(MVCConstants.ORDER_ID_PARAM));

			Order order = orderService.searchOrder(orderId);

			OrderBean orderBean = new OrderBean();
			this.updateOrderItemsBean(orderBean, order, locale);

			this.calculateOrderCost(orderBean, locale);

			model.addAttribute(MVCConstants.ORDER_BEAN, orderBean);

			logger.info("The selected purchase order has been retrieved for receival successfully");
		} catch (Exception e) {
			logger.error("There is an error while retrieving purchase order for receival", e);
			return ViewPathConstants.ERROR_PAGE;
		}
		return ViewPathConstants.RECEIVE_ORDER_PAGE;
	}

	@PostMapping(value = ViewPathConstants.RECEIVE_ORDER_URL, params = { MVCConstants.SAVE_ORDER_PARAM })
	public String saveOrderForReceival(@ModelAttribute @Valid OrderBean orderBean, BindingResult bindingResult, Model model, HttpSession session, Locale locale,
			Authentication authentication) {
		try {

			UserDetails userDetails = (UserDetails) authentication.getPrincipal();

			Order order = new Order();

			this.createApprovedOrderDomain(order, orderBean, userDetails);

			orderService.createOrder(order);

			model.addAttribute(MVCConstants.ORDER_BEAN, orderBean);
			model.addAttribute(MVCConstants.SUCCESS,
					messageSource.getMessage("commerce.screen.order.receive.save.success", new Object[] { order.getOrderId() }, locale));
			logger.info("The purchase order details has been saved after receiving successfully");
		} catch (Exception e) {
			logger.error("There is an error while saving order details during receive", e);
			return ViewPathConstants.ERROR_PAGE;
		}
		return ViewPathConstants.RECEIVE_ORDER_PAGE;
	}

	@PostMapping(value = ViewPathConstants.RECEIVE_ORDER_URL, params = { MVCConstants.RECEIVE_ORDER_PARAM })
	public String receiveOrder(@ModelAttribute @Valid OrderBean orderBean, BindingResult bindingResult, Model model, HttpSession session, Locale locale,
			Authentication authentication) {
		try {

			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			if (userDetails == null)
				return ViewPathConstants.ERROR_PAGE;
			Order order = new Order();

			this.updateOrderStatusToReceive(order, orderBean, userDetails);

			orderService.createOrder(order);
			orderService.receiveOrder(order.getOrderId(), userDetails.getUsername());

			model.addAttribute(MVCConstants.ORDER_BEAN, orderBean);
			model.addAttribute(MVCConstants.SUCCESS,
					messageSource.getMessage("commerce.screen.order.receive.receive.success", new Object[] { order.getOrderId() }, locale));
			logger.info("The purchase order has been received successfully");
		} catch (Exception e) {
			logger.error("There is an error while saving order details during receipt", e);
			return ViewPathConstants.ERROR_PAGE;
		}
		return ViewPathConstants.RECEIVE_ORDER_PAGE;
	}

	@PostMapping(value = ViewPathConstants.RECEIVE_ORDER_URL, params = { MVCConstants.RECEIVE_ALL_ORDERS_PARAM })
	public String receiveAllOrder(@ModelAttribute @Valid OrderBean orderBean, BindingResult bindingResult, Model model, HttpSession session, Locale locale,
			Authentication authentication) {
		try {

			UserDetails userDetails = (UserDetails) authentication.getPrincipal();

			Order order = new Order();

			this.receiveAllOrderItems(order, orderBean, userDetails);

			orderService.createOrder(order);

			model.addAttribute(MVCConstants.ORDER_BEAN, orderBean);

			logger.info("All the purchase order details has been received successfully");
			model.addAttribute(MVCConstants.SUCCESS,
					messageSource.getMessage("commerce.screen.order.receive.receive.all.success", new Object[] { order.getOrderId() }, locale));

		} catch (Exception e) {
			logger.error("There is an error while receiving bulk orders", e);
			return ViewPathConstants.ERROR_PAGE;
		}
		return ViewPathConstants.RECEIVE_ORDER_PAGE;
	}

	private void receiveAllOrderItems(Order order, OrderBean orderBean, UserDetails userDetails) {
		order.setOrderId(orderBean.getOrderId());

		Supplier supplier = new Supplier();
		supplier.setSupplierId(orderBean.getSupplierId());
		order.setSupplier(supplier);

		order.setLocationId(orderBean.getLocationId());

		if (userDetails != null)
			order.setCreatedBy(userDetails.getUsername());
		order.setCreatedDate(LocalDateTime.now());
		order.setStatus(MVCConstants.STATUS_RECEIVED);

		order.setEstimatedCost(orderBean.getEstimatedCost());

		/**
		 * Update Order Item details
		 */
		List<OrderItemBean> orderItemBeanList = orderBean.getOrderItems();
		List<OrderItem> orderItems = new ArrayList<>();
		OrderItem orderItem = null;
		OrderItemId orderItemId = null;

		BigDecimal totalAmount = new BigDecimal("0.00");

		for (OrderItemBean orderItemBean : orderItemBeanList) {
			orderItem = new OrderItem();
			orderItemId = new OrderItemId();

			// Fucking always remember this in relationships
			orderItemId.setOrder(order);

			orderItemId.setItemId(orderItemBean.getItemId());
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
		this.updateOrderDomain(order, orderBean, userDetails, MVCConstants.STATUS_RECEIVED);
	}

	private void createApprovedOrderDomain(Order order, OrderBean orderBean, UserDetails userDetails) {
		this.updateOrderDomain(order, orderBean, userDetails, MVCConstants.STATUS_APPROVED);
	}

	private void createOrderDomain(Order order, OrderBean orderBean, UserDetails userDetails) {
		this.updateOrderDomain(order, orderBean, userDetails, MVCConstants.STATUS_CREATED);

	}

	private void createBulkOrderDomain(Order order, OrderBean orderBean, UserDetails userDetails) {
		this.updateOrderDomain(order, orderBean, userDetails, MVCConstants.STATUS_CREATED);

	}

	@GetMapping(value = ViewPathConstants.PRINT_ORDERS_URL)
	public String printOrders(Model model, HttpSession session, Locale locale, Authentication authentication) {
		try {

			UserDetails userDetails = (UserDetails) authentication.getPrincipal();

			if (userDetails == null)
				return ViewPathConstants.ERROR_PAGE;

			OrderDTO ordersDTO = orderService.findAll();

			OrderBeansDTO orders = new OrderBeansDTO();

			this.updateOrderBeanList(orders, ordersDTO, locale);

			InputStream orderReportStream = getClass().getResourceAsStream(MVCConstants.ORDERS_REPORT);

			JasperReport jasperReport = JasperCompileManager.compileReport(orderReportStream);

			JRBeanCollectionDataSource orderDS = new JRBeanCollectionDataSource(orders.getOrders());

			InputStream orderReportStreamChild = getClass().getResourceAsStream(MVCConstants.ORDERS_ITEMS_REPORT);
			JasperReport jasperReportChild = JasperCompileManager.compileReport(orderReportStreamChild);

			Map<String, Object> paramMap = new HashMap<>();

			paramMap.put(MVCConstants.SUB_REPORT_DIR, jasperReportChild);

			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, paramMap, orderDS);

			// Export PDF report
			JRPdfExporter exporter = new JRPdfExporter();

			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(MVCConstants.ORDERS_REPORT_NAME));

			SimplePdfReportConfiguration reportConfig = new SimplePdfReportConfiguration();
			reportConfig.setSizePageToContent(true);
			reportConfig.setForceLineBreakPolicy(false);

			SimplePdfExporterConfiguration exportConfig = new SimplePdfExporterConfiguration();
			exportConfig.setMetadataAuthor(userDetails.getUsername());
			exportConfig.setEncrypted(true);
			exportConfig.setAllowedPermissionsHint(MVCConstants.REPORT_PERMISSION_PRINTING);

			exporter.setConfiguration(reportConfig);
			exporter.setConfiguration(exportConfig);

			exporter.exportReport();

			logger.info("The purchase order details list has been printed successfully");
		} catch (Exception e) {
			logger.error("There is an error while printing order details listing", e);
			return ViewPathConstants.ERROR_PAGE;
		}
		return ViewPathConstants.MANAGE_ORDER_PAGE;
	}

	private void updateOrderBeanList(OrderBeansDTO orders, OrderDTO ordersDTO, Locale locale) {

		OrderBean orderBean = null;
		List<OrderBean> orderBeanList = new ArrayList<>();
		for (Order order : ordersDTO.getOrders()) {
			orderBean = new OrderBean();
			this.updateOrderItemsBean(orderBean, order, locale);
			orderBeanList.add(orderBean);
		}

		orders.setOrders(orderBeanList);

		logger.info("The Order details has been updated in Order Beans");
	}

	@GetMapping(ViewPathConstants.PRINT_ORDER_URL)
	public void printOrder(Model model, HttpSession session, final HttpServletRequest req, HttpServletResponse response, Locale locale) {
		// set header as pdf
		response.setContentType(MVCConstants.REPORT_OUTPUT_PDF);

		try {
			// set input and output stream
			ServletOutputStream servletOutputStream = response.getOutputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			BigInteger orderId = new BigInteger(req.getParameter(MVCConstants.ORDER_ID_PARAM));

			Order order = orderService.searchOrder(orderId);

			List<OrderBean> orderCollection = new ArrayList<>();
			OrderBean orderBean = new OrderBean();
			this.updateOrderItemsBean(orderBean, order, locale);
			List<AddressBean> primaryAddress = this.getSupplierAddress(orderBean.getSupplier().getAddresses());

			orderCollection.add(orderBean);

			InputStream orderReportStream = getClass().getResourceAsStream(MVCConstants.ORDER_REPORT);

			JasperReport jasperReport = JasperCompileManager.compileReport(orderReportStream);

			JRBeanCollectionDataSource orderDS = new JRBeanCollectionDataSource(orderCollection);

			InputStream orderReportStreamChild = getClass().getResourceAsStream(MVCConstants.ORDER_ITEMS_REPORT);
			JasperReport jasperReportChild = JasperCompileManager.compileReport(orderReportStreamChild);

			InputStream supplierReportStream = getClass().getResourceAsStream(MVCConstants.SUPPLIER_REPORT);
			JasperReport jasperSupplierReport = JasperCompileManager.compileReport(supplierReportStream);

			InputStream supplierAddressReportStream = getClass().getResourceAsStream(MVCConstants.ADDRESS_REPORT);
			JasperReport jasperSupplierAddressReport = JasperCompileManager.compileReport(supplierAddressReportStream);

			InputStream deliveryAddressReportStream = getClass().getResourceAsStream(MVCConstants.ADDRESS_REPORT);
			JasperReport jasperDelieveryAddressReport = JasperCompileManager.compileReport(deliveryAddressReportStream);

			List<SupplierBean> supplierList = new ArrayList<>();

			supplierList.add(orderBean.getSupplier());

			Map<String, Object> paramMap = new HashMap<>();

			paramMap.put(MVCConstants.ORDER_ITEM_REPORT_PARAM, jasperReportChild);
			paramMap.put(MVCConstants.SUPPLIER_REPORT_PARAM, jasperSupplierReport);
			paramMap.put(MVCConstants.SUPPLIER_ADDRESS_REPORT_PARAM, jasperSupplierAddressReport);
			paramMap.put(MVCConstants.DELIVERY_ADDRESS_REPORT_PARAM, jasperDelieveryAddressReport);

			paramMap.put(MVCConstants.SUPPLIER_DATA_PARAM, supplierList);
			paramMap.put(MVCConstants.SUPPLIER_ADDRESS_DATA_PARAM, primaryAddress);
			paramMap.put(MVCConstants.DELIVERY_ADDRESS_DATA_PARAM, primaryAddress);

			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, paramMap, orderDS);

			// export to pdf
			JasperExportManager.exportReportToPdfStream(jasperPrint, baos);

			response.setContentLength(baos.size());
			baos.writeTo(servletOutputStream);

			logger.info("The selected purchase order report has been printed successfully");
		} catch (Exception e) {
			logger.error("There is an error while retrieving purchase order for updation", e);
		}
	}

	public List<AddressBean> getSupplierAddress(List<AddressBean> addresses) {
		List<AddressBean> primaryAddress = new ArrayList<>();
		for (AddressBean address : addresses) {
			if (address.getPrimary().equals(MVCConstants.YES)) {
				primaryAddress.add(address);
				break;
			}
		}
		return primaryAddress;

	}

}
