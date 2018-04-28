package com.punj.app.ecommerce.controller.item;
/**
 * 
 */

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.ItemTransformer;
import com.punj.app.ecommerce.domains.common.UOM;
import com.punj.app.ecommerce.domains.item.Attribute;
import com.punj.app.ecommerce.domains.item.Hierarchy;
import com.punj.app.ecommerce.domains.item.Item;
import com.punj.app.ecommerce.domains.item.ItemAttribute;
import com.punj.app.ecommerce.domains.item.ItemImage;
import com.punj.app.ecommerce.domains.item.ItemOptions;
import com.punj.app.ecommerce.domains.tax.TaxGroup;
import com.punj.app.ecommerce.models.item.AttributeBean;
import com.punj.app.ecommerce.models.item.ItemBean;
import com.punj.app.ecommerce.models.item.ItemImageBean;
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

	/**
	 * @param userService
	 *            the userService to set
	 */
	@Autowired
	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
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
	public String showStyle(Model model, HttpSession session) {

		ItemBean newItemBean = new ItemBean();

		List<AttributeBean> colorList = new ArrayList<>();
		List<AttributeBean> sizeList = new ArrayList<>();
		this.getDefaultAttributes(colorList, sizeList);

		List<TaxGroup> taxGroups = this.commonService.retrieveAllTaxGroups();
		model.addAttribute(MVCConstants.TAX_GROUP_LIST_BEAN, taxGroups);

		List<UOM> UOMs = this.commonService.retrieveAllUOMs();
		model.addAttribute(MVCConstants.UOM_LIST_BEAN, UOMs);

		model.addAttribute(MVCConstants.COLOR_LIST_BEAN, colorList);
		model.addAttribute(MVCConstants.SIZE_LIST_BEAN, sizeList);
		model.addAttribute(MVCConstants.ITEM_BEAN, newItemBean);
		logger.info("The default style object has been added to display on the screen");

		return ViewPathConstants.ADD_STYLE_PAGE;
	}

	private void getDefaultAttributes(List<AttributeBean> colorList, List<AttributeBean> sizeList) {
		List<Attribute> attributeList = itemService.getNewStyleAttribute();
		logger.info("The attribute list has been retrieved to select for the style");
		this.updateAttributeBean(attributeList, colorList, sizeList);
	}

	@PostMapping(value = ViewPathConstants.ADD_STYLE_URL, params = { MVCConstants.ADD_ITEM_IMAGE_PARAM })
	public String addItemImage(@ModelAttribute ItemBean itemBean, BindingResult bindingResult, Model model, HttpSession session,
			Authentication authentication) {
		List<ItemImageBean> itemImages = itemBean.getItemImages();
		if (itemImages == null)
			itemImages = new ArrayList<>();

		itemImages.add(new ItemImageBean());
		itemBean.setItemImages(itemImages);
		logger.info("An empty item image object has been created successfully");
		return ViewPathConstants.ADD_STYLE_PAGE;
	}

	@PostMapping(value = ViewPathConstants.ADD_STYLE_URL, params = { MVCConstants.REMOVE_ITEM_IMAGE_PARAM })
	public String removeItemImage(@ModelAttribute ItemBean itemBean, BindingResult bindingResult, Model model, final HttpServletRequest req) {

		final Integer rowId = Integer.parseInt(req.getParameter(MVCConstants.REMOVE_ITEM_IMAGE_PARAM));

		List<ItemImageBean> itemImages = itemBean.getItemImages();
		itemImages.remove(rowId.intValue());

		logger.info("The selected item image has been deleted from the item");
		return ViewPathConstants.ADD_STYLE_PAGE;
	}

	@PostMapping(value = ViewPathConstants.ADD_STYLE_URL, params = { MVCConstants.SAVE_ITEM_PARAM })
	public String addStyle(@ModelAttribute @Valid ItemBean itemBean, BindingResult bindingResult, Model model, HttpSession session,
			Authentication authentication) {
		if (bindingResult.hasErrors()) {
			List<AttributeBean> colorList = new ArrayList<>();
			List<AttributeBean> sizeList = new ArrayList<>();
			this.getDefaultAttributes(colorList, sizeList);
			model.addAttribute(MVCConstants.COLOR_LIST_BEAN, colorList);
			model.addAttribute(MVCConstants.SIZE_LIST_BEAN, sizeList);
			return ViewPathConstants.ADD_STYLE_PAGE;
		}
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			Item item = new Item();
			ItemOptions itemOptions = new ItemOptions();
			List<ItemAttribute> itemAttributes = new ArrayList<>();

			BigInteger styleNumber = itemService.generateNewStyle();
			itemBean.setItemId(styleNumber);
			logger.info("The new generated style number has been assigned to the item bean object");

			this.updateBeanInDomain(itemBean, item, itemOptions, itemAttributes, userDetails.getUsername());
			logger.info("The style details has been updated in domains object from the bean objects");

			itemService.saveItem(item, itemOptions, itemAttributes);
			logger.info("The style details has been saved successfully");

			List<Attribute> attributeList = itemService.getNewStyleAttribute();
			logger.info("The attribute list has been retrieved to select for the style");

			List<AttributeBean> colorList = new ArrayList<>();
			List<AttributeBean> sizeList = new ArrayList<>();

			this.updateAttributeBean(attributeList, colorList, sizeList);

			List<TaxGroup> taxGroups = this.commonService.retrieveAllTaxGroups();

			itemBean.setItemId(styleNumber);
			model.addAttribute(MVCConstants.COLOR_LIST_BEAN, colorList);
			model.addAttribute(MVCConstants.SIZE_LIST_BEAN, sizeList);
			model.addAttribute(MVCConstants.TAX_GROUP_LIST_BEAN, taxGroups);
			model.addAttribute(MVCConstants.ITEM_BEAN, itemBean);
			model.addAttribute(MVCConstants.SUCCESS, "The new style " + styleNumber + " has been created.");
		} catch (IOException e) {
			logger.error("There was some error while handling the item images related tasks", e);
		}

		return ViewPathConstants.ADD_STYLE_PAGE;
	}

	private void updateBeanInDomain(ItemBean itemBean, Item item, ItemOptions itemOptions, List<ItemAttribute> itemAttributes, String username)
			throws IOException {

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

		List<ItemImage> itemImages = ItemTransformer.tranformItemImageBeans(itemBean.getItemImages());
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

		logger.info("The item attributes has been set in domain object successfully");

	}

	private void updateAttributeBean(List<Attribute> attributeList, List<AttributeBean> colorList, List<AttributeBean> sizeList) {

		logger.info("All the attributes related to Color and Size has been sorted successfully");

	}

}
