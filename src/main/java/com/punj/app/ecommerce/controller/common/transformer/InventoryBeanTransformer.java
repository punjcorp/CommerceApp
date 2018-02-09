/**
 * 
 */
package com.punj.app.ecommerce.controller.common.transformer;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.punj.app.ecommerce.domains.inventory.ItemStock;
import com.punj.app.ecommerce.domains.inventory.StockAdjustment;
import com.punj.app.ecommerce.domains.inventory.StockAdjustmentDTO;
import com.punj.app.ecommerce.domains.inventory.StockAdjustmentItem;
import com.punj.app.ecommerce.domains.inventory.StockAdjustmentItemDTO;
import com.punj.app.ecommerce.domains.inventory.StockBucket;
import com.punj.app.ecommerce.domains.inventory.StockReason;
import com.punj.app.ecommerce.domains.inventory.ids.StockAdjustmentItemId;
import com.punj.app.ecommerce.domains.order.Order;
import com.punj.app.ecommerce.models.inventory.InvAdjustBean;
import com.punj.app.ecommerce.models.inventory.InvAdjustItemBean;
import com.punj.app.ecommerce.models.inventory.InvAdjustItemInventory;
import com.punj.app.ecommerce.models.inventory.InvReasonBean;
import com.punj.app.ecommerce.models.inventory.ItemInventory;
import com.punj.app.ecommerce.models.order.OrderBean;
import com.punj.app.ecommerce.services.common.ServiceConstants;

/**
 * @author admin
 *
 */
public class InventoryBeanTransformer {

	private static final Logger logger = LogManager.getLogger();

	private InventoryBeanTransformer() {
		throw new IllegalStateException("InventoryBeanTransformer class");
	}

	/**
	 * This method is used to set the Domain objects so that the data can be persisted to the database
	 * 
	 * @param invAdjustBean
	 * @return The domain object for entity
	 */
	public static StockAdjustment transformInvAdjustBean(InvAdjustBean invAdjustBean) {
		StockAdjustment stockAdjustment = new StockAdjustment();

		stockAdjustment.setStockAdjustId(invAdjustBean.getInvAdjustId());
		stockAdjustment.setDescription(invAdjustBean.getDescription());

		StockReason stockReason = new StockReason();
		stockReason.setReasonCodeId(invAdjustBean.getReasonCodeId());
		stockAdjustment.setStockReason(stockReason);

		stockAdjustment.setLocationId(invAdjustBean.getLocationId());
		stockAdjustment.setStatus(invAdjustBean.getStatus());

		stockAdjustment.setCreatedBy(invAdjustBean.getCreatedBy());
		stockAdjustment.setCreatedDate(invAdjustBean.getCreatedDate());

		stockAdjustment.setModifiedBy(invAdjustBean.getModifiedBy());
		stockAdjustment.setModifiedDate(invAdjustBean.getModifiedDate());

		logger.info("The inventory adjustment bean has been transformed to domain object");

		return stockAdjustment;
	}

	/**
	 * This method is used to set the Domain objects so that the data can be persisted to the database with inventory adjustment items
	 * 
	 * @param invAdjustBean
	 * @return The domain object for entity
	 */
	public static StockAdjustment transformInvAdjustBeanWithItems(InvAdjustBean invAdjustBean) {

		StockAdjustment stockAdjustment = InventoryBeanTransformer.transformInvAdjustBean(invAdjustBean);
		logger.debug("The header level stock adjustment details has been transformed");

		List<InvAdjustItemBean> invAdjustItems = invAdjustBean.getInvAdjustItems();
		List<StockAdjustmentItem> stockAdjustmentItems = new ArrayList<>(invAdjustItems.size());
		StockAdjustmentItem stockAdjItem = null;
		StockAdjustmentItemId stockAdjItemId = null;
		StockReason stockReason = null;
		for (InvAdjustItemBean invAdjustItem : invAdjustItems) {
			stockAdjItem = new StockAdjustmentItem();

			stockAdjItemId = new StockAdjustmentItemId();
			stockAdjItemId.setItemId(invAdjustItem.getItemId());
			stockReason = new StockReason();
			stockReason.setReasonCodeId(invAdjustItem.getReasonCodeId());
			stockAdjItemId.setStockReason(stockReason);
			stockAdjItemId.setStockAdjustment(stockAdjustment);
			stockAdjItem.setStockAdjustmentItemId(stockAdjItemId);

			stockAdjItem.setQty(invAdjustItem.getQty());
			stockAdjustmentItems.add(stockAdjItem);
		}
		stockAdjustment.setStockAdjustItems(stockAdjustmentItems);

		logger.info("The stock adjustment details wtih items has been updated in domain object");

		return stockAdjustment;
	}

