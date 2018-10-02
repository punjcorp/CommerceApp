/**
 * 
 */
package com.punj.app.ecommerce.models.price;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.punj.app.ecommerce.models.common.LocationBean;
import com.punj.app.ecommerce.services.dtos.SaleItem;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
public class PriceBean {

	private BigInteger itemPriceId;

	@NotNull(message = "{commerce.error.item.empty}")
	private BigInteger itemId;
	@NotNull(message = "{commerce.error.item.empty}")
	private String itemDesc;
	@NotNull(message = "{commerce.error.select.empty}")
	private Integer locationId;
	private List<LocationBean> locations;
	@NotEmpty(message = "{commerce.error.select.empty}")
	private String priceType;
	private String priceTypeDesc;
	@NotNull(message = "{commerce.error.date.empty}")
	private LocalDateTime startDate;
	private LocalDateTime endDate;

	private BigInteger clearanceResetId;
	private PriceBean existingClearance;
	private SaleItem priceDtls;

	private String createdBy;
	private LocalDateTime createdDate;

	private String modifiedBy;
	private LocalDateTime modifiedDate;

	/*
	 * @NotNull(message = "{commerce.error.amount.empty}")
	 * 
	 * @DecimalMin(value = "0.01", message = "{commerce.error.amt.range}")
	 * 
	 * @DecimalMax(value = "9999999999.99", message = "{commerce.error.amt.range}")
	 */ private BigDecimal itemPriceAmt;
	 
	private String taxType;

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
	 * @param itemPriceAmt
	 *            the itemPriceAmt to set
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

	/**
	 * @return the clearanceResetId
	 */
	public BigInteger getClearanceResetId() {
		return clearanceResetId;
	}

	/**
	 * @param clearanceResetId
	 *            the clearanceResetId to set
	 */
	public void setClearanceResetId(BigInteger clearanceResetId) {
		this.clearanceResetId = clearanceResetId;
	}

	/**
	 * @return the existingClearance
	 */
	public PriceBean getExistingClearance() {
		return existingClearance;
	}

	/**
	 * @param existingClearance
	 *            the existingClearance to set
	 */
	public void setExistingClearance(PriceBean existingClearance) {
		this.existingClearance = existingClearance;
	}

	/**
	 * @return the itemDesc
	 */
	public String getItemDesc() {
		return itemDesc;
	}

	/**
	 * @param itemDesc
	 *            the itemDesc to set
	 */
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	/**
	 * @return the priceDtls
	 */
	public SaleItem getPriceDtls() {
		return priceDtls;
	}

	/**
	 * @param priceDtls the priceDtls to set
	 */
	public void setPriceDtls(SaleItem priceDtls) {
		this.priceDtls = priceDtls;
	}

	/**
	 * @return the priceTypeDesc
	 */
	public String getPriceTypeDesc() {
		return priceTypeDesc;
	}

	/**
	 * @param priceTypeDesc the priceTypeDesc to set
	 */
	public void setPriceTypeDesc(String priceTypeDesc) {
		this.priceTypeDesc = priceTypeDesc;
	}

	/**
	 * @return the taxType
	 */
	public String getTaxType() {
		return taxType;
	}

	/**
	 * @param taxType the taxType to set
	 */
	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}

}
