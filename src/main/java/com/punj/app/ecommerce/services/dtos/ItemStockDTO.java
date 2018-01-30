/**
 * 
 */
package com.punj.app.ecommerce.services.dtos;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

/**
 * @author admin
 *
 */
public class ItemStockDTO implements Serializable {

	private static final long serialVersionUID = -7494396842129155817L;

	private BigInteger itemId;
	private Integer locationId;
	private Integer stockBucketId;
	private String reasonCode;
	private String functionality;
	private Integer qty;
	private String createdBy;
	private LocalDateTime createdDate;

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
	 * @return the stockBucketId
	 */
	public Integer getStockBucketId() {
		return stockBucketId;
	}

	/**
	 * @param stockBucketId
	 *            the stockBucketId to set
	 */
	public void setStockBucketId(Integer stockBucketId) {
		this.stockBucketId = stockBucketId;
	}

	/**
	 * @return the reasonCode
	 */
	public String getReasonCode() {
		return reasonCode;
	}

	/**
	 * @param reasonCode
	 *            the reasonCode to set
	 */
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	/**
	 * @return the functionality
	 */
	public String getFunctionality() {
		return functionality;
	}

	/**
	 * @param functionality
	 *            the functionality to set
	 */
	public void setFunctionality(String functionality) {
		this.functionality = functionality;
	}

	/**
	 * @return the qty
	 */
	public Integer getQty() {
		return qty;
	}

	/**
	 * @param qty
	 *            the qty to set
	 */
	public void setQty(Integer qty) {
		this.qty = qty;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the createdDate
	 */
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate
	 *            the createdDate to set
	 */
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

}
