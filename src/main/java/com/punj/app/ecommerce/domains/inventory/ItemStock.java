/**
 * 
 */
package com.punj.app.ecommerce.domains.inventory;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.punj.app.ecommerce.domains.inventory.ids.ItemStockId;

/**
 * @author admin
 *
 */
@Entity
@Table(name = "item_stock")
public class ItemStock implements Serializable {

	private static final long serialVersionUID = 3666796915407718256L;

	@EmbeddedId
	private ItemStockId itemStockId;

	@Column(name = "total_qty")
	private Integer totalQty;
	@Column(name = "non_sellable_qty")
	private Integer nonSellableQty;
	@Column(name = "reserved_qty")
	private Integer reservedQty;
	@Column(name = "stock_on_hand")
	private Integer stockOnHand;

	/**
	 * @return the itemStockId
	 */
	public ItemStockId getItemStockId() {
		return itemStockId;
	}

	/**
	 * @param itemStockId
	 *            the itemStockId to set
	 */
	public void setItemStockId(ItemStockId itemStockId) {
		this.itemStockId = itemStockId;
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
		return nonSellableQty;
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
	 * @return the stockOnHand
	 */
	public Integer getStockOnHand() {
		return stockOnHand;
	}

	/**
	 * @param stockOnHand
	 *            the stockOnHand to set
	 */
	public void setStockOnHand(Integer stockOnHand) {
		this.stockOnHand = stockOnHand;
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
		result = prime * result + ((itemStockId == null) ? 0 : itemStockId.hashCode());
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
		ItemStock other = (ItemStock) obj;
		if (itemStockId == null) {
			if (other.itemStockId != null) {
				return false;
			}
		} else if (!itemStockId.equals(other.itemStockId)) {
			return false;
		}
		return true;
	}

}
