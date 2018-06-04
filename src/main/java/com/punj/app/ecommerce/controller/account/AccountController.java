package com.punj.app.ecommerce.controller.account;
/**
 * 
 */

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.AccountTransformer;
import com.punj.app.ecommerce.controller.common.transformer.CommonMVCTransformer;
import com.punj.app.ecommerce.domains.common.Location;
import com.punj.app.ecommerce.domains.user.Role;
import com.punj.app.ecommerce.domains.user.User;
import com.punj.app.ecommerce.models.account.AccountBean;
import com.punj.app.ecommerce.models.account.RoleBean;
import com.punj.app.ecommerce.models.account.validator.AccountBeanValidator;
import com.punj.app.ecommerce.models.common.LocationBean;
import com.punj.app.ecommerce.models.common.SearchBean;
import com.punj.app.ecommerce.services.UserService;
import com.punj.app.ecommerce.services.common.CommonService;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class AccountController {
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

	@RequestMapping(ViewPathConstants.CREATE_ACCOUNT_URL)
	public String createAccount(Model model) {

		this.updateEmptyBeans(model);
		logger.info("The screen for creating an new account is ready for display");
		return ViewPathConstants.CREATE_ACCOUNT_PAGE;

	}

	private void updateEmptyBeans(Model model) {
		AccountBean accountBean = new AccountBean();
		this.updateAccountBeans(model, accountBean);
	}

	private void updateAccountBeans(Model model, AccountBean accountBean) {

		List<Location> locations = this.commonService.retrieveAllLocations();
		if (locations != null && !locations.isEmpty()) {
			List<LocationBean> locationBeans = CommonMVCTransformer.transformLocationList(locations, Boolean.TRUE);
			model.addAttribute(MVCConstants.LOCATION_BEANS, locationBeans);
		}

		List<Role> roles = this.userService.getAllUserRoles();
		if (roles != null && !roles.isEmpty()) {
			List<RoleBean> roleBeans = AccountTransformer.transformRoles(roles);
			model.addAttribute(MVCConstants.ROLE_BEANS, roleBeans);
			logger.info("The account roles has been updated in the bean details");
		}
		model.addAttribute(MVCConstants.ACCOUNT_BEAN_PARAM, accountBean);
		logger.info("The account bean has been updated in the model object");
	}

	@PostMapping(value = ViewPathConstants.CREATE_ACCOUNT_URL, params = { MVCConstants.SAVE_ACCOUNT_PARAM })
	public String saveAccountDetails(@ModelAttribute @Valid AccountBean accountBean, BindingResult bindingResult, Model model, Locale locale,
			Authentication authentication) {
		return processAccountRequest(accountBean, bindingResult, model, locale, authentication, MVCConstants.ACTION_NEW);
	}

	@PostMapping(value = ViewPathConstants.CREATE_ACCOUNT_URL, params = { MVCConstants.APPROVE_ACCOUNT_PARAM })
	public String approveAccountDetails(@ModelAttribute @Valid AccountBean accountBean, BindingResult bindingResult, Model model, Locale locale,
			Authentication authentication) {
		String action = null;
		if (StringUtils.isNotBlank(accountBean.getCreatedBy()))
			action = MVCConstants.ACTION_EDIT_APPROVE;
		else
			action = MVCConstants.ACTION_NEW_APPROVE;

		return processAccountRequest(accountBean, bindingResult, model, locale, authentication, action);
	}

	private String getReturnPageResult(String action, Model model, AccountBean accountBean, Locale locale, Boolean isSuccess) {
		if(isSuccess) {
			if (action.equals(MVCConstants.ACTION_NEW)) {
				model.addAttribute(MVCConstants.SUCCESS,
						messageSource.getMessage("commerce.screen.register.success", new Object[] { accountBean.getUsername() }, locale));
				return ViewPathConstants.CREATE_ACCOUNT_PAGE;
			}
				
			else if (action.equals(MVCConstants.ACTION_NEW_APPROVE)) {
				model.addAttribute(MVCConstants.SUCCESS,
						messageSource.getMessage("commerce.screen.register.approve.success", new Object[] { accountBean.getUsername() }, locale));
				return ViewPathConstants.CREATE_ACCOUNT_PAGE;
			}
				
			else if (action.equals(MVCConstants.ACTION_EDIT)) {
				model.addAttribute(MVCConstants.SUCCESS,
						messageSource.getMessage("commerce.screen.account.edit.success", new Object[] { accountBean.getUsername() }, locale));
				return ViewPathConstants.EDIT_ACCOUNT_PAGE;
			}
				
			else if (action.equals(MVCConstants.ACTION_EDIT_APPROVE)) {
				model.addAttribute(MVCConstants.SUCCESS,
						messageSource.getMessage("commerce.screen.account.edit.approve.success", null, locale));
				return ViewPathConstants.EDIT_ACCOUNT_PAGE;
			}else {
				return ViewPathConstants.EDIT_ACCOUNT_PAGE;
			}
		}else {
			if (action.equals(MVCConstants.ACTION_NEW) || action.equals(MVCConstants.ACTION_NEW_APPROVE)) {
				return ViewPathConstants.CREATE_ACCOUNT_PAGE;
			}else if (action.equals(MVCConstants.ACTION_EDIT) || action.equals(MVCConstants.ACTION_EDIT_APPROVE)) {
				return ViewPathConstants.EDIT_ACCOUNT_PAGE;
			}else {
				return ViewPathConstants.EDIT_ACCOUNT_PAGE;
			}
		}
					
	}
	
	private String processAccountRequest(AccountBean accountBean, BindingResult bindingResult, Model model, Locale locale, Authentication authentication,
			String action) {
		String result=null;
		this.accountValidator.validate(accountBean, bindingResult);
		logger.info("The price class level validation has been completed successfully");
		if (bindingResult.hasErrors()) {
			this.updateAccountBeans(model, accountBean);
			return this.getReturnPageResult(action, model, accountBean, locale, Boolean.FALSE);
		}
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			String username = userDetails.getUsername();
			this.updateAccountBeanBasedOnAction(accountBean, action, username);
			User user = AccountTransformer.transformAccountBean(accountBean);
			if (user != null) {
				user = this.userService.saveUser(user);
				if (user != null) {
					User updatedUser = this.userService.getUserByUsername(user.getUsername());
					accountBean = AccountTransformer.transformUser(updatedUser);
					this.updateAccountBeans(model, accountBean);
					logger.info("The user details has been successfully registered");
					result=this.getReturnPageResult(action, model, accountBean, locale, Boolean.TRUE);
								
				} else {
					this.updateAccountBeans(model, accountBean);
					logger.info("There was some issue while creating a new user account!!");
					model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.register.save.failure", null, locale));
				}
			} else {
				this.updateAccountBeans(model, accountBean);
				logger.info("The needed details for account creation were not provided!!");
				model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.register.nouser", null, locale));
			}

		} catch (IOException e) {
			logger.info("There is some unknown error while creating an account", e);
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
		}

		return result;
	}

	private void updateAccountBeanBasedOnAction(AccountBean accountBean, String action, String username) {

		if (action.equals(MVCConstants.ACTION_NEW) || action.equals(MVCConstants.ACTION_NEW_APPROVE)) {
			if (action.equals(MVCConstants.ACTION_NEW))
				accountBean.setStatus(MVCConstants.STATUS_CREATED);
			else
				accountBean.setStatus(MVCConstants.STATUS_APPROVED);
			accountBean.setCreatedBy(username);
			accountBean.setCreatedDate(LocalDateTime.now());
		} else if (action.equals(MVCConstants.ACTION_EDIT_APPROVE) || action.equals(MVCConstants.ACTION_EDIT)) {

			if (action.equals(MVCConstants.ACTION_EDIT_APPROVE))
				accountBean.setStatus(MVCConstants.STATUS_APPROVED);
			accountBean.setModifiedBy(username);
			accountBean.setModifiedDate(LocalDateTime.now());
		}

	}

	@PostMapping(value = ViewPathConstants.SEARCH_ACCOUNT_URL, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public Boolean isUserExisting(@RequestBody @Valid SearchBean searchBean, BindingResult bindingResult, Model model, HttpSession session) {
		Boolean result = Boolean.FALSE;
		if (bindingResult.hasErrors())
			return result;
		try {

			User user = this.userService.getUserByUsername(searchBean.getSearchText());
			if (user != null) {
				result = Boolean.TRUE;
				logger.info("The user account has been retrieved successfully");
			}

		} catch (Exception e) {
			logger.error("There is an error while retrieving user account", e);
			return result;
		}

		return result;
	}

	@GetMapping(ViewPathConstants.EDIT_ACCOUNT_URL)
	public String approveAccount(Model model, final HttpServletRequest req, Locale locale, Authentication authentication, HttpSession session) {
		try {
			String username = req.getParameter(MVCConstants.USERNAME_PARAM);
			AccountBean accountBean = null;
			if (StringUtils.isNotBlank(username)) {
				User user = this.userService.getUserByUsername(username);
				if (user != null) {
					if (user.getStatus().equals(MVCConstants.STATUS_CREATED)) {

						accountBean = AccountTransformer.transformUser(user);
						this.updateAccountBeans(model, accountBean);
						logger.info("The selected user account has been retrived and ready for editing");
					} else {
						this.updateEmptyBeans(model);
						model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.manage.accounts.edit.status", null, locale));
						logger.info("There user account is not eligible for the editing as status is not 'created'!!");
					}
				} else {
					this.updateEmptyBeans(model);
					model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.manage.accounts.user.notfound", null, locale));
					logger.info("The provided user details was not found in the database!!");
				}

			} else {
				this.updateEmptyBeans(model);
				model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.manage.accounts.action.nouser", null, locale));
				logger.info("There is no username specified for editing");
			}
		} catch (Exception e) {
			this.updateEmptyBeans(model);
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			logger.error("There is an error while retrieving user details", e);
		}
		return ViewPathConstants.EDIT_ACCOUNT_PAGE;
	}

	@PostMapping(value = ViewPathConstants.EDIT_ACCOUNT_URL, params = { MVCConstants.SAVE_ACCOUNT_PARAM })
	public String saveEditedAccountDetails(@ModelAttribute @Valid AccountBean accountBean, BindingResult bindingResult, Model model, Locale locale,
			Authentication authentication) {
		return processAccountRequest(accountBean, bindingResult, model, locale, authentication, MVCConstants.ACTION_EDIT);
	}

	@PostMapping(value = ViewPathConstants.EDIT_ACCOUNT_URL, params = { MVCConstants.APPROVE_ACCOUNT_PARAM })
	public String approveEditedAccountDetails(@ModelAttribute @Valid AccountBean accountBean, BindingResult bindingResult, Model model, Locale locale,
			Authentication authentication) {
		return processAccountRequest(accountBean, bindingResult, model, locale, authentication, MVCConstants.ACTION_EDIT_APPROVE);
	}

}
