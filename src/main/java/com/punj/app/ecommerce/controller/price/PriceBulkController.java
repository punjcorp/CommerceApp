package com.punj.app.ecommerce.controller.price;
/**
 * 
 */

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.View;

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
import com.punj.app.ecommerce.services.PriceService;
import com.punj.app.ecommerce.services.common.CommonService;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class PriceBulkController {
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

	@PostMapping(value = ViewPathConstants.BULK_PRICE_URL, params = { MVCConstants.APPROVE_PARAM })
	public String approvePrices(@ModelAttribute PriceBeanDTO priceBeanDTO, BindingResult bindingResult, Model model, Locale locale,
			Authentication authentication) {
		if (bindingResult.hasErrors()) {
			this.updatePriceBeanDTO(priceBeanDTO, model);
			return ViewPathConstants.MANAGE_PRICE_PAGE;
		}
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			if (userDetails != null) {

				List<PriceBean> priceBeanList = priceBeanDTO.getPriceBeans();
				Map<BigInteger, Integer> idIndex = CommonMVCTransformer.transformSelectedIds(priceBeanDTO.getItemPriceIds());

				List<PriceBean> finalPriceBeans = new ArrayList<>(idIndex.size());

				Set<BigInteger> ids = idIndex.keySet();
				Integer index = null;
				PriceBean priceBean = null;
				for (BigInteger id : ids) {
					index = idIndex.get(id);
					priceBean = priceBeanList.get(index);
					if (priceBean.getStatus().equals(MVCConstants.STATUS_CREATED)) {
						priceBean.setItemPriceId(id);
						this.updatePriceBeanForApproval(priceBean, userDetails.getUsername());
						finalPriceBeans.add(priceBean);
					}
				}
				logger.info("The selected list of item prices has been finalized for processing");

				List<ItemPrice> itemPriceList = PriceTransformer.transformPriceBeanList(finalPriceBeans);
				itemPriceList=this.priceService.approveItemPriceList(itemPriceList);
				this.updateApproveStatus(model, itemPriceList, locale);
				this.updatePriceBeanDTO(priceBeanDTO, model); 
				logger.info("The price approval method processing has been completed");				
			}

		} catch (Exception e) {
			logger.error("There is an error while approving selected item prices", e);
			this.updatePriceBeanDTO(priceBeanDTO, model);
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.common.error", null, locale));
			return ViewPathConstants.MANAGE_PRICE_PAGE;
		}
		return ViewPathConstants.MANAGE_PRICE_PAGE;
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

	private void updatePriceBeanForApproval(PriceBean priceBean, String username) {
		priceBean.setStatus(MVCConstants.STATUS_APPROVED);
		priceBean.setModifiedBy(username);
		priceBean.setModifiedDate(LocalDateTime.now());
		logger.info("The price bean status has been updated to approved now");
	}

	private void updateApproveStatus(Model model,List<ItemPrice> itemPriceList, Locale locale) {
		if(itemPriceList!=null && !itemPriceList.isEmpty()) {
			model.addAttribute(MVCConstants.SUCCESS,this.messageSource.getMessage("commerce.screen.common.approve.success", null, locale));
			logger.info("The selected {} prices has been approved successfully",itemPriceList.size());
		}else {
			logger.info("The selected prices were not approved");
			model.addAttribute(MVCConstants.ALERT,this.messageSource.getMessage("commerce.screen.common.approve.failure", null, locale));
		}
	}
	
	private void updateDeleteStatus(Model model,List<ItemPrice> itemPriceList, Locale locale) {
		if(itemPriceList!=null && !itemPriceList.isEmpty()) {
			model.addAttribute(MVCConstants.SUCCESS,this.messageSource.getMessage("commerce.screen.common.delete.success", null, locale));
			logger.info("The selected {} prices has been deleted successfully",itemPriceList.size());
		}else {
			logger.info("The selected prices were not deleted due to some expected condition");
			model.addAttribute(MVCConstants.ALERT,this.messageSource.getMessage("commerce.screen.common.delete.failure", null, locale));
		}
	}	
	
	@PostMapping(value = ViewPathConstants.BULK_PRICE_URL, params = { MVCConstants.DELETE_PARAM })
	public String deletePrices(@ModelAttribute PriceBeanDTO priceBeanDTO, BindingResult bindingResult, Model model, Locale locale,
			Authentication authentication) {
		if (bindingResult.hasErrors()) {
			this.updatePriceBeanDTO(priceBeanDTO, model);
			return ViewPathConstants.MANAGE_PRICE_PAGE;
		}
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			if (userDetails != null) {

				List<PriceBean> priceBeanList = priceBeanDTO.getPriceBeans();
				Map<BigInteger, Integer> idIndex = CommonMVCTransformer.transformSelectedIds(priceBeanDTO.getItemPriceIds());

				List<PriceBean> finalPriceBeans = new ArrayList<>(idIndex.size());

				Set<BigInteger> ids = idIndex.keySet();
				Integer index = null;
				PriceBean priceBean = null;
				for (BigInteger id : ids) {
					index = idIndex.get(id);
					priceBean = priceBeanList.get(index);
					if (priceBean.getStatus().equals(MVCConstants.STATUS_CREATED)) {
						priceBean.setItemPriceId(id);
						this.updatePriceBeanForApproval(priceBean, userDetails.getUsername());
						finalPriceBeans.add(priceBean);
					}
				}
				logger.info("The selected list of item prices has been finalized for processing");

				List<ItemPrice> itemPriceList = PriceTransformer.transformPriceBeanList(finalPriceBeans);
				this.priceService.deleteItemPriceList(itemPriceList);
				this.updateDeleteStatus(model, itemPriceList, locale);
				this.updatePriceBeanDTO(priceBeanDTO, model); 
				logger.info("The price deletion method processing has been completed");				
			}

		} catch (Exception e) {
			logger.error("There is an error while deleting selected item prices", e);
			this.updatePriceBeanDTO(priceBeanDTO, model);
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.common.error", null, locale));
			return ViewPathConstants.MANAGE_PRICE_PAGE;
		}
		return ViewPathConstants.MANAGE_PRICE_PAGE;
	}
	
	
}
