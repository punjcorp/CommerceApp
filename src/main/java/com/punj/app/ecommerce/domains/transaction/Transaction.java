package com.punj.app.ecommerce.domains.transaction;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.punj.app.ecommerce.domains.transaction.ids.TransactionId;

@Entity
@Table(name = "txn_master")
public class Transaction implements Serializable {

	private static final long serialVersionUID = 5681811399121746778L;

	@EmbeddedId
	private TransactionId transactionId;

	@Column(name = "offline_flag")
	private Boolean offlineFlag;
	@Column(name = "post_void_flag")
	private Boolean postVoidFlag;

	@Column(name = "session_id")
	private String sessionId;

	@Column(name = "total")
	private BigDecimal totalAmt;
	@Column(name = "tax_total")
	private BigDecimal taxTotalAmt;
	@Column(name = "discount_total")
	private BigDecimal discountTotalAmt;
	@Column(name = "rounded_amount")
	private BigDecimal roundedTotalAmt;
	@Column(name = "subtotal")
	private BigDecimal subTotalAmt;

	@Column(name = "cancel_reason_code")
	private String cancelReason;

	@Column(name = "txn_type")
	private String txnType;
	private String status;
	private String comments;

	@Column(name = "start_time")
	private LocalDateTime startTime;
	@Column(name = "end_time")
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
	 * @return the transactionId
	 */
	public TransactionId getTransactionId() {
		return transactionId;
	}

	/**
	 * @param transactionId
	 *            the transactionId to set
	 */
	public void setTransactionId(TransactionId transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * @return the offlineFlag
	 */
	public Boolean getOfflineFlag() {
		return offlineFlag;
	}

	/**
	 * @param offlineFlag
	 *            the offlineFlag to set
	 */
	public void setOfflineFlag(Boolean offlineFlag) {
		this.offlineFlag = offlineFlag;
	}

	/**
	 * @return the postVoidFlag
	 */
	public Boolean getPostVoidFlag() {
		return postVoidFlag;
	}

	/**
	 * @param postVoidFlag
	 *            the postVoidFlag to set
	 */
	public void setPostVoidFlag(Boolean postVoidFlag) {
		this.postVoidFlag = postVoidFlag;
	}

	/**
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * @param sessionId
	 *            the sessionId to set
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * @return the totalAmt
	 */
	public BigDecimal getTotalAmt() {
		return totalAmt;
	}

	/**
	 * @param totalAmt
	 *            the totalAmt to set
	 */
	public void setTotalAmt(BigDecimal totalAmt) {
		this.totalAmt = totalAmt;
	}

	/**
	 * @return the taxTotalAmt
	 */
	public BigDecimal getTaxTotalAmt() {
		return taxTotalAmt;
	}

	/**
	 * @param taxTotalAmt
	 *            the taxTotalAmt to set
	 */
	public void setTaxTotalAmt(BigDecimal taxTotalAmt) {
		this.taxTotalAmt = taxTotalAmt;
	}

	/**
	 * @return the roundedTotalAmt
	 */
	public BigDecimal getRoundedTotalAmt() {
		return roundedTotalAmt;
	}

	/**
	 * @param roundedTotalAmt
	 *            the roundedTotalAmt to set
	 */
	public void setRoundedTotalAmt(BigDecimal roundedTotalAmt) {
		this.roundedTotalAmt = roundedTotalAmt;
	}

	/**
	 * @return the subTotalAmt
	 */
	public BigDecimal getSubTotalAmt() {
		return subTotalAmt;
	}

	/**
	 * @param subTotalAmt
	 *            the subTotalAmt to set
	 */
	public void setSubTotalAmt(BigDecimal subTotalAmt) {
		this.subTotalAmt = subTotalAmt;
	}

	/**
	 * @return the cancelReason
	 */
	public String getCancelReason() {
		return cancelReason;
	}

	/**
	 * @param cancelReason
	 *            the cancelReason to set
	 */
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	/**
	 * @return the txnType
	 */
	public String getTxnType() {
		return txnType;
	}

	/**
	 * @param txnType
	 *            the txnType to set
	 */
	public void setTxnType(String txnType) {
		this.txnType = txnType;
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
	 * @return the discountTotalAmt
	 */
	public BigDecimal getDiscountTotalAmt() {
		return discountTotalAmt;
	}

	/**
	 * @param discountTotalAmt
	 *            the discountTotalAmt to set
	 */
	public void setDiscountTotalAmt(BigDecimal discountTotalAmt) {
		this.discountTotalAmt = discountTotalAmt;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments
	 *            the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
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
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
		result = prime * result + ((transactionId == null) ? 0 : transactionId.hashCode());
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
		Transaction other = (Transaction) obj;
		if (endTime == null) {
			if (other.endTime != null) {
				return false;
			}
		} else if (!endTime.equals(other.endTime)) {
			return false;
		}
		if (startTime == null) {
			if (other.startTime != null) {
				return false;
			}
		} else if (!startTime.equals(other.startTime)) {
			return false;
		}
		if (transactionId == null) {
			if (other.transactionId != null) {
				return false;
			}
		} else if (!transactionId.equals(other.transactionId)) {
			return false;
		}
		return true;
	}

}
