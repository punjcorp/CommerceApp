/**
 * 
 */
package com.punj.app.ecommerce.models.supplier;

import java.util.List;

import com.punj.app.ecommerce.domains.supplier.Supplier;

/**
 * @author admin
 *
 */
public class SupplierDTO {

	private List<SupplierBean> suppliers;
	private List<String> supplierIds;

	/**
	 * @return the suppliers
	 */
	public List<SupplierBean> getSuppliers() {
		return suppliers;
	}

	/**
	 * @param suppliers
	 *            the suppliers to set
	 */
	public void setSuppliers(List<SupplierBean> suppliers) {
		this.suppliers = suppliers;
	}

	/**
	 * @return the supplierIds
	 */
	public List<String> getSupplierIds() {
		return supplierIds;
	}

	/**
	 * @param supplierIds
	 *            the supplierIds to set
	 */
	public void setSupplierIds(List<String> supplierIds) {
		this.supplierIds = supplierIds;
	}

}
