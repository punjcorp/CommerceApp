/**
 * 
 */
package com.punj.app.ecommerce.services.impl;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.punj.app.ecommerce.domains.common.Location;
import com.punj.app.ecommerce.domains.inventory.ItemStock;
import com.punj.app.ecommerce.domains.inventory.ItemStockBucket;
import com.punj.app.ecommerce.domains.inventory.ItemStockJournal;
import com.punj.app.ecommerce.domains.inventory.StockAdjustment;
import com.punj.app.ecommerce.domains.inventory.StockAdjustmentItem;
import com.punj.app.ecommerce.domains.inventory.StockBucket;
import com.punj.app.ecommerce.domains.inventory.StockDTO;
import com.punj.app.ecommerce.domains.inventory.StockReason;
import com.punj.app.ecommerce.domains.inventory.ids.ItemStockBucketId;
import com.punj.app.ecommerce.domains.inventory.ids.ItemStockId;
import com.punj.app.ecommerce.domains.inventory.ids.StockAdjustmentItemId;
import com.punj.app.ecommerce.domains.item.Item;
import com.punj.app.ecommerce.repositories.common.LocationRepository;
import com.punj.app.ecommerce.repositories.inventory.ItemStockBucketRepository;
import com.punj.app.ecommerce.repositories.inventory.ItemStockJournalRepository;
import com.punj.app.ecommerce.repositories.inventory.ItemStockRepository;
import com.punj.app.ecommerce.repositories.inventory.StockAdjustmentItemRepository;
import com.punj.app.ecommerce.repositories.inventory.StockAdjustmentRepository;
import com.punj.app.ecommerce.repositories.inventory.StockBucketRepository;
import com.punj.app.ecommerce.repositories.inventory.StockReasonRepository;
import com.punj.app.ecommerce.repositories.inventory.StockSearchRepository;
import com.punj.app.ecommerce.services.InventoryService;
import com.punj.app.ecommerce.services.common.ServiceConstants;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
@Service
public class InventoryServiceImpl implements InventoryService {

	private static final Logger logger = LogManager.getLogger();
	private ItemStockJournalRepository itemStockJournalRepository;
	private ItemStockRepository itemStockRepository;
	private ItemStockBucketRepository itemStockBucketRepository;
	private StockReasonRepository stockReasonRepository;
	private StockBucketRepository stockBucketRepository;
	private StockAdjustmentRepository stockAdjustmentRepository;
	private StockAdjustmentItemRepository stockAdjustmentItemRepository;
	private LocationRepository locationRepository;
	private StockSearchRepository stockSearchRepository;

	@Value("${commerce.list.max.perpage}")
	private Integer maxResultPerPage;

	@Value("${commerce.list.max.pageno}")
	private Integer maxPageBtns;

	/**
	 * @return the stockSearchRepository
	 */
	public StockSearchRepository getStockSearchRepository() {
		return stockSearchRepository;
	}

	/**
	 * @param stockSearchRepository
	 *            the stockSearchRepository to set
	 */
	@Autowired
	public void setStockSearchRepository(StockSearchRepository stockSearchRepository) {
		this.stockSearchRepository = stockSearchRepository;
	}

	/**
	 * @return the stockAdjustmentRepository
	 */
	public StockAdjustmentRepository getStockAdjustmentRepository() {
		return stockAdjustmentRepository;
	}

	/**
	 * @param stockAdjustmentRepository
	 *            the stockAdjustmentRepository to set
	 */
	@Autowired
	public void setStockAdjustmentRepository(StockAdjustmentRepository stockAdjustmentRepository) {
		this.stockAdjustmentRepository = stockAdjustmentRepository;
	}

	/**
	 * @return the stockAdjustmentItemRepository
	 */
	public StockAdjustmentItemRepository getStockAdjustmentItemRepository() {
		return stockAdjustmentItemRepository;
	}

	/**
	 * @param stockAdjustmentItemRepository
	 *            the stockAdjustmentItemRepository to set
	 */
	@Autowired
	public void setStockAdjustmentItemRepository(StockAdjustmentItemRepository stockAdjustmentItemRepository) {
		this.stockAdjustmentItemRepository = stockAdjustmentItemRepository;
	}

	/**
	 * @return the stockBucketRepository
	 */
	public StockBucketRepository getStockBucketRepository() {
		return stockBucketRepository;
	}

	/**
	 * @param stockBucketRepository
	 *            the stockBucketRepository to set
	 */
	@Autowired
	public void setStockBucketRepository(StockBucketRepository stockBucketRepository) {
		this.stockBucketRepository = stockBucketRepository;
	}

