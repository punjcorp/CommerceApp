/**
 * 
 */
package com.punj.app.ecommerce.common.web;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

/**
 * @author admin
 *
 */
public class RefererRedirectionAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	public RefererRedirectionAuthenticationSuccessHandler() {
		super();
		setUseReferer(true);
	}

}