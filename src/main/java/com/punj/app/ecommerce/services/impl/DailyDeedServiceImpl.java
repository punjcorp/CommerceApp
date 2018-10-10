/**
 * 
 */
package com.punj.app.ecommerce.services.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.punj.app.ecommerce.domains.transaction.storeopen.LocationStatus;
import com.punj.app.ecommerce.domains.transaction.tender.TenderCount;
import com.punj.app.ecommerce.domains.transaction.tender.ids.TenderCountId;
import com.punj.app.ecommerce.repositories.transaction.storeopen.LocationStatusRepository;
import com.punj.app.ecommerce.repositories.transaction.tender.TenderCountRepository;
import com.punj.app.ecommerce.services.DailyDeedService;
import com.punj.app.ecommerce.services.FinanceService;
import com.punj.app.ecommerce.services.TransactionService;
import com.punj.app.ecommerce.services.common.CommonService;
import com.punj.app.ecommerce.services.common.ServiceConstants;
import com.punj.app.ecommerce.services.common.dtos.RegisterDTO;
import com.punj.app.ecommerce.services.converter.TenderCountDTOConverter;
import com.punj.app.ecommerce.services.dtos.DailyTransaction;
import com.punj.app.ecommerce.services.dtos.dailydeeds.DailyDeedDTO;
import com.punj.app.ecommerce.services.dtos.dailydeeds.LocStatusDTO;
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
	private LocationStatusRepository locationStatusRepository;

	@Value("${app.skip.opening.closing.process}")
	private Boolean skipClosingProcess;

	/**
	 * @param locationStatusRepository
	 *            the locationStatusRepository to set
	 */
	@Autowired
	public void setLocationStatusRepository(LocationStatusRepository locationStatusRepository) {
		this.locationStatusRepository = locationStatusRepository;
	}

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
	public Boolean saveStoreOpenTxn(DailyTransaction storeOpenDetails, String username) {
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
			List<TenderCount> tenderCountList = TenderCountDTOConverter.transformTenderList(storeOpenDetails.getTenders(), txnDetails.getTransactionId(), username,
					ServiceConstants.TXN_OPEN_STORE);
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
	private DailyTotals updateStoreTotals(Transaction txnDetails) {

		DailyTotals storeTotals = new DailyTotals();
		String txnType = txnDetails.getTxnType();

		storeTotals.setBusinessDate(txnDetails.getTransactionId().getBusinessDate());
		storeTotals.setLocationId(txnDetails.getTransactionId().getLocationId());
		storeTotals.setStartOfDayAmount(txnDetails.getTotalAmt());
		if (ServiceConstants.TXN_CLOSE_STORE.equals(txnType))
			storeTotals.setEndOfDayAmount(txnDetails.getTotalAmt());
		storeTotals.setTotalTxnAmount(txnDetails.getTotalAmt());
		storeTotals.setTotalTxnCount(BigInteger.ONE.intValue());

		storeTotals = this.financeService.upsertStoreTotals(storeTotals, txnType);
		logger.info("The store total details has been updated successfully");

		return storeTotals;

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
	public DailyDeedDTO saveRegisterOpenTxn(DailyTransaction registerOpenDetails, String username) {
		TransactionIdDTO txnIdDTO = registerOpenDetails.getTransactionId();
		Transaction txnDetails = this.transactionService.createTransactionInstance(txnIdDTO, username, ServiceConstants.TXN_OPEN_REGISTER);
		DailyTotals registerDailyTotals = this.saveTransactionTenderDetails(txnDetails, registerOpenDetails, username, ServiceConstants.TXN_OPEN_REGISTER);
		if (registerDailyTotals != null) {
			DailyDeedDTO dailyDeedDTO = new DailyDeedDTO();
			dailyDeedDTO.setDailyTotals(registerDailyTotals);
			dailyDeedDTO.setTxnId(txnDetails.getTransactionId());
			return dailyDeedDTO;
		} else
			return null;
	}

	@Override
	@Transactional
	public DailyDeedDTO saveRegisterCloseTxn(DailyTransaction registerCloseDetails, String username) {
		TransactionIdDTO txnIdDTO = registerCloseDetails.getTransactionId();
		Transaction txnDetails = this.transactionService.createTransactionInstance(txnIdDTO, username, ServiceConstants.TXN_CLOSE_REGISTER);
		DailyTotals registerDailyTotals = this.saveTransactionTenderDetails(txnDetails, registerCloseDetails, username, ServiceConstants.TXN_CLOSE_REGISTER);
		if (registerDailyTotals != null) {
			DailyDeedDTO dailyDeedDTO = new DailyDeedDTO();
			dailyDeedDTO.setDailyTotals(registerDailyTotals);
			dailyDeedDTO.setTxnId(txnDetails.getTransactionId());
			dailyDeedDTO.setUniqueTxnNo(txnDetails.getTransactionId().toString());
			return dailyDeedDTO;
		} else
			return null;
	}

	@Override
	@Transactional
	public DailyDeedDTO saveStoreCloseTxn(DailyTransaction storeCloseDetails, String username) {
		TransactionIdDTO txnIdDTO = storeCloseDetails.getTransactionId();
		Transaction txnDetails = this.transactionService.createTransactionInstance(txnIdDTO, username, ServiceConstants.TXN_CLOSE_STORE);
		DailyTotals storeDailyTotals = this.saveTransactionTenderDetails(txnDetails, storeCloseDetails, username, ServiceConstants.TXN_CLOSE_STORE);
		if (storeDailyTotals != null) {
			DailyDeedDTO dailyDeedDTO = new DailyDeedDTO();
			dailyDeedDTO.setDailyTotals(storeDailyTotals);
			dailyDeedDTO.setTxnId(txnDetails.getTransactionId());
			return dailyDeedDTO;
		} else
			return null;
	}

	private DailyTotals saveTransactionTenderDetails(Transaction txnDetails, DailyTransaction txnRawDetails, String username, String txnType) {
		DailyTotals resultDailyTotals = null;
		txnDetails = this.transactionService.saveTransaction(txnDetails);
		if (txnDetails != null) {
			List<TenderCount> tenderCountList = TenderCountDTOConverter.transformTenderList(txnRawDetails.getTenders(), txnDetails.getTransactionId(), username, txnType);
			tenderCountList = this.tenderCountRepository.save(tenderCountList);
			if (tenderCountList != null && !tenderCountList.isEmpty()) {

				BigDecimal totalTxnAmount = BigDecimal.ZERO;
				Integer tenderId = null;
				for (TenderCount tenderCount : tenderCountList) {
					if (ServiceConstants.TXN_CLOSE_REGISTER.equals(txnType) || ServiceConstants.TXN_CLOSE_STORE.equals(txnType)) {
						totalTxnAmount = totalTxnAmount.add(tenderCount.getActualAmount());
					} else {
						totalTxnAmount = totalTxnAmount.add(tenderCount.getAmount());
					}

					tenderId = tenderCount.getTenderCountId().getTender().getTenderId();
				}
				txnDetails.setTotalAmt(totalTxnAmount);

				LocationSafe locationSafe = this.retrieveStoreSafe(txnDetails.getTransactionId().getLocationId(), tenderId);
				if (!txnType.equals(ServiceConstants.TXN_CLOSE_STORE)) {
					this.postTenderMovementTxn(txnDetails, locationSafe, username);
					resultDailyTotals = this.updateRegisterTotals(txnDetails);
				} else {
					resultDailyTotals = this.updateStoreTotals(txnDetails);
				}

				logger.info("The txn save procees has been successfully processed and saved.");
			} else {
				logger.info("The txn save procees has failed due to some unknown problem");
			}
		} else {
			logger.info("The txn save procees has failed due to some unknown problem");
		}
		return resultDailyTotals;
	}

	private LocationSafe retrieveStoreSafe(Integer locationId, Integer tenderId) {
		List<LocationSafe> locationSafeList = this.financeService.retrieveAllSafes(locationId);
		LocationSafe locationSafeResult = null;
		for (LocationSafe locationSafe : locationSafeList) {
			if (tenderId.equals(locationSafe.getTenderId())) {
				locationSafeResult = locationSafe;
				break;
			}

		}
		logger.info("The location safe for {} tender has been retrieved successfully ", tenderId);
		return locationSafeResult;
	}

	private void postTenderMovementTxn(Transaction txnDetails, LocationSafe locationSafe, String username) {

		TenderMovement tenderMovement = new TenderMovement();

		tenderMovement.setAmount(txnDetails.getTotalAmt());
		tenderMovement.setCreatedBy(username);
		tenderMovement.setCreatedDate(LocalDateTime.now());

		if (txnDetails.getTxnType().equals(ServiceConstants.TXN_CLOSE_REGISTER)) {
			tenderMovement.setReasonCode(ServiceConstants.REASON_REGISTER_TO_REPO);
			tenderMovement.setFromId(txnDetails.getTransactionId().getRegister().toString());
			tenderMovement.setToId(locationSafe.getLocationRepositoryId().toString());
		} else if (txnDetails.getTxnType().equals(ServiceConstants.TXN_OPEN_REGISTER)) {
			tenderMovement.setReasonCode(ServiceConstants.REASON_REPO_TO_REGISTER);
			tenderMovement.setFromId(locationSafe.getLocationRepositoryId().toString());
			tenderMovement.setToId(txnDetails.getTransactionId().getRegister().toString());
		}

		tenderMovement.setTransactionId(txnDetails.getTransactionId());
		tenderMovement.setTxnType(txnDetails.getTxnType());

		this.financeService.saveTenderMovement(tenderMovement);
		logger.info("The register total details has been updated successfully");

	}

	/**
	 * This method is used to perform the following tasks 1. Update the store /register totals as applicable
	 */
	private DailyTotals updateRegisterTotals(Transaction txnDetails) {

		DailyTotals registerTotals = new DailyTotals();
		String txnType = txnDetails.getTxnType();

		registerTotals.setBusinessDate(txnDetails.getTransactionId().getBusinessDate());
		registerTotals.setLocationId(txnDetails.getTransactionId().getLocationId());
		registerTotals.setRegisterId(txnDetails.getTransactionId().getRegister());
		registerTotals.setStartOfDayAmount(txnDetails.getTotalAmt());
		registerTotals.setEndOfDayAmount(txnDetails.getTotalAmt());
		registerTotals.setTotalTxnAmount(txnDetails.getTotalAmt());
		registerTotals.setTotalTxnCount(BigInteger.ONE.intValue());

		registerTotals = this.financeService.upsertRegisterTotals(registerTotals, txnType);
		this.financeService.upsertStoreTotals(registerTotals, txnType);
		logger.info("The register total details has been updated successfully");

		return registerTotals;

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
	public List<TenderCount> searchTxnTenderCounts(Integer locationId, Integer registerId, LocalDateTime businessDate, String txnType) {

		TenderCount tenderCountCriteria = new TenderCount();
		TenderCountId tenderCountCriteriaId = new TenderCountId();
		tenderCountCriteriaId.setBusinessDate(businessDate);
		tenderCountCriteriaId.setLocationId(locationId);
		if (registerId != null)
			tenderCountCriteriaId.setRegister(registerId);
		tenderCountCriteria.setTenderCountId(tenderCountCriteriaId);
		tenderCountCriteria.setTxnType(txnType);

		return this.searchTxnTenderCount(tenderCountCriteria);
	}

	private List<TenderCount> searchTxnTenderCount(TenderCount searchCriteria) {

		List<TenderCount> tenderCounts = this.tenderCountRepository.findAll(Example.of(searchCriteria));

		if (tenderCounts != null && !tenderCounts.isEmpty())
			logger.info("The tender count details has been retrieved successfully");
		else
			logger.info("The tender count details retrieval has failed");

		return tenderCounts;
	}

	@Override
	public Map<Integer, List<TenderCount>> retrieveTenderCounts(Integer locationId, LocalDateTime businessDate, String txnType) {

		Map<Integer, List<TenderCount>> tenderCountMap = new HashMap<>();
		List<TenderCount> tmpCountList = null;

		List<TenderCount> tenderCounts = this.searchTxnTenderCounts(locationId, null, businessDate, txnType);
		if (tenderCounts != null && !tenderCounts.isEmpty()) {
			logger.info("The tender count details has been retrieved successfully");
			for (TenderCount tenderCount : tenderCounts) {
				tmpCountList = tenderCountMap.get(tenderCount.getTenderCountId().getRegister());
				if (tmpCountList == null) {
					tmpCountList = new ArrayList<>();
				}
				tmpCountList.add(tenderCount);
				tenderCountMap.put(tenderCount.getTenderCountId().getRegister(), tmpCountList);
			}

		} else {
			logger.info("The tender count details retrieval has failed");
		}
		return tenderCountMap;

	}

	@Override
	public RegisterDTO retrieveRegisterConcilationDtls(Integer locationId, LocalDateTime businessDate, String txnType) {
		RegisterDTO registerDTO = this.commonService.retrieveRegisterConcilationDtls(locationId, businessDate);
		if (registerDTO != null) {

			Map<Integer, List<TenderCount>> tenderCountMap = this.retrieveTenderCounts(locationId, businessDate, txnType);
			registerDTO.setRegTenderTotals(tenderCountMap);

			logger.info("The register concilation details for location {} has been retrieved successfully", locationId);

		} else {
			logger.info("There were no details found for any of the registers for location {}.", locationId);
		}

		return registerDTO;
	}

	@Override
	public LocStatusDTO isLocationOpenAllowed(Integer locationId, LocalDateTime businessDate) {
		LocStatusDTO locStatusDTO = new LocStatusDTO();

		locStatusDTO.setBusinessDate(businessDate);
		locStatusDTO.setLocationId(locationId);

		LocalDateTime bDate = null;

		List<LocationStatus> locStatusList = this.getLocationStausForOpening(locationId, businessDate);

		if (locStatusList != null && !locStatusList.isEmpty()) {
			for (LocationStatus locStatus : locStatusList) {
				bDate = locStatus.getLocationStatusId().getBusinessDate();

				if (bDate != null && bDate.equals(businessDate)) {
					locStatusDTO.setHasOpen(locStatus.getStoreOpenExists());
					locStatusDTO.setHasClosed(locStatus.getStoreCloseExists());
					locStatusDTO.setHasSales(locStatus.getSaleExists());

				} else {
					if (!locStatusDTO.getHasOpenedInFuture()) {
						locStatusDTO.setHasOpenedInFuture(locStatus.getStoreOpenExists());
					}
					if (!locStatusDTO.getHasClosedInFuture()) {
						locStatusDTO.setHasClosedInFuture(locStatus.getStoreCloseExists());
					}
					if (!locStatusDTO.getHasSalesInFuture()) {
						locStatusDTO.setHasSalesInFuture(locStatus.getSaleExists());
					}
				}

			}
		}

		logger.info("The location status has been validated successfully as per database records");
		return locStatusDTO;
	}

	private List<LocationStatus> getLocationStausForOpening(Integer locationId, LocalDateTime businessDate) {
		List<LocationStatus> locStatusList = this.locationStatusRepository.getLocationStatusSinceDate(locationId, businessDate.toString());
		if (locStatusList != null && !locStatusList.isEmpty()) {
			logger.info("The records has been successfully retrieved for the provided business date");
		} else {
			logger.info("There are no records existing since the provided business date");
		}
		return locStatusList;
	}

	@Override
	@Transactional
	public void resetLocationForBusiness(Integer locationId, LocalDateTime businessDate) {

		// Delete all the future date daily deed txns, the sales are not part of it
		this.resetFutureTxns(locationId, businessDate);

		// Delete existing store close and last register close transactions
		Transaction storeCloseTxn = this.transactionService.searchStoreCloseTxn(locationId, businessDate);
		Transaction regCloseTxn = this.transactionService.searchLastRegCloseTxn(locationId, businessDate);
		logger.info("The last register close and store close transactions has been retrieved for {} business date", businessDate);
		if (storeCloseTxn != null && regCloseTxn != null) {

			// Delete register close
			// Delete store close
			this.transactionService.deleteStoreCloseTxn(storeCloseTxn.getTransactionId());
			this.transactionService.deleteRegCloseTxn(regCloseTxn.getTransactionId());
			logger.info("The last register close and store close transactions for {} business date has been delete successfully ", businessDate);

		} else {
			logger.error("There were no store close transaction found for the business date");
		}

	}

	private void resetFutureTxns(Integer locationId, LocalDateTime businessDate) {
		this.transactionService.deleteAllFutureTxns(locationId, businessDate);
		logger.info("All the exisitng transactions with future dates has been deleted successfully ");
	}

	@Override
	@Transactional
	public Boolean skipOpenProcess(DailyTransaction storeOpenDetails, String username) {
		Boolean result = Boolean.FALSE;

		Transaction storeOpenTxn=this.transactionService.searchLocationOpenTxn(storeOpenDetails.getTransactionId().getLocationId(), storeOpenDetails.getTransactionId().getBusinessDate());
		
		if(storeOpenTxn!=null) {
			logger.info("The store open transaction already exists.");
			result=Boolean.TRUE;
		}else {
			/**
			 * Store Open Process Starts
			 */
			
			TransactionIdDTO txnIdDTO = new TransactionIdDTO();
			txnIdDTO.setBusinessDate(storeOpenDetails.getTransactionId().getBusinessDate());
			txnIdDTO.setLocationId(storeOpenDetails.getTransactionId().getLocationId());
			txnIdDTO.setRegister(ServiceConstants.REGISTER_ONE);

			Transaction txnDetails = this.transactionService.createTransactionInstance(txnIdDTO, username, ServiceConstants.TXN_OPEN_STORE);
			logger.info("The store open transaction number has been generated successfully.");
			txnDetails = this.transactionService.saveTransaction(txnDetails);
			logger.info("The store open transaction basic details has been saved successfully.");
			if (txnDetails != null) {

				BigDecimal totalTxnAmount = BigDecimal.ZERO;
				txnDetails.setTotalAmt(totalTxnAmount);

				// this.updateDailySafe(txnDetails, tenderCountList, username);
				this.updateStoreTotals(txnDetails);
				this.updateLedgerJournal(txnDetails, username);

				result = Boolean.TRUE;
				logger.info("The store open process has been successfully processed and saved.");

			} else {
				logger.info("The store open process has failed due to some unknown problem");
			}

			/**
			 * Store Open Process Ends
			 */
			
			
			/**
			 * Register Open Process Starts
			 */
			
			Transaction regTxnDetails = this.transactionService.createTransactionInstance(txnIdDTO, username, ServiceConstants.TXN_OPEN_REGISTER);

			// Transaction details
			regTxnDetails = this.transactionService.saveTransaction(regTxnDetails);
			if (regTxnDetails != null) {

				BigDecimal totalTxnAmount = BigDecimal.ZERO;
				regTxnDetails.setTotalAmt(totalTxnAmount);

				DailyTotals resultDailyTotals = this.updateRegisterTotals(regTxnDetails);
				if(result)
					result=Boolean.TRUE;
				logger.info("The reg open txn save procees has been successfully completed.");
			} else {
				result=Boolean.FALSE;
				logger.info("The reg open txn save procees has failed due to some unknown problem");
			}
			/**
			 * Register Open Process Ends
			 */

		}
		
		logger.info("The automatic creation of store opening and closing process has completed");
		return result;
	}

}
