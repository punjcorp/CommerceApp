package com.punj.app.ecommerce.domains.transaction.ids;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.punj.app.ecommerce.domains.tender.Tender;

@Embeddable
public class NoSaleId implements Serializable {

	private static final long serialVersionUID = -8404744069642573105L;

	private TransactionId txnId;

	@Column(name = "created_date")
	private LocalDateTime createdDate;

	@Column(name = "tender_id")
	private Integer tenderId;

	/**
	 * @return the txnId
	 */
	public TransactionId getTxnId() {
		return txnId;
	}

	/**
	 * @param txnId
	 *            the txnId to set
	 */
	public void setTxnId(TransactionId txnId) {
		this.txnId = txnId;
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
	 * @return the tenderId
	 */
	public Integer getTenderId() {
		return tenderId;
	}

	/**
	 * @param tenderId the tenderId to set
	 */
	public void setTenderId(Integer tenderId) {
		this.tenderId = tenderId;
	}

	
}
