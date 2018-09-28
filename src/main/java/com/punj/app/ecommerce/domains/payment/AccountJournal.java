package com.punj.app.ecommerce.domains.payment;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.search.annotations.DocumentId;

@Entity
@Table(name = "account_journal")
public class AccountJournal implements Serializable {

	private static final long serialVersionUID = -8823308141175745432L;

	@Id
	@DocumentId
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "journal_id", updatable = false, nullable = false)
	private BigInteger journalId;

	@Column(name = "account_id")
	private Integer accountId;
	
	@Column(name = "journal_type")
	private String journalType;

	private BigDecimal amount;
	
	@Column(name = "ledger_generated")
	private String ledgerGenerated;

	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "created_date")
	private LocalDateTime createdDate;

	@Column(name = "modified_by")
	private String modifiedBy;
	@Column(name = "modified_date")
	private LocalDateTime modifiedDate;

	private String comments;

	@OneToMany(mappedBy = "journalTenderId.accountJournal", cascade = CascadeType.ALL)
	List<JournalTender> journalTenders;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "journal_id")
	AJReceipt journalReceipt;

	/**
	 * @return the journalId
	 */
	public BigInteger getJournalId() {
		return journalId;
	}

	/**
	 * @param journalId
	 *            the journalId to set
	 */
	public void setJournalId(BigInteger journalId) {
		this.journalId = journalId;
	}

	/**
	 * @return the accountId
	 */
	public Integer getAccountId() {
		return accountId;
	}

	/**
	 * @param accountId
	 *            the accountId to set
	 */
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	/**
	 * @return the journalType
	 */
	public String getJournalType() {
		return journalType;
	}

	/**
	 * @param journalType
	 *            the journalType to set
	 */
	public void setJournalType(String journalType) {
		this.journalType = journalType;
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
	 * @return the journalTenders
	 */
	public List<JournalTender> getJournalTenders() {
		return journalTenders;
	}

	/**
	 * @param journalTenders
	 *            the journalTenders to set
	 */
	public void setJournalTenders(List<JournalTender> journalTenders) {
		this.journalTenders = journalTenders;
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

	/**
	 * @return the journalReceipt
	 */
	public AJReceipt getJournalReceipt() {
		return journalReceipt;
	}

	/**
	 * @param journalReceipt
	 *            the journalReceipt to set
	 */
	public void setJournalReceipt(AJReceipt journalReceipt) {
		this.journalReceipt = journalReceipt;
	}

	/**
	 * @return the ledgerGenerated
	 */
	public String getLedgerGenerated() {
		return ledgerGenerated;
	}

	/**
	 * @param ledgerGenerated the ledgerGenerated to set
	 */
	public void setLedgerGenerated(String ledgerGenerated) {
		this.ledgerGenerated = ledgerGenerated;
	}

}
