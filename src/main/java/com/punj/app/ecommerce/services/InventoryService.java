/**
 * 
 */
package com.punj.app.ecommerce.services;

import java.math.BigInteger;
import java.util.List;

import com.punj.app.ecommerce.domains.inventory.ItemStockJournal;
import com.punj.app.ecommerce.domains.inventory.StockAdjustment;
import com.punj.app.ecommerce.domains.inventory.StockReason;
import com.punj.app.ecommerce.domains.item.Item;

/**
 * @author admin
 *
 */
public interface InventoryService {

	public void updateInventory(List<ItemStockJournal> itemStockDetails);

	public void rangeSKUs(List<Item> items);
	
	public List<StockReason> listAllReasonCodes();

	public BigInteger createStockAdjustment(StockAdjustment stockAdjustment);

	public BigInteger approveStockAdjustment(StockAdjustment stockAdjustment);

	public void deleteStockAdjustments(List<BigInteger> stockAdjustmentIds);

}