	/**
	 * @return the itemStockJournalRepository
	 */
	public ItemStockJournalRepository getItemStockJournalRepository() {
		return itemStockJournalRepository;
	}

	/**
	 * @param itemStockJournalRepository
	 *            the itemStockJournalRepository to set
	 */
	@Autowired
	public void setItemStockJournalRepository(ItemStockJournalRepository itemStockJournalRepository) {
		this.itemStockJournalRepository = itemStockJournalRepository;
	}

	/**
	 * @return the itemStockBucketRepository
	 */
	public ItemStockBucketRepository getItemStockBucketRepository() {
		return itemStockBucketRepository;
	}

	/**
	 * @param itemStockBucketRepository
	 *            the itemStockBucketRepository to set
	 */
	@Autowired
	public void setItemStockBucketRepository(ItemStockBucketRepository itemStockBucketRepository) {
		this.itemStockBucketRepository = itemStockBucketRepository;
	}

	/**
	 * @return the itemStockRepository
	 */
	public ItemStockRepository getItemStockRepository() {
		return itemStockRepository;
	}

	/**
	 * @param itemStockRepository
	 *            the itemStockRepository to set
	 */
	@Autowired
	public void setItemStockRepository(ItemStockRepository itemStockRepository) {
		this.itemStockRepository = itemStockRepository;
	}

	/**
	 * @return the stockReasonRepository
	 */
	public StockReasonRepository getStockReasonRepository() {
		return stockReasonRepository;
	}

	/**
	 * @param stockReasonRepository
	 *            the stockReasonRepository to set
	 */
	@Autowired
	public void setStockReasonRepository(StockReasonRepository stockReasonRepository) {
		this.stockReasonRepository = stockReasonRepository;
	}

	/**
	 * @return the locationRepository
	 */
	public LocationRepository getLocationRepository() {
		return locationRepository;
	}

	/**
	 * @param locationRepository
	 *            the locationRepository to set
	 */
	@Autowired
	public void setLocationRepository(LocationRepository locationRepository) {
		this.locationRepository = locationRepository;
	}

	@Override
	@Transactional
	public void updateInventory(List<ItemStockJournal> itemStockDetails) {
		StockReason stockReason = null;

		for (ItemStockJournal itemStockJournal : itemStockDetails) {

			stockReason = itemStockJournal.getReasonCode();
			stockReason = this.stockReasonRepository.findOne(Example.of(stockReason));

			itemStockJournal.setReasonCode(stockReason);

			itemStockJournal = this.itemStockJournalRepository.save(itemStockJournal);

			this.updateItemStockAndBuckets(itemStockJournal);

		}

		logger.info("The inventory has been updated for the items successfully.");

	}

	private void updateItemStockAndBuckets(ItemStockJournal itemStockJournal) {

		ItemStockId itemStockId = new ItemStockId();
		Item item=new Item();
		item.setItemId(itemStockJournal.getItemId());
		itemStockId.setItem(item);
		itemStockId.setLocationId(itemStockJournal.getLocationId());

		ItemStock itemStock = this.itemStockRepository.findOne(itemStockId);

		StockReason reason = itemStockJournal.getReasonCode();
		StockBucket toStockBucket = reason.getToBucket();
		StockBucket fromStockBucket = reason.getFromBucket();
		String action = null;

		if (toStockBucket != null) {
			action = reason.getToAction();
			this.calculateInventory(itemStockJournal, toStockBucket, fromStockBucket, action, itemStock);
		}

		if (fromStockBucket != null) {
			action = reason.getFromAction();
			this.calculateInventory(itemStockJournal, fromStockBucket, toStockBucket, action, itemStock);
		}

		this.itemStockRepository.save(itemStock);
		logger.info("The item {} stock on hand has been updated successfully", itemStockId.getItem().getItemId());

		logger.info("The item stock and bucket details has been updated based in the item stock journal successfully.");
	}

	private void incrementBucketQty(ItemStockBucketId itemStockBucketId, Integer qty) {
		ItemStockBucket itemStockBucket = this.itemStockBucketRepository.findOne(itemStockBucketId);
		itemStockBucket.setTotalQty(itemStockBucket.getTotalQty() + qty);
		this.itemStockBucketRepository.save(itemStockBucket);

		logger.info("The item {} stock bucket has been increased successfully", itemStockBucketId.getStockBucketId());
	}

