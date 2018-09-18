/**
 * 
 */
package com.punj.app.ecommerce.services.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
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
import com.punj.app.ecommerce.repositories.finance.DailySafeRepository;
import com.punj.app.ecommerce.repositories.finance.DailyTotalsRepository;
import com.punj.app.ecommerce.repositories.finance.LedgerJournalRepository;
import com.punj.app.ecommerce.repositories.finance.LocationSafeRepository;
import com.punj.app.ecommerce.repositories.finance.TenderMovementRepository;
import com.punj.app.ecommerce.services.FinanceService;
import com.punj.app.ecommerce.services.common.ServiceConstants;

/**
 * @author admin
 *
 */
@Service
public class FinanceServiceImpl implements FinanceService {

	private static final Logger logger = LogManager.getLogger();
	private DailyTotalsRepository dailyTotalsRepository;
	private LedgerJournalRepository ledgerJournalRepository;
	private TenderMovementRepository tenderMovementRepository;
	private DailySafeRepository dailySafeRepository;
	private LocationSafeRepository locationSafeRepository;

	/**
	 * @param locationSafeRepository
	 *            the locationSafeRepository to set
	 */
	@Autowired
	public void setLocationSafeRepository(LocationSafeRepository locationSafeRepository) {
		this.locationSafeRepository = locationSafeRepository;
	}

	/**
	 * @param dailySafeRepository
	 *            the dailySafeRepository to set
	 */
	@Autowired
	public void setDailySafeRepository(DailySafeRepository dailySafeRepository) {
		this.dailySafeRepository = dailySafeRepository;
	}

	/**
	 * @param tenderMovementRepository
	 *            the tenderMovementRepository to set
	 */
	@Autowired
	public void setTenderMovementRepository(TenderMovementRepository tenderMovementRepository) {
		this.tenderMovementRepository = tenderMovementRepository;
	}

	/**
	 * @param dailyTotalsRepository
	 *            the dailyTotalsRepository to set
	 */
	@Autowired
	public void setDailyTotalsRepository(DailyTotalsRepository dailyTotalsRepository) {
		this.dailyTotalsRepository = dailyTotalsRepository;
	}

	/**
	 * @param ledgerJournalRepository
	 *            the ledgerJournalRepository to set
	 */
	@Autowired
	public void setLedgerJournalRepository(LedgerJournalRepository ledgerJournalRepository) {
		this.ledgerJournalRepository = ledgerJournalRepository;
	}

	@Override
	@Transactional
	public LedgerJournal saveLedgerDetails(LedgerJournal ledgerDetails) {

		ledgerDetails = this.ledgerJournalRepository.save(ledgerDetails);
		if (ledgerDetails != null)
			logger.info("The ledger details has been updated successfully");
		else
			logger.info("The ledger details were not saved due to some issues.");
		return ledgerDetails;
	}

	@Override
	@Transactional
	public DailyTotals upsertStoreTotals(DailyTotals storeTotals, String txnType) {

		DailyTotals storeTotalsCriteria = new DailyTotals();
		storeTotalsCriteria.setBusinessDate(storeTotals.getBusinessDate());
		storeTotalsCriteria.setLocationId(storeTotals.getLocationId());

		List<DailyTotals> storeTotalsList = this.dailyTotalsRepository.findAll(Example.of(storeTotalsCriteria));

		DailyTotals storeTotalsActual = null;

		if (storeTotalsList != null && !storeTotalsList.isEmpty()) {
			for (DailyTotals dlyTotal : storeTotalsList) {
				if (dlyTotal.getRegisterId() == null) {
					storeTotalsActual = dlyTotal;
					break;
				}
			}
		}

		storeTotalsActual = this.updateStoreTotalsDetails(storeTotalsActual, storeTotals, txnType);
		storeTotalsActual = this.dailyTotalsRepository.save(storeTotalsActual);
		if (storeTotalsActual != null)
			logger.info("The store total details has been updated successfully");
		else
			logger.info("The store total details were not saved due to some issues.");

		return storeTotalsActual;
	}

