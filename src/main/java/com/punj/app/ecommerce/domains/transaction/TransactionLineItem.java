package com.punj.app.ecommerce.domains.transaction;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.punj.app.ecommerce.domains.transaction.ids.TransactionLineItemId;

@Entity
@Table(name = "txn_line_item_master")
public class TransactionLineItem implements Serializable {

	private static final long serialVersionUID = 719509369401510233L;

	@EmbeddedId
	private TransactionLineItemId transactionLineItemId;

	@Column(name = "type_code")
	private String lineItemType;

	@Column(name = "void_flag")
	private Boolean voidFlag;
	@Column(name = "void_reason_code")
	private String voidReason;
	private String status;

	@Column(name = "begin_date")
	private LocalDateTime startTime;
	@Column(name = "end_date")
	private LocalDateTime endTime;

	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "created_date")
	private LocalDateTime createdDate;
	@Column(name = "modified_by")
	private String modifiedBy;
	@Column(name = "modified_date")
	private LocalDateTime modifiedDate;

	/**
	 * @return the transactionLineItemId
	 */
	public TransactionLineItemId getTransactionLineItemId() {
		return transactionLineItemId;
	}

	/**
	 * @param transactionLineItemId
	 *            the transactionLineItemId to set
	 */
	public void setTransactionLineItemId(TransactionLineItemId transactionLineItemId) {
		this.transactionLineItemId = transactionLineItemId;
	}

	/**
	 * @return the voidFlag
	 */
	public Boolean getVoidFlag() {
		return voidFlag;
	}

	/**
	 * @param voidFlag
	 *            the voidFlag to set
	 */
	public void setVoidFlag(Boolean voidFlag) {
		this.voidFlag = voidFlag;
	}

	/**
	 * @return the voidReason
	 */
	public String getVoidReason() {
		return voidReason;
	}

	/**
	 * @param voidReason
	 *            the voidReason to set
	 */
	public void setVoidReason(String voidReason) {
		this.voidReason = voidReason;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the startTime
	 */
	public LocalDateTime getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public LocalDateTime getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
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

	/**
	 * @return the lineItemType
	 */
	public String getLineItemType() {
		return lineItemType;
	}

	/**
	 * @param lineItemType
	 *            the lineItemType to set
	 */
	public void setLineItemType(String lineItemType) {
		this.lineItemType = lineItemType;
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
		result = prime * result + ((lineItemType == null) ? 0 : lineItemType.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((transactionLineItemId == null) ? 0 : transactionLineItemId.hashCode());
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
		TransactionLineItem other = (TransactionLineItem) obj;
		if (lineItemType == null) {
			if (other.lineItemType != null) {
				return false;
			}
		} else if (!lineItemType.equals(other.lineItemType)) {
			return false;
		}
		if (status == null) {
			if (other.status != null) {
				return false;
			}
		} else if (!status.equals(other.status)) {
			return false;
		}
		if (transactionLineItemId == null) {
			if (other.transactionLineItemId != null) {
				return false;
			}
		} else if (!transactionLineItemId.equals(other.transactionLineItemId)) {
			return false;
		}
		return true;
	}

}
