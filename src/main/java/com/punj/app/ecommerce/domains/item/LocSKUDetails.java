package com.punj.app.ecommerce.domains.item;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import com.punj.app.ecommerce.domains.item.ids.LocSKUDetailsId;

@Indexed
@Entity
@Table(name = "v_sku_dtls")
public class LocSKUDetails implements Serializable {

	private static final long serialVersionUID = 1751164775047315729L;

	@Id
	@DocumentId
	@Column(name = "loc_sku_id", updatable = false, nullable = false)
	private String locSKUId;

	@Field
	@Column(name = "location_id")
	private Integer locationId;
	
	@Field(analyzer = @Analyzer(definition = "edgeNgram"))
	@Column(name = "item_id")
	private BigInteger itemId;
	
	@Field(analyzer = @Analyzer(definition = "edgeNgram"))
	@Column(name = "item_name")
	private String name;
	@Field(analyzer = @Analyzer(definition = "edgeNgram"))
	@Column(name = "item_desc")
	private String description;

	@Field(analyzer = @Analyzer(definition = "edgeNgram"))
	@Column(name = "hsn_no")
	private String hsnNo;

	@Column(name = "suggested_price")
	private BigDecimal suggestedPrice = new BigDecimal(0);
	@Column(name = "max_retail_price")
	private BigDecimal maxRetailPrice = new BigDecimal(0);

	@Column(name = "total_qty")
	private Integer totalQty;
	@Column(name = "non_sellable_qty")
	private Integer nonSellableQty;
	@Column(name = "reserved_qty")
	private Integer reservedQty;
	@Column(name = "stock_on_hand")
	private Integer stockOnHand;

	@Column(name = "item_image_id")
	private BigInteger itemImageId;
	@Column(name = "image_name")
	private String imageName;
	@Column(name = "image_url")
	private String imageURL;
	@Column(name = "image_type")
	private String imageType;
	@Lob
	@Column(name = "image_data")
	private byte[] imageData;

	@Column(name = "base_unit_cost")
	private BigDecimal unitCost = new BigDecimal(0);

	@Column(name = "tax_group_id")
	private Integer taxGroupId;
	@Column(name = "tax_group_name")
	private String taxGroupName;

	@Column(name = "sgst_rate_rule_id")
	private Integer sgstRateRuleId;
	@Column(name = "sgst_amount")
	private BigDecimal sgstAmount;
	@Column(name = "sgst_rate")
	private BigDecimal sgstRate;
	@Column(name = "sgst_code")
	private String sgstCode;

	@Column(name = "cgst_rate_rule_id")
	private Integer cgstRateRuleId;
	@Column(name = "cgst_amount")
	private BigDecimal cgstAmount;
	@Column(name = "cgst_rate")
	private BigDecimal cgstRate;
	@Column(name = "cgst_code")
	private String cgstCode;

	@Column(name = "igst_rate_rule_id")
	private Integer igstRateRuleId;
	@Column(name = "igst_amount")
	private BigDecimal igstAmount;
	@Column(name = "igst_rate")
	private BigDecimal igstRate;
	@Column(name = "igst_code")
	private String igstCode;

	@Column(name = "item_price_id")
	private BigInteger itemPriceId;
	@Column(name = "price_change_type")
	private String priceChangeType;
	@Field
	@Column(name = "item_price")
	private BigDecimal itemPrice = new BigDecimal(0);
	@Column(name = "start_date")
	private LocalDateTime startDate;
	@Column(name = "end_date")
	private LocalDateTime endDate;


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
	 * @return the suggestedPrice
	 */
	public BigDecimal getSuggestedPrice() {
		return suggestedPrice;
	}

	/**
	 * @param suggestedPrice
	 *            the suggestedPrice to set
	 */
	public void setSuggestedPrice(BigDecimal suggestedPrice) {
		this.suggestedPrice = suggestedPrice;
	}

	/**
	 * @return the maxRetailPrice
	 */
	public BigDecimal getMaxRetailPrice() {
		return maxRetailPrice;
	}

	/**
	 * @param maxRetailPrice
	 *            the maxRetailPrice to set
	 */
	public void setMaxRetailPrice(BigDecimal maxRetailPrice) {
		this.maxRetailPrice = maxRetailPrice;
	}

