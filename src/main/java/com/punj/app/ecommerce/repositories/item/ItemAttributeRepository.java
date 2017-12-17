/**
 * 
 */
package com.punj.app.ecommerce.repositories.item;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.item.ItemAttribute;
import com.punj.app.ecommerce.domains.item.ids.AttributeId;

/**
 * @author admin
 *
 */
public interface ItemAttributeRepository extends JpaRepository<ItemAttribute, AttributeId> {

}
