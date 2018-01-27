/**
 * 
 */
package com.punj.app.ecommerce.domains.inventory;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "stock_reason_code")
public class StockReason implements Serializable {

	private static final long serialVersionUID = -8690012873931948210L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "reason_code_id", updatable = false, nullable = false)
	private Integer reasonCodeId;

	@Column(name = "reason_code")
	private String reasonCode;

	private String name;

	@Column(name = "from_stock_action")
	private String fromAction;
	@Column(name = "to_stock_action")
	private String toAction;

	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "created_date")
	private LocalDateTime createdDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "from_bucket_id", nullable = false)
	private StockBucket fromBucket;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "to_bucket_id", nullable = false)
	private StockBucket toBucket;

	/**
	 * @return the reasonCodeId
	 */
	public Integer getReasonCodeId() {
		return reasonCodeId;
	}

	/**
	 * @param reasonCodeId
	 *            the reasonCodeId to set
	 */
	public void setReasonCodeId(Integer reasonCodeId) {
		this.reasonCodeId = reasonCodeId;
	}

	/**
	 * @return the reasonCode
	 */
	public String getReasonCode() {
		return reasonCode;
	}

	/**
	 * @param reasonCode
	 *            the reasonCode to set
	 */
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the fromAction
	 */
	public String getFromAction() {
		return fromAction;
	}

	/**
	 * @param fromAction
	 *            the fromAction to set
	 */
	public void setFromAction(String fromAction) {
		this.fromAction = fromAction;
	}

	/**
	 * @return the toAction
	 */
	public String getToAction() {
		return toAction;
	}

	/**
	 * @param toAction
	 *            the toAction to set
	 */
	public void setToAction(String toAction) {
		this.toAction = toAction;
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
	 * @return the fromBucket
	 */
	public StockBucket getFromBucket() {
		return fromBucket;
	}

	/**
	 * @param fromBucket
	 *            the fromBucket to set
	 */
	public void setFromBucket(StockBucket fromBucket) {
		this.fromBucket = fromBucket;
	}

	/**
	 * @return the toBucket
	 */
	public StockBucket getToBucket() {
		return toBucket;
	}

	/**
	 * @param toBucket
	 *            the toBucket to set
	 */
	public void setToBucket(StockBucket toBucket) {
		this.toBucket = toBucket;
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
		result = prime * result + ((reasonCode == null) ? 0 : reasonCode.hashCode());
		result = prime * result + ((reasonCodeId == null) ? 0 : reasonCodeId.hashCode());
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
		StockReason other = (StockReason) obj;
		if (reasonCode == null) {
			if (other.reasonCode != null) {
				return false;
			}
		} else if (!reasonCode.equals(other.reasonCode)) {
			return false;
		}
		if (reasonCodeId == null) {
			if (other.reasonCodeId != null) {
				return false;
			}
		} else if (!reasonCodeId.equals(other.reasonCodeId)) {
			return false;
		}
		return true;
	}

}
