package com.punj.app.ecommerce.controller;
/**
 * 
 */

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
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

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class ManageAccountController {
	private static final Logger logger = LogManager.getLogger();
	private UserService userService;

	/**
	 * @param userService
	 *            the userService to set
	 */
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/manage_user")
	public String showUser(Model model, HttpSession session) {
		User loggedInUser = null;
		RegisterUserBean userBean = null;
		Password userDetails = (Password) session.getAttribute("userDetails");
		if (userDetails != null) {
			loggedInUser = userService.getUserByUsername(userDetails.getPasswordId().getUsername());
			userBean = this.updateUserBean(loggedInUser);
			model.addAttribute("registerUserBean", userBean);
		}

		return "manage_user";

	}

	@PostMapping("/manage_user")
	public String saveUser(@ModelAttribute RegisterUserBean registerUserBean, Model model, HttpSession session) {
		logger.info("Captured the updated details for the user");
		Password pwd = (Password) session.getAttribute("userDetails");
		if (pwd != null && registerUserBean.getPassword() != null
				&& registerUserBean.getPassword().equals(pwd.getPasswordId().getPassword())) {
			User newUser = userService.getUserByUsername(registerUserBean.getEmail());
			newUser = this.updateUserDomain(registerUserBean, newUser);

			newUser = userService.saveUser(newUser);
			model.addAttribute("success", "The details has been updated successfully.");

			logger.info("The user details has been successfully updated");

		} else {
			model.addAttribute("alert", "Please enter the current password for validation!!");
		}
		model.addAttribute("registerUserBean", registerUserBean);

		return "manage_user";

	}

	@GetMapping("/manage_password")
	public String showPassword(Model model) {
		model.addAttribute("passwordBean", new LoginBean());
		return "manage_password";
	}

	@PostMapping("/manage_password")
	public String savePassword(@ModelAttribute LoginBean passwordBean, Model model, HttpSession session) {
		logger.info("Captured the updated password for the user");
		try {
			Password pwd = (Password) session.getAttribute("userDetails");
			if (pwd == null) {
				logger.error("The user object in session not found");
				return "error";
			}
			if (passwordBean.getPassword() != null
					&& passwordBean.getPassword().equals(pwd.getPasswordId().getPassword())) {
				pwd = userService.updatePassword(pwd, passwordBean.getNewPassword(), null);
				session.setAttribute("userDetails", pwd);
				model.addAttribute("passwordBean", passwordBean);
				model.addAttribute("success", "The password has been changed.");
			} else {
				model.addAttribute("alert", "The old password is incorrect, please try again.");
				model.addAttribute("passwordBean", passwordBean);
			}
		} catch (Exception e) {
			model.addAttribute("alert", "Unexpected error occured.");
			model.addAttribute("passwordBean", passwordBean);
			logger.error("The error has occured while updating the new password", e);
			return "error";
		}
		return "manage_password";
	}

	private RegisterUserBean updateUserBean(User loggedInUser) {
		RegisterUserBean newUserBean = new RegisterUserBean();
		newUserBean.setEmail(loggedInUser.getEmail());
		newUserBean.setFirstName(loggedInUser.getFirstname());
		newUserBean.setLastName(loggedInUser.getLastname());
		newUserBean.setPhone1(loggedInUser.getPhone1().toString());
		newUserBean.setPhone2(loggedInUser.getPhone2().toString());

		return newUserBean;

	}

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
