/**
 * 
 */
package com.punj.app.ecommerce.controller.common.transformer;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.punj.app.ecommerce.domains.common.Location;
import com.punj.app.ecommerce.domains.user.Password;
import com.punj.app.ecommerce.models.account.AccountBean;
import com.punj.app.ecommerce.models.common.LocationBean;
import com.punj.app.ecommerce.models.setup.InitialSetupBean;

/**
 * @author admin
 *
 */
public class SetupTransformer {

	private static final Logger logger = LogManager.getLogger();

	private SetupTransformer() {
		throw new IllegalStateException("SetupTransformer class");
	}

	public static Location transformLocationDetails(InitialSetupBean initialSetupBean) {
		LocationBean locBean = initialSetupBean.getLocation();
		Location location = CommonMVCTransformer.transformLocationBean(locBean);
		logger.info("The location details has been transformed successfully");
		return location;
	}

	public static Password transformPasswordDetails(InitialSetupBean initialSetupBean) {
		Password password=null;
		AccountBean accountBean = initialSetupBean.getAccount();
		List<Password> passwords = AccountTransformer.transformAccountPasswordWithoutEncoding(accountBean);
		if(passwords!=null && !passwords.isEmpty())
			password=passwords.get(0);
		logger.info("The account password details has been transformed successfully");
		return password;
	}
	
	

}
