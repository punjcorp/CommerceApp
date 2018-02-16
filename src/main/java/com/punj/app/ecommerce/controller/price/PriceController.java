package com.punj.app.ecommerce.controller.price;
/**
 * 
 */

import java.math.BigInteger;
import java.util.ArrayList;
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
import com.punj.app.ecommerce.models.price.PriceBeanDTO;
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
			return ViewPathConstants.ADD_PRICE_PAGE;
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
				priceBean = this.updateSuccess(itemPrice, priceBean, model, locale,MVCConstants.ACTION_NEW);
				this.updatePriceBeans(priceBean, model);
				logger.info("The Item Price has been created successfully");
			}
		} catch (Exception e) {
			logger.error("There is an error while saving newly created item price", e);
			this.updatePriceBeans(priceBean, model);
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			return ViewPathConstants.ADD_PRICE_PAGE;
		}
		return ViewPathConstants.ADD_PRICE_PAGE;
	}

	private PriceBean updateSuccess(ItemPrice itemPrice, PriceBean priceBean, Model model, Locale locale, String action) {
		if (itemPrice != null && itemPrice.getItemPriceId() != null) {
			priceBean = PriceTransformer.transformItemPriceDomain(itemPrice);
			BigInteger itemPriceId = priceBean.getItemPriceId();
			if (MVCConstants.STATUS_CREATED.equals(itemPrice.getStatus()) && MVCConstants.ACTION_NEW.equals(action)) {
				logger.info("The {} is newly generated price record", itemPriceId);
				model.addAttribute(MVCConstants.SUCCESS,
						this.messageSource.getMessage("commerce.screen.price.add.success", new Object[] { itemPriceId }, locale));
			} else if (MVCConstants.STATUS_CREATED.equals(itemPrice.getStatus()) && MVCConstants.ACTION_EDIT.equals(action)) {
				logger.info("The {} item price record has been modified", itemPriceId);
				model.addAttribute(MVCConstants.SUCCESS,
						this.messageSource.getMessage("commerce.screen.price.edit.success", new Object[] { itemPriceId }, locale));
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
				priceBean = this.updateSuccess(itemPrice, priceBean, model, locale,MVCConstants.ACTION_NEW);
				this.updatePriceBeans(priceBean, model);
				logger.info("The Item Price has been saved and approved successfully");
			}
		} catch (Exception e) {
			logger.error("There is an error while approving item price", e);
			this.updatePriceBeans(priceBean, model);
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			return ViewPathConstants.ADD_PRICE_PAGE;
		}
		return ViewPathConstants.ADD_PRICE_PAGE;
	}
	

	@GetMapping(ViewPathConstants.APPROVE_PRICE_URL)
	public String approvePrice(@ModelAttribute PriceBeanDTO priceBeanDTO, BindingResult bindingResult, Model model, final HttpServletRequest req, Locale locale, Authentication authentication) {
		if (bindingResult.hasErrors()) {
			this.updatePriceBeanDTO(priceBeanDTO, model);
			return ViewPathConstants.MANAGE_PRICE_PAGE;
		}
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			if (userDetails != null) {		
				this.executePriceAction(req, MVCConstants.APPROVE_PRICE_PARAM, model, priceBeanDTO, locale,userDetails.getUsername());
			}
		} catch (Exception e) {
			logger.error("There is an error while approving inventory adjustment", e);
			this.updatePriceBeanDTO(priceBeanDTO, model);
			return ViewPathConstants.MANAGE_PRICE_PAGE;
		}
		return ViewPathConstants.MANAGE_PRICE_PAGE;
	}

	@GetMapping(ViewPathConstants.DELETE_PRICE_URL)
	public String deletePrice(@ModelAttribute PriceBeanDTO priceBeanDTO, BindingResult bindingResult, Model model, final HttpServletRequest req, Locale locale, Authentication authentication) {
		if (bindingResult.hasErrors()) {
			this.updatePriceBeanDTO(priceBeanDTO, model);
			return ViewPathConstants.MANAGE_PRICE_PAGE;
		}
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			if (userDetails != null) {		
				this.executePriceAction(req, MVCConstants.DELETE_PRICE_PARAM, model, priceBeanDTO, locale,userDetails.getUsername());
			}
		} catch (Exception e) {
			logger.error("There is an error while approving inventory adjustment", e);
			this.updatePriceBeanDTO(priceBeanDTO, model);
			return ViewPathConstants.MANAGE_PRICE_PAGE;
		}
		return ViewPathConstants.MANAGE_PRICE_PAGE;
	}
	
	private void executePriceAction(final HttpServletRequest req, String action, Model model, PriceBeanDTO priceBeanDTO, Locale locale, String username) {

		BigInteger itemPriceId = new BigInteger(req.getParameter(MVCConstants.PRICE_ID_PARAM));
		Boolean result = Boolean.FALSE;
		if (action.equals(MVCConstants.APPROVE_PRICE_PARAM)) {
			result = this.priceService.approvePrice(itemPriceId, username);
		} else if (action.equals(MVCConstants.DELETE_PRICE_PARAM)) {
			result = this.priceService.deletePrice(itemPriceId, username);
		}
		if (result) {
			model.addAttribute(MVCConstants.PRICE_BEAN_DTO, priceBeanDTO);
			if (action.equals(MVCConstants.APPROVE_PRICE_PARAM)) {
				model.addAttribute(MVCConstants.SUCCESS,
						messageSource.getMessage("commerce.screen.common.approve.success", null, locale));
				logger.info("The selected item price has been approved successfully");
			}else if(action.equals(MVCConstants.DELETE_PRICE_PARAM)) {
				model.addAttribute(MVCConstants.SUCCESS,
						messageSource.getMessage("commerce.screen.common.delete.success", null, locale));
				logger.info("The selected item price has been deleted successfully");				
			}
		}else {
			if (action.equals(MVCConstants.APPROVE_PRICE_PARAM)) {
				model.addAttribute(MVCConstants.ALERT,
						messageSource.getMessage("commerce.screen.common.approve.failure", null, locale));
				logger.info("The selected item price approval has failed");				
			}else if(action.equals(MVCConstants.DELETE_PRICE_PARAM)) {
				model.addAttribute(MVCConstants.ALERT,
						messageSource.getMessage("commerce.screen.common.delete.failure", null, locale));
				logger.info("The selected item price deletion has failed");								
			}
		}
		this.updatePriceBeanDTO(priceBeanDTO, model);

	}
	
	/**
	 * This method avoids the duplicate code for price bean settings and retrieving locations and making it available for UI use
	 * 
	 * @param searchBean
	 * @param priceBeanDTO
	 * @param model
	 */
	private void updatePriceBeanDTO(PriceBeanDTO priceBeanDTO, Model model) {
		List<Location> locationList = this.commonService.retrieveAllLocations();
		this.updateLocations(priceBeanDTO, locationList);
		SearchBean searchBean = new SearchBean();
		model.addAttribute(MVCConstants.SEARCH_BEAN, searchBean);
		model.addAttribute(MVCConstants.PRICE_BEAN_DTO, priceBeanDTO);
		logger.info("All the needed beans for price management screens has been updated in the model");
	}	
	
	/**
	 * This method is used to update the Location details in bean object
	 * 
	 * @param invAdjustBean
	 * @param locationList
	 */
	private void updateLocations(PriceBeanDTO priceBeanDTO, List<Location> locationList) {
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
			priceBeanDTO.setLocations(locations);
		}
		logger.info("The location details has been set in Price Bean DTO object");
	}

	
	@GetMapping(value = ViewPathConstants.EDIT_PRICE_URL)
	public String showPrice(Model model, final HttpServletRequest req, Locale locale) {
		logger.info("The edit method for item price has been called");
		try {
			BigInteger itemPriceId = new BigInteger(req.getParameter(MVCConstants.PRICE_ID_PARAM));
			ItemPrice itemPrice= this.priceService.searchPrice(itemPriceId);
			PriceBean priceBean = PriceTransformer.transformItemPriceDomain(itemPrice);
			this.updatePriceBeans(priceBean, model);
			logger.info("The provided item price has been retrieved from database for modification");
		} catch (Exception e) {
			logger.error("An unknown error has occurred while showing item price for modification.", e);
			return ViewPathConstants.EDIT_PRICE_PAGE;
		}
		return ViewPathConstants.EDIT_PRICE_PAGE;
	}

	@PostMapping(value = ViewPathConstants.EDIT_PRICE_URL, params = { MVCConstants.SAVE_PRICE_PARAM })
	public String savePriceDetailsAfterEdit(@ModelAttribute @Valid PriceBean priceBean, BindingResult bindingResult, Model model, Locale locale,
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
				priceBean = this.updateSuccess(itemPrice, priceBean, model, locale,MVCConstants.ACTION_EDIT);
				this.updatePriceBeans(priceBean, model);
				logger.info("The Item Price has been modified successfully");
			}
		} catch (Exception e) {
			logger.error("There is an error while saving modifieditem price", e);
			this.updatePriceBeans(priceBean, model);
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			return ViewPathConstants.EDIT_PRICE_PAGE;
		}
		return ViewPathConstants.EDIT_PRICE_PAGE;
	}
	
	@PostMapping(value = ViewPathConstants.EDIT_PRICE_URL, params = { MVCConstants.APPROVE_PRICE_PARAM })
	public String approvePriceDetailsAfterEdit(@ModelAttribute @Valid PriceBean priceBean, BindingResult bindingResult, Model model, Locale locale,
			Authentication authentication) {
		if (bindingResult.hasErrors()) {
			this.updatePriceBeans(priceBean, model);
			return ViewPathConstants.EDIT_PRICE_PAGE;
		}
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			if (userDetails != null) {
				ItemPrice itemPrice = PriceTransformer.transformPriceBean(priceBean);
				itemPrice = PriceTransformer.updatePrice(itemPrice, userDetails.getUsername());
				itemPrice = this.priceService.saveAndApproveItemPrice(itemPrice);
				priceBean = this.updateSuccess(itemPrice, priceBean, model, locale,MVCConstants.ACTION_EDIT);
				this.updatePriceBeans(priceBean, model);
				logger.info("The Item Price has been saved and approved after modification successfully");
			}
		} catch (Exception e) {
			logger.error("There is an error while approving item price after modificatio", e);
			this.updatePriceBeans(priceBean, model);
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			return ViewPathConstants.EDIT_PRICE_PAGE;
		}
		return ViewPathConstants.EDIT_PRICE_PAGE;
	}
		
	
}
