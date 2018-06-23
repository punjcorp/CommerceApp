
package com.punj.app.ecommerce.domains.item;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "item_options")
public class ItemOptions implements Serializable {

	private static final long serialVersionUID = 3685438029545786496L;

	@Id
	@Column(name = "item_id")
	private BigInteger itemId;

	@Column(name = "unit_cost")
	private BigDecimal unitCost = new BigDecimal(0);
	@Column(name = "suggested_price")
	private BigDecimal suggestedPrice = new BigDecimal(0);
	@Column(name = "compare_at_price")
	private BigDecimal compareAtPrice = new BigDecimal(0);
	@Column(name = "current_price")
	private BigDecimal currentPrice = new BigDecimal(0);
	@Column(name = "max_retail_price")
	private BigDecimal maxRetailPrice = new BigDecimal(0);
	@Column(name = "restocking_fee")
	private BigDecimal restockingFee = new BigDecimal(0);

	@Column(name = "discount_flag")
	private Boolean discountFlag;
	@Column(name = "tax_inclusive_flag")
	private Boolean taxInclusiveFlag;
	@Column(name = "tax_flag")
	private Boolean taxFlag;
	@Column(name = "ask_qty_flag")
	private Boolean askQtyFlag;
	@Column(name = "ask_price_flag")
	private Boolean askPriceFlag;
	@Column(name = "return_flag")
	private Boolean returnFlag;
	@Column(name = "desc_flag")
	private Boolean descFlag;
	@Column(name = "related_item_flag")
	private Boolean relatedItemFlag;
	@Column(name = "price_change_flag")
	private Boolean priceChangeFlag;
	@Column(name = "non_merch_flag")
	private Boolean nonMerchFlag;
	@Column(name = "min_age_flag")
	private Boolean minAgeFlag;
	@Column(name = "customer_prompt")
	private Boolean customerPrompt;

	private String uom;
	@Column(name = "tax_group_id")
	private Integer taxGroupId;
	@Column(name = "stock_status")
	private String stockStatus;
	@Column(name = "shipping_weight")
	private BigDecimal shippingWeight;
	@Column(name = "pack_size")
	private String packSize;
	@Column(name = "hsn_no")
	private String hsnNo;
	@Column(name = "next_level_created")
	private String nextLevelCreated;

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
	 * @return the compareAtPrice
	 */
	public BigDecimal getCompareAtPrice() {
		return compareAtPrice;
	}

	/**
	 * @param compareAtPrice
	 *            the compareAtPrice to set
	 */
	public void setCompareAtPrice(BigDecimal compareAtPrice) {
		this.compareAtPrice = compareAtPrice;
	}

	/**
	 * @return the currentPrice
	 */
	public BigDecimal getCurrentPrice() {
		return currentPrice;
	}

	/**
	 * @param currentPrice
	 *            the currentPrice to set
	 */
	public void setCurrentPrice(BigDecimal currentPrice) {
		this.currentPrice = currentPrice;
	}

	/**
	 * @return the restockingFee
	 */
	public BigDecimal getRestockingFee() {
		return restockingFee;
	}

	/**
	 * @param restockingFee
	 *            the restockingFee to set
	 */
	public void setRestockingFee(BigDecimal restockingFee) {
		this.restockingFee = restockingFee;
	}

	/**
	 * @return the discountFlag
	 */
	public Boolean getDiscountFlag() {
		return discountFlag;
	}

	/**
	 * @param discountFlag
	 *            the discountFlag to set
	 */
	public void setDiscountFlag(Boolean discountFlag) {
		this.discountFlag = discountFlag;
	}

	/**
	 * @return the taxFlag
	 */
	public Boolean getTaxFlag() {
		return taxFlag;
	}

	/**
	 * @param taxFlag
	 *            the taxFlag to set
	 */
	public void setTaxFlag(Boolean taxFlag) {
		this.taxFlag = taxFlag;
	}

	/**
	 * @return the askQtyFlag
	 */
	public Boolean getAskQtyFlag() {
		return askQtyFlag;
	}

	/**
	 * @param askQtyFlag
	 *            the askQtyFlag to set
	 */
	public void setAskQtyFlag(Boolean askQtyFlag) {
		this.askQtyFlag = askQtyFlag;
	}

	/**
	 * @return the askPriceFlag
	 */
	public Boolean getAskPriceFlag() {
		return askPriceFlag;
	}

