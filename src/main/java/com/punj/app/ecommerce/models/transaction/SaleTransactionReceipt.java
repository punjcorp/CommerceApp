/**
 * 
 */
package com.punj.app.ecommerce.models.transaction;

import java.util.List;

import com.punj.app.ecommerce.models.common.LocationBean;
import com.punj.app.ecommerce.models.customer.CustomerBean;

/**
 * @author admin
 *
 */
public class SaleTransactionReceipt {
	private String applicableTax;
	private String receiptType;
	private String receiptMsg;
	private LocationBean locationDetails;
	private TransactionHeader transactionHeader;
	private CustomerBean customer;
	private List<SaleReceiptLineItem> txnSaleLineItems;
	private List<TenderLineItem> txnTenderLineItems;

	/**
	 * @return the locationDetails
	 */
	public LocationBean getLocationDetails() {
		return locationDetails;
	}

	/**
	 * @param locationDetails
	 *            the locationDetails to set
	 */
	public void setLocationDetails(LocationBean locationDetails) {
		this.locationDetails = locationDetails;
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
	public List<SaleReceiptLineItem> getTxnSaleLineItems() {
		return txnSaleLineItems;
	}

	/**
	 * @param txnSaleLineItems
	 *            the txnSaleLineItems to set
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
	 * @param txnTenderLineItems
	 *            the txnTenderLineItems to set
	 */
	public void setTxnTenderLineItems(List<TenderLineItem> txnTenderLineItems) {
		this.txnTenderLineItems = txnTenderLineItems;
	}

	public CustomerBean getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerBean customer) {
		this.customer = customer;
	}

	/**
	 * @return the applicableTax
	 */
	public String getApplicableTax() {
		return applicableTax;
	}

	/**
	 * @param applicableTax
	 *            the applicableTax to set
	 */
	public void setApplicableTax(String applicableTax) {
		this.applicableTax = applicableTax;
	}

	/**
	 * @return the receiptType
	 */
	public String getReceiptType() {
		return receiptType;
	}

	/**
	 * @param receiptType
	 *            the receiptType to set
	 */
	public void setReceiptType(String receiptType) {
		this.receiptType = receiptType;
	}

	/**
	 * @return the receiptMsg
	 */
	public String getReceiptMsg() {
		return receiptMsg;
	}

	/**
	 * @param receiptMsg
	 *            the receiptMsg to set
	 */
	public void setReceiptMsg(String receiptMsg) {
		this.receiptMsg = receiptMsg;
	}

}
