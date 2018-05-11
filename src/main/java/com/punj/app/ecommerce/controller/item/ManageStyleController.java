package com.punj.app.ecommerce.controller.item;
/**
 * 
 */

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
import org.springframework.web.multipart.MultipartFile;

import com.punj.app.ecommerce.controller.common.ItemDefaultConfig;
import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.HierarchyTransformer;
import com.punj.app.ecommerce.controller.common.transformer.ItemTransformer;
import com.punj.app.ecommerce.controller.common.transformer.OrderTransformer;
import com.punj.app.ecommerce.domains.common.UOM;
import com.punj.app.ecommerce.domains.item.Hierarchy;
import com.punj.app.ecommerce.domains.item.Item;
import com.punj.app.ecommerce.domains.order.Order;
import com.punj.app.ecommerce.domains.tax.TaxGroup;
import com.punj.app.ecommerce.models.common.SearchBean;
import com.punj.app.ecommerce.models.item.HierarchyBean;
import com.punj.app.ecommerce.models.item.ItemBean;
import com.punj.app.ecommerce.models.item.ItemImageBean;
import com.punj.app.ecommerce.models.item.ItemOptionsBean;
import com.punj.app.ecommerce.models.order.OrderBean;
import com.punj.app.ecommerce.models.order.OrderBeanDTO;
import com.punj.app.ecommerce.models.supplier.SupplierBean;
import com.punj.app.ecommerce.services.ItemService;
import com.punj.app.ecommerce.services.common.CommonService;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class ManageStyleController {
	private static final Logger logger = LogManager.getLogger();
	private ItemService itemService;
	private CommonService commonService;
	private MessageSource messageSource;

	private ItemDefaultConfig itemConfig;

	/**
	 * @param userService
	 *            the userService to set
	 */
	@Autowired
	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
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
	 * @param userService
	 *            the userService to set
	 */
	@Autowired
	public void setItemDefaultConfig(ItemDefaultConfig itemConfig) {
		this.itemConfig = itemConfig;
	}

	/**
	 * @param commonService
	 *            the commonService to set
	 */
	@Autowired
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	@GetMapping(ViewPathConstants.ADD_STYLE_URL)
	public String showStyle(Model model, HttpSession session, Locale locale) {

		ItemBean itemBean = new ItemBean();

		List<ItemImageBean> itemImages = new ArrayList<>();
		itemImages.add(new ItemImageBean());
		itemImages.add(new ItemImageBean());
		itemImages.add(new ItemImageBean());
		itemImages.add(new ItemImageBean());
		itemBean.setItemImages(itemImages);

		this.updateItemDefaultConfs(itemBean);

		this.updateModelWithEssentialScreenData(itemBean, model, locale);

		logger.info("The default style object has been added to display on the screen");

		return ViewPathConstants.ADD_STYLE_PAGE;
	}

	public void updateModelWithEssentialScreenData(ItemBean itemBean, Model model, Locale locale) {

		try {
			this.prepareImagesForDisplay(itemBean.getItemImages());

			List<TaxGroup> taxGroups = this.commonService.retrieveAllTaxGroups();
			model.addAttribute(MVCConstants.TAX_GROUP_LIST_BEAN, taxGroups);
			logger.info("The tax group information has been updated in the bean");

			List<UOM> UOMs = this.commonService.retrieveAllUOMs();
			model.addAttribute(MVCConstants.UOM_LIST_BEAN, UOMs);
			logger.info("The UOM information has been updated in the bean");

			model.addAttribute(MVCConstants.ITEM_BEAN, itemBean);
		} catch (IOException e) {
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			logger.error("There was some error while handling the item creation related tasks", e);
		}
	}

	public List<ItemImageBean> prepareImagesForDisplay(List<ItemImageBean> itemImages) throws IOException {
		if (itemImages != null && !itemImages.isEmpty()) {
			MultipartFile imageFile = null;
			for (ItemImageBean itemImageBean : itemImages) {
				imageFile = itemImageBean.getImageData();
				if (imageFile != null) {
					ItemTransformer.setItemImageForDisplay(itemImageBean);
					itemImageBean.setImageType(imageFile.getContentType());
					itemImageBean.setImageURL(imageFile.getOriginalFilename());
				}
			}
		}
		logger.info("The item images data has been set for display on the Page");
		return itemImages;
	}

	/**
	 * This method is used to update the default configurations for the item
	 * 
	 * @param model
	 * @param itemBean
	 */
	public ItemBean updateItemDefaultConfs(ItemBean itemBean) {

		ItemOptionsBean itemOptionsBean = itemBean.getItemOptions();

		String defaultHierarchyName = this.itemConfig.defaultHierarchy;
		Hierarchy hierarchy = this.commonService.retrieveHierarchy(defaultHierarchyName);
		if (hierarchy != null) {
			HierarchyBean hierarchyBean = HierarchyTransformer.transformHierarchy(hierarchy);
			itemBean.setHierarchy(hierarchyBean);
			logger.info("The default item hierarchy has been set successfully");
		} else {
			logger.info("The default item configuration for Item hierarchy was not retrieved");
		}

		String defaultItemType = this.itemConfig.defaultItemType;
		itemBean.setItemType(defaultItemType);
		logger.info("The default item type has been set successfully");

		String defaultUOMCode = this.itemConfig.defaultUOM;
		UOM uom = this.commonService.retrieveUOM(defaultUOMCode);
		if (uom != null) {
			itemOptionsBean.setUom(uom.getCode());
			logger.info("The default UOM has been set successfully");
		} else {
			logger.info("The default item configuration for Item UOM was not retrieved");
		}

		Integer defaultPackSize = this.itemConfig.defaultPackSize;
		itemOptionsBean.setPackSize(defaultPackSize.toString());
		logger.info("The default pack size has been set successfully");

		/**
		 * All the price configuration starts here
		 */

		BigDecimal defaultUnitCost = this.itemConfig.defaultUnitCost;
		itemOptionsBean.setUnitCost(defaultUnitCost);
		logger.info("The default unit cost has been set successfully");

		BigDecimal defaultSuggestedPrice = this.itemConfig.defaultSuggestedPrice;
		itemOptionsBean.setSuggestedPrice(defaultSuggestedPrice);
		logger.info("The default suggested Price has been set successfully");

		BigDecimal defaultRestockingFee = this.itemConfig.defaultRestockingFee;
		itemOptionsBean.setRestockingFee(defaultRestockingFee);
		logger.info("The default restocking fee has been set successfully");

		BigDecimal defaultCompareAtPrice = this.itemConfig.defaultCompareAtPrice;
		itemOptionsBean.setCompareAtPrice(defaultCompareAtPrice);
		logger.info("The default compare at price has been set successfully");

		BigDecimal defaultMRP = this.itemConfig.defaultMRP;
		itemOptionsBean.setMaxRetailPrice(defaultMRP);
		logger.info("The default MRP has been set successfully");

		BigDecimal defaultCurrentPrice = this.itemConfig.defaultCurrentPrice;
		itemOptionsBean.setCurrentPrice(defaultCurrentPrice);
		logger.info("The default current price has been set successfully");

		/**
		 * All the flag configurations starts here
		 */

		Boolean defaultDiscountFlag = this.itemConfig.defaultDiscountFlag;
		itemOptionsBean.setDiscountFlag(defaultDiscountFlag);

		Boolean defaultAskQtyFlag = this.itemConfig.defaultAskQtyFlag;
		itemOptionsBean.setAskQtyFlag(defaultAskQtyFlag);

		Boolean defaultPriceChangeFlag = this.itemConfig.defaultPriceChangeFlag;
		itemOptionsBean.setPriceChangeFlag(defaultPriceChangeFlag);

		Boolean defaultReturnFlag = this.itemConfig.defaultReturnFlag;
		itemOptionsBean.setReturnFlag(defaultReturnFlag);

		Boolean defaultDefaultTaxFlag = this.itemConfig.defaultTaxFlag;
		itemOptionsBean.setTaxFlag(defaultDefaultTaxFlag);

		logger.info("The default flags for item has been set successfully");

		logger.info("The default configurations has been applied to item successfully");
		return itemBean;

	}

	@PostMapping(value = ViewPathConstants.ADD_STYLE_URL, params = { MVCConstants.SAVE_STYLE_PARAM })
	public String addStyle(@ModelAttribute @Valid ItemBean itemBean, BindingResult bindingResult, Model model, HttpSession session,
			Authentication authentication, Locale locale) {
		if (bindingResult.hasErrors()) {
			this.updateModelWithEssentialScreenData(itemBean, model, locale);
			return ViewPathConstants.ADD_STYLE_PAGE;
		}
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();

			Item item = ItemTransformer.transformItemBean(itemBean, userDetails.getUsername(), MVCConstants.ACTION_NEW, MVCConstants.FUNCTIONALITY_NEW_STYLE_PARAM);
			
			if(item.getItemId()!=null)
				item = this.itemService.saveItem(item);
			else
				item = this.itemService.saveNewItem(item);
			logger.info("The style details has been saved successfully");

			if (item != null) {
				itemBean=ItemTransformer.transformItem(item);

				model.addAttribute(MVCConstants.SUCCESS, messageSource.getMessage("commerce.screen.item.add.success", new Object[] { item.getItemId().toString() }, locale));

			} else {
				model.addAttribute(MVCConstants.ALERT, messageSource.getMessage("commerce.screen.item.add.failure", null, locale));
			}
			this.updateModelWithEssentialScreenData(itemBean, model, locale);

		} catch (IOException e) {
			this.updateModelWithEssentialScreenData(itemBean, model, locale);
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			logger.error("There was some error while handling the item creation related tasks", e);
		}

		return ViewPathConstants.ADD_STYLE_PAGE;
	}
	
	@PostMapping(value = ViewPathConstants.ADD_STYLE_URL, params = { MVCConstants.SAVE_APPROVE_STYLE_PARAM })
	public String saveApproveStyle(@ModelAttribute @Valid ItemBean itemBean, BindingResult bindingResult, Model model, HttpSession session,
			Authentication authentication, Locale locale) {
		
		if (bindingResult.hasErrors()) {
			this.updateModelWithEssentialScreenData(itemBean, model, locale);
			return ViewPathConstants.ADD_STYLE_PAGE;
		}
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			List<ItemImageBean> itemImagesBeans=itemBean.getItemImages();
			Item item = ItemTransformer.transformItemBean(itemBean, userDetails.getUsername(), MVCConstants.ACTION_EDIT, MVCConstants.FUNCTIONALITY_APPROVE_STYLE_PARAM);

			item = this.itemService.approveItem(item);
			logger.info("The style details has been saved and approved successfully");

			if (item != null) {
				item=this.itemService.getItem(item.getItemId());
				itemBean=ItemTransformer.transformItem(item);
				itemBean.setItemImages(itemImagesBeans);
				
				model.addAttribute(MVCConstants.SUCCESS, messageSource.getMessage("commerce.screen.item.approve.success", new Object[] {  item.getItemId().toString()}, locale));

			} else {
				model.addAttribute(MVCConstants.ALERT, messageSource.getMessage("commerce.screen.item.approve.failure", null, locale));
			}
			this.updateModelWithEssentialScreenData(itemBean, model, locale);

		} catch (IOException e) {
			this.updateModelWithEssentialScreenData(itemBean, model, locale);
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			logger.error("There was some error while handling the item save and approval related tasks", e);
		}

		return ViewPathConstants.ADD_STYLE_PAGE;
	}
	

	@GetMapping(value = ViewPathConstants.EDIT_STYLE_URL)
	public String editStyle(Model model, final HttpServletRequest req, Locale locale) {
		logger.info("The edit method for item has been called");
		ItemBean itemBean=null;
		try {
			BigInteger styleNumber = new BigInteger(req.getParameter(MVCConstants.STYLE_ID_PARAM));

			Item item= this.itemService.getItem(styleNumber);
			
			if (item != null) {
				item=this.itemService.getItem(item.getItemId());
				itemBean=ItemTransformer.transformItem(item);
				logger.info("The selected item has been updated successfully");
			} else {
				logger.info("There was some issue while retrieving Item details");
			}
			this.updateModelWithEssentialScreenData(itemBean, model, locale);

		} catch (Exception e) {
			logger.error("There is an error while retrieving item for updation", e);
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			this.updateModelWithEssentialScreenData(itemBean, model, locale);
			return ViewPathConstants.EDIT_STYLE_PAGE;
		}
		return ViewPathConstants.EDIT_STYLE_PAGE;

	}
	
	@PostMapping(value = ViewPathConstants.EDIT_STYLE_URL, params = { MVCConstants.SAVE_STYLE_PARAM })
	public String addStyleAfterEdit(@ModelAttribute @Valid ItemBean itemBean, BindingResult bindingResult, Model model, HttpSession session,
			Authentication authentication, Locale locale) {
		if (bindingResult.hasErrors()) {
			this.updateModelWithEssentialScreenData(itemBean, model, locale);
			return ViewPathConstants.EDIT_STYLE_PAGE;
		}
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			List<ItemImageBean> itemImagesBeans=itemBean.getItemImages();
			Item item = ItemTransformer.transformItemBean(itemBean, userDetails.getUsername(), MVCConstants.ACTION_EDIT, MVCConstants.FUNCTIONALITY_NEW_STYLE_PARAM);

			item = this.itemService.saveItem(item);
			logger.info("The style details has been saved successfully");

			if (item != null) {
				item=this.itemService.getItem(item.getItemId());
				itemBean=ItemTransformer.transformItem(item);
				itemBean.setItemImages(itemImagesBeans);

				model.addAttribute(MVCConstants.SUCCESS, messageSource.getMessage("commerce.screen.item.edit.success", new Object[] { item.getItemId().toString() }, locale));

			} else {
				model.addAttribute(MVCConstants.ALERT, messageSource.getMessage("commerce.screen.item.edit.failure", null, locale));
			}
			this.updateModelWithEssentialScreenData(itemBean, model, locale);

		} catch (IOException e) {
			this.updateModelWithEssentialScreenData(itemBean, model, locale);
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			logger.error("There was some error while handling the item creation related tasks", e);
		}

		return ViewPathConstants.EDIT_STYLE_PAGE;
	}
	
	@PostMapping(value = ViewPathConstants.EDIT_STYLE_URL, params = { MVCConstants.SAVE_APPROVE_STYLE_PARAM })
	public String saveApproveStyleAfterEdit(@ModelAttribute @Valid ItemBean itemBean, BindingResult bindingResult, Model model, HttpSession session,
			Authentication authentication, Locale locale) {
		
		if (bindingResult.hasErrors()) {
			this.updateModelWithEssentialScreenData(itemBean, model, locale);
			return ViewPathConstants.EDIT_STYLE_PAGE;
		}
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			List<ItemImageBean> itemImagesBeans=itemBean.getItemImages();
			Item item = ItemTransformer.transformItemBean(itemBean, userDetails.getUsername(), MVCConstants.ACTION_EDIT, MVCConstants.FUNCTIONALITY_APPROVE_STYLE_PARAM);

			item = this.itemService.approveItem(item);
			logger.info("The style details has been saved and approved successfully");

			if (item != null) {
				item=this.itemService.getItem(item.getItemId());
				itemBean=ItemTransformer.transformItem(item);
				itemBean.setItemImages(itemImagesBeans);
				
				model.addAttribute(MVCConstants.SUCCESS, messageSource.getMessage("commerce.screen.item.approve.success", new Object[] {  item.getItemId().toString()}, locale));

			} else {
				model.addAttribute(MVCConstants.ALERT, messageSource.getMessage("commerce.screen.item.approve.failure", null, locale));
			}
			this.updateModelWithEssentialScreenData(itemBean, model, locale);

		} catch (IOException e) {
			this.updateModelWithEssentialScreenData(itemBean, model, locale);
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			logger.error("There was some error while handling the item save and approval related tasks", e);
		}

		return ViewPathConstants.EDIT_STYLE_PAGE;
	}
	
	
}
