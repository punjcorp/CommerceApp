/**
 * 
 */
package com.punj.app.ecommerce.common.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

/**
 * @author amit
 *
 */
@ApplicationScope
@Component
public class CommerceContext {

	private Map<String, Object> storeSettings = new HashMap<>();

	/**
	 * @return the storeSettings
	 */
	public Map<String, Object> getStoreSettings() {
		return storeSettings;
	}

	/**
	 * @return the storeSetting based on setting name
	 */
	public Object getStoreSettings(String settingName) {
		return storeSettings.get(settingName);
	}

	
	public void setStoreSettings(String settingName, Object settingDetails) {
		storeSettings.put(settingName, settingDetails);
	}	
	
}
