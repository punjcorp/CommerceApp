package com.punj.app.ecommerce.controller.item;
/**
 * 
 */

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

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
import com.punj.app.ecommerce.controller.common.transformer.ItemTransformer;
import com.punj.app.ecommerce.domains.item.Attribute;
import com.punj.app.ecommerce.domains.item.Hierarchy;
import com.punj.app.ecommerce.domains.item.Item;
import com.punj.app.ecommerce.domains.item.ItemAttribute;
import com.punj.app.ecommerce.domains.item.ItemImage;
import com.punj.app.ecommerce.domains.item.ItemOptions;
import com.punj.app.ecommerce.domains.item.comparators.AttributeComparator;
import com.punj.app.ecommerce.models.item.AttributeBean;
import com.punj.app.ecommerce.models.item.HierarchyBean;
import com.punj.app.ecommerce.models.item.ItemBean;
import com.punj.app.ecommerce.models.item.ItemImageBean;
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
	public String showItem(@RequestParam("styleNumber") String styleNo, Model model, HttpSession session, Locale locale) {

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
	public String addItem(@ModelAttribute ItemBean itemBean, Model model, HttpSession session, Authentication authentication, Locale locale) {
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();

			Item item = new Item();
			ItemOptions itemOptions = new ItemOptions();

			this.updateBeanInDomain(itemBean, item, itemOptions, userDetails.getUsername());

			logger.info("The item details has been updated in domains object from the bean objects");

			logger.info("The item attribute list has been updated in Domain objects");

			List<Item> finalSKUs = itemService.createSKUs(item, itemOptions);
			logger.info("The item SKUs  has been saved successfully");

			model.addAttribute("SKUs", finalSKUs);
			model.addAttribute(MVCConstants.ITEM_BEAN, itemBean);
			model.addAttribute(MVCConstants.SUCCESS, "The new SKU(s) has been created successfully.");
		} catch (IOException e) {
			logger.error("There was some error while handling the item images related tasks", e);
		}
		return "item/add_item";
	}

	private void updateBeanInDomain(ItemBean itemBean, Item item, ItemOptions itemOptions, String username) throws IOException {

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
		List<ItemImage> itemImages = ItemTransformer.transformItemImageBeans(itemBean.getItemImages(),username, item);
		item.setImages(itemImages);

		logger.info("The item images has been set in domain object successfully");

		/**
		 * Setting the options information about the style
		 */

		itemOptions.setUnitCost(itemBean.getItemOptions().getUnitCost());
		itemOptions.setSuggestedPrice(itemBean.getItemOptions().getSuggestedPrice());
		itemOptions.setCompareAtPrice(itemBean.getItemOptions().getCompareAtPrice());
		itemOptions.setCurrentPrice(itemBean.getItemOptions().getCurrentPrice());
		itemOptions.setRestockingFee(itemBean.getItemOptions().getRestockingFee());

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

	private void updateAttributeBean(List<Attribute> attributeList, List<AttributeBean> colorList, List<AttributeBean> sizeList) {

		for (Attribute attribute : attributeList) {

			AttributeBean attrBean = new AttributeBean();
			attrBean.setAttributeId(attribute.getAttributeId());
			attrBean.setCode(attribute.getCode());
			attrBean.setName(attribute.getName());
			attrBean.setDescription(attribute.getDescription());
			attrBean.setValCode(attribute.getValCode());

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

	private ItemBean updateBean(Item style, List<AttributeBean> colorList, List<AttributeBean> sizeList, Locale locale) {
		ItemBean itemBean = new ItemBean();

		/**
		 * Setting the image feature list
		 */

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
		List<ItemImageBean> itemImageBeans=null;
		try {
			itemImageBeans = ItemTransformer.tranformItemImages(style.getImages());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		itemBean.setItemImages(itemImageBeans);
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

		itemOptionsBean.setUnitCost(style.getItemOptions().getUnitCost());
		itemOptionsBean.setSuggestedPrice(style.getItemOptions().getSuggestedPrice());
		itemOptionsBean.setCompareAtPrice(style.getItemOptions().getCompareAtPrice());
		itemOptionsBean.setCurrentPrice(style.getItemOptions().getCurrentPrice());
		itemOptionsBean.setRestockingFee(style.getItemOptions().getRestockingFee());

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

}
