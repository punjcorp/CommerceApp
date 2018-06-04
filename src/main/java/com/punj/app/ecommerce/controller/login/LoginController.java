package com.punj.app.ecommerce.controller.login;
/**
 * 
 */

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.domains.user.Address;
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
public class LoginController {
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

	@GetMapping(ViewPathConstants.LOGIN_URL)
	public String login(Model model) {
		model.addAttribute("loginBean", new LoginBean());
		return ViewPathConstants.LOGIN_PAGE;

	}

	@PostMapping(ViewPathConstants.LOGIN_URL)
	public String validateLogin(@ModelAttribute LoginBean loginBean, Model model, HttpSession session, Locale locale) {
		logger.info("Entered details User Name {} and Password {} ", loginBean.getUsername(), loginBean.getPassword());
		List<Password> passwords = userService.getAllPasswordsByUsername(loginBean.getUsername());
		logger.info("Retrieved Passsword count is {} ", passwords != null ? passwords.size() : 0);
		if (passwords != null && !passwords.isEmpty()) {

			for (Password pwd : passwords) {
				logger.info("The user login username is {} and password is {} with status {}", pwd.getUsername(), pwd.getPassword(), pwd.getStatus());

				if (loginBean.getPassword().equals(pwd.getPassword()) && pwd.getStatus().equals("A")) {
					session.setAttribute("userDetails", pwd);
					User loggedInUser = userService.getUserByUsername(pwd.getUsername());
					RegisterUserBean userBean = this.updateUserBean(loggedInUser);
					model.addAttribute("registerUserBean", userBean);

					return ViewPathConstants.MANAGE_USER_PAGE;
				}
			}
			model.addAttribute("alert", messageSource.getMessage("commerce.screen.login.invalid.password", null, locale));
			return ViewPathConstants.LOGIN_PAGE;
		}
		model.addAttribute("alert", messageSource.getMessage("commerce.screen.login.invalid.username", null, locale));
		return ViewPathConstants.LOGIN_PAGE;
	}

	@RequestMapping(ViewPathConstants.REGISTER_USER_URL)
	public String registerUser(Model model) {
		model.addAttribute("registerUserBean", new RegisterUserBean());
		return ViewPathConstants.REGISTER_USER_PAGE;

	}

	@PostMapping(ViewPathConstants.REGISTER_USER_URL)
	public String saveUserDetails(@ModelAttribute RegisterUserBean registerUserBean, Model model, Locale locale) {
		logger.info("Captured the details for registering the user");
		User newUser = this.updateUserDomain(registerUserBean);

		userService.saveUser(newUser);
		logger.info("The user details has been successfully registered");

		model.addAttribute("loginBean", new LoginBean());
		model.addAttribute(MVCConstants.SUCCESS, messageSource.getMessage("commerce.screen.register.success", null, locale));
		return ViewPathConstants.LOGIN_PAGE;
	}

	private User updateUserDomain(RegisterUserBean registerUserBean) {
		User newUser = new User();
		newUser.setUsername(registerUserBean.getEmail());
		newUser.setEmail(registerUserBean.getEmail());
		newUser.setFirstname(registerUserBean.getFirstName());
		newUser.setLastname(registerUserBean.getLastName());
		newUser.setPhone1(registerUserBean.getPhone1());
		newUser.setPhone2(registerUserBean.getPhone2());
		logger.info("The value for phone1 is {} and phone2 is {} ", registerUserBean.getPhone1(), registerUserBean.getPhone2());
		newUser.setStatus("A");
		newUser.setCreatedBy("admin");
		newUser.setCreatedDate(LocalDateTime.now());
		logger.info("The current time is -> " + LocalDate.now());

		Password newPassword = new Password();
		List passwords = new ArrayList<>();
		newPassword.setModifiedBy("admin");
		newPassword.setStatus("A");

		newPassword.setPassword(Utils.encodePassword(registerUserBean.getPassword()));
		newPassword.setUsername(registerUserBean.getEmail());
		newPassword.setModifiedDate(LocalDateTime.now());

		passwords.add(newPassword);
		newUser.setPasswords(passwords);

		Address address = new Address();
		address.setAddress1(registerUserBean.getAddress1());
		address.setAddress2(registerUserBean.getAddress2());
		address.setCity(registerUserBean.getCity());
		address.setState(registerUserBean.getState());
		address.setCountry(registerUserBean.getCountry());
		address.setPincode(registerUserBean.getPincode());
		address.setAddressType(registerUserBean.getAddressType());
		address.setPrimary("Y");
		List<Address> addresses = new ArrayList<>();
		addresses.add(address);

		newUser.setAddresses(addresses);

		return newUser;

	}

	private RegisterUserBean updateUserBean(User loggedInUser) {
		RegisterUserBean newUserBean = new RegisterUserBean();
		newUserBean.setEmail(loggedInUser.getEmail());
		newUserBean.setFirstName(loggedInUser.getFirstname());
		newUserBean.setLastName(loggedInUser.getLastname());
		newUserBean.setPhone1(loggedInUser.getPhone1());
		newUserBean.setPhone2(loggedInUser.getPhone2());

		return newUserBean;

	}

}
