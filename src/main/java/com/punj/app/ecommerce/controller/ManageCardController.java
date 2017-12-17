package com.punj.app.ecommerce.controller;
/**
 * 
 */

import java.util.List;

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

import com.punj.app.ecommerce.domains.user.Address;
import com.punj.app.ecommerce.domains.user.Card;
import com.punj.app.ecommerce.domains.user.Password;
import com.punj.app.ecommerce.domains.user.ids.CardId;
import com.punj.app.ecommerce.models.CardBean;
import com.punj.app.ecommerce.models.RegisterUserBean;
import com.punj.app.ecommerce.services.UserService;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class ManageCardController {
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

	@GetMapping("/manage_card")
	public String showCard(Model model, HttpSession session) {

		Password pwd = (Password) session.getAttribute("userDetails");
		if (pwd == null) {
			logger.error("The user object in session not found");
			return "error";
		}

		List<Card> cards = userService.getCardsByUsername(pwd.getPasswordId().getUsername());

		logger.info("The address list of {} cards for the current user has been retrieved.", cards.size());

		model.addAttribute("cards", cards);
		model.addAttribute("cardBean", new CardBean());

		return "card_list";
	}

	@GetMapping("/add_card")
	public String addCard(Model model, HttpSession session) {
		Password pwd = (Password) session.getAttribute("userDetails");
		if (pwd == null) {
			logger.error("The user object in session not found");
			return "error";
		}
		CardBean cardBean = new CardBean();

		List<Address> addresses = userService.getAddressByUsername(pwd.getPasswordId().getUsername());

		Address address = null;
		for (Address addObject : addresses) {
			if (addObject.getPrimary().equals("Y")) {
				address = addObject;
				break;
			}
		}

		cardBean = this.updateAddressDetails(cardBean, address);
		
		logger.info("The address details has been updated in cardBean");
		
		model.addAttribute("primaryAddress", address);
		model.addAttribute("addresses", addresses);
		model.addAttribute("cardBean", cardBean);
		return "add_card";
	}

	@PostMapping("/add_card")
	public String saveCard(@ModelAttribute CardBean cardBean, Model model, HttpSession session) {
		Password pwd = (Password) session.getAttribute("userDetails");
		if (pwd == null) {
			logger.error("The user object in session not found");
			return "error";
		}
		try {
			Card card = this.updateCardInDomain(cardBean, pwd.getPasswordId().getUsername());
			userService.saveCard(card);
			model.addAttribute("success", "The new card has been added.");

			logger.info("The card details has been saved.");

			List<Address> addresses = userService.getAddressByUsername(pwd.getPasswordId().getUsername());
			logger.info("All the addresses has been retrieved.");

			Address address = null;
			for (Address addObject : addresses) {
				if (addObject.getAddressId().equals(cardBean.getAddress().getAddressId())) {
					address = addObject;
					break;
				}
			}

			model.addAttribute("primaryAddress", address);
			model.addAttribute("addresses", addresses);

		} catch (Exception e) {
			logger.error("There is an error while adding card", e);
			return "error";

		}
		model.addAttribute("cardBean", cardBean);

		return "add_card";
	}

	@GetMapping(value = "/manage_card/edit")
	public String editCardAction(@ModelAttribute CardBean cardBean, Model model, HttpSession session) {
		logger.info("The edit method for card has been called");
		try {
			Password pwd = (Password) session.getAttribute("userDetails");
			if (pwd == null) {
				logger.error("The user object in session not found");
				return "error";
			}

			Card card = userService.getCard(pwd.getPasswordId().getUsername(), cardBean.getCardNo());
			logger.info("The selected card details has been updated in cardBean");

			List<Address> addresses = userService.getAddressByUsername(pwd.getPasswordId().getUsername());
			logger.info("All the addresses has been retrieved.");

			Address address = null;
			for (Address addObject : addresses) {
				if (addObject.getAddressId().equals(card.getAddress().getAddressId())) {
					address = addObject;
					break;
				}
			}
			
			cardBean=this.updateCardInBean(cardBean, card, address);

			model.addAttribute("primaryAddress", address);
			model.addAttribute("addresses", addresses);

		} catch (Exception e) {
			logger.error("An unknown error has occurred while saving address.", e);
			return "error";
		}

		model.addAttribute("cardBean", cardBean);
		return "manage_card";

	}

	@PostMapping("/manage_card")
	public String updateCard(@ModelAttribute CardBean cardBean, Model model, HttpSession session) {
		Password pwd = (Password) session.getAttribute("userDetails");
		if (pwd == null) {
			logger.error("The user object in session not found");
			return "error";
		}
		try {
			Card card = this.updateCardInDomain(cardBean, pwd.getPasswordId().getUsername());
			userService.saveCard(card);
			logger.info("The card details has been updated now");
			model.addAttribute("success", "The new card has been updated.");

			List<Address> addresses = userService.getAddressByUsername(pwd.getPasswordId().getUsername());
			logger.info("All the addresses has been retrieved.");

			Address address = null;
			for (Address addObject : addresses) {
				if (addObject.getAddressId().equals(cardBean.getAddress().getAddressId())) {
					address = addObject;
					break;
				}
			}

			model.addAttribute("primaryAddress", address);
			model.addAttribute("addresses", addresses);
			
			
			
		} catch (Exception e) {
			logger.error("There is an error while updating card details", e);
			return "error";
		}
		
		model.addAttribute("cardBean", cardBean);
		return "manage_card";
	}

	@GetMapping(value = "/manage_card/delete")
	public String deleteAddressAction(@ModelAttribute CardBean cardBean, Model model, HttpSession session) {
		logger.info("The logic for delete card method goes here");
		Password pwd = null;
		try {
			pwd = (Password) session.getAttribute("userDetails");
			if (pwd == null) {
				logger.error("The user object in session not found");
				return "error";
			}

			userService.deleteCard(pwd.getPasswordId().getUsername(), cardBean.getCardNo());
			model.addAttribute("success", "The selected card has been deleted.");
			logger.info("The address has been deleted now");

			List<Card> cards = userService.getCardsByUsername(pwd.getPasswordId().getUsername());
			logger.info("The address list of {} cards for the current user has been retrieved.", cards.size());
			model.addAttribute("cards", cards);
			model.addAttribute("cardBean", cardBean);

		} catch (Exception e) {
			logger.error("An unknown error has occurred while deleting address.", e);
			model.addAttribute("alert", "The selected card deletion failed due to some error.");
		}

		return "card_list";
	}

	private Card updateCardInDomain(CardBean cardBean, String username) {
		Card card = new Card();
		CardId cardId = new CardId();
		cardId.setCardNo(cardBean.getCardNo());
		cardId.setUsername(username);
		card.setCardId(cardId);

		Address address = userService.getAddress(cardBean.getAddress().getAddressId());
		card.setAddress(address);

		card.setExpiryDate(cardBean.getExpiryDate());
		card.setNameOnCard(cardBean.getName());
		card.setCvvNo(cardBean.getCvvNo());
		return card;
	}

	
	private CardBean updateCardInBean(CardBean cardBean, Card card, Address address) {

		cardBean=this.updateAddressDetails(cardBean, address);				
		
		cardBean.setCardNo(card.getCardId().getCardNo());
		cardBean.setCvvNo(card.getCvvNo());
		cardBean.setExpiryDate(card.getExpiryDate());
		cardBean.setName(card.getNameOnCard());

		return cardBean;

	}
	
	
	private CardBean updateAddressDetails(CardBean cardBean, Address address) {

		RegisterUserBean addressBean = new RegisterUserBean();
		addressBean.setAddressId(address.getAddressId());
		addressBean.setAddress1(address.getAddress1());
		addressBean.setAddress2(address.getAddress2());
		addressBean.setCity(address.getCity());
		addressBean.setState(address.getState());
		addressBean.setCountry(address.getCountry());
		addressBean.setPincode(address.getPincode());
		cardBean.setAddress(addressBean);

		return cardBean;

	}

}
