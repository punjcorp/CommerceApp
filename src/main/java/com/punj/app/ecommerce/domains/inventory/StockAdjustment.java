/**
 * 
 */
package com.punj.app.ecommerce.domains.inventory;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

/**
 * @author admin
 *
 */
@Indexed
@Entity
@Table(name = "stock_adjustment")
public class StockAdjustment implements Serializable {

	private static final long serialVersionUID = -2702423814300979765L;

	@Id
	@DocumentId
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "stock_adjust_id", updatable = false, nullable = false)
	private BigInteger stockAdjustId;

	@Field
	@Column(name = "location_id")
	private Integer locationId;

	@Field
	private String description;
	private String status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reason_code_id", nullable = false)
	private StockReason stockReason;

	@Field
	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "created_date")
	private LocalDateTime createdDate;

	@Column(name = "modified_by")
	private String modifiedBy;
	@Column(name = "modified_date")
	private LocalDateTime modifiedDate;

	@IndexedEmbedded
	@OneToMany(mappedBy = "stockAdjustmentItemId.stockAdjustment", cascade = CascadeType.ALL)
	private List<StockAdjustmentItem> stockAdjustItems;

	/**
	 * @return the stockAdjustId
	 */
	public BigInteger getStockAdjustId() {
		return stockAdjustId;
	}

	/**
	 * @param stockAdjustId
	 *            the stockAdjustId to set
	 */
	public void setStockAdjustId(BigInteger stockAdjustId) {
		this.stockAdjustId = stockAdjustId;
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the stockReason
	 */
	public StockReason getStockReason() {
		return stockReason;
	}

	/**
	 * @param stockReason
	 *            the stockReason to set
	 */
	public void setStockReason(StockReason stockReason) {
		this.stockReason = stockReason;
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
	 * @return the stockAdjustItems
	 */
	public List<StockAdjustmentItem> getStockAdjustItems() {
		return stockAdjustItems;
	}

	/**
	 * @param stockAdjustItems
	 *            the stockAdjustItems to set
	 */
	public void setStockAdjustItems(List<StockAdjustmentItem> stockAdjustItems) {
		this.stockAdjustItems = stockAdjustItems;
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
		result = prime * result + ((stockAdjustId == null) ? 0 : stockAdjustId.hashCode());
		result = prime * result + ((stockReason == null) ? 0 : stockReason.hashCode());
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
		StockAdjustment other = (StockAdjustment) obj;
		if (stockAdjustId == null) {
			if (other.stockAdjustId != null) {
				return false;
			}
		} else if (!stockAdjustId.equals(other.stockAdjustId)) {
			return false;
		}
		if (stockReason == null) {
			if (other.stockReason != null) {
				return false;
			}
		} else if (!stockReason.equals(other.stockReason)) {
			return false;
		}
		return true;
	}

}
