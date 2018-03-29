/**
 * 
 */
package com.punj.app.ecommerce.domains.supplier.ids;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author admin
 *
 */

@Embeddable
public class SupplierAddressId implements Serializable {

	private static final long serialVersionUID = -2743584053445878461L;
	
	@Column(name = "supplier_id")
	private Integer supplierId;
	@Column(name = "address_id")
	private BigInteger addressId;

	/**
	 * @return the supplierId
	 */
	public Integer getSupplierId() {
		return supplierId;
	}

	/**
	 * @param supplierId
	 *            the supplierId to set
	 */
	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
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
