/**
 * 
 */
package com.punj.app.ecommerce.domains.inventory;

import java.io.Serializable;
import java.util.List;

/**
 * @author admin
 *
 */
public class StockAdjustmentDTO implements Serializable {

	private static final long serialVersionUID = -1460880466805120039L;
	private StockAdjustment stockAdjustment;
	private List<StockAdjustmentItemDTO> stockAdjustmentItems;

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
	 * @return the stockAdjustmentItems
	 */
	public List<StockAdjustmentItemDTO> getStockAdjustmentItems() {
		return stockAdjustmentItems;
	}

	/**
	 * @param stockAdjustmentItems
	 *            the stockAdjustmentItems to set
	 */
	public void setStockAdjustmentItems(List<StockAdjustmentItemDTO> stockAdjustmentItems) {
		this.stockAdjustmentItems = stockAdjustmentItems;
	}

}
