/**
 * 
 */
package com.punj.app.ecommerce.controller.common.transformer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.domains.item.Attribute;
import com.punj.app.ecommerce.domains.item.Hierarchy;
import com.punj.app.ecommerce.domains.item.Item;
import com.punj.app.ecommerce.domains.item.ItemAttribute;
import com.punj.app.ecommerce.domains.item.ItemImage;
import com.punj.app.ecommerce.domains.item.ItemOptions;
import com.punj.app.ecommerce.domains.item.LocSKUDetails;
import com.punj.app.ecommerce.domains.item.ids.ItemAttributeId;
import com.punj.app.ecommerce.models.common.CommerceMultipartFile;
import com.punj.app.ecommerce.models.item.AttributeBean;
import com.punj.app.ecommerce.models.item.HierarchyBean;
import com.punj.app.ecommerce.models.item.ItemBean;
import com.punj.app.ecommerce.models.item.ItemBeanDTO;
import com.punj.app.ecommerce.models.item.ItemImageBean;
import com.punj.app.ecommerce.models.item.ItemOptionsBean;
import com.punj.app.ecommerce.models.lookup.ItemLookupBean;
import com.punj.app.ecommerce.services.dtos.SaleItem;
import com.punj.app.ecommerce.services.dtos.SaleItemTax;

/**
 * @author admin
 *
 */
public class ItemTransformer {

	private static final Logger logger = LogManager.getLogger();

	private ItemTransformer() {
		throw new IllegalStateException("ItemTransformer class");
	}

	public static List<ItemLookupBean> transformItemsForLookup(List<Item> items) {
		List<ItemLookupBean> itemLookupBeans = new ArrayList<>(items.size());
		ItemLookupBean itemLookupBean = null;
		for (Item item : items) {
			itemLookupBean = ItemTransformer.transformItemForLookup(item);
			itemLookupBeans.add(itemLookupBean);
		}

		logger.info("The item list has been transformed to item lookup beans successfully.");
		return itemLookupBeans;
	}

	public static ItemLookupBean transformItemForLookup(Item item) {
		ItemLookupBean itemLookupBean = new ItemLookupBean();

		itemLookupBean.setItemId(item.getItemId());

		itemLookupBean.setLongDesc(item.getDescription());
		itemLookupBean.setName(item.getName());

		itemLookupBean.setCreatedBy(item.getCreatedBy());
		itemLookupBean.setCreatedDate(item.getCreatedDate());
		itemLookupBean.setModifiedBy(item.getModifiedBy());
		itemLookupBean.setModifiedDate(item.getModifiedDate());
		logger.info("The item base data has been transformed to bean successfully.");

		if (item.getItemOptions() != null) {
			ItemOptionsBean itemOptionsBean = ItemTransformer.transformItemOptions(item.getItemOptions());
			itemLookupBean.setItemOptions(itemOptionsBean);
			logger.info("The item options details has been transformed in item details lookup item options bean successfully.");
		}

		if (item.getHierarchy() != null) {
			HierarchyBean hierarchyBean = HierarchyTransformer.transformHierarchy(item.getHierarchy());
			itemLookupBean.setHierarchy(hierarchyBean);
			logger.info("The item details lookup bean has updated with transformed hierarchy object successfully.");
		}

		if (item.getImages() != null && !item.getImages().isEmpty()) {
			ItemImage itemImage = item.getImages().get(0);
			ItemImageBean itemImageBean;
			try {
				itemImageBean = ItemTransformer.transformItemImage(itemImage);
				itemLookupBean.setItemImage(itemImageBean);
				logger.info("The item image has updated with transformed image bean objects successfully.");
			} catch (IOException e) {
				logger.info("There was some problem transforming image for the item");
			}
		}

		if (item.getItemAttributes() != null && !item.getItemAttributes().isEmpty()) {

			List<AttributeBean> itemAttributes = ItemTransformer.transformItemAttributes(item.getItemAttributes());
			itemLookupBean.setItemAttributes(itemAttributes);
			logger.info("The item attributes has been transformed successfully.");
		}

		logger.info("The item lookup details has been transformed successfully.");

		return itemLookupBean;
	}

	public static List<ItemBean> transformItems(List<Item> items) throws IOException {
		List<ItemBean> itemBeans = new ArrayList<>(items.size());
		ItemBean itemBean = null;
		for (Item item : items) {
			itemBean = ItemTransformer.transformItem(item);
			itemBeans.add(itemBean);
		}

		logger.info("The item list has been transformed to item beans successfully.");
		return itemBeans;
	}

