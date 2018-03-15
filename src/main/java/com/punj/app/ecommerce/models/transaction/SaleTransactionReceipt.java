/**
 * 
 */
package com.punj.app.ecommerce.models.transaction;

import java.util.ArrayList;
import java.util.List;

import com.punj.app.ecommerce.models.common.LocationBean;

/**
 * @author admin
 *
 */
public class SaleTransactionReceipt {
	private LocationBean locationInformation;
	private TransactionHeader transactionHeader;
	private List<SaleReceiptLineItem> txnSaleLineItems;
	private List<TenderLineItem> txnTenderLineItems;
	/**
	 * @return the locationInformation
	 */
	public LocationBean getLocationInformation() {
		return locationInformation;
	}
	/**
	 * @param locationInformation the locationInformation to set
	 */
	public void setLocationInformation(LocationBean locationInformation) {
		this.locationInformation = locationInformation;
	}
	/**
	 * @return the transactionHeader
	 */
	public TransactionHeader getTransactionHeader() {
		return transactionHeader;
	}
	/**
	 * @param transactionHeader the transactionHeader to set
	 */
	public void setTransactionHeader(TransactionHeader transactionHeader) {
		this.transactionHeader = transactionHeader;
	}
	/**
	 * @return the txnSaleLineItems
	 */
	public List<SaleReceiptLineItem> getTxnSaleLineItems() {
		return txnSaleLineItems;
	}
	/**
	 * @param txnSaleLineItems the txnSaleLineItems to set
	 */
	public void setTxnSaleLineItems(List<SaleReceiptLineItem> txnSaleLineItems) {
		this.txnSaleLineItems = txnSaleLineItems;
	}
	/**
	 * @return the txnTenderLineItems
	 */
	public List<TenderLineItem> getTxnTenderLineItems() {
		return txnTenderLineItems;
	}
	/**
	 * @param txnTenderLineItems the txnTenderLineItems to set
	 */
	public void setTxnTenderLineItems(List<TenderLineItem> txnTenderLineItems) {
		this.txnTenderLineItems = txnTenderLineItems;
	}

	
	
}