	private DailyTotals updateStoreTotalsDetails(DailyTotals storeTotalsResult, DailyTotals storeTotals, String txnType) {

		switch (txnType) {
		case ServiceConstants.TXN_CLOSE_REGISTER:
			if (storeTotalsResult != null) {
				storeTotalsResult.setTotalTxnCount(storeTotalsResult.getTotalTxnCount() + BigInteger.ONE.intValue());
				logger.info("The existing store total record has been updated with the register closing totals");
				return storeTotalsResult;
			}
			break;
		case ServiceConstants.TXN_OPEN_REGISTER:
			if (storeTotalsResult != null) {
				storeTotalsResult.setTotalTxnCount(storeTotalsResult.getTotalTxnCount() + 1);
				return storeTotalsResult;
			} else {
				storeTotals.setEndOfDayAmount(null);
				return storeTotals;
			}
		case ServiceConstants.TXN_CLOSE_STORE:
			if (storeTotalsResult != null) {
				storeTotalsResult.setTotalTxnCount(storeTotalsResult.getTotalTxnCount() + 1);
				storeTotalsResult.setEndOfDayAmount(storeTotals.getEndOfDayAmount());
				return storeTotalsResult;
			} else {
				return storeTotals;
			}
		}

		if (txnType.equals(ServiceConstants.TXN_CLOSE_REGISTER)) {

		} else if (txnType.equals(ServiceConstants.TXN_OPEN_REGISTER)) {

		}
		return storeTotals;
	}

	@Override
	@Transactional
	public DailyTotals upsertRegisterTotals(DailyTotals registerTotals, String txnType) {

		DailyTotals registerTotalsCriteria = new DailyTotals();
		registerTotalsCriteria.setLocationId(registerTotals.getLocationId());
		registerTotalsCriteria.setRegisterId(registerTotals.getRegisterId());
		registerTotalsCriteria.setBusinessDate(registerTotals.getBusinessDate());

		DailyTotals registerTotalsResult = null;
		List<DailyTotals> registerTotalsResults = this.dailyTotalsRepository.findAll(Example.of(registerTotalsCriteria));
		if (registerTotalsResults != null && !registerTotalsResults.isEmpty())
			registerTotalsResult = registerTotalsResults.get(0);

		registerTotals = this.updateTotalsDetails(registerTotalsResult, registerTotals, txnType);
		registerTotals = this.dailyTotalsRepository.save(registerTotals);

		if (registerTotals != null)
			logger.info("The register total details has been updated successfully");
		else
			logger.info("The register total details were not saved due to some issues.");
		return registerTotals;
	}

	private DailyTotals updateTotalsDetails(DailyTotals registerTotalsResult, DailyTotals registerTotals, String txnType) {
		if (txnType.equals(ServiceConstants.TXN_CLOSE_REGISTER)) {
			if (registerTotalsResult != null) {
				registerTotalsResult.setEndOfDayAmount(registerTotals.getEndOfDayAmount());
				registerTotalsResult.setTotalTxnCount(registerTotalsResult.getTotalTxnCount() + BigInteger.ONE.intValue());
				logger.info("The existing register total record has been updated with the register closing totals");
				return registerTotalsResult;
			} else {
				registerTotals.setEndOfDayAmount(null);
				return registerTotals;
			}
		} else if (txnType.equals(ServiceConstants.TXN_OPEN_REGISTER) && registerTotalsResult == null) {
			registerTotals.setEndOfDayAmount(null);
			return registerTotals;
		}
		return registerTotals;
	}

	@Override
	@Transactional
	public TenderMovement saveTenderMovement(TenderMovement moveDetails) {
		moveDetails = this.tenderMovementRepository.save(moveDetails);
		if (moveDetails != null)
			logger.info("The tender move transaction details has been updated successfully");
		else
			logger.info("The tender move transaction details were not saved due to some issues.");
		return moveDetails;
	}

	@Override
	@Transactional
	public List<DailySafe> createDailySafe(List<DailySafe> safeDetailList) {
		safeDetailList = this.dailySafeRepository.save(safeDetailList);
		if (safeDetailList != null && !safeDetailList.isEmpty())
			logger.info("The daily safe details has been updated successfully");
		else
			logger.info("The daily safe details were not saved due to some issues.");
		return safeDetailList;
	}

