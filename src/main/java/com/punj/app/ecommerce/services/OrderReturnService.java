/**
 * 
 */
package com.punj.app.ecommerce.services;

import java.math.BigInteger;

import com.punj.app.ecommerce.domains.order.returns.OrderReturn;
import com.punj.app.ecommerce.domains.order.returns.OrderReturnDTO;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
public interface OrderReturnService {

	public OrderReturn createOrderReturn(OrderReturn orderReturn, String username);

	public OrderReturn searchOrderReturn(BigInteger orderReturnId);
	
	public OrderReturnDTO searchOrderReturns(String text, Pager pager);

	public OrderReturn approveOrderReturn(BigInteger orderReturnId, String username);

	public Boolean deleteOrderReturn(BigInteger orderReturnId);
	
	public OrderReturn updateOrderReturn(OrderReturn orderReturn, String username);
	
}
