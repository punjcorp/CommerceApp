/**
 * 
 */
package com.punj.app.ecommerce.domains.tax;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.punj.app.ecommerce.domains.tax.ids.TaxGroupRuleId;

/**
 * @author admin
 *
 */
@Entity
@Table(name = "tax_group_rule")
public class TaxGroupRule implements Serializable {

	private static final long serialVersionUID = -2229016076057694776L;

	@EmbeddedId
	private TaxGroupRuleId taxGroupRuleId;

	private String name;
	private String description;

	@Column(name = "compound_seq_nbr")
	private Integer compoundSeq;
	@Column(name = "compound_flag")
	private String compoundFlag;
	@Column(name = "trans_level_flag")
	private String transactionFlag;

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

	/**
	 * @return the taxGroupRuleId
	 */
	public TaxGroupRuleId getTaxGroupRuleId() {
		return taxGroupRuleId;
	}

	/**
	 * @param taxGroupRuleId
	 *            the taxGroupRuleId to set
	 */
	public void setTaxGroupRuleId(TaxGroupRuleId taxGroupRuleId) {
		this.taxGroupRuleId = taxGroupRuleId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the compoundSeq
	 */
	public Integer getCompoundSeq() {
		return compoundSeq;
	}

	/**
	 * @param compoundSeq
	 *            the compoundSeq to set
	 */
	public void setCompoundSeq(Integer compoundSeq) {
		this.compoundSeq = compoundSeq;
	}

	/**
	 * @return the compoundFlag
	 */
	public String getCompoundFlag() {
		return compoundFlag;
	}

	/**
	 * @param compoundFlag
	 *            the compoundFlag to set
	 */
	public void setCompoundFlag(String compoundFlag) {
		this.compoundFlag = compoundFlag;
	}

	/**
	 * @return the transactionFlag
	 */
	public String getTransactionFlag() {
		return transactionFlag;
	}

	/**
	 * @param transactionFlag
	 *            the transactionFlag to set
	 */
	public void setTransactionFlag(String transactionFlag) {
		this.transactionFlag = transactionFlag;
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
		result = prime * result + ((taxGroupRuleId == null) ? 0 : taxGroupRuleId.hashCode());
		result = prime * result + ((typeCode == null) ? 0 : typeCode.hashCode());
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
		TaxGroupRule other = (TaxGroupRule) obj;
		if (createdDate == null) {
			if (other.createdDate != null) {
				return false;
			}
		} else if (!createdDate.equals(other.createdDate)) {
			return false;
		}
		if (taxGroupRuleId == null) {
			if (other.taxGroupRuleId != null) {
				return false;
			}
		} else if (!taxGroupRuleId.equals(other.taxGroupRuleId)) {
			return false;
		}
		if (typeCode == null) {
			if (other.typeCode != null) {
				return false;
			}
		} else if (!typeCode.equals(other.typeCode)) {
			return false;
		}
		return true;
	}

}
