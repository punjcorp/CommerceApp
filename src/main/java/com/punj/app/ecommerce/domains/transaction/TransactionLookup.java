package com.punj.app.ecommerce.domains.transaction;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.punj.app.ecommerce.domains.transaction.ids.TransactionLookupId;

@Entity
@Table(name = "v_txn_lookup")
public class TransactionLookup implements Serializable {

	private static final long serialVersionUID = -1215290698843145903L;

	@EmbeddedId
	private TransactionLookupId transactionLookupId;

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
	@Column(name = "business_date")
	private LocalDateTime createdDate;

	/**
	 * @return the transactionLookupId
	 */
	public TransactionLookupId getTransactionLookupId() {
		return transactionLookupId;
	}

	/**
	 * @param transactionLookupId
	 *            the transactionLookupId to set
	 */
	public void setTransactionLookupId(TransactionLookupId transactionLookupId) {
		this.transactionLookupId = transactionLookupId;
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

}