	public static ItemBean transformItem(Item item) throws IOException {
		ItemBean itemBean = new ItemBean();

		itemBean.setItemId(item.getItemId());

		itemBean.setLongDesc(item.getDescription());
		itemBean.setItemType(item.getItemType());
		itemBean.setName(item.getName());
		itemBean.setParentItemId(item.getParentItemId());
		itemBean.setItemLevel(item.getItemLevel());

		itemBean.setStatus(item.getStatus());
		itemBean.setCreatedBy(item.getCreatedBy());
		itemBean.setCreatedDate(item.getCreatedDate());
		itemBean.setModifiedBy(item.getModifiedBy());
		itemBean.setModifiedDate(item.getModifiedDate());
		logger.info("The item base data has been transformed to bean successfully.");

		if (item.getItemOptions() != null) {
			ItemOptionsBean itemOptionsBean = ItemTransformer.transformItemOptions(item.getItemOptions());
			itemBean.setItemOptions(itemOptionsBean);
			logger.info("The item options details has been transformed in item options bean successfully.");
		}

		if (item.getHierarchy() != null) {
			HierarchyBean hierarchyBean = HierarchyTransformer.transformHierarchy(item.getHierarchy());
			itemBean.setHierarchy(hierarchyBean);
			logger.info("The item bean has updated with transformed hierarchy object successfully.");
		}

		if (item.getImages() != null && !item.getImages().isEmpty()) {
			List<ItemImageBean> itemImageBeans = ItemTransformer.tranformItemImages(item.getImages());
			itemBean.setItemImages(itemImageBeans);
			logger.info("The item images has updated with transformed image bean objects successfully.");
		} else {
			List<ItemImageBean> itemImageBeans = new ArrayList<>();
			itemImageBeans.add(new ItemImageBean());
			itemBean.setItemImages(itemImageBeans);
		}

		if (item.getItemAttributes() != null && !item.getItemAttributes().isEmpty()) {
			List<AttributeBean> itemAttributeBeans = ItemTransformer.transformItemAttributes(item.getItemAttributes());
			itemBean.setSelectedAttributes(itemAttributeBeans);
			logger.info("The item attributes has updated with transformed attribute bean objects successfully.");
		}

		logger.info("The item has been transformed successfully.");

		return itemBean;
	}

	public static Item transformItemBean(ItemBean itemBean, String username, String action, String functionality) throws IOException {
		Item item = new Item();

		item.setItemId(itemBean.getItemId());

		item.setDescription(itemBean.getLongDesc());
		item.setItemType(itemBean.getItemType());
		item.setName(itemBean.getName());
		item.setParentItemId(itemBean.getParentItemId());
		if (functionality.equals(MVCConstants.FUNCTIONALITY_NEW_STYLE_PARAM) && action.equals(MVCConstants.ACTION_NEW)) {
			item.setItemLevel(MVCConstants.STYLE_LEVEL);
		} else if (functionality.equals(MVCConstants.FUNCTIONALITY_NEW_STYLE_PARAM) && action.equals(MVCConstants.ACTION_EDIT)) {
			item.setItemLevel(itemBean.getItemLevel());
		} else if (functionality.equals(MVCConstants.FUNCTIONALITY_APPROVE_STYLE_PARAM)) {
			if (itemBean.getItemLevel() != null && StringUtils.isNotEmpty(itemBean.getItemLevel().toString()))
				item.setItemLevel(itemBean.getItemLevel());
			else
				item.setItemLevel(MVCConstants.STYLE_LEVEL);
		} else if (functionality.equals(MVCConstants.FUNCTIONALITY_NEW_ITEM_PARAM) && action.equals(MVCConstants.ACTION_NEW)) {
			item.setItemLevel(MVCConstants.ITEM_LEVEL);
		} else if (functionality.equals(MVCConstants.FUNCTIONALITY_APPROVE_ITEM_PARAM) && action.equals(MVCConstants.ACTION_EDIT)) {
			item.setItemLevel(itemBean.getItemLevel());
		} else if (functionality.equals(MVCConstants.FUNCTIONALITY_APPROVE_ITEM_PARAM)) {
			if (itemBean.getItemLevel() != null && StringUtils.isNotEmpty(itemBean.getItemLevel().toString()))
				item.setItemLevel(itemBean.getItemLevel());
			else
				item.setItemLevel(MVCConstants.ITEM_LEVEL);
		} else if (functionality.equals(MVCConstants.FUNCTIONALITY_UPC_PARAM)) {
			item.setItemLevel(MVCConstants.UPC_LEVEL);
		}

		ItemTransformer.updateActionSpecificFields(action, item, itemBean, username, functionality);

		ItemOptions itemOptions = ItemTransformer.transformItemOptionsBean(itemBean.getItemOptions(), item);
		itemOptions.setNextLevelCreated(MVCConstants.NO);
		item.setItemOptions(itemOptions);
		logger.info("The item options bean has updated with transformed item options object successfully.");

		Hierarchy hierarchy = HierarchyTransformer.transformHierarchyBean(itemBean.getHierarchy());
		item.setHierarchy(hierarchy);
		logger.info("The item bean has updated with transformed hierarchy object successfully.");

		List<ItemImage> itemImages = ItemTransformer.transformItemImageBeans(itemBean.getItemImages(), username, item);
		item.setImages(itemImages);
		logger.info("The item bean has updated with transformed image objects successfully.");

		List<AttributeBean> attributes = itemBean.getSelectedAttributes();
		if (attributes != null && !attributes.isEmpty()) {
			ItemTransformer.updateSKUAttributes(attributes, item);
			logger.info("The item bean has updated with transformed item attribute objects successfully.");
		}

		logger.info("The item bean has been transformed to item object successfully.");

		return item;
	}