	@Override
	public LocationSafe retrieveSafeDetails(Integer locationId, Integer tenderId) {
		LocationSafe locationSafe = new LocationSafe();
		locationSafe.setLocationId(locationId);
		locationSafe.setTenderId(tenderId);
		locationSafe = this.locationSafeRepository.findOne(Example.of(locationSafe));
		if (locationSafe != null)
			logger.info("The location safe details has been retrieved successfully");
		else
			logger.info("The location safe detail were not found for the provided {} location and {} tender.", locationId, tenderId);
		return locationSafe;
	}

	@Override
	public List<LocationSafe> retrieveAllSafes(Integer locationId) {
		List<LocationSafe> locationSafeList = null;

		LocationSafe locationSafeCriteria = new LocationSafe();
		locationSafeCriteria.setLocationId(locationId);
		locationSafeList = this.locationSafeRepository.findAll(Example.of(locationSafeCriteria));
		if (locationSafeList != null && !locationSafeList.isEmpty())
			logger.info("The location safe details for provided location has been retrieved successfully");
		else
			logger.info("The location safe detail were not found for the provided {} location.", locationId);
		return locationSafeList;
	}

	@Override
	public DailyTotals updateStoreTotals(DailyTotals storeTotals, String action) {
		DailyTotals storeTotalsRcd = null;
		DailyTotals storeTotalsCriteria = new DailyTotals();
		storeTotalsCriteria.setLocationId(storeTotals.getLocationId());
		storeTotalsCriteria.setBusinessDate(storeTotals.getBusinessDate());
		List<DailyTotals> dailyTotalsList = this.dailyTotalsRepository.findAll(Example.of(storeTotalsCriteria));
		if (dailyTotalsList != null && !dailyTotalsList.isEmpty()) {
			for (DailyTotals dailyTotals : dailyTotalsList) {
				if (dailyTotals.getRegisterId() == null) {
					storeTotalsRcd = dailyTotals;
					break;
				}
			}
		}
		if (storeTotalsRcd != null) {
			switch (action) {
			case ServiceConstants.ACTION_EXPENSE:

				if (storeTotalsRcd.getTotalNoSalesAmount() != null) {
					storeTotalsRcd.setTotalNoSalesCount(storeTotalsRcd.getTotalNoSalesCount() + 1);
					storeTotalsRcd.setTotalNoSalesAmount(storeTotalsRcd.getTotalNoSalesAmount().add(storeTotals.getTotalTxnAmount()));
				} else {
					storeTotalsRcd.setTotalNoSalesCount(BigInteger.ONE.intValue());
					storeTotalsRcd.setTotalNoSalesAmount(storeTotals.getTotalTxnAmount());
				}

				storeTotalsRcd.setTotalTxnAmount(storeTotalsRcd.getTotalTxnAmount().subtract(storeTotals.getTotalTxnAmount()));
				storeTotalsRcd.setTotalTxnCount(storeTotalsRcd.getTotalTxnCount() + BigInteger.ONE.intValue());

				break;

			case ServiceConstants.TXN_RETURN:

				if (storeTotalsRcd.getTotalReturnCount() != null) {
					storeTotalsRcd.setTotalReturnCount(storeTotalsRcd.getTotalReturnCount() + 1);
					storeTotalsRcd.setTotalReturnsamount(storeTotalsRcd.getTotalReturnsamount().add(storeTotals.getTotalTxnAmount()));
				} else {
					storeTotalsRcd.setTotalReturnCount(BigInteger.ONE.intValue());
					storeTotalsRcd.setTotalReturnsamount(storeTotals.getTotalTxnAmount());
				}

				storeTotalsRcd.setTotalTxnAmount(storeTotalsRcd.getTotalTxnAmount().add(storeTotals.getTotalTxnAmount()));
				storeTotalsRcd.setTotalTxnCount(storeTotalsRcd.getTotalTxnCount() + BigInteger.ONE.intValue());

				break;

			case ServiceConstants.TXN_SALE:

				if (storeTotalsRcd.getTotalSalesCount() != null) {
					storeTotalsRcd.setTotalSalesCount(storeTotalsRcd.getTotalSalesCount() + 1);
					storeTotalsRcd.setTotalSalesamount(storeTotalsRcd.getTotalSalesamount().add(storeTotals.getTotalTxnAmount()));
				} else {
					storeTotalsRcd.setTotalSalesCount(BigInteger.ONE.intValue());
					storeTotalsRcd.setTotalSalesamount(storeTotals.getTotalTxnAmount());
				}

				storeTotalsRcd.setTotalTxnAmount(storeTotalsRcd.getTotalTxnAmount().add(storeTotals.getTotalTxnAmount()));
				storeTotalsRcd.setTotalTxnCount(storeTotalsRcd.getTotalTxnCount() + BigInteger.ONE.intValue());

				break;

			case ServiceConstants.TXN_OPEN_STORE:
			case ServiceConstants.TXN_OPEN_REGISTER:

				break;

			case ServiceConstants.TXN_CLOSE_STORE:
			case ServiceConstants.TXN_CLOSE_REGISTER:
				storeTotalsRcd.setTotalTxnCount(storeTotalsRcd.getTotalTxnCount() + BigInteger.ONE.intValue());

				break;
			}

			storeTotalsRcd = this.dailyTotalsRepository.save(storeTotalsRcd);
			if (storeTotalsRcd != null)
				logger.info("The store total details has been updated successfully");
			else
				logger.info("The store total details were not saved due to some issues.");

		}
		return storeTotalsRcd;
	}

