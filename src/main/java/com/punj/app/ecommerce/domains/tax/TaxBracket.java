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
import javax.persistence.Table;

/**
 * @author admin
 *
 */
@Entity
@Table(name = "tax_bracket")
public class TaxBracket implements Serializable {

	private static final long serialVersionUID = 1982994837919699915L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "tax_bracket_id", updatable = false, nullable = false)
	private Integer taxBracketId;

	@Column(name = "seq_no")
	private Integer seqNo;

	@Column(name = "breakpoint")
	private BigDecimal breakPointAmt;
	@Column(name = "tax_amount")
	private BigDecimal taxAmt;

	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "created_date")
	private LocalDateTime createdDate;

	@Column(name = "modified_by")
	private String modifiedBy;
	@Column(name = "modified_date")
	private LocalDateTime modifiedDate;

	/**
	 * @return the taxBracketId
	 */
	public Integer getTaxBracketId() {
		return taxBracketId;
	}

	/**
	 * @param taxBracketId
	 *            the taxBracketId to set
	 */
	public void setTaxBracketId(Integer taxBracketId) {
		this.taxBracketId = taxBracketId;
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
	 * @return the breakPointAmt
	 */
	public BigDecimal getBreakPointAmt() {
		return breakPointAmt;
	}

	/**
	 * @param breakPointAmt
	 *            the breakPointAmt to set
	 */
	public void setBreakPointAmt(BigDecimal breakPointAmt) {
		this.breakPointAmt = breakPointAmt;
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
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
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
		result = prime * result + ((seqNo == null) ? 0 : seqNo.hashCode());
		result = prime * result + ((taxBracketId == null) ? 0 : taxBracketId.hashCode());
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
		TaxBracket other = (TaxBracket) obj;
		if (seqNo == null) {
			if (other.seqNo != null) {
				return false;
			}
		} else if (!seqNo.equals(other.seqNo)) {
			return false;
		}
		if (taxBracketId == null) {
			if (other.taxBracketId != null) {
				return false;
			}
		} else if (!taxBracketId.equals(other.taxBracketId)) {
			return false;
		}
		return true;
	}

}
