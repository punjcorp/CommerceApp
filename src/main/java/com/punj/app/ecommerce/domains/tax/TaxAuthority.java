/**
 * 
 */
package com.punj.app.ecommerce.domains.tax;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author admin
 *
 */
@Entity
@Table(name = "tax_authority")
public class TaxAuthority implements Serializable {
	private static final long serialVersionUID = -8164393876995305912L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "tax_authority_id", updatable = false, nullable = false)
	private Integer taxAuthorityId;
	private String name;

	@Column(name = "rounding_code")
	private String roundingCode;
	@Column(name = "rounding_digit_qty")
	private String roundingDigitQty;

	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "created_date")
	private LocalDateTime createdDate;

	/**
	 * @return the taxAuthorityId
	 */
	public Integer getTaxAuthorityId() {
		return taxAuthorityId;
	}

	/**
	 * @param taxAuthorityId
	 *            the taxAuthorityId to set
	 */
	public void setTaxAuthorityId(Integer taxAuthorityId) {
		this.taxAuthorityId = taxAuthorityId;
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
	 * @return the roundingCode
	 */
	public String getRoundingCode() {
		return roundingCode;
	}

	/**
	 * @param roundingCode
	 *            the roundingCode to set
	 */
	public void setRoundingCode(String roundingCode) {
		this.roundingCode = roundingCode;
	}

	/**
	 * @return the roundingDigitQty
	 */
	public String getRoundingDigitQty() {
		return roundingDigitQty;
	}

	/**
	 * @param roundingDigitQty
	 *            the roundingDigitQty to set
	 */
	public void setRoundingDigitQty(String roundingDigitQty) {
		this.roundingDigitQty = roundingDigitQty;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((taxAuthorityId == null) ? 0 : taxAuthorityId.hashCode());
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
		TaxAuthority other = (TaxAuthority) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (taxAuthorityId == null) {
			if (other.taxAuthorityId != null) {
				return false;
			}
		} else if (!taxAuthorityId.equals(other.taxAuthorityId)) {
			return false;
		}
		return true;
	}

}
