/**
 * 
 */
package com.punj.app.ecommerce.services;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.punj.app.ecommerce.domains.transaction.Transaction;
import com.punj.app.ecommerce.services.dtos.transaction.TransactionIdDTO;

/**
 * @author admin
 *
 */
public interface TransactionService {

	public Transaction saveTransaction(Transaction txnDetails);

	public Transaction createTransactionInstance(TransactionIdDTO txnIdDTO, String username, String txnType);

	public Transaction searchTxnByCriteria(Integer locationId, Set<String> txnTypes);
	
	public Map<Integer,Transaction> searchRegisterTxnByCriteria(Integer locationId, Set<String> txnTypes);
	
}
