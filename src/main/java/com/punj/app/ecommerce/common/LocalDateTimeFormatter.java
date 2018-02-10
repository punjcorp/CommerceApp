/**
 * 
 */
package com.punj.app.ecommerce.common;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

/**
 * @author admin
 *
 */

@Component
public class LocalDateTimeFormatter implements Formatter<LocalDateTime> {

	private static final Logger logger = LogManager.getLogger();
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yy HH:mm:ss");

	@Override
	public String print(LocalDateTime localDateTime, Locale locale) {
		logger.debug("The localdate is being formatter to String");
		return localDateTime.format(formatter);
	}

	@Override
	public LocalDateTime parse(String localDateTime, Locale locale) throws ParseException {
		logger.debug("The string date is being formatted as LocalDateTime");
		return LocalDateTime.parse(localDateTime, formatter);
	}

}