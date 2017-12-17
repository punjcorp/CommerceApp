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
import com.punj.app.ecommerce.domains.item.ItemOptions;
import com.punj.app.ecommerce.domains.item.ids.AttributeId;

/**
 * @author admin
 *
 */
public interface ItemService {

	public Item saveItem(Item item, ItemOptions itemOptions, List<ItemAttribute> itemAttributes);

	public List<Hierarchy> retrieveAllDepts();

	public BigInteger generateNewStyle();

	public BigInteger generateNewSKU();

	public List<Attribute> getNewStyleAttribute();

	public List<Attribute> getStyleAttribute(BigInteger styleNumber);

	public Item getStyle(BigInteger styleNumber);

	public List<Item> createSKUs(Item item, ItemOptions itemOptions, List<AttributeId> attributeIds);

}
