/**
 * 
 */
package com.punj.app.ecommerce.repositories.price;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.punj.app.ecommerce.domains.price.ItemPrice;

/**
 * @author admin
 *
 */
public interface ItemPriceRepository extends JpaRepository<ItemPrice, BigInteger> {

	@Procedure("p_get_current_item_price")
	public BigInteger findCurrentPrice(@Param("i_item_id") BigInteger itemId, @Param("i_location_id") Integer locationId);

}
