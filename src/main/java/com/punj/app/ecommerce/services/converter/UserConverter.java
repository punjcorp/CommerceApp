/**
 * 
 */
package com.punj.app.ecommerce.services.converter;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.punj.app.ecommerce.domains.user.Password;
import com.punj.app.ecommerce.domains.user.User;
import com.punj.app.ecommerce.domains.user.UserRole;
import com.punj.app.ecommerce.services.common.ServiceConstants;
import com.punj.app.ecommerce.services.dtos.UserPrincipal;

/**
 * @author admin
 *
 */
public class UserConverter {

	private static final Logger logger = LogManager.getLogger();

	private UserConverter() {
		throw new IllegalStateException("UserConverter class");
	}

	public static UserPrincipal convertUser(User user) {

		List<Password> passwords = user.getPasswords();
		String password = null;
		if (passwords != null && !passwords.isEmpty()) {
			for (Password passwd : passwords) {
				if (passwd.getStatus().equals(ServiceConstants.STATUS_APPROVED)) {
					password = passwd.getPassword();
					break;
				}
			}
		}

		List<UserRole> userRoles = user.getUserRoles();
		UserPrincipal userDTO = null;
		if (StringUtils.isNotBlank(password) && userRoles != null && !userRoles.isEmpty()) {
			userDTO = new UserPrincipal(user.getUsername(), password, userRoles);

			userDTO.setDefaultLocationId(user.getDefaultLocationId());
			userDTO.setEmail(user.getEmail());
			userDTO.setFirstname(user.getFirstname());
			userDTO.setLastname(user.getLastname());
			userDTO.setLoginCount(user.getLoginCount());
			userDTO.setPhone1(user.getPhone1());
			userDTO.setPhone2(user.getPhone2());
			userDTO.setPhoto(user.getPhoto());
			userDTO.setStatus(user.getStatus());

			logger.info("The user details has been converted successfully in form which is understandable by Spring");
		} else {
			logger.info("The complete user details were not found, hence cannot be converted");
		}

		return userDTO;
	}

}
