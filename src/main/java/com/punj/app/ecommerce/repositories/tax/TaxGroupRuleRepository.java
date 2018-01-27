/**
 * 
 */
package com.punj.app.ecommerce.repositories.tax;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.tax.TaxGroupRule;
import com.punj.app.ecommerce.domains.tax.ids.TaxGroupRuleId;

/**
 * @author admin
 *
 */
public interface TaxGroupRuleRepository extends JpaRepository<TaxGroupRule, TaxGroupRuleId> {

}
