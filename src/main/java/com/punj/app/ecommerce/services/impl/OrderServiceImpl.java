/**
 * 
 */
package com.punj.app.ecommerce.services.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.punj.app.ecommerce.domains.order.Order;
import com.punj.app.ecommerce.domains.order.OrderDTO;
import com.punj.app.ecommerce.repositories.item.ItemRepository;
import com.punj.app.ecommerce.repositories.order.OrderItemRepository;
import com.punj.app.ecommerce.repositories.order.OrderRepository;
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

	@Override
	public OrderDTO searchOrder(String text, Pager pager) {
		int startCount = (pager.getCurrentPageNo() - 1) * maxResultPerPage + 1;
		pager.setPageSize(maxResultPerPage);
		pager.setStartCount(startCount);
		pager.setMaxDisplayPage(maxPageBtns);

		OrderDTO orders = null;// supplierSearchRepository.search(text, pager);
		logger.info("The suppliers has been retrieved based on searched keyword");
		return orders;
	}

	@Override
	public Order createOrder(Order order) {
		
		order=orderRepository.save(order);
		return order;
	}

}
