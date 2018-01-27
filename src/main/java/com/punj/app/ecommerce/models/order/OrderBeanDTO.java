/**
 * 
 */
package com.punj.app.ecommerce.models.order;

import java.util.List;

/**
 * @author admin
 *
 */
public class OrderBeanDTO {

	private List<OrderBean> orders;
	private List<String> orderIds;

	/**
	 * @return the orders
	 */
	public List<OrderBean> getOrders() {
		return orders;
	}

	/**
	 * @param orders
	 *            the orders to set
	 */
	public void setOrders(List<OrderBean> orders) {
		this.orders = orders;
	}

	/**
	 * @return the orderIds
	 */
	public List<String> getOrderIds() {
		return orderIds;
	}

	/**
	 * @param orderIds
	 *            the orderIds to set
	 */
	public void setOrderIds(List<String> orderIds) {
		this.orderIds = orderIds;
	}

}
