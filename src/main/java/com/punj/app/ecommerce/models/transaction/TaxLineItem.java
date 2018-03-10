/**
 * 
 */
package com.punj.app.ecommerce.models.transaction;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author admin
 *
 */
public class TaxLineItem {

	private Integer locationId;
	private Integer registerId;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime businessDate;
	private Integer txnNo;

	private Integer seqNo;
	private BigInteger itemId;
	private Integer taxGroupId;
	private Integer taxRuleRateId;

	private BigDecimal totalTaxAmt;
	private BigDecimal totalTaxableAmt;
	private BigDecimal totalTaxExemptAmt;
	private BigDecimal taxOverrideAmt;
	private BigDecimal taxOverrideRate;
	private Boolean taxOverrideFlag;
	private String taxOverrideReason;

	private Boolean voidFlag;
	private BigDecimal taxRuleRate;
	private BigDecimal taxRuleAmt;

	private BigDecimal orgTaxableAmt;
	private Integer orgTaxGroupId;

	private String createdBy;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdDate;

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
	 * @return the registerId
	 */
	public Integer getRegisterId() {
		return registerId;
	}

	/**
	 * @param registerId
	 *            the registerId to set
	 */
	public void setRegisterId(Integer registerId) {
		this.registerId = registerId;
	}

	/**
	 * @return the businessDate
	 */
	public LocalDateTime getBusinessDate() {
		return businessDate;
	}

	/**
	 * @param businessDate
	 *            the businessDate to set
	 */
	public void setBusinessDate(LocalDateTime businessDate) {
		this.businessDate = businessDate;
	}

	/**
	 * @return the txnNo
	 */
	public Integer getTxnNo() {
		return txnNo;
	}

	/**
	 * @param txnNo
	 *            the txnNo to set
	 */
	public void setTxnNo(Integer txnNo) {
		this.txnNo = txnNo;
	}

	/**
	 * @return the seqNo
	 */
	public Integer getSeqNo() {
		return seqNo;
	}

	/**
	 * @param seqNo
	 *            the seqNo to set
	 */
	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
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
	 * @return the totalTaxAmt
	 */
	public BigDecimal getTotalTaxAmt() {
		return totalTaxAmt;
	}

	/**
	 * @param totalTaxAmt
	 *            the totalTaxAmt to set
	 */
	public void setTotalTaxAmt(BigDecimal totalTaxAmt) {
		this.totalTaxAmt = totalTaxAmt;
	}

	/**
	 * @return the totalTaxableAmt
	 */
	public BigDecimal getTotalTaxableAmt() {
		return totalTaxableAmt;
	}

	/**
	 * @param totalTaxableAmt
	 *            the totalTaxableAmt to set
	 */
	public void setTotalTaxableAmt(BigDecimal totalTaxableAmt) {
		this.totalTaxableAmt = totalTaxableAmt;
	}

	/**
	 * @return the totalTaxExemptAmt
	 */
	public BigDecimal getTotalTaxExemptAmt() {
		return totalTaxExemptAmt;
	}

	/**
	 * @param totalTaxExemptAmt
	 *            the totalTaxExemptAmt to set
	 */
	public void setTotalTaxExemptAmt(BigDecimal totalTaxExemptAmt) {
		this.totalTaxExemptAmt = totalTaxExemptAmt;
	}

	/**
	 * @return the taxOverrideAmt
	 */
	public BigDecimal getTaxOverrideAmt() {
		return taxOverrideAmt;
	}

	/**
	 * @param taxOverrideAmt
	 *            the taxOverrideAmt to set
	 */
	public void setTaxOverrideAmt(BigDecimal taxOverrideAmt) {
		this.taxOverrideAmt = taxOverrideAmt;
	}

	/**
	 * @return the taxOverrideRate
	 */
	public BigDecimal getTaxOverrideRate() {
		return taxOverrideRate;
	}

	/**
	 * @param taxOverrideRate
	 *            the taxOverrideRate to set
	 */
	public void setTaxOverrideRate(BigDecimal taxOverrideRate) {
		this.taxOverrideRate = taxOverrideRate;
	}

	/**
	 * @return the taxOverrideFlag
	 */
	public Boolean getTaxOverrideFlag() {
		return taxOverrideFlag;
	}

	/**
	 * @param taxOverrideFlag
	 *            the taxOverrideFlag to set
	 */
	public void setTaxOverrideFlag(Boolean taxOverrideFlag) {
		this.taxOverrideFlag = taxOverrideFlag;
	}

	/**
	 * @return the taxOverrideReason
	 */
	public String getTaxOverrideReason() {
		return taxOverrideReason;
	}

	/**
	 * @param taxOverrideReason
	 *            the taxOverrideReason to set
	 */
	public void setTaxOverrideReason(String taxOverrideReason) {
		this.taxOverrideReason = taxOverrideReason;
	}

	/**
	 * @return the voidFlag
	 */
	public Boolean getVoidFlag() {
		return voidFlag;
	}

	/**
	 * @param voidFlag
	 *            the voidFlag to set
	 */
	public void setVoidFlag(Boolean voidFlag) {
		this.voidFlag = voidFlag;
	}

	/**
	 * @return the taxRuleRate
	 */
	public BigDecimal getTaxRuleRate() {
		return taxRuleRate;
	}

	/**
	 * @param taxRuleRate
	 *            the taxRuleRate to set
	 */
	public void setTaxRuleRate(BigDecimal taxRuleRate) {
		this.taxRuleRate = taxRuleRate;
	}

	/**
	 * @return the taxRuleAmt
	 */
	public BigDecimal getTaxRuleAmt() {
		return taxRuleAmt;
	}

	/**
	 * @param taxRuleAmt
	 *            the taxRuleAmt to set
	 */
	public void setTaxRuleAmt(BigDecimal taxRuleAmt) {
		this.taxRuleAmt = taxRuleAmt;
	}

	/**
	 * @return the orgTaxableAmt
	 */
	public BigDecimal getOrgTaxableAmt() {
		return orgTaxableAmt;
	}

	/**
	 * @param orgTaxableAmt
	 *            the orgTaxableAmt to set
	 */
	public void setOrgTaxableAmt(BigDecimal orgTaxableAmt) {
		this.orgTaxableAmt = orgTaxableAmt;
	}

	/**
	 * @return the orgTaxGroupId
	 */
	public Integer getOrgTaxGroupId() {
		return orgTaxGroupId;
	}

	/**
	 * @param orgTaxGroupId
	 *            the orgTaxGroupId to set
	 */
	public void setOrgTaxGroupId(Integer orgTaxGroupId) {
		this.orgTaxGroupId = orgTaxGroupId;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the createdDate
	 */
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate
	 *            the createdDate to set
	 */
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

}
