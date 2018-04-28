package com.punj.app.ecommerce.domains.item.ids;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import com.punj.app.ecommerce.domains.item.Attribute;
import com.punj.app.ecommerce.domains.item.Item;

@Embeddable
public class ItemAttributeId implements Serializable {

	private static final long serialVersionUID = -570521135584306854L;

	@JoinColumn(name = "attribute_id", referencedColumnName = "attribute_id")
	@ManyToOne
	private Attribute attribute;

	@ManyToOne
	@JoinColumn(name = "item_id")
	private Item item;

	/**
	 * @return the attribute
	 */
	public Attribute getAttribute() {
		return attribute;
	}

	/**
	 * @param attribute
	 *            the attribute to set
	 */
	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}

	/**
	 * @return the item
	 */
	public Item getItem() {
		return item;
	}

	/**
	 * @param item
	 *            the item to set
	 */
	public void setItem(Item item) {
		this.item = item;
	}

}
