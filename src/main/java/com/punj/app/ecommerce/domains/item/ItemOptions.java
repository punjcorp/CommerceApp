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
	@Id
	@Column(name = "item_id")
	private BigInteger itemId;

	@Column(name = "unit_cost")
	private BigDecimal unitCost;
	@Column(name = "suggested_price")
	private BigDecimal suggestedPrice;
	@Column(name = "compare_at_price")
	private BigDecimal compareAtPrice;
	@Column(name = "current_price")
	private BigDecimal currentPrice;
	@Column(name = "restocking_fee")
	private BigDecimal restockingFee;

	@Column(name = "discount_flag")
	private Boolean discountFlag;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((itemId == null) ? 0 : itemId.hashCode());
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
		ItemOptions other = (ItemOptions) obj;
		if (itemId == null) {
			if (other.itemId != null) {
				return false;
			}
		} else if (!itemId.equals(other.itemId)) {
			return false;
		}
		return true;
	}

}
