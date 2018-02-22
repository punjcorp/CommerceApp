package com.punj.app.ecommerce.controller.dailydeeds;
/**
 * 
 */

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.CommonMVCTransformer;
import com.punj.app.ecommerce.domains.common.Location;
import com.punj.app.ecommerce.models.common.LocationBean;
import com.punj.app.ecommerce.models.dailydeeds.DailyDeedBean;
import com.punj.app.ecommerce.models.tender.DenominationBean;
import com.punj.app.ecommerce.models.tender.TenderBean;
import com.punj.app.ecommerce.services.common.CommonService;

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

	@GetMapping(value = ViewPathConstants.STORE_OPEN_URL)
	public String showOpenStore(Model model, Locale locale) {
		logger.info("The show store open screen method has been called");
		try {
			DailyDeedBean dailyDeedBean = new DailyDeedBean();
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
		List<Location> locationList = this.commonService.retrieveAllLocations();
		List<LocationBean> locations = CommonMVCTransformer.transformLocationList(locationList, MVCConstants.LOC_FULL);

		model.addAttribute(MVCConstants.DAILY_DEED_BEAN, dailyDeedBean);
		model.addAttribute(MVCConstants.LOCATION_BEANS, locations);
		logger.info("All the beans needs for open store screen has been updated in model");

	}

	@PostMapping(value = ViewPathConstants.STORE_OPEN_URL, params = { MVCConstants.OPEN_STORE_PARAM })
	public String processOpenStoreDetails(@ModelAttribute @Valid DailyDeedBean dailyDeedBean, BindingResult bindingResult, Model model, Locale locale,
			Authentication authentication) {
		logger.info("The show store open screen method has been called");
		try {
			this.updateBeans(dailyDeedBean, model);
			logger.info("The Store open process was successfuland store has been opened now");
		} catch (Exception e) {
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.store.open.failure", null, locale));
			logger.error("There was some error while opening the store", e);
			return ViewPathConstants.STORE_OPEN_PAGE;
		}

		return ViewPathConstants.STORE_OPEN_PAGE;

	}

	@PostMapping(value = ViewPathConstants.STORE_OPEN_URL, params = { MVCConstants.ADD_DENOMINATION_PARAM })
	public String addTenderDenomination(@ModelAttribute @Valid DailyDeedBean dailyDeedBean, BindingResult bindingResult, Model model, Locale locale,
			Authentication authentication, final HttpServletRequest req) {
		logger.info("The add tender denomination has been called");
		if (bindingResult.hasErrors()) {
			this.updateBeans(dailyDeedBean, model);
			return ViewPathConstants.STORE_OPEN_PAGE;
		}
		try {
			final Integer tenderId = Integer.parseInt(req.getParameter(MVCConstants.TENDER_ID_PARAM));

			List<TenderBean> tenders = dailyDeedBean.getTenders();
			TenderBean tender = tenders.get(tenderId);

			List<DenominationBean> denominations = tender.getDenominations();
			denominations.add(new DenominationBean());

			this.updateBeans(dailyDeedBean, model);
			logger.info("A new denomination record has been added to the daily deed bean successfully");
		} catch (Exception e) {
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.store.open.failure", null, locale));
			logger.error("There was some error while adding a new denomination during store open process", e);
			return ViewPathConstants.STORE_OPEN_PAGE;
		}

		return ViewPathConstants.STORE_OPEN_PAGE;

	}

	@PostMapping(value = ViewPathConstants.STORE_OPEN_URL, params = { MVCConstants.REMOVE_DENOMINATION_PARAM })
	public String removeTenderDenomination(@ModelAttribute @Valid DailyDeedBean dailyDeedBean, BindingResult bindingResult, Model model, Locale locale, final HttpServletRequest req) {
		logger.info("The remove tender denomination has been called");
		if (bindingResult.hasErrors()) {
			this.updateBeans(dailyDeedBean, model);
			return ViewPathConstants.STORE_OPEN_PAGE;
		}
		try {
			final Integer tenderId = Integer.parseInt(req.getParameter(MVCConstants.TENDER_ID_PARAM));
			final Integer rowId = Integer.parseInt(req.getParameter(MVCConstants.ID_PARAM));
			
			List<TenderBean> tenders = dailyDeedBean.getTenders();
			TenderBean tender = tenders.get(tenderId);
			List<DenominationBean> denominations = tender.getDenominations();
			denominations.remove(rowId.intValue());
			
			tender.setDenominations(denominations);
			
			
			this.updateBeans(dailyDeedBean, model);
			logger.info("The selected denomination has been removed successfully");
		} catch (Exception e) {
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.store.open.failure", null, locale));
			logger.error("There was some error while opening the store", e);
			return ViewPathConstants.STORE_OPEN_PAGE;
		}

		return ViewPathConstants.STORE_OPEN_PAGE;

	}

}
