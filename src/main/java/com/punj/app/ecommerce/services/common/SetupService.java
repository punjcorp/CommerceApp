/**
 * 
 */
package com.punj.app.ecommerce.services.common;

import com.punj.app.ecommerce.domains.common.Location;
import com.punj.app.ecommerce.domains.user.Password;

/**
 * @author admin
 *
 */
public interface SetupService {

	public Boolean isSetupGood();
	
	public Boolean saveInitialSetup(Location location, String[] selectedTenders, Password password);

}
