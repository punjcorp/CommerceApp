/**
 * 
 */
package com.punj.app.ecommerce.repositories.order.returns;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.order.returns.OrderReturn;

/**
 * @author admin
 *
 */
public interface OrderReturnRepository extends JpaRepository<OrderReturn, BigInteger> {

}
