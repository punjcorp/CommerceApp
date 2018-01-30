/**
 * 
 */
package com.punj.app.ecommerce.common;

import java.math.BigDecimal;
import java.util.Locale;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javamoney.moneta.Money;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author admin
 *
 */

@Component
public class MonetaryAmountConverter implements Converter<String, MonetaryAmount> {

	private static final Logger logger = LogManager.getLogger();

	@Override
	public MonetaryAmount convert(String amount) {
		amount = amount.replaceAll("[^\\d.]", "");
		Locale locale = LocaleContextHolder.getLocale();
		CurrencyUnit currenyUnit = Monetary.getCurrency(locale);
		MonetaryAmount money = null;
		if (StringUtils.isEmpty(amount)) {
			money = Money.of(0, currenyUnit);
		} else {
			amount = amount.replaceAll("[^\\d.]", "");
			money = Money.of(new BigDecimal(amount), currenyUnit);

		}
		logger.info("The money has been converted successfully.");
		return money;
	}

}