	private void decrementBucketQty(ItemStockBucketId itemStockBucketId, Integer qty) {
		ItemStockBucket itemStockBucket = this.itemStockBucketRepository.findOne(itemStockBucketId);
		itemStockBucket.setTotalQty(itemStockBucket.getTotalQty() - qty);
		this.itemStockBucketRepository.save(itemStockBucket);

		logger.info("The item {} stock bucket has been decreased successfully", itemStockBucketId.getStockBucketId());
	}

	private void incrementItemStock(StockBucket stockBucket, ItemStock itemStock, Integer qty) {
		String invBucket = stockBucket.getSystemBucket();
		if (StringUtils.isNotEmpty(invBucket)) {
			if (invBucket.equals(ServiceConstants.INV_BUCKET_NON_SELL)) {
				itemStock.setNonSellableQty(itemStock.getNonSellableQty() + qty);
			} else if (invBucket.equals(ServiceConstants.INV_BUCKET_RESERVED)) {
				itemStock.setReservedQty(itemStock.getReservedQty() + qty);
			} else if (invBucket.equals(ServiceConstants.INV_BUCKET_SOH)) {
				itemStock.setStockOnHand(itemStock.getStockOnHand() + qty);
			}
		}
		logger.info("The {} stock bucket has been incremented with {} in item stock object", invBucket, qty);

	}

	private void decrementItemStock(StockBucket stockBucket, ItemStock itemStock, Integer qty) {
		String invBucket = stockBucket.getSystemBucket();
		if (StringUtils.isNotEmpty(invBucket)) {
			if (invBucket.equals(ServiceConstants.INV_BUCKET_NON_SELL)) {
				itemStock.setNonSellableQty(itemStock.getNonSellableQty() - qty);
			} else if (invBucket.equals(ServiceConstants.INV_BUCKET_RESERVED)) {
				itemStock.setReservedQty(itemStock.getReservedQty() - qty);
			} else if (invBucket.equals(ServiceConstants.INV_BUCKET_SOH)) {
				itemStock.setStockOnHand(itemStock.getStockOnHand() - qty);
			}
		}
		logger.info("The {} stock bucket has been incremented with {} in item stock object", invBucket, qty);

	}

	private void calculateInventory(ItemStockJournal itemStockJournal, StockBucket stockBucket,
			StockBucket otherStockBucket, String action, ItemStock itemStock) {
		Integer bucketId = stockBucket.getBucketId();

		ItemStockBucketId itemStockBucketId = new ItemStockBucketId();
		itemStockBucketId.setItemId(itemStockJournal.getItemId());
		itemStockBucketId.setLocationId(itemStockJournal.getLocationId());
		itemStockBucketId.setStockBucketId(bucketId);

		Integer qty = itemStockJournal.getQty();

		if (StringUtils.isNotEmpty(action) && action.equals(ServiceConstants.INV_ACTION_ADD)) {
			this.incrementBucketQty(itemStockBucketId, qty);
			this.incrementItemStock(stockBucket, itemStock, qty);
			if (otherStockBucket == null) {
				itemStock.setTotalQty(itemStock.getTotalQty() + qty);
			}
		} else if (StringUtils.isNotEmpty(action) && action.equals(ServiceConstants.INV_ACTION_SUBSTRACT)) {
			this.decrementBucketQty(itemStockBucketId, qty);
			this.decrementItemStock(stockBucket, itemStock, qty);
			if (otherStockBucket == null) {
				itemStock.setTotalQty(itemStock.getTotalQty() - qty);
			}
		}

		logger.info("The stock bucket {} for system bucket {} has been updated successfully", stockBucket.getName(),
				stockBucket.getSystemBucket());
	}

	@Override
	public void rangeSKUs(List<Item> items) {

		List<Location> locations = this.locationRepository.findAll();

		List<StockBucket> stockBuckets = this.stockBucketRepository.findAll();

		ItemStock itemStock = null;
		ItemStockId itemStockId = null;
		ItemStockBucket itemStockBucket = null;
		ItemStockBucketId itemStockBucketId = null;
		Integer locationId = null;

		List<ItemStockBucket> itemStockBucketList = new ArrayList<>();
		List<ItemStock> itemStockList = new ArrayList<>();

		for (Item item : items) {

			for (Location location : locations) {

				locationId = location.getLocationId();

				itemStock = new ItemStock();

				itemStockId = new ItemStockId();
				itemStockId.setItem(item);
				itemStockId.setLocationId(locationId);

				itemStock.setItemStockId(itemStockId);

				itemStockList.add(itemStock);

				for (StockBucket stockBucket : stockBuckets) {
					itemStockBucketId = new ItemStockBucketId();
					itemStockBucketId.setItemId(item.getItemId());
					itemStockBucketId.setLocationId(locationId);
					itemStockBucketId.setStockBucketId(stockBucket.getBucketId());

					itemStockBucket = new ItemStockBucket();
					itemStockBucket.setItemStockBucketId(itemStockBucketId);

					itemStockBucketList.add(itemStockBucket);
				}

			}

		}
		itemStockList = this.itemStockRepository.save(itemStockList);
		logger.info("The item stock records has been created for all {} SKUs.", itemStockList.size());

		itemStockBucketList = this.itemStockBucketRepository.save(itemStockBucketList);
		logger.info("The item stock bucket records has been created for all {} SKU.", itemStockBucketList.size());

		logger.info("The new SKUS has been ranged for all the locations successfully");
	}

