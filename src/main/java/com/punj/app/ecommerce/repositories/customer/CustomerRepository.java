/**
 * 
 */
package com.punj.app.ecommerce.repositories.customer;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.customer.Customer;

/**
 * @author admin
 *
 */
public interface CustomerRepository extends JpaRepository<Customer, BigInteger> {

}
