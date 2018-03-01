/**
 * 
 */
package com.punj.app.ecommerce.services.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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
		Transaction txnDetails = this.transactionRepository.getTop1ByCriteriaAndSort(locationId, txnTypes);
		if (txnDetails != null)
			logger.info("The last transaction {} details retrieved successfully", txnDetails.getTransactionId());
		return txnDetails;
	}

	@Override
	public Map<Integer, Transaction> searchRegisterTxnByCriteria(Integer locationId, Set<String> txnTypes) {
		Map<Integer, Transaction> txnMap = new HashMap<>();
		List<Transaction> txnDetails = this.transactionRepository.getLastDailyRegisterTxns(locationId, txnTypes);
		for(Transaction txnDtl:txnDetails) {
			txnMap.put(txnDtl.getTransactionId().getRegister(), txnDtl);
		}
		logger.info("The last transaction details for location {} regitsters has been etrieved successfully",locationId);
		return txnMap;
	}

	private Map<Integer, Transaction> searchTxnByCriteria(List<Integer> locationIds, Set<String> txnTypes) {
		Sort sort = new Sort(Sort.Direction.ASC, "transactionId.locationId");
		sort.and(new Sort(Sort.Direction.DESC, "transactionId.businessDate"));
		sort.and(new Sort(Sort.Direction.DESC, "startTime"));
		List<Transaction> txnDetails = null;
		/*
		 * =this.transactionRepository.getTop1ByCriteriaAndSort(ListlocationIds, txnTypes, sort); if(txnDetails!=null)
		 * logger.info("The last transaction {} details retrieved successfully", txnDetails.getTransactionId());
		 */
		return null;
	}

	@Override
	public Transaction searchLocationOpenTxn(Integer locationId, LocalDateTime businessDate) {
		Transaction txnCriteria=new Transaction();
		TransactionId txnId=new TransactionId();
		txnId.setLocationId(locationId);
		txnId.setBusinessDate(businessDate);
		txnCriteria.setTransactionId(txnId);
		txnCriteria.setTxnType(ServiceConstants.TXN_OPEN_STORE);
		Transaction txnDetails=this.transactionRepository.findOne(Example.of(txnCriteria));
		if(txnDetails!=null)
			logger.info("The last location open txn for location {} and business date {} has been retrieved succefully",locationId, businessDate);
		else
			logger.info("The last location open txn retrieval for location {} and business date {} has failed",locationId, businessDate);
		
		return txnDetails;
	}

}
