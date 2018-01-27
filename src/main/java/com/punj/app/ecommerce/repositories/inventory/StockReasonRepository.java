/**
 * 
 */
package com.punj.app.ecommerce.repositories.inventory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.inventory.StockReason;

/**
 * @author admin
 *
 */
public interface StockReasonRepository extends JpaRepository<StockReason, Integer> {

}
