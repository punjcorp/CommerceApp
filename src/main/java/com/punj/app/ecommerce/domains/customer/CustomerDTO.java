/**
 * 
 */
package com.punj.app.ecommerce.domains.customer;

import java.util.List;

import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
public class CustomerDTO {
	List<Customer> customers;
	Pager pager;

	/**
	 * @return the customers
	 */
	public List<Customer> getCustomers() {
		return customers;
	}

	/**
	 * @param customers
	 *            the customers to set
	 */
	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
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
