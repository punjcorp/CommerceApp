/**
 * 
 */
package com.punj.app.ecommerce.services;

import java.math.BigInteger;
import java.util.List;

import com.punj.app.ecommerce.domains.price.ItemPrice;

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

}
