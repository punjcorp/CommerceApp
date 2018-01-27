/**
 * 
 */
package com.punj.app.ecommerce.repositories.tax;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.tax.TaxLocation;

/**
 * @author admin
 *
 */
public interface TaxLocationRepository extends JpaRepository<TaxLocation, Integer> {

}
