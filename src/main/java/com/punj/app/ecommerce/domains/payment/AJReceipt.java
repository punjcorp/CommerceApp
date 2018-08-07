package com.punj.app.ecommerce.domains.payment;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "account_journal_receipts")
public class AJReceipt implements Serializable {

	private static final long serialVersionUID = 340025670030754135L;

	@Id
	@Column(name = "journal_id")
	private BigInteger journalId;

	@Lob
	@Column(name = "receipt_data")
	private byte[] receiptData;

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
	 * @return the receiptData
	 */
	public byte[] getReceiptData() {
		return receiptData;
	}

	/**
	 * @param receiptData
	 *            the receiptData to set
	 */
	public void setReceiptData(byte[] receiptData) {
		this.receiptData = receiptData;
	}

}
