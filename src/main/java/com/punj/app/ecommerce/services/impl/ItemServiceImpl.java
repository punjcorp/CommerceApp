/**
 * 
 */
package com.punj.app.ecommerce.services.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.punj.app.ecommerce.domains.item.Attribute;
import com.punj.app.ecommerce.domains.item.Hierarchy;
import com.punj.app.ecommerce.domains.item.Item;
import com.punj.app.ecommerce.domains.item.ItemAttribute;
import com.punj.app.ecommerce.domains.item.ItemDTO;
import com.punj.app.ecommerce.domains.item.ItemImage;
import com.punj.app.ecommerce.domains.item.ItemOptions;
import com.punj.app.ecommerce.domains.item.SKUCounter;
import com.punj.app.ecommerce.domains.item.StyleCounter;
import com.punj.app.ecommerce.domains.item.comparators.AttributeComparator;
import com.punj.app.ecommerce.domains.item.ids.AttributeId;
import com.punj.app.ecommerce.domains.item.ids.ItemAttributeId;
import com.punj.app.ecommerce.domains.item.ids.ItemImageId;
import com.punj.app.ecommerce.domains.item.ids.SKUCounterId;
import com.punj.app.ecommerce.repositories.item.AttributeRepository;
import com.punj.app.ecommerce.repositories.item.HierarchyRepository;
import com.punj.app.ecommerce.repositories.item.ItemAttributeRepository;
import com.punj.app.ecommerce.repositories.item.ItemOptionsRepository;
import com.punj.app.ecommerce.repositories.item.ItemRepository;
import com.punj.app.ecommerce.repositories.item.ItemSearchRepository;
import com.punj.app.ecommerce.repositories.item.SKUCounterRepository;
import com.punj.app.ecommerce.repositories.item.StyleCounterRepository;
import com.punj.app.ecommerce.services.ItemService;
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

	@Value("${commerce.list.max.perpage}")
	private Integer maxResultPerPage;
	
	@Value("${commerce.list.max.pageno}")
	private Integer maxPageBtns;

	/**
	 * @return the itemRepository
	 */
	public ItemRepository getItemRepository() {
		return itemRepository;
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
		itemOptions = itemOptionsRepository.save(itemOptions);
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

		return null;
	}

	@Override
	public BigInteger generateNewStyle() {
		StyleCounter style = new StyleCounter();
		style.setStatus("N");

		style = styleRepository.save(style);

		logger.info("A new style number {} has been generated successfully", style.getStyleId());

		return style.getStyleId();
	}

	@Override
	public BigInteger generateNewSKU() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Attribute> getNewStyleAttribute() {

		List<Attribute> attributeList = attributeRepository.findAll();

		logger.info("All the attributes-{} for a new style creation has been retrieved successfully",
				attributeList.size());

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

		List<Attribute> finalList = new ArrayList<Attribute>();
		List<ItemAttribute> attributeList = itemAttributeRepository.findAll(Example.of(itemAttribute));
		for (ItemAttribute itemAttr : attributeList) {
			finalList.add(itemAttr.getItemAttributeId().getAttribute());
		}

		logger.info("All the attributes-{} for a new style creation has been retrieved successfully",
				attributeList.size());

		return finalList;
	}

	@Override
	public Item getStyle(BigInteger styleNumber) {
		Item item = itemRepository.findOne(styleNumber);
		logger.info("The style details has been retrieved successfully");
		return item;
	}

	@Override
	public List<Item> createSKUs(Item item, ItemOptions itemOptionsOrg, List<AttributeId> attributeIds) {

		List<BigInteger> skuItemIds = new ArrayList<BigInteger>();

		List<Attribute> attributeList = attributeRepository.findAll(attributeIds);
		Collections.sort(attributeList, new AttributeComparator());

		List<Attribute> colorList = new ArrayList<Attribute>();
		List<Attribute> sizeList = new ArrayList<Attribute>();

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
				skuCounterId.setColor(colorAttribute.getSeqNo());
				skuCounterId.setSize(sizeAttribute.getSeqNo());
				skuCounterId.setStyleCounter(skuStyleCounter);

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
				List<ItemImage> itemImages = skuItem.getImages();
				List<ItemImage> updatedItemImages = new ArrayList<ItemImage>();
				ItemImageId itemImageId = null;
				for (ItemImage itemImage : itemImages) {
					itemImageId = itemImage.getItemImageId();
					itemImageId.setItemId(skuItem.getItemId());
					itemImage.setItemImageId(itemImageId);
					updatedItemImages.add(itemImage);
				}
				skuItem.setImages(updatedItemImages);

				skuItem = itemRepository.save(skuItem);
				logger.info("The requested item object has been saved successfully");
				/**
				 * save item options
				 */
				// Clone a new SKU from style item
				ItemOptions itemOptions = SerializationUtils.clone(itemOptionsOrg);

				itemOptions.setItemId(skuItem.getItemId());
				itemOptions = itemOptionsRepository.save(itemOptions);
				logger.info("The requested item options object has been saved successfully");

				/**
				 * Save item attributes for color and size
				 */
				List<ItemAttribute> itemAttributes = new ArrayList<ItemAttribute>();
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

				itemAttributes = itemAttributeRepository.save(itemAttributes);
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

		for (Attribute attribute : attributeList) {
			switch ((attribute.getCode().toCharArray())[0]) {
			case 'C':
				colorList.add(attribute);
				break;
			case 'S':
				sizeList.add(attribute);
				break;
			}

		}
		logger.info("All the attributes related to Color and Size has been splitted successfully");

	}

	@Override
	public ItemDTO searchItem(String text, Pager pager) {
		int startCount = (pager.getCurrentPageNo() - 1) * maxResultPerPage + 1;
		pager.setPageSize(maxResultPerPage);
		pager.setStartCount(startCount);
		pager.setMaxDisplayPage(maxPageBtns);
		
		ItemDTO items=this.itemSearchRepository.search(text, pager);
		logger.info("The searched items has been retrieved successfully");

		return items;
	}
}
