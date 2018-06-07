/**
 * 
 */
package com.punj.app.ecommerce.repositories.item;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.item.ItemImage;

/**
 * @author admin
 *
 */
public interface ItemImageRepository extends JpaRepository<ItemImage, BigInteger> {

}
