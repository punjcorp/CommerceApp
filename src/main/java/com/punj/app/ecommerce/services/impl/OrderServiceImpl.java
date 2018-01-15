/**
 * 
 */
package com.punj.app.ecommerce.services.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.punj.app.ecommerce.domains.order.Order;
import com.punj.app.ecommerce.domains.order.OrderDTO;
import com.punj.app.ecommerce.domains.order.OrderItem;
import com.punj.app.ecommerce.repositories.item.ItemRepository;
import com.punj.app.ecommerce.repositories.order.OrderItemRepository;
import com.punj.app.ecommerce.repositories.order.OrderRepository;
import com.punj.app.ecommerce.repositories.order.OrderSearchRepository;
import com.punj.app.ecommerce.repositories.supplier.SupplierRepository;
import com.punj.app.ecommerce.services.OrderService;
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
	private SupplierRepository supplierRepository;
	private ItemRepository itemRepository;
	private OrderSearchRepository orderSearchRepository;

	@Value("${commerce.list.max.perpage}")
	private Integer maxResultPerPage;

	@Value("${commerce.list.max.pageno}")
	private Integer maxPageBtns;

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
		int startCount = (pager.getCurrentPageNo() - 1) * maxResultPerPage + 1;
		pager.setPageSize(maxResultPerPage);
		pager.setStartCount(startCount);
		pager.setMaxDisplayPage(maxPageBtns);

		OrderDTO orders = this.orderSearchRepository.search(text, pager);
		logger.info("The suppliers has been retrieved based on searched keyword");
		return orders;
	}

	@Override
	public Order createOrder(Order order) {

		order = orderRepository.save(order);
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
		for (Order order : orders) {
			BigInteger orderId = order.getOrderId();
			if (orderId != null) {
				actualOrder = orderRepository.findOne(orderId);
				actualOrder.setSupplierId(order.getSupplierId());

			} else {
				actualOrder = order;
			}

			finalOrders.add(orderRepository.save(actualOrder));
			logger.info("The {} purchase order has been updated", order.getOrderId());
		}
		return finalOrders;
	}

	@Override
	public List<Order> approveOrders(List<Order> orders) {

		List<Order> finalOrders = new ArrayList<Order>(orders.size());
		Order actualOrder = null;
		for (Order order : orders) {
			BigInteger orderId = order.getOrderId();
			if (orderId != null) {
				actualOrder = orderRepository.findOne(orderId);
				actualOrder.setStatus("A");

			} else {
				actualOrder = order;
			}

			finalOrders.add(orderRepository.save(actualOrder));
			logger.info("The {} purchase order has been approved now", order.getOrderId());
		}
		return finalOrders;
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

		Order order = orderRepository.findOne(orderId);
		logger.info("The selected purchase order {} has been retrieved", order.getOrderId());

		return order;
	}

	@Override
	public void deleteOrderItem(OrderItem orderItem) {
		
		orderItemRepository.delete(orderItem.getOrderItemId());
		logger.info("The selected item {} has been deleted from order {} successfully",
				orderItem.getOrderItemId().getItemId(), orderItem.getOrderItemId().getOrder().getOrderId());

	}

	@Override
	public OrderDTO findAll() {
		OrderDTO orders=new OrderDTO();
		List<Order> orderList=orderRepository.findAll();
		orders.setOrders(orderList);
		
		return orders;
	}

}
