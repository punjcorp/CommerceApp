/**
 * 
 */
package com.punj.app.ecommerce.models.financials;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

/**
 * @author admin
 *
 */
public class AccountHeadBean {

	private Integer locationId;
	private Integer accountId;
	private String entityType;
	private BigInteger entityId;
	private String entityName;

	private BigDecimal advanceAmt;
	private BigDecimal dueAmt;
	private BigDecimal paymentAmt;
	private String createdBy;
	private LocalDateTime createdDate;

	private String modifiedBy;
	private LocalDateTime modifiedDate;

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
	 * @return the accountId
	 */
	public Integer getAccountId() {
		return accountId;
	}

	/**
	 * @param accountId
	 *            the accountId to set
	 */
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	/**
	 * @return the entityType
	 */
	public String getEntityType() {
		return entityType;
	}

	/**
	 * @param entityType
	 *            the entityType to set
	 */
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	/**
	 * @return the entityId
	 */
	public BigInteger getEntityId() {
		return entityId;
	}

	/**
	 * @param entityId
	 *            the entityId to set
	 */
	public void setEntityId(BigInteger entityId) {
		this.entityId = entityId;
	}

	/**
	 * @return the advanceAmt
	 */
	public BigDecimal getAdvanceAmt() {
		return advanceAmt;
	}

	/**
	 * @param advanceAmt
	 *            the advanceAmt to set
	 */
	public void setAdvanceAmt(BigDecimal advanceAmt) {
		this.advanceAmt = advanceAmt;
	}

	/**
	 * @return the dueAmt
	 */
	public BigDecimal getDueAmt() {
		return dueAmt;
	}

	/**
	 * @param dueAmt
	 *            the dueAmt to set
	 */
	public void setDueAmt(BigDecimal dueAmt) {
		this.dueAmt = dueAmt;
	}

	/**
	 * @return the paymentAmt
	 */
	public BigDecimal getPaymentAmt() {
		return paymentAmt;
	}

	/**
	 * @param paymentAmt
	 *            the paymentAmt to set
	 */
	public void setPaymentAmt(BigDecimal paymentAmt) {
		this.paymentAmt = paymentAmt;
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
	 * @return the entityName
	 */
	public String getEntityName() {
		return entityName;
	}

	/**
	 * @param entityName the entityName to set
	 */
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

}
