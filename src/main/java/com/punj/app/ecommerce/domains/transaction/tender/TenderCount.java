package com.punj.app.ecommerce.domains.transaction.tender;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.punj.app.ecommerce.domains.transaction.tender.ids.TenderCountId;

@Entity
@Table(name = "txn_tender_count")
public class TenderCount implements Serializable {

	private static final long serialVersionUID = 3859115059350647476L;

	@EmbeddedId
	private TenderCountId tenderCountId;

	@Column(name = "txn_type")
	private String txnType;

	private BigDecimal amount;
	@Column(name = "media_count")
	private BigInteger mediaCount;
	@Column(name = "actual_amount")
	private BigDecimal actualAmount;
	@Column(name = "actual_media_count")
	private BigInteger actualMediaCount;

	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "created_date")
	private LocalDateTime createdDate;
	@Column(name = "modified_by")
	private String modifiedBy;
	@Column(name = "modified_date")
	private LocalDateTime modifiedDate;

	@OneToMany(mappedBy = "tenderDenominationId.tenderCountId", cascade = CascadeType.ALL)
	private List<TenderDenomination> denominations;

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
	 * @return the mediaCount
	 */
	public BigInteger getMediaCount() {
		return mediaCount;
	}

	/**
	 * @param mediaCount
	 *            the mediaCount to set
	 */
	public void setMediaCount(BigInteger mediaCount) {
		this.mediaCount = mediaCount;
	}

	/**
	 * @return the actualAmount
	 */
	public BigDecimal getActualAmount() {
		return actualAmount;
	}

	/**
	 * @param actualAmount
	 *            the actualAmount to set
	 */
	public void setActualAmount(BigDecimal actualAmount) {
		this.actualAmount = actualAmount;
	}

	/**
	 * @return the actualMediaCount
	 */
	public BigInteger getActualMediaCount() {
		return actualMediaCount;
	}

	/**
	 * @param actualMediaCount
	 *            the actualMediaCount to set
	 */
	public void setActualMediaCount(BigInteger actualMediaCount) {
		this.actualMediaCount = actualMediaCount;
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
	 * @return the tenderCountId
	 */
	public TenderCountId getTenderCountId() {
		return tenderCountId;
	}

	/**
	 * @param tenderCountId
	 *            the tenderCountId to set
	 */
	public void setTenderCountId(TenderCountId tenderCountId) {
		this.tenderCountId = tenderCountId;
	}

	/**
	 * @return the denominations
	 */
	public List<TenderDenomination> getDenominations() {
		return denominations;
	}

	/**
	 * @param denominations
	 *            the denominations to set
	 */
	public void setDenominations(List<TenderDenomination> denominations) {
		this.denominations = denominations;
	}

}
