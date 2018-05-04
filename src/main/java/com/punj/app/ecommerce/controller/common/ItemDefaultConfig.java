/**
 * 
 */
package com.punj.app.ecommerce.controller.common;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * @author admin
 *
 */
@Configuration
@PropertySource(value = { "classpath:item_defaults.properties" })
public class ItemDefaultConfig {

	@Value("${item.default.hierarchy}")
	public String defaultHierarchy;

	@Value("${item.default.item.type}")
	public String defaultItemType;

	@Value("${item.default.uom}")
	public String defaultUOM;

	@Value("${item.default.pack.size}")
	public Integer defaultPackSize;

	@Value("${item.default.unit.cost}")
	public BigDecimal defaultUnitCost;
	@Value("${item.default.current.price}")
	public BigDecimal defaultCurrentPrice;	
	@Value("${item.default.mrp}")
	public BigDecimal defaultMRP;
	@Value("${item.default.suggested.price}")
	public BigDecimal defaultSuggestedPrice;
	@Value("${item.default.compareat.price}")
	public BigDecimal defaultCompareAtPrice;
	@Value("${item.default.restocking.fee}")
	public BigDecimal defaultRestockingFee;
	
	
	@Value("${item.default.discount.flag}")
	public Boolean defaultDiscountFlag;
	@Value("${item.default.return.flag}")
	public Boolean defaultReturnFlag;
	@Value("${item.default.tax.flag}")
	public Boolean defaultTaxFlag;
	@Value("${item.default.ask.qty.flag}")
	public Boolean defaultAskQtyFlag;
	@Value("${item.default.price.change.flag}")
	public Boolean defaultPriceChangeFlag;	
	

	// this bean needed to resolve ${property.name} syntax
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}

}