	private static void updateActionSpecificFields(String action, Item item, ItemBean itemBean, String username, String functionality) {
		if (MVCConstants.ACTION_NEW.equals(action)) {
			item.setStatus(MVCConstants.STATUS_CREATED);
			item.setCreatedBy(username);
			item.setCreatedDate(LocalDateTime.now());
		} else if (MVCConstants.ACTION_EDIT.equals(action)) {
			if (functionality == null || functionality.equals(MVCConstants.FUNCTIONALITY_NEW_STYLE_PARAM))
				item.setStatus(itemBean.getStatus());
			else if (functionality.equals(MVCConstants.FUNCTIONALITY_APPROVE_STYLE_PARAM))
				item.setStatus(MVCConstants.STATUS_APPROVED);
			if (itemBean.getCreatedBy() != null && StringUtils.isNotEmpty(itemBean.getCreatedBy())) {
				item.setCreatedBy(itemBean.getCreatedBy());
				item.setCreatedDate(itemBean.getCreatedDate());
			} else {
				item.setCreatedBy(username);
				item.setCreatedDate(LocalDateTime.now());
			}
			item.setModifiedBy(username);
			item.setModifiedDate(LocalDateTime.now());
		}

		logger.info("The action specific fields has been updated successfully");
	}

	public static ItemOptionsBean transformItemOptions(ItemOptions itemOptions) {
		ItemOptionsBean itemOptionsBean = new ItemOptionsBean();

		itemOptionsBean.setItemId(itemOptions.getItemId());
		itemOptionsBean.setHsnNo(itemOptions.getHsnNo());
		itemOptionsBean.setPackSize(itemOptions.getPackSize());
		itemOptionsBean.setRestockingFee(itemOptions.getRestockingFee());

		itemOptionsBean.setTaxGroupId(itemOptions.getTaxGroupId());
		itemOptionsBean.setUom(itemOptions.getUom());

		itemOptionsBean.setCompareAtPrice(itemOptions.getCompareAtPrice());
		itemOptionsBean.setCurrentPrice(itemOptions.getCurrentPrice());
		itemOptionsBean.setMaxRetailPrice(itemOptions.getMaxRetailPrice());
		itemOptionsBean.setSuggestedPrice(itemOptions.getSuggestedPrice());
		itemOptionsBean.setUnitCost(itemOptions.getUnitCost());

		if (itemOptions.getTaxInclusiveFlag())
			itemOptionsBean.setTaxInclusive(MVCConstants.TAX_INCLUSIVE_PARAM);
		else
			itemOptionsBean.setTaxInclusive(MVCConstants.TAX_EXCLUSIVE_PARAM);

		itemOptionsBean.setAskPriceFlag(itemOptions.getAskPriceFlag());
		itemOptionsBean.setAskQtyFlag(itemOptions.getAskQtyFlag());
		itemOptionsBean.setCustomerPromptFlag(itemOptions.getCustomerPrompt());
		itemOptionsBean.setDescFlag(itemOptions.getDescFlag());
		itemOptionsBean.setDiscountFlag(itemOptions.getDiscountFlag());
		itemOptionsBean.setMinAgeFlag(itemOptions.getMinAgeFlag());
		itemOptionsBean.setNonMerchFlag(itemOptions.getNonMerchFlag());
		itemOptionsBean.setPriceChangeFlag(itemOptions.getPriceChangeFlag());
		itemOptionsBean.setRelatedItemFlag(itemOptions.getRelatedItemFlag());
		itemOptionsBean.setReturnFlag(itemOptions.getReturnFlag());
		itemOptionsBean.setTaxFlag(itemOptions.getTaxFlag());
		itemOptionsBean.setShippingWeight(itemOptions.getShippingWeight());
		itemOptionsBean.setStockStatus(itemOptions.getStockStatus());

		itemOptionsBean.setNextLevelCreated(itemOptions.getNextLevelCreated());

		logger.info("The item options details has been transformed to bean successfully");
		return itemOptionsBean;
	}

