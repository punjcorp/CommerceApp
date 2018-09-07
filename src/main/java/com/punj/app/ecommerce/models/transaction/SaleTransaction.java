/**
 * 
 */
package com.punj.app.ecommerce.models.transaction;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.punj.app.ecommerce.models.customer.CustomerBean;
import com.punj.app.ecommerce.models.customer.or.CustomerORBean;
import com.punj.app.ecommerce.models.shipment.ShipmentBean;

/**
 * @author admin
 *
 */
public class SaleTransaction {
	@Valid
	private TransactionHeader transactionHeader;
	private List<SaleLineItem> txnSaleLineItems;
	private List<TenderLineItem> txnTenderLineItems;
	private CustomerBean customer;
	private ShipmentBean shipment;
	private CustomerORBean orderRequest;

	private BigInteger invoiceNo;

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

	/**
	 * @return the customer
	 */
	public CustomerBean getCustomer() {
		return customer;
	}

	/**
	 * @param customer
	 *            the customer to set
	 */
	public void setCustomer(CustomerBean customer) {
		this.customer = customer;
	}

	/**
	 * @return the shipment
	 */
	public ShipmentBean getShipment() {
		return shipment;
	}

	/**
	 * @param shipment
	 *            the shipment to set
	 */
	public void setShipment(ShipmentBean shipment) {
		this.shipment = shipment;
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
	 * @param invoiceNo the invoiceNo to set
	 */
	public void setInvoiceNo(BigInteger invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

}
