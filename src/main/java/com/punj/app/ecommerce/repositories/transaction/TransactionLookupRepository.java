/**
 * 
 */
package com.punj.app.ecommerce.repositories.transaction;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.punj.app.ecommerce.domains.transaction.TransactionLookup;
import com.punj.app.ecommerce.domains.transaction.ids.TransactionLookupId;

/**
 * @author admin
 *
 */
public interface TransactionLookupRepository extends JpaRepository<TransactionLookup, TransactionLookupId> {

	@Query(value = "select txn_l.* from pi_pos_industry.v_txn_lookup txn_l where txn_l.txn_type in (?1) and txn_l.business_date>=?2 and txn_l.business_date<= ?3 ", nativeQuery = true)
	public List<TransactionLookup> searchTxns(Set<String> txnType, String startDate, String endDate);
}
