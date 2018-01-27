package com.punj.app.ecommerce.domains.transaction.ids;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.StringUtils;

import com.punj.app.ecommerce.utils.Utils;

@Embeddable
public class TransactionReceiptId implements Serializable {

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

	@Column(name = "receipt_type")
	private String receiptType;

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
	 * @return the receiptType
	 */
	public String getReceiptType() {
		return receiptType;
	}

	/**
	 * @param receiptType
	 *            the receiptType to set
	 */
	public void setReceiptType(String receiptType) {
		this.receiptType = receiptType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((businessDate == null) ? 0 : businessDate.hashCode());
		result = prime * result + ((locationId == null) ? 0 : locationId.hashCode());
		result = prime * result + ((receiptType == null) ? 0 : receiptType.hashCode());
		result = prime * result + ((register == null) ? 0 : register.hashCode());
		result = prime * result + ((transactionSeq == null) ? 0 : transactionSeq.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		TransactionReceiptId other = (TransactionReceiptId) obj;
		if (businessDate == null) {
			if (other.businessDate != null) {
				return false;
			}
		} else if (!businessDate.equals(other.businessDate)) {
			return false;
		}
		if (locationId == null) {
			if (other.locationId != null) {
				return false;
			}
		} else if (!locationId.equals(other.locationId)) {
			return false;
		}
		if (receiptType == null) {
			if (other.receiptType != null) {
				return false;
			}
		} else if (!receiptType.equals(other.receiptType)) {
			return false;
		}
		if (register == null) {
			if (other.register != null) {
				return false;
			}
		} else if (!register.equals(other.register)) {
			return false;
		}
		if (transactionSeq == null) {
			if (other.transactionSeq != null) {
				return false;
			}
		} else if (!transactionSeq.equals(other.transactionSeq)) {
			return false;
		}
		return true;
	}

	/**
	 * This method returns the actual transaction ID which need to be stored in the
	 * database and will be printed on receipts
	 */
	public String toString() {

		return String.join("", StringUtils.leftPad(this.locationId.toString(), LOCATION_SIZE, '0'),
				StringUtils.leftPad(this.register.toString(), REGISTER_SIZE, '0'),
				StringUtils.leftPad(this.transactionSeq.toString(), TXN_SIZE, '0'),
				Utils.formatDate(this.businessDate));
	}

}
