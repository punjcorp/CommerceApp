/**
 * 
 */
package com.punj.app.ecommerce.repositories.price;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

	@Procedure("p_get_oldest_clearance")
	public BigInteger findOldestClearance(@Param("i_item_id") BigInteger itemId, @Param("i_location_id") Integer locationId);

	@Query(value = "select itmprc.* from commercedb.item_price itmprc where itmprc.item_id = ?1 AND itmprc.location_id = ?2  and itmprc.status='A' and itmprc.start_date > now() and itmprc.price_change_type<>4 order by itmprc.start_date asc, itmprc.price_change_type desc", nativeQuery = true)
	public List<ItemPrice> getItemPricesByDate(BigInteger itemId, Integer locationId);
	
	@Query(value = "select itmprc.* from commercedb.item_price itmprc where itmprc.item_id = ?1 AND itmprc.location_id = ?2  and itmprc.status='A' and itmprc.price_change_type<>4 order by itmprc.start_date asc, itmprc.price_change_type desc", nativeQuery = true)
	public List<ItemPrice> getItemPricesByLocation(BigInteger itemId, Integer locationId);
	
	@Query(value = "select itmprc.* from commercedb.item_price itmprc where itmprc.item_id = ?1 and itmprc.status='A' and itmprc.price_change_type<>4 order by itmprc.start_date asc, itmprc.price_change_type desc", nativeQuery = true)
	public List<ItemPrice> getAllItemPrices(BigInteger itemId);

}
