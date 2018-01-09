/**
 * 
 */
package com.punj.app.ecommerce.models.item;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author admin
 *
 */
public class ItemOptionsBean {

	private BigInteger itemId;
	private String uom;
	private Boolean discountFlag = true;
	private Boolean taxFlag = true;
	private Boolean askQtyFlag = true;
	private Boolean askPriceFlag = false;
	private Boolean returnFlag = true;
	private Boolean descFlag = false;
	private Boolean relatedItemFlag = false;
	private Boolean priceChangeFlag = true;
	private Boolean nonMerchFlag = false;
	private Boolean minAgeFlag = false;
	private Boolean customerPromptFlag = false;

	private BigDecimal unitCost;
	private BigDecimal suggestedPrice;
	private BigDecimal compareAtPrice;
	private BigDecimal currentPrice;
	private BigDecimal restockingFee;

	private String packSize = "1";
	private String stockStatus = "NIL";
	private BigDecimal shippingWeight;

	private Integer taxGroupId;

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
	 * @return the customerPromptFlag
	 */
	public Boolean getCustomerPromptFlag() {
		return customerPromptFlag;
	}

	/**
	 * @param customerPromptFlag
	 *            the customerPromptFlag to set
	 */
	public void setCustomerPromptFlag(Boolean customerPromptFlag) {
		this.customerPromptFlag = customerPromptFlag;
	}

}
