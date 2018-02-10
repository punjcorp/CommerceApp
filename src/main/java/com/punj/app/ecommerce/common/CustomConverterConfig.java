/**
 * 
 */
package com.punj.app.ecommerce.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author admin
 *
 */
@Configuration
public class CustomConverterConfig extends WebMvcConfigurerAdapter {
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addFormatter(new LocalDateTimeFormatter());
		registry.addConverter(new MonetaryAmountConverter());
	}
}
