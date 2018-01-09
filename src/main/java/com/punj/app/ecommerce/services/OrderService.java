/**
 * 
 */
package com.punj.app.ecommerce.services;

import com.punj.app.ecommerce.domains.order.Order;
import com.punj.app.ecommerce.domains.order.OrderDTO;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
public interface OrderService {

	public Order createOrder(Order order);

	public OrderDTO searchOrder(String text, Pager pager);

}
