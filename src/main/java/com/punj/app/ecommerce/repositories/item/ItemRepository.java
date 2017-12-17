/**
 * 
 */
package com.punj.app.ecommerce.repositories.item;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.item.Item;

/**
 * @author admin
 *
 */
public interface ItemRepository extends JpaRepository<Item, BigInteger> {

}
