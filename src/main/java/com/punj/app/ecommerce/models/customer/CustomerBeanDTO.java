/**
 * 
 */
package com.punj.app.ecommerce.models.customer;

import java.util.List;

import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
public class CustomerBeanDTO {

	private List<CustomerBean> customers;
	private List<String> customerIds;
	private Pager pager;

	/**
	 * @return the customers
	 */
	public List<CustomerBean> getCustomers() {
		return customers;
	}

	/**
	 * @param customers
	 *            the customers to set
	 */
	public void setCustomers(List<CustomerBean> customers) {
		this.customers = customers;
	}

	/**
	 * @return the customerIds
	 */
	public List<String> getCustomerIds() {
		return customerIds;
	}

	/**
	 * @param customerIds
	 *            the customerIds to set
	 */
	public void setCustomerIds(List<String> customerIds) {
		this.customerIds = customerIds;
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
