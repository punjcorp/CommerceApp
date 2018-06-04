package com.punj.app.ecommerce.controller.account;
/**
 * 
 */

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.AccountTransformer;
import com.punj.app.ecommerce.domains.user.UserDTO;
import com.punj.app.ecommerce.models.account.AccountBeanDTO;
import com.punj.app.ecommerce.models.common.SearchBean;
import com.punj.app.ecommerce.services.UserService;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class ManageAccountsController {
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

	@GetMapping(value = ViewPathConstants.MANAGE_ACCOUNTS_URL)
	public String showManageAccounts(Model model) {
		logger.info("The manage users has been called");
		model.addAttribute(MVCConstants.SEARCH_BEAN, new SearchBean());
		return ViewPathConstants.MANAGE_ACCOUNTS_PAGE;
	}

	@PostMapping(ViewPathConstants.MANAGE_ACCOUNTS_URL)
	public String searchAccounts(@ModelAttribute @Valid SearchBean searchBean, BindingResult bindingResult,
			@RequestParam(MVCConstants.PAGE_PARAM) Optional<Integer> page, Model model, HttpSession session, Locale locale) {
		if (bindingResult.hasErrors() && !model.containsAttribute(MVCConstants.SUCCESS))
			return ViewPathConstants.MANAGE_ACCOUNTS_PAGE;
		try {
			if (!model.containsAttribute(MVCConstants.SUCCESS)) {
				Pager pager = new Pager();
				if (!page.isPresent()) {
					pager.setCurrentPageNo(1);
				} else {
					pager.setCurrentPageNo(page.get());
				}
				UserDTO usersDTO = this.userService.searchUsers(searchBean.getSearchText(), pager);
				if (usersDTO != null) {
					AccountBeanDTO accountBeanDTO = AccountTransformer.transformUserDTO(usersDTO);
					this.updateAccountManagementBeans(model, accountBeanDTO, searchBean, session, page);
				}

				model.addAttribute(MVCConstants.SUCCESS,
						messageSource.getMessage("commerce.screen.manage.accounts.result", new Object[] { pager.getResultSize() }, locale));
				logger.info("The user account details has been retrieved successfully.");
			} else {
				model.addAttribute("org.springframework.validation.BindingResult.searchBean", null);
			}
		} catch (Exception e) {
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.manage.accounts.result.failure", null, locale));
			logger.error("There is an error while retrieving user account details", e);
		}
		return ViewPathConstants.MANAGE_ACCOUNTS_PAGE;
	}

	private void updateAccountManagementBeans(Model model, AccountBeanDTO accountBeanDTO, SearchBean searchBean, HttpSession session, Optional<Integer> page) {

		session.setAttribute(MVCConstants.SCREEN_MANAGE_ACCOUNTS_SEARCH_CRITERIA, searchBean);
		session.setAttribute(MVCConstants.SCREEN_MANAGE_ACCOUNTS_SEARCH_CRITERIA_PAGE, page);

		model.addAttribute(MVCConstants.USERS_BEAN, accountBeanDTO);
		model.addAttribute(MVCConstants.PAGER, accountBeanDTO.getPager());
		model.addAttribute(MVCConstants.SEARCH_BEAN, searchBean);
		logger.info("All the needed details has been updated in the model for display.");
	}

	@PostMapping(value = ViewPathConstants.BULK_ACCOUNTS_URL, params = { MVCConstants.APPROVE_ACCOUNTS_PARAM })
	public String approveOrders(@ModelAttribute AccountBeanDTO users, RedirectAttributes redirectAttrs, final HttpServletRequest req, Locale locale,
			Authentication authentication, HttpSession session) {
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			List<String> usernames = AccountTransformer.retrieveEligibleUsers(users, MVCConstants.ACTION_APPROVE_ALL);
			if (usernames != null && !usernames.isEmpty()) {
				this.userService.approveAllUsers(usernames, userDetails.getUsername());
				redirectAttrs.addFlashAttribute(MVCConstants.SUCCESS,
						messageSource.getMessage("commerce.screen.manage.accounts.bulk.approve.success", null, locale));
				logger.info("The bulk update operation for user accounts approval is completed.");
			} else {
				redirectAttrs.addFlashAttribute(MVCConstants.ALERT,
						messageSource.getMessage("commerce.screen.manage.accounts.bulk.approve.no.records", null, locale));
				logger.info("There were no user accounts selected for approval operation!!");
			}
		} catch (Exception e) {
			redirectAttrs.addFlashAttribute(MVCConstants.ALERT, messageSource.getMessage("commerce.screen.manage.accounts.bulk.approve.failure", null, locale));
			logger.error("There is an error while approving bulk user accounts", e);
		}
		AccountTransformer.updateSearchCriteria(session, redirectAttrs);
		req.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
		return ViewPathConstants.REDIRECT_URL + ViewPathConstants.MANAGE_ACCOUNTS_URL;
	}

	@PostMapping(value = ViewPathConstants.BULK_ACCOUNTS_URL, params = { MVCConstants.DELETE_ACCOUNTS_PARAM })
	public String deleteOrders(@ModelAttribute AccountBeanDTO users, RedirectAttributes redirectAttrs, final HttpServletRequest req, Locale locale,
			Authentication authentication, HttpSession session) {
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			List<String> usernames = AccountTransformer.retrieveEligibleUsers(users, MVCConstants.ACTION_DELETE_ALL);
			if (usernames != null && !usernames.isEmpty()) {
				this.userService.deleteAllUsers(usernames, userDetails.getUsername());
				redirectAttrs.addFlashAttribute(MVCConstants.SUCCESS,
						messageSource.getMessage("commerce.screen.manage.accounts.bulk.delete.success", null, locale));
				logger.info("The bulk update operation for user accounts deletion is completed.");
			} else {
				redirectAttrs.addFlashAttribute(MVCConstants.ALERT,
						messageSource.getMessage("commerce.screen.manage.accounts.bulk.delete.no.records", null, locale));
				logger.info("There were no user accounts selected for deletion operation!!");
			}
		} catch (Exception e) {
			redirectAttrs.addFlashAttribute(MVCConstants.ALERT, messageSource.getMessage("commerce.screen.manage.accounts.bulk.delete.failure", null, locale));
			logger.error("There is an error while deleting bulk user accounts", e);
		}
		AccountTransformer.updateSearchCriteria(session, redirectAttrs);
		req.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
		return ViewPathConstants.REDIRECT_URL + ViewPathConstants.MANAGE_ACCOUNTS_URL;
	}

	@PostMapping(value = ViewPathConstants.BULK_ACCOUNTS_URL, params = { MVCConstants.DISABLE_ACCOUNTS_PARAM })
	public String disableOrders(@ModelAttribute AccountBeanDTO users, RedirectAttributes redirectAttrs, final HttpServletRequest req, Locale locale,
			Authentication authentication, HttpSession session) {
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			List<String> usernames = AccountTransformer.retrieveEligibleUsers(users, MVCConstants.ACTION_DISABLE_ALL);
			if (usernames != null && !usernames.isEmpty()) {
				this.userService.disableAllUsers(usernames, userDetails.getUsername());
				redirectAttrs.addFlashAttribute(MVCConstants.SUCCESS,
						messageSource.getMessage("commerce.screen.manage.accounts.bulk.disable.success", null, locale));
				logger.info("The bulk update operation to disable user accounts is completed.");
			} else {
				redirectAttrs.addFlashAttribute(MVCConstants.ALERT,
						messageSource.getMessage("commerce.screen.manage.accounts.bulk.disable.no.records", null, locale));
				logger.info("There were no user accounts selected for disable operation!!");
			}
		} catch (Exception e) {
			redirectAttrs.addFlashAttribute(MVCConstants.ALERT, messageSource.getMessage("commerce.screen.manage.accounts.bulk.disable.failure", null, locale));
			logger.error("There is an error while disabling bulk user accounts", e);
		}
		AccountTransformer.updateSearchCriteria(session, redirectAttrs);
		req.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
		return ViewPathConstants.REDIRECT_URL + ViewPathConstants.MANAGE_ACCOUNTS_URL;
	}



}
