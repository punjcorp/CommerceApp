/**
 * 
 */
package com.punj.app.ecommerce.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.punj.app.ecommerce.domains.transaction.ids.TransactionId;
import com.punj.app.ecommerce.domains.transaction.tender.TenderCount;
import com.punj.app.ecommerce.services.common.dtos.RegisterDTO;
import com.punj.app.ecommerce.services.dtos.DailyTransaction;

/**
 * @author admin
 *
 */
public interface DailyDeedService {

	public Boolean saveStoreOpenTxn(DailyTransaction storeOpenDetails, String username);

	public Boolean saveRegisterOpenTxn(DailyTransaction storeOpenDetails, String username);

	public Boolean saveRegisterCloseTxn(DailyTransaction registerOpenDetails, String username);

	public Boolean saveStoreCloseTxn(DailyTransaction registerOpenDetails, String username);

	public TenderCount searchTxnTenderCount(TransactionId txnId);

	public List<TenderCount> searchTxnTenderCounts(Integer locationId, Integer registerId, LocalDateTime businessDate, String txnType);

	public Map<Integer, List<TenderCount>> retrieveTenderCounts(Integer locationId, LocalDateTime businessDate, String txnType);

	public RegisterDTO retrieveRegisterConcilationDtls(Integer locationId, LocalDateTime businessDate, String txnType);

}
