/**
 * 
 */
package com.punj.app.ecommerce.domains.finance;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author admin
 *
 */
@Entity
@Table(name = "daily_repository")
public class DailySafe implements Serializable {

	private static final long serialVersionUID = 6541108161666763835L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "daily_repository_id", updatable = false, nullable = false)
	private Integer dailyRepositoryId;

	@Column(name = "location_repository_id")
	private Integer locationRepositoryId;
	@Column(name = "business_date")
	private LocalDateTime businessDate;

	private BigDecimal amount;

	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "created_date")
	private LocalDateTime createdDate;
	@Column(name = "modified_by")
	private String modifiedBy;
	@Column(name = "modified_date")
	private LocalDateTime modifiedDate;

	/**
	 * @return the dailyRepositoryId
	 */
	public Integer getDailyRepositoryId() {
		return dailyRepositoryId;
	}

	/**
	 * @param dailyRepositoryId
	 *            the dailyRepositoryId to set
	 */
	public void setDailyRepositoryId(Integer dailyRepositoryId) {
		this.dailyRepositoryId = dailyRepositoryId;
	}

	/**
	 * @return the locationRepositoryId
	 */
	public Integer getLocationRepositoryId() {
		return locationRepositoryId;
	}

	/**
	 * @param locationRepositoryId
	 *            the locationRepositoryId to set
	 */
	public void setLocationRepositoryId(Integer locationRepositoryId) {
		this.locationRepositoryId = locationRepositoryId;
	}

	/**
	 * @return the businessDate
	 */
	public LocalDateTime getBusinessDate() {
		return businessDate;
	}

	/**
	 * @param businessDate
	 *            the businessDate to set
	 */
	public void setBusinessDate(LocalDateTime businessDate) {
		this.businessDate = businessDate;
	}

	/**
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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

}
