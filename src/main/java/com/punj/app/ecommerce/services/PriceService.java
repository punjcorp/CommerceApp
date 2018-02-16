/**
 * 
 */
package com.punj.app.ecommerce.services;

import java.math.BigInteger;
import java.util.List;

import com.punj.app.ecommerce.domains.price.ItemPrice;
import com.punj.app.ecommerce.domains.price.ItemPriceHistory;

/**
 * @author admin
 *
 */
public interface PriceService {

	public ItemPrice createItemPrice(ItemPrice itemPrice);

	public ItemPrice saveAndApproveItemPrice(ItemPrice itemPrice);

	public List<ItemPrice> searchItemPrice(BigInteger itemId, Integer locationId, String priceType);
	
	public List<ItemPrice> approveItemPriceList(List<ItemPrice> itemPriceList);
	
	public void deleteItemPriceList(List<ItemPrice> itemPriceList);
	
	public List<ItemPrice> saveItemPriceList(List<ItemPrice> itemPriceList);
	
	public Boolean approvePrice(BigInteger itemPriceId, String username);
	
	public Boolean deletePrice(BigInteger itemPriceId, String username);	
	
	public ItemPrice searchPrice(BigInteger itemPriceId);

	public ItemPriceHistory createItemPrice(ItemPriceHistory itemPriceHistory);

}
