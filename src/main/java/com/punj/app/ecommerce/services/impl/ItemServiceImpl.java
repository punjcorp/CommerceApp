/**
 * 
 */
package com.punj.app.ecommerce.services.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.domains.common.Location;
import com.punj.app.ecommerce.domains.item.Attribute;
import com.punj.app.ecommerce.domains.item.AttributeDTO;
import com.punj.app.ecommerce.domains.item.Hierarchy;
import com.punj.app.ecommerce.domains.item.Item;
import com.punj.app.ecommerce.domains.item.ItemAttribute;
import com.punj.app.ecommerce.domains.item.ItemDTO;
import com.punj.app.ecommerce.domains.item.ItemLocationTax;
import com.punj.app.ecommerce.domains.item.ItemOptions;
import com.punj.app.ecommerce.domains.item.SKUCounter;
import com.punj.app.ecommerce.domains.item.StyleCounter;
import com.punj.app.ecommerce.domains.item.comparators.AttributeComparator;
import com.punj.app.ecommerce.domains.item.ids.ItemAttributeId;
import com.punj.app.ecommerce.domains.item.ids.SKUCounterId;
import com.punj.app.ecommerce.domains.price.ItemPrice;
import com.punj.app.ecommerce.repositories.item.AttributeRepository;
import com.punj.app.ecommerce.repositories.item.AttributeSearchRepository;
import com.punj.app.ecommerce.repositories.item.HierarchyRepository;
import com.punj.app.ecommerce.repositories.item.ItemAttributeRepository;
import com.punj.app.ecommerce.repositories.item.ItemOptionsRepository;
import com.punj.app.ecommerce.repositories.item.ItemRepository;
import com.punj.app.ecommerce.repositories.item.ItemSearchRepository;
import com.punj.app.ecommerce.repositories.item.SKUCounterRepository;
import com.punj.app.ecommerce.repositories.item.StyleCounterRepository;
import com.punj.app.ecommerce.services.ItemService;
import com.punj.app.ecommerce.services.PriceService;
import com.punj.app.ecommerce.services.common.CommonService;
import com.punj.app.ecommerce.services.common.ServiceConstants;
import com.punj.app.ecommerce.services.converter.AttributeConverter;
import com.punj.app.ecommerce.services.converter.ItemConverter;
import com.punj.app.ecommerce.services.dtos.SaleItem;
import com.punj.app.ecommerce.services.dtos.SaleItemTax;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
@Service
public class ItemServiceImpl implements ItemService {

	private static final Logger logger = LogManager.getLogger();
	private ItemRepository itemRepository;
	private ItemSearchRepository itemSearchRepository;
	private ItemOptionsRepository itemOptionsRepository;
	private AttributeRepository attributeRepository;
	private ItemAttributeRepository itemAttributeRepository;
	private HierarchyRepository hierarchyRepository;
	private StyleCounterRepository styleRepository;
	private SKUCounterRepository skuRepository;
	private AttributeSearchRepository attributeSearchRepository;
	private CommonService commonService;
	private PriceService priceService;

	@Value("${commerce.list.max.perpage}")
	private Integer maxResultPerPage;

	@Value("${commerce.list.max.pageno}")
	private Integer maxPageBtns;

	/**
	 * @return the attributeSearchRepository
	 */
	public AttributeSearchRepository getAttributeSearchRepository() {
		return attributeSearchRepository;
	}

	/**
	 * @param priceService
	 *            the priceService to set
	 */
	@Autowired
	public void setPriceService(PriceService priceService) {
		this.priceService = priceService;
	}

	/**
	 * @param attributeSearchRepository
	 *            the attributeSearchRepository to set
	 */
	@Autowired
	public void setAttributeSearchRepository(AttributeSearchRepository attributeSearchRepository) {
		this.attributeSearchRepository = attributeSearchRepository;
	}

	/**
	 * @return the itemRepository
	 */
	public ItemRepository getItemRepository() {
		return itemRepository;
	}

	/**
	 * @param commonService
	 *            the commonService to set
	 */
	@Autowired
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	/**
	 * @param itemRepository
	 *            the itemRepository to set
	 */
	@Autowired
	public void setItemRepository(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}

