package com.punj.app.ecommerce.domains.inventory.ids;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ItemStockBucketId implements Serializable {

	private static final long serialVersionUID = -6208130341400297143L;

	@Column(name = "item_id")
	private BigInteger itemId;

	@Column(name = "location_id")
	private Integer locationId;

	@Column(name = "stock_bucket_id")
	private Integer stockBucketId;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((itemId == null) ? 0 : itemId.hashCode());
		result = prime * result + ((locationId == null) ? 0 : locationId.hashCode());
		result = prime * result + ((stockBucketId == null) ? 0 : stockBucketId.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ItemStockBucketId other = (ItemStockBucketId) obj;
		if (itemId == null) {
			if (other.itemId != null) {
				return false;
			}
		} else if (!itemId.equals(other.itemId)) {
			return false;
		}
		if (locationId == null) {
			if (other.locationId != null) {
				return false;
			}
		} else if (!locationId.equals(other.locationId)) {
			return false;
		}
		if (stockBucketId == null) {
			if (other.stockBucketId != null) {
				return false;
			}
		} else if (!stockBucketId.equals(other.stockBucketId)) {
			return false;
		}
		return true;
	}

}
