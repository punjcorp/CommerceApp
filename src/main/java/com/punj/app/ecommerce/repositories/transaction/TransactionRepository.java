/**
 * 
 */
package com.punj.app.ecommerce.repositories.transaction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.punj.app.ecommerce.domains.transaction.Transaction;
import com.punj.app.ecommerce.domains.transaction.ids.TransactionId;

/**
 * @author admin
 *
 */
public interface TransactionRepository extends JpaRepository<Transaction, TransactionId> {

	@Query(value = "SELECT * FROM commercedb.txn_master WHERE location_id = ?1 and txn_type IN (?2) ORDER BY location_id, business_date DESC , start_time DESC limit 1", nativeQuery = true)
	Transaction getTop1ByCriteriaAndSort(Integer locationId, Set<String> txnType);

	@Query(value = "select a.* from commercedb.txn_master  a, (select location_id, business_date, register, max(start_time) start_time from commercedb.txn_master where location_id = ?1 and txn_type in (?2) group by location_id, business_date, register) b where a.location_id=b.location_id and a.business_date=b.business_date and a.register=b.register and a.start_time=b.start_time order by register ASC", nativeQuery = true)
	List<Transaction> getLastDailyRegisterTxns(Integer locationId, Set<String> txnType);

	@Query(value = "SELECT MAX(txn_no) as txn_no FROM commercedb.txn_master WHERE location_id = ?1 AND register = ?2" + 
			" AND business_date = ?3 and txn_type in ('SALE','RETURN')", nativeQuery = true)
	Integer getLastTransactionNo(Integer locationId, Integer registerId, LocalDateTime businessDate);

}
