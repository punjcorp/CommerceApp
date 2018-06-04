package com.punj.app.ecommerce.controller.account;
/**
 * 
 */

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.AccountTransformer;
import com.punj.app.ecommerce.domains.user.User;
import com.punj.app.ecommerce.models.account.validator.AccountBeanValidator;
import com.punj.app.ecommerce.services.UserService;
import com.punj.app.ecommerce.services.common.CommonService;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class AccountPhaseController {
	private static final Logger logger = LogManager.getLogger();
	private UserService userService;
	private CommonService commonService;
	private MessageSource messageSource;
	private AccountBeanValidator accountValidator;

	/**
	 * @param accountValidator
	 *            the accountValidator to set
	 */
	@Autowired
	public void setAccountValidator(AccountBeanValidator accountValidator) {
		this.accountValidator = accountValidator;
	}

	/**
	 * @param messageSource
	 *            the messageSource to set
	 */
	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	/**
	 * @param commonService
	 *            the commonService to set
	 */
	@Autowired
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	/**
	 * @param userService
	 *            the userService to set
	 */
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@GetMapping(ViewPathConstants.APPROVE_ACCOUNT_URL)
	public String approveAccount(Model model, RedirectAttributes redirectAttrs, final HttpServletRequest req, Locale locale, Authentication authentication,
			HttpSession session) {
		try {
			String username = req.getParameter(MVCConstants.USERNAME_PARAM);

			if (StringUtils.isNotBlank(username)) {
				UserDetails userDetails = (UserDetails) authentication.getPrincipal();
				User user = this.userService.getUserByUsername(username);
				if (user != null) {
					if (user.getStatus().equals(MVCConstants.STATUS_CREATED)) {
						this.userService.approveUser(user.getUsername(), userDetails.getUsername());
						redirectAttrs.addFlashAttribute(MVCConstants.SUCCESS,
								messageSource.getMessage("commerce.screen.manage.accounts.approve.success", new Object[] { username }, locale));

						logger.info("The selected user account has been approved successfully");
					} else {
						redirectAttrs.addFlashAttribute(MVCConstants.ALERT,
								this.messageSource.getMessage("commerce.screen.manage.accounts.approve.status", null, locale));
						logger.info("There user account is not eligible for the approval as status is not 'created'!!");
					}
				} else {
					redirectAttrs.addFlashAttribute(MVCConstants.ALERT,
							this.messageSource.getMessage("commerce.screen.manage.accounts.user.notfound", null, locale));
					logger.info("The provided user details was not found in the database!!");
				}

			} else {
				redirectAttrs.addFlashAttribute(MVCConstants.ALERT,
						this.messageSource.getMessage("commerce.screen.manage.accounts.action.nouser", null, locale));
				logger.info("There is no username specified for approval");
			}
		} catch (Exception e) {
			redirectAttrs.addFlashAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			logger.error("There is an error while approving user details", e);
		}
		AccountTransformer.updateSearchCriteria(session, redirectAttrs);
		req.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
		return ViewPathConstants.REDIRECT_URL + ViewPathConstants.MANAGE_ACCOUNTS_URL;
	}

	@GetMapping(ViewPathConstants.DELETE_ACCOUNT_URL)
	public String deleteAccount(Model model, final HttpServletRequest req, RedirectAttributes redirectAttrs, Locale locale, Authentication authentication,
			HttpSession session) {
		try {
			String username = req.getParameter(MVCConstants.USERNAME_PARAM);

			if (StringUtils.isNotBlank(username)) {
				UserDetails userDetails = (UserDetails) authentication.getPrincipal();
				User user = this.userService.getUserByUsername(username);
				if (user != null) {
					if (user.getStatus().equals(MVCConstants.STATUS_CREATED)) {
						this.userService.deleteUser(user.getUsername(), userDetails.getUsername());
						redirectAttrs.addFlashAttribute(MVCConstants.SUCCESS,
								messageSource.getMessage("commerce.screen.manage.accounts.delete.success", new Object[] { username }, locale));

						logger.info("The selected user account has been deleted successfully");
					} else {
						redirectAttrs.addFlashAttribute(MVCConstants.ALERT,
								this.messageSource.getMessage("commerce.screen.manage.accounts.delete.status", null, locale));
						logger.info("There user account is not eligible for the deletion as status is not 'created'!!");
					}
				} else {
					redirectAttrs.addFlashAttribute(MVCConstants.ALERT,
							this.messageSource.getMessage("commerce.screen.manage.accounts.user.notfound", null, locale));
					logger.info("The provided user details was not found in the database!!");
				}

			} else {
				redirectAttrs.addFlashAttribute(MVCConstants.ALERT,
						this.messageSource.getMessage("commerce.screen.manage.accounts.action.nouser", null, locale));
				logger.info("There is no username specified for deletion");
			}
		} catch (Exception e) {
			redirectAttrs.addFlashAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			logger.error("There is an error while deleting user details", e);
		}
		AccountTransformer.updateSearchCriteria(session, redirectAttrs);
		req.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
		return ViewPathConstants.REDIRECT_URL + ViewPathConstants.MANAGE_ACCOUNTS_URL;
	}

	@GetMapping(ViewPathConstants.DISABLE_ACCOUNT_URL)
	public String disableAccount(Model model, final HttpServletRequest req, RedirectAttributes redirectAttrs, Locale locale, Authentication authentication,
			HttpSession session) {
		try {
			String username = req.getParameter(MVCConstants.USERNAME_PARAM);

			if (StringUtils.isNotBlank(username)) {
				UserDetails userDetails = (UserDetails) authentication.getPrincipal();
				User user = this.userService.getUserByUsername(username);
				if (user != null) {
					if (user.getStatus().equals(MVCConstants.STATUS_APPROVED)) {
						this.userService.disableUser(user.getUsername(), userDetails.getUsername());
						redirectAttrs.addFlashAttribute(MVCConstants.SUCCESS,
								messageSource.getMessage("commerce.screen.manage.accounts.disable.success", new Object[] { username }, locale));

						logger.info("The selected user account has been disabled successfully");
					} else {
						redirectAttrs.addFlashAttribute(MVCConstants.ALERT,
								this.messageSource.getMessage("commerce.screen.manage.accounts.disable.status", null, locale));
						logger.info("There user account cannot be disabled as status is not 'approved'!!");
					}
				} else {
					redirectAttrs.addFlashAttribute(MVCConstants.ALERT,
							this.messageSource.getMessage("commerce.screen.manage.accounts.user.notfound", null, locale));
					logger.info("The provided user details was not found in the database!!");
				}

			} else {
				redirectAttrs.addFlashAttribute(MVCConstants.ALERT,
						this.messageSource.getMessage("commerce.screen.manage.accounts.action.nouser", null, locale));
				logger.info("There is no username specified for disable action");
			}
		} catch (Exception e) {
			redirectAttrs.addFlashAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			logger.error("There is an error while disabling user details", e);
		}
		AccountTransformer.updateSearchCriteria(session, redirectAttrs);
		req.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
		return ViewPathConstants.REDIRECT_URL + ViewPathConstants.MANAGE_ACCOUNTS_URL;
	}

	@GetMapping(ViewPathConstants.ACTIVATE_ACCOUNT_URL)
	public String activateAccount(Model model, final HttpServletRequest req, RedirectAttributes redirectAttrs, Locale locale, Authentication authentication, HttpSession session) {
		try {
			String username = req.getParameter(MVCConstants.USERNAME_PARAM);

			if (StringUtils.isNotBlank(username)) {
				UserDetails userDetails = (UserDetails) authentication.getPrincipal();
				User user = this.userService.getUserByUsername(username);
				if (user != null) {
					if (user.getStatus().equals(MVCConstants.STATUS_DISABLED)) {
						this.userService.activateUser(user.getUsername(), userDetails.getUsername());
						redirectAttrs.addFlashAttribute(MVCConstants.SUCCESS,
								messageSource.getMessage("commerce.screen.manage.accounts.activate.success", new Object[] { username }, locale));

						logger.info("The selected user account has been disabled successfully");
					} else {
						redirectAttrs.addFlashAttribute(MVCConstants.ALERT,
								this.messageSource.getMessage("commerce.screen.manage.accounts.activate.status", null, locale));
						logger.info("There user account cannot be disabled as status is not 'approved'!!");
					}
				} else {
					redirectAttrs.addFlashAttribute(MVCConstants.ALERT,
							this.messageSource.getMessage("commerce.screen.manage.accounts.user.notfound", null, locale));
					logger.info("The provided user details was not found in the database!!");
				}

			} else {
				redirectAttrs.addFlashAttribute(MVCConstants.ALERT,
						this.messageSource.getMessage("commerce.screen.manage.accounts.action.nouser", null, locale));
				logger.info("There is no username specified for disable action");
			}
		} catch (Exception e) {
			redirectAttrs.addFlashAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			logger.error("There is an error while disabling user details", e);
		}
		AccountTransformer.updateSearchCriteria(session, redirectAttrs);
		req.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
		return ViewPathConstants.REDIRECT_URL + ViewPathConstants.MANAGE_ACCOUNTS_URL;
	}

}
