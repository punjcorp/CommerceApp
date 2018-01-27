/**
 * 
 */
package com.punj.app.ecommerce.repositories.inventory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.inventory.ItemStockBucket;
import com.punj.app.ecommerce.domains.inventory.ids.ItemStockBucketId;

/**
 * @author admin
 *
 */
public interface ItemStockBucketRepository extends JpaRepository<ItemStockBucket, ItemStockBucketId> {

}
