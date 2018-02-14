/**
 * 
 */
package com.punj.app.ecommerce.models.price;

import java.math.BigInteger;
import java.util.List;

import com.punj.app.ecommerce.models.common.LocationBean;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
public class PriceBeanDTO {

	private List<String> itemPriceIds;
	private List<PriceBean> priceBeans;
	private List<LocationBean> locations;
	private BigInteger itemId;
	private Integer locationId;
	private String priceType;
	private Pager pager;

	/**
	 * @return the itemPriceIds
	 */
	public List<String> getItemPriceIds() {
		return itemPriceIds;
	}

	/**
	 * @param itemPriceIds
	 *            the itemPriceIds to set
	 */
	public void setItemPriceIds(List<String> itemPriceIds) {
		this.itemPriceIds = itemPriceIds;
	}

	/**
	 * @return the priceBeans
	 */
	public List<PriceBean> getPriceBeans() {
		return priceBeans;
	}

	/**
	 * @param priceBeans
	 *            the priceBeans to set
	 */
	public void setPriceBeans(List<PriceBean> priceBeans) {
		this.priceBeans = priceBeans;
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

	/**
	 * @return the itemId
	 */
	public BigInteger getItemId() {
		return itemId;
	}

	/**
	 * @param itemId
	 *            the itemId to set
	 */
	public void setItemId(BigInteger itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the locationId
	 */
	public Integer getLocationId() {
		return locationId;
	}

	/**
	 * @param locationId
	 *            the locationId to set
	 */
	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
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

	/**
	 * @return the priceType
	 */
	public String getPriceType() {
		return priceType;
	}

	/**
	 * @param priceType
	 *            the priceType to set
	 */
	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

}
