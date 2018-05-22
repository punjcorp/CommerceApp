/**
 * 
 */
package com.punj.app.ecommerce.services;

import java.time.LocalDateTime;
import java.util.List;

import com.punj.app.ecommerce.domains.transaction.Transaction;
import com.punj.app.ecommerce.domains.transaction.ids.TransactionId;
import com.punj.app.ecommerce.domains.transaction.tender.TenderCount;
import com.punj.app.ecommerce.services.dtos.DailyOpenTransaction;

/**
 * @author admin
 *
 */
public interface DailyDeedService {

	public Boolean saveStoreOpenTxn(DailyOpenTransaction storeOpenDetails, String username);

	public Boolean saveRegisterOpenTxn(DailyOpenTransaction storeOpenDetails, String username);

	public TenderCount searchTxnTenderCount(TransactionId txnId);

	public List<TenderCount> searchTxnTenderCounts(Integer locationId, Integer registerId, LocalDateTime businessDate);

}
