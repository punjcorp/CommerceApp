/**
 * 
 */
package com.punj.app.ecommerce.repositories.finance;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.finance.LedgerJournal;

/**
 * @author admin
 *
 */
public interface LedgerJournalRepository extends JpaRepository<LedgerJournal, BigInteger> {

}
