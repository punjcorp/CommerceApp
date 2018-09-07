/**
 * 
 */
package com.punj.app.ecommerce.services;

import java.math.BigInteger;

import com.punj.app.ecommerce.domains.transaction.Transaction;
import com.punj.app.ecommerce.domains.transaction.seqs.SaleInvoice;

/**
 * @author admin
 *
 */
public interface TransactionSeqService {

	public BigInteger saveTransactionSeqs(Transaction txnHeader);

	public BigInteger retrieveSaleInvoiceNo(SaleInvoice saleInvoice);

}
