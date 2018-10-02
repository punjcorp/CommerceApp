/**
 * 
 */
package com.punj.app.ecommerce.repositories.item;

import java.math.BigInteger;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.punj.app.ecommerce.domains.item.Attribute;

/**
 * @author admin
 *
 */
public interface AttributeRepository extends JpaRepository<Attribute, BigInteger> {

	@Query(value = "select distinct attr_code from attribute_master", nativeQuery = true)
	public Set<String> getUniqueAttrCodes();

	@Query(value = "select distinct value_code from attribute_master", nativeQuery = true)
	public Set<String> getUniqueAttrValCodes();

}
