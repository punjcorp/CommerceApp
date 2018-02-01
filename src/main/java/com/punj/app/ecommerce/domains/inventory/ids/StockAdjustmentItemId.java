package com.punj.app.ecommerce.domains.inventory.ids;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import com.punj.app.ecommerce.domains.inventory.StockAdjustment;

@Indexed
@Embeddable
public class StockAdjustmentItemId implements Serializable {

	private static final long serialVersionUID = -7874786414845443944L;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "stock_adjust_id")
	private StockAdjustment stockAdjustment;

	@Field
	@Column(name = "item_id")
	private BigInteger itemId;

	@Field
	@Column(name = "reason_code_id")
	private Integer reasonCodeId;

	/**
	 * @return the stockAdjustment
	 */
	public StockAdjustment getStockAdjustment() {
		return stockAdjustment;
	}

	/**
	 * @param stockAdjustment
	 *            the stockAdjustment to set
	 */
	public void setStockAdjustment(StockAdjustment stockAdjustment) {
		this.stockAdjustment = stockAdjustment;
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
	 * @return the reasonCodeId
	 */
	public Integer getReasonCodeId() {
		return reasonCodeId;
	}

	/**
	 * @param reasonCodeId
	 *            the reasonCodeId to set
	 */
	public void setReasonCodeId(Integer reasonCodeId) {
		this.reasonCodeId = reasonCodeId;
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
		result = prime * result + ((reasonCodeId == null) ? 0 : reasonCodeId.hashCode());
		result = prime * result + ((stockAdjustment == null) ? 0 : stockAdjustment.hashCode());
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
		StockAdjustmentItemId other = (StockAdjustmentItemId) obj;
		if (itemId == null) {
			if (other.itemId != null) {
				return false;
			}
		} else if (!itemId.equals(other.itemId)) {
			return false;
		}
		if (reasonCodeId == null) {
			if (other.reasonCodeId != null) {
				return false;
			}
		} else if (!reasonCodeId.equals(other.reasonCodeId)) {
			return false;
		}
		if (stockAdjustment == null) {
			if (other.stockAdjustment != null) {
				return false;
			}
		} else if (!stockAdjustment.equals(other.stockAdjustment)) {
			return false;
		}
		return true;
	}

}
