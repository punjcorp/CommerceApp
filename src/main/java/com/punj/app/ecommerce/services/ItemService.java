/**
 * 
 */
package com.punj.app.ecommerce.services;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.punj.app.ecommerce.domains.item.Attribute;
import com.punj.app.ecommerce.domains.item.AttributeDTO;
import com.punj.app.ecommerce.domains.item.Hierarchy;
import com.punj.app.ecommerce.domains.item.Item;
import com.punj.app.ecommerce.domains.item.ItemAttribute;
import com.punj.app.ecommerce.domains.item.ItemDTO;
import com.punj.app.ecommerce.domains.item.ItemImage;
import com.punj.app.ecommerce.domains.item.ItemOptions;
import com.punj.app.ecommerce.services.dtos.SaleItem;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
public interface ItemService {

	public BigInteger generateNewStyle();

	public List<Item> updateSKUs(List<Item> skuList, String username, Boolean isSKUExisting);

	public BigInteger generateNewSKU(BigInteger styleNo, BigInteger skuNo);

	public Item saveNewItem(Item item);

	public Item saveNewSKU(Item item, BigInteger skuNo);

	public Item saveItem(Item item);

	public Item approveItem(Item item);

	public List<Item> createSKUs(List<Item> skuList, String username, Boolean isSKUExisting);

	public List<Item> saveSKUs(List<Item> skuList, String username, Boolean isSKUExisting);

	public List<Attribute> retrieveAttributeValues(String attributeCode);

	public Map<String, List<Attribute>> retrieveAttrListValues(List<String> attrCodeList);

	public void deleteSKUs(BigInteger styleNo);

	public List<Item> retrieveItems(Item itemCriteria);

	public Map<BigInteger, List<ItemImage>> retrieveItems(Set<BigInteger> itemIds);

	public Item retrieveItem(BigInteger itemId);

	public Item saveItem(Item item, ItemOptions itemOptions, List<ItemAttribute> itemAttributes);

	public List<Hierarchy> retrieveAllDepts();

	public List<Attribute> getNewStyleAttribute();

	public Map<String, Attribute> getAllAttributes();

	public List<Attribute> getStyleAttribute(BigInteger styleNumber);

	public Item getStyle(BigInteger styleNumber);

	public Item getItem(BigInteger itemNumber);

	public List<Item> createSKUs(Item item, ItemOptions itemOptions);

	public ItemDTO searchItem(String text, Pager pager);

	public ItemDTO searchSKUs(String text, Pager pager);

	public ItemDTO listItems(Item itemCriteria, Pager pager);

	public SaleItem retrieveItemDetails(Integer locationId, Integer supplierId, BigInteger itemId, Boolean outOfStateFlag);

	public AttributeDTO searchAttributes(String text, Pager pager);

	public List<Attribute> updateAttributeOrder(List<Attribute> attrOrderList);

	public Attribute saveAttribute(Attribute attribute);
	
	public Attribute saveAttributeValue(Attribute attribute);
	
	public Boolean isAttrValueAssignedToItem(Attribute attr);
	
	public void deleteAttributeValue(BigInteger attrId);
	
	public Set<String> findUniqueAttrCodes();
	
	public Set<String> findUniqueAttrValCodes();

}
