/**
 * 
 */
package com.punj.app.ecommerce.domains.inventory;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author admin
 *
 */
@Entity
@Table(name = "item_stock_journal")
public class ItemStockJournal implements Serializable {

	private static final long serialVersionUID = -4897105515860853225L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "stock_journal_id", updatable = false, nullable = false)
	private Integer journalId;

	@ManyToOne
	@JoinColumn(name = "reason_code_id")
	private StockReason reasonCode;

	@Column(name = "item_id")
	private BigInteger itemId;

	@Column(name = "location_id")
	private Integer locationId;

	private String functionality;
	private Integer qty;

	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "created_date")
	private LocalDateTime createdDate;

	/**
	 * @return the journalId
	 */
	public Integer getJournalId() {
		return journalId;
	}

	/**
	 * @param journalId
	 *            the journalId to set
	 */
	public void setJournalId(Integer journalId) {
		this.journalId = journalId;
	}

	/**
	 * @return the reasonCode
	 */
	public StockReason getReasonCode() {
		return reasonCode;
	}

	/**
	 * @param reasonCode
	 *            the reasonCode to set
	 */
	public void setReasonCode(StockReason reasonCode) {
		this.reasonCode = reasonCode;
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

	/**
	 * @return the functionality
	 */
	public String getFunctionality() {
		return functionality;
	}

	/**
	 * @param functionality
	 *            the functionality to set
	 */
	public void setFunctionality(String functionality) {
		this.functionality = functionality;
	}

	/**
	 * @return the qty
	 */
	public Integer getQty() {
		return qty;
	}

	/**
	 * @param qty
	 *            the qty to set
	 */
	public void setQty(Integer qty) {
		this.qty = qty;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result + ((journalId == null) ? 0 : journalId.hashCode());
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
		ItemStockJournal other = (ItemStockJournal) obj;
		if (createdDate == null) {
			if (other.createdDate != null) {
				return false;
			}
		} else if (!createdDate.equals(other.createdDate)) {
			return false;
		}
		if (journalId == null) {
			if (other.journalId != null) {
				return false;
			}
		} else if (!journalId.equals(other.journalId)) {
			return false;
		}
		return true;
	}

}