	public static InvAdjustBean transformStockAdjustmentDomain(StockAdjustment stockAdjustment) {
		InvAdjustBean invAdjustBean = new InvAdjustBean();
		/**
		 * Setting the basic information about the inventory adjustment
		 */
		invAdjustBean.setInvAdjustId(stockAdjustment.getStockAdjustId());
		invAdjustBean.setDescription(stockAdjustment.getDescription());
		invAdjustBean.setLocationId(stockAdjustment.getLocationId());
		invAdjustBean.setReasonCodeId(stockAdjustment.getStockReason().getReasonCodeId());
		invAdjustBean.setStatus(stockAdjustment.getStatus());
		invAdjustBean.setCreatedBy(stockAdjustment.getCreatedBy());
		invAdjustBean.setCreatedDate(stockAdjustment.getCreatedDate());
		invAdjustBean.setModifiedBy(stockAdjustment.getModifiedBy());
		invAdjustBean.setModifiedDate(stockAdjustment.getModifiedDate());

		logger.info("The stock adjustment has been transformed into inventory adjustment successfully");
		return invAdjustBean;
	}

	public static InvAdjustBean transformStockAdjustmentDomainWithItems(StockAdjustment stockAdjustment) {
		InvAdjustBean invAdjustBean = InventoryBeanTransformer.transformStockAdjustmentDomain(stockAdjustment);
		logger.info("The stock adjustment header has been transformed into inventory adjustment successfully");

		List<StockAdjustmentItem> stockAdjustmentItems = stockAdjustment.getStockAdjustItems();
		if (stockAdjustmentItems != null && !stockAdjustmentItems.isEmpty()) {

			List<InvAdjustItemBean> invAdjustItemBeans = new ArrayList<>(stockAdjustmentItems.size());
			InvAdjustItemBean invAdjustItemBean = null;
			for (StockAdjustmentItem stockAdjustmentItem : stockAdjustmentItems) {
				invAdjustItemBean=InventoryBeanTransformer.transformStockAdjustmentItem(stockAdjustmentItem);
				invAdjustItemBeans.add(invAdjustItemBean);
			}
			invAdjustBean.setInvAdjustItems(invAdjustItemBeans);
			logger.info("The stock adjustment item details has been transformed into inventory adjustment successfully");
		}
		return invAdjustBean;
	}
	
	public static InvAdjustItemBean transformStockAdjustmentItem(StockAdjustmentItem stockAdjustmentItem) {
		InvAdjustItemBean invAdjustItemBean = new InvAdjustItemBean();
		invAdjustItemBean.setInvAdjustId(stockAdjustmentItem.getStockAdjustmentItemId().getStockAdjustment().getStockAdjustId());
		invAdjustItemBean.setItemId(stockAdjustmentItem.getStockAdjustmentItemId().getItemId());
		invAdjustItemBean.setReasonCodeId(stockAdjustmentItem.getStockAdjustmentItemId().getStockReason().getReasonCodeId());
		invAdjustItemBean.setQty(stockAdjustmentItem.getQty());
		logger.info("The stock adjustment item details has been transformed into inventory adjustment successfully");
		
		return invAdjustItemBean;
	}

	public static List<InvAdjustBean> transformStockAdjustmentList(List<StockAdjustment> stockAdjustments) {
		List<InvAdjustBean> invAdjustBeanList = new ArrayList<>(stockAdjustments.size());
		InvAdjustBean invAdjustBean = null;
		for (StockAdjustment stockAdjustment : stockAdjustments) {
			invAdjustBean = InventoryBeanTransformer.transformStockAdjustmentDomainWithItems(stockAdjustment);
			invAdjustBeanList.add(invAdjustBean);
		}
		logger.info("The stock adjustment list has been transformed successfully");
		return invAdjustBeanList;
	}

	public static InvAdjustItemInventory transformItemStockWithReason(ItemStock itemStock, StockReason stockReason) {
		InvAdjustItemInventory invAdjustItemInventory = new InvAdjustItemInventory();

		ItemInventory itemInventory = transformItemStock(itemStock);
		InvReasonBean invReasonCode = transformStockReason(stockReason);

		invAdjustItemInventory.setItemInventory(itemInventory);
		invAdjustItemInventory.setItemReasonBean(invReasonCode);
		StockBucket fromStockBucket = stockReason.getFromBucket();
		StockBucket toStockBucket = stockReason.getToBucket();
		String fromSystemBucket = null;
		String toSystemBucket = null;
		if (fromStockBucket != null) {
			fromSystemBucket = fromStockBucket.getSystemBucket();
			if (fromSystemBucket.equals(ServiceConstants.INV_BUCKET_NON_SELL)) {
				invAdjustItemInventory.setFromBucketQty(itemStock.getNonSellableQty());
			} else if (fromSystemBucket.equals(ServiceConstants.INV_BUCKET_RESERVED)) {
				invAdjustItemInventory.setFromBucketQty(itemStock.getReservedQty());
			} else if (fromSystemBucket.equals(ServiceConstants.INV_BUCKET_SOH)) {
				invAdjustItemInventory.setFromBucketQty(itemStock.getStockOnHand());
			}

		}
		if (toStockBucket != null) {
			toSystemBucket = toStockBucket.getSystemBucket();
			if (toSystemBucket.equals(ServiceConstants.INV_BUCKET_NON_SELL)) {
				invAdjustItemInventory.setToBucketQty(itemStock.getNonSellableQty());
			} else if (toSystemBucket.equals(ServiceConstants.INV_BUCKET_RESERVED)) {
				invAdjustItemInventory.setToBucketQty(itemStock.getReservedQty());
			} else if (toSystemBucket.equals(ServiceConstants.INV_BUCKET_SOH)) {
				invAdjustItemInventory.setToBucketQty(itemStock.getStockOnHand());
			}

		}

		return invAdjustItemInventory;
	}