	@Override
	public BigInteger createStockAdjustment(StockAdjustment stockAdjustment) {
		stockAdjustment = this.stockAdjustmentRepository.save(stockAdjustment);
		logger.info("The stock adjustment was created successfully");

		BigInteger stockAdjustmentId = null;
		if (stockAdjustment != null) {
			stockAdjustmentId = stockAdjustment.getStockAdjustId();
			logger.debug("The stock adjustment id is -> {} <-", stockAdjustmentId);
		}
		return stockAdjustmentId;
	}

	@Override
	public void deleteStockAdjustments(List<BigInteger> stockAdjustmentIds) {

		for (BigInteger stockAdjustmentId : stockAdjustmentIds) {
			this.stockAdjustmentRepository.delete(stockAdjustmentId);
			logger.debug("The stock adjustment id -> {} <- was deleted", stockAdjustmentId);
		}
		logger.info("All the provided stock adjustments were deleted successfully");
	}

	@Override
	public List<StockReason> listAllReasonCodes() {
		logger.info("The request for Stock Reason codes has been sent");
		return this.stockReasonRepository.findAll();
	}

	@Override
	@Transactional
	public BigInteger approveStockAdjustment(StockAdjustment stockAdjustment) {
		stockAdjustment = this.stockAdjustmentRepository.save(stockAdjustment);
		logger.info("The stock adjustment was approved successfully");

		BigInteger stockAdjustmentId = null;
		if (stockAdjustment != null) {
			stockAdjustmentId = stockAdjustment.getStockAdjustId();
			logger.debug("The stock adjustment id is -> {} <-", stockAdjustmentId);

			List<ItemStockJournal> inventoryDetails = this.createStockDetails(stockAdjustment,
					stockAdjustment.getCreatedBy());
			this.updateInventory(inventoryDetails);

		}
		return stockAdjustmentId;
	}

	private List<ItemStockJournal> createStockDetails(StockAdjustment stockAdjustment, String username) {

		List<StockAdjustmentItem> stockAdjustmentItems = stockAdjustment.getStockAdjustItems();
		List<ItemStockJournal> itemStockDetails = new ArrayList<>(stockAdjustmentItems.size());
		ItemStockJournal itemStockJournal = null;
		Item item = null;

		for (StockAdjustmentItem stockAdjustmentItem : stockAdjustmentItems) {
			itemStockJournal = new ItemStockJournal();
			itemStockJournal.setCreatedBy(username);
			itemStockJournal.setCreatedDate(LocalDateTime.now());

			itemStockJournal.setLocationId(stockAdjustment.getLocationId());

			item = new Item();
			item.setItemId(stockAdjustmentItem.getStockAdjustmentItemId().getItemId());
			itemStockJournal.setItemId(item.getItemId());

			StockReason stockReason = new StockReason();
			stockReason.setReasonCodeId(stockAdjustmentItem.getStockAdjustmentItemId().getReasonCodeId());
			itemStockJournal.setReasonCode(stockReason);

			itemStockJournal.setFunctionality(ServiceConstants.STOCK_ADJUSTMENT_FUNCTIONALITY);
			itemStockJournal.setQty(stockAdjustmentItem.getQty());

			itemStockDetails.add(itemStockJournal);
		}

		logger.info("The stock details has been created for Inventory Adjustment");
		return itemStockDetails;

	}