	/**
	 * @param askPriceFlag
	 *            the askPriceFlag to set
	 */
	public void setAskPriceFlag(Boolean askPriceFlag) {
		this.askPriceFlag = askPriceFlag;
	}

	/**
	 * @return the returnFlag
	 */
	public Boolean getReturnFlag() {
		return returnFlag;
	}

	/**
	 * @param returnFlag
	 *            the returnFlag to set
	 */
	public void setReturnFlag(Boolean returnFlag) {
		this.returnFlag = returnFlag;
	}

	/**
	 * @return the descFlag
	 */
	public Boolean getDescFlag() {
		return descFlag;
	}

	/**
	 * @param descFlag
	 *            the descFlag to set
	 */
	public void setDescFlag(Boolean descFlag) {
		this.descFlag = descFlag;
	}

	/**
	 * @return the relatedItemFlag
	 */
	public Boolean getRelatedItemFlag() {
		return relatedItemFlag;
	}

	/**
	 * @param relatedItemFlag
	 *            the relatedItemFlag to set
	 */
	public void setRelatedItemFlag(Boolean relatedItemFlag) {
		this.relatedItemFlag = relatedItemFlag;
	}

	/**
	 * @return the priceChangeFlag
	 */
	public Boolean getPriceChangeFlag() {
		return priceChangeFlag;
	}

	/**
	 * @param priceChangeFlag
	 *            the priceChangeFlag to set
	 */
	public void setPriceChangeFlag(Boolean priceChangeFlag) {
		this.priceChangeFlag = priceChangeFlag;
	}

	/**
	 * @return the nonMerchFlag
	 */
	public Boolean getNonMerchFlag() {
		return nonMerchFlag;
	}

	/**
	 * @param nonMerchFlag
	 *            the nonMerchFlag to set
	 */
	public void setNonMerchFlag(Boolean nonMerchFlag) {
		this.nonMerchFlag = nonMerchFlag;
	}

	/**
	 * @return the minAgeFlag
	 */
	public Boolean getMinAgeFlag() {
		return minAgeFlag;
	}

	/**
	 * @param minAgeFlag
	 *            the minAgeFlag to set
	 */
	public void setMinAgeFlag(Boolean minAgeFlag) {
		this.minAgeFlag = minAgeFlag;
	}

	/**
	 * @return the customerPrompt
	 */
	public Boolean getCustomerPrompt() {
		return customerPrompt;
	}

	/**
	 * @param customerPrompt
	 *            the customerPrompt to set
	 */
	public void setCustomerPrompt(Boolean customerPrompt) {
		this.customerPrompt = customerPrompt;
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
	 * @return the stockStatus
	 */
	public String getStockStatus() {
		return stockStatus;
	}

	/**
	 * @param stockStatus
	 *            the stockStatus to set
	 */
	public void setStockStatus(String stockStatus) {
		this.stockStatus = stockStatus;
	}

	/**
	 * @return the shippingWeight
	 */
	public BigDecimal getShippingWeight() {
		return shippingWeight;
	}

	/**
	 * @param shippingWeight
	 *            the shippingWeight to set
	 */
	public void setShippingWeight(BigDecimal shippingWeight) {
		this.shippingWeight = shippingWeight;
	}

	/**
	 * @return the packSize
	 */
	public String getPackSize() {
		return packSize;
	}

	/**
	 * @param packSize
	 *            the packSize to set
	 */
	public void setPackSize(String packSize) {
		this.packSize = packSize;
	}

	/**
	 * @return the uom
	 */
	public String getUom() {
		return uom;
	}

	/**
	 * @param uom
	 *            the uom to set
	 */
	public void setUom(String uom) {
		this.uom = uom;
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
	 * @return the nextLevelCreated
	 */
	public String getNextLevelCreated() {
		return nextLevelCreated;
	}

	/**
	 * @param nextLevelCreated the nextLevelCreated to set
	 */
	public void setNextLevelCreated(String nextLevelCreated) {
		this.nextLevelCreated = nextLevelCreated;
	}

	/**
	 * @return the taxInclusiveFlag
	 */
	public Boolean getTaxInclusiveFlag() {
		return taxInclusiveFlag;
	}

	/**
	 * @param taxInclusiveFlag the taxInclusiveFlag to set
	 */
	public void setTaxInclusiveFlag(Boolean taxInclusiveFlag) {
		this.taxInclusiveFlag = taxInclusiveFlag;
	}

}
