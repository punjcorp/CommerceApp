/**
 * 
 */
package com.punj.app.ecommerce.services.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.punj.app.ecommerce.domains.inventory.ItemStockJournal;
import com.punj.app.ecommerce.domains.inventory.StockReason;
import com.punj.app.ecommerce.domains.item.Item;
import com.punj.app.ecommerce.domains.item.ItemOptions;
import com.punj.app.ecommerce.domains.order.Order;
import com.punj.app.ecommerce.domains.order.OrderBill;
import com.punj.app.ecommerce.domains.order.OrderDTO;
import com.punj.app.ecommerce.domains.order.OrderItem;
import com.punj.app.ecommerce.domains.payment.AccountHead;
import com.punj.app.ecommerce.domains.supplier.Supplier;
import com.punj.app.ecommerce.repositories.item.ItemRepository;
import com.punj.app.ecommerce.repositories.order.OrderBillRepository;
import com.punj.app.ecommerce.repositories.order.OrderItemRepository;
import com.punj.app.ecommerce.repositories.order.OrderItemTaxRepository;
import com.punj.app.ecommerce.repositories.order.OrderRepository;
import com.punj.app.ecommerce.repositories.order.OrderSearchRepository;
import com.punj.app.ecommerce.repositories.supplier.SupplierRepository;
import com.punj.app.ecommerce.services.InventoryService;
import com.punj.app.ecommerce.services.OrderService;
import com.punj.app.ecommerce.services.PaymentAccountService;
import com.punj.app.ecommerce.services.common.ServiceConstants;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
@Service
public class OrderServiceImpl implements OrderService {

	private static final Logger logger = LogManager.getLogger();
	private OrderRepository orderRepository;
	private OrderItemRepository orderItemRepository;
	private OrderBillRepository orderBillRepository;
	private SupplierRepository supplierRepository;
	private ItemRepository itemRepository;
	private OrderSearchRepository orderSearchRepository;
	private PaymentAccountService paymentService;
	private InventoryService inventoryService;
	private OrderItemTaxRepository orderItemTaxRepository;

	@Value("${commerce.list.max.perpage}")
	private Integer maxResultPerPage;

	@Value("${commerce.list.max.pageno}")
	private Integer maxPageBtns;

	/**
	 * @param paymentService
	 *            the paymentService to set
	 */
	@Autowired
	public void setPaymentService(PaymentAccountService paymentService) {
		this.paymentService = paymentService;
	}

	/**
	 * @param paymentService
	 *            the paymentService to set
	 */
	@Autowired
	public void setOrderItemTaxRepository(OrderItemTaxRepository orderItemTaxRepository) {
		this.orderItemTaxRepository = orderItemTaxRepository;
	}

	/**
	 * @return the inventoryService
	 */
	public InventoryService getInventoryService() {
		return inventoryService;
	}

	/**
	 * @param inventoryService
	 *            the inventoryService to set
	 */
	@Autowired
	public void setInventoryService(InventoryService inventoryService) {
		this.inventoryService = inventoryService;
	}

	/**
	 * @param supplierRepository
	 *            the supplierRepository to set
	 */
	@Autowired
	public void setSupplierRepository(SupplierRepository supplierRepository) {
		this.supplierRepository = supplierRepository;
	}

	/**
	 * @return the orderRepository
	 */
	public OrderRepository getOrderRepository() {
		return orderRepository;
	}

