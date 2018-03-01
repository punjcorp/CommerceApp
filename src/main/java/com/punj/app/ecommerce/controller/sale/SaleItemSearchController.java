package com.punj.app.ecommerce.controller.sale;
/**
 * 
 */

import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.punj.app.ecommerce.common.web.CommerceConstants;
import com.punj.app.ecommerce.common.web.CommerceContext;
import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.models.common.SearchBean;
import com.punj.app.ecommerce.models.sale.SaleHeaderBean;
import com.punj.app.ecommerce.services.ItemService;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class SaleItemSearchController {
	private static final Logger logger = LogManager.getLogger();
	private ItemService itemService;
	private CommerceContext commerceContext;

	/**
	 * @param userService
	 *            the userService to set
	 */
	@Autowired
	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	/**
	 * @param commerceContext
	 *            the commerceContext to set
	 */
	@Autowired
	public void setCommerceContext(CommerceContext commerceContext) {
		this.commerceContext = commerceContext;
	}

	@GetMapping(ViewPathConstants.POS_URL)
	public String showSaleScreen(Model model) {
		try {
			this.updateBeans(model);
			logger.info("The sale screen is ready for display now");
		} catch (Exception e) {
			logger.error("There is an error while showing the new sale screen", e);
			return ViewPathConstants.AUTO_ITEM_PAGE;
		}
		return ViewPathConstants.AUTO_ITEM_PAGE;
	}

	/**
	 * This method is to set all the bean objects in model needed for the sale screen functionalities
	 * 
	 */
	private void updateBeans(Model model) {
		SearchBean searchBean = new SearchBean();
		model.addAttribute(MVCConstants.SEARCH_BEAN, searchBean);

		SaleHeaderBean saleHeaderBean = new SaleHeaderBean();
		Object openLocationId=commerceContext.getStoreSettings(CommerceConstants.OPEN_LOC_ID);
		Object openLocationName=commerceContext.getStoreSettings(CommerceConstants.OPEN_LOC_NAME);
		Object openBusinessDate=commerceContext.getStoreSettings(CommerceConstants.OPEN_BUSINESS_DATE);
		if(openLocationId!=null)
			saleHeaderBean.setLocationId((Integer)openLocationId);
		if(openLocationName!=null)
		saleHeaderBean.setLocationName((String)openLocationName);
		if(openBusinessDate!=null)
		saleHeaderBean.setBusinessDate((LocalDateTime)openBusinessDate);

		model.addAttribute(MVCConstants.SALE_HEADER_BEAN, saleHeaderBean);
		logger.info("All the needed beans for sales screen has been set in the model");
	}

}
