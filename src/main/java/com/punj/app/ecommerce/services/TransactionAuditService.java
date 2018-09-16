/**
 * 
 */
package com.punj.app.ecommerce.services;

import java.time.LocalDateTime;

import com.punj.app.ecommerce.domains.transaction.ids.TransactionId;

/**
 * @author admin
 *
 */
public interface TransactionAuditService {

	public Boolean createTransactionAuditVersion(TransactionId txnId);

	public Boolean createDailyTotalAuditVersion(Integer locationId, LocalDateTime businessDate, Integer registerId);

}
