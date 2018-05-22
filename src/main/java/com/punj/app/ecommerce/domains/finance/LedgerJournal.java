/**
 * 
 */
package com.punj.app.ecommerce.domains.finance;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author admin
 *
 */
@Entity
@Table(name = "ledger_journal")
public class LedgerJournal implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1422756638978094327L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ledger_journal_id", updatable = false, nullable = false)
	private BigInteger ledgerJournalId;

	@Column(name = "txn_no")
	private String txnNo;
	@Column(name = "txn_type")
	private String txnType;

	@Column(name = "location_id")
	private Integer locationId;
	@Column(name = "business_date")
	private LocalDateTime businessDate;

	@Column(name = "action_code")
	private String actionType;
	private BigDecimal amount;

	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "created_date")
	private LocalDateTime createdDate;

	/**
	 * @return the ledgerJournalId
	 */
	public BigInteger getLedgerJournalId() {
		return ledgerJournalId;
	}

	/**
	 * @param ledgerJournalId
	 *            the ledgerJournalId to set
	 */
	public void setLedgerJournalId(BigInteger ledgerJournalId) {
		this.ledgerJournalId = ledgerJournalId;
	}

	/**
	 * @return the txnNo
	 */
	public String getTxnNo() {
		return txnNo;
	}

	/**
	 * @param txnNo
	 *            the txnNo to set
	 */
	public void setTxnNo(String txnNo) {
		this.txnNo = txnNo;
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
	 * @return the actionType
	 */
	public String getActionType() {
		return actionType;
	}

	/**
	 * @param actionType
	 *            the actionType to set
	 */
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	/**
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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

}
