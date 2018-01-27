/**
 * 
 */
package com.punj.app.ecommerce.repositories.tax;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.tax.TaxRateRule;

/**
 * @author admin
 *
 */
public interface TaxRateRuleRepository extends JpaRepository<TaxRateRule, Integer> {

}
