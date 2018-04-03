/**
 * 
 */
package com.punj.app.ecommerce.repositories.order;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.order.OrderItemTax;
import com.punj.app.ecommerce.domains.order.ids.OrderItemTaxId;

/**
 * @author admin
 *
 */
public interface OrderItemTaxRepository extends JpaRepository<OrderItemTax, OrderItemTaxId> {

}