	public static ItemOptions transformItemOptionsBean(ItemOptionsBean itemOptionsBean, Item item) {
		ItemOptions itemOptions = new ItemOptions();

		itemOptions.setItemId(item.getItemId());
		itemOptions.setHsnNo(itemOptionsBean.getHsnNo());
		itemOptions.setPackSize(itemOptionsBean.getPackSize());
		itemOptions.setRestockingFee(itemOptionsBean.getRestockingFee());

		itemOptions.setTaxGroupId(itemOptionsBean.getTaxGroupId());
		itemOptions.setUom(itemOptionsBean.getUom());

		itemOptions.setCompareAtPrice(itemOptionsBean.getCompareAtPrice());
		itemOptions.setCurrentPrice(itemOptionsBean.getCurrentPrice());
		itemOptions.setMaxRetailPrice(itemOptionsBean.getMaxRetailPrice());
		itemOptions.setSuggestedPrice(itemOptionsBean.getSuggestedPrice());
		itemOptions.setUnitCost(itemOptionsBean.getUnitCost());

		if (StringUtils.isNotBlank(itemOptionsBean.getTaxInclusive()) && MVCConstants.TAX_EXCLUSIVE_PARAM.equals(itemOptionsBean.getTaxInclusive()))
			itemOptions.setTaxInclusiveFlag(Boolean.FALSE);
		else if (StringUtils.isNotBlank(itemOptionsBean.getTaxInclusive()) && MVCConstants.TAX_INCLUSIVE_PARAM.equals(itemOptionsBean.getTaxInclusive())) {
			itemOptions.setTaxInclusiveFlag(Boolean.TRUE);
		}

		if (itemOptionsBean.getAskPriceFlag() == null)
			itemOptionsBean.setAskPriceFlag(Boolean.FALSE);
		itemOptions.setAskPriceFlag(itemOptionsBean.getAskPriceFlag());

		if (itemOptionsBean.getAskPriceFlag() == null)
			itemOptionsBean.setAskPriceFlag(Boolean.FALSE);
		itemOptions.setAskQtyFlag(itemOptionsBean.getAskQtyFlag());

		if (itemOptionsBean.getCustomerPromptFlag() == null)
			itemOptionsBean.setCustomerPromptFlag(Boolean.FALSE);
		itemOptions.setCustomerPrompt(itemOptionsBean.getCustomerPromptFlag());

		if (itemOptionsBean.getDescFlag() == null)
			itemOptionsBean.setDescFlag(Boolean.FALSE);
		itemOptions.setDescFlag(itemOptionsBean.getDescFlag());

		if (itemOptionsBean.getDiscountFlag() == null)
			itemOptionsBean.setDiscountFlag(Boolean.FALSE);
		itemOptions.setDiscountFlag(itemOptionsBean.getDiscountFlag());

		if (itemOptionsBean.getMinAgeFlag() == null)
			itemOptionsBean.setMinAgeFlag(Boolean.FALSE);
		itemOptions.setMinAgeFlag(itemOptionsBean.getMinAgeFlag());

		if (itemOptionsBean.getNonMerchFlag() == null)
			itemOptionsBean.setNonMerchFlag(Boolean.FALSE);
		itemOptions.setNonMerchFlag(itemOptionsBean.getNonMerchFlag());

		if (itemOptionsBean.getPriceChangeFlag() == null)
			itemOptionsBean.setPriceChangeFlag(Boolean.FALSE);
		itemOptions.setPriceChangeFlag(itemOptionsBean.getPriceChangeFlag());

		if (itemOptionsBean.getRelatedItemFlag() == null)
			itemOptionsBean.setRelatedItemFlag(Boolean.FALSE);
		itemOptions.setRelatedItemFlag(itemOptionsBean.getRelatedItemFlag());

		if (itemOptionsBean.getReturnFlag() == null)
			itemOptionsBean.setReturnFlag(Boolean.FALSE);
		itemOptions.setReturnFlag(itemOptionsBean.getReturnFlag());

		if (itemOptionsBean.getTaxFlag() == null)
			itemOptionsBean.setTaxFlag(Boolean.FALSE);
		itemOptions.setTaxFlag(itemOptionsBean.getTaxFlag());

		itemOptions.setShippingWeight(itemOptionsBean.getShippingWeight());
		itemOptions.setStockStatus(itemOptionsBean.getStockStatus());

		logger.info("The item options bean details has been transformed successfully");
		return itemOptions;
	}

	public static List<ItemImageBean> tranformItemImages(List<ItemImage> itemImages) throws IOException {

		List<ItemImageBean> itemImageBeans = new ArrayList<>();

		ItemImageBean itemImageBean;
		if (itemImages != null) {
			for (ItemImage itemImage : itemImages) {
				itemImageBean = ItemTransformer.transformItemImage(itemImage);
				itemImageBeans.add(itemImageBean);
			}

			int length = 4;
			int counter = itemImageBeans.size();

			while (counter < length) {
				itemImageBeans.add(new ItemImageBean());
				counter++;
			}

		}

		logger.info("The item images has been transformed to item images bean successfully");
		return itemImageBeans;
	}

