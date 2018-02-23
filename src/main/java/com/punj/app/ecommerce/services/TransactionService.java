/**
 * 
 */
package com.punj.app.ecommerce.services;

import com.punj.app.ecommerce.domains.transaction.Transaction;
import com.punj.app.ecommerce.services.dtos.transaction.TransactionIdDTO;

/**
 * @author admin
 *
 */
public interface TransactionService {

	public Transaction saveTransaction(Transaction txnDetails);

	public Transaction createTransactionInstance(TransactionIdDTO txnIdDTO, String username);

}
