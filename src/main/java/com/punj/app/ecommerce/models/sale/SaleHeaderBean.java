/**
 * 
 */
package com.punj.app.ecommerce.models.sale;

import java.time.LocalDateTime;

/**
 * @author admin
 *
 */
public class SaleHeaderBean {

	private Integer locationId;
	private String locationName;
	private LocalDateTime businessDate;
	private Integer registerId;
	private String registerName;
	private String defaultTender;

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
	 * @return the locationName
	 */
	public String getLocationName() {
		return locationName;
	}

	/**
	 * @param locationName
	 *            the locationName to set
	 */
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	/**
	 * @return the businessDate
	 */
	public LocalDateTime getBusinessDate() {
		return businessDate;
	}

	/**
	 * @param businessDate
	 *            the businessDate to set
	 */
	public void setBusinessDate(LocalDateTime businessDate) {
		this.businessDate = businessDate;
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
	 * @return the registerName
	 */
	public String getRegisterName() {
		return registerName;
	}

	/**
	 * @param registerName
	 *            the registerName to set
	 */
	public void setRegisterName(String registerName) {
		this.registerName = registerName;
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

}
