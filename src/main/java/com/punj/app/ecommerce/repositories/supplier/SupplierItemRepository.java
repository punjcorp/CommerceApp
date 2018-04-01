/**
 * 
 */
package com.punj.app.ecommerce.repositories.supplier;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.supplier.SupplierItem;
import com.punj.app.ecommerce.domains.supplier.ids.SupplierItemId;

/**
 * @author admin
 *
 */
public interface SupplierItemRepository extends JpaRepository<SupplierItem, SupplierItemId> {

}
