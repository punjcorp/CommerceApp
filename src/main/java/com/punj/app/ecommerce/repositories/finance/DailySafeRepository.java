/**
 * 
 */
package com.punj.app.ecommerce.repositories.finance;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.finance.DailySafe;

/**
 * @author admin
 *
 */
public interface DailySafeRepository extends JpaRepository<DailySafe, Integer> {

}
