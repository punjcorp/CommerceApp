/**
 * 
 */
package com.punj.app.ecommerce.domains.customer.ids;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author admin
 *
 */

@Embeddable
public class CustomerAddressId implements Serializable {

	private static final long serialVersionUID = -5880870734951569492L;

	@Column(name = "customer_id")
	private BigInteger customerId;
	@Column(name = "address_id")
	private BigInteger addressId;

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
	 * @return the addressId
	 */
	public BigInteger getAddressId() {
		return addressId;
	}

	/**
	 * @param addressId
	 *            the addressId to set
	 */
	public void setAddressId(BigInteger addressId) {
		this.addressId = addressId;
	}

}
