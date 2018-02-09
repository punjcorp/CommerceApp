/**
 * 
 */
package com.punj.app.ecommerce.domains.inventory;

import java.io.Serializable;
import java.util.List;

import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
public class StockDTO implements Serializable {

	private static final long serialVersionUID = -4166478633919243583L;
	private List<StockAdjustment> stockAdjustments;
	private List<StockAdjustmentItemDTO> stockAdjustmentItemDTOs;

	private Pager pager;

	/**
	 * @return the stockAdjustments
	 */
	public List<StockAdjustment> getStockAdjustments() {
		return stockAdjustments;
	}

	/**
	 * @param stockAdjustments
	 *            the stockAdjustments to set
	 */
	public void setStockAdjustments(List<StockAdjustment> stockAdjustments) {
		this.stockAdjustments = stockAdjustments;
	}

	/**
	 * @return the pager
	 */
	public Pager getPager() {
		return pager;
	}

	/**
	 * @param pager
	 *            the pager to set
	 */
	public void setPager(Pager pager) {
		this.pager = pager;
	}

	/**
	 * @return the stockAdjustmentItemDTOs
	 */
	public List<StockAdjustmentItemDTO> getStockAdjustmentItemDTOs() {
		return stockAdjustmentItemDTOs;
	}

	/**
	 * @param stockAdjustmentItemDTOs
	 *            the stockAdjustmentItemDTOs to set
	 */
	public void setStockAdjustmentItemDTOs(List<StockAdjustmentItemDTO> stockAdjustmentItemDTOs) {
		this.stockAdjustmentItemDTOs = stockAdjustmentItemDTOs;
	}

}
