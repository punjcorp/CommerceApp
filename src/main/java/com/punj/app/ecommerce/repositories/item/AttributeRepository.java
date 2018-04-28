/**
 * 
 */
package com.punj.app.ecommerce.repositories.item;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.item.Attribute;

/**
 * @author admin
 *
 */
public interface AttributeRepository extends JpaRepository<Attribute, BigInteger> {

}
