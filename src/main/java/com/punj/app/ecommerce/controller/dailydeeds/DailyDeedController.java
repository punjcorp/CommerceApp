package com.punj.app.ecommerce.controller.dailydeeds;
/**
 * 
 */

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.punj.app.ecommerce.common.web.CommerceConstants;
import com.punj.app.ecommerce.common.web.CommerceContext;
import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.CommonMVCTransformer;
import com.punj.app.ecommerce.controller.common.transformer.DailyDeedTransformer;
import com.punj.app.ecommerce.domains.tender.Tender;
import com.punj.app.ecommerce.domains.transaction.Transaction;
import com.punj.app.ecommerce.domains.transaction.tender.TenderCount;
import com.punj.app.ecommerce.models.common.LocationBean;
import com.punj.app.ecommerce.models.common.RegisterBean;
import com.punj.app.ecommerce.models.common.validator.ValidationGroup;
import com.punj.app.ecommerce.models.dailydeeds.DailyDeedBean;
import com.punj.app.ecommerce.models.tender.DenominationBean;
import com.punj.app.ecommerce.models.tender.TenderBean;
import com.punj.app.ecommerce.services.DailyDeedService;
import com.punj.app.ecommerce.services.TransactionService;
import com.punj.app.ecommerce.services.common.CommonService;
import com.punj.app.ecommerce.services.common.dtos.LocationDTO;
import com.punj.app.ecommerce.services.common.dtos.RegisterDTO;
import com.punj.app.ecommerce.services.dtos.DailyOpenTransaction;
import com.punj.app.ecommerce.utils.Utils;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class DailyDeedController {
	private static final Logger logger = LogManager.getLogger();
	private CommonService commonService;
	private MessageSource messageSource;
	private DailyDeedService dailyDeedService;
	private CommerceContext commerceContext;
	private TransactionService txnService;

	/**
	 * @param commerceContext
	 *            the commerceContext to set
	 */
	@Autowired
	public void setCommerceContext(CommerceContext commerceContext) {
		this.commerceContext = commerceContext;
	}

	/**
	 * @param txnService
	 *            the txnService to set
	 */
	@Autowired
	public void setTransactionService(TransactionService txnService) {
		this.txnService = txnService;
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
	 * @param dailyDeedService
	 *            the dailyDeedService to set
	 */
	@Autowired
	public void setDailyDeedService(DailyDeedService dailyDeedService) {
		this.dailyDeedService = dailyDeedService;
	}

	/**
	 * @param messageSource
	 *            the messageSource to set
	 */
	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@GetMapping(value = ViewPathConstants.STORE_OPEN_URL)
	public String showOpenStore(Model model, Locale locale) {
		logger.info("The show store open screen method has been called");
		try {
			DailyDeedBean dailyDeedBean = new DailyDeedBean();
			// Change this later on and make it an ajax call based on store selected from store open screen
			List<Tender> tenders = this.commonService.retrieveTendersForReconcilation(7997);
			List<TenderBean> tenderBeans = CommonMVCTransformer.tranformTenders(tenders);
			dailyDeedBean.setTenders(tenderBeans);
			this.updateBeans(dailyDeedBean, model);
			logger.info("The Store open screen is ready for display");
		} catch (Exception e) {
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.store.open.error", null, locale));
			logger.error("There was some error retrieving locations for store opening", e);
			return ViewPathConstants.STORE_OPEN_PAGE;
		}

		return ViewPathConstants.STORE_OPEN_PAGE;

	}

	private void updateBeans(DailyDeedBean dailyDeedBean, Model model) {

		LocationDTO locationDTO = this.commonService.retrieveLocationWithDailyStatus();
		List<LocationBean> locations = CommonMVCTransformer.transformLocationDTO(locationDTO);

		Integer locationId = dailyDeedBean.getLocationId();
		if (locationId != null) {
			for (LocationBean location : locations) {
				if (location.getLocationId().equals(locationId)) {
					dailyDeedBean.setLocationName(location.getName());
					dailyDeedBean.setDefaultTender(location.getDefaultTender());
					break;
				}
			}
			logger.info("The location name has been setup correctly");
		}

		model.addAttribute(MVCConstants.DAILY_DEED_BEAN, dailyDeedBean);
		model.addAttribute(MVCConstants.LOCATION_BEANS, locations);
		logger.info("All the beans needs for open store screen has been updated in model");

	}

	private void updateRedirectBeans(DailyDeedBean dailyDeedBean, RedirectAttributes redirectAttrs, HttpServletRequest request) {

		redirectAttrs.addFlashAttribute(MVCConstants.DAILY_DEED_BEAN, dailyDeedBean);
		request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);

		this.updateCommerceContext(dailyDeedBean);

		logger.info("All the beans needs for open store screen has been updated in model");

	}

	private void updateCommerceContext(DailyDeedBean dailyDeedBean) {
		Integer locationId=dailyDeedBean.getLocationId();
		commerceContext.setStoreSettings(CommerceConstants.OPEN_LOC_ID, locationId);
		commerceContext.setStoreSettings(locationId+"-"+CommerceConstants.OPEN_LOC_NAME, dailyDeedBean.getLocationName());
		commerceContext.setStoreSettings(locationId+"-"+CommerceConstants.OPEN_BUSINESS_DATE, dailyDeedBean.getBusinessDate());
		//Get this from selected location later on
		commerceContext.setStoreSettings(locationId+"-"+CommerceConstants.LOC_DEFAULT_TENDER,dailyDeedBean.getDefaultTender()); //MVCConstants.TNDR_CASH

		logger.info("All the store open details has been updated in commerce app context now");

	}

	@PostMapping(value = ViewPathConstants.STORE_OPEN_URL, params = { MVCConstants.OPEN_STORE_PARAM })
	public String processOpenStoreDetails(@ModelAttribute @Validated(ValidationGroup.ValidationGroupStoreOpen.class) DailyDeedBean dailyDeedBean, BindingResult bindingResult, Model model, Locale locale,
			Authentication authentication, RedirectAttributes redirectAttrs, HttpServletRequest request) {
		logger.info("The show store open screen method has been called");
		if (bindingResult.hasErrors()) {
			this.updateBeans(dailyDeedBean, model);
			return ViewPathConstants.STORE_OPEN_PAGE;
		}
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			if (userDetails != null) {
				DailyOpenTransaction storeOpenDetails = DailyDeedTransformer.transformOpenTxnDetails(dailyDeedBean);
				Boolean result = this.dailyDeedService.saveStoreOpenTxn(storeOpenDetails, userDetails.getUsername());
				this.updateBeans(dailyDeedBean, model);
				if (!result) {
					model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.store.open.failure", null, locale));
					logger.info("The Store open process has failed");
					return ViewPathConstants.STORE_OPEN_PAGE;
				}
				this.updateRedirectBeans(dailyDeedBean, redirectAttrs, request);
				logger.info("The Store open process has been successful and ready for register open");
			}
		} catch (Exception e) {
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.store.open.failure", null, locale));
			logger.error("There was some error while opening the store", e);
			this.updateBeans(dailyDeedBean, model);
			return ViewPathConstants.STORE_OPEN_PAGE;
		}

		return ViewPathConstants.REDIRECT_URL + ViewPathConstants.REGISTER_OPEN_URL;

	}

	@PostMapping(value = ViewPathConstants.STORE_OPEN_URL, params = { MVCConstants.ADD_DENOMINATION_PARAM })
	public String addTenderDenomination(@ModelAttribute DailyDeedBean dailyDeedBean, Model model, Locale locale, Authentication authentication,
			final HttpServletRequest req) {
		logger.info("The add tender denomination has been called");
		try {
			final Integer tenderId = dailyDeedBean.getSelectedTenderId();
			if (tenderId != null) {
				List<TenderBean> tenders = dailyDeedBean.getTenders();
				TenderBean tender = tenders.get(tenderId);

				List<DenominationBean> denominations = tender.getDenominations();
				denominations.add(new DenominationBean());
				logger.info("A new denomination record has been added to the daily deed bean successfully");
			} else {
				logger.info("Provide the tender Id to add new denomination!!");
			}
			this.updateBeans(dailyDeedBean, model);

		} catch (Exception e) {
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.store.open.failure", null, locale));
			logger.error("There was some error while adding a new denomination during store open process", e);
			this.updateBeans(dailyDeedBean, model);
			return ViewPathConstants.STORE_OPEN_PAGE;
		}

		return ViewPathConstants.STORE_OPEN_PAGE;

	}

	@PostMapping(value = ViewPathConstants.STORE_OPEN_URL, params = { MVCConstants.REMOVE_DENOMINATION_PARAM })
	public String removeTenderDenomination(@ModelAttribute DailyDeedBean dailyDeedBean, Model model, Locale locale, final HttpServletRequest req) {
		logger.info("The remove tender denomination has been called");
		try {
			final Integer rowId = dailyDeedBean.getSelectedDenominationId();
			final Integer tenderId = dailyDeedBean.getSelectedTenderId();
			if (tenderId != null && rowId != null) {
				List<TenderBean> tenders = dailyDeedBean.getTenders();
				TenderBean tender = tenders.get(tenderId);
				List<DenominationBean> denominations = tender.getDenominations();
				denominations.remove(rowId.intValue());
				tender.setDenominations(denominations);
				logger.info("The selected denomination has been removed successfully");
			} else {
				logger.info("Provide the tender Id and denomination id to delete denomination!!");
			}

			this.updateBeans(dailyDeedBean, model);

		} catch (Exception e) {
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.store.open.failure", null, locale));
			logger.error("There was some error while opening the store", e);
			this.updateBeans(dailyDeedBean, model);
			return ViewPathConstants.STORE_OPEN_PAGE;
		}

		return ViewPathConstants.STORE_OPEN_PAGE;

	}

	private void updateBeansForRegisterOpen(DailyDeedBean dailyDeedBean, Model model) {

		RegisterDTO registerDTO = this.commonService.retrieveRegisterWithDailyStatus(dailyDeedBean.getLocationId());
		List<RegisterBean> registers = CommonMVCTransformer.transformRegisterDTO(registerDTO);

		Integer registerId = dailyDeedBean.getRegister();
		if (registerId != null) {
			for (RegisterBean register : registers) {
				if (register.getRegisterId().equals(registerId)) {
					dailyDeedBean.setRegisterId(register.getRegisterId());
					dailyDeedBean.setRegisterName(register.getName());
					break;
				}
			}
			logger.info("The location name has been setup correctly");
		}

		dailyDeedBean.setRegister(null);

		model.addAttribute(MVCConstants.DAILY_DEED_BEAN, dailyDeedBean);
		model.addAttribute(MVCConstants.REGISTER_BEANS, registers);
		logger.info("All the beans needs for open store screen has been updated in model");

	}

	@GetMapping(value = ViewPathConstants.REGISTER_OPEN_URL)
	public String showOpenRegisterScreen(final HttpServletRequest req, Model model, Locale locale) {
		logger.info("The show store open screen method has been called when store is already open");
		try {
			Integer locationId = new Integer(req.getParameter(MVCConstants.LOCATION_ID_PARAM));
			String locationName = req.getParameter(MVCConstants.LOC_NAME_PARAM);
			String defaultTender = req.getParameter(MVCConstants.DEFAULT_TENDER_PARAM);
			LocalDateTime businessDate = Utils.parseDate((String) req.getParameter(MVCConstants.B_DATE_PARAM));
			if (businessDate != null) {

				Transaction txnDetails = this.txnService.searchLocationOpenTxn(locationId, businessDate);
				TenderCount tenderCount = this.dailyDeedService.searchTxnTenderCount(txnDetails.getTransactionId());

				DailyDeedBean dailyDeedBean = DailyDeedTransformer.transformDailyTxn(txnDetails, tenderCount);
				if (dailyDeedBean != null) {
					logger.info("The Store open transaction data has been retrieved successfully");
					this.updateBeansForRegisterOpen(dailyDeedBean, model);
					dailyDeedBean.setLocationName(locationName);
					dailyDeedBean.setDefaultTender(defaultTender);

					this.updateCommerceContext(dailyDeedBean);
					logger.info("The Register open screen is ready for display");
				} else {
					model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.register.open.error", null, locale));
					logger.error("There was some error retrieving store open details for register open");
				}

			} else {
				model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.register.open.error", null, locale));
				logger.error("There was some error retrieving store open details for register open");
			}
		} catch (Exception e) {
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.register.open.error", null, locale));
			logger.error("There was some error retrieving store open details for register open", e);
			return ViewPathConstants.REGISTER_OPEN_PAGE;
		}

		return ViewPathConstants.REGISTER_OPEN_PAGE;

	}

	@PostMapping(value = ViewPathConstants.REGISTER_OPEN_URL)
	public String showOpenRegister(@ModelAttribute DailyDeedBean dailyDeedBean, BindingResult bindingResult, Model model, Locale locale) {
		logger.info("The show store open screen method has been called");
		try {
			if (dailyDeedBean != null && dailyDeedBean.getLocationId() != null && dailyDeedBean.getBusinessDate() != null) {
				// This will go as the extra amount will get added to repository during register open
				// if we want we will notify user that you have added more than store open amounts
				List<TenderBean> tenders = DailyDeedTransformer.cloneTenderList(dailyDeedBean.getTenders());
				dailyDeedBean.setAvailableTenders(tenders);
				this.updateBeansForRegisterOpen(dailyDeedBean, model);
				logger.info("The Register open screen is ready for display");
			} else {
				model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.register.open.error", null, locale));
				logger.error("There was some error retrieving store open details for register open");
			}
		} catch (Exception e) {
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.register.open.error", null, locale));
			logger.error("There was some error retrieving store open details for register open", e);
			return ViewPathConstants.REGISTER_OPEN_PAGE;
		}

		return ViewPathConstants.REGISTER_OPEN_PAGE;

	}

	private void addDenominationDetails(DailyDeedBean dailyDeedBean, Model model) {
		final Integer tenderId = dailyDeedBean.getSelectedTenderId();
		if (tenderId != null) {
			List<TenderBean> tenders = dailyDeedBean.getTenders();
			TenderBean tender = tenders.get(tenderId);

			List<DenominationBean> denominations = tender.getDenominations();
			denominations.add(new DenominationBean());
			logger.info("A new denomination record has been added to the daily deed bean successfully");
		} else {
			logger.info("Provide the tender Id to add new denomination!!");
		}
		this.updateBeans(dailyDeedBean, model);

	}

	@PostMapping(value = ViewPathConstants.REGISTER_OPEN_URL, params = { MVCConstants.ADD_DENOMINATION_PARAM })
	public String addTenderDenominationRegister(@ModelAttribute DailyDeedBean dailyDeedBean, Model model, Locale locale, Authentication authentication,
			final HttpServletRequest req) {
		logger.info("The add tender denomination has been called");
		try {
			this.addDenominationDetails(dailyDeedBean, model);
		} catch (Exception e) {
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.register.open.failure", null, locale));
			logger.error("There was some error while adding a new denomination during register open process", e);
			return ViewPathConstants.REGISTER_OPEN_PAGE;
		}

		return ViewPathConstants.REGISTER_OPEN_PAGE;

	}

	private void deleteDenominationDetails(DailyDeedBean dailyDeedBean, Model model) {
		final Integer rowId = dailyDeedBean.getSelectedDenominationId();
		final Integer tenderId = dailyDeedBean.getSelectedTenderId();
		if (tenderId != null && rowId != null) {
			List<TenderBean> tenders = dailyDeedBean.getTenders();
			TenderBean tender = tenders.get(tenderId);
			List<DenominationBean> denominations = tender.getDenominations();
			denominations.remove(rowId.intValue());
			tender.setDenominations(denominations);
			logger.info("The selected denomination has been removed successfully");
		} else {
			logger.info("Provide the tender Id and denomination id to delete denomination!!");
		}

		this.updateBeans(dailyDeedBean, model);
	}

	@PostMapping(value = ViewPathConstants.REGISTER_OPEN_URL, params = { MVCConstants.REMOVE_DENOMINATION_PARAM })
	public String removeTenderDenominationRegister(@ModelAttribute DailyDeedBean dailyDeedBean, Model model, Locale locale, final HttpServletRequest req) {
		logger.info("The remove tender denomination has been called");
		try {
			this.deleteDenominationDetails(dailyDeedBean, model);

		} catch (Exception e) {
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.register.open.failure", null, locale));
			logger.error("There was some error while adding denomination for open register screen", e);
			return ViewPathConstants.REGISTER_OPEN_PAGE;
		}

		return ViewPathConstants.REGISTER_OPEN_PAGE;

	}

	@PostMapping(value = ViewPathConstants.REGISTER_OPEN_URL, params = { MVCConstants.OPEN_REGISTER_PARAM })
	public String processOpenRegisterDetails(@ModelAttribute @Validated(ValidationGroup.ValidationGroupRegOpen.class) DailyDeedBean dailyDeedBean, BindingResult bindingResult, Model model, Locale locale,
			Authentication authentication, HttpSession session) {
		logger.info("The show store open screen method has been called");
		if (bindingResult.hasErrors()) {
			this.updateBeansForRegisterOpen(dailyDeedBean, model);
			return ViewPathConstants.REGISTER_OPEN_PAGE;
		}
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			if (userDetails != null) {
				DailyOpenTransaction registerOpenDetails = DailyDeedTransformer.transformOpenTxnDetails(dailyDeedBean);
				Boolean result = this.dailyDeedService.saveRegisterOpenTxn(registerOpenDetails, userDetails.getUsername());
				this.updateBeans(dailyDeedBean, model);
				if (!result) {
					model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.register.open.failure", null, locale));
					logger.info("The Register open process failed due to some unknown issue");
					return ViewPathConstants.REGISTER_OPEN_PAGE;
				}
				this.updateBeansForRegisterOpen(dailyDeedBean, model);

				session.setAttribute(MVCConstants.REGISTER_ID_PARAM, dailyDeedBean.getRegisterId());
				session.setAttribute(MVCConstants.REG_NAME_PARAM, dailyDeedBean.getRegisterName());
				logger.info("The Register open process was successful and register is ready for sale now");
			}
		} catch (Exception e) {
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.register.open.failure", null, locale));
			logger.error("There was some error while opening the register", e);
			return ViewPathConstants.REGISTER_OPEN_PAGE;
		}

		return ViewPathConstants.REDIRECT_URL + ViewPathConstants.POS_URL;

	}

}
