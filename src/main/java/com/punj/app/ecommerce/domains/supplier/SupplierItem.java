/**
 * 
 */
package com.punj.app.ecommerce.domains.supplier;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.punj.app.ecommerce.domains.supplier.ids.SupplierItemId;

/**
 * @author admin
 *
 */
@Entity
@Table(name = "supplier_item")
public class SupplierItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SupplierItemId supplierItemId;

	@Column(name = "unit_cost")
	private BigDecimal unitCost;

	/**
	 * @return the supplierItemId
	 */
	public SupplierItemId getSupplierItemId() {
		return supplierItemId;
	}

	/**
	 * @param supplierItemId
	 *            the supplierItemId to set
	 */
	public void setSupplierItemId(SupplierItemId supplierItemId) {
		this.supplierItemId = supplierItemId;
	}

	/**
	 * @return the unitCost
	 */
	public BigDecimal getUnitCost() {
		return unitCost;
	}

	/**
	 * @param unitCost
	 *            the unitCost to set
	 */
	public void setUnitCost(BigDecimal unitCost) {
		this.unitCost = unitCost;
	}

}
