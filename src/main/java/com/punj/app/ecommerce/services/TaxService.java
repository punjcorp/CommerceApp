/**
 * 
 */
package com.punj.app.ecommerce.services;

import java.util.List;

import com.punj.app.ecommerce.domains.item.Item;
import com.punj.app.ecommerce.domains.tax.TaxGroup;
import com.punj.app.ecommerce.domains.tax.TaxRateRule;
import com.punj.app.ecommerce.models.tax.TaxLineItem;

/**
 * @author admin
 *
 */
public interface TaxService {

	public TaxGroup retrieveTax(Integer taxGroupId, Integer locationId);

	public List<TaxRateRule> retrieveTaxRates(Integer taxGroupId, Integer locationId, Integer taxAuthorityId);

	public List<TaxLineItem> applyTax(Item item);

}
