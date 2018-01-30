/**
 * 
 */
package com.punj.app.ecommerce.services;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

import com.punj.app.ecommerce.domains.order.Order;
import com.punj.app.ecommerce.domains.order.OrderDTO;
import com.punj.app.ecommerce.domains.order.OrderItem;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
public interface OrderService {

	public Order createOrder(Order order);

	public Order searchOrder(BigInteger orderId);

	public OrderDTO searchOrder(String text, Pager pager);

	public OrderDTO searchAllOrders(Pager pager);

	public void deleteOrder(BigInteger orderId);

	public void deleteOrders(Set<BigInteger> orderIds);

	public List<Order> updateOrders(List<Order> orders);

	public List<Order> approveOrders(List<Order> orders);

	public Order approveOrder(BigInteger orderId);

	public void deleteOrderItem(OrderItem orderItem);

	public OrderDTO findAll();
	
	public Order receiveOrder(BigInteger orderId, String username);
	
}
