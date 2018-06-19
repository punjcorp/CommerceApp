/**
 * 
 */
package com.punj.app.ecommerce.domains.customer;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.punj.app.ecommerce.domains.customer.ids.CustomerAddressId;

/**
 * @author admin
 *
 */
@Entity
@Table(name = "customer_address")
public class CustomerAddress implements Serializable {

	private static final long serialVersionUID = -8824831222913085437L;

	@EmbeddedId
	private CustomerAddressId customerAddressId;

	/**
	 * @return the customerAddressId
	 */
	public CustomerAddressId getCustomerAddressId() {
		return customerAddressId;
	}

	/**
	 * @param customerAddressId the customerAddressId to set
	 */
	public void setCustomerAddressId(CustomerAddressId customerAddressId) {
		this.customerAddressId = customerAddressId;
	}
	
}
