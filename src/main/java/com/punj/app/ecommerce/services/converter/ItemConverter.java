/**
 * 
 */
package com.punj.app.ecommerce.services.converter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.punj.app.ecommerce.domains.common.Location;
import com.punj.app.ecommerce.domains.item.Item;
import com.punj.app.ecommerce.domains.price.ItemPrice;
import com.punj.app.ecommerce.services.common.ServiceConstants;

/**
 * @author admin
 *
 */
public class ItemConverter {

	private static final Logger logger = LogManager.getLogger();

	private ItemConverter() {
		throw new IllegalStateException("ItemConverter class");
	}

	public static List<ItemPrice> convertToItemPriceForAll(Item item, String username, List<Location> locations) {

		List<ItemPrice> itemPriceList = new ArrayList<>(locations.size());
		ItemPrice itemPrice = null;
		for (Location location : locations) {
			itemPrice = ItemConverter.convertToItemPrice(item, username, location);
			itemPriceList.add(itemPrice);
		}
		logger.info("The item price list has been created for all locations from item object successfully");
		return itemPriceList;
	}

	public static ItemPrice convertToItemPrice(Item item, String username, Location location) {

		ItemPrice itemPrice = new ItemPrice();

		itemPrice.setCreatedBy(username);
		itemPrice.setCreatedDate(LocalDateTime.now());
		itemPrice.setEndDate(null);
		itemPrice.setItem(item);
		itemPrice.setItemPrice(item.getItemOptions().getCurrentPrice());
		itemPrice.setLocationId(location.getLocationId());
		itemPrice.setStartDate(LocalDateTime.now());
		itemPrice.setStatus(ServiceConstants.STATUS_APPROVED);
		itemPrice.setType(ServiceConstants.PRICE_TYPE_PERMANENT);
		logger.info("The item price has been created from item object successfully");
		return itemPrice;
	}

}
