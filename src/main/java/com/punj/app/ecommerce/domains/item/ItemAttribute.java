package com.punj.app.ecommerce.domains.item;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.punj.app.ecommerce.domains.item.ids.ItemAttributeId;

@Entity
@Table(name = "item_attributes")
public class ItemAttribute implements Serializable {

	private static final long serialVersionUID = 2244295488976495753L;
	@EmbeddedId
	private ItemAttributeId itemAttributeId;

	/**
	 * @return the itemAttributeId
	 */
	public ItemAttributeId getItemAttributeId() {
		return itemAttributeId;
	}

	/**
	 * @param itemAttributeId
	 *            the itemAttributeId to set
	 */
	public void setItemAttributeId(ItemAttributeId itemAttributeId) {
		this.itemAttributeId = itemAttributeId;
	}

}
