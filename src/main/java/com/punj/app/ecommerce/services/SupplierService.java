/**
 * 
 */
package com.punj.app.ecommerce.services;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

import com.punj.app.ecommerce.domains.supplier.Supplier;
import com.punj.app.ecommerce.domains.supplier.SupplierDTO;
import com.punj.app.ecommerce.domains.supplier.SupplierItem;
import com.punj.app.ecommerce.domains.user.Address;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
public interface SupplierService {

	public Supplier createSupplier(Supplier supplier);

	public List<Supplier> updateSupplier(List<Supplier> suppliers);

	public Supplier searchSupplier(Supplier supplier);

	public Supplier searchSupplier(Integer supplierId);

	public SupplierDTO searchSupplier(String text, Pager pager);

	public List<Supplier> getAll();

	public void delete(Integer supplierId);

	public void deleteSuppliers(Set<Integer> supplierIds);

	public void deleteAddress(Address supplierAddress, Integer supplierId);

	public SupplierItem getSupplierItem(BigInteger itemId, Integer supplierId);

}
