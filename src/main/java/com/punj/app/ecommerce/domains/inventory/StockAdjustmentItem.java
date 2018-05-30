/**
 * 
 */
package com.punj.app.ecommerce.domains.inventory;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

/**
 * @author admin
 *
 */
@Indexed
@Entity
@Table(name = "stock_adjustment_items")
public class StockAdjustmentItem implements Serializable {

	private static final long serialVersionUID = -8507413669072471638L;

	@Id
	@DocumentId
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "stock_adjust_li_id", updatable = false, nullable = false)
	private BigInteger lineItemId;

	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.REFRESH)
	@JoinColumn(name = "stock_adjust_id")
	private StockAdjustment stockAdjustment;

	@Field
	@Column(name = "item_id")
	private BigInteger itemId;
	
	@Column(name = "item_desc")
	private String itemDesc;

	@IndexedEmbedded
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.REFRESH)
	@JoinColumn(name = "reason_code_id")
	private StockReason stockReason;

	private Integer qty;

	/**
	 * @return the lineItemId
	 */
	public BigInteger getLineItemId() {
		return lineItemId;
	}

	/**
	 * @param lineItemId
	 *            the lineItemId to set
	 */
	public void setLineItemId(BigInteger lineItemId) {
		this.lineItemId = lineItemId;
	}

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
	 * @return the stockReason
	 */
	public StockReason getStockReason() {
		return stockReason;
	}

	/**
	 * @param stockReason
	 *            the stockReason to set
	 */
	public void setStockReason(StockReason stockReason) {
		this.stockReason = stockReason;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lineItemId == null) ? 0 : lineItemId.hashCode());
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
		StockAdjustmentItem other = (StockAdjustmentItem) obj;
		if (lineItemId == null) {
			if (other.lineItemId != null) {
				return false;
			}
		} else if (!lineItemId.equals(other.lineItemId)) {
			return false;
		}
		return true;
	}

}