	/**
	 * @return the itemSearchRepository
	 */
	public ItemSearchRepository getItemSearchRepository() {
		return itemSearchRepository;
	}

	/**
	 * @param itemRepository
	 *            the itemRepository to set
	 */
	@Autowired
	public void setItemSearchRepository(ItemSearchRepository itemSearchRepository) {
		this.itemSearchRepository = itemSearchRepository;
	}

	/**
	 * @return the attributeRepository
	 */
	public AttributeRepository getAttributeRepository() {
		return attributeRepository;
	}

	/**
	 * @return the hierarchyRepository
	 */
	public HierarchyRepository getHierarchyRepository() {
		return hierarchyRepository;
	}

	/**
	 * @param hierarchyRepository
	 *            the hierarchyRepository to set
	 */
	@Autowired
	public void setHierarchyRepository(HierarchyRepository hierarchyRepository) {
		this.hierarchyRepository = hierarchyRepository;
	}

	/**
	 * @return the styleRepository
	 */
	public StyleCounterRepository getStyleRepository() {
		return styleRepository;
	}

	/**
	 * @param styleRepository
	 *            the styleRepository to set
	 */
	@Autowired
	public void setStyleRepository(StyleCounterRepository styleRepository) {
		this.styleRepository = styleRepository;
	}

	/**
	 * @return the skuRepository
	 */
	public SKUCounterRepository getSkuRepository() {
		return skuRepository;
	}

	/**
	 * @param skuRepository
	 *            the skuRepository to set
	 */
	@Autowired
	public void setSkuRepository(SKUCounterRepository skuRepository) {
		this.skuRepository = skuRepository;
	}

	/**
	 * @param attributeRepository
	 *            the attributeRepository to set
	 */
	@Autowired
	public void setAttributeRepository(AttributeRepository attributeRepository) {
		this.attributeRepository = attributeRepository;
	}

	/**
	 * @return the itemOptionsRepository
	 */
	public ItemOptionsRepository getItemOptionsRepository() {
		return itemOptionsRepository;
	}

	/**
	 * @param itemOptionsRepository
	 *            the itemOptionsRepository to set
	 */
	@Autowired
	public void setItemOptionsRepository(ItemOptionsRepository itemOptionsRepository) {
		this.itemOptionsRepository = itemOptionsRepository;
	}

	/**
	 * @return the itemAttributeRepository
	 */
	public ItemAttributeRepository getItemAttributeRepository() {
		return itemAttributeRepository;
	}

	/**
	 * @param itemAttributeRepository
	 *            the itemAttributeRepository to set
	 */
	@Autowired
	public void setItemAttributeRepository(ItemAttributeRepository itemAttributeRepository) {
		this.itemAttributeRepository = itemAttributeRepository;
	}

	@Override
	public Item saveItem(Item item, ItemOptions itemOptions, List<ItemAttribute> itemAttributes) {
		item.setStatus("A");

		item = itemRepository.save(item);
		logger.info("The requested item object has been saved successfully");
		itemOptionsRepository.save(itemOptions);
		logger.info("The requested item options object has been saved successfully");

		itemAttributeRepository.save(itemAttributes);
		logger.info("The requested item attributes has been saved successfully");

		StyleCounter styleCounter = new StyleCounter();
		styleCounter.setStyleId(item.getItemId());
		styleCounter.setStatus("Y");
		styleRepository.save(styleCounter);
		logger.info("The requested style counter records has been updated successfully");

		return item;
	}

	public List<Hierarchy> retrieveAllDepts() {
		Hierarchy searchHierarchy = new Hierarchy();
		searchHierarchy.setCode("Dept");
		List<Hierarchy> deptList = hierarchyRepository.findAll(Example.of(searchHierarchy));
		logger.info("The requested department objects has been retrieved successfully with all the needed children");

		return deptList;
	}

