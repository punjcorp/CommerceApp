/**
 * 
 */
package com.punj.app.ecommerce.repositories.item;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.item.Attribute;
import com.punj.app.ecommerce.domains.item.ids.AttributeId;

/**
 * @author admin
 *
 */
public interface AttributeRepository extends JpaRepository<Attribute, AttributeId> {

}