	@Override
	@Transactional
	public DailyTotals updateRegisterTotals(DailyTotals registerTotals, String action) {
		DailyTotals updatedTotals = null;
		DailyTotals registerTotalsCriteria = new DailyTotals();
		registerTotalsCriteria.setLocationId(registerTotals.getLocationId());
		registerTotalsCriteria.setRegisterId(registerTotals.getRegisterId());
		registerTotalsCriteria.setBusinessDate(registerTotals.getBusinessDate());
		registerTotalsCriteria = this.dailyTotalsRepository.findOne(Example.of(registerTotalsCriteria));
		if (registerTotalsCriteria != null) {
			registerTotals.setDailyTotalsId(registerTotalsCriteria.getDailyTotalsId());

			switch (action) {
			case ServiceConstants.ACTION_EXPENSE:

				if (registerTotalsCriteria.getTotalNoSalesCount() != null) {
					registerTotalsCriteria.setTotalNoSalesAmount(registerTotalsCriteria.getTotalNoSalesAmount().add(registerTotals.getTotalTxnAmount()));
					registerTotalsCriteria.setTotalNoSalesCount(registerTotalsCriteria.getTotalNoSalesCount() + 1);
				} else {
					registerTotalsCriteria.setTotalNoSalesAmount(registerTotals.getTotalTxnAmount());
					registerTotalsCriteria.setTotalNoSalesCount(1);
				}

				registerTotalsCriteria.setTotalTxnAmount(registerTotalsCriteria.getTotalTxnAmount().subtract(registerTotals.getTotalTxnAmount()));
				registerTotalsCriteria.setTotalTxnCount(registerTotalsCriteria.getTotalTxnCount() + 1);

				break;

			case ServiceConstants.TXN_RETURN:

				if (registerTotalsCriteria.getTotalReturnCount() != null) {
					registerTotalsCriteria.setTotalReturnCount(registerTotalsCriteria.getTotalReturnCount() + 1);
					registerTotalsCriteria.setTotalReturnsamount(registerTotalsCriteria.getTotalReturnsamount().add(registerTotals.getTotalTxnAmount()));
				} else {
					registerTotalsCriteria.setTotalReturnCount(1);
					registerTotalsCriteria.setTotalReturnsamount(registerTotals.getTotalTxnAmount());
				}

				registerTotalsCriteria.setTotalTxnAmount(registerTotalsCriteria.getTotalTxnAmount().add(registerTotals.getTotalTxnAmount()));
				registerTotalsCriteria.setTotalTxnCount(registerTotalsCriteria.getTotalTxnCount() + 1);

				break;

			case ServiceConstants.TXN_SALE:
				if (registerTotalsCriteria.getTotalSalesCount() != null) {
					registerTotalsCriteria.setTotalSalesCount(registerTotalsCriteria.getTotalSalesCount() + 1);
					registerTotalsCriteria.setTotalSalesamount(registerTotalsCriteria.getTotalSalesamount().add(registerTotals.getTotalTxnAmount()));
				} else {
					registerTotalsCriteria.setTotalSalesCount(1);
					registerTotalsCriteria.setTotalSalesamount(registerTotals.getTotalTxnAmount());
				}

				registerTotalsCriteria.setTotalTxnAmount(registerTotalsCriteria.getTotalTxnAmount().add(registerTotals.getTotalTxnAmount()));
				registerTotalsCriteria.setTotalTxnCount(registerTotalsCriteria.getTotalTxnCount() + 1);

				break;

			case ServiceConstants.TXN_OPEN_STORE:
			case ServiceConstants.TXN_OPEN_REGISTER:

				registerTotalsCriteria.setStartOfDayAmount(registerTotalsCriteria.getStartOfDayAmount().add(registerTotals.getTotalTxnAmount()));

				registerTotalsCriteria.setTotalTxnAmount(registerTotalsCriteria.getTotalTxnAmount().add(registerTotals.getTotalTxnAmount()));
				registerTotalsCriteria.setTotalTxnCount(registerTotalsCriteria.getTotalTxnCount() + 1);

				break;
			}

			updatedTotals = this.dailyTotalsRepository.save(registerTotalsCriteria);
			if (updatedTotals != null)
				logger.info("The register total details has been updated successfully");
			else
				logger.info("The register total details were not saved due to some issues.");

		}
		return updatedTotals;
	}

