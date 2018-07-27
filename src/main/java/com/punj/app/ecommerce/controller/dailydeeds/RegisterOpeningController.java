package com.punj.app.ecommerce.controller.dailydeeds;
/**
 * 
 */

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.punj.app.ecommerce.services.dtos.dailydeeds.DailyDeedDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.punj.app.ecommerce.common.web.CommerceConstants;
import com.punj.app.ecommerce.common.web.CommerceContext;
import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.CommonMVCTransformer;
import com.punj.app.ecommerce.controller.common.transformer.DailyDeedTransformer;
import com.punj.app.ecommerce.domains.common.Denomination;
import com.punj.app.ecommerce.domains.transaction.tender.TenderCount;
import com.punj.app.ecommerce.models.common.BaseDenominationBean;
import com.punj.app.ecommerce.models.common.LocationBean;
import com.punj.app.ecommerce.models.common.RegisterBean;
import com.punj.app.ecommerce.models.common.validator.ValidationGroup;
import com.punj.app.ecommerce.models.dailydeeds.DailyDeedBean;
import com.punj.app.ecommerce.models.dailydeeds.validator.RegisterOpenValidator;
import com.punj.app.ecommerce.models.tender.DenominationBean;
import com.punj.app.ecommerce.models.tender.TenderBean;
import com.punj.app.ecommerce.services.DailyDeedService;
import com.punj.app.ecommerce.services.TransactionService;
import com.punj.app.ecommerce.services.common.CommonService;
import com.punj.app.ecommerce.services.common.dtos.LocationDTO;
import com.punj.app.ecommerce.services.common.dtos.RegisterDTO;
import com.punj.app.ecommerce.services.dtos.DailyTransaction;
import com.punj.app.ecommerce.utils.Utils;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class RegisterOpeningController {
	private static final Logger logger = LogManager.getLogger();
	private CommonService commonService;
	private MessageSource messageSource;
	private DailyDeedService dailyDeedService;
	private CommerceContext commerceContext;
	private TransactionService txnService;
	private RegisterOpenValidator registerOpenValidator;

	/**
	 * @param registerOpenValidator
	 *            the registerOpenValidator to set
	 */
	@Autowired
	public void setRegisterOpenValidator(RegisterOpenValidator registerOpenValidator) {
		this.registerOpenValidator = registerOpenValidator;
	}

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

	@PostMapping(value = ViewPathConstants.REGISTER_OPEN_URL)
	public String showRedirectedRegisterOpenScreen(@ModelAttribute DailyDeedBean dailyDeedBean, final HttpServletRequest req, Model model, Locale locale,
			HttpSession session) {
		Optional<DailyDeedBean> dailyDeedOptionalBean =  Optional.of(dailyDeedBean);
		return this.showRegisterOpenScreen(dailyDeedOptionalBean, req, model, locale, session);
	}

	@GetMapping(value = ViewPathConstants.REGISTER_OPEN_URL)
	public String showRegisterOpenScreen(Optional<DailyDeedBean> dailyDeedOptionalBean, final HttpServletRequest req, Model model, Locale locale, HttpSession session) {
		logger.info("The show register open screen method has been called ");
		try {
			Integer locationId = null;
			String locationName = null;
			String defaultTender = null;
			LocalDateTime businessDate = null;
			String referrerURL = null;
			DailyDeedBean dailyDeedBean=null;
			if(dailyDeedOptionalBean.isPresent())
				dailyDeedBean=dailyDeedOptionalBean.get();
			if(dailyDeedBean!=null) {
				locationId=dailyDeedBean.getLocationId();
				if(locationId!=null) {
					locationName=dailyDeedBean.getLocationName();
					defaultTender=dailyDeedBean.getDefaultTender();
					businessDate=dailyDeedBean.getBusinessDate();
				}
			}
			
			if(locationId==null || businessDate==null) {
				String locStr = req.getParameter(MVCConstants.LOCATION_ID_PARAM);

				if (locStr != null) {
					locationId = new Integer(locStr);
					locationName = req.getParameter(MVCConstants.LOC_NAME_PARAM);
					defaultTender = req.getParameter(MVCConstants.DEFAULT_TENDER_PARAM);
					businessDate = Utils.parseDate((String) req.getParameter(MVCConstants.B_DATE_PARAM));
				} else {
					locationId = (Integer) commerceContext.getStoreSettings(CommerceConstants.OPEN_LOC_ID);
					locationName = (String) commerceContext.getStoreSettings(locationId + "-" + CommerceConstants.OPEN_LOC_NAME);
					businessDate = (LocalDateTime) commerceContext.getStoreSettings(locationId + "-" + CommerceConstants.OPEN_BUSINESS_DATE);
					defaultTender = (String) commerceContext.getStoreSettings(locationId + "-" + CommerceConstants.LOC_DEFAULT_TENDER);
				}
			}
			
			if (locationId != null) {
				if (businessDate != null) {

					if(dailyDeedBean==null) {
						dailyDeedBean = new DailyDeedBean();
						dailyDeedBean.setLocationId(locationId);
						dailyDeedBean.setLocationName(locationName);
						dailyDeedBean.setBusinessDate(businessDate);
						dailyDeedBean.setDefaultTender(defaultTender);

						referrerURL = req.getParameter(MVCConstants.REFERRER_URL_PARAM);
						if (StringUtils.isBlank(referrerURL)) {
							Map<String, Object> attrMap = model.asMap();
							if (attrMap != null && attrMap.size() > 0) {
								Object referrerObj = attrMap.get(MVCConstants.REFERRER_URL_PARAM);
								if (referrerObj != null)
									referrerURL = (String) referrerObj;
							}
						}

						if (StringUtils.isNotBlank(referrerURL))
							dailyDeedBean.setReferrerURL(referrerURL);
					}

					this.updateCommerceContext(dailyDeedBean);


					Boolean hasError = this.updateBeansAllActions(dailyDeedBean, model, locale, session, Boolean.TRUE);
					if (hasError)
						return ViewPathConstants.REGISTER_OPEN_PAGE;

					if (dailyDeedBean.getRegister() != null) {
						if (referrerURL != null)
							return ViewPathConstants.REDIRECT_URL + referrerURL;
						else
							return ViewPathConstants.SALES_URL;
					}

				} else {
					model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.register.open.error.no.store.opened", null, locale));
					logger.error("There was no STORE OPENING txn found before proceeding with REGISTER OPENING");
				}
			} else {
				model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.register.open.error.no.store", null, locale));
				logger.error("There was some error retrieving store open details for register OPEN SCREEN");
			}
		} catch (Exception e) {
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.register.open.error", null, locale));
			logger.error("There was some error retrieving details needed for REGISTER OPENING", e);
		}

		return ViewPathConstants.REGISTER_OPEN_PAGE;

	}

	private Boolean updateBeansAllActions(DailyDeedBean dailyDeedBean, Model model, Locale locale, HttpSession session, Boolean isOpenRequest) {
		Boolean hasError = Boolean.FALSE;
		if(dailyDeedBean.getDenominationList()==null || dailyDeedBean.getDenominationList().isEmpty()) {
			List<Denomination> denominations = this.commonService.retrieveAllDenominations();
			if (denominations != null) {
				List<BaseDenominationBean> denominationBeans = CommonMVCTransformer.transformDenominations(denominations);
				dailyDeedBean.setDenominationList(denominationBeans);
			}
		}
		
		if(dailyDeedBean.getTenders()==null || dailyDeedBean.getTenders().isEmpty() || isOpenRequest) {
			List<TenderCount> storeOpenTotals = this.dailyDeedService.searchTxnTenderCounts(dailyDeedBean.getLocationId(), null, dailyDeedBean.getBusinessDate(),
					MVCConstants.TXN_OPEN_STORE);
			if (storeOpenTotals != null && !storeOpenTotals.isEmpty()) {
				List<TenderBean> tenders = CommonMVCTransformer.transformTenderCounts(storeOpenTotals);
				dailyDeedBean.setTenders(tenders);
			}
		}
		

		RegisterDTO registerDTO = this.dailyDeedService.retrieveRegisterConcilationDtls(dailyDeedBean.getLocationId(), dailyDeedBean.getBusinessDate(),
				MVCConstants.TXN_OPEN_REGISTER);
		if (registerDTO != null) {
			List<RegisterBean> registers = CommonMVCTransformer.transformRegisterDTO(registerDTO);

			for (RegisterBean registerBean : registers) {
				if ((registerBean.getLastStatus() != null && !registerBean.getLastStatus().equals(MVCConstants.TXN_OPEN_REGISTER))
						|| (StringUtils.isBlank(registerBean.getLastStatus()))) {
					dailyDeedBean.setAllRegOpenedFlag(Boolean.FALSE);
					break;
				} else {
					dailyDeedBean.setAllRegOpenedFlag(Boolean.TRUE);
				}
			}

			if (dailyDeedBean.getAllRegOpenedFlag()) {
				// We need this somehow to proceed to txn flow
				if (registers.size() == 1) {
					dailyDeedBean.setRegister(registers.get(0).getRegisterId());
					dailyDeedBean.setRegisterId(registers.get(0).getRegisterId());
					dailyDeedBean.setRegisterName(registers.get(0).getName());

					session.setAttribute(dailyDeedBean.getLocationId() + MVCConstants.REGISTER_ID_PARAM, dailyDeedBean.getRegisterId());
					session.setAttribute(dailyDeedBean.getLocationId() + MVCConstants.REG_NAME_PARAM, dailyDeedBean.getRegisterName());

					logger.error("The single register is existing with open status, lets proceed to Txn screen");
				} else if (registers.size() > 1) {
					model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.register.open.error.all.regs.open", null, locale));
					logger.error("All the registers are already in OPEN status for location {}.", dailyDeedBean.getLocationId());
					hasError = Boolean.TRUE;
				}

			}

			model.addAttribute(MVCConstants.DAILY_DEED_BEAN, dailyDeedBean);
			model.addAttribute(MVCConstants.REGISTER_BEANS, registers);
			logger.info("All the beans needed for open register screen has been updated in model");

		} else {

			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.register.open.error.no.valid.regs", null, locale));
			logger.error("There are no VALID REGISTERS existing for the provided location {}.", dailyDeedBean.getLocationId());
			hasError = Boolean.TRUE;
		}

		return hasError;

	}

	
	/**
	 * This method is required for request attributes of Open store details
	 * 
	 * @param dailyDeedBean
	 */
	private void updateCommerceContext(DailyDeedBean dailyDeedBean) {
		Integer locationId = dailyDeedBean.getLocationId();
		commerceContext.setStoreSettings(CommerceConstants.OPEN_LOC_ID, locationId);
		commerceContext.setStoreSettings(locationId + "-" + CommerceConstants.OPEN_LOC_NAME, dailyDeedBean.getLocationName());
		commerceContext.setStoreSettings(locationId + "-" + CommerceConstants.OPEN_BUSINESS_DATE, dailyDeedBean.getBusinessDate());
		// Get this from selected location later on
		commerceContext.setStoreSettings(locationId + "-" + CommerceConstants.LOC_DEFAULT_TENDER, dailyDeedBean.getDefaultTender()); // MVCConstants.TNDR_CASH

		logger.info("All the store open details has been updated in commerce app context now");

	}

	private void addDenominationDetails(DailyDeedBean dailyDeedBean, Model model, HttpSession session, Locale locale) {
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
		this.updateBeansAllActions(dailyDeedBean, model, locale, session, Boolean.FALSE);

	}

	@PostMapping(value = ViewPathConstants.REGISTER_OPEN_URL, params = { MVCConstants.ADD_DENOMINATION_PARAM })
	public String addTenderDenominationRegister(@ModelAttribute DailyDeedBean dailyDeedBean, Model model, Locale locale, HttpSession session) {
		logger.info("The add tender denomination has been called");
		try {
			this.addDenominationDetails(dailyDeedBean, model, session, locale);

		} catch (Exception e) {
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.register.open.failure", null, locale));
			logger.error("There was some error while adding a new denomination during register open process", e);
			return ViewPathConstants.REGISTER_OPEN_PAGE;
		}

		return ViewPathConstants.REGISTER_OPEN_PAGE;
	}

	private void deleteDenominationDetails(DailyDeedBean dailyDeedBean, Model model, HttpSession session, Locale locale) {
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

		this.updateBeansAllActions(dailyDeedBean, model, locale, session, Boolean.FALSE);
	}

	@PostMapping(value = ViewPathConstants.REGISTER_OPEN_URL, params = { MVCConstants.REMOVE_DENOMINATION_PARAM })
	public String removeTenderDenominationRegister(@ModelAttribute DailyDeedBean dailyDeedBean, Model model, Locale locale, final HttpServletRequest req,
			HttpSession session) {
		logger.info("The remove tender denomination has been called");
		try {
			this.deleteDenominationDetails(dailyDeedBean, model, session, locale);

		} catch (Exception e) {
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.register.open.failure", null, locale));
			logger.error("There was some error while adding denomination for open register screen", e);
			return ViewPathConstants.REGISTER_OPEN_PAGE;
		}

		return ViewPathConstants.REGISTER_OPEN_PAGE;

	}

	@PostMapping(value = ViewPathConstants.REGISTER_OPEN_URL, params = { MVCConstants.OPEN_REGISTER_PARAM })
	public String processOpenRegisterDetails(@ModelAttribute @Validated(ValidationGroup.ValidationGroupRegOpen.class) DailyDeedBean dailyDeedBean,
			BindingResult bindingResult, Model model, Locale locale, Authentication authentication, HttpSession session) {
		logger.info("The show store open screen method has been called");
		registerOpenValidator.validate(dailyDeedBean, bindingResult);
		logger.info("The price class level validation has been completed successfully");
		if (bindingResult.hasErrors()) {
			this.updateBeansAllActions(dailyDeedBean, model, locale, session, Boolean.FALSE);
			return ViewPathConstants.REGISTER_OPEN_PAGE;
		}
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			if (userDetails != null) {
				DailyTransaction registerOpenDetails = DailyDeedTransformer.transformOpenTxnDetails(dailyDeedBean);
				DailyDeedDTO regTotalsDTO=  this.dailyDeedService.saveRegisterOpenTxn(registerOpenDetails, userDetails.getUsername());
				this.updateBeansAllActions(dailyDeedBean, model, locale, session, Boolean.FALSE);
				if (regTotalsDTO==null) {
					model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.register.open.failure", null, locale));
					logger.info("The Register open process failed due to some unknown issue");
					return ViewPathConstants.REGISTER_OPEN_PAGE;
				}
				session.setAttribute(dailyDeedBean.getLocationId() + MVCConstants.REGISTER_ID_PARAM, dailyDeedBean.getRegisterId());
				session.setAttribute(dailyDeedBean.getLocationId() + MVCConstants.REG_NAME_PARAM, dailyDeedBean.getRegisterName());
				logger.info("The Register open process was successful and register is ready for sale now");
			}
		} catch (Exception e) {
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.register.open.failure", null, locale));
			logger.error("There was some error while opening the register", e);
			this.updateBeansAllActions(dailyDeedBean, model, locale, session, Boolean.FALSE);
			return ViewPathConstants.REGISTER_OPEN_PAGE;
		}
		if (StringUtils.isBlank(dailyDeedBean.getReferrerURL()))
			return ViewPathConstants.REDIRECT_URL + ViewPathConstants.POS_URL;
		else
			return ViewPathConstants.REDIRECT_URL + dailyDeedBean.getReferrerURL();

	}

}
