/**
 * 
 */
package com.punj.app.ecommerce.repositories.order;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.order.Order;

/**
 * @author admin
 *
 */
public interface OrderRepository extends JpaRepository<Order, BigInteger> {

}
