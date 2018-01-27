/**
 * 
 */
package com.punj.app.ecommerce.repositories.price;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.price.ItemPrice;

/**
 * @author admin
 *
 */
public interface ItemPriceRepository extends JpaRepository<ItemPrice, BigInteger> {

}
