package com.punj.app.ecommerce.controller.price;
/**
 * 
 */

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.PriceTransformer;
import com.punj.app.ecommerce.domains.common.Location;
import com.punj.app.ecommerce.domains.price.ItemPrice;
import com.punj.app.ecommerce.models.common.LocationBean;
import com.punj.app.ecommerce.models.common.SearchBean;
import com.punj.app.ecommerce.models.price.PriceBean;
import com.punj.app.ecommerce.services.PriceService;
import com.punj.app.ecommerce.services.common.CommonService;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class PriceController {
	private static final Logger logger = LogManager.getLogger();
	private PriceService priceService;
	private CommonService commonService;
	private MessageSource messageSource;

	/**
	 * @param priceService
	 *            the priceService to set
	 */
	@Autowired
	public void setPriceService(PriceService priceService) {
		this.priceService = priceService;
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

	@GetMapping(value = ViewPathConstants.ADD_PRICE_URL)
	public String showPriceCreationScreen(Model model, Authentication authentication) {
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			if (userDetails != null) {

				PriceBean priceBean = new PriceBean();
				this.updatePriceBeans(priceBean, model);
				logger.info("The Price creation screen is ready for display");
			}
		} catch (Exception e) {
			logger.error("There is an error while creating an empty price", e);
			return ViewPathConstants.ERROR_PAGE;
		}
		return ViewPathConstants.ADD_PRICE_PAGE;
	}

	/**
	 * This method avoids the duplicate code for price bean settings and retrieving locations and making it available for UI use
	 * 
	 * @param invAdjustBean
	 * @param model
	 */
	private void updatePriceBeans(PriceBean priceBean, Model model) {
		List<Location> locationList = this.commonService.retrieveAllLocations();
		this.updateLocations(priceBean, locationList);
		SearchBean searchBean = new SearchBean();
		model.addAttribute(MVCConstants.SEARCH_BEAN, searchBean);
		model.addAttribute(MVCConstants.PRICE_BEAN, priceBean);
		logger.info("All the needed beans for price screens has been updated in the model");
	}

	/**
	 * This method is used to update the Location details in bean object
	 * 
	 * @param invAdjustBean
	 * @param locationList
	 */
	private void updateLocations(PriceBean priceBean, List<Location> locationList) {
		LocationBean locationBean = null;
		List<LocationBean> locations = null;
		if (locationList != null && !locationList.isEmpty()) {
			locations = new ArrayList<>(locationList.size());
			for (Location location : locationList) {
				locationBean = new LocationBean();
				locationBean.setLocationId(location.getLocationId());
				locationBean.setLocationType(location.getStatus());
				locationBean.setName(location.getName());
				locations.add(locationBean);
			}
			priceBean.setLocations(locations);
		}
		logger.info("The location details has been set in Price Bean object");
	}

	@PostMapping(value = ViewPathConstants.ADD_PRICE_URL, params = { MVCConstants.SAVE_PRICE_PARAM })
	public String savePriceDetails(@ModelAttribute @Valid PriceBean priceBean, BindingResult bindingResult, Model model, Locale locale,
			Authentication authentication) {
		if (bindingResult.hasErrors()) {
			this.updatePriceBeans(priceBean, model);
			return ViewPathConstants.ADD_PRICE_PAGE;
		}
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			if (userDetails != null) {
				ItemPrice itemPrice = PriceTransformer.transformPriceBean(priceBean);
				itemPrice = PriceTransformer.updatePrice(itemPrice, userDetails.getUsername());
				itemPrice = this.priceService.createItemPrice(itemPrice);
				priceBean = this.updateSuccess(itemPrice, priceBean, model, locale);
				this.updatePriceBeans(priceBean, model);
				logger.info("The Item Price has been created successfully");
			}
		} catch (Exception e) {
			logger.error("There is an error while saving newly created item price", e);
			this.updatePriceBeans(priceBean, model);
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.common.error", null, locale));
			return ViewPathConstants.ADD_PRICE_PAGE;
		}
		return ViewPathConstants.ADD_PRICE_PAGE;
	}

	private PriceBean updateSuccess(ItemPrice itemPrice, PriceBean priceBean, Model model, Locale locale) {
		if (itemPrice != null && itemPrice.getItemPriceId() != null) {
			priceBean = PriceTransformer.transformItemPriceDomain(itemPrice);
			BigInteger itemPriceId = priceBean.getItemPriceId();
			if (MVCConstants.STATUS_CREATED.equals(itemPrice.getStatus())) {
				logger.info("The {} is newly generated price record", itemPriceId);
				model.addAttribute(MVCConstants.SUCCESS,
						this.messageSource.getMessage("commerce.screen.price.add.success", new Object[] { itemPriceId }, locale));
			} else if (MVCConstants.STATUS_APPROVED.equals(itemPrice.getStatus())) {
				logger.info("The {} is approved price record", itemPriceId);
				model.addAttribute(MVCConstants.SUCCESS,
						this.messageSource.getMessage("commerce.screen.price.approve.success", new Object[] { itemPriceId }, locale));
			} else {
				model.addAttribute(MVCConstants.ALERT,
						this.messageSource.getMessage("commerce.screen.price.approve.failure", new Object[] { itemPriceId }, locale));
			}
		} else {
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.price.add.failure", null, locale));
		}
		return priceBean;
	}

	@PostMapping(value = ViewPathConstants.ADD_PRICE_URL, params = { MVCConstants.APPROVE_PRICE_PARAM })
	public String saveAndApprovePriceDetails(@ModelAttribute @Valid PriceBean priceBean, BindingResult bindingResult, Model model, Locale locale,
			Authentication authentication) {
		if (bindingResult.hasErrors()) {
			this.updatePriceBeans(priceBean, model);
			return ViewPathConstants.ADD_PRICE_PAGE;
		}
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			if (userDetails != null) {
				ItemPrice itemPrice = PriceTransformer.transformPriceBean(priceBean);
				itemPrice = PriceTransformer.updatePrice(itemPrice, userDetails.getUsername());
				itemPrice = this.priceService.saveAndApproveItemPrice(itemPrice);
				priceBean = this.updateSuccess(itemPrice, priceBean, model, locale);
				this.updatePriceBeans(priceBean, model);
				logger.info("The Item Price has been saved and approved successfully");
			}
		} catch (Exception e) {
			logger.error("There is an error while approving item price", e);
			this.updatePriceBeans(priceBean, model);
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.common.error", null, locale));
			return ViewPathConstants.ADD_PRICE_PAGE;
		}
		return ViewPathConstants.ADD_PRICE_PAGE;
	}
	
	
}
