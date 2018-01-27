package com.punj.app.ecommerce.domains.price;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.punj.app.ecommerce.domains.price.ids.ItemPriceHistoryId;

@Entity
@Table(name = "item_price_history")
public class ItemPriceHistory implements Serializable {

	private static final long serialVersionUID = -1949176761016060270L;

	@EmbeddedId
	private ItemPriceHistoryId itemPriceHistoryId;

	@Column(name = "price_change_type")
	private String type;

	@Column(name = "start_date")
	private LocalDateTime startDate;
	@Column(name = "end_date")
	private LocalDateTime endDate;

	@Column(name = "item_price")
	private BigDecimal itemPrice;

	private String status;

	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "created_date")
	private LocalDateTime createdDate;
	@Column(name = "modified_by")
	private String modifiedBy;
	@Column(name = "modified_date")
	private LocalDateTime modifiedDate;

	@Column(name = "location_id")
	private Integer locationId;

	@Column(name = "item_id")
	private BigInteger itemId;

	/**
	 * @return the itemPriceHistoryId
	 */
	public ItemPriceHistoryId getItemPriceHistoryId() {
		return itemPriceHistoryId;
	}

	/**
	 * @param itemPriceHistoryId
	 *            the itemPriceHistoryId to set
	 */
	public void setItemPriceHistoryId(ItemPriceHistoryId itemPriceHistoryId) {
		this.itemPriceHistoryId = itemPriceHistoryId;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the startDate
	 */
	public LocalDateTime getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public LocalDateTime getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the itemPrice
	 */
	public BigDecimal getItemPrice() {
		return itemPrice;
	}

	/**
	 * @param itemPrice
	 *            the itemPrice to set
	 */
	public void setItemPrice(BigDecimal itemPrice) {
		this.itemPrice = itemPrice;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the createdDate
	 */
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate
	 *            the createdDate to set
	 */
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the modifiedBy
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * @param modifiedBy
	 *            the modifiedBy to set
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * @return the modifiedDate
	 */
	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	/**
	 * @param modifiedDate
	 *            the modifiedDate to set
	 */
	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/**
	 * @return the locationId
	 */
	public Integer getLocationId() {
		return locationId;
	}

	/**
	 * @param locationId
	 *            the locationId to set
	 */
	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	/**
	 * @return the itemId
	 */
	public BigInteger getItemId() {
		return itemId;
	}

	/**
	 * @param itemId
	 *            the itemId to set
	 */
	public void setItemId(BigInteger itemId) {
		this.itemId = itemId;
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
		result = prime * result + ((itemPriceHistoryId == null) ? 0 : itemPriceHistoryId.hashCode());
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
		ItemPriceHistory other = (ItemPriceHistory) obj;
		if (itemPriceHistoryId == null) {
			if (other.itemPriceHistoryId != null) {
				return false;
			}
		} else if (!itemPriceHistoryId.equals(other.itemPriceHistoryId)) {
			return false;
		}
		return true;
	}

}
