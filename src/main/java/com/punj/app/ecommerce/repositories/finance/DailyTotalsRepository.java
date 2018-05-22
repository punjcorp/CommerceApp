/**
 * 
 */
package com.punj.app.ecommerce.repositories.finance;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.finance.DailyTotals;

/**
 * @author admin
 *
 */
public interface DailyTotalsRepository extends JpaRepository<DailyTotals, BigInteger> {

}
