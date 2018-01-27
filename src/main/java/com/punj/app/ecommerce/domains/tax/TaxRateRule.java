/**
 * 
 */
package com.punj.app.ecommerce.domains.tax;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

/**
 * @author admin
 *
 */
@Entity
@Table(name = "tax_rate_rule")
public class TaxRateRule implements Serializable {

	private static final long serialVersionUID = -7337960642191749031L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "tax_rate_rule_id", updatable = false, nullable = false)
	private Integer taxRateRuleId;

	@Column(name = "seq_no")
	private Integer seqNo;

	@Column(name = "min_taxable_amt")
	private BigDecimal minTaxableAmt;
	@Column(name = "max_taxable_amt")
	private BigDecimal maxTaxableAmt;

	@Column(name = "effective_date")
	private LocalDateTime effectiveDate;
	@Column(name = "expiry_date")
	private LocalDateTime expiryDate;

	private BigDecimal percentage;
	private BigDecimal amount;

	@Column(name = "type_code")
	private String typeCode;

	private String status;

	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "created_date")
	private LocalDateTime createdDate;

	@Column(name = "modified_by")
	private String modifiedBy;
	@Column(name = "modified_date")
	private LocalDateTime modifiedDate;

	@MapsId("taxGroupRateId")
	@JoinColumns({ @JoinColumn(name = "tax_group_id", referencedColumnName = "tax_group_id"),
			@JoinColumn(name = "tax_location_id", referencedColumnName = "tax_location_id"),
			@JoinColumn(name = "tax_authority_id", referencedColumnName = "tax_authority_id"),
			@JoinColumn(name = "tax_group_rule_seq", referencedColumnName = "seq_no") })
	@ManyToOne
	private TaxGroupRule taxGroupRule;

	@ManyToOne
	@JoinColumn(name = "tax_bracket_id")
	private TaxBracket taxBracket;

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
	 * @return the minTaxableAmt
	 */
	public BigDecimal getMinTaxableAmt() {
		return minTaxableAmt;
	}

	/**
	 * @param minTaxableAmt
	 *            the minTaxableAmt to set
	 */
	public void setMinTaxableAmt(BigDecimal minTaxableAmt) {
		this.minTaxableAmt = minTaxableAmt;
	}

	/**
	 * @return the maxTaxableAmt
	 */
	public BigDecimal getMaxTaxableAmt() {
		return maxTaxableAmt;
	}

	/**
	 * @param maxTaxableAmt
	 *            the maxTaxableAmt to set
	 */
	public void setMaxTaxableAmt(BigDecimal maxTaxableAmt) {
		this.maxTaxableAmt = maxTaxableAmt;
	}

	/**
	 * @return the effectiveDate
	 */
	public LocalDateTime getEffectiveDate() {
		return effectiveDate;
	}

	/**
	 * @param effectiveDate
	 *            the effectiveDate to set
	 */
	public void setEffectiveDate(LocalDateTime effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	/**
	 * @return the expiryDate
	 */
	public LocalDateTime getExpiryDate() {
		return expiryDate;
	}

	/**
	 * @param expiryDate
	 *            the expiryDate to set
	 */
	public void setExpiryDate(LocalDateTime expiryDate) {
		this.expiryDate = expiryDate;
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
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
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
	 * @return the modifiedBy
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * @param modifiedBy
	 *            the modifiedBy to set
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * @return the modifiedDate
	 */
	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	/**
	 * @param modifiedDate
	 *            the modifiedDate to set
	 */
	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/**
	 * @return the taxGroupRule
	 */
	public TaxGroupRule getTaxGroupRule() {
		return taxGroupRule;
	}

	/**
	 * @param taxGroupRule
	 *            the taxGroupRule to set
	 */
	public void setTaxGroupRule(TaxGroupRule taxGroupRule) {
		this.taxGroupRule = taxGroupRule;
	}

	/**
	 * @return the taxBracket
	 */
	public TaxBracket getTaxBracket() {
		return taxBracket;
	}

	/**
	 * @param taxBracket
	 *            the taxBracket to set
	 */
	public void setTaxBracket(TaxBracket taxBracket) {
		this.taxBracket = taxBracket;
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
		result = prime * result + ((taxBracket == null) ? 0 : taxBracket.hashCode());
		result = prime * result + ((taxGroupRule == null) ? 0 : taxGroupRule.hashCode());
		result = prime * result + ((taxRateRuleId == null) ? 0 : taxRateRuleId.hashCode());
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
		TaxRateRule other = (TaxRateRule) obj;
		if (taxBracket == null) {
			if (other.taxBracket != null) {
				return false;
			}
		} else if (!taxBracket.equals(other.taxBracket)) {
			return false;
		}
		if (taxGroupRule == null) {
			if (other.taxGroupRule != null) {
				return false;
			}
		} else if (!taxGroupRule.equals(other.taxGroupRule)) {
			return false;
		}
		if (taxRateRuleId == null) {
			if (other.taxRateRuleId != null) {
				return false;
			}
		} else if (!taxRateRuleId.equals(other.taxRateRuleId)) {
			return false;
		}
		return true;
	}

}
