/**
 * 
 */
package com.punj.app.ecommerce.models.order;

import java.util.List;

import com.punj.app.ecommerce.models.common.LocationBean;
import com.punj.app.ecommerce.models.common.SearchBean;

/**
 * @author admin
 *
 */
public class OrderBeanDTO {

	private OrderBean order;
	private SearchBean supplierSearch;
	private SearchBean itemSearch;
	private List<LocationBean> locations;

	/**
	 * @return the order
	 */
	public OrderBean getOrder() {
		return order;
	}

	/**
	 * @param order
	 *            the order to set
	 */
	public void setOrder(OrderBean order) {
		this.order = order;
	}

	/**
	 * @return the supplierSearch
	 */
	public SearchBean getSupplierSearch() {
		return supplierSearch;
	}

	/**
	 * @param supplierSearch
	 *            the supplierSearch to set
	 */
	public void setSupplierSearch(SearchBean supplierSearch) {
		this.supplierSearch = supplierSearch;
	}

	/**
	 * @return the itemSearch
	 */
	public SearchBean getItemSearch() {
		return itemSearch;
	}

	/**
	 * @param itemSearch
	 *            the itemSearch to set
	 */
	public void setItemSearch(SearchBean itemSearch) {
		this.itemSearch = itemSearch;
	}

	/**
	 * @return the locations
	 */
	public List<LocationBean> getLocations() {
		return locations;
	}

	/**
	 * @param locations
	 *            the locations to set
	 */
	public void setLocations(List<LocationBean> locations) {
		this.locations = locations;
	}

}
