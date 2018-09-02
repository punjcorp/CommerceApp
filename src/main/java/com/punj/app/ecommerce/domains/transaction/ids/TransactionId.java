package com.punj.app.ecommerce.domains.transaction.ids;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Transient;

import com.punj.app.ecommerce.utils.Utils;

@Embeddable
public class TransactionId implements Serializable {

	private static final long serialVersionUID = 8165680113853045035L;

	private static final int REGISTER_SIZE = 3;
	private static final int LOCATION_SIZE = 4;
	private static final int TXN_SIZE = 5;

	@Column(name = "location_id")
	private Integer locationId;

	@Column(name = "business_date")
	private LocalDateTime businessDate;

	@Column(name = "register")
	private Integer register;

	@Column(name = "txn_no")
	private Integer transactionSeq;


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
	 * @return the transactionSeq
	 */
	public Integer getTransactionSeq() {
		return transactionSeq;
	}

	/**
	 * @param transactionSeq
	 *            the transactionSeq to set
	 */
	public void setTransactionSeq(Integer transactionSeq) {
		this.transactionSeq = transactionSeq;
	}

	/**
	 * This method returns the actual transaction ID which need to be stored in the database and will be printed on receipts
	 */
	public String toString() {

		return String.join("", StringUtils.leftPad(this.locationId.toString(), LOCATION_SIZE, '0'), StringUtils.leftPad(this.register.toString(), REGISTER_SIZE, '0'),
				StringUtils.leftPad(this.transactionSeq.toString(), TXN_SIZE, '0'), Utils.formatDate(this.businessDate));
	}

}
