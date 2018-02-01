/**
 * 
 */
package com.punj.app.ecommerce.domains.inventory;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Indexed;

import com.punj.app.ecommerce.domains.inventory.ids.StockAdjustmentItemId;

/**
 * @author admin
 *
 */
@Indexed
@Entity
@Table(name = "stock_adjustment_items")
public class StockAdjustmentItem implements Serializable {

	private static final long serialVersionUID = -8507413669072471638L;

	@EmbeddedId
	@FieldBridge(impl = StockAdjustmentItemFieldBridge.class)
	@DocumentId
	private StockAdjustmentItemId stockAdjustmentItemId;

	private Integer qty;

	/**
	 * @return the stockAdjustmentItemId
	 */
	public StockAdjustmentItemId getStockAdjustmentItemId() {
		return stockAdjustmentItemId;
	}

	/**
	 * @param stockAdjustmentItemId
	 *            the stockAdjustmentItemId to set
	 */
	public void setStockAdjustmentItemId(StockAdjustmentItemId stockAdjustmentItemId) {
		this.stockAdjustmentItemId = stockAdjustmentItemId;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((qty == null) ? 0 : qty.hashCode());
		result = prime * result + ((stockAdjustmentItemId == null) ? 0 : stockAdjustmentItemId.hashCode());
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
		if (qty == null) {
			if (other.qty != null) {
				return false;
			}
		} else if (!qty.equals(other.qty)) {
			return false;
		}
		if (stockAdjustmentItemId == null) {
			if (other.stockAdjustmentItemId != null) {
				return false;
			}
		} else if (!stockAdjustmentItemId.equals(other.stockAdjustmentItemId)) {
			return false;
		}
		return true;
	}

}
