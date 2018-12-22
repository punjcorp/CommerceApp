/**
 * 
 */
package com.punj.app.ecommerce.services.dtos.transaction;

import java.util.List;

import com.punj.app.ecommerce.domains.customer.Customer;
import com.punj.app.ecommerce.domains.payment.AccountJournal;
import com.punj.app.ecommerce.domains.supplier.Supplier;
import com.punj.app.ecommerce.domains.transaction.NoSaleTransaction;
import com.punj.app.ecommerce.domains.transaction.SaleLineItem;
import com.punj.app.ecommerce.domains.transaction.TaxLineItem;
import com.punj.app.ecommerce.domains.transaction.TenderLineItem;
import com.punj.app.ecommerce.domains.transaction.Transaction;
import com.punj.app.ecommerce.domains.transaction.TransactionCustomer;
import com.punj.app.ecommerce.domains.transaction.TransactionLineItem;

/**
 * @author admin
 *
 */
public class TransactionDTO {
	private Transaction txn;
	private List<TransactionLineItem> txnLineItems;
	private List<SaleLineItem> saleLineItems;
	private List<TaxLineItem> taxLineItems;
	private List<TenderLineItem> tenderLineItems;
	private TransactionCustomer txnCustomer;
	private Customer customer;
	private Supplier supplier;
	private NoSaleTransaction noSaleTxn;
	private AccountJournal journalDetails;

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
	public List<TransactionLineItem> getTxnLineItems() {
		return txnLineItems;
	}

	/**
	 * @param txnLineItems
	 *            the txnLineItems to set
	 */
	public void setTxnLineItems(List<TransactionLineItem> txnLineItems) {
		this.txnLineItems = txnLineItems;
	}

	/**
	 * @return the saleLineItems
	 */
	public List<SaleLineItem> getSaleLineItems() {
		return saleLineItems;
	}

	/**
	 * @param saleLineItems
	 *            the saleLineItems to set
	 */
	public void setSaleLineItems(List<SaleLineItem> saleLineItems) {
		this.saleLineItems = saleLineItems;
	}

	/**
	 * @return the taxLineItems
	 */
	public List<TaxLineItem> getTaxLineItems() {
		return taxLineItems;
	}

	/**
	 * @param taxLineItems
	 *            the taxLineItems to set
	 */
	public void setTaxLineItems(List<TaxLineItem> taxLineItems) {
		this.taxLineItems = taxLineItems;
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
	 * @return the customer
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * @param customer
	 *            the customer to set
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	/**
	 * @return the noSaleTxn
	 */
	public NoSaleTransaction getNoSaleTxn() {
		return noSaleTxn;
	}

	/**
	 * @param noSaleTxn
	 *            the noSaleTxn to set
	 */
	public void setNoSaleTxn(NoSaleTransaction noSaleTxn) {
		this.noSaleTxn = noSaleTxn;
	}

	/**
	 * @return the journalDetails
	 */
	public AccountJournal getJournalDetails() {
		return journalDetails;
	}

	/**
	 * @param journalDetails
	 *            the journalDetails to set
	 */
	public void setJournalDetails(AccountJournal journalDetails) {
		this.journalDetails = journalDetails;
	}

	/**
	 * @return the supplier
	 */
	public Supplier getSupplier() {
		return supplier;
	}

	/**
	 * @param supplier
	 *            the supplier to set
	 */
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

}
