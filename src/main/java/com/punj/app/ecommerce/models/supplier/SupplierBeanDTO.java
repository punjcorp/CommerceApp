/**
 * 
 */
package com.punj.app.ecommerce.models.supplier;

import java.util.List;

import com.punj.app.ecommerce.models.common.AddressBean;

/**
 * @author admin
 *
 */
public class SupplierBeanDTO {

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

	public static SupplierBean[] getSuppliersForReport() {
		return new SupplierBean[1];

	}

	public static AddressBean[] getAddressForReport() {
		return new AddressBean[1];

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
