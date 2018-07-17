package com.punj.app.ecommerce.controller.account;
/**
 * 
 */

import java.time.LocalDateTime;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.domains.user.Password;
import com.punj.app.ecommerce.domains.user.User;
import com.punj.app.ecommerce.models.LoginBean;
import com.punj.app.ecommerce.models.RegisterUserBean;
import com.punj.app.ecommerce.services.UserService;

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

	@GetMapping(ViewPathConstants.MANAGE_USER_URL)
	public String showUser(Model model, HttpSession session, Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String loggedInUser = userDetails.getUsername();
		
		RegisterUserBean userBean = null;
		User user = this.userService.getUserByUsername(loggedInUser);
		if(user !=null)
			userBean = this.updateUserBean(user);
		model.addAttribute(MVCConstants.USER_BEAN_PARAM, userBean);

		return ViewPathConstants.MANAGE_USER_PAGE;

	}

	@PostMapping(ViewPathConstants.MANAGE_USER_URL)
	public String saveUser(@ModelAttribute RegisterUserBean registerUserBean, Model model, HttpSession session, Locale locale, Authentication authentication) {
		logger.info("Captured the updated details for the user");

		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		User newUser = userService.getUserByUsername(registerUserBean.getUsername());
		if (newUser != null) {
			this.updateUserDomain(registerUserBean, newUser, userDetails.getUsername());
			newUser = this.userService.updateUserDetails(newUser, userDetails.getUsername());
			if (newUser != null) {
				model.addAttribute(MVCConstants.SUCCESS, messageSource.getMessage("commerce.screen.manage.account.success", null, locale));
				logger.info("The user details has been successfully updated");
			} else {
				model.addAttribute(MVCConstants.ALERT, messageSource.getMessage("commerce.screen.manage.account.failure", null, locale));
				logger.error("The user details were not saved due to some issues");
			}
		}

		model.addAttribute(MVCConstants.USER_BEAN_PARAM, registerUserBean);
		return ViewPathConstants.MANAGE_USER_PAGE;

	}

	@GetMapping(ViewPathConstants.CHANGE_PASSWORD_URL)
	public String showPassword(Model model) {
		model.addAttribute(MVCConstants.PASSWORD_BEAN_PARAM, new LoginBean());
		return ViewPathConstants.CHANGE_PASSWORD_PAGE;
	}

	@PostMapping(ViewPathConstants.CHANGE_PASSWORD_URL)
	public String savePassword(@ModelAttribute LoginBean passwordBean, Model model, HttpSession session, Locale locale, Authentication authentication) {
		logger.info("Captured the updated password for the user");
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		try {
			if (StringUtils.isNotEmpty(passwordBean.getNewPassword()) && StringUtils.isNotEmpty(passwordBean.getConfirmPassword())
					&& passwordBean.getConfirmPassword().equals(passwordBean.getNewPassword())) {
				Password pwd = new Password();
				pwd = userService.updatePassword(userDetails, pwd, passwordBean.getNewPassword(), null);
				session.setAttribute(MVCConstants.USER_PASSWORD_BEAN_PARAM, pwd);
				model.addAttribute(MVCConstants.SUCCESS, messageSource.getMessage("commerce.screen.manage.password.success", null, locale));
			} else {
				model.addAttribute(MVCConstants.ALERT, messageSource.getMessage("commerce.screen.manage.password.matching.failed", null, locale));
			}
			model.addAttribute(MVCConstants.PASSWORD_BEAN_PARAM, passwordBean);
		} catch (Exception e) {
			model.addAttribute(MVCConstants.ALERT, messageSource.getMessage("commerce.screen.manage.password.failure", null, locale));
			model.addAttribute(MVCConstants.PASSWORD_BEAN_PARAM, passwordBean);
			logger.error("The error has occured while updating the new password", e);
		}
		return ViewPathConstants.CHANGE_PASSWORD_PAGE;
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
		newUserBean.setUsername(loggedInUser.getUsername());
		newUserBean.setFirstName(loggedInUser.getFirstname());
		newUserBean.setLastName(loggedInUser.getLastname());
		newUserBean.setPhone1(loggedInUser.getPhone1());
		newUserBean.setPhone2(loggedInUser.getPhone2());

		return newUserBean;

	}

	/**
	 * This method is to update the domain object from the form bean object
	 * 
	 * @param registerUserBean
	 * @param newUser
	 * @return
	 */
	private User updateUserDomain(RegisterUserBean registerUserBean, User newUser, String modifiedBy) {

		newUser.setUsername(registerUserBean.getUsername());
		newUser.setEmail(registerUserBean.getEmail());
		newUser.setFirstname(registerUserBean.getFirstName());
		newUser.setLastname(registerUserBean.getLastName());
		newUser.setPhone1(registerUserBean.getPhone1());
		newUser.setPhone2(registerUserBean.getPhone2());
		
		newUser.setModifiedBy(modifiedBy);
		newUser.setModifiedDate(LocalDateTime.now());
		
		return newUser;

	}

}
