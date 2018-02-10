package com.punj.app.ecommerce.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.money.CurrencyUnit;
import javax.money.Monetary;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Utils {
	private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
	private static final Logger logger = LogManager.getLogger();

	public static String encodePassword(String password) {
		return ENCODER.encode(password);

	}

	public static BCryptPasswordEncoder getPassEncoder() {
		return ENCODER;
	}

	public static String formatDate(LocalDateTime date) {
		return date.format(formatter);
	}

	public static CurrencyUnit getLocaleCurrency() {
		Locale locale = LocaleContextHolder.getLocale();
		return Monetary.getCurrency(locale);
	}


	/**
	 * Static method tester
	 */
	public static void main(String[] args) {
		logger.info("password is {}", Utils.encodePassword("admin"));

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yy HH:mm:ss");
		String formattedDate=LocalDateTime.now().format(formatter);
		System.out.println("here is the output "+formattedDate);
		LocalDateTime newDate=LocalDateTime.parse(formattedDate,formatter);
		System.out.println("here is the new output "+newDate);

	}

}