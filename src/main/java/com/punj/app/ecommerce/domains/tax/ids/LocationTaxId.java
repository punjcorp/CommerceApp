package com.punj.app.ecommerce.domains.tax.ids;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class LocationTaxId implements Serializable {

	private static final long serialVersionUID = -3179868161822656833L;

	@Column(name = "location_id")
	private Integer locationId;

	@Column(name = "billing_location")
	private String billingLocationCode;

	@Column(name = "tax_group_id")
	private Integer taxGroupId;

	@Column(name = "tax_group_name")
	private String groupName;

	@Column(name = "tax_group_rate_seq")
	private Integer groupRateSeq;

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
	 * @return the billingLocationCode
	 */
	public String getBillingLocationCode() {
		return billingLocationCode;
	}

	/**
	 * @param billingLocationCode
	 *            the billingLocationCode to set
	 */
	public void setBillingLocationCode(String billingLocationCode) {
		this.billingLocationCode = billingLocationCode;
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
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName
	 *            the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return the groupRateSeq
	 */
	public Integer getGroupRateSeq() {
		return groupRateSeq;
	}

	/**
	 * @param groupRateSeq
	 *            the groupRateSeq to set
	 */
	public void setGroupRateSeq(Integer groupRateSeq) {
		this.groupRateSeq = groupRateSeq;
	}

}
