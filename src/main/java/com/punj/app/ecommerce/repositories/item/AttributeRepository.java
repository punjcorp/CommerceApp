/**
 * 
 */
package com.punj.app.ecommerce.repositories.item;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.punj.app.ecommerce.domains.item.Attribute;
import com.punj.app.ecommerce.domains.tender.Tender;

/**
 * @author admin
 *
 */
public interface AttributeRepository extends JpaRepository<Attribute, BigInteger> {
	


}
