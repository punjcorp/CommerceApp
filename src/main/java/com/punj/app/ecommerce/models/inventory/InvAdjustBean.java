/**
 * 
 */
package com.punj.app.ecommerce.models.inventory;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.punj.app.ecommerce.models.common.LocationBean;

/**
 * @author admin
 *
 */
public class InvAdjustBean {

	private BigInteger invAdjustId;

	private String description;

	@NotNull(message = "{commerce.error.select.empty}")
	private Integer locationId;

	@NotNull(message = "{commerce.error.select.empty}")
	private Integer reasonCodeId;

	private List<InvReasonBean> reasonCodes;
	private List<LocationBean> locations;
	@Valid
	private List<InvAdjustItemBean> invAdjustItems;

	private String createdBy;
	private LocalDateTime createdDate;

	private String modifiedBy;
	private LocalDateTime modifiedDate;

	private String status;

	private LocationBean locationDetails;

	public InvAdjustBean() {
		invAdjustItems = new ArrayList<>(1);
		invAdjustItems.add(new InvAdjustItemBean());
	}

	/**
	 * @return the invAdjustId
	 */
	public BigInteger getInvAdjustId() {
		return invAdjustId;
	}

	/**
	 * @param invAdjustId
	 *            the invAdjustId to set
	 */
	public void setInvAdjustId(BigInteger invAdjustId) {
		this.invAdjustId = invAdjustId;
	}

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
	 * @return the reasonCodeId
	 */
	public Integer getReasonCodeId() {
		return reasonCodeId;
	}

	/**
	 * @param reasonCodeId
	 *            the reasonCodeId to set
	 */
	public void setReasonCodeId(Integer reasonCodeId) {
		this.reasonCodeId = reasonCodeId;
	}

	/**
	 * @return the reasonCodes
	 */
	public List<InvReasonBean> getReasonCodes() {
		return reasonCodes;
	}

	/**
	 * @param reasonCodes
	 *            the reasonCodes to set
	 */
	public void setReasonCodes(List<InvReasonBean> reasonCodes) {
		this.reasonCodes = reasonCodes;
	}

	/**
	 * @return the locations
	 */
	public List<LocationBean> getLocations() {
		return locations;
	}

	/**
	 * @param locations
	 *            the locations to set
	 */
	public void setLocations(List<LocationBean> locations) {
		this.locations = locations;
	}

	/**
	 * @return the invAdjustItems
	 */
	public List<InvAdjustItemBean> getInvAdjustItems() {
		return invAdjustItems;
	}

	/**
	 * @param invAdjustItems
	 *            the invAdjustItems to set
	 */
	public void setInvAdjustItems(List<InvAdjustItemBean> invAdjustItems) {
		this.invAdjustItems = invAdjustItems;
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
	 * @return the locationDetails
	 */
	public LocationBean getLocationDetails() {
		return locationDetails;
	}

	/**
	 * @param locationDetails
	 *            the locationDetails to set
	 */
	public void setLocationDetails(LocationBean locationDetails) {
		this.locationDetails = locationDetails;
	}

}
