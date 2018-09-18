package com.punj.app.ecommerce.controller.dailydeeds;
/**
 * 
 */

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.punj.app.ecommerce.common.web.CommerceConstants;
import com.punj.app.ecommerce.common.web.CommerceContext;
import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.CommonMVCTransformer;
import com.punj.app.ecommerce.controller.common.transformer.DailyDeedTransformer;
import com.punj.app.ecommerce.domains.common.Denomination;
import com.punj.app.ecommerce.domains.tender.Tender;
import com.punj.app.ecommerce.models.common.AJAXResponseBean;
import com.punj.app.ecommerce.models.common.BaseDenominationBean;
import com.punj.app.ecommerce.models.common.LocationBean;
import com.punj.app.ecommerce.models.common.validator.ValidationGroup;
import com.punj.app.ecommerce.models.dailydeeds.DailyDeedBean;
import com.punj.app.ecommerce.models.dailydeeds.validator.RegisterOpenValidator;
import com.punj.app.ecommerce.models.tender.DenominationBean;
import com.punj.app.ecommerce.models.tender.TenderBean;
import com.punj.app.ecommerce.services.DailyDeedService;
import com.punj.app.ecommerce.services.TransactionService;
import com.punj.app.ecommerce.services.common.CommonService;
import com.punj.app.ecommerce.services.common.dtos.LocationDTO;
import com.punj.app.ecommerce.services.dtos.DailyTransaction;
import com.punj.app.ecommerce.services.dtos.dailydeeds.LocStatusDTO;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class LocationOpeningController {
	private static final Logger logger = LogManager.getLogger();
	private CommonService commonService;
	private MessageSource messageSource;
	private DailyDeedService dailyDeedService;
	private CommerceContext commerceContext;
	private TransactionService txnService;
	private RegisterOpenValidator registerOpenValidator;

	@Value("${commerce.default.location}")
	private Integer defaultLocation;

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

	@GetMapping(value = ViewPathConstants.STORE_OPEN_URL)
	public String showOpenStore(Model model, Locale locale, HttpServletRequest request) {
		logger.info("The show store open screen method has been called");
		try {
			DailyDeedBean dailyDeedBean = new DailyDeedBean();

			String referrerURL = request.getParameter(MVCConstants.REFERRER_URL_PARAM);
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

			// Change this later on and make it an ajax call based on store selected from store open screen
			List<Tender> tenders = this.commonService.retrieveTendersForReconcilation(this.defaultLocation);
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

		List<Denomination> denominations = this.commonService.retrieveAllDenominations();
		if (denominations != null) {
			List<BaseDenominationBean> denominationBeans = CommonMVCTransformer.transformDenominations(denominations);
			dailyDeedBean.setDenominationList(denominationBeans);
			LocationDTO locationDTO = this.commonService.retrieveLocationWithDailyStatus();
			List<LocationBean> locations = CommonMVCTransformer.transformLocationDTO(locationDTO);

			Integer locationId = dailyDeedBean.getLocationId();
			if (locationId != null) {
				for (LocationBean location : locations) {
					if (location.getLocationId().equals(locationId)) {
						dailyDeedBean.setLocationName(location.getName());
						dailyDeedBean.setGstNo(location.getGstNo());
						dailyDeedBean.setDefaultTender(location.getDefaultTender());
						break;
					}
				}
				logger.info("The location name has been setup correctly");
			}

			model.addAttribute(MVCConstants.DAILY_DEED_BEAN, dailyDeedBean);
			model.addAttribute(MVCConstants.LOCATION_BEANS, locations);
			logger.info("All the beans needs for open store screen has been updated in model");

		} else {
			logger.error("There was some error retrieving denominations for tender");
		}

	}

	private void updateRedirectBeans(DailyDeedBean dailyDeedBean, RedirectAttributes redirectAttrs, HttpServletRequest request) {

		redirectAttrs.addFlashAttribute(MVCConstants.DAILY_DEED_BEAN, dailyDeedBean);
		request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);

		this.updateCommerceContext(dailyDeedBean);

		logger.info("All the beans needs for open store screen has been updated in model");

	}

	private void updateCommerceContext(DailyDeedBean dailyDeedBean) {
		Integer locationId = dailyDeedBean.getLocationId();
		commerceContext.setStoreSettings(CommerceConstants.OPEN_LOC_ID, locationId);
		commerceContext.setStoreSettings(locationId + "-" + CommerceConstants.OPEN_LOC_NAME, dailyDeedBean.getLocationName());
		commerceContext.setStoreSettings(locationId + "-" + CommerceConstants.OPEN_LOC_GST_NO, dailyDeedBean.getGstNo());
		commerceContext.setStoreSettings(locationId + "-" + CommerceConstants.OPEN_BUSINESS_DATE, dailyDeedBean.getBusinessDate());
		// Get this from selected location later on
		commerceContext.setStoreSettings(locationId + "-" + CommerceConstants.LOC_DEFAULT_TENDER, dailyDeedBean.getDefaultTender()); // MVCConstants.TNDR_CASH

		logger.info("All the store open details has been updated in commerce app context now");

	}

	@PostMapping(value = ViewPathConstants.STORE_OPEN_URL, params = { MVCConstants.OPEN_STORE_PARAM })
	public String processOpenStoreDetails(@ModelAttribute @Validated(ValidationGroup.ValidationGroupStoreOpen.class) DailyDeedBean dailyDeedBean, BindingResult bindingResult,
			Model model, Locale locale, Authentication authentication, RedirectAttributes redirectAttrs, HttpServletRequest request, HttpSession session) {
		logger.info("The show store open screen method has been called");
		if (bindingResult.hasErrors()) {
			this.updateBeans(dailyDeedBean, model);
			return ViewPathConstants.STORE_OPEN_PAGE;
		}
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			if (userDetails != null) {
				DailyTransaction storeOpenDetails = DailyDeedTransformer.transformOpenTxnDetails(dailyDeedBean);
				Boolean result = this.dailyDeedService.saveStoreOpenTxn(storeOpenDetails, userDetails.getUsername());
				this.updateBeans(dailyDeedBean, model);
				if (!result) {
					model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.store.open.failure", null, locale));
					logger.info("The Store open process has failed");
					return ViewPathConstants.STORE_OPEN_PAGE;
				} else {
					Integer locationId = dailyDeedBean.getLocationId();
					session.removeAttribute(locationId + MVCConstants.REGISTER_ID_PARAM);
					session.removeAttribute(locationId + MVCConstants.REG_NAME_PARAM);
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
	public String addTenderDenomination(@ModelAttribute DailyDeedBean dailyDeedBean, Model model, Locale locale, Authentication authentication, final HttpServletRequest req) {
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
	
	@GetMapping(value = ViewPathConstants.RESET_STORE_STATUS_URL, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public AJAXResponseBean resetLocationForOpening(@RequestParam("locationId") Integer locationId, @RequestParam("businessDate") LocalDateTime businessDate) {
		AJAXResponseBean response = new AJAXResponseBean();
		response.setStatus(MVCConstants.AJAX_STATUS_FAILURE);
		
		this.dailyDeedService.resetLocationForBusiness(locationId, businessDate);
		response.setStatus(MVCConstants.AJAX_STATUS_SUCCESS);
		logger.info("The location transactions has been reset successfully for {} business date ", businessDate);
		
		return response;
	}
	

	@GetMapping(value = ViewPathConstants.STORE_STATUS_URL, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public AJAXResponseBean lookupLocationStatus(@RequestParam("locationId") Integer locationId, @RequestParam("businessDate") LocalDateTime businessDate) {
		AJAXResponseBean response = new AJAXResponseBean();

		LocStatusDTO locStatusDTO = this.dailyDeedService.isLocationOpenAllowed(locationId, businessDate);
		if(locStatusDTO!=null) {
			response.setStatus(MVCConstants.AJAX_STATUS_SUCCESS);
			response.setResultObj(locStatusDTO);
			logger.info("The location status has been retrieved successfully");
		}
		else {
			response.setStatus(MVCConstants.AJAX_STATUS_FAILURE);
			logger.info("The location status retrieval has failed!!");
		}
		return response;
	}
}
