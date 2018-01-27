/**
 * 
 */
package com.punj.app.ecommerce.domains.inventory;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.punj.app.ecommerce.domains.inventory.ids.ItemStockBucketId;

/**
 * @author admin
 *
 */
@Entity
@Table(name = "item_stock_details")
public class ItemStockBucket implements Serializable {

	private static final long serialVersionUID = -1284770070432595703L;

	@EmbeddedId
	private ItemStockBucketId itemStockBucketId;

	@Column(name = "total_qty")
	private Integer totalQty;

	/**
	 * @return the itemStockBucketId
	 */
	public ItemStockBucketId getItemStockBucketId() {
		return itemStockBucketId;
	}

	/**
	 * @param itemStockBucketId
	 *            the itemStockBucketId to set
	 */
	public void setItemStockBucketId(ItemStockBucketId itemStockBucketId) {
		this.itemStockBucketId = itemStockBucketId;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((itemStockBucketId == null) ? 0 : itemStockBucketId.hashCode());
		result = prime * result + ((totalQty == null) ? 0 : totalQty.hashCode());
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
		ItemStockBucket other = (ItemStockBucket) obj;
		if (itemStockBucketId == null) {
			if (other.itemStockBucketId != null) {
				return false;
			}
		} else if (!itemStockBucketId.equals(other.itemStockBucketId)) {
			return false;
		}
		if (totalQty == null) {
			if (other.totalQty != null) {
				return false;
			}
		} else if (!totalQty.equals(other.totalQty)) {
			return false;
		}
		return true;
	}

}
