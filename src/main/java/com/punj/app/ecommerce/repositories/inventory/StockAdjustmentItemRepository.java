/**
 * 
 */
package com.punj.app.ecommerce.repositories.inventory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.inventory.StockAdjustmentItem;
import com.punj.app.ecommerce.domains.inventory.ids.StockAdjustmentItemId;

/**
 * @author admin
 *
 */
public interface StockAdjustmentItemRepository extends JpaRepository<StockAdjustmentItem, StockAdjustmentItemId> {

}
