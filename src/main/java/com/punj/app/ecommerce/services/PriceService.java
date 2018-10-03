/**
 * 
 */
package com.punj.app.ecommerce.services;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import com.punj.app.ecommerce.domains.price.ItemPrice;
import com.punj.app.ecommerce.domains.price.ItemPriceDTO;
import com.punj.app.ecommerce.domains.price.ItemPriceHistory;

/**
 * @author admin
 *
 */
public interface PriceService {

	public ItemPrice createItemPrice(ItemPrice itemPrice);

	public ItemPrice saveAndApproveItemPrice(ItemPrice itemPrice);
	
	public ItemPrice getOldestClearance(BigInteger itemId, Integer locationId);
	
	public ItemPrice createClearanceReset(ItemPrice itemPrice, String username);
	
	public ItemPrice approveClearanceReset(ItemPrice itemPrice, String username);
	
	public ItemPriceDTO searchItemPrice(BigInteger itemId, Integer locationId, String priceType, Integer page);

	public List<ItemPrice> approveItemPriceList(List<ItemPrice> itemPriceList);

	public void deleteItemPriceList(List<ItemPrice> itemPriceList);

	public List<ItemPrice> saveItemPriceList(List<ItemPrice> itemPriceList);

	public Boolean approvePrice(BigInteger itemPriceId, String username);

	public Boolean deletePrice(BigInteger itemPriceId, String username);

	public ItemPrice searchPrice(BigInteger itemPriceId);

	public ItemPriceHistory createItemPrice(ItemPriceHistory itemPriceHistory);

	public ItemPrice getCurrentItemPrice(BigInteger itemId, Integer locationId, LocalDateTime currentDate);
	
	public List<ItemPrice> getFutureItemPrices(BigInteger itemId, Integer locationId, LocalDateTime businessDate);

	public List<ItemPrice> retrieveItemPrice(BigInteger itemId, Integer locationId);
	
	public List<ItemPrice> retrieveAllItemPrices(BigInteger itemId);
	
}
