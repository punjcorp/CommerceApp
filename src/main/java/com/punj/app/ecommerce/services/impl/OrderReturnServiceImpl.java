/**
 * 
 */
package com.punj.app.ecommerce.services.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.punj.app.ecommerce.domains.order.Order;
import com.punj.app.ecommerce.domains.order.OrderItem;
import com.punj.app.ecommerce.domains.order.returns.OrderReturn;
import com.punj.app.ecommerce.domains.order.returns.OrderReturnItem;
import com.punj.app.ecommerce.repositories.item.ItemRepository;
import com.punj.app.ecommerce.repositories.order.returns.OrderReturnItemRepository;
import com.punj.app.ecommerce.repositories.order.returns.OrderReturnItemTaxRepository;
import com.punj.app.ecommerce.repositories.order.returns.OrderReturnRepository;
import com.punj.app.ecommerce.repositories.supplier.SupplierRepository;
import com.punj.app.ecommerce.services.InventoryService;
import com.punj.app.ecommerce.services.OrderReturnService;
import com.punj.app.ecommerce.services.OrderService;
import com.punj.app.ecommerce.services.PaymentAccountService;
import com.punj.app.ecommerce.services.common.CommonService;

/**
 * @author admin
 *
 */
@Service
public class OrderReturnServiceImpl implements OrderReturnService {

	private static final Logger logger = LogManager.getLogger();
	private OrderReturnRepository orderReturnRepository;
	private OrderReturnItemRepository orderReturnItemRepository;
	private OrderReturnItemTaxRepository orderReturnItemTaxRepository;

	private SupplierRepository supplierRepository;
	private ItemRepository itemRepository;
	private PaymentAccountService paymentService;
	private InventoryService inventoryService;
	private CommonService commonService;
	private OrderService orderService;

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
	 * @param orderReturnItemTaxRepository
	 *            the orderReturnItemTaxRepository to set
	 */
	@Autowired
	public void setOrderReturnItemTaxRepository(OrderReturnItemTaxRepository orderReturnItemTaxRepository) {
		this.orderReturnItemTaxRepository = orderReturnItemTaxRepository;
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
	 * @param orderReturnRepository
	 *            the orderReturnRepository to set
	 */
	@Autowired
	public void setOrderReturnRepository(OrderReturnRepository orderReturnRepository) {
		this.orderReturnRepository = orderReturnRepository;
	}

	/**
	 * @param orderReturnItemRepository
	 *            the orderReturnItemRepository to set
	 */
	@Autowired
	public void setOrderReturnItemRepository(OrderReturnItemRepository orderReturnItemRepository) {
		this.orderReturnItemRepository = orderReturnItemRepository;
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
	 * @return the supplierRepository
	 */
	public SupplierRepository getSupplierRepository() {
		return supplierRepository;
	}

	@Override
	@Transactional
	public OrderReturn createOrderReturn(OrderReturn orderReturn, String username) {
		orderReturn = this.orderReturnRepository.save(orderReturn);
		if (orderReturn != null) {
			logger.info("The new return for order {} has been created with provided details", orderReturn.getOrderReturnId());
			BigInteger orderId = orderReturn.getOrder().getOrderId();
			Map<BigInteger, BigDecimal> returnQtyMap = new HashMap<>();
			Order order = this.orderService.searchOrder(orderId);

			for (OrderReturnItem orderReturnItem : orderReturn.getOrderReturnItems()) {
				for (OrderItem orderItem : order.getOrderItems()) {
					if(orderItem.getItemId().equals(orderReturnItem.getItemId())) {
						returnQtyMap.put(orderItem.getOrderItemId(), orderReturnItem.getReturnQty());
						break;
					}
				}
			}
			order = this.orderService.updateReturnedItems(orderId, returnQtyMap, username);
		}

		else
			logger.info("The order return creation has been failed due to some issues");
		return orderReturn;
	}

}