	@Override
	@Transactional
	public DailyTotals updateDailyTotals(DailyTotals dailyTotals, String action) {

		DailyTotals dailyTotalsOrg = new DailyTotals();
		dailyTotalsOrg.setBusinessDate(dailyTotals.getBusinessDate());
		dailyTotalsOrg.setRegisterId(dailyTotals.getRegisterId());
		dailyTotalsOrg.setLocationId(dailyTotals.getLocationId());
		dailyTotalsOrg.setTotalTxnAmount(dailyTotals.getTotalTxnAmount());

		DailyTotals registerTotals = this.updateRegisterTotals(dailyTotals, action);
		DailyTotals storeTotals = this.updateStoreTotals(dailyTotalsOrg, action);

		logger.info("The daily total details has been updated as requested");
		return registerTotals;
	}

	@Override
	public List<DailyTotals> retrieveDailyTotals(DailyTotals dailyTotalCriteria) {

		List<DailyTotals> dailyTotalsList = this.dailyTotalsRepository.findAll(Example.of(dailyTotalCriteria));

		if (dailyTotalsList != null && !dailyTotalsList.isEmpty()) {
			logger.info("The requested daily total records has been retrieved successfully");
		} else {
			logger.error("There was no record found for the daily totals based on provided criteria");
		}
		return dailyTotalsList;
	}

	@Override
	public DailyTotals postDailyTotalReversal(DailyTotals dailyTotals, String action) {

		dailyTotals.setTotalTxnAmount(dailyTotals.getTotalTxnAmount().negate());

		DailyTotals dailyTotalsOrg = new DailyTotals();
		dailyTotalsOrg.setBusinessDate(dailyTotals.getBusinessDate());
		dailyTotalsOrg.setRegisterId(dailyTotals.getRegisterId());
		dailyTotalsOrg.setLocationId(dailyTotals.getLocationId());
		dailyTotalsOrg.setTotalTxnAmount(dailyTotals.getTotalTxnAmount());

		DailyTotals registerTotals = this.updateRegisterTotalsReversal(dailyTotals, action);
		DailyTotals storeTotals = this.updateStoreTotalsReversal(dailyTotalsOrg, action);

		logger.info("The daily total details has been updated as requested");
		return registerTotals;
	}

