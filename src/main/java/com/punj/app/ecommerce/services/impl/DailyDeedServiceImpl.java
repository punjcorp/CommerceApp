/**
 * 
 */
package com.punj.app.ecommerce.services.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.punj.app.ecommerce.domains.finance.DailySafe;
import com.punj.app.ecommerce.domains.finance.DailyTotals;
import com.punj.app.ecommerce.domains.finance.LedgerJournal;
import com.punj.app.ecommerce.domains.finance.LocationSafe;
import com.punj.app.ecommerce.domains.finance.TenderMovement;
import com.punj.app.ecommerce.domains.transaction.Transaction;
import com.punj.app.ecommerce.domains.transaction.ids.TransactionId;
import com.punj.app.ecommerce.domains.transaction.tender.TenderCount;
import com.punj.app.ecommerce.domains.transaction.tender.ids.TenderCountId;
import com.punj.app.ecommerce.repositories.transaction.tender.TenderCountRepository;
import com.punj.app.ecommerce.services.DailyDeedService;
import com.punj.app.ecommerce.services.FinanceService;
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
	private FinanceService financeService;

	/**
	 * @param financeService
	 *            the financeService to set
	 */
	@Autowired
	public void setFinanceService(FinanceService financeService) {
		this.financeService = financeService;
	}

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
		logger.info("The store open transaction number has been generated successfully.");
		txnDetails = this.transactionService.saveTransaction(txnDetails);
		logger.info("The store open transaction basic details has been saved successfully.");
		if (txnDetails != null) {
			List<TenderCount> tenderCountList = TenderCountDTOConverter.transformTenderList(storeOpenDetails.getTenders(), txnDetails.getTransactionId(),
					username, Boolean.FALSE);
			tenderCountList = this.tenderCountRepository.save(tenderCountList);
			logger.info("The store open tender details has been saved successfully.");
			if (tenderCountList != null && !tenderCountList.isEmpty()) {

				BigDecimal totalTxnAmount = BigDecimal.ZERO;
				for (TenderCount tenderCount : tenderCountList) {
					totalTxnAmount = totalTxnAmount.add(tenderCount.getAmount());
				}
				txnDetails.setTotalAmt(totalTxnAmount);

				this.updateDailySafe(txnDetails, tenderCountList, username);
				this.updateStoreTotals(txnDetails);
				this.updateLedgerJournal(txnDetails, username);

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

	/**
	 * This method is used to perform the following tasks 1. Open the repository for the day
	 * 
	 */
	private void updateDailySafe(Transaction txnDetails, List<TenderCount> tenderCountList, String username) {

		List<LocationSafe> locSafeList = this.financeService.retrieveAllSafes(txnDetails.getTransactionId().getLocationId());

		Integer tenderId = null;
		DailySafe safeDetails = null;
		List<DailySafe> safeDetailList = new ArrayList<>();
		for (TenderCount tenderCount : tenderCountList) {
			tenderId = tenderCount.getTenderCountId().getTender().getTenderId();

			for (LocationSafe locSafe : locSafeList) {
				if (tenderId.equals(locSafe.getTenderId())) {
					safeDetails = new DailySafe();
					safeDetails.setAmount(tenderCount.getAmount());
					safeDetails.setBusinessDate(txnDetails.getTransactionId().getBusinessDate());
					safeDetails.setCreatedBy(username);
					safeDetails.setCreatedDate(LocalDateTime.now());
					safeDetails.setLocationRepositoryId(locSafe.getLocationRepositoryId());
				} else {
					safeDetails = new DailySafe();
					safeDetails.setAmount(BigDecimal.ZERO);
					safeDetails.setBusinessDate(txnDetails.getTransactionId().getBusinessDate());
					safeDetails.setCreatedBy(username);
					safeDetails.setCreatedDate(LocalDateTime.now());
					safeDetails.setLocationRepositoryId(locSafe.getLocationRepositoryId());
				}
				safeDetailList.add(safeDetails);
			}
		}

		this.financeService.createDailySafe(safeDetailList);

		logger.info("The daily safe records has been created successfully for the daily deed transactions");
	}

	/**
	 * This method is used to perform the following tasks 1. Update the store /register totals as applicable
	 */
	private void updateStoreTotals(Transaction txnDetails) {

		DailyTotals storeTotals = new DailyTotals();
		storeTotals.setBusinessDate(txnDetails.getTransactionId().getBusinessDate());
		storeTotals.setLocationId(txnDetails.getTransactionId().getLocationId());
		storeTotals.setTotalTxnAmount(txnDetails.getTotalAmt());

		this.financeService.upsertStoreTotals(storeTotals);
		logger.info("The store total details has been updated successfully");

	}

	private void updateLedgerJournal(Transaction txnDetails, String username) {
		LedgerJournal ledgerDetails = new LedgerJournal();
		ledgerDetails.setActionType(ServiceConstants.LEDGER_ACTION_ADD_TO_SAFE);
		ledgerDetails.setAmount(txnDetails.getTotalAmt());
		ledgerDetails.setBusinessDate(txnDetails.getTransactionId().getBusinessDate());
		ledgerDetails.setCreatedBy(username);
		ledgerDetails.setCreatedDate(LocalDateTime.now());
		ledgerDetails.setLocationId(txnDetails.getTransactionId().getLocationId());
		ledgerDetails.setTxnNo(txnDetails.getTransactionId().toString());
		ledgerDetails.setTxnType(txnDetails.getTxnType());

		this.financeService.saveLedgerDetails(ledgerDetails);
		logger.info("The ledget details has been updated successfully");
	}

	@Override
	@Transactional
	public Boolean saveRegisterOpenTxn(DailyOpenTransaction registerOpenDetails, String username) {
		Boolean result = Boolean.FALSE;
		TransactionIdDTO txnIdDTO = registerOpenDetails.getTransactionId();
		Transaction txnDetails = this.transactionService.createTransactionInstance(txnIdDTO, username, ServiceConstants.TXN_OPEN_REGISTER);
		txnDetails = this.transactionService.saveTransaction(txnDetails);
		if (txnDetails != null) {
			List<TenderCount> tenderCountList = TenderCountDTOConverter.transformTenderList(registerOpenDetails.getTenders(), txnDetails.getTransactionId(),
					username, Boolean.TRUE);
			tenderCountList = this.tenderCountRepository.save(tenderCountList);
			if (tenderCountList != null && !tenderCountList.isEmpty()) {

				BigDecimal totalTxnAmount = BigDecimal.ZERO;
				Integer tenderId=null;
				for (TenderCount tenderCount : tenderCountList) {
					totalTxnAmount = totalTxnAmount.add(tenderCount.getAmount());
					tenderId=tenderCount.getTenderCountId().getTender().getTenderId();
				}
				txnDetails.setTotalAmt(totalTxnAmount);

				
				LocationSafe locationSafe=this.retrieveStoreSafe(txnDetails.getTransactionId().getLocationId(), tenderId);
				
				this.postTenderMovementTxn(txnDetails, locationSafe,username);
				this.updateRegisterTotals(txnDetails);
				
				//This is not needed for register as no new tender added or removed
				//this.updateLedgerJournal(txnDetails, username);

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

	
	private LocationSafe retrieveStoreSafe(Integer locationId, Integer tenderId) {
		List<LocationSafe> locationSafeList=this.financeService.retrieveAllSafes(locationId);
		LocationSafe locationSafeResult=null;
		for(LocationSafe locationSafe: locationSafeList) {
			if(tenderId.equals(locationSafe.getTenderId())) {
				locationSafeResult=locationSafe;
				break;
			}
				
		}
		logger.info("The location safe for {} tender has been retrieved successfully ", tenderId);
		return locationSafeResult;
	}
	
	
	private void postTenderMovementTxn(Transaction txnDetails,LocationSafe locationSafe, String username) {

		TenderMovement tenderMovement = new TenderMovement();

		tenderMovement.setAmount(txnDetails.getTotalAmt());
		tenderMovement.setCreatedBy(username);
		tenderMovement.setCreatedDate(LocalDateTime.now());
		tenderMovement.setFromId(locationSafe.getLocationRepositoryId().toString());
		tenderMovement.setReasonCode(ServiceConstants.REASON_REPO_TO_REGISTER);
		tenderMovement.setToId(txnDetails.getTransactionId().getRegister().toString());
		tenderMovement.setTransactionId(txnDetails.getTransactionId());
		tenderMovement.setTxnType(ServiceConstants.TXN_OPEN_REGISTER);
		
		this.financeService.saveTenderMovement(tenderMovement);
		logger.info("The register total details has been updated successfully");

	}

	/**
	 * This method is used to perform the following tasks 1. Update the store /register totals as applicable
	 */
	private void updateRegisterTotals(Transaction txnDetails) {

		DailyTotals registerTotals = new DailyTotals();
		registerTotals.setBusinessDate(txnDetails.getTransactionId().getBusinessDate());
		registerTotals.setLocationId(txnDetails.getTransactionId().getLocationId());
		registerTotals.setRegisterId(txnDetails.getTransactionId().getRegister());
		registerTotals.setTotalTxnAmount(txnDetails.getTotalAmt());

		this.financeService.upsertRegisterTotals(registerTotals);
		logger.info("The register total details has been updated successfully");

	}

	@Override
	public TenderCount searchTxnTenderCount(TransactionId txnId) {
		TenderCount tenderCountCriteria = new TenderCount();
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

	@Override
	public List<TenderCount> searchTxnTenderCounts(Integer locationId, Integer registerId, LocalDateTime businessDate) {

		TenderCount tenderCountCriteria = new TenderCount();
		TenderCountId tenderCountCriteriaId = new TenderCountId();
		tenderCountCriteriaId.setBusinessDate(businessDate);
		tenderCountCriteriaId.setLocationId(locationId);
		tenderCountCriteriaId.setRegister(registerId);
		tenderCountCriteria.setTenderCountId(tenderCountCriteriaId);
		tenderCountCriteria.setTxnType(ServiceConstants.TXN_OPEN_STORE);

		List<TenderCount> tenderCounts = this.tenderCountRepository.findAll(Example.of(tenderCountCriteria));

		if (tenderCounts != null && !tenderCounts.isEmpty())
			logger.info("The tender count details has been retrieved successfully");
		else
			logger.info("The tender count details retrieval has failed");

		return tenderCounts;
	}

}
