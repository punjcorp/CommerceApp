package com.punj.app.ecommerce.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javamoney.moneta.Money;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.domains.item.Attribute;
import com.punj.app.ecommerce.services.common.ServiceConstants;

public class Utils {
	private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
	private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
	private static final DateTimeFormatter dateFormatterShort = DateTimeFormatter.ofPattern("dd-MMM-yy'T'HH:mm");
	private static final List<MonetaryAmount> denominations = new ArrayList<>();
	private static final Logger logger = LogManager.getLogger();

	public static String encodePassword(String password) {
		return ENCODER.encode(password);

	}

	public static BCryptPasswordEncoder getPassEncoder() {
		return ENCODER;
	}

	public static LocalDateTime convertMonthToStartDate(Integer month) {
		LocalDate dateValue = LocalDate.now().withMonth(month);
		LocalTime timeValue = LocalTime.of(0, 0, 0);

		LocalDateTime startDate = LocalDateTime.of(dateValue, timeValue).withMonth(month).withDayOfMonth(1);
		logger.info("The month has been converted to start of the month date");
		return startDate;
	}

	public static LocalDateTime convertMonthToEndDate(Integer month) {
		LocalDate dateValue = LocalDate.now().withMonth(month);
		LocalTime timeValue = LocalTime.of(0, 0, 0);

		LocalDateTime endDate = LocalDateTime.of(dateValue, timeValue).withMonth(month + 1).withDayOfMonth(1).minusDays(1);
		logger.info("The month has been converted to end of the month date");
		return endDate;
	}

	public static String getCustomerJournalDisplay(String journalType) {

		switch (journalType) {
		case MVCConstants.PAYMENT_FULL:
			return MVCConstants.PAYMENT_FULL_DISPLAY;
		case MVCConstants.PAYMENT_PART:
			return MVCConstants.PAYMENT_PART_DISPLAY;
		case MVCConstants.PAYMENT_ADVANCE:
			return MVCConstants.PAYMENT_ADVANCE_DISPLAY;

		case MVCConstants.PAYMENT_CREDIT:
			return MVCConstants.PAYMENT_CREDIT_DISPLAY;

		case MVCConstants.CUST_JOURNAL_CREDIT:
			return MVCConstants.JOURNAL_CREDIT_CUST_DISPLAY;
		case MVCConstants.CUST_JOURNAL_CREDIT_RETURN:
			return MVCConstants.JOURNAL_CREDIT_RETURN_CUST_DISPLAY;

		default:
			return journalType;
		}

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

	public static String showSupplierTxn(String txnType) {

		switch (txnType) {
		case ServiceConstants.PAYMENT_FULL:
		case ServiceConstants.PAYMENT_PART:
		case ServiceConstants.PAYMENT_ADVANCE:
			return ServiceConstants.TXN_SUPPLIER_PAYMENT;
		case ServiceConstants.PAYMENT_DUE:
			return ServiceConstants.TXN_PURCHASE_ORDER_RECEIVED;
		case ServiceConstants.PAYMENT_CREDIT:
			return ServiceConstants.TXN_ORDER_RETURN;
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

		case ServiceConstants.TXN_SUPPLIER_PAYMENT:
			return ServiceConstants.LEDGER_ACTION_PAYMENT_SUPPLIER;
		case ServiceConstants.TXN_PURCHASE_ORDER_RECEIVED:
			return ServiceConstants.LEDGER_ACTION_SUPPLIER_PO_RECEIVED;
		case ServiceConstants.TXN_ORDER_RETURN:
			return ServiceConstants.LEDGER_ACTION_SUPPLIER_PO_RETURN;

		case ServiceConstants.TXN_CUSTOMER_PAYMENT:
			return ServiceConstants.LEDGER_ACTION_PAYMENT_CUSTOMER;
		default:
			return "";
		}

	}

	public static LocalDateTime parseDate(String date) {
		if (date.length() == 9) {
			date = date + "T00:00";
			return LocalDateTime.parse(date, dateFormatterShort);
		} else {
			return LocalDateTime.parse(date, dateFormatter);
		}

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
		LocalDate currentDate = LocalDate.now();
		String utilString = RandomStringUtils.random(142, Boolean.TRUE, Boolean.TRUE);
		String monthDay = String.format("%02d", currentDate.getDayOfMonth());
		String month = String.format("%02d", currentDate.getMonthValue());
		int year = currentDate.getYear();
		
		StringBuffer finalResult=new StringBuffer();
		finalResult.append(utilString.substring(0, 27));
		finalResult.append(monthDay);
		finalResult.append(utilString.substring(28, 37));
		finalResult.append(month);
		finalResult.append(utilString.substring(38, 119));
		finalResult.append(year);
		finalResult.append(utilString.substring(120, 141));

		System.out.println(finalResult.toString());
		
		System.out.println(Utils.encodePassword("kashish"));

	}

	public static String generateUniqueCode(Set<String> exisingCodes) {
		String attrCode = RandomStringUtils.random(2, true, false);
		attrCode =attrCode.toUpperCase();
		if (exisingCodes.contains(attrCode))
			return null;

		return attrCode;

	}

}