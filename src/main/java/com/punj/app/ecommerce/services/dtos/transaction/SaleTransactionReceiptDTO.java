/**
 * 
 */
package com.punj.app.ecommerce.services.dtos.transaction;

import java.math.BigInteger;
import java.util.List;

import com.punj.app.ecommerce.domains.common.Location;
import com.punj.app.ecommerce.domains.customer.Customer;
import com.punj.app.ecommerce.domains.supplier.Supplier;
import com.punj.app.ecommerce.domains.transaction.ReceiptItemTax;
import com.punj.app.ecommerce.domains.transaction.TenderLineItem;
import com.punj.app.ecommerce.domains.transaction.Transaction;
import com.punj.app.ecommerce.domains.transaction.TransactionCustomer;
import com.punj.app.ecommerce.domains.transaction.shipment.Shipment;

/**
 * @author admin
 *
 */
public class SaleTransactionReceiptDTO {
	private BigInteger invoiceNo;
	private Location location;
	private Transaction txn;
	private List<ReceiptItemTax> txnLineItems;
	private List<TenderLineItem> tenderLineItems;
	private TransactionCustomer txnCustomer;
	private Customer customerDetails;
	private Supplier supplierDetails;
	private Shipment shipmentDetails;

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * @return the txn
	 */
	public Transaction getTxn() {
		return txn;
	}

	/**
	 * @param txn
	 *            the txn to set
	 */
	public void setTxn(Transaction txn) {
		this.txn = txn;
	}

	/**
	 * @return the txnLineItems
	 */
	public List<ReceiptItemTax> getTxnLineItems() {
		return txnLineItems;
	}

	/**
	 * @param txnLineItems
	 *            the txnLineItems to set
	 */
	public void setTxnLineItems(List<ReceiptItemTax> txnLineItems) {
		this.txnLineItems = txnLineItems;
	}

	/**
	 * @return the tenderLineItems
	 */
	public List<TenderLineItem> getTenderLineItems() {
		return tenderLineItems;
	}

	/**
	 * @param tenderLineItems
	 *            the tenderLineItems to set
	 */
	public void setTenderLineItems(List<TenderLineItem> tenderLineItems) {
		this.tenderLineItems = tenderLineItems;
	}

	/**
	 * @return the txnCustomer
	 */
	public TransactionCustomer getTxnCustomer() {
		return txnCustomer;
	}

	/**
	 * @param txnCustomer
	 *            the txnCustomer to set
	 */
	public void setTxnCustomer(TransactionCustomer txnCustomer) {
		this.txnCustomer = txnCustomer;
	}

	/**
	 * @return the customerDetails
	 */
	public Customer getCustomerDetails() {
		return customerDetails;
	}

	/**
	 * @param customerDetails
	 *            the customerDetails to set
	 */
	public void setCustomerDetails(Customer customerDetails) {
		this.customerDetails = customerDetails;
	}

	/**
	 * @return the supplierDetails
	 */
	public Supplier getSupplierDetails() {
		return supplierDetails;
	}

	/**
	 * @param supplierDetails
	 *            the supplierDetails to set
	 */
	public void setSupplierDetails(Supplier supplierDetails) {
		this.supplierDetails = supplierDetails;
	}

	/**
	 * @return the shipmentDetails
	 */
	public Shipment getShipmentDetails() {
		return shipmentDetails;
	}

	/**
	 * @param shipmentDetails
	 *            the shipmentDetails to set
	 */
	public void setShipmentDetails(Shipment shipmentDetails) {
		this.shipmentDetails = shipmentDetails;
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
