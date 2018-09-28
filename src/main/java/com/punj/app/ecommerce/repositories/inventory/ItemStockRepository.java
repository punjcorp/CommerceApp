/**
 * 
 */
package com.punj.app.ecommerce.repositories.inventory;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.punj.app.ecommerce.domains.inventory.ItemStock;
import com.punj.app.ecommerce.domains.inventory.ids.ItemStockId;

/**
 * @author admin
 *
 */
public interface ItemStockRepository extends JpaRepository<ItemStock, ItemStockId> {

	@Query(value = "select * from item_stock where item_id=?1", nativeQuery = true)
	List<ItemStock> retrieveItemStocks(BigInteger itemId);
}
