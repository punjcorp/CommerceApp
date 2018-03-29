/**
 * 
 */
package com.punj.app.ecommerce.models.financials;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author admin
 *
 */
public class AccountDTO {

	private Integer locationId;
	private Integer registerId;
	@JsonFormat(pattern = "dd-MMM-yy HH:mm:ss")
	private LocalDateTime businessDate;
	@NotNull(message = "{commerce.error.item.empty}")
	private Integer txnNo;

	private Integer accountId;
	private String entityType;
	private BigInteger entityId;

	private BigDecimal advanceAmt;
	private BigDecimal dueAmt;
	private BigDecimal paymentAmt;
	private String createdBy;
	private LocalDateTime createdDate;

	private String remarks;

	// Temporary Payment Details for first form
	private String journalType;

	// Temporary Tender Details for first form
	private String defaultTender;
	private String tenderId;
	private String tenderName;

	private BigDecimal tenderAmount;
	private String tenderType;
	private String accountNo;
	private String bankName;
	private String bankBranch;
	private String tenderComment;

	private List<AccountJournalBean> accountJournalList;

	/**
	 * @return the accountJournalList
	 */
	public List<AccountJournalBean> getAccountJournalList() {
		return accountJournalList;
	}

	/**
	 * @param accountJournalList
	 *            the accountJournalList to set
	 */
	public void setAccountJournalList(List<AccountJournalBean> accountJournalList) {
		this.accountJournalList = accountJournalList;
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
	 * @return the entityType
	 */
	public String getEntityType() {
		return entityType;
	}

	/**
	 * @param entityType
	 *            the entityType to set
	 */
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	/**
	 * @return the entityId
	 */
	public BigInteger getEntityId() {
		return entityId;
	}

	/**
	 * @param entityId
	 *            the entityId to set
	 */
	public void setEntityId(BigInteger entityId) {
		this.entityId = entityId;
	}

	/**
	 * @return the advanceAmt
	 */
	public BigDecimal getAdvanceAmt() {
		return advanceAmt;
	}

	/**
	 * @param advanceAmt
	 *            the advanceAmt to set
	 */
	public void setAdvanceAmt(BigDecimal advanceAmt) {
		this.advanceAmt = advanceAmt;
	}

	/**
	 * @return the dueAmount
	 */
	public BigDecimal getDueAmt() {
		return dueAmt;
	}

	/**
	 * @param dueAmt
	 *            the dueAmt to set
	 */
	public void setDueAmt(BigDecimal dueAmt) {
		this.dueAmt = dueAmt;
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
	 * @return the defaultTender
	 */
	public String getDefaultTender() {
		return this.defaultTender;
	}

	/**
	 * @param defaultTender
	 *            the defaultTender to set
	 */
	public void setDefaultTender(String defaultTender) {
		this.defaultTender = defaultTender;
	}

	/**
	 * @return the tenderId
	 */
	public String getTenderId() {
		return tenderId;
	}

	/**
	 * @param tenderId
	 *            the tenderId to set
	 */
	public void setTenderId(String tenderId) {
		this.tenderId = tenderId;
	}

	/**
	 * @return the tenderName
	 */
	public String getTenderName() {
		return tenderName;
	}

	/**
	 * @param tenderName
	 *            the tenderName to set
	 */
	public void setTenderName(String tenderName) {
		this.tenderName = tenderName;
	}

	/**
	 * @return the tenderType
	 */
	public String getTenderType() {
		return tenderType;
	}

	/**
	 * @param tenderType
	 *            the tenderType to set
	 */
	public void setTenderType(String tenderType) {
		this.tenderType = tenderType;
	}

	/**
	 * @return the accountNo
	 */
	public String getAccountNo() {
		return accountNo;
	}

	/**
	 * @param accountNo
	 *            the accountNo to set
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param bankName
	 *            the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * @return the bankBranch
	 */
	public String getBankBranch() {
		return bankBranch;
	}

	/**
	 * @param bankBranch
	 *            the bankBranch to set
	 */
	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	/**
	 * @return the tenderComment
	 */
	public String getTenderComment() {
		return tenderComment;
	}

	/**
	 * @param tenderComment
	 *            the tenderComment to set
	 */
	public void setTenderComment(String tenderComment) {
		this.tenderComment = tenderComment;
	}

	/**
	 * @return the tenderAmount
	 */
	public BigDecimal getTenderAmount() {
		return tenderAmount;
	}

	/**
	 * @param tenderAmount
	 *            the tenderAmount to set
	 */
	public void setTenderAmount(BigDecimal tenderAmount) {
		this.tenderAmount = tenderAmount;
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
	 * @return the registerId
	 */
	public Integer getRegisterId() {
		return registerId;
	}

	/**
	 * @param registerId
	 *            the registerId to set
	 */
	public void setRegisterId(Integer registerId) {
		this.registerId = registerId;
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
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the paymentAmt
	 */
	public BigDecimal getPaymentAmt() {
		return paymentAmt;
	}

	/**
	 * @param paymentAmt
	 *            the paymentAmt to set
	 */
	public void setPaymentAmt(BigDecimal paymentAmt) {
		this.paymentAmt = paymentAmt;
	}

}
