/**
 * 
 */
package com.punj.app.ecommerce.services;

import java.util.List;

import com.punj.app.ecommerce.domains.finance.DailySafe;
import com.punj.app.ecommerce.domains.finance.DailyTotals;
import com.punj.app.ecommerce.domains.finance.LedgerJournal;
import com.punj.app.ecommerce.domains.finance.LocationSafe;
import com.punj.app.ecommerce.domains.finance.TenderMovement;

/**
 * @author admin
 *
 */
public interface FinanceService {

	public LedgerJournal saveLedgerDetails(LedgerJournal ledgerDetails);

	public DailyTotals upsertStoreTotals(DailyTotals storeTotals);

	public DailyTotals upsertRegisterTotals(DailyTotals registerTotals);

	public DailyTotals updateDailyTotals(DailyTotals storeTotals, String action);
	
	public DailyTotals updateStoreTotals(DailyTotals storeTotals, String action);

	public DailyTotals updateRegisterTotals(DailyTotals registerTotals, String action);
	
	public TenderMovement saveTenderMovement(TenderMovement moveDetails);

	public List<DailySafe> createDailySafe(List<DailySafe> safeDetailList);

	public LocationSafe retrieveSafeDetails(Integer locationId, Integer tenderId);

	public List<LocationSafe> retrieveAllSafes(Integer locationId);

}
