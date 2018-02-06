/**
 * 
 */
package com.punj.app.ecommerce.models.inventory;

/**
 * @author admin
 *
 */
public class InvAdjustItemInventory {
	private ItemInventory itemInventory;
	private InvReasonBean itemReasonBean;
	private Integer fromBucketQty;
	private Integer toBucketQty;

	/**
	 * @return the itemInventory
	 */
	public ItemInventory getItemInventory() {
		return itemInventory;
	}

	/**
	 * @param itemInventory
	 *            the itemInventory to set
	 */
	public void setItemInventory(ItemInventory itemInventory) {
		this.itemInventory = itemInventory;
	}

	/**
	 * @return the itemReasonBean
	 */
	public InvReasonBean getItemReasonBean() {
		return itemReasonBean;
	}

	/**
	 * @param itemReasonBean
	 *            the itemReasonBean to set
	 */
	public void setItemReasonBean(InvReasonBean itemReasonBean) {
		this.itemReasonBean = itemReasonBean;
	}

	/**
	 * @return the fromBucketQty
	 */
	public Integer getFromBucketQty() {
		return fromBucketQty;
	}

	/**
	 * @param fromBucketQty
	 *            the fromBucketQty to set
	 */
	public void setFromBucketQty(Integer fromBucketQty) {
		this.fromBucketQty = fromBucketQty;
	}

	/**
	 * @return the toBucketQty
	 */
	public Integer getToBucketQty() {
		return toBucketQty;
	}

	/**
	 * @param toBucketQty
	 *            the toBucketQty to set
	 */
	public void setToBucketQty(Integer toBucketQty) {
		this.toBucketQty = toBucketQty;
	}

}
