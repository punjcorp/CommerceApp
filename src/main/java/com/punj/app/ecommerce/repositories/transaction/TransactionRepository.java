/**
 * 
 */
package com.punj.app.ecommerce.repositories.transaction;

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

	/*
	 * @Query("select t from Transaction t where t.location_id in ?1 and t.txn_type in ?2"
	 * ) Transaction getByCriteria(List<Integer> locationIds, Set<String>
	 * txnType, Sort sort);
	 */

}