	@Override
	public StockDTO listStockAdjustments(StockAdjustment stockAdjustmentCriteria, Pager pager) {
		StockDTO stockDTO = null;
		try {

			int startCount = (pager.getCurrentPageNo() - 1) * maxResultPerPage;
			pager.setPageSize(maxResultPerPage);
			pager.setStartCount(startCount);
			pager.setMaxDisplayPage(maxPageBtns);

			Pageable pageable = new PageRequest(pager.getCurrentPageNo() - 1, maxResultPerPage);

			Page<StockAdjustment> stockAdjustmentsPage = this.stockAdjustmentRepository.findAll(pageable);
			logger.info("The searched stock adjustments has been retrieved successfully from database");

			List<StockAdjustment> stockAdjustments = stockAdjustmentsPage.getContent();

			stockDTO = new StockDTO();
			stockDTO.setStockAdjustments(stockAdjustments);

			pager.setResultSize((int) stockAdjustmentsPage.getTotalElements());

			stockDTO.setPager(pager);

			logger.info("The Stock Adjustments and pager has been set in DTO object successfully");
		} catch (Exception e) {
			logger.error("There was an error while searching for all the Stock Adjustments", e);
		}
		return stockDTO;
	}

	@Override
	public StockDTO searchStockAdjustments(String text, Pager pager) {

		int startCount = (pager.getCurrentPageNo() - 1) * maxResultPerPage;
		pager.setPageSize(maxResultPerPage);
		pager.setStartCount(startCount);
		pager.setMaxDisplayPage(maxPageBtns);

		StockDTO stockDTO = this.stockSearchRepository.search(text, pager);
		logger.info("The search Stock Adjustments has been set in DTO object successfully");

		return stockDTO;
	}

	@Override
	public Boolean approveStockAdjustment(BigInteger stockAdjustmentId) {
		Boolean result = Boolean.FALSE;
		StockAdjustment stockAdjustment = this.stockAdjustmentRepository.findOne(stockAdjustmentId);
		if (stockAdjustment != null && !StringUtils.isEmpty(stockAdjustment.getStatus())
				&& stockAdjustment.getStatus().equals(ServiceConstants.STOCK_ADJUSTMENT_STATUS_CREATED)) {
			stockAdjustment.setStatus(ServiceConstants.STOCK_ADJUSTMENT_STATUS_APPROVED);
			stockAdjustment = this.stockAdjustmentRepository.save(stockAdjustment);
			if (stockAdjustment != null) {
				result = Boolean.TRUE;
				logger.info("The Stock Adjustment has been approved successfully");
			}
		}
		return result;
	}

	@Override
	public Boolean deleteStockAdjustment(BigInteger stockAdjustmentId) {
		Boolean result = Boolean.FALSE;
		StockAdjustment stockAdjustment = this.stockAdjustmentRepository.findOne(stockAdjustmentId);
		if (stockAdjustment != null && !StringUtils.isEmpty(stockAdjustment.getStatus())
				&& stockAdjustment.getStatus().equals(ServiceConstants.STOCK_ADJUSTMENT_STATUS_CREATED)) {
			this.stockAdjustmentRepository.delete(stockAdjustmentId);
			result = Boolean.TRUE;
			logger.info("The Stock Adjustment has been deleted successfully");
		}
		if (!result)
			logger.info("The Stock Adjustment was not deleted due to some pre defined condition.");
		return result;
	}

	@Override
	public StockAdjustment editStockAdjustment(BigInteger stockAdjustmentId) {
		StockAdjustment stockAdjustment = this.stockAdjustmentRepository.findOne(stockAdjustmentId);
		if (stockAdjustment != null
				&& stockAdjustment.getStatus().equals(ServiceConstants.STOCK_ADJUSTMENT_STATUS_CREATED)) {
			logger.info("The Stock Adjustment has been retrieved successfully for editing");
		} else {
			stockAdjustment = null;
			logger.info("The Stock Adjustment cannot be retrieved for editing");
		}
		return stockAdjustment;
	}

	@Override
	public Boolean deleteStockAdjustmentItem(BigInteger stockAdjustmentId, BigInteger itemId, Integer reasonCodeId) {

		StockAdjustment stockAdjustment = new StockAdjustment();
		stockAdjustment.setStockAdjustId(stockAdjustmentId);

		StockAdjustmentItemId stockAdjustmentItemId = new StockAdjustmentItemId();
		stockAdjustmentItemId.setStockAdjustment(stockAdjustment);
		stockAdjustmentItemId.setItemId(itemId);
		stockAdjustmentItemId.setReasonCodeId(reasonCodeId);
		
		this.stockAdjustmentItemRepository.delete(stockAdjustmentItemId);
		logger.info("The Stock Adjustment Item has been deleted successfully");
		return Boolean.TRUE;
	}

}
