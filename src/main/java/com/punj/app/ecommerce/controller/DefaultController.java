package com.punj.app.ecommerce.controller;
/**
 * 
 */

import java.security.Principal;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class DefaultController {
	private static final Logger logger = LogManager.getLogger();
	private MessageSource messageSource;

	/**
	 * @param messageSource
	 *            the messageSource to set
	 */
	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@GetMapping("/home")
	public String login(Model model, Principal principal) {
		logger.info("========================");
		logger.info("WELCOME TO THE HOME PAGE");
		logger.info("========================");
		return "home";

	}

	@GetMapping("/403")
	public String accessError(Model model) {
		logger.info("========================");
		logger.info("WELCOME TO THE HOME PAGE");
		logger.info("========================");
		return "access_denied";

	}

	@GetMapping("/logout")
	public String accessError(Model model, HttpServletRequest request, HttpServletResponse response,
			Authentication auth, Locale locale) {

		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		model.addAttribute("success", messageSource.getMessage("commerce.screen.logout.success", null, locale));
		return "redirect:/login?logout";
	}

}
