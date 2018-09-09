package com.punj.app.ecommerce.domains.transaction;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.punj.app.ecommerce.domains.transaction.ids.TransactionLineItemId;

@Entity
@Table(name = "txn_li_tax")
public class TaxLineItem implements Serializable {

	private static final long serialVersionUID = -4480903190459924122L;

	@EmbeddedId
	private TransactionLineItemId transactionLineItemId;

	@Column(name = "item_id")
	private BigInteger itemId;

	@Column(name = "total_taxable_amount")
	private BigDecimal totalTaxableAmt;
	@Column(name = "total_tax_amount")
	private BigDecimal totalTaxAmt;
	@Column(name = "total_tax_exempt_amount")
	private BigDecimal totalTaxExemptAmt;
	@Column(name = "tax_override_amount")
	private BigDecimal taxOverrideAmt;
	@Column(name = "tax_override_percentage")
	private BigDecimal taxOverridePercentage;

	@Column(name = "tax_override_flag")
	private Boolean taxOverrideFlag;
	@Column(name = "void_flag")
	private Boolean voidFlag;

	@Column(name = "tax_rule_amount")
	private BigDecimal taxRuleAmt;
	@Column(name = "tax_rule_percentage")
	private BigDecimal taxRulePercentage;

	@Column(name = "org_taxable_amount")
	private BigDecimal orgTaxableAmt;
	@Column(name = "org_tax_group_id")
	private Integer orgTaxGroupId;

	@Column(name = "tax_group_id")
	private Integer taxGroupId;
	@Column(name = "tax_rule_rate_id")
	private Integer taxRuleRateId;
	
	@Column(name = "tax_code")
	private String taxCode;

	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "created_date")
	private LocalDateTime createdDate;

	/**
	 * @return the transactionLineItemId
	 */
	public TransactionLineItemId getTransactionLineItemId() {
		return transactionLineItemId;
	}

	/**
	 * @param transactionLineItemId
	 *            the transactionLineItemId to set
	 */
	public void setTransactionLineItemId(TransactionLineItemId transactionLineItemId) {
		this.transactionLineItemId = transactionLineItemId;
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
	 * @return the taxOverridePercentage
	 */
	public BigDecimal getTaxOverridePercentage() {
		return taxOverridePercentage;
	}

	/**
	 * @param taxOverridePercentage
	 *            the taxOverridePercentage to set
	 */
	public void setTaxOverridePercentage(BigDecimal taxOverridePercentage) {
		this.taxOverridePercentage = taxOverridePercentage;
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
	 * @return the taxRulePercentage
	 */
	public BigDecimal getTaxRulePercentage() {
		return taxRulePercentage;
	}

	/**
	 * @param taxRulePercentage
	 *            the taxRulePercentage to set
	 */
	public void setTaxRulePercentage(BigDecimal taxRulePercentage) {
		this.taxRulePercentage = taxRulePercentage;
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
	 * @return the taxCode
	 */
	public String getTaxCode() {
		return taxCode;
	}

	/**
	 * @param taxCode the taxCode to set
	 */
	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result + ((transactionLineItemId == null) ? 0 : transactionLineItemId.hashCode());
		result = prime * result + ((voidFlag == null) ? 0 : voidFlag.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		TaxLineItem other = (TaxLineItem) obj;
		if (createdDate == null) {
			if (other.createdDate != null) {
				return false;
			}
		} else if (!createdDate.equals(other.createdDate)) {
			return false;
		}
		if (transactionLineItemId == null) {
			if (other.transactionLineItemId != null) {
				return false;
			}
		} else if (!transactionLineItemId.equals(other.transactionLineItemId)) {
			return false;
		}
		if (voidFlag == null) {
			if (other.voidFlag != null) {
				return false;
			}
		} else if (!voidFlag.equals(other.voidFlag)) {
			return false;
		}
		return true;
	}

}
