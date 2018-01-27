package com.punj.app.ecommerce.domains.tax.ids;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.punj.app.ecommerce.domains.tax.TaxAuthority;
import com.punj.app.ecommerce.domains.tax.TaxGroup;
import com.punj.app.ecommerce.domains.tax.TaxLocation;

@Embeddable
public class TaxGroupRuleId implements Serializable {

	private static final long serialVersionUID = 6495412291742554908L;

	@ManyToOne
	@JoinColumn(name = "tax_group_id")
	private TaxGroup taxGroup;

	@ManyToOne
	@JoinColumn(name = "tax_location_id")
	private TaxLocation taxLocation;

	@ManyToOne
	@JoinColumn(name = "tax_authority_id")
	private TaxAuthority taxAuthority;

	@Column(name = "seq_no")
	private Integer seqNo;

	/**
	 * @return the taxGroup
	 */
	public TaxGroup getTaxGroup() {
		return taxGroup;
	}

	/**
	 * @param taxGroup
	 *            the taxGroup to set
	 */
	public void setTaxGroup(TaxGroup taxGroup) {
		this.taxGroup = taxGroup;
	}

	/**
	 * @return the taxLocation
	 */
	public TaxLocation getTaxLocation() {
		return taxLocation;
	}

	/**
	 * @param taxLocation
	 *            the taxLocation to set
	 */
	public void setTaxLocation(TaxLocation taxLocation) {
		this.taxLocation = taxLocation;
	}

	/**
	 * @return the taxAuthority
	 */
	public TaxAuthority getTaxAuthority() {
		return taxAuthority;
	}

	/**
	 * @param taxAuthority
	 *            the taxAuthority to set
	 */
	public void setTaxAuthority(TaxAuthority taxAuthority) {
		this.taxAuthority = taxAuthority;
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
		result = prime * result + ((taxAuthority == null) ? 0 : taxAuthority.hashCode());
		result = prime * result + ((taxGroup == null) ? 0 : taxGroup.hashCode());
		result = prime * result + ((taxLocation == null) ? 0 : taxLocation.hashCode());
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
		TaxGroupRuleId other = (TaxGroupRuleId) obj;
		if (seqNo == null) {
			if (other.seqNo != null) {
				return false;
			}
		} else if (!seqNo.equals(other.seqNo)) {
			return false;
		}
		if (taxAuthority == null) {
			if (other.taxAuthority != null) {
				return false;
			}
		} else if (!taxAuthority.equals(other.taxAuthority)) {
			return false;
		}
		if (taxGroup == null) {
			if (other.taxGroup != null) {
				return false;
			}
		} else if (!taxGroup.equals(other.taxGroup)) {
			return false;
		}
		if (taxLocation == null) {
			if (other.taxLocation != null) {
				return false;
			}
		} else if (!taxLocation.equals(other.taxLocation)) {
			return false;
		}
		return true;
	}

}
