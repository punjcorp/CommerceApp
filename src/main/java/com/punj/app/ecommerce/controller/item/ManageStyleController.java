package com.punj.app.ecommerce.controller.item;
/**
 * 
 */

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.domains.item.Attribute;
import com.punj.app.ecommerce.domains.item.Hierarchy;
import com.punj.app.ecommerce.domains.item.Item;
import com.punj.app.ecommerce.domains.item.ItemAttribute;
import com.punj.app.ecommerce.domains.item.ItemImage;
import com.punj.app.ecommerce.domains.item.ItemOptions;
import com.punj.app.ecommerce.domains.item.ids.AttributeId;
import com.punj.app.ecommerce.domains.item.ids.ItemAttributeId;
import com.punj.app.ecommerce.domains.item.ids.ItemImageId;
import com.punj.app.ecommerce.models.item.AttributeBean;
import com.punj.app.ecommerce.models.item.ItemBean;
import com.punj.app.ecommerce.services.ItemService;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class ManageStyleController {
	private static final Logger logger = LogManager.getLogger();
	private ItemService itemService;

	/**
	 * @param userService
	 *            the userService to set
	 */
	@Autowired
	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	@GetMapping("/add_style")
	public String showStyle(Model model, HttpSession session) {

		ItemBean newItemBean = new ItemBean();

		Map<String, String> featureMap = newItemBean.getItemImages();
		featureMap.put("Listing", "");
		featureMap.put("Detail", "");
		featureMap.put("Search", "");
		featureMap.put("Cart", "");

		newItemBean.setItemImages(featureMap);

		List<AttributeBean> colorList = new ArrayList<AttributeBean>();
		List<AttributeBean> sizeList = new ArrayList<AttributeBean>();
		this.getDefaultAttributes(colorList, sizeList);

		model.addAttribute("colorList", colorList);
		model.addAttribute("sizeList", sizeList);
		model.addAttribute(MVCConstants.ITEM_BEAN, newItemBean);
		logger.info("The default style object has been added to display on the screen");

		return "item/add_style";
	}
	
	private void getDefaultAttributes(List<AttributeBean> colorList,List<AttributeBean> sizeList) {
		List<Attribute> attributeList = itemService.getNewStyleAttribute();
		logger.info("The attribute list has been retrieved to select for the style");
		this.updateAttributeBean(attributeList, colorList, sizeList);
	}

	@PostMapping("/add_style")
	public String addStyle(@ModelAttribute @Valid ItemBean itemBean, BindingResult bindingResult, Model model, HttpSession session,
			Authentication authentication) {
		if (bindingResult.hasErrors()) {
			List<AttributeBean> colorList = new ArrayList<AttributeBean>();
			List<AttributeBean> sizeList = new ArrayList<AttributeBean>();
			this.getDefaultAttributes(colorList, sizeList);			
			model.addAttribute("colorList", colorList);
			model.addAttribute("sizeList", sizeList);
			return "item/add_style";
		}
		
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		Item item = new Item();
		ItemOptions itemOptions = new ItemOptions();
		List<ItemAttribute> itemAttributes = new ArrayList<ItemAttribute>();

		BigInteger styleNumber = itemService.generateNewStyle();
		itemBean.setItemId(styleNumber);
		logger.info("The new generated style number has been assigned to the item bean object");

		this.updateBeanInDomain(itemBean, item, itemOptions, itemAttributes, userDetails.getUsername());
		logger.info("The style details has been updated in domains object from the bean objects");

		itemService.saveItem(item, itemOptions, itemAttributes);
		logger.info("The style details has been saved successfully");

		List<Attribute> attributeList = itemService.getNewStyleAttribute();
		logger.info("The attribute list has been retrieved to select for the style");

		List<AttributeBean> colorList = new ArrayList<AttributeBean>();
		List<AttributeBean> sizeList = new ArrayList<AttributeBean>();

		this.updateAttributeBean(attributeList, colorList, sizeList);

		itemBean.setItemId(styleNumber);
		model.addAttribute("colorList", colorList);
		model.addAttribute("sizeList", sizeList);
		model.addAttribute(MVCConstants.ITEM_BEAN, itemBean);
		model.addAttribute(MVCConstants.SUCCESS, "The new style " + styleNumber + " has been created.");

		return "item/add_style";
	}

	private void updateBeanInDomain(ItemBean itemBean, Item item, ItemOptions itemOptions,
			List<ItemAttribute> itemAttributes, String username) {

		/**
		 * Setting the basic information about the style
		 */
		item.setItemId(itemBean.getItemId());
		item.setName(itemBean.getName());
		item.setDescription(itemBean.getLongDesc());

		Hierarchy hierarchy = new Hierarchy();
		hierarchy.setHierarchyId(itemBean.getHierarchy().getHierarchyId());
		item.setHierarchy(hierarchy);

		item.setItemType(itemBean.getItemType());
		item.setCreatedDate(LocalDateTime.now());
		item.setCreatedBy(username);
		item.setItemLevel(1);

		logger.info("The item basic details has been set in domain object successfully");

		/**
		 * Setting the images for the style
		 */
		Map<String, String> images = itemBean.getItemImages();
		List<ItemImage> itemImages = new ArrayList<ItemImage>();
		List<String> imageUrlList = itemBean.getImageUrlList();
		for (String featureName : itemBean.getFeatureList()) {
			ItemImage itemImage = new ItemImage();
			ItemImageId itemImageId = new ItemImageId();
			itemImageId.setFeatureName(featureName);
			itemImageId.setItemId(itemBean.getItemId());

			itemImage.setItemImageId(itemImageId);
			itemImage.setCreatedDate(LocalDateTime.now());
			itemImage.setCreatedBy(username);
			itemImage.setImageURL(images.get(featureName));
			itemImage.setName(itemBean.getName());

			itemImages.add(itemImage);
		}
		item.setImages(itemImages);

		logger.info("The item images has been set in domain object successfully");

		/**
		 * Setting the options information about the style
		 */

		itemOptions.setItemId(itemBean.getItemId());

		itemOptions.setUnitCost(new BigDecimal(itemBean.getItemOptions().getUnitCost().getNumber().toString()));
		itemOptions.setSuggestedPrice(new BigDecimal(itemBean.getItemOptions().getSuggestedPrice().getNumber().toString()));
		itemOptions.setCompareAtPrice(new BigDecimal(itemBean.getItemOptions().getCompareAtPrice().getNumber().toString()));
		itemOptions.setCurrentPrice(new BigDecimal(itemBean.getItemOptions().getCurrentPrice().getNumber().toString()));
		itemOptions.setRestockingFee(new BigDecimal(itemBean.getItemOptions().getRestockingFee().getNumber().toString()));

		itemOptions.setDiscountFlag(itemBean.getItemOptions().getDiscountFlag());
		itemOptions.setTaxFlag(itemBean.getItemOptions().getTaxFlag());
		itemOptions.setAskQtyFlag(itemBean.getItemOptions().getAskQtyFlag());
		itemOptions.setAskPriceFlag(itemBean.getItemOptions().getAskPriceFlag());
		itemOptions.setReturnFlag(itemBean.getItemOptions().getReturnFlag());
		itemOptions.setDescFlag(itemBean.getItemOptions().getDescFlag());
		itemOptions.setRelatedItemFlag(itemBean.getItemOptions().getRelatedItemFlag());
		itemOptions.setPriceChangeFlag(itemBean.getItemOptions().getPriceChangeFlag());
		itemOptions.setNonMerchFlag(itemBean.getItemOptions().getNonMerchFlag());
		itemOptions.setMinAgeFlag(itemBean.getItemOptions().getMinAgeFlag());
		itemOptions.setCustomerPrompt(itemBean.getItemOptions().getCustomerPromptFlag());

		itemOptions.setUom(itemBean.getItemOptions().getUom());
		itemOptions.setTaxGroupId(itemBean.getItemOptions().getTaxGroupId());
		itemOptions.setStockStatus(itemBean.getItemOptions().getStockStatus());
		itemOptions.setShippingWeight(itemBean.getItemOptions().getShippingWeight());
		itemOptions.setPackSize(itemBean.getItemOptions().getPackSize());

		logger.info("The item options has been set in domain object successfully");

		/**
		 * Setting the item attributes
		 */
		String[] colors = itemBean.getItemColorSelected();
		String[] sizes = itemBean.getItemSizeSelected();

		String[] splittedData = new String[2];
		for (String color : colors) {

			splittedData = color.split("_");
			if (splittedData.length > 1) {
				AttributeId attributeId = new AttributeId();

				attributeId.setAttributeId(new BigInteger(splittedData[0]));
				attributeId.setValue(splittedData[1]);

				Attribute attribute = new Attribute();
				attribute.setAttributeId(attributeId);

				ItemAttributeId itemAttributeId = new ItemAttributeId();
				itemAttributeId.setAttribute(attribute);
				itemAttributeId.setItem(item);

				ItemAttribute itemAttribute = new ItemAttribute();
				itemAttribute.setItemAttributeId(itemAttributeId);

				itemAttributes.add(itemAttribute);

			}

		}

		for (String size : sizes) {
			splittedData = size.split("_");
			if (splittedData.length > 1) {
				AttributeId attributeId = new AttributeId();

				attributeId.setAttributeId(new BigInteger(splittedData[0]));
				attributeId.setValue(splittedData[1]);

				Attribute attribute = new Attribute();
				attribute.setAttributeId(attributeId);

				ItemAttributeId itemAttributeId = new ItemAttributeId();
				itemAttributeId.setAttribute(attribute);
				itemAttributeId.setItem(item);

				ItemAttribute itemAttribute = new ItemAttribute();
				itemAttribute.setItemAttributeId(itemAttributeId);

				itemAttributes.add(itemAttribute);

			}

		}

		logger.info("The item attributes has been set in domain object successfully");

	}

	private void updateAttributeBean(List<Attribute> attributeList, List<AttributeBean> colorList,
			List<AttributeBean> sizeList) {

		for (Attribute attribute : attributeList) {

			AttributeBean attrBean = new AttributeBean();
			attrBean.setAttributeId(attribute.getAttributeId().getAttributeId());
			attrBean.setCode(attribute.getCode());
			attrBean.setName(attribute.getName());
			attrBean.setDescription(attribute.getDescription());
			attrBean.setValue(attribute.getAttributeId().getValue());

			switch ((attribute.getCode().toCharArray())[0]) {
			case 'C':
				colorList.add(attrBean);
				break;
			case 'S':
				sizeList.add(attrBean);
				break;
			default:
				logger.warn("Unknown attribute found!!");
			}

		}
		logger.info("All the attributes related to Color and Size has been sorted successfully");

	}

	private void updateColorSizeLists(List<AttributeBean> colorList, List<AttributeBean> sizeList, String[] colors,
			String[] sizes) {
		String[] splittedData = new String[2];
		for (String color : colors) {

			splittedData = color.split("_");
			if (splittedData.length > 1) {
				AttributeBean attributeBean = new AttributeBean();
				attributeBean.setAttributeId(new BigInteger(splittedData[0]));
				attributeBean.setValue(splittedData[1]);

				colorList.add(attributeBean);

			}

		}

		for (String size : sizes) {
			splittedData = size.split("_");
			if (splittedData.length > 1) {
				AttributeBean attributeBean = new AttributeBean();
				attributeBean.setAttributeId(new BigInteger(splittedData[0]));
				attributeBean.setValue(splittedData[1]);

				sizeList.add(attributeBean);

			}

		}

		logger.info("The item attributes color and sizes has been set in bean objects successfully");

	}
}
