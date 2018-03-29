/**
 * 
 */
package com.punj.app.ecommerce.domains.supplier;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.punj.app.ecommerce.domains.supplier.ids.SupplierAddressId;

/**
 * @author admin
 *
 */
@Entity
@Table(name = "supplier_address")
public class SupplierAddress implements Serializable {

	private static final long serialVersionUID = 9179137389174644338L;
	
	@EmbeddedId
	private SupplierAddressId supplierAddressId;

	/**
	 * @return the supplierAddressId
	 */
	public SupplierAddressId getSupplierAddressId() {
		return supplierAddressId;
	}

	/**
	 * @param supplierAddressId
	 *            the supplierAddressId to set
	 */
	public void setSupplierAddressId(SupplierAddressId supplierAddressId) {
		this.supplierAddressId = supplierAddressId;
	}

}
