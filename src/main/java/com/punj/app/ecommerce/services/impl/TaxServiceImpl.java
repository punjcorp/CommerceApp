/**
 * 
 */
package com.punj.app.ecommerce.services.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.punj.app.ecommerce.domains.item.Item;
import com.punj.app.ecommerce.domains.tax.TaxGroup;
import com.punj.app.ecommerce.domains.tax.TaxRateRule;
import com.punj.app.ecommerce.models.tax.TaxLineItem;
import com.punj.app.ecommerce.repositories.tax.TaxGroupRepository;
import com.punj.app.ecommerce.repositories.tax.TaxGroupRuleRepository;
import com.punj.app.ecommerce.repositories.tax.TaxRateRuleRepository;
import com.punj.app.ecommerce.services.TaxService;

/**
 * @author admin
 *
 */
@Service
public class TaxServiceImpl implements TaxService {

	private static final Logger logger = LogManager.getLogger();
	private TaxGroupRepository taxGroupRepository;
	private TaxGroupRuleRepository taxGroupRuleRepository;
	private TaxRateRuleRepository taxRateRuleRepository;

	@Value("${commerce.list.max.perpage}")
	private Integer maxResultPerPage;

	@Value("${commerce.list.max.pageno}")
	private Integer maxPageBtns;

	/**
	 * @return the itemRepository
	 */
	public TaxGroupRepository getTaxGroupRepository() {
		return taxGroupRepository;
	}

	/**
	 * @param taxGroupRepository
	 *            the taxGroupRepository to set
	 */
	@Autowired
	public void setTaxGroupRepository(TaxGroupRepository taxGroupRepository) {
		this.taxGroupRepository = taxGroupRepository;
	}

	/**
	 * @return the taxGroupRuleRepository
	 */
	public TaxGroupRuleRepository getTaxGroupRuleRepository() {
		return taxGroupRuleRepository;
	}

	/**
	 * @param taxGroupRuleRepository
	 *            the taxGroupRuleRepository to set
	 */
	@Autowired
	public void setTaxGroupRuleRepository(TaxGroupRuleRepository taxGroupRuleRepository) {
		this.taxGroupRuleRepository = taxGroupRuleRepository;
	}

	/**
	 * @return the taxRateRuleRepository
	 */
	public TaxRateRuleRepository getTaxRateRuleRepository() {
		return taxRateRuleRepository;
	}

	/**
	 * @param taxRateRuleRepository
	 *            the taxRateRuleRepository to set
	 */
	@Autowired
	public void setTaxRateRuleRepository(TaxRateRuleRepository taxRateRuleRepository) {
		this.taxRateRuleRepository = taxRateRuleRepository;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.punj.app.ecommerce.services.TaxService#retrieveTax(java.math.BigInteger,
	 * java.lang.Integer)
	 */
	@Override
	public TaxGroup retrieveTax(Integer taxGroupId, Integer locationId) {

		TaxGroup taxGroup = this.taxGroupRepository.findOne(taxGroupId);

		logger.info("The tax group details has been retrieved successfully");

		return taxGroup;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.punj.app.ecommerce.services.TaxService#retrieveTaxRates(java.lang.
	 * Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<TaxRateRule> retrieveTaxRates(Integer taxGroupId, Integer locationId, Integer taxAuthorityId) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.punj.app.ecommerce.services.TaxService#applyTax(com.punj.app.ecommerce.
	 * domains.item.Item)
	 */
	@Override
	public List<TaxLineItem> applyTax(Item item) {
		// TODO Auto-generated method stub
		return null;
	}

}
