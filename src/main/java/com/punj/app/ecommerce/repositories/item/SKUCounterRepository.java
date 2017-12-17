/**
 * 
 */
package com.punj.app.ecommerce.repositories.item;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.item.SKUCounter;
import com.punj.app.ecommerce.domains.item.ids.SKUCounterId;

/**
 * @author admin
 *
 */
public interface SKUCounterRepository extends JpaRepository<SKUCounter, SKUCounterId> {

}
