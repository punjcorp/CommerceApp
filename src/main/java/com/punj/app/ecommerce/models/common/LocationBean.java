/**
 * 
 */
package com.punj.app.ecommerce.models.common;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

/**
 * @author admin
 *
 */
public class LocationBean {

	private Integer locationId;
	@NotNull
	private String locationType;
	@NotNull
	private String name;

	private String address1;
	private String address2;
	private String city;
	private String district;
	private String state;
	private String stateCode;
	private String country;
	private String pincode;
	private String neighborhood;
	private String telephone1;
	private String telephone2;
	private String manager;
	private String email;

	private String defaultTender;

	private String gstNo;
	private String panNo;

	private LocalDateTime lastBusinessDate;
	private LocalDateTime lastCreatedDate;
	private String lastStatus;
	private Boolean eligibleForStoreOpen = Boolean.TRUE;
	private LocalDateTime lastSaleBusinessDate;
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
	 * @return the locationType
	 */
	public String getLocationType() {
		return locationType;
	}

	/**
	 * @param locationType
	 *            the locationType to set
	 */
	public void setLocationType(String locationType) {
		this.locationType = locationType;
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
	 * @return the address1
	 */
	public String getAddress1() {
		return address1;
	}

	/**
	 * @param address1
	 *            the address1 to set
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	/**
	 * @return the address2
	 */
	public String getAddress2() {
		return address2;
	}

	/**
	 * @param address2
	 *            the address2 to set
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the pincode
	 */
	public String getPincode() {
		return pincode;
	}

	/**
	 * @param pincode
	 *            the pincode to set
	 */
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	/**
	 * @return the neighborhood
	 */
	public String getNeighborhood() {
		return neighborhood;
	}

	/**
	 * @param neighborhood
	 *            the neighborhood to set
	 */
	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	/**
	 * @return the telephone1
	 */
	public String getTelephone1() {
		return telephone1;
	}

	/**
	 * @param telephone1
	 *            the telephone1 to set
	 */
	public void setTelephone1(String telephone1) {
		this.telephone1 = telephone1;
	}

	/**
	 * @return the telephone2
	 */
	public String getTelephone2() {
		return telephone2;
	}

	/**
	 * @param telephone2
	 *            the telephone2 to set
	 */
	public void setTelephone2(String telephone2) {
		this.telephone2 = telephone2;
	}

	/**
	 * @return the manager
	 */
	public String getManager() {
		return manager;
	}

	/**
	 * @param manager
	 *            the manager to set
	 */
	public void setManager(String manager) {
		this.manager = manager;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
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
	 * @return the eligibleForStoreOpen
	 */
	public Boolean getEligibleForStoreOpen() {
		return eligibleForStoreOpen;
	}

	/**
	 * @param eligibleForStoreOpen
	 *            the eligibleForStoreOpen to set
	 */
	public void setEligibleForStoreOpen(Boolean eligibleForStoreOpen) {
		this.eligibleForStoreOpen = eligibleForStoreOpen;
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
	 * @return the defaultTender
	 */
	public String getDefaultTender() {
		return defaultTender;
	}

	/**
	 * @param defaultTender
	 *            the defaultTender to set
	 */
	public void setDefaultTender(String defaultTender) {
		this.defaultTender = defaultTender;
	}

	/**
	 * @return the gstNo
	 */
	public String getGstNo() {
		return gstNo;
	}

	/**
	 * @param gstNo
	 *            the gstNo to set
	 */
	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	/**
	 * @return the stateCode
	 */
	public String getStateCode() {
		return stateCode;
	}

	/**
	 * @param stateCode
	 *            the stateCode to set
	 */
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	/**
	 * @return the panNo
	 */
	public String getPanNo() {
		return panNo;
	}

	/**
	 * @param panNo
	 *            the panNo to set
	 */
	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	/**
	 * @return the district
	 */
	public String getDistrict() {
		return district;
	}

	/**
	 * @param district the district to set
	 */
	public void setDistrict(String district) {
		this.district = district;
	}

	/**
	 * @return the lastSaleBusinessDate
	 */
	public LocalDateTime getLastSaleBusinessDate() {
		return lastSaleBusinessDate;
	}

	/**
	 * @param lastSaleBusinessDate the lastSaleBusinessDate to set
	 */
	public void setLastSaleBusinessDate(LocalDateTime lastSaleBusinessDate) {
		this.lastSaleBusinessDate = lastSaleBusinessDate;
	}

}