	public static ItemImageBean transformItemImage(ItemImage itemImage) throws IOException {
		ItemImageBean itemImageBean = new ItemImageBean();

		itemImageBean.setCreatedBy(itemImage.getCreatedBy());
		itemImageBean.setCreatedDate(itemImage.getCreatedDate());

		itemImageBean.setImageType(itemImage.getImageType());
		itemImageBean.setImageURL(itemImage.getImageURL());
		itemImageBean.setItemId(itemImage.getItem().getItemId());
		itemImageBean.setItemImageId(itemImage.getItemImageId());
		itemImageBean.setName(itemImage.getName());

		MultipartFile multipartFile = new CommerceMultipartFile(itemImage.getImageData(), itemImage.getImageURL(), itemImage.getImageType());

		itemImageBean.setImageData(multipartFile);
		ItemTransformer.setItemImageForDisplay(itemImageBean);
		logger.info("The item image has been transformed to item image bean successfully");

		return itemImageBean;
	}

	public static void setItemImageForDisplay(ItemImageBean itemImageBean) throws IOException {

		byte[] imageData = itemImageBean.getImageData().getBytes();
		byte[] encodeBase64 = Base64.encodeBase64(imageData);
		String base64Encoded = new String(encodeBase64, "UTF-8");
		itemImageBean.setBaseEncodedImage(base64Encoded);

		logger.info("The item image bean has been updated with data to display on screen");
	}

	public static List<ItemImage> transformItemImageBeans(List<ItemImageBean> itemImageBeans, String username, Item item) throws IOException {

		List<ItemImage> itemImages = new ArrayList<>();
		ItemImage itemImage;
		if (itemImageBeans != null && !itemImageBeans.isEmpty()) {

			for (ItemImageBean itemImageBean : itemImageBeans) {
				if (itemImageBean.getImageData() != null && StringUtils.isNotEmpty(itemImageBean.getImageData().getOriginalFilename())) {
					itemImage = ItemTransformer.transformItemImageBean(itemImageBean, username, item);
					itemImages.add(itemImage);
				}
			}
		}
		logger.info("The item image beans has been transformed to item images successfully");
		return itemImages;
	}

	public static ItemImage transformItemImageBean(ItemImageBean itemImageBean, String username, Item item) throws IOException {
		ItemImage itemImage = new ItemImage();

		itemImage.setCreatedBy(username);
		itemImage.setCreatedDate(LocalDateTime.now());

		itemImage.setItem(item);
		itemImage.setItemImageId(itemImageBean.getItemImageId());
		itemImage.setName(itemImageBean.getName());

		MultipartFile multipartFile = itemImageBean.getImageData();

		if (multipartFile != null) {
			itemImage.setImageData(multipartFile.getBytes());
			itemImage.setImageType(multipartFile.getContentType());
			itemImage.setImageURL(multipartFile.getOriginalFilename());
			if (StringUtils.isBlank(itemImageBean.getName()))
				itemImage.setName(multipartFile.getOriginalFilename());

		}

		logger.info("The item image bean has been transformed to item image successfully");

		return itemImage;
	}

	public static String updateSKUAttributes(List<AttributeBean> attributeBeans, Item sku) {

		StringBuilder attrValCodes = new StringBuilder();
		List<ItemAttribute> itemAttributes = new ArrayList<>(attributeBeans.size());
		ItemAttribute attribute;
		for (AttributeBean attributeBean : attributeBeans) {
			attribute = ItemTransformer.transformItemAttribute(attributeBean, sku);
			itemAttributes.add(attribute);
			attrValCodes.append(attribute.getItemAttributeId().getAttribute().getValCode()).append(" ");
		}
		sku.setItemAttributes(itemAttributes);
		logger.info("The item attribute beans has been transformed to item attribute successfully");

		return attrValCodes.toString();
	}

	public static List<AttributeBean> transformAttributes(List<Attribute> attributes) {
		List<AttributeBean> itemAttributes = new ArrayList<>(attributes.size());
		AttributeBean attributeBean;
		for (Attribute attribute : attributes) {
			attributeBean = ItemTransformer.transformItemAttribute(attribute);
			itemAttributes.add(attributeBean);
		}
		logger.info("The item attribute has been transformed to item attribute beans successfully");
		return itemAttributes;
	}

	public static List<AttributeBean> transformItemAttributes(List<ItemAttribute> itemAttributes) {
		List<AttributeBean> attributeBeanList = new ArrayList<>(itemAttributes.size());
		AttributeBean attributeBean = null;
		Attribute attribute = null;
		for (ItemAttribute itemAttribute : itemAttributes) {
			attribute = itemAttribute.getItemAttributeId().getAttribute();
			attributeBean = ItemTransformer.transformItemAttribute(attribute);
			attributeBeanList.add(attributeBean);
		}

		logger.info("The item attribute has been transformed to item attribute bean successfully");
		return attributeBeanList;
	}

