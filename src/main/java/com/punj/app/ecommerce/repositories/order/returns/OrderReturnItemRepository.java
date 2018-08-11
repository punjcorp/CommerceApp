/**
 * 
 */
package com.punj.app.ecommerce.repositories.order.returns;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.order.returns.OrderReturnItem;

/**
 * @author admin
 *
 */
public interface OrderReturnItemRepository extends JpaRepository<OrderReturnItem, BigInteger> {

}