	/**
	 * @return the totalQty
	 */
	public Integer getTotalQty() {
		return totalQty;
	}

	/**
	 * @param totalQty
	 *            the totalQty to set
	 */
	public void setTotalQty(Integer totalQty) {
		this.totalQty = totalQty;
	}

	/**
	 * @return the nonSellableQty
	 */
	public Integer getNonSellableQty() {
		return nonSellableQty;
	}

	/**
	 * @param nonSellableQty
	 *            the nonSellableQty to set
	 */
	public void setNonSellableQty(Integer nonSellableQty) {
		this.nonSellableQty = nonSellableQty;
	}

	/**
	 * @return the reservedQty
	 */
	public Integer getReservedQty() {
		return reservedQty;
	}

	/**
	 * @param reservedQty
	 *            the reservedQty to set
	 */
	public void setReservedQty(Integer reservedQty) {
		this.reservedQty = reservedQty;
	}

	/**
	 * @return the stockOnHand
	 */
	public Integer getStockOnHand() {
		return stockOnHand;
	}

	/**
	 * @param stockOnHand
	 *            the stockOnHand to set
	 */
	public void setStockOnHand(Integer stockOnHand) {
		this.stockOnHand = stockOnHand;
	}

	/**
	 * @return the itemImageId
	 */
	public BigInteger getItemImageId() {
		return itemImageId;
	}

	/**
	 * @param itemImageId
	 *            the itemImageId to set
	 */
	public void setItemImageId(BigInteger itemImageId) {
		this.itemImageId = itemImageId;
	}

	/**
	 * @return the imageName
	 */
	public String getImageName() {
		return imageName;
	}

	/**
	 * @param imageName
	 *            the imageName to set
	 */
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	/**
	 * @return the imageURL
	 */
	public String getImageURL() {
		return imageURL;
	}

	/**
	 * @param imageURL
	 *            the imageURL to set
	 */
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	/**
	 * @return the imageType
	 */
	public String getImageType() {
		return imageType;
	}

	/**
	 * @param imageType
	 *            the imageType to set
	 */
	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	/**
	 * @return the imageData
	 */
	public byte[] getImageData() {
		return imageData;
	}