	public static AttributeBean transformItemAttribute(Attribute attribute) {
		AttributeBean attributeBean = new AttributeBean();

		attributeBean.setAttributeId(attribute.getAttributeId());
		attributeBean.setCode(attribute.getCode());
		attributeBean.setDescription(attribute.getDescription());
		attributeBean.setName(attribute.getName());
		attributeBean.setValCode(attribute.getValCode());
		attributeBean.setValDescription(attribute.getValDesc());
		attributeBean.setValName(attribute.getValName());
		attributeBean.setValSeqNo(attribute.getValSeqNo());
		logger.info("The item attribute has been transformed to item attribute bean successfully");
		return attributeBean;
	}

	public static Attribute transformItemAttributeBean(AttributeBean attributeBean) {
		Attribute attribute = new Attribute();

		attribute.setAttributeId(attributeBean.getAttributeId());
		attribute.setCode(attributeBean.getCode());
		attribute.setDescription(attributeBean.getDescription());
		attribute.setName(attributeBean.getName());
		attribute.setValCode(attributeBean.getValCode());
		attribute.setValDesc(attributeBean.getValDescription());
		attribute.setValName(attributeBean.getValName());
		attribute.setValSeqNo(attributeBean.getValSeqNo());
		logger.info("The item attribute bean has been transformed to item attribute successfully");
		return attribute;
	}

	public static ItemAttribute transformItemAttribute(AttributeBean attributeBean, Item item) {
		ItemAttribute itemAttribute = new ItemAttribute();
		ItemAttributeId itemAttributeId = new ItemAttributeId();

		Attribute attribute = ItemTransformer.transformItemAttributeBean(attributeBean);
		itemAttributeId.setAttribute(attribute);

		itemAttributeId.setItem(item);
		itemAttribute.setItemAttributeId(itemAttributeId);

		logger.info("The item attribute bean has been transformed to item attribute object successfully");
		return itemAttribute;
	}

	public static List<Item> updateSKUs(List<Item> existingSKUs, ItemBeanDTO itemDTO, String username) throws IOException {
		Map<BigInteger, Item> existingSKUMap = new HashMap<>(existingSKUs.size());
		List<Item> updatedSKUs = new ArrayList<>(existingSKUs.size());
		for (Item sku : existingSKUs) {
			existingSKUMap.put(sku.getItemId(), sku);
		}
		Item sku = null;
		List<ItemImage> itemImages = null;
		for (ItemBean skuBean : itemDTO.getSkus()) {
			sku = existingSKUMap.get(skuBean.getItemId());
			sku.setName(skuBean.getName());
			if (StringUtils.isNotBlank(skuBean.getIsImageUpdated()) && skuBean.getIsImageUpdated().equals(MVCConstants.YES)) {
				itemImages = ItemTransformer.transformItemImageBeans(skuBean.getItemImages(), username, sku);
				sku.setImages(itemImages);
			}
			sku.setModifiedBy(username);
			sku.setModifiedDate(LocalDateTime.now());
			updatedSKUs.add(sku);
		}
		logger.info("The sku details has been updated in existing skus successfully");
		return updatedSKUs;

	}

	public static List<Item> createSKUs(Item style, ItemBeanDTO itemDTO, String username, String action) {

		List<ItemImage> itemImages = style.getImages();
		ItemImage itemImage = null;
		if (itemImages != null && !itemImages.isEmpty()) {
			itemImage = itemImages.get(0);
		}

		List<ItemBean> skuList = itemDTO.getSkus();
		List<Item> finalSkuList = new ArrayList<>(skuList.size());
		Item sku = null;

		for (ItemBean skuDetails : skuList) {
			sku = SerializationUtils.clone(style);

			ItemTransformer.resetItemId(sku);
			logger.info("The SKU bean has been transformed to sku item entity successfully");

			ItemTransformer.updateSKUValues(sku, skuDetails, username, style, action);
			logger.info("The SKU specific details has been updated successfully");

			finalSkuList.add(sku);
		}

		return finalSkuList;
	}

