/**
 * 
 */
package com.punj.app.ecommerce.common;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
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
	private static final String DATE_FORMAT = "dd-MMM-yy";
	private static final String HRS_VALUE = " 00:00:00";
	private static final String DATE_TIME_FORMAT = "dd-MMM-yy HH:mm:ss";
		private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

	@Override
	public String print(LocalDateTime localDateTime, Locale locale) {
		logger.debug("The localdate is being formatter to String");
		return localDateTime.format(dateTimeFormatter);
	}

	@Override
	public LocalDateTime parse(String localDateTime, Locale locale) throws ParseException {
		logger.debug("The string date is being formatted as LocalDateTime");
		if (StringUtils.isNotEmpty(localDateTime) && localDateTime.length() <= DATE_FORMAT.length()) {
			localDateTime=localDateTime+HRS_VALUE;
		}
		return LocalDateTime.parse(localDateTime, dateTimeFormatter);
	}

}