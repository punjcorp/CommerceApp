package com.punj.app.ecommerce.controller.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CustomPropertyLoader {

	@Autowired
	private StandardEnvironment environment;

	@Scheduled(fixedRate = 1000000)
	public void reload() throws IOException {
		MutablePropertySources propertySources = environment.getPropertySources();
		PropertySource<?> resourcePropertySource = propertySources.get("class path resource [item_defaults.properties]");
		Properties properties = new Properties();
		InputStream inputStream = getClass().getResourceAsStream("/item_defaults.properties");
		properties.load(inputStream);
		inputStream.close();
		propertySources.replace("class path resource [item_defaults.properties]", new PropertiesPropertySource("class path resource [config.properties]", properties));
	}
}