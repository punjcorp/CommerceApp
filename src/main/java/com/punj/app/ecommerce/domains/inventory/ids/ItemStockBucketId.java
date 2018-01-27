package com.punj.app.ecommerce.domains.inventory.ids;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.punj.app.ecommerce.domains.common.Location;
import com.punj.app.ecommerce.domains.inventory.StockBucket;
import com.punj.app.ecommerce.domains.item.Item;

@Embeddable
public class ItemStockBucketId implements Serializable {

	private static final long serialVersionUID = -6208130341400297143L;

	@OneToOne
	@JoinColumn(name = "item_id")
	private Item item;

	@ManyToOne
	@JoinColumn(name = "location_id")
	private Location location;

	@ManyToOne
	@JoinColumn(name = "stock_bucket_id")
	private StockBucket stockBucket;

	/**
	 * @return the item
	 */
	public Item getItem() {
		return item;
	}

	/**
	 * @param item
	 *            the item to set
	 */
	public void setItem(Item item) {
		this.item = item;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * @return the stockBucket
	 */
	public StockBucket getStockBucket() {
		return stockBucket;
	}

	/**
	 * @param stockBucket
	 *            the stockBucket to set
	 */
	public void setStockBucket(StockBucket stockBucket) {
		this.stockBucket = stockBucket;
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
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((stockBucket == null) ? 0 : stockBucket.hashCode());
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
		if (item == null) {
			if (other.item != null) {
				return false;
			}
		} else if (!item.equals(other.item)) {
			return false;
		}
		if (location == null) {
			if (other.location != null) {
				return false;
			}
		} else if (!location.equals(other.location)) {
			return false;
		}
		if (stockBucket == null) {
			if (other.stockBucket != null) {
				return false;
			}
		} else if (!stockBucket.equals(other.stockBucket)) {
			return false;
		}
		return true;
	}

}
