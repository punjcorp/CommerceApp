/**
 * 
 */
package com.punj.app.ecommerce.models.inventory;

import java.math.BigInteger;

import javax.validation.constraints.NotNull;

/**
 * @author admin
 *
 */
public class InvAdjustItemBean {
	private BigInteger invAdjustId;
	@NotNull
	private BigInteger itemId;
	@NotNull
	private Integer reasonCodeId;
	@NotNull
	private Integer qty;

	private Integer fromAvailable;
	private Integer toAvailable;

	/**
	 * @return the invAdjustId
	 */
	public BigInteger getInvAdjustId() {
		return invAdjustId;
	}

	/**
	 * @param invAdjustId
	 *            the invAdjustId to set
	 */
	public void setInvAdjustId(BigInteger invAdjustId) {
		this.invAdjustId = invAdjustId;
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
	 * @return the fromAvailable
	 */
	public Integer getFromAvailable() {
		return fromAvailable;
	}

	/**
	 * @param fromAvailable
	 *            the fromAvailable to set
	 */
	public void setFromAvailable(Integer fromAvailable) {
		this.fromAvailable = fromAvailable;
	}

	/**
	 * @return the toAvailable
	 */
	public Integer getToAvailable() {
		return toAvailable;
	}

	/**
	 * @param toAvailable
	 *            the toAvailable to set
	 */
	public void setToAvailable(Integer toAvailable) {
		this.toAvailable = toAvailable;
	}

}
