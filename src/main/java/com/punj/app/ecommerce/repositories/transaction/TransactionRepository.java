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

	@Query(value = "SELECT * FROM pi_pos_industry.txn_master WHERE location_id = ?1 and txn_type IN (?2) ORDER BY location_id, business_date DESC , start_time DESC limit 1", nativeQuery = true)
	public Transaction getTop1ByCriteriaAndSort(Integer locationId, Set<String> txnType);

	@Query(value = "SELECT \r\n" + 
			"    a.*\r\n" + 
			"FROM\r\n" + 
			"    pi_pos_industry.txn_master a,\r\n" + 
			"    (SELECT \r\n" + 
			"        location_id, register, max(business_date) max_business_date\r\n" + 
			"    FROM\r\n" + 
			"        pi_pos_industry.txn_master\r\n" + 
			"    WHERE\r\n" + 
			"        location_id = ?1\r\n" + 
			"            AND txn_type IN (?2) group by location_id, register ) b\r\n" + 
			"WHERE\r\n" + 
			"    a.location_id = b.location_id\r\n" + 
			"        AND a.business_date = b.max_business_date\r\n" + 
			"        AND a.register = b.register AND txn_type IN (?2) ORDER BY business_date desc, register asc, txn_no desc", nativeQuery = true)
	public List<Transaction> getLastDailyRegisterTxns(Integer locationId, Set<String> txnType);

	@Query(value = "SELECT MAX(txn_no) as txn_no FROM pi_pos_industry.txn_master WHERE location_id = ?1 AND register = ?2" + 
			" AND business_date = ?3 and txn_type in ('SALE','RETURN')", nativeQuery = true)
	public Integer getLastTransactionNo(Integer locationId, Integer registerId, LocalDateTime businessDate);
	

	@Query(value = "select * from pi_pos_industry.txn_master where location_id=?1 and business_date=?2 and txn_type='CLOSE_STORE'", nativeQuery = true)
	public Transaction getStoreCloseTxn(Integer locationId, String businessDate);
	
	@Query(value = "select * from pi_pos_industry.txn_master where location_id=?1 and business_date=?2 and txn_type='CLOSE_REGISTER' order by created_date desc limit 1", nativeQuery = true)
	public Transaction getLastRegisterCloseTxn(Integer locationId, String businessDate);
	
	@Query(value = "select result_data.* from pi_pos_industry.txn_master result_data where result_data.location_id=?1 and result_data.business_date>?2 order by created_date desc", nativeQuery = true)
	public List<Transaction> getFutureTxns(Integer locationId, String businessDate);
	
	@Query(value = "select * from pi_pos_industry.txn_master where location_id=?1 and business_date between ?2 and ?3 order by location_id asc, business_date asc, register asc,txn_no asc, created_date asc", nativeQuery = true)
	public List<Transaction> getSaleTxnsByMonth(Integer locationId, String startDate, String endDate);

}