	@Override
	public DailyTotals updateRegisterTotalsReversal(DailyTotals registerTotals, String action) {
		DailyTotals updatedTotals = null;
		DailyTotals registerTotalsCriteria = new DailyTotals();
		registerTotalsCriteria.setLocationId(registerTotals.getLocationId());
		registerTotalsCriteria.setRegisterId(registerTotals.getRegisterId());
		registerTotalsCriteria.setBusinessDate(registerTotals.getBusinessDate());
		registerTotalsCriteria = this.dailyTotalsRepository.findOne(Example.of(registerTotalsCriteria));
		if (registerTotalsCriteria != null) {
			registerTotals.setDailyTotalsId(registerTotalsCriteria.getDailyTotalsId());

			switch (action) {
			case ServiceConstants.ACTION_EXPENSE:

				if (registerTotalsCriteria.getTotalNoSalesCount() != null) {
					registerTotalsCriteria.setTotalNoSalesAmount(registerTotalsCriteria.getTotalNoSalesAmount().add(registerTotals.getTotalTxnAmount()));
					registerTotalsCriteria.setTotalNoSalesCount(registerTotalsCriteria.getTotalNoSalesCount() - 1);
				} else {
					registerTotalsCriteria.setTotalNoSalesAmount(registerTotals.getTotalTxnAmount());
					registerTotalsCriteria.setTotalNoSalesCount(1);
				}

				if (registerTotalsCriteria.getEndOfDayAmount() != null) {
					registerTotalsCriteria.setEndOfDayAmount(registerTotalsCriteria.getEndOfDayAmount().subtract(registerTotals.getTotalTxnAmount()));
				}

				registerTotalsCriteria.setTotalTxnAmount(registerTotalsCriteria.getTotalTxnAmount().subtract(registerTotals.getTotalTxnAmount()));
				registerTotalsCriteria.setTotalTxnCount(registerTotalsCriteria.getTotalTxnCount() - 1);

				break;

			case ServiceConstants.TXN_RETURN:

				if (registerTotalsCriteria.getTotalReturnCount() != null) {
					registerTotalsCriteria.setTotalReturnCount(registerTotalsCriteria.getTotalReturnCount() - 1);
					registerTotalsCriteria.setTotalReturnsamount(registerTotalsCriteria.getTotalReturnsamount().add(registerTotals.getTotalTxnAmount()));
				} else {
					registerTotalsCriteria.setTotalReturnCount(0);
					registerTotalsCriteria.setTotalReturnsamount(registerTotals.getTotalTxnAmount().negate());
				}
				if (registerTotalsCriteria.getEndOfDayAmount() != null) {
					registerTotalsCriteria.setEndOfDayAmount(registerTotalsCriteria.getEndOfDayAmount().add(registerTotals.getTotalTxnAmount()));
				}

				registerTotalsCriteria.setTotalTxnAmount(registerTotalsCriteria.getTotalTxnAmount().add(registerTotals.getTotalTxnAmount()));
				registerTotalsCriteria.setTotalTxnCount(registerTotalsCriteria.getTotalTxnCount() - 1);

				break;

			case ServiceConstants.TXN_SALE:
				if (registerTotalsCriteria.getTotalSalesCount() != null) {
					registerTotalsCriteria.setTotalSalesCount(registerTotalsCriteria.getTotalSalesCount() - 1);
					registerTotalsCriteria.setTotalSalesamount(registerTotalsCriteria.getTotalSalesamount().add(registerTotals.getTotalTxnAmount()));
				} else {
					registerTotalsCriteria.setTotalSalesCount(0);
					registerTotalsCriteria.setTotalSalesamount(registerTotals.getTotalTxnAmount());
				}

				if (registerTotalsCriteria.getEndOfDayAmount() != null) {
					registerTotalsCriteria.setEndOfDayAmount(registerTotalsCriteria.getEndOfDayAmount().add(registerTotals.getTotalTxnAmount()));
				}

				registerTotalsCriteria.setTotalTxnAmount(registerTotalsCriteria.getTotalTxnAmount().add(registerTotals.getTotalTxnAmount()));
				registerTotalsCriteria.setTotalTxnCount(registerTotalsCriteria.getTotalTxnCount() - 1);

				break;

			}

			updatedTotals = this.dailyTotalsRepository.save(registerTotalsCriteria);
			if (updatedTotals != null)
				logger.info("The register total reversal details has been updated successfully");
			else
				logger.info("The register total reversal details were not saved due to some issues.");

		}
		return updatedTotals;
	}