	public static ItemInventory transformItemStock(ItemStock itemStock) {
		ItemInventory itemInventory = new ItemInventory();
		itemInventory.setItemId(itemStock.getItemStockId().getItem().getItemId());
		itemInventory.setLocationId(itemStock.getItemStockId().getLocationId());
		itemInventory.setNonSellableQty(itemStock.getNonSellableQty());
		itemInventory.setReservedQty(itemStock.getReservedQty());
		itemInventory.setTotalQty(itemStock.getTotalQty());
		itemInventory.setSohQty(itemStock.getStockOnHand());
		logger.info("The item stock details has been trasnformed to item inventory successfully");
		return itemInventory;

	}

	public static InvReasonBean transformStockReason(StockReason stockReason) {
		InvReasonBean invReason = new InvReasonBean();

		invReason.setReasonCodeId(stockReason.getReasonCodeId());
		invReason.setReasonCode(stockReason.getReasonCode());
		invReason.setName(stockReason.getName());

		StockBucket fromBucket = stockReason.getFromBucket();
		StockBucket toBucket = stockReason.getToBucket();
		if (fromBucket != null) {
			invReason.setFromBucketId(stockReason.getFromBucket().getBucketId());
			invReason.setFromBucket(stockReason.getFromBucket().getSystemBucket());
		}
		if (toBucket != null) {
			invReason.setToBucketId(stockReason.getToBucket().getBucketId());
			invReason.setToBucket(stockReason.getToBucket().getSystemBucket());
		}
		logger.info("The stock reason details has been trasnformed to inventory reason successfully");
		return invReason;

	}

	public static InvAdjustBean transformStockAdjustmentDTO(StockAdjustmentDTO stockAdjustmentDTO) {

		StockAdjustment stockAdjustment=stockAdjustmentDTO.getStockAdjustment();
		List<StockAdjustmentItemDTO> stockAdjustmentItemDTOs = stockAdjustmentDTO.getStockAdjustmentItems();
		
		InvAdjustItemBean invAdjustItemBean;
		List<InvAdjustItemBean> invAdjustItemBeanList=new ArrayList<>(stockAdjustmentItemDTOs.size()); 
		for(StockAdjustmentItemDTO stockAdjustmentItemDTO:stockAdjustmentItemDTOs) {
			
			invAdjustItemBean=InventoryBeanTransformer.transformStockAdjustmentItem(stockAdjustmentItemDTO.getStockAdjustmentItem());
			
			invAdjustItemBean.setFromAvailable(stockAdjustmentItemDTO.getFromQty());
			invAdjustItemBean.setToAvailable(stockAdjustmentItemDTO.getToQty());
			invAdjustItemBeanList.add(invAdjustItemBean);
		}

		InvAdjustBean invAdjustBean = InventoryBeanTransformer.transformStockAdjustmentDomain(stockAdjustment);
		invAdjustBean.setInvAdjustItems(invAdjustItemBeanList);
		
		logger.info("The stock adjustment header has been transformed into inventory adjustment successfully");

		return invAdjustBean;
	}

	public static Map<BigInteger, Integer> transformSelectedIds(List<String> invAdjustIds) {
		Map<BigInteger, Integer> idIndex = new HashMap<>(invAdjustIds.size());
		String[] splittedData = null;
		for (String id : invAdjustIds) {
			splittedData = id.split("_");
			idIndex.put(new BigInteger(splittedData[0]), new Integer(splittedData[1]));
		}

		logger.info("The inventory adjustment ids and list index from management page has been seperated");
		return  idIndex;
	}
	
	public static List<StockAdjustment> transformInvAdjustmentList(List<InvAdjustBean> invAdjustBeanList, String username) {
		List<StockAdjustment> stockAdjustmentList = new ArrayList<>(invAdjustBeanList.size());
		StockAdjustment stockAdjustment;
		for (InvAdjustBean invAdjustBean : invAdjustBeanList) {
			stockAdjustment=InventoryBeanTransformer.transformInvAdjustBean(invAdjustBean);
			stockAdjustment.setModifiedDate(LocalDateTime.now());
			stockAdjustment.setModifiedBy(username);
			stockAdjustmentList.add(stockAdjustment);
		}
		logger.info("The inventory adjustment list details has been transformed in Stock Adjustment Domain object list");
		return stockAdjustmentList;
	}
	
	
	
}
