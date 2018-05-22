/**
 * 
 */
package com.punj.app.ecommerce.services.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

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
	public LedgerJournal saveLedgerDetails(LedgerJournal ledgerDetails) {

		ledgerDetails = this.ledgerJournalRepository.save(ledgerDetails);
		if (ledgerDetails != null)
			logger.info("The ledger details has been updated successfully");
		else
			logger.info("The ledger details were not saved due to some issues.");
		return ledgerDetails;
	}

	@Override
	public DailyTotals upsertStoreTotals(DailyTotals storeTotals) {
		
		DailyTotals storeTotalsCriteria=new DailyTotals();
		storeTotalsCriteria.setBusinessDate(storeTotals.getBusinessDate());
		storeTotalsCriteria.setLocationId(storeTotals.getLocationId());
		storeTotalsCriteria.setRegisterId(storeTotals.getRegisterId());
		
		storeTotalsCriteria=this.dailyTotalsRepository.findOne(Example.of(storeTotalsCriteria)); 
		
		if(storeTotalsCriteria!=null)
			storeTotals.setDailyTotalsId(storeTotalsCriteria.getDailyTotalsId());
	
		storeTotals = this.dailyTotalsRepository.save(storeTotals);
		if (storeTotals != null)
			logger.info("The store total details has been updated successfully");
		else
			logger.info("The store total details were not saved due to some issues.");
		return storeTotals;
	}

	@Override
	public DailyTotals upsertRegisterTotals(DailyTotals registerTotals) {
		registerTotals = this.dailyTotalsRepository.save(registerTotals);
		if (registerTotals != null)
			logger.info("The register total details has been updated successfully");
		else
			logger.info("The register total details were not saved due to some issues.");
		return registerTotals;
	}

	@Override
	public TenderMovement saveTenderMovement(TenderMovement moveDetails) {
		moveDetails = this.tenderMovementRepository.save(moveDetails);
		if (moveDetails != null)
			logger.info("The tender move transaction details has been updated successfully");
		else
			logger.info("The tender move transaction details were not saved due to some issues.");
		return moveDetails;
	}

	@Override
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

}
