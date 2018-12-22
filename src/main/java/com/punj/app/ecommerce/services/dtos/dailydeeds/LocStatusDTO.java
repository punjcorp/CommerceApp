package com.punj.app.ecommerce.services.dtos.dailydeeds;

import java.time.LocalDateTime;

public class LocStatusDTO {

	private Integer locationId;
	private LocalDateTime businessDate;

	private Boolean hasOpen=Boolean.FALSE;
	private Boolean hasClosed=Boolean.FALSE;
	private Boolean hasSales=Boolean.FALSE;

	private Boolean hasOpenedInFuture=Boolean.FALSE;
	private Boolean hasClosedInFuture=Boolean.FALSE;
	private Boolean hasSalesInFuture=Boolean.FALSE;

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
	 * @return the hasOpen
	 */
	public Boolean getHasOpen() {
		return hasOpen;
	}

	/**
	 * @param hasOpen
	 *            the hasOpen to set
	 */
	public void setHasOpen(Boolean hasOpen) {
		this.hasOpen = hasOpen;
	}

	/**
	 * @return the hasClosed
	 */
	public Boolean getHasClosed() {
		return hasClosed;
	}

	/**
	 * @param hasClosed
	 *            the hasClosed to set
	 */
	public void setHasClosed(Boolean hasClosed) {
		this.hasClosed = hasClosed;
	}

	/**
	 * @return the hasSales
	 */
	public Boolean getHasSales() {
		return hasSales;
	}

	/**
	 * @param hasSales
	 *            the hasSales to set
	 */
	public void setHasSales(Boolean hasSales) {
		this.hasSales = hasSales;
	}

	/**
	 * @return the hasOpenedInFuture
	 */
	public Boolean getHasOpenedInFuture() {
		return hasOpenedInFuture;
	}

	/**
	 * @param hasOpenedInFuture
	 *            the hasOpenedInFuture to set
	 */
	public void setHasOpenedInFuture(Boolean hasOpenedInFuture) {
		this.hasOpenedInFuture = hasOpenedInFuture;
	}

	/**
	 * @return the hasClosedInFuture
	 */
	public Boolean getHasClosedInFuture() {
		return hasClosedInFuture;
	}

	/**
	 * @param hasClosedInFuture
	 *            the hasClosedInFuture to set
	 */
	public void setHasClosedInFuture(Boolean hasClosedInFuture) {
		this.hasClosedInFuture = hasClosedInFuture;
	}

	/**
	 * @return the hasSalesInFuture
	 */
	public Boolean getHasSalesInFuture() {
		return hasSalesInFuture;
	}

	/**
	 * @param hasSalesInFuture
	 *            the hasSalesInFuture to set
	 */
	public void setHasSalesInFuture(Boolean hasSalesInFuture) {
		this.hasSalesInFuture = hasSalesInFuture;
	}

}
