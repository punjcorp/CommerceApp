/**
 * 
 */
package com.punj.app.ecommerce.services.common.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.punj.app.ecommerce.domains.common.AppConfig;
import com.punj.app.ecommerce.repositories.common.AppConfigRepository;
import com.punj.app.ecommerce.repositories.common.LocationRepository;
import com.punj.app.ecommerce.repositories.common.StateRepository;
import com.punj.app.ecommerce.services.common.ConfigService;

/**
 * @author admin
 *
 */
@Service
public class ConfigServiceImpl implements ConfigService {

	private static final Logger logger = LogManager.getLogger();
	private LocationRepository locationRepository;
	private AppConfigRepository appConfigRepository;
	
	
	/**
	 * @param appConfigRepository
	 *            the appConfigRepository to set
	 */
	@Autowired
	public void setAppConfigRepository(AppConfigRepository appConfigRepository) {
		this.appConfigRepository = appConfigRepository;
	}
	
	@Override
	public List<AppConfig> retreiveAllConfigs() {
		List<AppConfig> configs = this.appConfigRepository.findAll();
		if (configs != null && !configs.isEmpty())
			logger.info("The application configurations has been retrieved successfully");
		else
			logger.error("The application configurations were not found!!");
		return configs;
	}

	@Override
	public AppConfig getAppConfig(Integer confId) {
		AppConfig config = this.appConfigRepository.findOne(confId);
		if (config != null)
			logger.info("The application configuration has been retrieved successfully");
		else
			logger.error("The application configuration was not found!!");
		return config;
	}

	private List<AppConfig> getAppConfigByCriteria(AppConfig configCriteria) {
		List<AppConfig> configs = this.appConfigRepository.findAll(Example.of(configCriteria));
		if (configs != null && !configs.isEmpty())
			logger.info("The application configurations has been retrieved successfully");
		else
			logger.error("The application configurations were not found!!");
		return configs;
	}

	@Override
	public List<AppConfig> getAppConfigByName(String name) {
		AppConfig configCriteria = new AppConfig();
		configCriteria.setName(name);
		return this.getAppConfigByCriteria(configCriteria);
	}

	@Override
	public String getAppConfigByKey(String confKey) {
		String result = null;
		AppConfig configCriteria = new AppConfig();
		configCriteria.setConfKey(confKey);
		List<AppConfig> appConfigs = this.getAppConfigByCriteria(configCriteria);

		if (appConfigs != null && !appConfigs.isEmpty()) {
			AppConfig appConfig = appConfigs.get(0);
			result = appConfig.getConfVal();
		}
		logger.info("The application configurations has been retrieved successfully using key");
		return result;
	}

	private AppConfig getAppConfigDtlByKey(String confKey) {
		AppConfig appConfig = null;
		AppConfig configCriteria = new AppConfig();
		configCriteria.setConfKey(confKey);
		List<AppConfig> appConfigs = this.getAppConfigByCriteria(configCriteria);

		if (appConfigs != null && !appConfigs.isEmpty()) {
			appConfig = appConfigs.get(0);
		}
		logger.info("The application configurations has been retrieved successfully using key");
		return appConfig;
	}
	
	@Override
	public Boolean updateAppConfigByKey(String confKey, String confValue) {
		Boolean result=Boolean.FALSE;
		AppConfig appConfig=this.getAppConfigDtlByKey(confKey);
		if(appConfig!=null) {
			appConfig.setConfVal(confValue);
			appConfig = this.appConfigRepository.save(appConfig);
			logger.info("The application configurations {} has been updated successfully with provided value", confKey);
			result=Boolean.TRUE;
		}
				
		return result;
	}

}
