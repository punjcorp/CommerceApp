/**
 * 
 */
package com.punj.app.ecommerce.models.order.returns;

import java.util.List;

import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
public class ReturnBeanDTO {

	private List<OrderReturnBean> orderReturns;
	private List<String> orderReturnIds;
	private Pager pager;

	/**
	 * @return the orderReturns
	 */
	public List<OrderReturnBean> getOrderReturns() {
		return orderReturns;
	}

	/**
	 * @param orderReturns
	 *            the orderReturns to set
	 */
	public void setOrderReturns(List<OrderReturnBean> orderReturns) {
		this.orderReturns = orderReturns;
	}

	/**
	 * @return the orderReturnIds
	 */
	public List<String> getOrderReturnIds() {
		return orderReturnIds;
	}

	/**
	 * @param orderReturnIds
	 *            the orderReturnIds to set
	 */
	public void setOrderReturnIds(List<String> orderReturnIds) {
		this.orderReturnIds = orderReturnIds;
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