	@Override
	public DailyTotals updateStoreTotalsReversal(DailyTotals storeTotals, String action) {
		DailyTotals storeTotalsRcd = null;
		DailyTotals storeTotalsCriteria = new DailyTotals();
		storeTotalsCriteria.setLocationId(storeTotals.getLocationId());
		storeTotalsCriteria.setBusinessDate(storeTotals.getBusinessDate());
		List<DailyTotals> dailyTotalsList = this.dailyTotalsRepository.findAll(Example.of(storeTotalsCriteria));
		if (dailyTotalsList != null && !dailyTotalsList.isEmpty()) {
			for (DailyTotals dailyTotals : dailyTotalsList) {
				if (dailyTotals.getRegisterId() == null) {
					storeTotalsRcd = dailyTotals;
					break;
				}
			}
		}
		if (storeTotalsRcd != null) {
			switch (action) {
			case ServiceConstants.ACTION_EXPENSE:

				if (storeTotalsRcd.getTotalNoSalesAmount() != null) {
					storeTotalsRcd.setTotalNoSalesCount(storeTotalsRcd.getTotalNoSalesCount() - 1);
					storeTotalsRcd.setTotalNoSalesAmount(storeTotalsRcd.getTotalNoSalesAmount().add(storeTotals.getTotalTxnAmount()));
				} else {
					storeTotalsRcd.setTotalNoSalesCount(BigInteger.ZERO.intValue());
					storeTotalsRcd.setTotalNoSalesAmount(storeTotals.getTotalTxnAmount().negate());
				}

				if (storeTotalsRcd.getEndOfDayAmount() != null && storeTotalsRcd.getEndOfDayAmount().compareTo(BigDecimal.ZERO) > 0) {
					storeTotalsRcd.setEndOfDayAmount(storeTotalsRcd.getEndOfDayAmount().subtract(storeTotals.getTotalTxnAmount()));
				}

				storeTotalsRcd.setTotalTxnAmount(storeTotalsRcd.getTotalTxnAmount().subtract(storeTotals.getTotalTxnAmount()));
				storeTotalsRcd.setTotalTxnCount(storeTotalsRcd.getTotalTxnCount() - BigInteger.ONE.intValue());

				break;

			case ServiceConstants.TXN_RETURN:

				if (storeTotalsRcd.getTotalReturnCount() != null) {
					storeTotalsRcd.setTotalReturnCount(storeTotalsRcd.getTotalReturnCount() - 1);
					storeTotalsRcd.setTotalReturnsamount(storeTotalsRcd.getTotalReturnsamount().add(storeTotals.getTotalTxnAmount()));
				} else {
					storeTotalsRcd.setTotalReturnCount(BigInteger.ZERO.intValue());
					storeTotalsRcd.setTotalReturnsamount(storeTotals.getTotalTxnAmount().negate());
				}
				if (storeTotalsRcd.getEndOfDayAmount() != null && storeTotalsRcd.getEndOfDayAmount().compareTo(BigDecimal.ZERO) > 0) {
					storeTotalsRcd.setEndOfDayAmount(storeTotalsRcd.getEndOfDayAmount().add(storeTotals.getTotalTxnAmount()));
				}

				storeTotalsRcd.setTotalTxnAmount(storeTotalsRcd.getTotalTxnAmount().add(storeTotals.getTotalTxnAmount()));
				storeTotalsRcd.setTotalTxnCount(storeTotalsRcd.getTotalTxnCount() - BigInteger.ONE.intValue());

				break;

			case ServiceConstants.TXN_SALE:

				if (storeTotalsRcd.getTotalSalesCount() != null) {
					storeTotalsRcd.setTotalSalesCount(storeTotalsRcd.getTotalSalesCount() - 1);
					storeTotalsRcd.setTotalSalesamount(storeTotalsRcd.getTotalSalesamount().add(storeTotals.getTotalTxnAmount()));
				} else {
					storeTotalsRcd.setTotalSalesCount(BigInteger.ZERO.intValue());
					storeTotalsRcd.setTotalSalesamount(storeTotals.getTotalTxnAmount().negate());
				}

				if (storeTotalsRcd.getEndOfDayAmount() != null && storeTotalsRcd.getEndOfDayAmount().compareTo(BigDecimal.ZERO) > 0) {
					storeTotalsRcd.setEndOfDayAmount(storeTotalsRcd.getEndOfDayAmount().add(storeTotals.getTotalTxnAmount()));
				}

				storeTotalsRcd.setTotalTxnAmount(storeTotalsRcd.getTotalTxnAmount().add(storeTotals.getTotalTxnAmount()));
				storeTotalsRcd.setTotalTxnCount(storeTotalsRcd.getTotalTxnCount() - BigInteger.ONE.intValue());

				break;

			case ServiceConstants.TXN_OPEN_STORE:
			case ServiceConstants.TXN_OPEN_REGISTER:

				break;

			case ServiceConstants.TXN_CLOSE_STORE:
			case ServiceConstants.TXN_CLOSE_REGISTER:
				storeTotalsRcd.setTotalTxnCount(storeTotalsRcd.getTotalTxnCount() - BigInteger.ONE.intValue());

				break;
			}

			storeTotalsRcd = this.dailyTotalsRepository.save(storeTotalsRcd);
			if (storeTotalsRcd != null)
				logger.info("The store total reversal details has been updated successfully");
			else
				logger.info("The store total reversal details were not saved due to some issues.");

		}
		return storeTotalsRcd;
	}

