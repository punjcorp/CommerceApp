/**
 * 
 */
package com.punj.app.ecommerce.services.impl;

import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.punj.app.ecommerce.domains.transaction.Transaction;
import com.punj.app.ecommerce.domains.transaction.ids.TransactionId;
import com.punj.app.ecommerce.repositories.transaction.TransactionRepository;
import com.punj.app.ecommerce.services.TransactionService;
import com.punj.app.ecommerce.services.common.ServiceConstants;
import com.punj.app.ecommerce.services.dtos.transaction.TransactionIdDTO;

/**
 * @author admin
 *
 */
@Service
public class TransactionServiceImpl implements TransactionService {

	private static final Logger logger = LogManager.getLogger();
	private TransactionRepository transactionRepository;

	/**
	 * @param transactionRepository
	 *            the transactionRepository to set
	 */
	@Autowired
	public void setTransactionRepository(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	@Override
	public Transaction saveTransaction(Transaction txnDetails) {
		txnDetails = this.transactionRepository.save(txnDetails);
		logger.info("The transaction has been created based on provided details successfully.");
		return txnDetails;
	}

	@Override
	public Transaction createTransactionInstance(TransactionIdDTO txnIdDTO, String username) {
		TransactionId txnId = new TransactionId();
		txnId.setTransactionSeq(txnIdDTO.getTxnNo());
		txnId.setLocationId(txnIdDTO.getLocationId());
		txnId.setRegister(txnIdDTO.getRegister());
		txnId.setBusinessDate(txnIdDTO.getBusinessDate());

		Transaction txnDetails = new Transaction();
		txnDetails.setTransactionId(txnId);
		txnDetails.setStartTime(LocalDateTime.now());
		txnDetails.setCreatedDate(LocalDateTime.now());
		txnDetails.setCreatedBy(username);

		txnDetails.setStatus(ServiceConstants.TXN_STATUS_STARTED);
		txnDetails.setTxnType(ServiceConstants.TXN_OPEN_STORE);

		return txnDetails;
	}

	public Integer getNextTxnNo(Integer register, Integer locationId) {

		return 0;
	}
}
