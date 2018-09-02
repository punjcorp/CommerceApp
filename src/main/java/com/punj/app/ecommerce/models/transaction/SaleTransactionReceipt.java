/**
 * 
 */
package com.punj.app.ecommerce.models.transaction;

import java.math.BigInteger;
import java.util.List;

import com.punj.app.ecommerce.models.common.LocationBean;
import com.punj.app.ecommerce.models.customer.CustomerBean;
import com.punj.app.ecommerce.models.customer.or.CustomerORBean;
import com.punj.app.ecommerce.models.shipment.ShipmentBean;

/**
 * @author admin
 *
 */
public class SaleTransactionReceipt {
	private BigInteger invoiceNo;
	private LocationBean locationDetails;
	private TransactionHeader transactionHeader;
	private CustomerBean customer;
	private List<SaleReceiptLineItem> txnSaleLineItems;
	private List<TenderLineItem> txnTenderLineItems;
	private ShipmentBean shipmentDetails;
	private CustomerORBean orderRequest;

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
	 * @return the shipmentDetails
	 */
	public ShipmentBean getShipmentDetails() {
		return shipmentDetails;
	}

	/**
	 * @param shipmentDetails
	 *            the shipmentDetails to set
	 */
	public void setShipmentDetails(ShipmentBean shipmentDetails) {
		this.shipmentDetails = shipmentDetails;
	}

	/**
	 * @return the orderRequest
	 */
	public CustomerORBean getOrderRequest() {
		return orderRequest;
	}

	/**
	 * @param orderRequest
	 *            the orderRequest to set
	 */
	public void setOrderRequest(CustomerORBean orderRequest) {
		this.orderRequest = orderRequest;
	}

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

}
