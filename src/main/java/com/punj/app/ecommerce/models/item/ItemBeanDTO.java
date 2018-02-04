/**
 * 
 */
package com.punj.app.ecommerce.models.item;

import java.util.List;

import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
public class ItemBeanDTO {

	private List<ItemBean> items;
	private List<String> itemIds;

	private Pager pager;

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

	/**
	 * @return the pager
	 */
	public Pager getPager() {
		return pager;
	}

	/**
	 * @param pager
	 *            the pager to set
	 */
	public void setPager(Pager pager) {
		this.pager = pager;
	}

}
