/**
 * 
 */
package com.punj.app.ecommerce.repositories.tax;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.tax.TaxBracket;

/**
 * @author admin
 *
 */
public interface TaxBracketRepository extends JpaRepository<TaxBracket, Integer> {

}
