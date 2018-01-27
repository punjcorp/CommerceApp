package com.punj.app.ecommerce.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
	
	
	/**
	 * Static method tester
	 */
	public static void main(String args[]) {
		logger.info("password is {}",Utils.encodePassword("sneha"));

	}

}