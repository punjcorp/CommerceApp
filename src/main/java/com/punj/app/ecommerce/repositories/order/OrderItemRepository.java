/**
 * 
 */
package com.punj.app.ecommerce.repositories.order;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.order.OrderItem;

/**
 * @author admin
 *
 */
public interface OrderItemRepository extends JpaRepository<OrderItem, BigInteger> {

}
