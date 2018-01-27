/**
 * 
 */
package com.punj.app.ecommerce.repositories.price;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.price.ItemPriceHistory;
import com.punj.app.ecommerce.domains.price.ids.ItemPriceHistoryId;

/**
 * @author admin
 *
 */
public interface ItemPriceHistoryRepository extends JpaRepository<ItemPriceHistory, ItemPriceHistoryId> {

}
