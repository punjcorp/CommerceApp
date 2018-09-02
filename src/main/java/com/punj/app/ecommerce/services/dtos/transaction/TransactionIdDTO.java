package com.punj.app.ecommerce.services.dtos.transaction;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;

import com.punj.app.ecommerce.utils.Utils;

public class TransactionIdDTO implements Serializable {

	private static final long serialVersionUID = -392183503045093764L;

	private static final int REGISTER_SIZE = 3;
	private static final int LOCATION_SIZE = 4;
	private static final int TXN_SIZE = 5;

	private Integer locationId;
	private LocalDateTime businessDate;
	private Integer register;
	private Integer txnNo;

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
	 * @return the register
	 */
	public Integer getRegister() {
		return register;
	}

	/**
	 * @param register
	 *            the register to set
	 */
	public void setRegister(Integer register) {
		this.register = register;
	}

	/**
	 * @return the txnNo
	 */
	public Integer getTxnNo() {
		return txnNo;
	}

	/**
	 * @param txnNo
	 *            the txnNo to set
	 */
	public void setTxnNo(Integer txnNo) {
		this.txnNo = txnNo;
	}

	/**
	 * This method returns the actual transaction ID which need to be stored in the database and will be printed on receipts
	 */
	public String toString() {

		return String.join("", StringUtils.leftPad(this.locationId.toString(), LOCATION_SIZE, '0'), StringUtils.leftPad(this.register.toString(), REGISTER_SIZE, '0'),
				StringUtils.leftPad(this.txnNo.toString(), TXN_SIZE, '0'), Utils.formatDate(this.businessDate));
	}

}