	public static Item updateSKUValues(Item sku, ItemBean skuBean, String username, Item style, String action) {

		// Update SKU item level details
		sku.setName(skuBean.getName());
		sku.setItemLevel(MVCConstants.ITEM_LEVEL);
		if (action != null && action.equals(MVCConstants.ACTION_NEW))
			sku.setStatus(MVCConstants.STATUS_CREATED);
		else if (action != null && action.equals(MVCConstants.ACTION_NEW_APPROVE))
			sku.setStatus(MVCConstants.STATUS_APPROVED);
		sku.setParentItemId(style.getItemId());
		sku.setCreatedBy(username);
		sku.setCreatedDate(LocalDateTime.now());

		logger.info("The SKU basic information has been updated successfully");

		// Update SKU option details
		sku.getItemOptions().setItemId(sku.getItemId());
		sku.getItemOptions().setCurrentPrice(skuBean.getItemOptions().getCurrentPrice());
		sku.getItemOptions().setStockStatus(skuBean.getItemOptions().getStockStatus());
		sku.getItemOptions().setNextLevelCreated(MVCConstants.NO);
		logger.info("The SKU options has been updated successfully");

		// sku image updates
		List<ItemImage> skuImages = sku.getImages();
		if (skuImages != null && !skuImages.isEmpty()) {
			for (ItemImage skuImage : skuImages) {
				skuImage.setItemImageId(null);
				skuImage.setItem(sku);
				skuImage.setCreatedBy(username);
				skuImage.setCreatedDate(LocalDateTime.now());
			}
		}
		logger.info("The SKU images has been updated successfully");

		// sku attribute updates
		if (skuBean.getSelectedAttributes() != null && !skuBean.getSelectedAttributes().isEmpty()) {
			String skuAttrs = ItemTransformer.updateSKUAttributes(skuBean.getSelectedAttributes(), sku);
			logger.info("The SKU attributes has been updated successfully");
		}

		return sku;
	}

	public static void resetItemId(Item item) {
		// Resetting the item fields
		item.setItemId(null);
		item.setCreatedBy(null);
		item.setCreatedDate(null);
		item.setModifiedBy(null);
		item.setModifiedDate(null);

		// Resetting the item options
		item.getItemOptions().setItemId(item.getItemId());

		// Resetting the item images
		List<ItemImage> itemImages = item.getImages();
		if (itemImages != null && itemImages.size() > 1) {
			ItemImage itemImage = itemImages.get(0);
			itemImages.clear();
			itemImage.setItemImageId(null);
			itemImage.setCreatedBy(null);
			itemImage.setCreatedDate(null);
			itemImages.add(itemImage);
			item.setImages(itemImages);
		}
		logger.info("The SKU bean has been transformed to sku item entity successfully");

	}

	public static List<String> getAttrCodes(List<AttributeBean> attrBeans) {
		List<String> attrCodes = new ArrayList<>(attrBeans.size());
		for (AttributeBean attrBean : attrBeans) {
			attrCodes.add(attrBean.getCode());
		}
		logger.info("The attribute codes has been retrieved from attribute list");

		return attrCodes;
	}

	public static void updateAttrValues(List<AttributeBean> attrBeans, Map<String, List<Attribute>> attrValList) {
		List<Attribute> attrList = null;
		for (AttributeBean attrBean : attrBeans) {
			attrList = attrValList.get(attrBean.getCode());
			if (attrList != null && !attrList.isEmpty())
				attrBean.setAttrValList(ItemTransformer.transformAttributes(attrList));
		}

		logger.info("The attribute values has been updated in attribute beans successfully");
	}

	public static List<String> retrieveSelectedAttributes(List<Item> skuList) {
		List<String> attrCodeList = new ArrayList<>();

		Item sku = skuList.get(0);
		List<ItemAttribute> itemAttributes = sku.getItemAttributes();
		if (itemAttributes != null && !itemAttributes.isEmpty()) {
			for (ItemAttribute itemAttribute : itemAttributes) {
				attrCodeList.add(itemAttribute.getItemAttributeId().getAttribute().getCode());
			}
		}

		logger.info("The attribute code list has been successfully retrieved");
		return attrCodeList;
	}

	public static List<SaleItem> transformSKUDetails(List<LocSKUDetails> skuDetails, String gstFlag) {
		List<SaleItem> saleItems = new ArrayList<>();
		SaleItem saleItem = null;
		try {
			for (LocSKUDetails locSKU : skuDetails) {

				saleItem = ItemTransformer.transformSKUDetail(locSKU, gstFlag);
				saleItems.add(saleItem);

			}
		} catch (UnsupportedEncodingException e) {
			logger.error("There is some error transforming sku details", e);
		}

		logger.info("The sku details has been transformed to sale item details successfully");
		return saleItems;
	}

