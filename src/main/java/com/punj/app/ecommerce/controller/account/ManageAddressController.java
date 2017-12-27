package com.punj.app.ecommerce.controller.account;
/**
 * 
 */

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

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

import com.punj.app.ecommerce.domains.user.Address;
import com.punj.app.ecommerce.domains.user.Password;
import com.punj.app.ecommerce.domains.user.User;
import com.punj.app.ecommerce.models.RegisterUserBean;
import com.punj.app.ecommerce.services.UserService;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class ManageAddressController {
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

	@GetMapping("/manage_address")
	public String showAddress(Model model, HttpSession session,Authentication authentication) {

		RegisterUserBean addressBean = new RegisterUserBean();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		
		List<Address> addresses = userService.getAddressByUsername(userDetails.getUsername());

		logger.info("The address list of {} addresses for the current user has been retrieved.", addresses.size());

		model.addAttribute("addresses", addresses);
		model.addAttribute("addressBean", addressBean);
		return "account/address_list";
	}

	@GetMapping(value = "/add_address")
	public String addAddress(Model model, HttpSession session) {
		logger.info("The add method for new address has been called");
		try {
			RegisterUserBean addressBean = new RegisterUserBean();
			model.addAttribute("addressBean", addressBean);
			logger.info("The empty address bean has been created");
		} catch (Exception e) {
			logger.error("An unknown error has occurred while creating empty address.", e);
			return "error";
		}

		return "account/add_address";

	}

	@PostMapping("/add_address")
	public String saveAddress(@ModelAttribute RegisterUserBean addressBean, Model model, HttpSession session,
			Locale locale, Authentication authentication) {

		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			User user = userService.getUserByUsername(userDetails.getUsername());

			Address address = new Address();
			address.setPrimary("N");
			address = this.updateAddressInDomain(address, addressBean);

			List<Address> addresses = user.getAddresses();
			addresses.add(address);
			user.setAddresses(addresses);
			userService.saveUser(user);

			model.addAttribute("addressBean", addressBean);
			model.addAttribute("success",
					messageSource.getMessage("commerce.screen.add.address.success", null, locale));
		} catch (Exception e) {
			logger.error("There is an error while updating address", e);
			return "error";
		}
		return "account/add_address";
	}

	@GetMapping(value = "/manage_address/edit")
	public String editAddressAction(@ModelAttribute RegisterUserBean addressBean, Model model, HttpSession session) {
		logger.info("The edit method for address has been called");
		try {
			Address primaryAddress = userService.getAddress(addressBean.getAddressId());
			addressBean = this.updateAddressInBean(primaryAddress, addressBean);
			logger.info("The primary address details has been updated in addressBean");
		} catch (Exception e) {
			logger.error("An unknown error has occurred while saving address.", e);
			return "error";
		}
		model.addAttribute("addressBean", addressBean);
		return "account/edit_address";

	}

	@GetMapping(value = "/manage_address/delete")
	public String deleteAddressAction(@ModelAttribute RegisterUserBean addressBean, Model model, HttpSession session,
			Locale locale, Authentication authentication) {
		logger.info("The logic for delete method goes here");
		UserDetails userDetails = null;
		try {
			userDetails = (UserDetails) authentication.getPrincipal();
			User user = userService.getUserByUsername(userDetails.getUsername());

			user = this.deleteAddressInDomain(user, addressBean);

			userService.saveUser(user);

			model.addAttribute("addressBean", addressBean);
			model.addAttribute("success",
					messageSource.getMessage("commerce.screen.manage.address.deleted", null, locale));

			logger.info("The address has been deleted now");

		} catch (Exception e) {
			logger.error("An unknown error has occurred while deleting address.", e);
			model.addAttribute("alert",
					messageSource.getMessage("commerce.screen.manage.address.deletion.failed", null, locale));
		}
		List<Address> addresses = userService.getAddressByUsername(userDetails.getUsername());
		logger.info("The address list of {} addresses for the current user has been retrieved.", addresses.size());

		model.addAttribute("addresses", addresses);
		model.addAttribute("addressBean", addressBean);

		return "account/address_list";
	}

	@PostMapping("/manage_address")
	public String updateAddress(@ModelAttribute RegisterUserBean addressBean, Model model, HttpSession session,
			Locale locale) {
		try {

			Address address = userService.getAddress(addressBean.getAddressId());
			address = this.updateAddressInDomain(address, addressBean);
			userService.updateAddress(address);
			model.addAttribute("addressBean", addressBean);
			model.addAttribute("success",
					messageSource.getMessage("commerce.screen.edit.address.updated", null, locale));
		} catch (Exception e) {
			logger.error("There is an error while updating address", e);
			return "error";
		}
		return "account/edit_address";
	}

	private RegisterUserBean updateAddressInBean(Address primaryAddress, RegisterUserBean addressBean) {
		addressBean.setAddressId(primaryAddress.getAddressId());
		addressBean.setAddress1(primaryAddress.getAddress1());
		addressBean.setAddress2(primaryAddress.getAddress2());
		addressBean.setAddressType(primaryAddress.getAddressType());
		addressBean.setCity(primaryAddress.getCity());
		addressBean.setState(primaryAddress.getState());
		addressBean.setCountry(primaryAddress.getCountry());
		addressBean.setPincode(primaryAddress.getPincode());

		return addressBean;

	}

	private Address updateAddressInDomain(Address address, RegisterUserBean addressBean) {
		address.setAddressId(addressBean.getAddressId());
		address.setAddress1(addressBean.getAddress1());
		address.setAddress2(addressBean.getAddress2());
		address.setAddressType(addressBean.getAddressType());
		address.setCity(addressBean.getCity());
		address.setState(addressBean.getState());
		address.setCountry(addressBean.getCountry());
		address.setPincode(addressBean.getPincode());

		return address;
	}

	private User deleteAddressInDomain(User user, RegisterUserBean addressBean) {
		List<Address> addresses = user.getAddresses();
		Address removeAddress = null;
		for (Address itrAddress : addresses) {
			if (itrAddress.getAddressId().equals(addressBean.getAddressId())) {
				removeAddress = itrAddress;
				break;
			}

		}
		addresses.remove(removeAddress);
		user.setAddresses(addresses);
		return user;
	}

}
