package com.punj.app.ecommerce.domains.item;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.punj.app.ecommerce.domains.item.ids.ItemAttributeId;

@Entity
@Table(name = "item_attributes")
public class ItemAttribute implements Serializable {

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((itemAttributeId == null) ? 0 : itemAttributeId.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ItemAttribute other = (ItemAttribute) obj;
		if (itemAttributeId == null) {
			if (other.itemAttributeId != null) {
				return false;
			}
		} else if (!itemAttributeId.equals(other.itemAttributeId)) {
			return false;
		}
		return true;
	}

}
