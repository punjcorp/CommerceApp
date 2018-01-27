/**
 * 
 */
package com.punj.app.ecommerce.repositories.tax;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.tax.TaxGroup;

/**
 * @author admin
 *
 */
public interface TaxGroupRepository extends JpaRepository<TaxGroup, Integer> {

}
