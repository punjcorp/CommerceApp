/**
 * 
 */
package com.punj.app.ecommerce.repositories.inventory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.inventory.StockBucket;

/**
 * @author admin
 *
 */
public interface StockBucketRepository extends JpaRepository<StockBucket, Integer> {

}
