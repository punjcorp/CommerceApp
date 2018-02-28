/**
 * 
 */
package com.punj.app.ecommerce.models.common;

import java.time.LocalDateTime;

/**
 * @author admin
 *
 */
public class RegisterBean {

	private Integer locationId;
	private Integer registerId;
	private String name;

	private String createdBy;
	private LocalDateTime createdDate;

	private LocalDateTime lastBusinessDate;
	private LocalDateTime lastCreatedDate;
	private String lastStatus;
	private Boolean eligibleForRegisterOpen = Boolean.TRUE;

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
	 * @return the lastBusinessDate
	 */
	public LocalDateTime getLastBusinessDate() {
		return lastBusinessDate;
	}

	/**
	 * @param lastBusinessDate
	 *            the lastBusinessDate to set
	 */
	public void setLastBusinessDate(LocalDateTime lastBusinessDate) {
		this.lastBusinessDate = lastBusinessDate;
	}

	/**
	 * @return the lastCreatedDate
	 */
	public LocalDateTime getLastCreatedDate() {
		return lastCreatedDate;
	}

	/**
	 * @param lastCreatedDate
	 *            the lastCreatedDate to set
	 */
	public void setLastCreatedDate(LocalDateTime lastCreatedDate) {
		this.lastCreatedDate = lastCreatedDate;
	}

	/**
	 * @return the lastStatus
	 */
	public String getLastStatus() {
		return lastStatus;
	}

	/**
	 * @param lastStatus
	 *            the lastStatus to set
	 */
	public void setLastStatus(String lastStatus) {
		this.lastStatus = lastStatus;
	}

	/**
	 * @return the eligibleForRegisterOpen
	 */
	public Boolean getEligibleForRegisterOpen() {
		return eligibleForRegisterOpen;
	}

	/**
	 * @param eligibleForRegisterOpen
	 *            the eligibleForRegisterOpen to set
	 */
	public void setEligibleForRegisterOpen(Boolean eligibleForRegisterOpen) {
		this.eligibleForRegisterOpen = eligibleForRegisterOpen;
	}

}
