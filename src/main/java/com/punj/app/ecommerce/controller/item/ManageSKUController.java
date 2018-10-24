package com.punj.app.ecommerce.controller.item;
/**
 * 
 */

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
import com.punj.app.ecommerce.domains.item.Attribute;
import com.punj.app.ecommerce.domains.item.Item;
import com.punj.app.ecommerce.domains.item.ItemAttribute;
import com.punj.app.ecommerce.domains.tax.TaxGroup;
import com.punj.app.ecommerce.models.common.validator.ValidationGroup;
import com.punj.app.ecommerce.models.item.AttributeBean;
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
		List<AttributeBean> attributeBeans = null;
		ItemBeanDTO itemDTO = null;
		try {
			BigInteger styleNumber = new BigInteger(req.getParameter(MVCConstants.STYLE_ID_PARAM));
			List<ItemBean> skuList = null;
			Item item = this.itemService.getItem(styleNumber);

			if (item != null) {

				item = this.itemService.getItem(item.getItemId());
				itemBean = ItemTransformer.transformItem(item);

				if (item.getItemOptions().getNextLevelCreated() != null
						&& item.getItemOptions().getNextLevelCreated().equals(MVCConstants.NEXT_LEVEL_CREATED)) {
					Item itemCriteria = new Item();
					itemCriteria.setParentItemId(styleNumber);
					List<Item> skuItemList = this.itemService.retrieveItems(itemCriteria);
					if (skuItemList != null && !skuItemList.isEmpty()) {
						List<ItemAttribute> itemAttributes = skuItemList.get(0).getItemAttributes();

						List<Attribute> attributes = new ArrayList<>(itemAttributes.size());
						Attribute attr = null;
						for (ItemAttribute itemAttribute : itemAttributes) {
							attr = itemAttribute.getItemAttributeId().getAttribute();
							attributes.add(attr);
						}
						attributeBeans = ItemTransformer.transformAttributes(attributes);

						List<String> attrCodeList = ItemTransformer.retrieveSelectedAttributes(skuItemList);
						Map<String, List<Attribute>> attrValueMap = this.itemService.retrieveAttrListValues(attrCodeList);
						if (attrValueMap != null && attrValueMap.size() > 0)
							ItemTransformer.updateAttrValues(attributeBeans, attrValueMap);
						logger.info("The attribute value list has been successfully retrieved");

						skuList = ItemTransformer.transformItems(skuItemList);

					}

				}

				if (this.isItemEligibleForSKUCreation(itemBean, model, locale)) {
					itemDTO = new ItemBeanDTO();
					itemDTO.setSkus(skuList);
					itemBean.setSelectedAttributes(attributeBeans);
					itemDTO.setStyle(itemBean);

					logger.info("The selected style details has been retrieved successfully for sku creation");
				} else {

					logger.info("The selected style eligibility validation has failed for SKU creation");
					return ViewPathConstants.ADD_SKU_PAGE;
				}

			} else {
				logger.info("There was some issue while retrieving style details for sku creation");
			}
			this.updateModelWithEssentialScreenData(itemDTO, model, locale, Boolean.FALSE);

		} catch (Exception e) {
			logger.error("There is an error while retrieving item for updation", e);
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			this.updateModelWithEssentialScreenData(itemDTO, model, locale, Boolean.FALSE);
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
		if (itemBean.getItemOptions() == null || itemBean.getItemOptions().getNextLevelCreated() == null
				|| MVCConstants.NEXT_LEVEL_APPROVED.equals(itemBean.getItemOptions().getNextLevelCreated())) {
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.common.error.style.sku.status.approved", null, locale));
			result = Boolean.FALSE;
		}

		return result;

	}

	public void updateModelWithEssentialScreenData(ItemBeanDTO itemBeanDTO, Model model, Locale locale, Boolean skipStyleImage) {

		try {
			if (itemBeanDTO != null) {

				BigInteger styleNo = itemBeanDTO.getStyle().getItemId();
				if (styleNo != null) {
					Item style = this.itemService.getStyle(styleNo);
					ItemBean styleBean = ItemTransformer.transformItem(style);
					styleBean.setSelectedAttributes(itemBeanDTO.getStyle().getSelectedAttributes());
					itemBeanDTO.setStyle(styleBean);
				}

				if (!skipStyleImage)
					this.prepareImagesForDisplay(itemBeanDTO.getStyle().getItemImages());

				List<AttributeBean> attrList = itemBeanDTO.getStyle().getSelectedAttributes();
				if (attrList != null && !attrList.isEmpty()) {
					List<String> attrCodes = ItemTransformer.getAttrCodes(attrList);
					Map<String, List<Attribute>> attrValueMap = this.itemService.retrieveAttrListValues(attrCodes);
					if (attrValueMap != null && attrValueMap.size() > 0)
						ItemTransformer.updateAttrValues(attrList, attrValueMap);
					logger.info("The attribute value list has been successfully retrieved");
				}

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
			if (style != null && itemBean.getSkus() != null && !itemBean.getSkus().isEmpty()) {

				List<Item> skuList = ItemTransformer.createSKUs(style, itemBean, userDetails.getUsername(), MVCConstants.ACTION_NEW);

				Boolean nextLevelCreated = Boolean.FALSE;
				if (style.getItemOptions().getNextLevelCreated() != null
						&& (MVCConstants.NEXT_LEVEL_CREATED.equals(style.getItemOptions().getNextLevelCreated())
								|| MVCConstants.NEXT_LEVEL_APPROVED.equals(style.getItemOptions().getNextLevelCreated())))
					nextLevelCreated = Boolean.TRUE;
				skuList = this.itemService.createSKUs(skuList, userDetails.getUsername(), nextLevelCreated);
				if (skuList != null && !skuList.isEmpty()) {

					List<ItemBean> skuBeanList = ItemTransformer.transformItems(skuList);
					itemBean.setSkus(skuBeanList);

					model.addAttribute(MVCConstants.SUCCESS, messageSource.getMessage("commerce.screen.sku.add.success", null, locale));

					logger.info("The new skus has been created successfully");
				} else {
					model.addAttribute(MVCConstants.ALERT, messageSource.getMessage("commerce.screen.sku.add.failure", null, locale));
					logger.info("The new skus creation has failed due to some issues");
				}
				this.updateModelWithEssentialScreenData(itemBean, model, locale, Boolean.TRUE);
			} else {
				model.addAttribute(MVCConstants.ALERT, messageSource.getMessage("commerce.screen.sku.add.failure", null, locale));
				logger.info("The new skus creation has failed due to some issues");
			}
		} catch (Exception e) {
			this.updateModelWithEssentialScreenData(itemBean, model, locale, Boolean.TRUE);
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			logger.error("There was some error while handling the sku creation related tasks", e);
		}

		return ViewPathConstants.ADD_SKU_PAGE;
	}

	@PostMapping(value = ViewPathConstants.ADD_SKU_URL, params = { MVCConstants.SAVE_APPROVE_SKU_PARAM })
	public String approveSKUs(@ModelAttribute @Validated(ValidationGroup.ValidationGroupSKU.class) ItemBeanDTO itemBean, BindingResult bindingResult,
			Model model, HttpSession session, Authentication authentication, Locale locale) {

		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();

			Item style = this.itemService.getStyle(itemBean.getStyle().getItemId());
			if (style != null) {

				List<Item> skuList = ItemTransformer.createSKUs(style, itemBean, userDetails.getUsername(), MVCConstants.ACTION_NEW_APPROVE);

				Boolean nextLevelCreated = Boolean.FALSE;
				if (style.getItemOptions().getNextLevelCreated() != null
						&& (MVCConstants.NEXT_LEVEL_CREATED.equals(style.getItemOptions().getNextLevelCreated())
								|| MVCConstants.NEXT_LEVEL_APPROVED.equals(style.getItemOptions().getNextLevelCreated())))
					nextLevelCreated = Boolean.TRUE;
				skuList = this.itemService.createSKUs(skuList, userDetails.getUsername(), nextLevelCreated);
				if (skuList != null && !skuList.isEmpty()) {
					this.itemService.updateItemIndexes();
					List<ItemBean> skuBeanList = ItemTransformer.transformItems(skuList);
					itemBean.setSkus(skuBeanList);

					model.addAttribute(MVCConstants.SUCCESS, messageSource.getMessage("commerce.screen.sku.approve.success", null, locale));

					logger.info("The new skus has been save and approved successfully");
				} else {
					model.addAttribute(MVCConstants.ALERT, messageSource.getMessage("commerce.screen.sku.approve.failure", null, locale));
					logger.info("The new skus save and approval has failed due to some issues");
				}
				this.updateModelWithEssentialScreenData(itemBean, model, locale, Boolean.TRUE);
			} else {
				model.addAttribute(MVCConstants.ALERT, messageSource.getMessage("commerce.screen.sku.approve.failure", null, locale));
				logger.info("The new skus save and approval has failed due to some issues");
			}
		} catch (Exception e) {
			this.updateModelWithEssentialScreenData(itemBean, model, locale, Boolean.TRUE);
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			logger.error("There was some error while handling the sku save and approval related tasks", e);
		}

		return ViewPathConstants.ADD_SKU_PAGE;
	}

	@GetMapping(ViewPathConstants.EDIT_SKU_URL)
	public String showSKUForEdit(Model model, final HttpServletRequest req, Locale locale) {

		logger.info("The edit method for item has been called");
		ItemBean itemBean = null;
		List<AttributeBean> attributeBeans = null;
		ItemBeanDTO itemDTO = null;
		try {
			BigInteger styleNumber = new BigInteger(req.getParameter(MVCConstants.STYLE_ID_PARAM));
			List<ItemBean> skuList = null;
			Item item = null;
			if (BigInteger.ZERO.compareTo(styleNumber) < 0) {
				item = this.itemService.getItem(styleNumber);
			}
			if (item != null) {

				itemBean = ItemTransformer.transformItem(item);

				if (item.getItemOptions().getNextLevelCreated() != null
						&& item.getItemOptions().getNextLevelCreated().equals(MVCConstants.NEXT_LEVEL_APPROVED)) {
					Item itemCriteria = new Item();
					itemCriteria.setParentItemId(styleNumber);
					List<Item> skuItemList = this.itemService.retrieveItems(itemCriteria);
					if (skuItemList != null && !skuItemList.isEmpty()) {
						List<ItemAttribute> itemAttributes = skuItemList.get(0).getItemAttributes();

						List<Attribute> attributes = new ArrayList<>(itemAttributes.size());
						Attribute attr = null;
						for (ItemAttribute itemAttribute : itemAttributes) {
							attr = itemAttribute.getItemAttributeId().getAttribute();
							attributes.add(attr);
						}
						attributeBeans = ItemTransformer.transformAttributes(attributes);

						List<String> attrCodeList = ItemTransformer.retrieveSelectedAttributes(skuItemList);
						Map<String, List<Attribute>> attrValueMap = this.itemService.retrieveAttrListValues(attrCodeList);
						if (attrValueMap != null && attrValueMap.size() > 0)
							ItemTransformer.updateAttrValues(attributeBeans, attrValueMap);
						logger.info("The attribute value list has been successfully retrieved");

						skuList = ItemTransformer.transformItems(skuItemList);

						itemDTO = new ItemBeanDTO();
						itemDTO.setSkus(skuList);
						itemBean.setSelectedAttributes(attributeBeans);
						itemDTO.setStyle(itemBean);

						logger.info("The selected style details has been retrieved successfully for sku creation");

					} else {
						logger.info("The SKU does not exist for the provided style details");
					}

				} else {
					logger.info("The SKU does not exist in approved status to be edited for this style.");
				}

			} else {
				logger.info("There was some issue while retrieving style details for sku creation");
			}
			this.updateModelWithEssentialScreenData(itemDTO, model, locale, Boolean.FALSE);

		} catch (Exception e) {
			logger.error("There is an error while retrieving item for updation", e);
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			this.updateModelWithEssentialScreenData(itemDTO, model, locale, Boolean.FALSE);
			return ViewPathConstants.EDIT_SKU_PAGE;
		}
		return ViewPathConstants.EDIT_SKU_PAGE;
	}
	
	
	private ItemBeanDTO updateSKUDetailsForEditScreen(List<Item> skuItemList, ItemBean itemBean) throws IOException {
		List<AttributeBean> attributeBeans = null;
		ItemBeanDTO itemDTO=null;
		List<ItemBean> skuList = null;
		if (skuItemList != null && !skuItemList.isEmpty()) {
			List<ItemAttribute> itemAttributes = skuItemList.get(0).getItemAttributes();

			List<Attribute> attributes = new ArrayList<>(itemAttributes.size());
			Attribute attr = null;
			for (ItemAttribute itemAttribute : itemAttributes) {
				attr = itemAttribute.getItemAttributeId().getAttribute();
				attributes.add(attr);
			}
			attributeBeans = ItemTransformer.transformAttributes(attributes);

			List<String> attrCodeList = ItemTransformer.retrieveSelectedAttributes(skuItemList);
			Map<String, List<Attribute>> attrValueMap = this.itemService.retrieveAttrListValues(attrCodeList);
			if (attrValueMap != null && attrValueMap.size() > 0)
				ItemTransformer.updateAttrValues(attributeBeans, attrValueMap);
			logger.info("The attribute value list has been successfully retrieved");

			skuList = ItemTransformer.transformItems(skuItemList);

			itemDTO = new ItemBeanDTO();
			itemDTO.setSkus(skuList);
			itemBean.setSelectedAttributes(attributeBeans);
			itemDTO.setStyle(itemBean);

			logger.info("The selected style details has been retrieved successfully for sku creation");

		} else {
			logger.info("The SKU does not exist for the provided style details");
		}
		return itemDTO;
	}
	
	

	@PostMapping(value = ViewPathConstants.EDIT_SKU_URL, params = { MVCConstants.SAVE_APPROVE_SKU_PARAM })
	public String approveSKUsAfterEditing(@ModelAttribute @Validated(ValidationGroup.ValidationGroupSKU.class) ItemBeanDTO itemBean,
			BindingResult bindingResult, Model model, HttpSession session, Authentication authentication, Locale locale) {

		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();

			Item skuRetrievalCriteria = new Item();
			skuRetrievalCriteria.setParentItemId(itemBean.getStyle().getItemId());
			List<Item> skuList = this.itemService.retrieveItems(skuRetrievalCriteria);
			if (skuList != null && !skuList.isEmpty()) {
				List<Item> updatedSKUs = ItemTransformer.updateSKUs(skuList, itemBean, userDetails.getUsername());
				updatedSKUs = this.itemService.updateSKUs(updatedSKUs);
				this.itemService.updateItemIndexes();
				List<ItemBean> skuBeanList = ItemTransformer.transformItems(updatedSKUs);
				itemBean.setSkus(skuBeanList);
				
				
				Item style=this.itemService.getItem(itemBean.getStyle().getItemId());
				ItemBean styleDetails=ItemTransformer.transformItem(style);
				
				itemBean=this.updateSKUDetailsForEditScreen(updatedSKUs, styleDetails);
				this.updateModelWithEssentialScreenData(itemBean, model, locale, Boolean.TRUE);
				
				model.addAttribute(MVCConstants.SUCCESS, messageSource.getMessage("commerce.screen.sku.approve.success", null, locale));

				logger.info("All the SKUs has been updated successfully");
			} else {
				this.updateModelWithEssentialScreenData(itemBean, model, locale, Boolean.TRUE);
				model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.item.edit.sku.notfound", null, locale));
				logger.info("There were no SKUs found for the editing");
			}

		} catch (Exception e) {
			this.updateModelWithEssentialScreenData(itemBean, model, locale, Boolean.TRUE);
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			logger.error("There was some error while handling the sku save and approval related tasks", e);
		}

		return ViewPathConstants.EDIT_SKU_PAGE;
	}

}
