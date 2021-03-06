/**
 * 
 */
package com.punj.app.ecommerce.models.order.returns;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author admin
 *
 */
public class OrderReturnItemTaxBean {
	private BigInteger orderReturnItemTaxId;

	private BigInteger orderReturnId;
	private BigInteger itemId;

	private Integer taxGroupId;
	private Integer taxRateRuleId;

	private String taxCode;
	private BigDecimal taxPercentage = BigDecimal.ZERO;
	private BigDecimal taxAmt = BigDecimal.ZERO;
	private BigDecimal taxableAmt = BigDecimal.ZERO;

	/**
	 * @return the orderReturnItemTaxId
	 */
	public BigInteger getOrderReturnItemTaxId() {
		return orderReturnItemTaxId;
	}

	/**
	 * @param orderReturnItemTaxId
	 *            the orderReturnItemTaxId to set
	 */
	public void setOrderReturnItemTaxId(BigInteger orderReturnItemTaxId) {
		this.orderReturnItemTaxId = orderReturnItemTaxId;
	}

	/**
	 * @return the orderReturnId
	 */
	public BigInteger getOrderReturnId() {
		return orderReturnId;
	}

	/**
	 * @param orderReturnId
	 *            the orderReturnId to set
	 */
	public void setOrderReturnId(BigInteger orderReturnId) {
		this.orderReturnId = orderReturnId;
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
	 * @return the taxGroupId
	 */
	public Integer getTaxGroupId() {
		return taxGroupId;
	}

	/**
	 * @param taxGroupId
	 *            the taxGroupId to set
	 */
	public void setTaxGroupId(Integer taxGroupId) {
		this.taxGroupId = taxGroupId;
	}

	/**
	 * @return the taxRateRuleId
	 */
	public Integer getTaxRateRuleId() {
		return taxRateRuleId;
	}

	/**
	 * @param taxRateRuleId
	 *            the taxRateRuleId to set
	 */
	public void setTaxRateRuleId(Integer taxRateRuleId) {
		this.taxRateRuleId = taxRateRuleId;
	}

	/**
	 * @return the taxCode
	 */
	public String getTaxCode() {
		return taxCode;
	}

	/**
	 * @param taxCode
	 *            the taxCode to set
	 */
	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	/**
	 * @return the taxPercentage
	 */
	public BigDecimal getTaxPercentage() {
		return taxPercentage;
	}

	/**
	 * @param taxPercentage
	 *            the taxPercentage to set
	 */
	public void setTaxPercentage(BigDecimal taxPercentage) {
		this.taxPercentage = taxPercentage;
	}

	/**
	 * @return the taxAmt
	 */
	public BigDecimal getTaxAmt() {
		return taxAmt;
	}

	/**
	 * @param taxAmt
	 *            the taxAmt to set
	 */
	public void setTaxAmt(BigDecimal taxAmt) {
		this.taxAmt = taxAmt;
	}

	/**
	 * @return the taxableAmt
	 */
	public BigDecimal getTaxableAmt() {
		return taxableAmt;
	}

	/**
	 * @param taxableAmt
	 *            the taxableAmt to set
	 */
	public void setTaxableAmt(BigDecimal taxableAmt) {
		this.taxableAmt = taxableAmt;
	}

}
