/**
 * 
 */
package com.punj.app.ecommerce.repositories.item;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.item.StyleCounter;

/**
 * @author admin
 *
 */
public interface StyleCounterRepository extends JpaRepository<StyleCounter, BigInteger> {

}
