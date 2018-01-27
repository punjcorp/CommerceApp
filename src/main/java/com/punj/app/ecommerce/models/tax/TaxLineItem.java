/**
 * 
 */
package com.punj.app.ecommerce.models.tax;

import java.math.BigDecimal;

import javax.money.MonetaryAmount;

/**
 * @author admin
 *
 */
public class TaxLineItem {

	private String taxCode;
	private String taxDescription;
	private MonetaryAmount taxAmount;
	private BigDecimal percentage;
	private MonetaryAmount taxOnItem;

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
	 * @return the taxDescription
	 */
	public String getTaxDescription() {
		return taxDescription;
	}

	/**
	 * @param taxDescription
	 *            the taxDescription to set
	 */
	public void setTaxDescription(String taxDescription) {
		this.taxDescription = taxDescription;
	}

	/**
	 * @return the taxAmount
	 */
	public MonetaryAmount getTaxAmount() {
		return taxAmount;
	}

	/**
	 * @param taxAmount
	 *            the taxAmount to set
	 */
	public void setTaxAmount(MonetaryAmount taxAmount) {
		this.taxAmount = taxAmount;
	}

	/**
	 * @return the percentage
	 */
	public BigDecimal getPercentage() {
		return percentage;
	}

	/**
	 * @param percentage
	 *            the percentage to set
	 */
	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}

	/**
	 * @return the taxOnItem
	 */
	public MonetaryAmount getTaxOnItem() {
		return taxOnItem;
	}

	/**
	 * @param taxOnItem
	 *            the taxOnItem to set
	 */
	public void setTaxOnItem(MonetaryAmount taxOnItem) {
		this.taxOnItem = taxOnItem;
	}

}
