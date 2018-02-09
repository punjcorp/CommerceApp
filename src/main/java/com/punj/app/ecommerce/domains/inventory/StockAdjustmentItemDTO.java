/**
 * 
 */
package com.punj.app.ecommerce.domains.inventory;

import java.io.Serializable;

/**
 * @author admin
 *
 */
public class StockAdjustmentItemDTO implements Serializable {

	private static final long serialVersionUID = 8417287003132034202L;
	private StockAdjustmentItem stockAdjustmentItem;
	private String fromBucket;
	private String toBucket;
	private Integer fromQty;
	private Integer toQty;

	/**
	 * @return the stockAdjustmentItem
	 */
	public StockAdjustmentItem getStockAdjustmentItem() {
		return stockAdjustmentItem;
	}

	/**
	 * @param stockAdjustmentItem
	 *            the stockAdjustmentItem to set
	 */
	public void setStockAdjustmentItem(StockAdjustmentItem stockAdjustmentItem) {
		this.stockAdjustmentItem = stockAdjustmentItem;
	}

	/**
	 * @return the fromBucket
	 */
	public String getFromBucket() {
		return fromBucket;
	}

	/**
	 * @param fromBucket
	 *            the fromBucket to set
	 */
	public void setFromBucket(String fromBucket) {
		this.fromBucket = fromBucket;
	}

	/**
	 * @return the toBucket
	 */
	public String getToBucket() {
		return toBucket;
	}

	/**
	 * @param toBucket
	 *            the toBucket to set
	 */
	public void setToBucket(String toBucket) {
		this.toBucket = toBucket;
	}

	/**
	 * @return the fromQty
	 */
	public Integer getFromQty() {
		return fromQty;
	}

	/**
	 * @param fromQty
	 *            the fromQty to set
	 */
	public void setFromQty(Integer fromQty) {
		this.fromQty = fromQty;
	}

	/**
	 * @return the toQty
	 */
	public Integer getToQty() {
		return toQty;
	}

	/**
	 * @param toQty
	 *            the toQty to set
	 */
	public void setToQty(Integer toQty) {
		this.toQty = toQty;
	}

}
