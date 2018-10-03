package com.punj.app.ecommerce.controller.lookup;
/**
 * 
 */

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.CommonMVCTransformer;
import com.punj.app.ecommerce.controller.common.transformer.PriceTransformer;
import com.punj.app.ecommerce.domains.common.Location;
import com.punj.app.ecommerce.domains.price.ItemPrice;
import com.punj.app.ecommerce.models.common.LocationBean;
import com.punj.app.ecommerce.models.item.ItemSearchBean;
import com.punj.app.ecommerce.models.price.PriceBean;
import com.punj.app.ecommerce.services.PriceService;
import com.punj.app.ecommerce.services.common.CommonService;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class PriceLookupController {
	private static final Logger logger = LogManager.getLogger();
	private PriceService priceService;
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

	/**
	 * @param priceService
	 *            the priceService to set
	 */
	@Autowired
	public void setPriceService(PriceService priceService) {
		this.priceService = priceService;
	}

	@GetMapping(ViewPathConstants.PRICE_LOOKUP_URL)
	public String showInventoryLookupScreen(Model model, Locale locale) {

		this.updateBeanDetails(model, new ItemSearchBean());

		logger.info("The item price lookup screen has been called.");
		return ViewPathConstants.PRICE_LOOKUP_PAGE;
	}

	@PostMapping(ViewPathConstants.PRICE_LOOKUP_URL)
	public String retrievePriceDetails(@ModelAttribute @Valid ItemSearchBean searchBean, BindingResult bindingResult, Model model, Locale locale) {
		if (bindingResult.hasErrors()) {
			this.updateBeanDetails(model, searchBean);
			return ViewPathConstants.PRICE_LOOKUP_PAGE;
		}
		try {
			List<ItemPrice> itemPrices = null;
			if (searchBean.getLocationId().equals(MVCConstants.ALL_LITERAL)) {
				itemPrices = this.priceService.retrieveAllItemPrices(searchBean.getItemId());
			} else {
				itemPrices = this.priceService.retrieveItemPrice(searchBean.getItemId(), new Integer(searchBean.getLocationId()));
			}

			if (itemPrices != null && !itemPrices.isEmpty()) {
				List<PriceBean> priceList = PriceTransformer.transformItemPriceList(itemPrices);
				model.addAttribute(MVCConstants.SUCCESS, messageSource.getMessage("commerce.screen.lookup.price.success", null, locale));
				model.addAttribute(MVCConstants.ITEM_PRICE_BEANS, priceList);
				logger.info("The item price lookup has completed successfully");
			}

		} catch (Exception e) {
			logger.error("There is an error while retrieving the price details", e);
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));

		}
		this.updateBeanDetails(model, searchBean);
		return ViewPathConstants.PRICE_LOOKUP_PAGE;
	}

	private void updateBeanDetails(Model model, ItemSearchBean searchBean) {
		List<Location> locationList = this.commonService.retrieveAllLocations();
		List<LocationBean> locations = null;
		if (locationList != null && !locationList.isEmpty()) {
			locations = CommonMVCTransformer.transformLocationList(locationList, Boolean.TRUE);

			Map<Integer, String> locationMap = new HashMap<>();

			for (LocationBean locationBean : locations) {
				locationMap.put(locationBean.getLocationId(), locationBean.getName());
			}

			model.addAttribute(MVCConstants.LOCATION_MAP_BEANS, locationMap);
			model.addAttribute(MVCConstants.LOCATION_BEANS, locations);
			logger.info("All the locations has been retrieved successfully");
		}

		model.addAttribute(MVCConstants.SEARCH_BEAN, searchBean);
	}

}
