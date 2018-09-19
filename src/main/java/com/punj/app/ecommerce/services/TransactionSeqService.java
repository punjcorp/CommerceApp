/**
 * 
 */
package com.punj.app.ecommerce.services;

import java.math.BigInteger;
import java.util.List;

import com.punj.app.ecommerce.domains.transaction.Transaction;
import com.punj.app.ecommerce.domains.transaction.ids.TransactionId;
import com.punj.app.ecommerce.domains.transaction.seqs.SaleInvoice;

/**
 * @author admin
 *
 */
public interface TransactionSeqService {

	public Boolean decrementSaleInvoiceNos(BigInteger invoiceNo);
	
	public BigInteger retrieveMaxSaleInvoiceSeq();
	
	public BigInteger saveTransactionSeqs(Transaction txnHeader);

	public BigInteger deleteSaleInvoice(TransactionId txnId);

	public BigInteger retrieveSaleInvoiceNo(SaleInvoice saleInvoice);

	public List<SaleInvoice> retrieveSaleInvoices(BigInteger startingInvoiceNo, BigInteger endingInvoiceNo);

	public Boolean alterSaleInvoiceSeq();
	
}
