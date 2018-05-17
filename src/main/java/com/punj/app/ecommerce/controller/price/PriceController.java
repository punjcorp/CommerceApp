package com.punj.app.ecommerce.controller.price;
/**
 * 
 */

import java.math.BigInteger;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.CommonMVCTransformer;
import com.punj.app.ecommerce.controller.common.transformer.PriceTransformer;
import com.punj.app.ecommerce.domains.common.Location;
import com.punj.app.ecommerce.domains.price.ItemPrice;
import com.punj.app.ecommerce.models.common.LocationBean;
import com.punj.app.ecommerce.models.common.SearchBean;
import com.punj.app.ecommerce.models.price.PriceBean;
import com.punj.app.ecommerce.models.price.PriceBeanDTO;
import com.punj.app.ecommerce.models.price.PriceBeanValidator;
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
	private PriceBeanValidator priceValidator;

	
	/**
	 * @param priceValidator
	 *            the priceValidator to set
	 */
	@Autowired
	public void setPriceBeanValidator(PriceBeanValidator priceValidator) {
		this.priceValidator = priceValidator;
	}	
	
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
				this.updatePriceBeans(priceBean, model, MVCConstants.ACTION_NEW);
				logger.info("The Price creation screen is ready for display");
			}
		} catch (Exception e) {
			logger.error("There is an error while creating an empty price", e);
			return ViewPathConstants.PRICE_DETAIL_PAGE;
		}
		return ViewPathConstants.PRICE_DETAIL_PAGE;
	}

	@PostMapping(value = ViewPathConstants.ADD_PRICE_URL, params = { MVCConstants.SAVE_PRICE_PARAM })
	public String savePriceDetails(@ModelAttribute @Valid PriceBean priceBean, BindingResult bindingResult, Model model, Locale locale,
			Authentication authentication) {
		return this.actionProcessing(priceBean, bindingResult, model, locale, authentication, MVCConstants.ACTION_NEW_SAVE);
	}

	@PostMapping(value = ViewPathConstants.ADD_PRICE_URL, params = { MVCConstants.APPROVE_PRICE_PARAM })
	public String saveAndApprovePriceDetails(@ModelAttribute @Valid PriceBean priceBean, BindingResult bindingResult, Model model, Locale locale,
			Authentication authentication) {
		return this.actionProcessing(priceBean, bindingResult, model, locale, authentication, MVCConstants.ACTION_NEW_APPROVE);
	}

	@GetMapping(value = ViewPathConstants.EDIT_PRICE_URL)
	public String showPrice(Model model, final HttpServletRequest req, Locale locale) {
		logger.info("The edit method for item price has been called");
		try {
			BigInteger itemPriceId = new BigInteger(req.getParameter(MVCConstants.PRICE_ID_PARAM));
			ItemPrice itemPrice = this.priceService.searchPrice(itemPriceId);
			if(itemPrice!=null) {
				PriceBean priceBean = PriceTransformer.transformItemPriceDomain(itemPrice);
				this.updatePriceBeans(priceBean, model, MVCConstants.ACTION_EDIT);
				logger.info("The provided item price has been retrieved from database for modification");
			}else {
				logger.info("There was no item price existing for retrieval");
			}
			
		} catch (Exception e) {
			logger.error("An unknown error has occurred while showing item price for modification.", e);
			return ViewPathConstants.PRICE_DETAIL_PAGE;
		}
		return ViewPathConstants.PRICE_DETAIL_PAGE;
	}

	@PostMapping(value = ViewPathConstants.EDIT_PRICE_URL, params = { MVCConstants.SAVE_PRICE_PARAM })
	public String savePriceDetailsAfterEdit(@ModelAttribute @Valid PriceBean priceBean, BindingResult bindingResult, Model model, Locale locale,
			Authentication authentication) {
		return this.actionProcessing(priceBean, bindingResult, model, locale, authentication, MVCConstants.ACTION_EDIT_SAVE);
	}

	@PostMapping(value = ViewPathConstants.EDIT_PRICE_URL, params = { MVCConstants.APPROVE_PRICE_PARAM })
	public String approvePriceDetailsAfterEdit(@ModelAttribute @Valid PriceBean priceBean, BindingResult bindingResult, Model model, Locale locale,
			Authentication authentication) {
		return this.actionProcessing(priceBean, bindingResult, model, locale, authentication, MVCConstants.ACTION_EDIT_APPROVE);
	}

	/**
	 * This method avoids the duplicate code for price bean settings and retrieving locations and making it available for UI use
	 * 
	 * @param invAdjustBean
	 * @param model
	 */
	private void updatePriceBeans(PriceBean priceBean, Model model, String action) {
		
		if(priceBean.getClearanceResetId()!=null) {
			ItemPrice clearancePrice = this.priceService.searchPrice(priceBean.getClearanceResetId());
			PriceBean clearancePriceBean = null;
			if(clearancePrice!=null) {
				clearancePriceBean = PriceTransformer.transformItemPriceDomain(clearancePrice);
				priceBean.setExistingClearance(clearancePriceBean);
				logger.info("The clearance has been retrieved successfully");
			}
		}		
		
		// This has to come from cache
		List<Location> locationList = this.commonService.retrieveAllLocations();
		this.updateLocations(priceBean, locationList);
		SearchBean searchBean = new SearchBean();
		model.addAttribute(MVCConstants.SEARCH_BEAN, searchBean);
		model.addAttribute(MVCConstants.PRICE_BEAN, priceBean);
		model.addAttribute(MVCConstants.ACTION_PARAM, action);
		logger.info("All the needed beans for price screens has been updated in the model");
	}

	/**
	 * This method is used to update the Location details in bean object
	 * 
	 * @param invAdjustBean
	 * @param locationList
	 */
	private void updateLocations(PriceBean priceBean, List<Location> locationList) {
		List<LocationBean> locations = null;
		if (locationList != null && !locationList.isEmpty()) {
			locations = CommonMVCTransformer.transformLocationList(locationList, MVCConstants.LOC_PARTIAL);
			priceBean.setLocations(locations);
		}
		logger.info("The location details has been set in Price Bean object");
	}

	public String actionProcessing(PriceBean priceBean, BindingResult bindingResult, Model model, Locale locale, Authentication authentication, String action) {
		Boolean status = Boolean.FALSE;
		
		priceValidator.validate(priceBean, bindingResult);
		logger.info("The price class level validation has been completed successfully");
		
		if (bindingResult.hasErrors()) {
			this.updatePriceBeans(priceBean, model, action);
			return ViewPathConstants.PRICE_DETAIL_PAGE;
		}
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			if (userDetails != null) {
				ItemPrice itemPrice = PriceTransformer.transformPriceBean(priceBean);
				itemPrice = PriceTransformer.updatePrice(itemPrice, userDetails.getUsername(), action);
				if (priceBean.getClearanceResetId() != null) {
					if (MVCConstants.ACTION_NEW_SAVE.equals(action) || MVCConstants.ACTION_EDIT_SAVE.equals(action)) {
						itemPrice = this.priceService.createClearanceReset(itemPrice, userDetails.getUsername());
					} else if (MVCConstants.ACTION_NEW_APPROVE.equals(action) || MVCConstants.ACTION_EDIT_APPROVE.equals(action)) {
						itemPrice = this.priceService.approveClearanceReset(itemPrice, userDetails.getUsername());
					}
				} else {
					if (MVCConstants.ACTION_NEW_SAVE.equals(action) || MVCConstants.ACTION_EDIT_SAVE.equals(action)) {
						itemPrice = this.priceService.createItemPrice(itemPrice);
					} else if (MVCConstants.ACTION_NEW_APPROVE.equals(action) || MVCConstants.ACTION_EDIT_APPROVE.equals(action)) {
						itemPrice = this.priceService.saveAndApproveItemPrice(itemPrice);
					}
				}
				status = Boolean.TRUE;
				priceBean = this.updateSuccess(itemPrice, model, locale, action, status);
				this.updatePriceBeans(priceBean, model, action);
				logger.info("The Item Price action has been completed successfully");
			}
		} catch (Exception e) {
			logger.error("There is an error while saving newly created item price", e);
			this.updatePriceBeans(priceBean, model, action);
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			return ViewPathConstants.PRICE_DETAIL_PAGE;
		}
		if (!status) {
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.common.failure", null, locale));
		}
		return ViewPathConstants.PRICE_DETAIL_PAGE;

	}

	private PriceBean updateSuccess(ItemPrice itemPrice, Model model, Locale locale, String action, Boolean status) {
		PriceBean priceBean = PriceTransformer.transformItemPriceDomain(itemPrice);
		BigInteger itemPriceId = priceBean.getItemPriceId();
		if (MVCConstants.ACTION_NEW_SAVE.equals(action) && status) {
			logger.info("The {} is newly generated price record", itemPriceId);
			model.addAttribute(MVCConstants.SUCCESS, this.messageSource.getMessage("commerce.screen.price.add.success", new Object[] { itemPriceId }, locale));
		} else if (MVCConstants.ACTION_EDIT_SAVE.equals(action) && status) {
			logger.info("The {} item price record has been modified", itemPriceId);
			model.addAttribute(MVCConstants.SUCCESS, this.messageSource.getMessage("commerce.screen.price.edit.success", new Object[] { itemPriceId }, locale));
		} else if ((MVCConstants.ACTION_EDIT_APPROVE.equals(action) || MVCConstants.ACTION_NEW_APPROVE.equals(action)) && status) {
			logger.info("The {} is approved price record", itemPriceId);
			model.addAttribute(MVCConstants.SUCCESS,
					this.messageSource.getMessage("commerce.screen.price.approve.success", new Object[] { itemPriceId }, locale));
		} else {
			logger.warn("There seems to an error for price action");
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.common.failure", null, locale));
		}
		return priceBean;
	}

	@GetMapping(ViewPathConstants.APPROVE_PRICE_URL)
	public String approvePrice(@ModelAttribute PriceBeanDTO priceBeanDTO, BindingResult bindingResult, Model model, final HttpServletRequest req, Locale locale,
			Authentication authentication) {
		return this.executePriceAction(req, MVCConstants.APPROVE_PRICE_PARAM, model, priceBeanDTO, bindingResult, authentication, locale);
	}

	@GetMapping(ViewPathConstants.DELETE_PRICE_URL)
	public String deletePrice(@ModelAttribute PriceBeanDTO priceBeanDTO, BindingResult bindingResult, Model model, final HttpServletRequest req, Locale locale,
			Authentication authentication) {
		return this.executePriceAction(req, MVCConstants.DELETE_PRICE_PARAM, model, priceBeanDTO, bindingResult, authentication, locale);
	}

	private String executePriceAction(final HttpServletRequest req, String action, Model model, PriceBeanDTO priceBeanDTO, BindingResult bindingResult,
			Authentication authentication, Locale locale) {
		if (bindingResult.hasErrors()) {
			this.updatePriceBeanDTO(priceBeanDTO, model);
			return ViewPathConstants.MANAGE_PRICE_PAGE;
		}
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			if (userDetails != null) {
				String username = userDetails.getUsername();

				BigInteger itemPriceId = new BigInteger(req.getParameter(MVCConstants.PRICE_ID_PARAM));
				Boolean result = Boolean.FALSE;
				if (action.equals(MVCConstants.APPROVE_PRICE_PARAM)) {
					result = this.priceService.approvePrice(itemPriceId, username);
				} else if (action.equals(MVCConstants.DELETE_PRICE_PARAM)) {
					result = this.priceService.deletePrice(itemPriceId, username);
				}
				this.updateManagePageActionStaus(model, action, result, priceBeanDTO, locale);

				this.updatePriceBeanDTO(priceBeanDTO, model);
				logger.info("The price management page action has been completed");
			}
		} catch (Exception e) {
			logger.error("There is an error while executing price action from management page", e);
			this.updatePriceBeanDTO(priceBeanDTO, model);
			return ViewPathConstants.MANAGE_PRICE_PAGE;
		}
		return ViewPathConstants.MANAGE_PRICE_PAGE;

	}

	private void updateManagePageActionStaus(Model model, String action, Boolean result, PriceBeanDTO priceBeanDTO, Locale locale) {
		if (result) {
			model.addAttribute(MVCConstants.PRICE_BEAN_DTO, priceBeanDTO);
			if (action.equals(MVCConstants.APPROVE_PRICE_PARAM)) {
				model.addAttribute(MVCConstants.SUCCESS, messageSource.getMessage("commerce.screen.common.approve.success", null, locale));
				logger.info("The selected item price has been approved successfully");
			} else if (action.equals(MVCConstants.DELETE_PRICE_PARAM)) {
				model.addAttribute(MVCConstants.SUCCESS, messageSource.getMessage("commerce.screen.common.delete.success", null, locale));
				logger.info("The selected item price has been deleted successfully");
			}
		} else {
			if (action.equals(MVCConstants.APPROVE_PRICE_PARAM)) {
				model.addAttribute(MVCConstants.ALERT, messageSource.getMessage("commerce.screen.common.approve.failure", null, locale));
				logger.info("The selected item price approval has failed");
			} else if (action.equals(MVCConstants.DELETE_PRICE_PARAM)) {
				model.addAttribute(MVCConstants.ALERT, messageSource.getMessage("commerce.screen.common.delete.failure", null, locale));
				logger.info("The selected item price deletion has failed");
			}
		}

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
		List<LocationBean> locations = null;
		if (locationList != null && !locationList.isEmpty()) {
			locations = CommonMVCTransformer.transformLocationList(locationList, MVCConstants.LOC_PARTIAL);
			priceBeanDTO.setLocations(locations);
		}
		logger.info("The location details has been set in Price Bean DTO object");
	}

	@PostMapping(value = ViewPathConstants.GET_OLDEST_CLEARANCE_URL, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public PriceBean lookupOrderItems(@RequestBody PriceBean priceBean, Model model) {
		PriceBean clearanceBean = null;
		try {
			ItemPrice clearanceRcd = this.priceService.getOldestClearance(priceBean.getItemId(), priceBean.getLocationId());
			if (clearanceRcd != null) {
				clearanceBean = PriceTransformer.transformItemPriceDomain(clearanceRcd);
				logger.info("The clearance record has been retrieved successfully to be reset");
			} else {
				logger.info("There is no clearance existing for this item and location yet!!");
			}

		} catch (Exception e) {
			logger.error("There is an error while retrieving oldest clearance for provided item and location", e);
			return clearanceBean;
		}
		return clearanceBean;

	}

}
