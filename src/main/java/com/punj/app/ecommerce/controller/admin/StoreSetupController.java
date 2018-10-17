package com.punj.app.ecommerce.controller.admin;
/**
 * 
 */

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.SetupTransformer;
import com.punj.app.ecommerce.domains.common.Location;
import com.punj.app.ecommerce.domains.common.State;
import com.punj.app.ecommerce.domains.user.Password;
import com.punj.app.ecommerce.models.LoginBean;
import com.punj.app.ecommerce.models.setup.InitialSetupBean;
import com.punj.app.ecommerce.services.common.CommonService;
import com.punj.app.ecommerce.services.common.SetupService;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class StoreSetupController {
	private static final Logger logger = LogManager.getLogger();
	private MessageSource messageSource;
	private CommonService commonService;
	private SetupService setupService;

	/**
	 * @param setupService
	 *            the setupService to set
	 */
	@Autowired
	public void setSetupService(SetupService setupService) {
		this.setupService = setupService;
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
	 * @param messageSource
	 *            the messageSource to set
	 */
	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@GetMapping(ViewPathConstants.STORE_SETUP_URL)
	public String showStoreSetupScreen(Model model, Authentication authentication, Locale locale) {
		List<Location> locations = this.commonService.retrieveAllLocations();
		List<State> states = this.commonService.retrieveAllStates();
		Boolean isSetupNeeded = Boolean.TRUE;
		if (locations != null && !locations.isEmpty())
			isSetupNeeded = Boolean.FALSE;
		InitialSetupBean initialSetupBean = new InitialSetupBean();

		Boolean result = this.setupService.isSetupGood();

		model.addAttribute(MVCConstants.STATES_LIST, states);
		model.addAttribute(MVCConstants.SETUP_STATUS_BEAN, result);
		model.addAttribute(MVCConstants.INITIAL_SETUP_BEAN, initialSetupBean);
		model.addAttribute(MVCConstants.LOGIN_BEAN, new LoginBean());
		model.addAttribute(MVCConstants.SETUP_NEEDED_PARAM, isSetupNeeded);

		return ViewPathConstants.STORE_SETUP_PAGE;

	}

	@PostMapping(value = ViewPathConstants.SAVE_STORE_SETUP_URL)
	public String saveStoreConfigurations(@ModelAttribute InitialSetupBean initialSetupBean, Model model, Locale locale) {
		try {
			Map<String, State> statesMap = this.commonService.statesAsMapByGSTCode();
			Location location = SetupTransformer.transformLocationDetails(initialSetupBean);
			State state=statesMap.get(location.getState());
			location.setState(state.getStateName());
			Password password = SetupTransformer.transformPasswordDetails(initialSetupBean);
			Boolean result = this.setupService.saveInitialSetup(location, initialSetupBean.getSelectedTenders(), password);
			if (!result) {
				logger.error("The initial store setup has failed, contact the administrator!!!");
				model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.initial.setup.failed", null, locale));
				model.addAttribute(MVCConstants.INITIAL_SETUP_BEAN, initialSetupBean);
			} else {
				model.addAttribute(MVCConstants.SUCCESS, this.messageSource.getMessage("commerce.screen.initial.setup.success", null, locale));
				model.addAttribute(MVCConstants.INITIAL_SETUP_BEAN, initialSetupBean);
				logger.info("The initial store setup has completed successfully");
			}
		} catch (Exception e) {
			logger.error("There is an error while setting up store", e);
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			model.addAttribute(MVCConstants.INITIAL_SETUP_BEAN, initialSetupBean);
		}

		List<State> states = this.commonService.retrieveAllStates();
		model.addAttribute(MVCConstants.STATES_LIST, states);

		Boolean isSetupGood = this.setupService.isSetupGood();
		model.addAttribute(MVCConstants.SETUP_STATUS_BEAN, isSetupGood);

		model.addAttribute(MVCConstants.LOGIN_BEAN, new LoginBean());

		List<Location> locations = this.commonService.retrieveAllLocations();
		Boolean isSetupNeeded = Boolean.TRUE;
		if (locations != null && !locations.isEmpty())
			isSetupNeeded = Boolean.FALSE;
		model.addAttribute(MVCConstants.SETUP_NEEDED_PARAM, isSetupNeeded);

		return ViewPathConstants.STORE_SETUP_PAGE;
	}

}