	/**
	 * @param orderRepository
	 *            the orderRepository to set
	 */
	@Autowired
	public void setOrderRepository(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	/**
	 * @return the orderItemRepository
	 */
	public OrderItemRepository getOrderItemRepository() {
		return orderItemRepository;
	}

	/**
	 * @param orderItemRepository
	 *            the orderItemRepository to set
	 */
	@Autowired
	public void setOrderItemRepository(OrderItemRepository orderItemRepository) {
		this.orderItemRepository = orderItemRepository;
	}

	/**
	 * @param orderBillRepository
	 *            the orderBillRepository to set
	 */
	@Autowired
	public void setOrderBillRepository(OrderBillRepository orderBillRepository) {
		this.orderBillRepository = orderBillRepository;
	}

	/**
	 * @return the itemRepository
	 */
	public ItemRepository getItemRepository() {
		return itemRepository;
	}

	/**
	 * @param itemRepository
	 *            the itemRepository to set
	 */
	@Autowired
	public void setItemRepository(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}

	/**
	 * @return the orderSearchRepository
	 */
	public OrderSearchRepository getOrderSearchRepository() {
		return orderSearchRepository;
	}

	/**
	 * @param orderSearchRepository
	 *            the orderSearchRepository to set
	 */
	@Autowired
	public void setOrderSearchRepository(OrderSearchRepository orderSearchRepository) {
		this.orderSearchRepository = orderSearchRepository;
	}

	/**
	 * @return the supplierRepository
	 */
	public SupplierRepository getSupplierRepository() {
		return supplierRepository;
	}

	@Override
	public OrderDTO searchOrder(String text, Pager pager) {
		int startCount = (pager.getCurrentPageNo() - 1) * maxResultPerPage;
		pager.setPageSize(maxResultPerPage);
		pager.setStartCount(startCount);
		pager.setMaxDisplayPage(maxPageBtns);

		OrderDTO orders = this.orderSearchRepository.search(text, pager);
		logger.info("The suppliers has been retrieved based on searched keyword");
		return orders;
	}

	@Override
	public Order createOrder(Order order) {

		order = this.orderRepository.save(order);
		logger.info("The new purchase order {} has been created with provided details", order.getOrderId());
		return order;
	}

	@Override
	public OrderDTO searchAllOrders(Pager pager) {
		OrderDTO orders = new OrderDTO();

		List<Order> orderList = orderRepository.findAll();

		orders.setOrders(orderList);
		orders.setPager(pager);

		logger.info("All the purchase orders has been retrieved successfully");

		return orders;
	}

	@Override
	public void deleteOrder(BigInteger orderId) {
		orderRepository.delete(orderId);
		logger.info("The selected order {} and order items has been deleted successfully", orderId);

	}

	@Override
	public void deleteOrders(Set<BigInteger> orderIds) {

		for (BigInteger orderId : orderIds) {
			this.deleteOrder(orderId);
			logger.info("The provided purchase order {} has been deleted successfully", orderId);
		}

		logger.info("All provided {} purchase orders has been deleted successfully", orderIds.size());

	}

	@Override
	public List<Order> updateOrders(List<Order> orders) {

		List<Order> finalOrders = new ArrayList<Order>(orders.size());
		Order actualOrder = null;
		Supplier supplier = null;
		for (Order order : orders) {
			BigInteger orderId = order.getOrderId();
			if (orderId != null) {
				actualOrder = orderRepository.findOne(orderId);
				supplier = new Supplier();
				supplier.setSupplierId(order.getSupplier().getSupplierId());
				actualOrder.setSupplier(supplier);

			} else {
				actualOrder = order;
			}

			finalOrders.add(orderRepository.save(actualOrder));
			logger.info("The {} purchase order has been updated", order.getOrderId());
		}
		return finalOrders;
	}

	@Override
	@Transactional
	public List<Order> approveOrders(List<Order> orders, String username) {

		List<Order> finalOrders = new ArrayList<>(orders.size());
		Order actualOrder = null;
		for (Order order : orders) {
			BigInteger orderId = order.getOrderId();
			if (orderId != null) {
				actualOrder = orderRepository.findOne(orderId);
				actualOrder.setStatus("A");
			} else {
				actualOrder = order;
			}
			finalOrders.add(actualOrder);
		}
		finalOrders = this.orderRepository.save(finalOrders);
		if (finalOrders != null && !finalOrders.isEmpty()) {
			logger.info("All the {} purchase orders has been approved now", finalOrders.size());
			List<AccountHead> accountHeads = this.getOrderAccounts(finalOrders);
			accountHeads = this.paymentService.updateAccountsDue(accountHeads, username);
			if (accountHeads != null && !accountHeads.isEmpty()) {
				logger.info("All the order accounts has been updated for the due amounts");
			} else {
				logger.info("There was some errors while updating order accounts for the due amounts");
			}
		}

		return finalOrders;
	}

	private List<AccountHead> getOrderAccounts(List<Order> orders) {
		List<AccountHead> accountHeads = new ArrayList<>(orders.size());
		AccountHead accountHead;

		for (Order order : orders) {
			accountHead = this.getOrderAccount(order);
			accountHeads.add(accountHead);
		}
		logger.info("All the account head details has been created for provided orders");
		return accountHeads;
	}

	private AccountHead getOrderAccount(Order order) {
		AccountHead accountHead = new AccountHead();
		accountHead.setLocationId(order.getLocation().getLocationId());
		accountHead.setEntityType(ServiceConstants.ACCOUNT_TYPE_SUPPLIER);
		accountHead.setEntityId(new BigInteger(order.getSupplier().getSupplierId().toString()));

		accountHead.setDueAmount(order.getTotalAmount());

		logger.info("All the account head details has been set for the provided order.");
		return accountHead;
	}

	@Override
	public Order approveOrder(BigInteger orderId) {

		Order actualOrder = null;
		actualOrder = orderRepository.findOne(orderId);
		actualOrder.setStatus("A");
		orderRepository.save(actualOrder);

		/**
		 * P-STORY - Add email to supplier functionality here if possible
		 */
		logger.info("The selected purchase order {} has been approved now ", orderId);

		return actualOrder;
	}

	public Order searchOrder(BigInteger orderId) {

		Order order = this.orderRepository.findOne(orderId);
		if (order != null)
			logger.info("The selected purchase order {} has been retrieved", order.getOrderId());
		else
			logger.info("The selected purchase order {} was not found!", orderId);

		return order;
	}

	@Override
	@Transactional
	public void deleteOrderItem(OrderItem orderItem) {

		this.orderItemTaxRepository.delete(orderItem.getOrderItemTaxes());
		logger.info("The order item taxes has been deleted from the order");

		this.orderItemRepository.delete(orderItem.getOrderItemId());

		logger.info("The selected item {} has been deleted from order {} successfully", orderItem.getItemId(), orderItem.getOrder().getOrderId());

	}

	@Override
	public Order updateOrderTotals(BigInteger orderId, String username) {

		Order order = this.orderRepository.findOne(orderId);

		order.setModifiedBy(username);
		order.setModifiedDate(LocalDateTime.now());

		BigDecimal estimatedCost = BigDecimal.ZERO;
		BigDecimal taxAmount = BigDecimal.ZERO;
		BigDecimal totalAmount = BigDecimal.ZERO;

		for (OrderItem orderItem : order.getOrderItems()) {
			estimatedCost = estimatedCost.add(orderItem.getTotalCost());
			taxAmount = taxAmount.add(orderItem.getCostAmount());
			totalAmount = totalAmount.add(orderItem.getTaxAmount());
		}

		order.setEstimatedCost(estimatedCost);
		order.setTaxAmount(taxAmount);
		order.setTotalAmount(totalAmount);

		order = this.orderRepository.save(order);
		if (order != null)
			logger.info("The order totals has been updated successfully");
		else
			logger.info("There was some issue while updating order total amounts");
		return order;
	}

	@Override
	public OrderDTO findAll() {
		OrderDTO orders = new OrderDTO();
		List<Order> orderList = orderRepository.findAll();
		orders.setOrders(orderList);

		return orders;
	}

	@Override
	@Transactional
	public Order receiveOrder(BigInteger orderId, String username) {

		Order order = this.orderRepository.findOne(orderId);
		logger.debug("The order details retrieved from database", orderId);

		order.setStatus("R");
		order = this.orderRepository.save(order);
		logger.debug("The order status has been marked as R now", orderId);

		List<OrderItem> orderItems = order.getOrderItems();
		List<ItemStockJournal> inventoryDetails = this.createStockDetails(order, orderItems, username);

		this.inventoryService.updateInventory(inventoryDetails);
		logger.debug("The order items inventory has been updated successfully", orderId);

		logger.info("The order {} has been marked as received successfully.", orderId);
		return order;
	}

	private List<ItemStockJournal> createStockDetails(Order order, List<OrderItem> orderItems, String username) {

		List<ItemStockJournal> itemStockDetails = new ArrayList<>(orderItems.size());
		ItemStockJournal itemStockJournal = null;
		Item item = null;

		for (OrderItem orderItem : orderItems) {
			itemStockJournal = new ItemStockJournal();
			itemStockJournal.setCreatedBy(username);
			itemStockJournal.setCreatedDate(LocalDateTime.now());

			itemStockJournal.setLocationId(order.getLocation().getLocationId());

			item = new Item();
			item.setItemId(orderItem.getItemId());
			itemStockJournal.setItemId(item.getItemId());

			StockReason stockReason = new StockReason();
			stockReason.setReasonCode(ServiceConstants.INV_REASON_STKIN);
			itemStockJournal.setReasonCode(stockReason);

			itemStockJournal.setFunctionality(ServiceConstants.RECEIVE_ORDER_FUNCTIONALITY);
			itemStockJournal.setQty(orderItem.getDelieveredQty().intValue());

			itemStockDetails.add(itemStockJournal);
		}

		logger.info("The stock details has been created from Order Item details");
		return itemStockDetails;

	}

	@Override
	public List<Order> approveAllOrders(List<BigInteger> orderIds, String username) {
		List<Order> orderList = this.orderRepository.findAll(orderIds);
		for (Order order : orderList) {
			order.setModifiedBy(username);
			order.setModifiedDate(LocalDateTime.now());
			order.setStatus(ServiceConstants.STATUS_APPROVED);
		}
		orderList = this.orderRepository.save(orderList);
		logger.info("All the selected orders has been marked as approved now");
		return orderList;
	}

	@Override
	public void deleteAllOrders(List<BigInteger> orderIds, String username) {
		List<Order> orderList = this.orderRepository.findAll(orderIds);
		for (Order order : orderList) {
			order.setModifiedBy(username);
			order.setModifiedDate(LocalDateTime.now());
			order.setStatus(ServiceConstants.STATUS_DELETED);
		}
		this.orderRepository.save(orderList);
		logger.info("All the selected orders has been marked as deleted now");
	}

	@Override
	public void deleteBill(OrderBill orderBill) {
		this.orderBillRepository.delete(orderBill.getOrderBillId());

		logger.info("The selected bill {} has been deleted from order {} successfully", orderBill.getBillNo(), orderBill.getOrder().getOrderId());
	}

	@Override
	public OrderBill retrieveOrderBillDoc(BigInteger orderBillId) {
		OrderBill orderBill = this.orderBillRepository.findOne(orderBillId);
		if (orderBill != null)
			logger.info("The selected bill for id {} of type {} has been retrieved successfully", orderBillId, orderBill.getBillFileType());
		else
			logger.info("The selected bill for id {} was not found", orderBillId);

		return orderBill;
	}

	@Override
	@Transactional
	public Order receiveOrder(Order order, String username) {
		BigInteger orderId = order.getOrderId();

		order = this.createOrder(order);
		if (order != null) {
			logger.info("The order {} has been marked as received in order tables.", orderId);

			List<OrderItem> orderItems = order.getOrderItems();
			List<ItemStockJournal> inventoryDetails = this.createStockDetails(order, orderItems, username);
			this.inventoryService.updateInventory(inventoryDetails);
			logger.debug("The order items inventory has been updated successfully", orderId);

			this.updateItemPriceUpdates(orderItems, orderId, username);
			logger.info("The order item price updates has been completed successfully.");
			
			
			logger.info("The order {} receive process is completed successfully.", orderId);

		} else {
			logger.error("The order {} was not marked as received due to some issue");
		}
		return order;
	}

	private void updateItemPriceUpdates(List<OrderItem> orderItems,BigInteger orderId, String username) {
		Item item = null;
		ItemOptions itemOptions = null;
		
		BigDecimal suggestedPrice=null;
		BigDecimal maxRetailPrice=null;
		
		for (OrderItem orderItem : orderItems) {
			suggestedPrice=orderItem.getActualSuggestedPrice();
			maxRetailPrice=orderItem.getActualMaxRetailPrice();
			item = this.itemRepository.findOne(orderItem.getItemId());
			if (( suggestedPrice!= null && suggestedPrice.doubleValue() > 0)
					|| (maxRetailPrice != null && maxRetailPrice.doubleValue() > 0)) {
				itemOptions = item.getItemOptions();
				if (suggestedPrice.doubleValue() > 0)
					itemOptions.setSuggestedPrice(suggestedPrice);
				if (maxRetailPrice.doubleValue() > 0)
					itemOptions.setMaxRetailPrice(maxRetailPrice);
			}
			itemOptions.setUnitCost(orderItem.getActualUnitCost());
			
			item.setModifiedBy(username);
			item.setModifiedDate(LocalDateTime.now());
			
			this.itemRepository.save(item);
			logger.info("The item {} prices has been updated successfully based on the details from order.",item.getItemId(), orderId);
		}
	}

	@Override
	@Transactional
	public Order receiveAllOrder(Order order, String username) {
		BigInteger orderId = order.getOrderId();
		order = this.receiveOrder(order, username);
		logger.info("The order {} receive all process is completed successfully.", orderId);
		return order;
	}

}
