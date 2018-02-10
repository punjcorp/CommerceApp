/**
 * 
 */
package com.punj.app.ecommerce.repositories.inventory;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.inventory.StockAdjustmentItem;

/**
 * @author admin
 *
 */
public interface StockAdjustmentItemRepository extends JpaRepository<StockAdjustmentItem, BigInteger> {

}