	@Override
	@Transactional
	public BigInteger generateNewStyle() {
		StyleCounter style = new StyleCounter();
		style.setStatus("N");

		style = this.styleRepository.save(style);

		String stylePrefix = style.getStyleId().toString();
		stylePrefix = stylePrefix + "0000";
		logger.info("A new style number {} has been generated successfully", stylePrefix);

		return new BigInteger(stylePrefix);
	}

	@Override
	@Transactional
	public BigInteger generateNewSKU(BigInteger styleNo, BigInteger skuNo) {
		SKUCounter skuCounter = new SKUCounter();
		SKUCounterId skuCounterId = new SKUCounterId();
		skuCounterId.setStyleId(styleNo);
		skuCounterId.setSkuId(skuNo);
		skuCounter.setStatus(ServiceConstants.NO_PARAM);
		skuCounter.setSkuCounterId(skuCounterId);
		skuCounter = this.skuRepository.save(skuCounter);
		if (skuCounter != null && skuCounter.getSkuCounterId().getSkuId() != null) {
			skuNo = skuCounter.getSkuCounterId().getStyleId().add(skuCounter.getSkuCounterId().getSkuId());
			logger.info("A new sku number {} has been generated successfully", skuNo);
		} else {
			logger.info("There was some error while creating SKU", skuNo);
		}

		return skuNo;
	}

	@Override
	public List<Attribute> getNewStyleAttribute() {

		List<Attribute> attributeList = attributeRepository.findAll();

		logger.info("All the attributes-{} for a new style creation has been retrieved successfully", attributeList.size());

		return attributeList;
	}

	@Override
	public List<Attribute> getStyleAttribute(BigInteger styleNumber) {
		ItemAttribute itemAttribute = new ItemAttribute();

		ItemAttributeId itemAttributeId = new ItemAttributeId();

		Item item = new Item();
		item.setItemId(styleNumber);
		itemAttributeId.setItem(item);

		itemAttribute.setItemAttributeId(itemAttributeId);

		List<Attribute> finalList = new ArrayList<>();
		List<ItemAttribute> attributeList = itemAttributeRepository.findAll(Example.of(itemAttribute));
		for (ItemAttribute itemAttr : attributeList) {
			finalList.add(itemAttr.getItemAttributeId().getAttribute());
		}

		logger.info("All the attributes-{} for a new style creation has been retrieved successfully", attributeList.size());

		return finalList;
	}

	@Override
	public Item getStyle(BigInteger styleNumber) {
		Item item = itemRepository.findOne(styleNumber);
		logger.info("The style details has been retrieved successfully");
		return item;
	}

	@Override
	@Transactional
	public List<Item> createSKUs(Item item, ItemOptions itemOptionsOrg) {

		List<BigInteger> skuItemIds = new ArrayList<>();

		List<Attribute> attributeList = null;
		Collections.sort(attributeList, new AttributeComparator());

		List<Attribute> colorList = new ArrayList<>();
		List<Attribute> sizeList = new ArrayList<>();

		this.splitColorAndSize(attributeList, colorList, sizeList);

		for (Attribute colorAttribute : colorList) {
			for (Attribute sizeAttribute : sizeList) {

				// Clone a new SKU from style item
				Item skuItem = SerializationUtils.clone(item);

				/**
				 * Create a new sku counter object and save it
				 */
				SKUCounter skuCounter = new SKUCounter();

				StyleCounter skuStyleCounter = new StyleCounter();
				skuStyleCounter.setStatus("Y");
				skuStyleCounter.setStyleId(skuItem.getParentItemId());

				SKUCounterId skuCounterId = new SKUCounterId();

				skuCounter.setSkuCounterId(skuCounterId);
				skuCounter.setStatus("N");
				skuCounter = skuRepository.save(skuCounter);

				/**
				 * Setup the SKU details now
				 */
				skuItem.setItemId(new BigInteger(skuCounter.toString()));
				skuItem.setStatus("A");

				/**
				 * Setting the item images
				 */

				// Check what is needed

				skuItem = itemRepository.save(skuItem);
				logger.info("The requested item object has been saved successfully");
				/**
				 * save item options
				 */
				// Clone a new SKU from style item
				ItemOptions itemOptions = SerializationUtils.clone(itemOptionsOrg);

				itemOptions.setItemId(skuItem.getItemId());
				itemOptionsRepository.save(itemOptions);
				logger.info("The requested item options object has been saved successfully");

				/**
				 * Save item attributes for color and size
				 */
				List<ItemAttribute> itemAttributes = new ArrayList<>();
				ItemAttribute colorItemAttribute = new ItemAttribute();
				ItemAttributeId colorItemAttributeId = new ItemAttributeId();

				colorItemAttributeId.setAttribute(colorAttribute);
				colorItemAttributeId.setItem(skuItem);

				colorItemAttribute.setItemAttributeId(colorItemAttributeId);
				itemAttributes.add(colorItemAttribute);

				ItemAttribute sizeItemAttribute = new ItemAttribute();
				ItemAttributeId sizeItemAttributeId = new ItemAttributeId();

				sizeItemAttributeId.setAttribute(sizeAttribute);
				sizeItemAttributeId.setItem(skuItem);

				sizeItemAttribute.setItemAttributeId(sizeItemAttributeId);
				itemAttributes.add(sizeItemAttribute);

				itemAttributeRepository.save(itemAttributes);
				logger.info("The requested item attributes has been saved successfully");

				skuCounter.setStatus("Y");
				skuRepository.save(skuCounter);
				logger.info("The requested sku counter records has been updated to used successfully");

				skuItemIds.add(skuItem.getItemId());
			}
		}
		logger.info("The SKUs has been created in database successfully");

		List<Item> finalSKUs = itemRepository.findAll(skuItemIds);
		logger.info("The SKU details has been retrieved from database with all related details");

		return finalSKUs;
	}

