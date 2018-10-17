/**
 * 
 */
package com.punj.app.ecommerce.services.common;

import java.util.List;

import com.punj.app.ecommerce.domains.common.AppConfig;

/**
 * @author admin
 *
 */
public interface ConfigService {

	public List<AppConfig> retreiveAllConfigs();

	public AppConfig getAppConfig(Integer confId);

	public List<AppConfig> getAppConfigByName(String name);

	public String getAppConfigByKey(String confKey);
	
	public Boolean updateAppConfigByKey(String confKey, String confValue);

}
