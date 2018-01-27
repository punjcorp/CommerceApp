/**
 * 
 */
package com.punj.app.ecommerce.repositories.inventory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.inventory.ItemStock;
import com.punj.app.ecommerce.domains.inventory.ids.ItemStockId;

/**
 * @author admin
 *
 */
public interface ItemStockRepository extends JpaRepository<ItemStock, ItemStockId> {

}
