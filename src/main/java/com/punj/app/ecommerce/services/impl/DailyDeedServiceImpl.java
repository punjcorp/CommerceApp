/**
 * 
 */
package com.punj.app.ecommerce.services.impl;

import java.math.BigInteger;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.punj.app.ecommerce.domains.transaction.Transaction;
import com.punj.app.ecommerce.domains.transaction.ids.TransactionId;
import com.punj.app.ecommerce.domains.transaction.tender.TenderCount;
import com.punj.app.ecommerce.domains.transaction.tender.ids.TenderCountId;
import com.punj.app.ecommerce.repositories.transaction.tender.TenderCountRepository;
import com.punj.app.ecommerce.services.DailyDeedService;
import com.punj.app.ecommerce.services.TransactionService;
import com.punj.app.ecommerce.services.common.CommonService;
import com.punj.app.ecommerce.services.common.ServiceConstants;
import com.punj.app.ecommerce.services.converter.TenderCountDTOConverter;
import com.punj.app.ecommerce.services.dtos.DailyOpenTransaction;
import com.punj.app.ecommerce.services.dtos.transaction.TransactionIdDTO;

/**
 * @author admin
 *
 */
@Service
public class DailyDeedServiceImpl implements DailyDeedService {

	private static final Logger logger = LogManager.getLogger();
	private TransactionService transactionService;
	private CommonService commonService;
	private TenderCountRepository tenderCountRepository;

	/**
	 * @param tenderCountRepository
	 *            the tenderCountRepository to set
	 */
	@Autowired
	public void setTenderCountRepository(TenderCountRepository tenderCountRepository) {
		this.tenderCountRepository = tenderCountRepository;
	}

	/**
	 * @param transactionService
	 *            the transactionService to set
	 */
	@Autowired
	public void setTransactionService(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	/**
	 * @param commonService
	 *            the commonService to set
	 */
	@Autowired
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	@Override
	@Transactional
	public Boolean saveStoreOpenTxn(DailyOpenTransaction storeOpenDetails, String username) {
		Boolean result = Boolean.FALSE;
		TransactionIdDTO txnIdDTO = new TransactionIdDTO();
		txnIdDTO.setBusinessDate(storeOpenDetails.getTransactionId().getBusinessDate());
		txnIdDTO.setLocationId(storeOpenDetails.getTransactionId().getLocationId());
		txnIdDTO.setRegister(ServiceConstants.REGISTER_ONE);

		Transaction txnDetails = this.transactionService.createTransactionInstance(txnIdDTO, username, ServiceConstants.TXN_OPEN_STORE);
		txnDetails = this.transactionService.saveTransaction(txnDetails);
		if (txnDetails != null) {
			List<TenderCount> tenderCountList = TenderCountDTOConverter.transformTenderList(storeOpenDetails.getTenders(), txnDetails.getTransactionId(), username);
			tenderCountList = this.tenderCountRepository.save(tenderCountList);
			if (tenderCountList != null && !tenderCountList.isEmpty()) {
				result = Boolean.TRUE;
				logger.info("The store open process has been successfully processed and saved.");
			} else {
				logger.info("The store open process has failed due to some unknown problem");
			}
		} else {
			logger.info("The store open process has failed due to some unknown problem");
		}

		return result;
	}

	@Override
	@Transactional
	public Boolean saveRegisterOpenTxn(DailyOpenTransaction registerOpenDetails, String username) {
		Boolean result = Boolean.FALSE;
		TransactionIdDTO txnIdDTO = registerOpenDetails.getTransactionId();
		Transaction txnDetails = this.transactionService.createTransactionInstance(txnIdDTO, username, ServiceConstants.TXN_OPEN_REGISTER);
		txnDetails = this.transactionService.saveTransaction(txnDetails);
		if (txnDetails != null) {
			List<TenderCount> tenderCountList = TenderCountDTOConverter.transformTenderList(registerOpenDetails.getTenders(), txnDetails.getTransactionId(), username);
			tenderCountList = this.tenderCountRepository.save(tenderCountList);
			if (tenderCountList != null && !tenderCountList.isEmpty()) {
				result = Boolean.TRUE;
				logger.info("The register open process has been successfully processed and saved.");
			} else {
				logger.info("The register open process has failed due to some unknown problem");
			}
		} else {
			logger.info("The register open process has failed due to some unknown problem");
		}

		return result;
	}


	@Override
	public TenderCount searchTxnTenderCount(TransactionId txnId) {
		TenderCount tenderCountCriteria= new TenderCount();
		TenderCountId tenderCountId = new TenderCountId();
		tenderCountId.setBusinessDate(txnId.getBusinessDate());
		tenderCountId.setRegister(txnId.getRegister());
		tenderCountId.setTransactionSeq(txnId.getTransactionSeq());
		tenderCountId.setLocationId(txnId.getLocationId());
		
		tenderCountCriteria.setTenderCountId(tenderCountId);
		TenderCount txnCountDetails = this.tenderCountRepository.findOne(Example.of(tenderCountCriteria));

		if (txnCountDetails != null)
			logger.info("The tender count details has been retrieved successfully");
		else
			logger.info("The tender count details retrieval has failed");

		return txnCountDetails;
	}

}
