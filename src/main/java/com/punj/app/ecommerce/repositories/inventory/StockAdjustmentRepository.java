/**
 * 
 */
package com.punj.app.ecommerce.repositories.inventory;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.inventory.StockAdjustment;

/**
 * @author admin
 *
 */
public interface StockAdjustmentRepository extends JpaRepository<StockAdjustment, BigInteger> {

}
