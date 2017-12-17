/**
 * 
 */
package com.punj.app.ecommerce.repositories;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.user.Address;

/**
 * @author admin
 *
 */
public interface AddressRepository extends JpaRepository<Address, BigInteger> {

}
