/**
 * 
 */
package com.punj.app.ecommerce.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
	public Transaction createTransactionInstance(TransactionIdDTO txnIdDTO, String username, String txnType) {
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
		txnDetails.setTxnType(txnType);

		logger.info("The transaction {} instance has been created based on provided details", txnType);

		return txnDetails;
	}

	@Override
	public Transaction searchTxnByCriteria(Integer locationId, Set<String> txnTypes) {

		/*
		 * Sort sort =new Sort(Sort.Direction.ASC,"transactionId.locationId");
		 * sort.and(new Sort(Sort.Direction.DESC,"transactionId.businessDate"));
		 * sort.and(new Sort(Sort.Direction.DESC,"startTime"));
		 */
		Transaction txnDetails = this.transactionRepository.getTop1ByCriteriaAndSort(locationId, txnTypes);
		if (txnDetails != null)
			logger.info("The last transaction {} details retrieved successfully", txnDetails.getTransactionId());
		return txnDetails;
	}

	private Map<Integer, Transaction> searchTxnByCriteria(List<Integer> locationIds, Set<String> txnTypes) {
		Sort sort = new Sort(Sort.Direction.ASC, "transactionId.locationId");
		sort.and(new Sort(Sort.Direction.DESC, "transactionId.businessDate"));
		sort.and(new Sort(Sort.Direction.DESC, "startTime"));
		List<Transaction> txnDetails = null;
		/*
		 * =this.transactionRepository.getTop1ByCriteriaAndSort(ListlocationIds,
		 * txnTypes, sort); if(txnDetails!=null)
		 * logger.info("The last transaction {} details retrieved successfully",
		 * txnDetails.getTransactionId());
		 */
		return null;
	}

}
