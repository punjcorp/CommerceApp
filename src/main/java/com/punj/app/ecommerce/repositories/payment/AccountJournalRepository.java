/**
 * 
 */
package com.punj.app.ecommerce.repositories.payment;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.payment.AccountJournal;

/**
 * @author admin
 *
 */
public interface AccountJournalRepository extends JpaRepository<AccountJournal, BigInteger> {

}
