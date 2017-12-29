/**
 * 
 */
package com.punj.app.ecommerce.models.item;

import java.util.List;

/**
 * @author admin
 *
 */
public class ItemBeanDTO {

	private List<ItemBean> items;
	private List<String> itemIds;

	/**
	 * @return the items
	 */
	public List<ItemBean> getItems() {
		return items;
	}

	/**
	 * @param suppliers
	 *            the suppliers to set
	 */
	public void setItems(List<ItemBean> items) {
		this.items = items;
	}

	/**
	 * @return the itemIds
	 */
	public List<String> getItemIds() {
		return itemIds;
	}

	/**
	 * @param supplierIds
	 *            the supplierIds to set
	 */
	public void setItemIds(List<String> itemIds) {
		this.itemIds = itemIds;
	}

}
