package com.punj.app.ecommerce.domains.transaction.tender;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.punj.app.ecommerce.domains.transaction.tender.ids.TenderDenominationId;

@Entity
@Table(name = "txn_tender_denomination")
public class TenderDenomination implements Serializable {

	private static final long serialVersionUID = -7699311698115491771L;

	@EmbeddedId
	private TenderDenominationId tenderDenominationId;

	private BigDecimal amount;
	@Column(name = "media_count")
	private BigInteger mediaCount;
	@Column(name = "difference_amount")
	private BigDecimal differenceAmount;
	@Column(name = "difference_media_count")
	private BigInteger differenceMediaCount;

	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "created_date")
	private LocalDateTime createdDate;
	@Column(name = "modified_by")
	private String modifiedBy;
	@Column(name = "modified_date")
	private LocalDateTime modifiedDate;

	/**
	 * @return the tenderDenominationId
	 */
	public TenderDenominationId getTenderDenominationId() {
		return tenderDenominationId;
	}

	/**
	 * @param tenderDenominationId
	 *            the tenderDenominationId to set
	 */
	public void setTenderDenominationId(TenderDenominationId tenderDenominationId) {
		this.tenderDenominationId = tenderDenominationId;
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
	 * @return the differenceAmount
	 */
	public BigDecimal getDifferenceAmount() {
		return differenceAmount;
	}

	/**
	 * @param differenceAmount
	 *            the differenceAmount to set
	 */
	public void setDifferenceAmount(BigDecimal differenceAmount) {
		this.differenceAmount = differenceAmount;
	}

	/**
	 * @return the differenceMediaCount
	 */
	public BigInteger getDifferenceMediaCount() {
		return differenceMediaCount;
	}

	/**
	 * @param differenceMediaCount
	 *            the differenceMediaCount to set
	 */
	public void setDifferenceMediaCount(BigInteger differenceMediaCount) {
		this.differenceMediaCount = differenceMediaCount;
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
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
