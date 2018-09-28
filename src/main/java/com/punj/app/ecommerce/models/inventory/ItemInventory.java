/**
 * 
 */
package com.punj.app.ecommerce.models.inventory;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @author admin
 *
 */
public class ItemInventory implements Serializable {

	private static final long serialVersionUID = 4775903741736028657L;
	
	private BigInteger itemId;
	private String itemDesc;
	private Integer locationId;
	private String locationName;
	private Integer totalQty;
	private Integer nonSellableQty;
	private Integer reservedQty;
	private Integer sohQty;

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
	 * @return the totalQty
	 */
	public Integer getTotalQty() {
		return totalQty;
	}

	/**
	 * @param totalQty
	 *            the totalQty to set
	 */
	public void setTotalQty(Integer totalQty) {
		this.totalQty = totalQty;
	}

	/**
	 * @return the nonSellableQty
	 */
	public Integer getNonSellableQty() {
		return this.nonSellableQty;
	}

	/**
	 * @param nonSellableQty
	 *            the nonSellableQty to set
	 */
	public void setNonSellableQty(Integer nonSellableQty) {
		this.nonSellableQty = nonSellableQty;
	}

	/**
	 * @return the reservedQty
	 */
	public Integer getReservedQty() {
		return reservedQty;
	}

	/**
	 * @param reservedQty
	 *            the reservedQty to set
	 */
	public void setReservedQty(Integer reservedQty) {
		this.reservedQty = reservedQty;
	}

	/**
	 * @return the sohQty
	 */
	public Integer getSohQty() {
		return sohQty;
	}

	/**
	 * @param sohQty
	 *            the sohQty to set
	 */
	public void setSohQty(Integer sohQty) {
		this.sohQty = sohQty;
	}

	/**
	 * @return the itemDesc
	 */
	public String getItemDesc() {
		return itemDesc;
	}

	/**
	 * @param itemDesc the itemDesc to set
	 */
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	/**
	 * @return the locationName
	 */
	public String getLocationName() {
		return locationName;
	}

	/**
	 * @param locationName the locationName to set
	 */
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

}
