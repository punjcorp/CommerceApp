/**
 * 
 */
package com.punj.app.ecommerce.domains.finance;

import java.io.Serializable;
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
@Table(name = "location_repository")
public class LocationSafe implements Serializable {

	private static final long serialVersionUID = 1160918226526628107L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "location_repository_id", updatable = false, nullable = false)
	private Integer locationRepositoryId;

	@Column(name = "repository_id")
	private Integer repositoryId;
	@Column(name = "location_id")
	private Integer locationId;
	@Column(name = "tender_id")
	private Integer tenderId;
	@Column(name = "reconcilation_flag")
	private Boolean reconcilationFlag;

	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "created_date")
	private LocalDateTime createdDate;
	@Column(name = "modified_by")
	private String modifiedBy;
	@Column(name = "modified_date")
	private LocalDateTime modifiedDate;

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
	 * @return the repositoryId
	 */
	public Integer getRepositoryId() {
		return repositoryId;
	}

	/**
	 * @param repositoryId
	 *            the repositoryId to set
	 */
	public void setRepositoryId(Integer repositoryId) {
		this.repositoryId = repositoryId;
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
	 * @return the tenderId
	 */
	public Integer getTenderId() {
		return tenderId;
	}

	/**
	 * @param tenderId
	 *            the tenderId to set
	 */
	public void setTenderId(Integer tenderId) {
		this.tenderId = tenderId;
	}

	/**
	 * @return the reconcilationFlag
	 */
	public Boolean getReconcilationFlag() {
		return reconcilationFlag;
	}

	/**
	 * @param reconcilationFlag
	 *            the reconcilationFlag to set
	 */
	public void setReconcilationFlag(Boolean reconcilationFlag) {
		this.reconcilationFlag = reconcilationFlag;
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
