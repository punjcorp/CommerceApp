/**
 * 
 */
package com.punj.app.ecommerce.services;

import java.math.BigInteger;

import com.punj.app.ecommerce.domains.transaction.Transaction;

/**
 * @author admin
 *
 */
public interface TransactionSeqService {

	public BigInteger saveTransactionSeqs(Transaction txnHeader);

}
