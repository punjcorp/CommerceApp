/**
 * 
 */
package com.punj.app.ecommerce.services;

import com.punj.app.ecommerce.domains.order.returns.OrderReturn;

/**
 * @author admin
 *
 */
public interface OrderReturnService {

	public OrderReturn createOrderReturn(OrderReturn orderReturn, String username);

}
