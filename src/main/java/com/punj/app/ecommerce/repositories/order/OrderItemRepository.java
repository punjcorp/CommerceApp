/**
 * 
 */
package com.punj.app.ecommerce.repositories.order;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.order.OrderItem;
import com.punj.app.ecommerce.domains.order.ids.OrderItemId;

/**
 * @author admin
 *
 */
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemId> {

}
