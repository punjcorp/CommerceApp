/**
 * 
 */
package com.punj.app.ecommerce.services;

import java.util.List;
import java.util.Set;

import com.punj.app.ecommerce.domains.supplier.Supplier;
import com.punj.app.ecommerce.domains.user.Address;

/**
 * @author admin
 *
 */
public interface SupplierService {

	public Supplier createSupplier(Supplier supplier);

	public List<Supplier> updateSupplier(List<Supplier> suppliers);

	public Supplier searchSupplier(Supplier supplier);

	public List<Supplier> getAll();

	public void delete(Integer supplierId);

	public void deleteSuppliers(Set<Integer> supplierIds);

	public void deleteAddress(Address supplierAddress);

}
