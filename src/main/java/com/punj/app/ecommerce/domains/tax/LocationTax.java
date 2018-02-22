/**
 * 
 */
package com.punj.app.ecommerce.domains.tax;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.punj.app.ecommerce.domains.tax.ids.LocationTaxId;

/**
 * @author admin
 *
 */
@Entity
@Table(name = "v_location_tax")
public class LocationTax implements Serializable {

	private static final long serialVersionUID = 7287960691860137764L;

	@EmbeddedId
	private LocationTaxId locationTaxId;

	@Column(name = "tax_group_desc")
	private String groupDesc;
	@Column(name = "tax_group_rate_name")
	private String groupRateName;
	@Column(name = "tax_group_rate_desc")
	private String groupRateDesc;
	@Column(name = "compound_flag")
	private String compoundFlag;
	@Column(name = "type_code")
	private String typeCode;
	@Column(name = "seq_no")
	private Integer seqNo;
	@Column(name = "effective_date")
	private LocalDateTime effectiveDate;
	@Column(name = "expiry_date")
	private LocalDateTime expiryDate;
	private BigDecimal percentage;
	private BigDecimal amount;

	/**
	 * @return the locationTaxId
	 */
	public LocationTaxId getLocationTaxId() {
		return locationTaxId;
	}

	/**
	 * @param locationTaxId
	 *            the locationTaxId to set
	 */
	public void setLocationTaxId(LocationTaxId locationTaxId) {
		this.locationTaxId = locationTaxId;
	}

	/**
	 * @return the groupDesc
	 */
	public String getGroupDesc() {
		return groupDesc;
	}

	/**
	 * @param groupDesc
	 *            the groupDesc to set
	 */
	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	/**
	 * @return the groupRateName
	 */
	public String getGroupRateName() {
		return groupRateName;
	}

	/**
	 * @param groupRateName
	 *            the groupRateName to set
	 */
	public void setGroupRateName(String groupRateName) {
		this.groupRateName = groupRateName;
	}

	/**
	 * @return the groupRateDesc
	 */
	public String getGroupRateDesc() {
		return groupRateDesc;
	}

	/**
	 * @param groupRateDesc
	 *            the groupRateDesc to set
	 */
	public void setGroupRateDesc(String groupRateDesc) {
		this.groupRateDesc = groupRateDesc;
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

}
