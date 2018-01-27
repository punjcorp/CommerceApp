package com.punj.app.ecommerce.domains.transaction;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.punj.app.ecommerce.domains.transaction.ids.TransactionReceiptId;

@Entity
@Table(name = "txn_receipt")
public class TransactionReceipt implements Serializable {

	private static final long serialVersionUID = 1573241693783659639L;

	@EmbeddedId
	private TransactionReceiptId transactionReceiptId;

	@Lob
	@Column(name = "receipt_data")
	private byte[] receiptData;

	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "created_date")
	private LocalDateTime createdDate;
	@Column(name = "modified_by")
	private String modifiedBy;
	@Column(name = "modified_date")
	private LocalDateTime modifiedDate;

	/**
	 * @return the transactionReceiptId
	 */
	public TransactionReceiptId getTransactionReceiptId() {
		return transactionReceiptId;
	}

	/**
	 * @param transactionReceiptId
	 *            the transactionReceiptId to set
	 */
	public void setTransactionReceiptId(TransactionReceiptId transactionReceiptId) {
		this.transactionReceiptId = transactionReceiptId;
	}

	/**
	 * @return the receiptData
	 */
	public byte[] getReceiptData() {
		return receiptData;
	}

	/**
	 * @param receiptData
	 *            the receiptData to set
	 */
	public void setReceiptData(byte[] receiptData) {
		this.receiptData = receiptData;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result + ((transactionReceiptId == null) ? 0 : transactionReceiptId.hashCode());
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
		TransactionReceipt other = (TransactionReceipt) obj;
		if (createdDate == null) {
			if (other.createdDate != null) {
				return false;
			}
		} else if (!createdDate.equals(other.createdDate)) {
			return false;
		}
		if (transactionReceiptId == null) {
			if (other.transactionReceiptId != null) {
				return false;
			}
		} else if (!transactionReceiptId.equals(other.transactionReceiptId)) {
			return false;
		}
		return true;
	}

}
