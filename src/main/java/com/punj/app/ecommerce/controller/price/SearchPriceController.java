package com.punj.app.ecommerce.controller.price;
/**
 * 
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.PriceTransformer;
import com.punj.app.ecommerce.domains.common.Location;
import com.punj.app.ecommerce.domains.price.ItemPrice;
import com.punj.app.ecommerce.domains.price.ItemPriceDTO;
import com.punj.app.ecommerce.models.common.LocationBean;
import com.punj.app.ecommerce.models.common.SearchBean;
import com.punj.app.ecommerce.models.price.PriceBean;
import com.punj.app.ecommerce.models.price.PriceBeanDTO;
import com.punj.app.ecommerce.services.PriceService;
import com.punj.app.ecommerce.services.common.CommonService;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class SearchPriceController {
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

	@GetMapping(value = ViewPathConstants.MANAGE_PRICE_URL)
	public String showPriceSearchScreen(Model model, Authentication authentication) {
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			if (userDetails != null) {
				PriceBeanDTO priceBeanDTO = new PriceBeanDTO();
				this.updatePriceBeanDTO(priceBeanDTO, model);
				logger.info("The Price mangement and search screen is ready for display");
			}
		} catch (Exception e) {
			logger.error("There is an error while opening price management page", e);
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
		model.addAttribute(MVCConstants.PAGER, priceBeanDTO.getPager());
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

	@PostMapping(value = ViewPathConstants.MANAGE_PRICE_URL, params = { MVCConstants.SEARCH_PARAM })
	public String searchPrices(@ModelAttribute PriceBeanDTO priceBeanDTO, @RequestParam(MVCConstants.PAGE_PARAM) Optional<Integer> page,
			Authentication authentication, Model model, Locale locale) {
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			if (userDetails != null) {
				Integer currentPage;
				if (page.isPresent()) {
					currentPage = page.get();
				} else {
					currentPage = 1;
				}

				ItemPriceDTO itemPriceDTO = this.priceService.searchItemPrice(priceBeanDTO.getItemId(), priceBeanDTO.getLocationId(),
						priceBeanDTO.getPriceType(), currentPage);
				List<ItemPrice> itemPriceList = itemPriceDTO.getItemPriceList();
				List<PriceBean> priceBeans = PriceTransformer.transformItemPriceList(itemPriceList);
				priceBeanDTO.setPriceBeans(priceBeans);

				Pager pager = itemPriceDTO.getPager();
				pager.setViewBasePath(ViewPathConstants.MANAGE_PRICE_URL);
				priceBeanDTO.setPager(pager);

				this.updatePriceBeanDTO(priceBeanDTO, model);
				this.updateSearchStatus(model, priceBeans, locale, pager);
				logger.info("The price search has been completed successfully");
			}
		} catch (Exception e) {
			logger.error("There is an error while searching for item prices", e);
			return ViewPathConstants.MANAGE_PRICE_PAGE;
		}
		return ViewPathConstants.MANAGE_PRICE_PAGE;
	}

	private void updateSearchStatus(Model model, List<PriceBean> priceBeans, Locale locale, Pager pager) {
		if (priceBeans != null && !priceBeans.isEmpty()) {
			model.addAttribute(MVCConstants.SUCCESS, messageSource.getMessage("commerce.screen.common.search.success",
					new Object[] { pager.getResultSize(), pager.getCurrentPageNo(), pager.getNoOfPages()}, locale));
			logger.info("The retrieved price record count is {}.", priceBeans.size());
		} else {
			logger.info("No records were retrieved based on criteria");
			model.addAttribute(MVCConstants.ALERT, messageSource.getMessage("commerce.screen.common.search.failure", null, locale));
		}
	}

}