	@Override
	public void resetRegDailyTotals(DailyTotals dailyTotalCriteria) {

		DailyTotals storeTotalsCriteria = new DailyTotals();
		storeTotalsCriteria.setBusinessDate(dailyTotalCriteria.getBusinessDate());
		storeTotalsCriteria.setLocationId(dailyTotalCriteria.getLocationId());

		List<DailyTotals> storeTotalsList = this.dailyTotalsRepository.findAll(Example.of(storeTotalsCriteria));

		DailyTotals storeTotalsActual = null;
		DailyTotals lastRegTotalsActual = null;

		if (storeTotalsList != null && !storeTotalsList.isEmpty()) {
			for (DailyTotals dlyTotal : storeTotalsList) {
				if (dlyTotal.getRegisterId() == null) {
					storeTotalsActual = dlyTotal;
				} else if (dlyTotal.getRegisterId().equals(dailyTotalCriteria.getRegisterId())) {
					lastRegTotalsActual = dlyTotal;
				}
			}
		}

		if (lastRegTotalsActual != null && storeTotalsActual != null) {
			storeTotalsActual.setEndOfDayAmount(null);
			this.dailyTotalsRepository.save(storeTotalsActual);

			lastRegTotalsActual.setEndOfDayAmount(null);
			this.dailyTotalsRepository.save(lastRegTotalsActual);

			logger.info("The last register and store EOD amounts has been updated successfully");

		}

	}

	@Override
	public void deleteRegDailyTotals(DailyTotals dailyTotalCriteria) {
		
		DailyTotals regTotals = this.dailyTotalsRepository.findOne(Example.of(dailyTotalCriteria));
		if (regTotals != null) {
			this.dailyTotalsRepository.delete(regTotals);
			logger.info("The register totals has been deleted successfully");
		}
		
	}

	@Override
	public void deleteStoreDailyTotals(DailyTotals dailyTotalCriteria) {
		DailyTotals storeTotalsCriteria = new DailyTotals();
		storeTotalsCriteria.setBusinessDate(dailyTotalCriteria.getBusinessDate());
		storeTotalsCriteria.setLocationId(dailyTotalCriteria.getLocationId());

		List<DailyTotals> storeTotalsList = this.dailyTotalsRepository.findAll(Example.of(storeTotalsCriteria));

		DailyTotals storeTotalsActual = null;

		if (storeTotalsList != null && !storeTotalsList.isEmpty()) {
			for (DailyTotals dlyTotal : storeTotalsList) {
				if (dlyTotal.getRegisterId() == null) {
					storeTotalsActual = dlyTotal;
					break;
				} 
			}
		}

		if (storeTotalsActual != null) {
			
			this.dailyTotalsRepository.delete(storeTotalsActual);
			logger.info("The store totals has been deleted  successfully");

		}
		
	}

}
