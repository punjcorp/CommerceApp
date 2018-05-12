/**
 * 
 */
package com.punj.app.ecommerce.models.price;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import javax.money.MonetaryAmount;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.punj.app.ecommerce.models.common.LocationBean;
import com.punj.app.ecommerce.models.common.validator.ValidationGroup;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
public class PriceBean {

	private BigInteger itemPriceId;

	@NotNull(message = "{commerce.error.item.empty}")
	private BigInteger itemId;
	@NotNull(message = "{commerce.error.select.empty}")
	private Integer locationId;
	private List<LocationBean> locations;
	@NotEmpty(message = "{commerce.error.select.empty}")
	private String priceType;
	@NotNull(message = "{commerce.error.date.empty}")
	private LocalDateTime startDate;
	private LocalDateTime endDate;

	private String createdBy;
	private LocalDateTime createdDate;

	private String modifiedBy;
	private LocalDateTime modifiedDate;
	
	@NotNull(message = "{commerce.error.amount.empty}")
	@DecimalMin(value = "0.01", message = "{commerce.error.amt.range}")
	@DecimalMax(value = "9999999999.99", message = "{commerce.error.amt.range}")
	private BigDecimal itemPriceAmt;

	private String status;

	private Pager pager;

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
	 * @return the itemPriceAmt
	 */
	public BigDecimal getItemPriceAmt() {
		return itemPriceAmt;
	}

	/**
	 * @param itemPriceAmt the itemPriceAmt to set
	 */
	public void setItemPriceAmt(BigDecimal itemPriceAmt) {
		this.itemPriceAmt = itemPriceAmt;
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
	 * @return the pager
	 */
	public Pager getPager() {
		return pager;
	}

	/**
	 * @param pager
	 *            the pager to set
	 */
	public void setPager(Pager pager) {
		this.pager = pager;
	}

	/**
	 * @return the locations
	 */
	public List<LocationBean> getLocations() {
		return locations;
	}

	/**
	 * @param locations
	 *            the locations to set
	 */
	public void setLocations(List<LocationBean> locations) {
		this.locations = locations;
	}

	/**
	 * @return the priceType
	 */
	public String getPriceType() {
		return priceType;
	}

	/**
	 * @param priceType
	 *            the priceType to set
	 */
	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

}
