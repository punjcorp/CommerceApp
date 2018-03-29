/**
 * 
 */
package com.punj.app.ecommerce.models.financials;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * @author admin
 *
 */
public class AccountJournalBean {

	private BigInteger journalId;
	private Integer accountId;
	private String journalType;

	private BigDecimal amount;

	private String createdBy;
	private String createdDate;
	private String modifiedBy;
	private String modifiedDate;
	
	private String remarks;

	private List<JournalTenderBean> paymentTenders;

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
	public String getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate
	 *            the createdDate to set
	 */
	public void setCreatedDate(String createdDate) {
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
	public String getModifiedDate() {
		return modifiedDate;
	}

	/**
	 * @param modifiedDate
	 *            the modifiedDate to set
	 */
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the paymentTenders
	 */
	public List<JournalTenderBean> getPaymentTenders() {
		return paymentTenders;
	}

	/**
	 * @param paymentTenders the paymentTenders to set
	 */
	public void setPaymentTenders(List<JournalTenderBean> paymentTenders) {
		this.paymentTenders = paymentTenders;
	}

}
