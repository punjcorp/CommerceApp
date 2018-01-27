package com.punj.app.ecommerce.domains.price.ids;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ItemPriceHistoryId implements Serializable {

	private static final long serialVersionUID = -842506076765490192L;

	@Column(name = "item_price_id")
	private BigInteger itemPriceId;

	@Column(name = "archived_date")
	private LocalDateTime archivedDate;

	/**
	 * @return the itemPriceId
	 */
	public BigInteger getItemPriceId() {
		return itemPriceId;
	}

	/**
	 * @param itemPriceId
	 *            the itemPriceId to set
	 */
	public void setItemPriceId(BigInteger itemPriceId) {
		this.itemPriceId = itemPriceId;
	}

	/**
	 * @return the archivedDate
	 */
	public LocalDateTime getArchivedDate() {
		return archivedDate;
	}

	/**
	 * @param archivedDate
	 *            the archivedDate to set
	 */
	public void setArchivedDate(LocalDateTime archivedDate) {
		this.archivedDate = archivedDate;
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
		result = prime * result + ((archivedDate == null) ? 0 : archivedDate.hashCode());
		result = prime * result + ((itemPriceId == null) ? 0 : itemPriceId.hashCode());
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
		ItemPriceHistoryId other = (ItemPriceHistoryId) obj;
		if (archivedDate == null) {
			if (other.archivedDate != null) {
				return false;
			}
		} else if (!archivedDate.equals(other.archivedDate)) {
			return false;
		}
		if (itemPriceId == null) {
			if (other.itemPriceId != null) {
				return false;
			}
		} else if (!itemPriceId.equals(other.itemPriceId)) {
			return false;
		}
		return true;
	}

}
