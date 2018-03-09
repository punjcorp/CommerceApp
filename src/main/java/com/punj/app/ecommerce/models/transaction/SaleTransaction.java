/**
 * 
 */
package com.punj.app.ecommerce.models.transaction;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

/**
 * @author admin
 *
 */
public class SaleTransaction {
	@Valid
	private TransactionHeader transactionHeader;
	private List<SaleLineItem> txnSaleLineItems;
	private List<TenderLineItem> txnTenderLineItems;

	public SaleTransaction() {
		transactionHeader = new TransactionHeader();
		txnSaleLineItems = new ArrayList<>();
		txnTenderLineItems = new ArrayList<>();
	}

	/**
	 * @return the transactionHeader
	 */
	public TransactionHeader getTransactionHeader() {
		return transactionHeader;
	}

	/**
	 * @param transactionHeader
	 *            the transactionHeader to set
	 */
	public void setTransactionHeader(TransactionHeader transactionHeader) {
		this.transactionHeader = transactionHeader;
	}

	/**
	 * @return the txnSaleLineItems
	 */
	public List<SaleLineItem> getTxnSaleLineItems() {
		return txnSaleLineItems;
	}

	/**
	 * @param txnSaleLineItems
	 *            the txnSaleLineItems to set
	 */
	public void setTxnSaleLineItems(List<SaleLineItem> txnSaleLineItems) {
		this.txnSaleLineItems = txnSaleLineItems;
	}

	/**
	 * @return the txnTenderLineItems
	 */
	public List<TenderLineItem> getTxnTenderLineItems() {
		return txnTenderLineItems;
	}

	/**
	 * @param txnTenderLineItems
	 *            the txnTenderLineItems to set
	 */
	public void setTxnTenderLineItems(List<TenderLineItem> txnTenderLineItems) {
		this.txnTenderLineItems = txnTenderLineItems;
	}

}
