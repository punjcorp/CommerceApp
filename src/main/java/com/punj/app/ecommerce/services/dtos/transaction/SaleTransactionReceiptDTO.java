/**
 * 
 */
package com.punj.app.ecommerce.services.dtos.transaction;

import java.util.List;

import com.punj.app.ecommerce.domains.common.Location;
import com.punj.app.ecommerce.domains.customer.Customer;
import com.punj.app.ecommerce.domains.supplier.Supplier;
import com.punj.app.ecommerce.domains.transaction.ReceiptItemTax;
import com.punj.app.ecommerce.domains.transaction.TenderLineItem;
import com.punj.app.ecommerce.domains.transaction.Transaction;
import com.punj.app.ecommerce.domains.transaction.TransactionCustomer;

/**
 * @author admin
 *
 */
public class SaleTransactionReceiptDTO {
	private Location location;
	private Transaction txn;
	private List<ReceiptItemTax> txnLineItems;
	private List<TenderLineItem> tenderLineItems;
	private TransactionCustomer txnCustomer;
	private Customer customerDetails;
	private Supplier supplierDetails;
	/**
	 * @return the txn
	 */
	public Transaction getTxn() {
		return txn;
	}
	/**
	 * @param txn the txn to set
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
	 * @param txnLineItems the txnLineItems to set
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
	 * @param tenderLineItems the tenderLineItems to set
	 */
	public void setTenderLineItems(List<TenderLineItem> tenderLineItems) {
		this.tenderLineItems = tenderLineItems;
	}
	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	public TransactionCustomer getTxnCustomer() {
		return txnCustomer;
	}

	public void setTxnCustomer(TransactionCustomer txnCustomer) {
		this.txnCustomer = txnCustomer;
	}

	public Customer getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomerDetails(Customer customerDetails) {
		this.customerDetails = customerDetails;
	}

	public Supplier getSupplierDetails() {
		return supplierDetails;
	}

	public void setSupplierDetails(Supplier supplierDetails) {
		this.supplierDetails = supplierDetails;
	}
}
