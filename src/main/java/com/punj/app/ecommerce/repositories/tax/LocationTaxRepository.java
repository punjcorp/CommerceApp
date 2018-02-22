/**
 * 
 */
package com.punj.app.ecommerce.repositories.tax;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.tax.LocationTax;
import com.punj.app.ecommerce.domains.tax.ids.LocationTaxId;

/**
 * @author admin
 *
 */
public interface LocationTaxRepository extends JpaRepository<LocationTax, LocationTaxId> {

}