	private void splitColorAndSize(List<Attribute> attributeList, List<Attribute> colorList, List<Attribute> sizeList) {

		char attributeCode;
		for (Attribute attribute : attributeList) {
			attributeCode = (attribute.getCode().toCharArray())[0];
			switch (attributeCode) {
			case 'C':
				colorList.add(attribute);
				break;
			case 'S':
				sizeList.add(attribute);
				break;
			default:
				logger.warn("!!Unknown Attribute Found!! -> {}", attributeCode);
			}

		}
		logger.info("All the attributes related to Color and Size has been splitted successfully");

	}

	@Override
	public ItemDTO searchItem(String text, Pager pager) {
		int startCount = (pager.getCurrentPageNo() - 1) * maxResultPerPage;
		pager.setPageSize(maxResultPerPage);
		pager.setStartCount(startCount);
		pager.setMaxDisplayPage(maxPageBtns);

		ItemDTO items = this.itemSearchRepository.search(text, pager);
		logger.info("The searched items has been retrieved using keyword successfully");

		return items;
	}

	@Override
	public ItemDTO searchSKUs(String text, Pager pager) {
		int startCount = (pager.getCurrentPageNo() - 1) * maxResultPerPage;
		pager.setPageSize(maxResultPerPage);
		pager.setStartCount(startCount);
		pager.setMaxDisplayPage(maxPageBtns);

		ItemDTO items = this.itemSearchRepository.searchSKU(text, pager);
		logger.info("The searched skus has been retrieved using keywords successfully");

		return items;
	}

	@Override
	public ItemDTO listItems(Item itemCriteria, Pager pager) {

		int startCount = (pager.getCurrentPageNo() - 1) * maxResultPerPage;
		pager.setPageSize(maxResultPerPage);
		pager.setStartCount(startCount);
		pager.setMaxDisplayPage(maxPageBtns);

		Pageable pageable = new PageRequest(pager.getCurrentPageNo() - 1, maxResultPerPage);

		Page<Item> itemsPage = this.itemRepository.findAll(pageable);
		logger.info("The searched items has been retrieved successfully");

		List<Item> items = itemsPage.getContent();

		ItemDTO itemDTO = new ItemDTO();
		itemDTO.setItems(items);

		pager.setResultSize((int) itemsPage.getTotalElements());

		itemDTO.setPager(pager);

		logger.info("The pager details has been set for all listed items in Item DTO object");

		return itemDTO;
	}

