/**
 * 
 */
package com.punj.app.ecommerce.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.punj.app.ecommerce.domains.finance.TenderMovement;
import com.punj.app.ecommerce.domains.transaction.Transaction;
import com.punj.app.ecommerce.domains.transaction.TransactionLookup;
import com.punj.app.ecommerce.domains.transaction.TransactionReceipt;
import com.punj.app.ecommerce.domains.transaction.ids.TransactionId;
import com.punj.app.ecommerce.services.dtos.transaction.SaleTransactionReceiptDTO;
import com.punj.app.ecommerce.services.dtos.transaction.TransactionDTO;
import com.punj.app.ecommerce.services.dtos.transaction.TransactionIdDTO;

/**
 * @author admin
 *
 */
public interface TransactionService {

	public Transaction saveTransaction(Transaction txnDetails);

	public Transaction createTransactionInstance(TransactionIdDTO txnIdDTO, String username, String txnType);

	public Transaction searchTxnByCriteria(Integer locationId, Set<String> txnTypes);

	public Map<Integer, Transaction> searchRegisterTxnByCriteria(Integer locationId, Set<String> txnTypes);

	public Transaction searchLocationOpenTxn(Integer locationId, LocalDateTime businessDate);

	public TransactionId saveSaleTransaction(TransactionDTO txnDTO);

	public SaleTransactionReceiptDTO generateTransactionReceipt(TransactionId txnId);

	public Boolean saveTransactionReceipt(List<TransactionReceipt> txnReceipts);

	public TenderMovement saveTenderMoveTxn(TenderMovement tenderMove);

	public TransactionReceipt retrieveLastTransaction(TransactionId txnIdCriteria);

	public Boolean isTransactionAllowed(Integer locationId, Integer registerId);

	public List<TransactionLookup> searchTxnJournals(String txnType, LocalDateTime startDate, LocalDateTime endDate);

	public TransactionDTO searchTransactionDetails(String uniqueTxnNo);

	public List<TransactionDTO> searchTransactionDetailsByMonth(Integer locationId, String finMonth);

	public TransactionReceipt searchTransactionReceipt(String uniqueTxnNo, String receiptType);

	public List<Transaction> searchAllFutureTxns(Integer locationId, LocalDateTime businessDate);

	public void deleteAllFutureTxns(Integer locationId, LocalDateTime businessDate);

	public Transaction searchStoreCloseTxn(Integer locationId, LocalDateTime businessDate);

	public Transaction searchLastRegCloseTxn(Integer locationId, LocalDateTime businessDate);

	public void deleteRegCloseTxn(TransactionId txnId);

	public void deleteStoreCloseTxn(TransactionId txnId);
}
