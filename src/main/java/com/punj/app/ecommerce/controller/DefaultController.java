package com.punj.app.ecommerce.controller;
/**
 * 
 */

import java.security.Principal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
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

}
