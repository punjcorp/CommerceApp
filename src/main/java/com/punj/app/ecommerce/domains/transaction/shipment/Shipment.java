package com.punj.app.ecommerce.domains.transaction.shipment;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.punj.app.ecommerce.domains.transaction.ids.TransactionId;

@Entity
@Table(name = "txn_shipment")
public class Shipment implements Serializable {

	private static final long serialVersionUID = 3708619262090713132L;

	@EmbeddedId
	private TransactionId txnId;

	@Column(name = "order_request_id")
	private String orderRequestNo;
	@Column(name = "order_request_date")
	private LocalDateTime orderRequestDate;

	@Column(name = "driver_name")
	private String driverName;
	@Column(name = "driver_phone")
	private String driverPhone;
	@Column(name = "vehicle_no")
	private String vehicleNo;
	@Column(name = "transport_company")
	private String transportCompany;
	@Column(name = "gp_pr_no")
	private String gpPrNo;
	@Column(name = "gp_pr_date")
	private LocalDateTime gpPrDate;

	/**
	 * @return the txnId
	 */
	public TransactionId getTxnId() {
		return txnId;
	}

	/**
	 * @param txnId
	 *            the txnId to set
	 */
	public void setTxnId(TransactionId txnId) {
		this.txnId = txnId;
	}

	/**
	 * @return the orderRequestNo
	 */
	public String getOrderRequestNo() {
		return orderRequestNo;
	}

	/**
	 * @param orderRequestNo
	 *            the orderRequestNo to set
	 */
	public void setOrderRequestNo(String orderRequestNo) {
		this.orderRequestNo = orderRequestNo;
	}

	/**
	 * @return the orderRequestDate
	 */
	public LocalDateTime getOrderRequestDate() {
		return orderRequestDate;
	}

	/**
	 * @param orderRequestDate
	 *            the orderRequestDate to set
	 */
	public void setOrderRequestDate(LocalDateTime orderRequestDate) {
		this.orderRequestDate = orderRequestDate;
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
	 * @return the transportCompany
	 */
	public String getTransportCompany() {
		return transportCompany;
	}

	/**
	 * @param transportCompany
	 *            the transportCompany to set
	 */
	public void setTransportCompany(String transportCompany) {
		this.transportCompany = transportCompany;
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

}
