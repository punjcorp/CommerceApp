/**
 * 
 */
package com.punj.app.ecommerce.services;

import java.math.BigInteger;
import java.util.List;

import com.punj.app.ecommerce.domains.inventory.ItemStock;
import com.punj.app.ecommerce.domains.inventory.ItemStockJournal;
import com.punj.app.ecommerce.domains.inventory.StockAdjustment;
import com.punj.app.ecommerce.domains.inventory.StockAdjustmentDTO;
import com.punj.app.ecommerce.domains.inventory.StockDTO;
import com.punj.app.ecommerce.domains.inventory.StockReason;
import com.punj.app.ecommerce.domains.item.Item;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
public interface InventoryService {

	public void updateInventory(List<ItemStockJournal> itemStockDetails);

	public void rangeSKUs(List<Item> items);

	public List<StockReason> listAllReasonCodes();

	public StockAdjustment createStockAdjustment(StockAdjustment stockAdjustment);

	public List<StockAdjustment> saveStockAdjustmentsDesc(List<StockAdjustment> stockAdjustments);
	
	public List<StockAdjustment> approveStockAdjustments(List<StockAdjustment> stockAdjustments);

	public StockAdjustment approveStockAdjustment(StockAdjustment stockAdjustment);

	public Boolean approveStockAdjustment(BigInteger stockAdjustmentId);

	public Boolean deleteStockAdjustment(BigInteger stockAdjustmentId);

	public Boolean deleteStockAdjustmentItem(BigInteger invAdjustLineItemId);

	public StockAdjustment searchStockAdjustment(BigInteger stockAdjustmentId);

	public StockAdjustmentDTO searchStockAdjustmentWithInventory(BigInteger stockAdjustmentId);

	public void deleteStockAdjustments(List<BigInteger> stockAdjustmentIds);

	public StockDTO listStockAdjustments(StockAdjustment stockAdjustmentCriteria, Pager pager);

	public List<StockAdjustment> listStockAdjustments();

	public StockDTO searchStockAdjustments(String text, Pager pager);

	public ItemStock searchItemStock(BigInteger itemId, Integer locationId);

	public StockReason searchReasonCode(Integer reasonCodeId);

}
