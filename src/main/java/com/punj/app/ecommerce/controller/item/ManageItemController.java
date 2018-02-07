package com.punj.app.ecommerce.controller.item;
/**
 * 
 */

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.domains.item.Attribute;
import com.punj.app.ecommerce.domains.item.Hierarchy;
import com.punj.app.ecommerce.domains.item.Item;
import com.punj.app.ecommerce.domains.item.ItemAttribute;
import com.punj.app.ecommerce.domains.item.ItemImage;
import com.punj.app.ecommerce.domains.item.ItemOptions;
import com.punj.app.ecommerce.domains.item.comparators.AttributeComparator;
import com.punj.app.ecommerce.domains.item.ids.AttributeId;
import com.punj.app.ecommerce.domains.item.ids.ItemImageId;
import com.punj.app.ecommerce.models.item.AttributeBean;
import com.punj.app.ecommerce.models.item.HierarchyBean;
import com.punj.app.ecommerce.models.item.ItemBean;
import com.punj.app.ecommerce.models.item.ItemOptionsBean;
import com.punj.app.ecommerce.services.ItemService;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class ManageItemController {
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

	@GetMapping("/add_item")
	public String showItem(@RequestParam("styleNumber") String styleNo, Model model, HttpSession session,
			Locale locale) {

		BigInteger styleNumber = new BigInteger(styleNo);

		Item style = itemService.getStyle(styleNumber);

		List<AttributeBean> colorList = new ArrayList<>();
		List<AttributeBean> sizeList = new ArrayList<>();

		ItemBean itemBean = this.updateBean(style, colorList, sizeList, locale);

		model.addAttribute("colorList", colorList);
		model.addAttribute("sizeList", sizeList);
		model.addAttribute(MVCConstants.ITEM_BEAN, itemBean);
		logger.info("The default SKU object has been added to display on the screen");

		return "item/add_item";
	}

	@PostMapping("/add_item")
	public String addItem(@ModelAttribute ItemBean itemBean, Model model, HttpSession session,
			Authentication authentication, Locale locale) {

		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		Item item = new Item();
		ItemOptions itemOptions = new ItemOptions();
		List<AttributeId> attributeIds = new ArrayList<>();

		this.updateBeanInDomain(itemBean, item, itemOptions, userDetails.getUsername());
		logger.info("The item details has been updated in domains object from the bean objects");

		this.updateItemAttributes(itemBean, attributeIds);
		logger.info("The item attribute list has been updated in Domain objects");

		List<Item> finalSKUs = itemService.createSKUs(item, itemOptions, attributeIds);
		logger.info("The item SKUs  has been saved successfully");

		model.addAttribute("SKUs", finalSKUs);
		model.addAttribute(MVCConstants.ITEM_BEAN, itemBean);
		model.addAttribute(MVCConstants.SUCCESS, "The new SKU(s) has been created successfully.");

		return "item/add_item";
	}

	private void updateBeanInDomain(ItemBean itemBean, Item item, ItemOptions itemOptions, String username) {

		/**
		 * Setting the basic information about the style
		 */
		item.setParentItemId(itemBean.getParentItemId());
		item.setName(itemBean.getName());
		item.setDescription(itemBean.getLongDesc());

		Hierarchy hierarchy = new Hierarchy();
		hierarchy.setHierarchyId(itemBean.getHierarchy().getHierarchyId());
		item.setHierarchy(hierarchy);

		item.setItemType(itemBean.getItemType());
		item.setCreatedDate(LocalDateTime.now());
		item.setCreatedBy(username);
		item.setItemLevel(2);

		logger.info("The item basic details has been set in domain object successfully");

		/**
		 * Setting the images for the style
		 */
		Map<String, String> images = itemBean.getItemImages();
		List<ItemImage> itemImages = new ArrayList<>();
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

		itemOptions.setUnitCost(new BigDecimal(itemBean.getItemOptions().getUnitCost().getNumber().toString()));
		itemOptions.setSuggestedPrice(
				new BigDecimal(itemBean.getItemOptions().getSuggestedPrice().getNumber().toString()));
		itemOptions.setCompareAtPrice(
				new BigDecimal(itemBean.getItemOptions().getCompareAtPrice().getNumber().toString()));
		itemOptions.setCurrentPrice(new BigDecimal(itemBean.getItemOptions().getCurrentPrice().getNumber().toString()));
		itemOptions
				.setRestockingFee(new BigDecimal(itemBean.getItemOptions().getRestockingFee().getNumber().toString()));

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

	private ItemBean updateBean(Item style, List<AttributeBean> colorList, List<AttributeBean> sizeList,
			Locale locale) {
		ItemBean itemBean = new ItemBean();

		/**
		 * Setting the image feature list
		 */
		Map<String, String> featureMap = itemBean.getItemImages();
		featureMap.put("Listing", "");
		featureMap.put("Detail", "");
		featureMap.put("Search", "");
		featureMap.put("Cart", "");

		/**
		 * Setting the basic information about the style
		 */
		itemBean.setName(style.getName());
		itemBean.setLongDesc(style.getDescription());
		itemBean.setParentItemId(style.getItemId());

		// Setting the hierarchy information
		Hierarchy hierarchy = style.getHierarchy();
		HierarchyBean hierarchyBean = new HierarchyBean();
		hierarchyBean.setHierarchyId(hierarchy.getHierarchyId());

		itemBean.setHierarchy(hierarchyBean);

		itemBean.setItemType(style.getItemType());
		logger.info("The item basic details has been set in bean object successfully");

		/**
		 * Setting the images for the item
		 */
		List<String> featureList = new ArrayList<>();
		List<String> imageUrlList = new ArrayList<>();
		List<ItemImage> images = style.getImages();
		int count = 0;
		for (ItemImage image : images) {
			featureList.add(image.getItemImageId().getFeatureName());
			featureMap.put(image.getItemImageId().getFeatureName(), image.getImageURL());

			imageUrlList.add(count, image.getImageURL());

			count++;
		}
		itemBean.setItemImages(featureMap);

		itemBean.setImageUrlList(imageUrlList);
		itemBean.setFeatureList(featureList);

		logger.info("The item images has been set in bean object successfully");

		/**
		 * Setting the options information about the style
		 */

		ItemOptionsBean itemOptionsBean = new ItemOptionsBean();

		CurrencyUnit currenyUnit = Monetary.getCurrency(locale);
		MonetaryAmount unitCostAmount = null;
		MonetaryAmount suggestedAmount = null;
		MonetaryAmount compareAtAmount = null;
		MonetaryAmount currentAmount = null;
		MonetaryAmount restockingAmount = null;

		if (style.getItemOptions().getUnitCost() != null)
			unitCostAmount = Monetary.getDefaultAmountFactory().setCurrency(currenyUnit)
					.setNumber(style.getItemOptions().getUnitCost()).create();
		if (style.getItemOptions().getSuggestedPrice() != null)
			suggestedAmount = Monetary.getDefaultAmountFactory().setCurrency(currenyUnit)
					.setNumber(style.getItemOptions().getSuggestedPrice()).create();
		if (style.getItemOptions().getCompareAtPrice() != null)
			compareAtAmount = Monetary.getDefaultAmountFactory().setCurrency(currenyUnit)
					.setNumber(style.getItemOptions().getCompareAtPrice()).create();
		if (style.getItemOptions().getCurrentPrice() != null)
			currentAmount = Monetary.getDefaultAmountFactory().setCurrency(currenyUnit)
					.setNumber(style.getItemOptions().getCurrentPrice()).create();
		if (style.getItemOptions().getRestockingFee() != null)
			restockingAmount = Monetary.getDefaultAmountFactory().setCurrency(currenyUnit)
					.setNumber(style.getItemOptions().getRestockingFee()).create();

		itemOptionsBean.setUnitCost(unitCostAmount);
		itemOptionsBean.setSuggestedPrice(suggestedAmount);
		itemOptionsBean.setCompareAtPrice(compareAtAmount);
		itemOptionsBean.setCurrentPrice(currentAmount);
		itemOptionsBean.setRestockingFee(restockingAmount);

		itemOptionsBean.setDiscountFlag(style.getItemOptions().getDiscountFlag());
		itemOptionsBean.setTaxFlag(style.getItemOptions().getTaxFlag());
		itemOptionsBean.setAskQtyFlag(style.getItemOptions().getAskQtyFlag());
		itemOptionsBean.setAskPriceFlag(style.getItemOptions().getAskPriceFlag());
		itemOptionsBean.setReturnFlag(style.getItemOptions().getReturnFlag());
		itemOptionsBean.setDescFlag(style.getItemOptions().getDescFlag());
		itemOptionsBean.setRelatedItemFlag(style.getItemOptions().getRelatedItemFlag());
		itemOptionsBean.setPriceChangeFlag(style.getItemOptions().getPriceChangeFlag());
		itemOptionsBean.setNonMerchFlag(style.getItemOptions().getNonMerchFlag());
		itemOptionsBean.setMinAgeFlag(style.getItemOptions().getMinAgeFlag());
		itemOptionsBean.setCustomerPromptFlag(style.getItemOptions().getCustomerPrompt());

		itemOptionsBean.setUom(style.getItemOptions().getUom());
		itemOptionsBean.setTaxGroupId(style.getItemOptions().getTaxGroupId());
		itemOptionsBean.setStockStatus(style.getItemOptions().getStockStatus());
		itemOptionsBean.setShippingWeight(style.getItemOptions().getShippingWeight());
		itemOptionsBean.setPackSize(style.getItemOptions().getPackSize());

		itemBean.setItemOptions(itemOptionsBean);

		logger.info("The item options has been set in bean object successfully");

		List<Attribute> finalList = new ArrayList<>();
		List<ItemAttribute> attributeList = style.getItemAttributes();
		for (ItemAttribute itemAttr : attributeList) {
			finalList.add(itemAttr.getItemAttributeId().getAttribute());
		}

		Collections.sort(finalList, new AttributeComparator());
		this.updateAttributeBean(finalList, colorList, sizeList);

		logger.info("The item attributes has been set in bean object successfully");

		return itemBean;

	}

	private void updateItemAttributes(ItemBean itemBean, List<AttributeId> itemAttributes) {
		/**
		 * Setting the item attributes
		 */
		String[] colors = itemBean.getItemColorSelected();
		String[] sizes = itemBean.getItemSizeSelected();

		AttributeId attributeId = null;
		String[] splittedData = null;
		for (String color : colors) {
			splittedData = color.split("_");
			if (splittedData.length > 1) {
				attributeId = new AttributeId();
				attributeId.setAttributeId(new BigInteger(splittedData[0]));
				attributeId.setValue(splittedData[1]);
				itemAttributes.add(attributeId);
			}
		}

		for (String size : sizes) {
			splittedData = size.split("_");
			if (splittedData.length > 1) {
				attributeId = new AttributeId();
				attributeId.setAttributeId(new BigInteger(splittedData[0]));
				attributeId.setValue(splittedData[1]);
				itemAttributes.add(attributeId);
			}
		}

		logger.info("The item attributes has been set in ATTRIBUTE domain object successfully");
	}
}