	@Override
	public Item getItem(BigInteger itemNumber) {
		Item item = this.getStyle(itemNumber);
		logger.info("The item object has been retrieved successfully for item ->{} .", itemNumber);
		return item;
	}

	@Override

	public SaleItem retrieveItemDetails(Integer locationId, Integer supplierId, BigInteger itemId, Boolean outOfStateFlag) {

		ItemLocationTax itemLocTax = this.commonService.retrieveItemDetails(locationId, supplierId, itemId);
		SaleItem saleItem = null;
		if (itemLocTax != null) {
			saleItem = this.transformItemDetails(itemLocTax, outOfStateFlag);
			logger.info("The item object has been retrieved successfully for item");
		} else {
			logger.info("There was no record found for the search criteria");
		}

		return saleItem;
	}

	private SaleItem transformItemDetails(ItemLocationTax itemLocTax, Boolean outOfStateFlag) {
		SaleItem saleItem = new SaleItem();

		saleItem.setItemId(itemLocTax.getItemLocationTaxId().getItemId());
		saleItem.setName(itemLocTax.getName());
		saleItem.setLongDesc(itemLocTax.getLongDesc());
		// Check this out
		saleItem.setImagePath(itemLocTax.getName());
		saleItem.setQty(1.0);
		saleItem.setUnitCostAmt(itemLocTax.getBaseUnitCost());

		saleItem.setPriceAmt(saleItem.getUnitCostAmt().multiply(BigDecimal.valueOf(saleItem.getQty())));

		SaleItemTax saleItemTax = new SaleItemTax();
		saleItemTax.setTaxGroupId(itemLocTax.getTaxGroupId());
		saleItemTax.setTaxRuleRateId(itemLocTax.getSgstRateRuleId());
		saleItemTax.setAmount(itemLocTax.getSgstAmount());
		saleItemTax.setPercentage(itemLocTax.getSgstRate());
		saleItemTax.setTaxRuleRateName(itemLocTax.getSgstCode());
		saleItemTax.setTypeCode(itemLocTax.getSgstCode());
		saleItem.setSgstTax(saleItemTax);

		saleItemTax = new SaleItemTax();
		saleItemTax.setTaxGroupId(itemLocTax.getTaxGroupId());
		saleItemTax.setTaxRuleRateId(itemLocTax.getCgstRateRuleId());
		saleItemTax.setAmount(itemLocTax.getCgstAmount());
		saleItemTax.setPercentage(itemLocTax.getCgstRate());
		saleItemTax.setTaxRuleRateName(itemLocTax.getCgstCode());
		saleItemTax.setTypeCode(itemLocTax.getCgstCode());
		saleItem.setCgstTax(saleItemTax);

		saleItemTax = new SaleItemTax();
		saleItemTax.setTaxGroupId(itemLocTax.getTaxGroupId());
		saleItemTax.setTaxRuleRateId(itemLocTax.getIgstRateRuleId());
		saleItemTax.setAmount(itemLocTax.getIgstAmount());
		saleItemTax.setPercentage(itemLocTax.getIgstRate());
		saleItemTax.setTaxRuleRateName(itemLocTax.getIgstCode());
		saleItemTax.setTypeCode(itemLocTax.getIgstCode());
		saleItem.setIgstTax(saleItemTax);

		if (outOfStateFlag) {
			saleItem.getIgstTax().setAmount(saleItem.getUnitCostAmt().multiply(saleItem.getIgstTax().getPercentage().divide(new BigDecimal(100))));
			saleItem.setTaxAmt(saleItem.getIgstTax().getAmount());
		} else {
			saleItem.getSgstTax().setAmount(saleItem.getUnitCostAmt().multiply(saleItem.getSgstTax().getPercentage().divide(new BigDecimal(100))));
			saleItem.getCgstTax().setAmount(saleItem.getUnitCostAmt().multiply(saleItem.getCgstTax().getPercentage().divide(new BigDecimal(100))));

			saleItem.setTaxAmt(saleItem.getCgstTax().getAmount().add(saleItem.getSgstTax().getAmount()));
		}

		saleItem.setDiscountAmt(BigDecimal.ZERO);

		saleItem.setTotalAmt(saleItem.getPriceAmt().add(saleItem.getTaxAmt()));

		logger.info("The item details from database has been transformed to Sale Item");

		return saleItem;
	}

