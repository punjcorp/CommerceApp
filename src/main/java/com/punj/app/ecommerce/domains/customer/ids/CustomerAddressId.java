/**
 * 
 */
package com.punj.app.ecommerce.domains.customer.ids;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.punj.app.ecommerce.domains.user.Address;

/**
 * @author admin
 *
 */

@Embeddable
public class CustomerAddressId implements Serializable {

	private static final long serialVersionUID = 2082718906978987119L;

	@Column(name = "customer_id")
	private BigInteger customerId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id")
	private Address address;

	/**
	 * @return the customerId
	 */
	public BigInteger getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId
	 *            the customerId to set
	 */
	public void setCustomerId(BigInteger customerId) {
		this.customerId = customerId;
	}

	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

}
