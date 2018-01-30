package com.punj.app.ecommerce.controller.item;
/**
 * 
 */

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.domains.item.Attribute;
import com.punj.app.ecommerce.domains.item.Hierarchy;
import com.punj.app.ecommerce.domains.item.Item;
import com.punj.app.ecommerce.domains.item.ItemAttribute;
import com.punj.app.ecommerce.domains.item.ItemDTO;
import com.punj.app.ecommerce.domains.item.ItemImage;
import com.punj.app.ecommerce.domains.item.comparators.AttributeComparator;
import com.punj.app.ecommerce.models.common.SearchBean;
import com.punj.app.ecommerce.models.item.AttributeBean;
import com.punj.app.ecommerce.models.item.HierarchyBean;
import com.punj.app.ecommerce.models.item.ItemBean;
import com.punj.app.ecommerce.models.item.ItemBeanDTO;
import com.punj.app.ecommerce.models.item.ItemOptionsBean;
import com.punj.app.ecommerce.services.ItemService;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class SearchItemController {
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

	@GetMapping(ViewPathConstants.LIST_ITEM_URL)
	public String listItems(@RequestParam(MVCConstants.PAGE_PARAM) Optional<Integer> page, Model model, HttpSession session) {
		try {
			Pager pager = new Pager();
			if (!page.isPresent()) {
				pager.setCurrentPageNo(1);
			} else {
				pager.setCurrentPageNo(page.get());
			}

			Item itemCriteria = new Item();

			ItemDTO itemList = itemService.listItems(itemCriteria, pager);
			List<Item> itemsList = itemList.getItems();

			ItemBeanDTO items = new ItemBeanDTO();

			this.setItemList(itemsList, items);

			Pager tmpPager = itemList.getPager();
			pager = new Pager(tmpPager.getResultSize(), tmpPager.getPageSize(), tmpPager.getCurrentPageNo(),
					tmpPager.getMaxDisplayPage(), ViewPathConstants.LIST_ITEM_URL);

			model.addAttribute("items", items);
			model.addAttribute(MVCConstants.PAGER, pager);
			model.addAttribute(MVCConstants.SUCCESS, "The {" + pager.getResultSize() + "} items record has been retrieved");
			logger.info("All the items has been retrieved successfully.");
		} catch (Exception e) {
			logger.error("There is an error while retrieving items", e);
			return ViewPathConstants.ERROR_PAGE;
		}
		return ViewPathConstants.DISPLAY_PAGE;
	}

	@GetMapping(ViewPathConstants.SEARCH_ITEM_URL)
	public String searchItem(Model model, HttpSession session) {
		model.addAttribute("searchBean", new SearchBean());
		logger.info("The item details has been retrieved successfully.");
		return ViewPathConstants.MANAGE_ITEM_PAGE;
	}

	@PostMapping(ViewPathConstants.SEARCH_ITEM_URL)
	public String searchItems(@ModelAttribute @Valid SearchBean searchBean, BindingResult bindingResult,
			@RequestParam(MVCConstants.PAGE_PARAM) Optional<Integer> page, Model model, HttpSession session) {
		if (bindingResult.hasErrors())
			return ViewPathConstants.MANAGE_ITEM_PAGE;
		try {

			Pager pager = new Pager();
			if (!page.isPresent()) {
				pager.setCurrentPageNo(1);
			} else {
				pager.setCurrentPageNo(page.get());
			}

			ItemDTO itemList = itemService.searchItem(searchBean.getSearchText(), pager);
			List<Item> itemsList = itemList.getItems();

			ItemBeanDTO items = new ItemBeanDTO();

			this.setItemList(itemsList, items);

			Pager tmpPager = itemList.getPager();
			pager = new Pager(tmpPager.getResultSize(), tmpPager.getPageSize(), tmpPager.getCurrentPageNo(),
					tmpPager.getMaxDisplayPage(), ViewPathConstants.SEARCH_ITEM_URL);

			model.addAttribute("items", items);
			model.addAttribute("searchBean", searchBean);
			model.addAttribute(MVCConstants.PAGER, pager);
			model.addAttribute(MVCConstants.SUCCESS,
					"The {" + pager.getResultSize() + "} items record has been retrieved");
			logger.info("The searched item details has been retrieved successfully.");
		} catch (Exception e) {
			logger.error("There is an error while retrieving items", e);
			return ViewPathConstants.ERROR_PAGE;
		}

		return ViewPathConstants.MANAGE_ITEM_PAGE;
	}

	private void setItemList(List<Item> itemsList, ItemBeanDTO items) {
		List<ItemBean> itemBeanList = new ArrayList<ItemBean>();
		ItemBean itemBean = null;
		HierarchyBean hierarchyBean = null;
		for (Item item : itemsList) {
			itemBean = new ItemBean();
			/**
			 * Setting the basic information about the style
			 */
			itemBean.setName(item.getName());
			itemBean.setLongDesc(item.getDescription());
			itemBean.setItemId(item.getItemId());
			itemBean.setParentItemId(item.getParentItemId());

			// Setting the hierarchy information
			Hierarchy hierarchy = item.getHierarchy();
			hierarchyBean = new HierarchyBean();
			hierarchyBean.setHierarchyId(hierarchy.getHierarchyId());
			itemBean.setHierarchy(hierarchyBean);
			itemBean.setItemType(item.getItemType());

			itemBeanList.add(itemBean);

		}
		items.setItems(itemBeanList);
		logger.info("The item basic details has been set in bean List successfully");
	}

	@GetMapping(ViewPathConstants.ITEM_DETAIL_URL)
	public String getItemDetails(@RequestParam("itemNumber") Optional<BigInteger> itemNumber, Model model,
			HttpSession session, Locale locale) {
		try {
			if (itemNumber.isPresent()) {

				Item itemDomain = itemService.getItem(itemNumber.get());

				List<AttributeBean> colorList = new ArrayList<AttributeBean>();
				List<AttributeBean> sizeList = new ArrayList<AttributeBean>();

				ItemBean itemBean = this.updateBean(itemDomain, colorList, sizeList, locale);

				model.addAttribute("colorList", colorList);
				model.addAttribute("sizeList", sizeList);
				model.addAttribute(MVCConstants.ITEM_BEAN, itemBean);

				logger.info("All the items details has been retrieved successfully and ready for detail page.");
			} else {
				model.addAttribute(MVCConstants.ALERT, "The itemNumber is needed to retrieve the item details");
			}
		} catch (Exception e) {
			logger.error("There is an error while retrieving items", e);
			return ViewPathConstants.ERROR_PAGE;
		}
		return ViewPathConstants.ITEM_DETAIL_PAGE;
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
		List<String> featureList = new ArrayList<String>();
		List<String> imageUrlList = new ArrayList<String>();
		List<ItemImage> images = style.getImages();
		int count = 0;
		for (ItemImage image : images) {
			featureList.add(image.getItemImageId().getFeatureName());
			featureMap.put(image.getItemImageId().getFeatureName(), image.getImageURL());

			imageUrlList.add(count, image.getImageURL());
			;

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
		MonetaryAmount unitCostAmount = Monetary.getDefaultAmountFactory().setCurrency(currenyUnit)
				.setNumber(style.getItemOptions().getUnitCost()).create();

		MonetaryAmount suggestedAmount = Monetary.getDefaultAmountFactory().setCurrency(currenyUnit)
				.setNumber(style.getItemOptions().getSuggestedPrice()).create();
		MonetaryAmount compareAtAmount = Monetary.getDefaultAmountFactory().setCurrency(currenyUnit)
				.setNumber(style.getItemOptions().getCompareAtPrice()).create();
		MonetaryAmount currentAmount = Monetary.getDefaultAmountFactory().setCurrency(currenyUnit)
				.setNumber(style.getItemOptions().getCurrentPrice()).create();
		MonetaryAmount restockingAmount = Monetary.getDefaultAmountFactory().setCurrency(currenyUnit)
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

		List<Attribute> finalList = new ArrayList<Attribute>();
		List<ItemAttribute> attributeList = style.getItemAttributes();
		for (ItemAttribute itemAttr : attributeList) {
			finalList.add(itemAttr.getItemAttributeId().getAttribute());
		}

		Collections.sort(finalList, new AttributeComparator());
		this.updateAttributeBean(finalList, colorList, sizeList);

		logger.info("The item attributes has been set in bean object successfully");

		return itemBean;

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
}
