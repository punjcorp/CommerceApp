/**
 * 
 */
package com.punj.app.ecommerce.repositories.tax;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.tax.TaxAuthority;

/**
 * @author admin
 *
 */
public interface TaxAuthorityRepository extends JpaRepository<TaxAuthority, Integer> {

}
