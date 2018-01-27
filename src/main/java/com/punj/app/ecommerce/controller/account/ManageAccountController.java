package com.punj.app.ecommerce.controller.account;
/**
 * 
 */

import java.security.Principal;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.punj.app.ecommerce.domains.user.Password;
import com.punj.app.ecommerce.domains.user.User;
import com.punj.app.ecommerce.models.LoginBean;
import com.punj.app.ecommerce.models.RegisterUserBean;
import com.punj.app.ecommerce.services.UserService;
import com.punj.app.ecommerce.utils.Utils;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class ManageAccountController {
	private static final Logger logger = LogManager.getLogger();
	private UserService userService;
	private MessageSource messageSource;

	/**
	 * @param messageSource
	 *            the messageSource to set
	 */
	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	/**
	 * @param userService
	 *            the userService to set
	 */
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/manage_user")
	public String showUser(Model model, HttpSession session, Principal principal) {
		String loggedInUser = principal.getName();
		RegisterUserBean userBean = null;
		User user = userService.getUserByUsername(loggedInUser);
		userBean = this.updateUserBean(user);
		model.addAttribute("registerUserBean", userBean);

		return "account/manage_user";

	}

	@PostMapping("/manage_user")
	public String saveUser(@ModelAttribute RegisterUserBean registerUserBean, Model model, HttpSession session,
			Locale locale, Authentication authentication) {
		logger.info("Captured the updated details for the user");

		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		User newUser = userService.getUserByUsername(registerUserBean.getEmail());
		newUser = this.updateUserDomain(registerUserBean, newUser);

		newUser = userService.saveUser(newUser);
		model.addAttribute("success", messageSource.getMessage("commerce.screen.manage.account.success", null, locale));

		logger.info("The user details has been successfully updated");

		model.addAttribute("registerUserBean", registerUserBean);

		return "account/manage_user";

	}

	@GetMapping("/manage_password")
	public String showPassword(Model model) {
		model.addAttribute("passwordBean", new LoginBean());
		return "account/manage_password";
	}

	@PostMapping("/manage_password")
	public String savePassword(@ModelAttribute LoginBean passwordBean, Model model, HttpSession session, Locale locale,
			Authentication authentication) {
		logger.info("Captured the updated password for the user");
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		try {
			if (StringUtils.isNotEmpty(passwordBean.getNewPassword())
					&& StringUtils.isNotEmpty(passwordBean.getConfirmPassword())
					&& passwordBean.getConfirmPassword().equals(passwordBean.getNewPassword())) {
				Password pwd = new Password();
				pwd = userService.updatePassword(userDetails, pwd, passwordBean.getNewPassword(), null);
				session.setAttribute("userDetails", pwd);
				model.addAttribute("passwordBean", passwordBean);
				model.addAttribute("success",
						messageSource.getMessage("commerce.screen.manage.password.success", null, locale));
			} else {
				model.addAttribute("passwordBean", passwordBean);
				model.addAttribute("alert",
						messageSource.getMessage("commerce.screen.manage.password.matching.failed", null, locale));
			}
		} catch (Exception e) {
			model.addAttribute("alert", "Unexpected error occured.");
			model.addAttribute("passwordBean", passwordBean);
			logger.error("The error has occured while updating the new password", e);
			return "error";
		}
		return "account/manage_password";
	}

	/**
	 * This method is used to update the bean object from entity domain object
	 * 
	 * @param loggedInUser
	 * @return
	 */
	private RegisterUserBean updateUserBean(User loggedInUser) {
		RegisterUserBean newUserBean = new RegisterUserBean();
		newUserBean.setEmail(loggedInUser.getEmail());
		newUserBean.setFirstName(loggedInUser.getFirstname());
		newUserBean.setLastName(loggedInUser.getLastname());
		newUserBean.setPhone1(loggedInUser.getPhone1().toString());
		newUserBean.setPhone2(loggedInUser.getPhone2().toString());

		return newUserBean;

	}

	/**
	 * This method is to update the domain object from the form bean object
	 * 
	 * @param registerUserBean
	 * @param newUser
	 * @return
	 */
	private User updateUserDomain(RegisterUserBean registerUserBean, User newUser) {

		newUser.setUsername(registerUserBean.getEmail());
		newUser.setEmail(registerUserBean.getEmail());
		newUser.setFirstname(registerUserBean.getFirstName());
		newUser.setLastname(registerUserBean.getLastName());
		newUser.setPhone1(registerUserBean.getPhone1());
		newUser.setPhone2(registerUserBean.getPhone2());

		return newUser;

	}

}