	public static SaleItem transformSKUDetail(LocSKUDetails skuDetail, String gstFlag) throws UnsupportedEncodingException {
		SaleItem saleItem = new SaleItem();

		saleItem.setItemId(skuDetail.getItemId());
		saleItem.setDiscountAmt(BigDecimal.ZERO);
		saleItem.setHsnNo(skuDetail.getHsnNo());

		byte[] imageData = skuDetail.getImageData();
		if (imageData != null) {
			byte[] encodeBase64 = Base64.encodeBase64(imageData);
			String base64Encoded = new String(encodeBase64, "UTF-8");

			saleItem.setImageData(base64Encoded);
		}

		saleItem.setImageType(skuDetail.getImageType());
		saleItem.setImagePath(skuDetail.getImageURL());

		saleItem.setLongDesc(skuDetail.getDescription());
		saleItem.setMaxRetailPrice(skuDetail.getMaxRetailPrice());
		saleItem.setName(skuDetail.getName());
		saleItem.setUnitCostAmt(skuDetail.getItemPrice());
		saleItem.setPriceAmt(skuDetail.getItemPrice());
		saleItem.setQty(1.0);
		saleItem.setSuggestedPrice(skuDetail.getSuggestedPrice());

		BigDecimal totalTaxAmt = ItemTransformer.updateSKUTax(skuDetail, saleItem, gstFlag);

		saleItem.setTaxAmt(totalTaxAmt);
		saleItem.setTotalAmt(saleItem.getPriceAmt().add(totalTaxAmt));

		logger.info("The sku detail has been transformed to sale item detail successfully");
		return saleItem;
	}

	private static BigDecimal updateSKUTax(LocSKUDetails skuDetail, SaleItem saleItem, String gstFlag) {
		SaleItemTax saleItemTax = null;
		BigDecimal taxAmount = null;
		BigDecimal totalTaxAmount = BigDecimal.ZERO;

		if(StringUtils.isNotBlank(gstFlag) && gstFlag.equals("I")) {
			saleItemTax = new SaleItemTax();
			taxAmount = skuDetail.getItemPrice().multiply(skuDetail.getIgstRate()).divide(new BigDecimal("100"), RoundingMode.HALF_UP);

			saleItemTax.setAmount(taxAmount);
			saleItemTax.setLocationId(skuDetail.getLocationId());
			saleItemTax.setPercentage(skuDetail.getIgstRate());
			saleItemTax.setTaxGroupId(skuDetail.getTaxGroupId());
			saleItemTax.setTaxGroupName(skuDetail.getTaxGroupName());
			saleItemTax.setTaxGroupRateName(skuDetail.getIgstCode());
			saleItemTax.setTaxRuleRateId(skuDetail.getIgstRateRuleId());
			saleItemTax.setTaxRuleRateName(skuDetail.getIgstCode());
			saleItemTax.setTypeCode(skuDetail.getIgstCode());

			saleItem.setIgstTax(saleItemTax);
			totalTaxAmount = totalTaxAmount.add(taxAmount);
			
			logger.info("The sale item IGST tax details has been updated successfully");
			
		} else if(StringUtils.isNotBlank(gstFlag) && gstFlag.equals("S")) {
			saleItemTax = new SaleItemTax();
			taxAmount = skuDetail.getItemPrice().multiply(skuDetail.getSgstRate()).divide(new BigDecimal("100"), RoundingMode.HALF_UP);

			saleItemTax.setAmount(taxAmount);
			saleItemTax.setLocationId(skuDetail.getLocationId());
			saleItemTax.setPercentage(skuDetail.getSgstRate());
			saleItemTax.setTaxGroupId(skuDetail.getTaxGroupId());
			saleItemTax.setTaxGroupName(skuDetail.getTaxGroupName());
			saleItemTax.setTaxGroupRateName(skuDetail.getSgstCode());
			saleItemTax.setTaxRuleRateId(skuDetail.getSgstRateRuleId());
			saleItemTax.setTaxRuleRateName(skuDetail.getSgstCode());
			saleItemTax.setTypeCode(skuDetail.getSgstCode());

			saleItem.setSgstTax(saleItemTax);
			totalTaxAmount = totalTaxAmount.add(taxAmount);
			logger.info("The sale item SGST tax details has been updated successfully");

			saleItemTax = new SaleItemTax();
			taxAmount = skuDetail.getItemPrice().multiply(skuDetail.getCgstRate()).divide(new BigDecimal("100"), RoundingMode.HALF_UP);

			saleItemTax.setAmount(taxAmount);
			saleItemTax.setLocationId(skuDetail.getLocationId());
			saleItemTax.setPercentage(skuDetail.getCgstRate());
			saleItemTax.setTaxGroupId(skuDetail.getTaxGroupId());
			saleItemTax.setTaxGroupName(skuDetail.getTaxGroupName());
			saleItemTax.setTaxGroupRateName(skuDetail.getCgstCode());
			saleItemTax.setTaxRuleRateId(skuDetail.getCgstRateRuleId());
			saleItemTax.setTaxRuleRateName(skuDetail.getCgstCode());
			saleItemTax.setTypeCode(skuDetail.getCgstCode());

			saleItem.setCgstTax(saleItemTax);
			totalTaxAmount = totalTaxAmount.add(taxAmount);
			logger.info("The sale item CGST tax details has been updated successfully");

		}

		
		return totalTaxAmount;

	}

}
