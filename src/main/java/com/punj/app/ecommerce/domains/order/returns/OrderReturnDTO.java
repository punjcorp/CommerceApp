/**
 * 
 */
package com.punj.app.ecommerce.domains.order.returns;

import java.io.Serializable;
import java.util.List;

import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
public class OrderReturnDTO implements Serializable {

	private static final long serialVersionUID = -4520201716152751113L;

	private List<OrderReturn> orderReturns;
	private Pager pager;

	/**
	 * @return the orderReturns
	 */
	public List<OrderReturn> getOrderReturns() {
		return orderReturns;
	}

	/**
	 * @param orderReturns
	 *            the orderReturns to set
	 */
	public void setOrderReturns(List<OrderReturn> orderReturns) {
		this.orderReturns = orderReturns;
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