	/**
	 * @param imageData
	 *            the imageData to set
	 */
	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}

	/**
	 * @return the unitCost
	 */
	public BigDecimal getUnitCost() {
		return unitCost;
	}

	/**
	 * @param unitCost
	 *            the unitCost to set
	 */
	public void setUnitCost(BigDecimal unitCost) {
		this.unitCost = unitCost;
	}

	/**
	 * @return the taxGroupId
	 */
	public Integer getTaxGroupId() {
		return taxGroupId;
	}

	/**
	 * @param taxGroupId
	 *            the taxGroupId to set
	 */
	public void setTaxGroupId(Integer taxGroupId) {
		this.taxGroupId = taxGroupId;
	}

	/**
	 * @return the taxGroupName
	 */
	public String getTaxGroupName() {
		return taxGroupName;
	}

	/**
	 * @param taxGroupName
	 *            the taxGroupName to set
	 */
	public void setTaxGroupName(String taxGroupName) {
		this.taxGroupName = taxGroupName;
	}

	/**
	 * @return the sgstRateRuleId
	 */
	public Integer getSgstRateRuleId() {
		return sgstRateRuleId;
	}

	/**
	 * @param sgstRateRuleId
	 *            the sgstRateRuleId to set
	 */
	public void setSgstRateRuleId(Integer sgstRateRuleId) {
		this.sgstRateRuleId = sgstRateRuleId;
	}

	/**
	 * @return the sgstAmount
	 */
	public BigDecimal getSgstAmount() {
		return sgstAmount;
	}

	/**
	 * @param sgstAmount
	 *            the sgstAmount to set
	 */
	public void setSgstAmount(BigDecimal sgstAmount) {
		this.sgstAmount = sgstAmount;
	}

	/**
	 * @return the sgstRate
	 */
	public BigDecimal getSgstRate() {
		return sgstRate;
	}

	/**
	 * @param sgstRate
	 *            the sgstRate to set
	 */
	public void setSgstRate(BigDecimal sgstRate) {
		this.sgstRate = sgstRate;
	}

	/**
	 * @return the sgstCode
	 */
	public String getSgstCode() {
		return sgstCode;
	}

	/**
	 * @param sgstCode
	 *            the sgstCode to set
	 */
	public void setSgstCode(String sgstCode) {
		this.sgstCode = sgstCode;
	}

	/**
	 * @return the cgstRateRuleId
	 */
	public Integer getCgstRateRuleId() {
		return cgstRateRuleId;
	}

	/**
	 * @param cgstRateRuleId
	 *            the cgstRateRuleId to set
	 */
	public void setCgstRateRuleId(Integer cgstRateRuleId) {
		this.cgstRateRuleId = cgstRateRuleId;
	}

	/**
	 * @return the cgstAmount
	 */
	public BigDecimal getCgstAmount() {
		return cgstAmount;
	}

	/**
	 * @param cgstAmount
	 *            the cgstAmount to set
	 */
	public void setCgstAmount(BigDecimal cgstAmount) {
		this.cgstAmount = cgstAmount;
	}

	/**
	 * @return the cgstRate
	 */
	public BigDecimal getCgstRate() {
		return cgstRate;
	}

	/**
	 * @param cgstRate
	 *            the cgstRate to set
	 */
	public void setCgstRate(BigDecimal cgstRate) {
		this.cgstRate = cgstRate;
	}

	/**
	 * @return the cgstCode
	 */
	public String getCgstCode() {
		return cgstCode;
	}

	/**
	 * @param cgstCode
	 *            the cgstCode to set
	 */
	public void setCgstCode(String cgstCode) {
		this.cgstCode = cgstCode;
	}

	/**
	 * @return the igstRateRuleId
	 */
	public Integer getIgstRateRuleId() {
		return igstRateRuleId;
	}

	/**
	 * @param igstRateRuleId
	 *            the igstRateRuleId to set
	 */
	public void setIgstRateRuleId(Integer igstRateRuleId) {
		this.igstRateRuleId = igstRateRuleId;
	}

	/**
	 * @return the igstAmount
	 */
	public BigDecimal getIgstAmount() {
		return igstAmount;
	}

	/**
	 * @param igstAmount
	 *            the igstAmount to set
	 */
	public void setIgstAmount(BigDecimal igstAmount) {
		this.igstAmount = igstAmount;
	}

	/**
	 * @return the igstRate
	 */
	public BigDecimal getIgstRate() {
		return igstRate;
	}

	/**
	 * @param igstRate
	 *            the igstRate to set
	 */
	public void setIgstRate(BigDecimal igstRate) {
		this.igstRate = igstRate;
	}

	/**
	 * @return the igstCode
	 */
	public String getIgstCode() {
		return igstCode;
	}

	/**
	 * @param igstCode
	 *            the igstCode to set
	 */
	public void setIgstCode(String igstCode) {
		this.igstCode = igstCode;
	}

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
	 * @return the priceChangeType
	 */
	public String getPriceChangeType() {
		return priceChangeType;
	}

	/**
	 * @param priceChangeType
	 *            the priceChangeType to set
	 */
	public void setPriceChangeType(String priceChangeType) {
		this.priceChangeType = priceChangeType;
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
	 * @return the hsnNo
	 */
	public String getHsnNo() {
		return hsnNo;
	}

	/**
	 * @param hsnNo
	 *            the hsnNo to set
	 */
	public void setHsnNo(String hsnNo) {
		this.hsnNo = hsnNo;
	}

	/**
	 * @return the locSKUId
	 */
	public String getLocSKUId() {
		return locSKUId;
	}

	/**
	 * @param locSKUId the locSKUId to set
	 */
	public void setLocSKUId(String locSKUId) {
		this.locSKUId = locSKUId;
	}

	/**
	 * @return the locationId
	 */
	public Integer getLocationId() {
		return locationId;
	}

	/**
	 * @param locationId the locationId to set
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
	 * @param itemId the itemId to set
	 */
	public void setItemId(BigInteger itemId) {
		this.itemId = itemId;
	}

}
