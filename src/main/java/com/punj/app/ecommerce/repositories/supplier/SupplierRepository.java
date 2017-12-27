/**
 * 
 */
package com.punj.app.ecommerce.repositories.supplier;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.supplier.Supplier;

/**
 * @author admin
 *
 */
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

}
