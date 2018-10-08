/**
 * 
 */
package com.punj.app.ecommerce.repositories.tax;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.tax.TaxLocationMap;
import com.punj.app.ecommerce.domains.tax.ids.TaxLocationMapId;

/**
 * @author admin
 *
 */
public interface TaxLocationMapRepository extends JpaRepository<TaxLocationMap, TaxLocationMapId> {

}
