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
import com.punj.app.ecommerce.controller.common.transformer.InventoryBeanTransformer;
import com.punj.app.ecommerce.domains.common.Location;
import com.punj.app.ecommerce.domains.inventory.ItemStock;
import com.punj.app.ecommerce.models.common.LocationBean;
import com.punj.app.ecommerce.models.inventory.ItemInventory;
import com.punj.app.ecommerce.models.item.ItemSearchBean;
import com.punj.app.ecommerce.services.InventoryService;
import com.punj.app.ecommerce.services.ItemService;
import com.punj.app.ecommerce.services.common.CommonService;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class InventoryLookupController {
	private static final Logger logger = LogManager.getLogger();
	private ItemService itemService;
	private CommonService commonService;
	private InventoryService inventoryService;
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
	 * @param inventoryService
	 *            the inventoryService to set
	 */
	@Autowired
	public void setInventoryService(InventoryService inventoryService) {
		this.inventoryService = inventoryService;
	}

	/**
	 * @param itemService
	 *            the itemService to set
	 */
	@Autowired
	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	@GetMapping(ViewPathConstants.INV_LOOKUP_URL)
	public String showInventoryLookupScreen(Model model, Locale locale) {

		this.updateBeanDetails(model, new ItemSearchBean());

		logger.info("The item inventory lookup screen has been called.");
		return ViewPathConstants.INV_LOOKUP_PAGE;
	}

	@PostMapping(ViewPathConstants.INV_LOOKUP_URL)
	public String retrieveInventoryDetails(@ModelAttribute @Valid ItemSearchBean searchBean, BindingResult bindingResult, Model model, Locale locale) {
		if (bindingResult.hasErrors()) {
			this.updateBeanDetails(model, searchBean);
			return ViewPathConstants.INV_LOOKUP_PAGE;
		}
		try {
			List<ItemStock> itemStocks = null;
			if (searchBean.getLocationId().equals(MVCConstants.ALL_LITERAL)) {
				itemStocks = this.inventoryService.searchItemStockBasedOnLocations(searchBean.getItemId(), 0);
			} else {
				itemStocks = this.inventoryService.searchItemStockBasedOnLocations(searchBean.getItemId(), new Integer(searchBean.getLocationId()));
			}

			if (itemStocks != null && !itemStocks.isEmpty()) {
				List<ItemInventory> itemInventoryList = InventoryBeanTransformer.transformItemStocks(itemStocks);
				model.addAttribute(MVCConstants.SUCCESS, messageSource.getMessage("commerce.screen.lookup.inv.success", null, locale));
				model.addAttribute(MVCConstants.ITEM_INV_BEANS, itemInventoryList);
				logger.info("The item inventory lookup has completed successfully");
			}

		} catch (Exception e) {
			logger.error("There is an error while retrieving the inventory details", e);
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));

		}
		this.updateBeanDetails(model, searchBean);
		return ViewPathConstants.INV_LOOKUP_PAGE;
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
