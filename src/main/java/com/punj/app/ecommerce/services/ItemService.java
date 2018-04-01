/**
 * 
 */
package com.punj.app.ecommerce.services;

import java.math.BigInteger;
import java.util.List;

import com.punj.app.ecommerce.domains.item.Attribute;
import com.punj.app.ecommerce.domains.item.Hierarchy;
import com.punj.app.ecommerce.domains.item.Item;
import com.punj.app.ecommerce.domains.item.ItemAttribute;
import com.punj.app.ecommerce.domains.item.ItemDTO;
import com.punj.app.ecommerce.domains.item.ItemOptions;
import com.punj.app.ecommerce.domains.item.ids.AttributeId;
import com.punj.app.ecommerce.services.dtos.SaleItem;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
public interface ItemService {

	public Item saveItem(Item item, ItemOptions itemOptions, List<ItemAttribute> itemAttributes);

	public List<Hierarchy> retrieveAllDepts();

	public BigInteger generateNewStyle();

	public List<Attribute> getNewStyleAttribute();

	public List<Attribute> getStyleAttribute(BigInteger styleNumber);

	public Item getStyle(BigInteger styleNumber);

	public Item getItem(BigInteger itemNumber);

	public List<Item> createSKUs(Item item, ItemOptions itemOptions, List<AttributeId> attributeIds);

	public ItemDTO searchItem(String text, Pager pager);

	public ItemDTO searchSKUs(String text, Pager pager);

	public ItemDTO listItems(Item itemCriteria, Pager pager);

	public SaleItem retrieveItemDetails(Integer locationId, Integer supplierId, BigInteger itemId, Boolean outOfStateFlag);

}
