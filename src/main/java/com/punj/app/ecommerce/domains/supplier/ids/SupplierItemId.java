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
public class SupplierItemId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "supplier_id")
	private Integer supplierId;
	@Column(name = "item_id")
	private BigInteger itemId;

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
	 * @return the itemId
	 */
	public BigInteger getItemId() {
		return itemId;
	}

	/**
	 * @param itemId
	 *            the itemId to set
	 */
	public void setItemId(BigInteger itemId) {
		this.itemId = itemId;
	}

}
