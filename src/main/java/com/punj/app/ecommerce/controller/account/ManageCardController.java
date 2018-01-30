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

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.domains.user.Address;
import com.punj.app.ecommerce.domains.user.Card;
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

	@GetMapping("/manage_card")
	public String showCard(Model model, HttpSession session, Authentication authentication) {

		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		List<Card> cards = userService.getCardsByUsername(userDetails.getUsername());

		logger.info("The address list of {} cards for the current user has been retrieved.", cards.size());

		model.addAttribute("cards", cards);
		model.addAttribute(MVCConstants.CARD_BEAN, new CardBean());

		return "account/card_list";
	}

	@GetMapping(ViewPathConstants.ADD_CARD_URL)
	public String addCard(Model model, HttpSession session, Authentication authentication) {

		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		CardBean cardBean = new CardBean();

		List<Address> addresses = userService.getAddressByUsername(userDetails.getUsername());

		Address address = null;
		for (Address addObject : addresses) {
			if (addObject.getPrimary().equals(MVCConstants.YES)) {
				address = addObject;
				break;
			}
		}
		if(address!=null)
			this.updateAddressDetails(cardBean, address);

		logger.info("The address details has been updated in cardBean");

		model.addAttribute(MVCConstants.PRIMARY_ADDRESS_PARAM, address);
		model.addAttribute(MVCConstants.ADDRESSES_PARAM, addresses);
		model.addAttribute(MVCConstants.CARD_BEAN, cardBean);
		return ViewPathConstants.ADD_CARD_PAGE;
	}

	@PostMapping(ViewPathConstants.ADD_CARD_URL)
	public String saveCard(@ModelAttribute CardBean cardBean, Model model, HttpSession session, Locale locale,
			Authentication authentication) {
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			Card card = this.updateCardInDomain(cardBean, userDetails.getUsername());
			userService.saveCard(card);
			model.addAttribute(MVCConstants.SUCCESS,
					messageSource.getMessage("commerce.screen.add.card.success", null, locale));

			logger.info("The card details has been saved.");

			List<Address> addresses = userService.getAddressByUsername(userDetails.getUsername());
			logger.info("All the addresses has been retrieved.");

			Address address = null;
			for (Address addObject : addresses) {
				if (addObject.getAddressId().equals(cardBean.getAddress().getAddressId())) {
					address = addObject;
					break;
				}
			}

			model.addAttribute(MVCConstants.PRIMARY_ADDRESS_PARAM, address);
			model.addAttribute(MVCConstants.ADDRESSES_PARAM, addresses);

		} catch (Exception e) {
			logger.error("There is an error while adding card", e);
			return ViewPathConstants.ERROR_PAGE;

		}
		model.addAttribute(MVCConstants.CARD_BEAN, cardBean);

		return ViewPathConstants.ADD_CARD_PAGE;
	}

	@GetMapping(value = ViewPathConstants.EDIT_CARD_URL)
	public String editCardAction(@ModelAttribute CardBean cardBean, Model model, HttpSession session,
			Authentication authentication) {
		logger.info("The edit method for card has been called");
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();

			Card card = userService.getCard(userDetails.getUsername(), cardBean.getCardNo());
			logger.info("The selected card details has been updated in cardBean");

			List<Address> addresses = userService.getAddressByUsername(userDetails.getUsername());
			logger.info("All the addresses has been retrieved.");

			Address address = null;
			for (Address addObject : addresses) {
				if (addObject.getAddressId().equals(card.getAddress().getAddressId())) {
					address = addObject;
					break;
				}
			}

			cardBean = this.updateCardInBean(cardBean, card, address);

			model.addAttribute(MVCConstants.PRIMARY_ADDRESS_PARAM, address);
			model.addAttribute(MVCConstants.ADDRESSES_PARAM, addresses);

		} catch (Exception e) {
			logger.error("An unknown error has occurred while saving address.", e);
			return ViewPathConstants.ERROR_PAGE;
		}

		model.addAttribute(MVCConstants.CARD_BEAN, cardBean);
		return ViewPathConstants.EDIT_CARD_PAGE;

	}

	@PostMapping(ViewPathConstants.MANAGE_CARD_URL)
	public String updateCard(@ModelAttribute CardBean cardBean, Model model, HttpSession session, Locale locale,
			Authentication authentication) {
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			Card card = this.updateCardInDomain(cardBean, userDetails.getUsername());
			userService.saveCard(card);
			logger.info("The card details has been updated now");
			model.addAttribute(MVCConstants.SUCCESS,
					messageSource.getMessage("commerce.screen.edit.card.success", null, locale));

			List<Address> addresses = userService.getAddressByUsername(userDetails.getUsername());
			logger.info("All the addresses has been retrieved.");

			Address address = null;
			for (Address addObject : addresses) {
				if (addObject.getAddressId().equals(cardBean.getAddress().getAddressId())) {
					address = addObject;
					break;
				}
			}

			model.addAttribute(MVCConstants.PRIMARY_ADDRESS_PARAM, address);
			model.addAttribute(MVCConstants.ADDRESSES_PARAM, addresses);

		} catch (Exception e) {
			logger.error("There is an error while updating card details", e);
			return ViewPathConstants.ERROR_PAGE;
		}

		model.addAttribute(MVCConstants.CARD_BEAN, cardBean);
		return ViewPathConstants.EDIT_CARD_PAGE;
	}

	@GetMapping(value = ViewPathConstants.DELETE_CARD_URL)
	public String deleteAddressAction(@ModelAttribute CardBean cardBean, Model model, HttpSession session,
			Locale locale, Authentication authentication) {
		logger.info("The logic for delete card method goes here");
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			userService.deleteCard(userDetails.getUsername(), cardBean.getCardNo());
			model.addAttribute(MVCConstants.SUCCESS,
					messageSource.getMessage("commerce.screen.manage.card.deletion.success", null, locale));
			logger.info("The address has been deleted now");

			List<Card> cards = userService.getCardsByUsername(userDetails.getUsername());
			logger.info("The address list of {} cards for the current user has been retrieved.", cards.size());
			model.addAttribute("cards", cards);
			model.addAttribute(MVCConstants.CARD_BEAN, cardBean);

		} catch (Exception e) {
			logger.error("An unknown error has occurred while deleting address.", e);
			model.addAttribute("alert",
					messageSource.getMessage("commerce.screen.manage.card.deletion.failure", null, locale));
		}

		return ViewPathConstants.CARD_LIST_PAGE;
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

		this.updateAddressDetails(cardBean, address);

		cardBean.setCardNo(card.getCardId().getCardNo());
		cardBean.setCvvNo(card.getCvvNo());
		cardBean.setExpiryDate(card.getExpiryDate());
		cardBean.setName(card.getNameOnCard());

		return cardBean;

	}

	private void updateAddressDetails(CardBean cardBean, Address address) {

		RegisterUserBean addressBean = new RegisterUserBean();
		addressBean.setAddressId(address.getAddressId());
		addressBean.setAddress1(address.getAddress1());
		addressBean.setAddress2(address.getAddress2());
		addressBean.setCity(address.getCity());
		addressBean.setState(address.getState());
		addressBean.setCountry(address.getCountry());
		addressBean.setPincode(address.getPincode());
		cardBean.setAddress(addressBean);
	}

}