	@Override
	public AttributeDTO searchAttributes(String searchText, Pager pager) {
		int startCount = (pager.getCurrentPageNo() - 1) * maxResultPerPage;
		pager.setPageSize(this.maxResultPerPage);
		pager.setStartCount(startCount);
		pager.setMaxDisplayPage(this.maxPageBtns);

		AttributeDTO attributeDTO = this.attributeSearchRepository.searchAttribute(searchText, pager);
		if (attributeDTO != null && attributeDTO.getAttributes() != null && !attributeDTO.getAttributes().isEmpty()) {
			List<Attribute> distinctAttributes = AttributeConverter.convertDistinctAttributes(attributeDTO.getAttributes());
			attributeDTO.setAttributes(distinctAttributes);
			logger.info("The item hierarchy has been retrieved based on searched keyword");
		} else {
			logger.info("There was no item hierarchy found based on searched keyword");
		}
		return attributeDTO;
	}

	@Override
	public List<Attribute> retrieveAttributeValues(String attributeCode) {
		Attribute attribute = new Attribute();
		attribute.setCode(attributeCode);

		Sort sort = new Sort(Sort.Direction.ASC, "valSeqNo");

		List<Attribute> attributeValueList = this.attributeRepository.findAll(Example.of(attribute), sort);

		if (attributeValueList != null && !attributeValueList.isEmpty())
			logger.info("The {} values for attribute {} has been retrieved successfully", attributeValueList.size(), attributeCode);
		else
			logger.info("There was no attribute values found for {} attribute", attributeCode);

		return attributeValueList;
	}

	@Transactional
	private void updateStyle(BigInteger styleNumber) {
		StyleCounter styleCounter = new StyleCounter();

		if (styleNumber.longValue() > 9999999) {
			String styleValue = styleNumber.toString();
			styleValue = styleValue.substring(0, 7);
			styleNumber = new BigInteger(styleValue);
		}

		styleCounter.setStyleId(styleNumber);
		styleCounter.setStatus("Y");

		this.styleRepository.save(styleCounter);
		logger.info("The new generated style was updated as used");
	}

	@Override
	@Transactional
	public Item saveItem(Item item) {

		item = this.itemRepository.save(item);
		if (item != null) {
			logger.info("The item details has been updated successfully");
		} else {
			logger.info("The item details were not saved due to some issue");
		}
		return item;
	}

	@Override
	@Transactional
	public Item saveNewItem(Item item) {
		BigInteger styleNumber = this.generateNewStyle();
		item.setItemId(styleNumber);
		item.getItemOptions().setItemId(item.getItemId());
		logger.info("The new generated style number has been assigned to the item object");

		item = this.itemRepository.save(item);
		if (item != null) {
			this.updateStyle(styleNumber);
			logger.info("The item details has been saved successfully");
		} else {
			logger.info("The item details were not saved due to some issue");
		}

		return item;
	}

	@Override
	@Transactional
	public Item approveItem(Item item) {

		item = this.saveItem(item);
		logger.info("The price information will be updated in price records now.");

		List<Location> locations = this.commonService.retrieveAllLocations();
		List<ItemPrice> itemPriceList = ItemConverter.convertToItemPriceForAll(item, item.getModifiedBy(), locations);
		itemPriceList = this.priceService.saveItemPriceList(itemPriceList);
		if (itemPriceList != null && !itemPriceList.isEmpty())
			logger.info("The item price records were created successfully");
		else
			logger.info("There was some issue while creating the item prices");
		return item;

	}

	@Override
	@Transactional
	public Item saveNewSKU(Item item, BigInteger skuNo) {

		BigInteger skuNumber = this.generateNewSKU(item.getParentItemId(), skuNo);
		item.setItemId(skuNumber);
		item.getItemOptions().setItemId(item.getItemId());
		logger.info("The new generated sku number has been assigned to the item object");

		item = this.itemRepository.save(item);
		if (item != null) {
			this.updateSKUId(skuNumber);
			logger.info("The sku details has been saved successfully");
		} else {
			logger.error("The sku details were not saved due to some issue");
		}

		return item;
	}

