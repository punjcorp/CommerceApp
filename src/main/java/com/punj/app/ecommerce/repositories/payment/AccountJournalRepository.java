/**
 * 
 */
package com.punj.app.ecommerce.repositories.payment;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.punj.app.ecommerce.domains.payment.AccountHead;
import com.punj.app.ecommerce.domains.payment.AccountJournal;

/**
 * @author admin
 *
 */
public interface AccountJournalRepository extends JpaRepository<AccountJournal, BigInteger> {
	
	@Query(value = "select aj.* from commercedb.account_journal aj where aj.account_id in (?1)", nativeQuery = true)
	List<AccountJournal> getAccountJournals(Set<Integer> accountIds);

	@Query(value = "select aj.* from commercedb. account_journal aj, commercedb.account_head ah where aj.account_id=ah.account_id and ah.entity_type = ?1 and aj.ledger_generated = 'N' order by created_date desc", nativeQuery = true) 
	List<AccountJournal> retrieveAccountJournals(String accountType);
}
