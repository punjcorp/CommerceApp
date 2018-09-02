package com.punj.app.ecommerce.domains.transaction.ids;

import java.io.Serializable;
import java.math.BigInteger;

public class TxnIdDTO implements Serializable {

	private static final long serialVersionUID = 2368074292872988298L;
	private BigInteger invoiceNo;
	private TransactionId transactionId;

	/**
	 * @return the invoiceNo
	 */
	public BigInteger getInvoiceNo() {
		return invoiceNo;
	}

	/**
	 * @param invoiceNo
	 *            the invoiceNo to set
	 */
	public void setInvoiceNo(BigInteger invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

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

}