	@Transactional
	private void updateSKUId(BigInteger skuNumber) {
		SKUCounter skuCounter = new SKUCounter();
		SKUCounterId skuCounterId = new SKUCounterId();

		String skuNoStr = skuNumber.toString();
		String skuIdStr = skuNoStr.substring(skuNoStr.length() - 4, skuNoStr.length());

		skuCounterId.setSkuId(new BigInteger(skuIdStr));
		skuCounterId.setStyleId(skuNumber.subtract(skuCounterId.getSkuId()));

		skuCounter.setSkuCounterId(skuCounterId);
		skuCounter.setStatus("Y");

		this.skuRepository.save(skuCounter);
		logger.info("The new generated sku counter was updated as used");
	}

	@Override
	@Transactional
	public List<Item> createSKUs(List<Item> skuList, String username, Boolean isSKUExisting) {
		List<Item> finalSKUs = null;
		if (skuList != null && !skuList.isEmpty()) {

			finalSKUs = new ArrayList<>(skuList.size());

			BigInteger styleNo = skuList.get(0).getParentItemId();

			if (isSKUExisting) {
				this.deleteSKUs(styleNo);
			}

			Item style = this.getStyle(styleNo);
			style.setModifiedBy(username);
			style.setModifiedDate(LocalDateTime.now());
			if (skuList.get(0).getStatus().equals(ServiceConstants.STATUS_APPROVED))
				style.getItemOptions().setNextLevelCreated(ServiceConstants.NEXT_LEVEL_APPROVED);
			else if (skuList.get(0).getStatus().equals(ServiceConstants.STATUS_CREATED))
				style.getItemOptions().setNextLevelCreated(ServiceConstants.NEXT_LEVEL_CREATED);
			this.saveItem(style);
			logger.info("The Style has been updated with SKU creation indicator successfully");

			BigInteger skuCounter = BigInteger.ZERO;
			BigInteger skuNo = null;
			for (Item sku : skuList) {
				skuCounter = skuCounter.add(BigInteger.ONE);
				skuNo = new BigInteger(skuCounter.toString());

				sku = this.saveNewSKU(sku, skuNo);
				finalSKUs.add(sku);
			}
			if (!finalSKUs.isEmpty())
				logger.info("The skus were created successfully");
			else
				logger.error("There was some issue while creating the skus");
		}

		return finalSKUs;
	}

	@Override
	public Map<String, List<Attribute>> retrieveAttrListValues(List<String> attrCodeList) {
		Map<String, List<Attribute>> attrValMap = new HashMap<>();

		for (String attrCode : attrCodeList) {
			List<Attribute> attrValues = this.retrieveAttributeValues(attrCode);
			attrValMap.put(attrCode, attrValues);
		}

		logger.info("The attribute values list has been successfully retrieved and updated in a Map");
		return attrValMap;
	}

	@Override
	@Transactional
	public void deleteSKUs(BigInteger styleNo) {
		Item item = new Item();
		item.setParentItemId(styleNo);
		item.setItemLevel(ServiceConstants.ITEM_LEVEL);
		List<Item> skus = this.itemRepository.findAll(Example.of(item));
		if (skus != null)
			this.itemRepository.delete(skus);
		logger.info("All the sku details for style {} has been deleted successfully", styleNo);

		SKUCounter skuCounter = new SKUCounter();
		SKUCounterId skuCounterId = new SKUCounterId();
		skuCounterId.setStyleId(styleNo);
		skuCounter.setSkuCounterId(skuCounterId);

		List<SKUCounter> skuCounterList = this.skuRepository.findAll(Example.of(skuCounter));
		if (skuCounterList != null) {
			this.skuRepository.delete(skuCounterList);
			logger.info("All the sku counters for style {} has been reset successfully", styleNo);
		}

		logger.info("The skus has been deleted successfully");

	}

}
