package com.punj.app.ecommerce.controller.item;
/**
 * 
 */

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.punj.app.ecommerce.controller.common.ItemDefaultConfig;
import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.ItemTransformer;
import com.punj.app.ecommerce.domains.item.Item;
import com.punj.app.ecommerce.domains.tax.TaxGroup;
import com.punj.app.ecommerce.models.common.validator.ValidationGroup;
import com.punj.app.ecommerce.models.item.ItemBean;
import com.punj.app.ecommerce.models.item.ItemBeanDTO;
import com.punj.app.ecommerce.models.item.ItemImageBean;
import com.punj.app.ecommerce.services.ItemService;
import com.punj.app.ecommerce.services.common.CommonService;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class ManageSKUController {
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

	@GetMapping(ViewPathConstants.ADD_SKU_URL)
	public String showStyle(Model model, final HttpServletRequest req, Locale locale) {

		logger.info("The edit method for item has been called");
		ItemBean itemBean = null;
		ItemBeanDTO itemDTO = null;
		try {
			BigInteger styleNumber = new BigInteger(req.getParameter(MVCConstants.STYLE_ID_PARAM));

			Item item = this.itemService.getItem(styleNumber);

			if (item != null) {

				item = this.itemService.getItem(item.getItemId());
				itemBean = ItemTransformer.transformItem(item);

				if (this.isItemEligibleForSKUCreation(itemBean, model, locale)) {
					itemDTO = new ItemBeanDTO();
					itemDTO.setStyle(itemBean);

					List<ItemBean> itemBeans = new ArrayList<>();
					itemBeans.add(new ItemBean());
					itemDTO.setSkus(itemBeans);

					logger.info("The selected style details has been retrieved successfully for sku creation");
				} else {

					logger.info("The selected style eligibility validation has failed for SKU creation");
					return ViewPathConstants.ADD_SKU_PAGE;
				}

			} else {
				logger.info("There was some issue while retrieving style details for sku creation");
			}
			this.updateModelWithEssentialScreenData(itemDTO, model, locale);

		} catch (Exception e) {
			logger.error("There is an error while retrieving item for updation", e);
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			this.updateModelWithEssentialScreenData(itemDTO, model, locale);
			return ViewPathConstants.ADD_SKU_PAGE;
		}
		return ViewPathConstants.ADD_SKU_PAGE;
	}

	public Boolean isItemEligibleForSKUCreation(ItemBean itemBean, Model model, Locale locale) {
		Boolean result = Boolean.TRUE;
		if (itemBean.getItemLevel() == null || !MVCConstants.STYLE_LEVEL.equals(itemBean.getItemLevel())) {
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.common.error.style.item.level", null, locale));
			result = Boolean.FALSE;
		}
		if (itemBean.getStatus() == null || !MVCConstants.STATUS_APPROVED.equals(itemBean.getStatus())) {
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.common.error.style.status.not.approved", null, locale));
			result = Boolean.FALSE;
		}
		return result;

	}

	public void updateModelWithEssentialScreenData(ItemBeanDTO itemBeanDTO, Model model, Locale locale) {

		try {
			if (itemBeanDTO != null) {
				this.prepareImagesForDisplay(itemBeanDTO.getStyle().getItemImages());

				List<TaxGroup> taxGroups = this.commonService.retrieveAllTaxGroups();
				model.addAttribute(MVCConstants.TAX_GROUP_LIST_BEAN, taxGroups);
				logger.info("The tax group information has been updated in the bean");

				model.addAttribute(MVCConstants.ITEM_BEAN, itemBeanDTO);
			} else {
				model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			}
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

	@PostMapping(value = ViewPathConstants.ADD_SKU_URL, params = { MVCConstants.SAVE_SKU_PARAM })
	public String addSKUs(@ModelAttribute @Validated(ValidationGroup.ValidationGroupSKU.class) ItemBeanDTO itemBean, BindingResult bindingResult, Model model,
			HttpSession session, Authentication authentication, Locale locale) {

		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();

			Item style = this.itemService.getStyle(itemBean.getStyle().getItemId());
			if (style != null) {

				List<Item> skuList = ItemTransformer.createSKUs(style, itemBean, userDetails.getUsername());

				skuList = this.itemService.createSKUs(skuList);
				if (skuList != null && !skuList.isEmpty()) {

					model.addAttribute(MVCConstants.SUCCESS, messageSource.getMessage("commerce.screen.sku.add.success", null, locale));

					logger.info("The new skus has been created successfully");
				} else {
					model.addAttribute(MVCConstants.ALERT, messageSource.getMessage("commerce.screen.sku.add.failure", null, locale));
					logger.info("The new skus creation has failed due to some issues");
				}
				this.updateModelWithEssentialScreenData(itemBean, model, locale);
			} else {
				model.addAttribute(MVCConstants.ALERT, messageSource.getMessage("commerce.screen.sku.add.failure", null, locale));
				logger.info("The new skus creation has failed due to some issues");
			}
		} catch (Exception e) {
			this.updateModelWithEssentialScreenData(itemBean, model, locale);
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			logger.error("There was some error while handling the sku creation related tasks", e);
		}

		return ViewPathConstants.ADD_SKU_PAGE;
	}

}
