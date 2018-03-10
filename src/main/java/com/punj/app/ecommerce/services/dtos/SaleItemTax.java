/**
 * 
 */
package com.punj.app.ecommerce.services.dtos;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author admin
 *
 */
public class SaleItemTax implements Serializable {

	private static final long serialVersionUID = -5564390114122991932L;

	private Integer locationId;

	private String billingLocation;

	private Integer taxGroupId;

	private String taxGroupName;

	private String taxGroupRateName;

	private Integer taxRuleRateId;

	private String taxRuleRateName;

	private String typeCode;

	private BigDecimal percentage;

	private BigDecimal amount;

	/**
	 * @return the locationId
	 */
	public Integer getLocationId() {
		return locationId;
	}

	/**
	 * @param locationId
	 *            the locationId to set
	 */
	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	/**
	 * @return the billingLocation
	 */
	public String getBillingLocation() {
		return billingLocation;
	}

	/**
	 * @param billingLocation
	 *            the billingLocation to set
	 */
	public void setBillingLocation(String billingLocation) {
		this.billingLocation = billingLocation;
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
	 * @return the taxGroupName
	 */
	public String getTaxGroupName() {
		return taxGroupName;
	}

	/**
	 * @param taxGroupName
	 *            the taxGroupName to set
	 */
	public void setTaxGroupName(String taxGroupName) {
		this.taxGroupName = taxGroupName;
	}

	/**
	 * @return the taxGroupRateName
	 */
	public String getTaxGroupRateName() {
		return taxGroupRateName;
	}

	/**
	 * @param taxGroupRateName
	 *            the taxGroupRateName to set
	 */
	public void setTaxGroupRateName(String taxGroupRateName) {
		this.taxGroupRateName = taxGroupRateName;
	}

	/**
	 * @return the typeCode
	 */
	public String getTypeCode() {
		return typeCode;
	}

	/**
	 * @param typeCode
	 *            the typeCode to set
	 */
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
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
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * @return the taxRuleRateId
	 */
	public Integer getTaxRuleRateId() {
		return taxRuleRateId;
	}

	/**
	 * @param taxRuleRateId
	 *            the taxRuleRateId to set
	 */
	public void setTaxRuleRateId(Integer taxRuleRateId) {
		this.taxRuleRateId = taxRuleRateId;
	}

	/**
	 * @return the taxRuleRateName
	 */
	public String getTaxRuleRateName() {
		return taxRuleRateName;
	}

	/**
	 * @param taxRuleRateName
	 *            the taxRuleRateName to set
	 */
	public void setTaxRuleRateName(String taxRuleRateName) {
		this.taxRuleRateName = taxRuleRateName;
	}

}
