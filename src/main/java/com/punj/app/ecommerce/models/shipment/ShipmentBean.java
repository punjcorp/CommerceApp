/**
 * 
 */
package com.punj.app.ecommerce.models.shipment;

import java.math.BigInteger;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author admin
 *
 */
public class ShipmentBean {

	private BigInteger shipmentId;
	private String shippingCompany;
	private String gpPrNo;
	@JsonFormat(pattern = "dd-MMM-yy HH:mm:ss")
	private LocalDateTime gpPrDate;

	private String driverName;
	private String driverPhone;
	private String vehicleNo;

	private String shippingInstructions;

	/**
	 * @return the shipmentId
	 */
	public BigInteger getShipmentId() {
		return shipmentId;
	}

	/**
	 * @param shipmentId
	 *            the shipmentId to set
	 */
	public void setShipmentId(BigInteger shipmentId) {
		this.shipmentId = shipmentId;
	}

	/**
	 * @return the shippingCompany
	 */
	public String getShippingCompany() {
		return shippingCompany;
	}

	/**
	 * @param shippingCompany
	 *            the shippingCompany to set
	 */
	public void setShippingCompany(String shippingCompany) {
		this.shippingCompany = shippingCompany;
	}

	/**
	 * @return the gpPrNo
	 */
	public String getGpPrNo() {
		return gpPrNo;
	}

	/**
	 * @param gpPrNo
	 *            the gpPrNo to set
	 */
	public void setGpPrNo(String gpPrNo) {
		this.gpPrNo = gpPrNo;
	}

	/**
	 * @return the gpPrDate
	 */
	public LocalDateTime getGpPrDate() {
		return gpPrDate;
	}

	/**
	 * @param gpPrDate
	 *            the gpPrDate to set
	 */
	public void setGpPrDate(LocalDateTime gpPrDate) {
		this.gpPrDate = gpPrDate;
	}

	/**
	 * @return the driverName
	 */
	public String getDriverName() {
		return driverName;
	}

	/**
	 * @param driverName
	 *            the driverName to set
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	/**
	 * @return the driverPhone
	 */
	public String getDriverPhone() {
		return driverPhone;
	}

	/**
	 * @param driverPhone
	 *            the driverPhone to set
	 */
	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}

	/**
	 * @return the vehicleNo
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * @param vehicleNo
	 *            the vehicleNo to set
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * @return the shippingInstructions
	 */
	public String getShippingInstructions() {
		return shippingInstructions;
	}

	/**
	 * @param shippingInstructions
	 *            the shippingInstructions to set
	 */
	public void setShippingInstructions(String shippingInstructions) {
		this.shippingInstructions = shippingInstructions;
	}

}
