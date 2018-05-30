/**
 * 
 */
package com.punj.app.ecommerce.models.inventory;

import java.math.BigInteger;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

/**
 * @author admin
 *
 */
public class InvAdjustItemBean {
	private BigInteger invAdjustId;
	private BigInteger invAdjustLineItemId;
	@NotNull(message = "{commerce.error.item.empty}")
	private BigInteger itemId;
	private String itemDesc;
	@NotNull(message = "{commerce.error.select.empty}")
	private Integer reasonCodeId;
	@NotNull(message = "{commerce.error.qty.min.small}")
	@Range(min = 1, max = 9999, message = "{commerce.error.nbr.range}")
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

	/**
	 * @return the invAdjustLineItemId
	 */
	public BigInteger getInvAdjustLineItemId() {
		return invAdjustLineItemId;
	}

	/**
	 * @param invAdjustLineItemId
	 *            the invAdjustLineItemId to set
	 */
	public void setInvAdjustLineItemId(BigInteger invAdjustLineItemId) {
		this.invAdjustLineItemId = invAdjustLineItemId;
	}

	/**
	 * @return the itemDesc
	 */
	public String getItemDesc() {
		return itemDesc;
	}

	/**
	 * @param itemDesc
	 *            the itemDesc to set
	 */
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

}
