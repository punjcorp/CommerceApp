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
@Table(name = "v_txn_lookup")
public class TransactionLookup implements Serializable {

	private static final long serialVersionUID = 5775571729302818526L;

	@EmbeddedId
	private TransactionId transactionId;

	@Column(name = "txn_type")
	private String txnType;

	@Column(name = "unique_txn")
	private String uniqueTxnNo;

	@Column(name = "customer_name")
	private String customerName;

	@Column(name = "total_amount")
	private BigDecimal totalAmt;
	@Column(name = "tax_total")
	private BigDecimal taxTotalAmt;

	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "created_date")
	private LocalDateTime createdDate;

	@Column(name = "reason_code")
	private Integer reasonCode;

	@Column(name = "reason_name")
	private String reasonName;

	@Column(name = "reason_type")
	private String reasonType;

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
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param customerName
	 *            the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @return the uniqueTxnNo
	 */
	public String getUniqueTxnNo() {
		return uniqueTxnNo;
	}

	/**
	 * @param uniqueTxnNo
	 *            the uniqueTxnNo to set
	 */
	public void setUniqueTxnNo(String uniqueTxnNo) {
		this.uniqueTxnNo = uniqueTxnNo;
	}

	/**
	 * @return the reasonCode
	 */
	public Integer getReasonCode() {
		return reasonCode;
	}

	/**
	 * @param reasonCode
	 *            the reasonCode to set
	 */
	public void setReasonCode(Integer reasonCode) {
		this.reasonCode = reasonCode;
	}

	/**
	 * @return the reasonName
	 */
	public String getReasonName() {
		return reasonName;
	}

	/**
	 * @param reasonName
	 *            the reasonName to set
	 */
	public void setReasonName(String reasonName) {
		this.reasonName = reasonName;
	}

	/**
	 * @return the reasonType
	 */
	public String getReasonType() {
		return reasonType;
	}

	/**
	 * @param reasonType
	 *            the reasonType to set
	 */
	public void setReasonType(String reasonType) {
		this.reasonType = reasonType;
	}

}
