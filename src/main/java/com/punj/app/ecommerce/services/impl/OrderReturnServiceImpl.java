/**
 * 
 */
package com.punj.app.ecommerce.services.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.punj.app.ecommerce.domains.inventory.ItemStockJournal;
import com.punj.app.ecommerce.domains.inventory.StockReason;
import com.punj.app.ecommerce.domains.item.Item;
import com.punj.app.ecommerce.domains.order.Order;
import com.punj.app.ecommerce.domains.order.OrderItem;
import com.punj.app.ecommerce.domains.order.returns.OrderReturn;
import com.punj.app.ecommerce.domains.order.returns.OrderReturnDTO;
import com.punj.app.ecommerce.domains.order.returns.OrderReturnItem;
import com.punj.app.ecommerce.domains.payment.AccountHead;
import com.punj.app.ecommerce.domains.payment.AccountJournal;
import com.punj.app.ecommerce.domains.tender.Tender;
import com.punj.app.ecommerce.repositories.item.ItemRepository;
import com.punj.app.ecommerce.repositories.order.returns.OrderReturnItemRepository;
import com.punj.app.ecommerce.repositories.order.returns.OrderReturnItemTaxRepository;
import com.punj.app.ecommerce.repositories.order.returns.OrderReturnRepository;
import com.punj.app.ecommerce.repositories.order.returns.ReturnSearchRepository;
import com.punj.app.ecommerce.repositories.supplier.SupplierRepository;
import com.punj.app.ecommerce.services.InventoryService;
import com.punj.app.ecommerce.services.OrderReturnService;
import com.punj.app.ecommerce.services.OrderService;
import com.punj.app.ecommerce.services.PaymentAccountService;
import com.punj.app.ecommerce.services.common.CommonService;
import com.punj.app.ecommerce.services.common.ServiceConstants;
import com.punj.app.ecommerce.services.converter.TxnToAJConverter;
import com.punj.app.ecommerce.utils.Pager;

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
	private ReturnSearchRepository returnSearchRepository;

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
	 * @param returnSearchRepository
	 *            the returnSearchRepository to set
	 */
	@Autowired
	public void setReturnSearchRepository(ReturnSearchRepository returnSearchRepository) {
		this.returnSearchRepository = returnSearchRepository;
	}

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
			orderReturn =this.orderReturnRepository.findOne(orderReturn.getOrderReturnId());
			if (orderReturn.getStatus().equals(ServiceConstants.STATUS_APPROVED)) {
				this.postApprovalActions(orderReturn, username);
			}
		} else
			logger.error("The order return creation has been failed due to some issues");
		return orderReturn;
	}

	@Override
	public OrderReturnDTO searchOrderReturns(String text, Pager pager) {
		int startCount = (pager.getCurrentPageNo() - 1) * maxResultPerPage;
		pager.setPageSize(maxResultPerPage);
		pager.setStartCount(startCount);
		pager.setMaxDisplayPage(maxPageBtns);

		OrderReturnDTO orderReturns = this.returnSearchRepository.search(text, pager);
		logger.info("The order returns has been retrieved based on searched keyword");
		return orderReturns;
	}

	@Override
	@Transactional
	public OrderReturn approveOrderReturn(BigInteger orderReturnId, String username) {
		OrderReturn orderReturn = this.searchOrderReturn(orderReturnId);
		if (orderReturn != null) {
			orderReturn.setStatus(ServiceConstants.STATUS_APPROVED);
			orderReturn.setModifiedBy(username);
			orderReturn.setModifiedDate(LocalDateTime.now());
			orderReturn = this.orderReturnRepository.save(orderReturn);
			if (orderReturn != null) {
				this.postApprovalActions(orderReturn, username);
				logger.info("The order return has been approved successfully");
			} else {
				logger.info("There is some error while approving the order return");
			}
		} else {
			logger.info("There was no order return found with the provided number {} ", orderReturnId);
		}
		return orderReturn;
	}

	private void postApprovalActions(OrderReturn orderReturn, String username) {

		BigInteger orderId = orderReturn.getOrder().getOrderId();
		Map<BigInteger, BigDecimal> returnQtyMap = new HashMap<>();
		Order order = this.orderService.searchOrder(orderId);

		for (OrderReturnItem orderReturnItem : orderReturn.getOrderReturnItems()) {
			for (OrderItem orderItem : order.getOrderItems()) {
				if (orderItem.getItemId().equals(orderReturnItem.getItemId())) {
					returnQtyMap.put(orderItem.getOrderItemId(), orderReturnItem.getReturnQty());
					break;
				}
			}
		}
		order = this.orderService.updateReturnedItems(orderId, returnQtyMap, username);

		if (order != null) {
			logger.info("The order returns item updates has been updated in original order items");
			Boolean result = this.updateInventoryAfterApproval(orderReturn, username);
			if (result)
				logger.info("The inventory updates for the returned items is a success");
			else
				logger.error("The inventory updates for the supplier order returned items has failed");

			AccountJournal accountJournal = this.createAccountJournalUpdates(orderReturn, username);
			AccountHead accountHead = this.paymentService.recordOrderAmount(accountJournal, new BigInteger(order.getSupplier().getSupplierId().toString()),
					order.getLocation().getLocationId(), username);
			if (accountHead != null)
				logger.info("The order items return amount has been updated in supplier account successfully.");
			else
				logger.error("There was an issue posting order return amounts to Supplier's account");

		} else {
			logger.error("There is some error while updating the order return item returned qty in original order");
		}
	}

	private AccountJournal createAccountJournalUpdates(OrderReturn orderReturn, String username) {

		AccountJournal accountJournal = null;

		AccountHead accountHead = this.paymentService.retrievePaymentAccount(ServiceConstants.ACCOUNT_TYPE_SUPPLIER,
				new BigInteger(orderReturn.getOrder().getSupplier().getSupplierId().toString()), orderReturn.getOrder().getLocation().getLocationId());

		if (accountHead != null) {
			Map<String, Tender> tenderDetails = this.commonService.retrieveAllTenderNamesAsMap(orderReturn.getOrder().getLocation().getLocationId());
			Tender cashTender = tenderDetails.get(ServiceConstants.TENDER_CASH);
			accountJournal = TxnToAJConverter.convertOrderReturnAmounts(orderReturn, accountHead.getAccountId(), cashTender.getTenderId(), username);
			logger.info("The order return {} has been transformed into account journal details.", orderReturn.getOrderReturnId());
		}

		return accountJournal;
	}

	private Boolean updateInventoryAfterApproval(OrderReturn orderReturn, String username) {
		Boolean result = Boolean.FALSE;

		List<OrderReturnItem> orderReturnItems = orderReturn.getOrderReturnItems();
		List<ItemStockJournal> inventoryDetails = this.createInventoryUpdates(orderReturnItems, username, orderReturn.getOrder().getLocation().getLocationId());
		this.inventoryService.updateInventory(inventoryDetails);
		logger.debug("The order return items inventory has been updated successfully", orderReturn.getOrderReturnId());
		result = Boolean.TRUE;
		return result;

	}

	private List<ItemStockJournal> createInventoryUpdates(List<OrderReturnItem> returnItems, String username, Integer locationId) {

		List<ItemStockJournal> itemStockDetails = new ArrayList<>(returnItems.size());
		ItemStockJournal itemStockJournal = null;
		Item item = null;

		for (OrderReturnItem returnItem : returnItems) {

			itemStockJournal = new ItemStockJournal();
			itemStockJournal.setCreatedBy(username);
			itemStockJournal.setCreatedDate(LocalDateTime.now());

			itemStockJournal.setLocationId(locationId);

			item = new Item();
			item.setItemId(returnItem.getItemId());
			itemStockJournal.setItemId(item.getItemId());

			StockReason stockReason = new StockReason();
			stockReason.setReasonCode(ServiceConstants.INV_REASON_STKOUT);
			itemStockJournal.setReasonCode(stockReason);

			itemStockJournal.setFunctionality(ServiceConstants.SUPPLIER_ORDER_RETURN_FUNCTIONALITY);
			itemStockJournal.setQty(returnItem.getReturnQty().intValue());

			itemStockDetails.add(itemStockJournal);

		}

		logger.info("The returned items has been adjusted in the inventory successfully");
		return itemStockDetails;

	}

	@Override
	public Boolean deleteOrderReturn(BigInteger orderReturnId) {
		this.orderReturnRepository.delete(orderReturnId);
		logger.info("The order returns has been deleted successfully");
		return Boolean.TRUE;
	}

	@Override
	public OrderReturn searchOrderReturn(BigInteger orderReturnId) {
		OrderReturn orderReturn = this.orderReturnRepository.findOne(orderReturnId);
		if (orderReturn != null) {
			logger.info("The order return {} has been found successfully", orderReturnId);
		} else {
			logger.info("There was no order return found with the provided number {} ", orderReturnId);
		}
		return orderReturn;
	}

	@Override
	
	public OrderReturn updateOrderReturn(OrderReturn orderReturn, String username) {

		this.deleteOrderReturnItems(orderReturn.getOrderReturnId(), orderReturn.getOrderReturnItems());

		orderReturn = this.createOrderReturn(orderReturn, username);
		if (orderReturn != null) {
			logger.info("The order return {} changes has been saved successfully", orderReturn.getOrderReturnId());
		} else {
			logger.info("There was some error while saving order return {} changes ", orderReturn.getOrderReturnId());
		}
		return orderReturn;
	}

	private void deleteOrderReturnItems(BigInteger orderReturnId, List<OrderReturnItem> updatedOrderItems) {
		OrderReturnItem returnItemCriteria = new OrderReturnItem();
		returnItemCriteria.setActualUnitCost(null);
		returnItemCriteria.setActualCostAmount(null);
		returnItemCriteria.setActualDiscountAmount(null);
		returnItemCriteria.setActualTaxAmount(null);
		returnItemCriteria.setActualTotalCost(null);

		OrderReturn orderReturn = new OrderReturn();
		orderReturn.setOrderReturnId(orderReturnId);
		orderReturn.setSubTotalAmount(null);
		orderReturn.setDiscountAmount(null);
		orderReturn.setTaxAmount(null);
		orderReturn.setTotalAmount(null);
		orderReturn.setRefundAmount(null);
		
		
		returnItemCriteria.setOrderReturn(orderReturn);

		List<OrderReturnItem> returnItems = this.orderReturnItemRepository.findAll(Example.of(returnItemCriteria));
		if (returnItems != null && !returnItems.isEmpty()) {
			List<OrderReturnItem> filteredItems = this.filterOrderReturnItems(updatedOrderItems, returnItems);
			this.orderReturnItemRepository.delete(filteredItems);
			logger.info("The filtered order return items has been deleted successfully");
		}

	}

	private List<OrderReturnItem> filterOrderReturnItems(List<OrderReturnItem> orderItems, List<OrderReturnItem> orgOrderItems) {
		List<OrderReturnItem> filteredList = new ArrayList<>();
		Boolean isAddable = Boolean.FALSE;
		for (OrderReturnItem orgRI : orgOrderItems) {
			isAddable = Boolean.FALSE;
			for (OrderReturnItem updatedRI : orderItems) {
				if (orgRI.getOrderReturnItemId().equals(updatedRI.getOrderReturnItemId())) {
					isAddable = Boolean.TRUE;
					break;
				}
			}
			if (!isAddable) {
				filteredList.add(orgRI);
			}
		}
		logger.info("The order return items for deletion has been filtered successfully");

		return filteredList;
	}

	@Override
	public List<OrderReturn> approveOrderReturns(List<BigInteger> orderReturnIds, String username) {
		List<OrderReturn> orderReturns=new ArrayList<>(orderReturnIds.size());
		OrderReturn orderReturn=null;
		for(BigInteger orderReturnId:orderReturnIds) {
			orderReturn=this.approveOrderReturn(orderReturnId, username);
			orderReturns.add(orderReturn);
		}
		
		logger.info("The order returns has been approved successfully");
		return orderReturns;
	}

	@Override
	public void deleteOrderReturns(List<BigInteger> orderReturnIds, String username) {
		
		for(BigInteger orderReturnId:orderReturnIds) {
			this.deleteOrderReturn(orderReturnId);
		}
		logger.info("The provided order returns has been deleted successfully");
		
	}

}
