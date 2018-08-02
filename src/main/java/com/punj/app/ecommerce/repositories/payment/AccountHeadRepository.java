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

/**
 * @author admin
 *
 */
public interface AccountHeadRepository extends JpaRepository<AccountHead, Integer> {

	@Query(value = "select ah.* from commercedb.account_head ah where ah.entity_id in (?1) and ah.entity_type=?2", nativeQuery = true)
	List<AccountHead> getAccounts(Set<BigInteger> entityIds, String entityType);
}
