package com.punj.app.ecommerce.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javamoney.moneta.Money;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.services.common.ServiceConstants;

public class Utils {
	private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
	private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
	private static final List<MonetaryAmount> denominations = new ArrayList<>();
	private static final Logger logger = LogManager.getLogger();

	public static String encodePassword(String password) {
		return ENCODER.encode(password);

	}

	public static BCryptPasswordEncoder getPassEncoder() {
		return ENCODER;
	}

	public static String showStatus(String status) {

		switch (status) {
		case MVCConstants.STATUS_CREATED:
			return MVCConstants.STATUS_CREATED_DESC;
		case MVCConstants.STATUS_APPROVED:
			return MVCConstants.STATUS_APPROVED_DESC;

		case MVCConstants.STATUS_DISABLED:
			return MVCConstants.STATUS_DISABLED_DESC;

		case MVCConstants.STATUS_RECEIVED:
			return MVCConstants.STATUS_RECEIVED_DESC;

		default:
			return "";
		}

	}

	public static String showPriceType(String priceType) {

		switch (priceType) {
		case MVCConstants.PRICE_TYPE_PERMANENT:
			return MVCConstants.PRICE_TYPE_PERMANENT_DESC;
		case MVCConstants.PRICE_TYPE_PROMOTION:
			return MVCConstants.PRICE_TYPE_PROMOTION_DESC;
		case MVCConstants.PRICE_TYPE_CLEARANCE:
			return MVCConstants.PRICE_TYPE_CLEARANCE_DESC;
		case MVCConstants.PRICE_TYPE_CLEARANCE_RESET:
			return MVCConstants.PRICE_TYPE_CLEARANCE_RESET_DESC;

		default:
			return "";
		}

	}
	
	public static String showLedgerAction(String txnType) {

		switch (txnType) {
		case ServiceConstants.TXN_SALE:
			return ServiceConstants.LEDGER_ACTION_SALE_TXN;
		case ServiceConstants.TXN_RETURN:
			return ServiceConstants.LEDGER_ACTION_RETURN_TXN;

		default:
			return "";
		}

	}

	public static LocalDateTime parseDate(String date) {
		return LocalDateTime.parse(date, dateFormatter);
	}

	public static String formatDate(LocalDateTime date) {
		return date.format(formatter);
	}

	public static CurrencyUnit getLocaleCurrency() {
		Locale locale = LocaleContextHolder.getLocale();
		return Monetary.getCurrency(locale);
	}

	public static String formatCurrency(BigDecimal amount) {
		return NumberFormat.getCurrencyInstance().format(amount);
	}

	public static List<MonetaryAmount> getDenominations() {
		if (denominations.isEmpty()) {
			denominations.add(Money.of(0.50, Utils.getLocaleCurrency()));
			denominations.add(Money.of(1, Utils.getLocaleCurrency()));
			denominations.add(Money.of(2, Utils.getLocaleCurrency()));
			denominations.add(Money.of(5, Utils.getLocaleCurrency()));
			denominations.add(Money.of(10, Utils.getLocaleCurrency()));
			denominations.add(Money.of(20, Utils.getLocaleCurrency()));
			denominations.add(Money.of(50, Utils.getLocaleCurrency()));
			denominations.add(Money.of(100, Utils.getLocaleCurrency()));
			denominations.add(Money.of(200, Utils.getLocaleCurrency()));
			denominations.add(Money.of(500, Utils.getLocaleCurrency()));
			denominations.add(Money.of(1000, Utils.getLocaleCurrency()));
			denominations.add(Money.of(2000, Utils.getLocaleCurrency()));
		}
		return denominations;
	}

	/**
	 * Static method tester
	 */
	public static void main(String[] args) {
		logger.info("password is {}", Utils.encodePassword("cashier"));

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yy HH:mm:ss");
		String formattedDate = LocalDateTime.now().format(formatter);
		System.out.println("here is the output " + formattedDate);
		LocalDateTime newDate = LocalDateTime.parse(formattedDate, formatter);
		System.out.println("here is the new output " + newDate);

	}

}