/**
 * 
 */
package com.punj.app.ecommerce.repositories.item;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.punj.app.ecommerce.domains.item.ItemAttribute;
import com.punj.app.ecommerce.domains.item.ids.ItemAttributeId;

/**
 * @author admin
 *
 */
public interface ItemAttributeRepository extends JpaRepository<ItemAttribute, ItemAttributeId> {

	@Query(value = "select * from item_attributes where attribute_id=?1", nativeQuery = true)
	public List<ItemAttribute> getItemAttrsByAttrId(BigInteger attributeId);

}
