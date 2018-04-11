/**
 * 
 */
package com.punj.app.ecommerce.models.order;

import java.util.List;

import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
public class OrderBeansDTO {

	private List<OrderBean> orders;
	private List<String> orderIds;
	private Pager pager;

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

	/**
	 * @return the pager
	 */
	public Pager getPager() {
		return pager;
	}

	/**
	 * @param pager
	 *            the pager to set
	 */
	public void setPager(Pager pager) {
		this.pager = pager;
	}

}
