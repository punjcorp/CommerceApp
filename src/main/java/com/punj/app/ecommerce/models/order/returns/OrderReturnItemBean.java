/**
 * 
 */
package com.punj.app.ecommerce.models.order.returns;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.validation.constraints.NotNull;

import com.punj.app.ecommerce.models.common.validator.ValidationGroup;

/**
 * @author admin
 *
 */
public class OrderReturnItemBean {

	private BigInteger orderReturnItemId;

	@NotNull(message = "{commerce.error.string.empty}", groups = { ValidationGroup.VGAddOrderReturn.class })
	private BigInteger itemId;

	private BigInteger orderId;

	private String itemDesc;

	private BigInteger orderReturnId;
	private Integer taxGroupId;

	@NotNull(message = "{commerce.error.string.empty}", groups = { ValidationGroup.VGAddOrderReturn.class })
	private BigDecimal returnedQty;

	@NotNull(message = "{commerce.error.option.empty}", groups = { ValidationGroup.VGAddOrderReturn.class })
	private Integer reasonCodeId;

	private BigDecimal unitCost = BigDecimal.ZERO;
	private BigDecimal costAmount = BigDecimal.ZERO;
	private BigDecimal totalCost = BigDecimal.ZERO;
	private BigDecimal discountAmount = BigDecimal.ZERO;

	private BigInteger sgstTaxId;
	private BigInteger cgstTaxId;
	private BigInteger igstTaxId;

	private String sgstCode;
	private String cgstCode;
	private String igstCode;

	private Integer sgstRateRuleId;
	private Integer cgstRateRuleId;
	private Integer igstRateRuleId;

	private BigDecimal cgstRate = BigDecimal.ZERO;
	private BigDecimal sgstRate = BigDecimal.ZERO;
	private BigDecimal igstRate = BigDecimal.ZERO;

	private BigDecimal cgstTaxAmount = BigDecimal.ZERO;
	private BigDecimal sgstTaxAmount = BigDecimal.ZERO;
	private BigDecimal igstTaxAmount = BigDecimal.ZERO;

	private BigDecimal cgstActualTaxAmount = BigDecimal.ZERO;
	private BigDecimal sgstActualTaxAmount = BigDecimal.ZERO;
	private BigDecimal igstActualTaxAmount = BigDecimal.ZERO;

	private BigDecimal taxAmount = BigDecimal.ZERO;

	/**
	 * @return the orderReturnItemId
	 */
	public BigInteger getOrderReturnItemId() {
		return orderReturnItemId;
	}

	/**
	 * @param orderReturnItemId
	 *            the orderReturnItemId to set
	 */
	public void setOrderReturnItemId(BigInteger orderReturnItemId) {
		this.orderReturnItemId = orderReturnItemId;
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
	 * @return the orderReturnId
	 */
	public BigInteger getOrderReturnId() {
		return orderReturnId;
	}

	/**
	 * @param orderReturnId
	 *            the orderReturnId to set
	 */
	public void setOrderReturnId(BigInteger orderReturnId) {
		this.orderReturnId = orderReturnId;
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
	 * @return the returnedQty
	 */
	public BigDecimal getReturnedQty() {
		return returnedQty;
	}

	/**
	 * @param returnedQty
	 *            the returnedQty to set
	 */
	public void setReturnedQty(BigDecimal returnedQty) {
		this.returnedQty = returnedQty;
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
	 * @return the costAmount
	 */
	public BigDecimal getCostAmount() {
		return costAmount;
	}

	/**
	 * @param costAmount
	 *            the costAmount to set
	 */
	public void setCostAmount(BigDecimal costAmount) {
		this.costAmount = costAmount;
	}

	/**
	 * @return the totalCost
	 */
	public BigDecimal getTotalCost() {
		return totalCost;
	}

	/**
	 * @param totalCost
	 *            the totalCost to set
	 */
	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}

	/**
	 * @return the sgstTaxId
	 */
	public BigInteger getSgstTaxId() {
		return sgstTaxId;
	}

	/**
	 * @param sgstTaxId
	 *            the sgstTaxId to set
	 */
	public void setSgstTaxId(BigInteger sgstTaxId) {
		this.sgstTaxId = sgstTaxId;
	}

	/**
	 * @return the cgstTaxId
	 */
	public BigInteger getCgstTaxId() {
		return cgstTaxId;
	}

	/**
	 * @param cgstTaxId
	 *            the cgstTaxId to set
	 */
	public void setCgstTaxId(BigInteger cgstTaxId) {
		this.cgstTaxId = cgstTaxId;
	}

	/**
	 * @return the igstTaxId
	 */
	public BigInteger getIgstTaxId() {
		return igstTaxId;
	}

	/**
	 * @param igstTaxId
	 *            the igstTaxId to set
	 */
	public void setIgstTaxId(BigInteger igstTaxId) {
		this.igstTaxId = igstTaxId;
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
	 * @return the cgstTaxAmount
	 */
	public BigDecimal getCgstTaxAmount() {
		return cgstTaxAmount;
	}

	/**
	 * @param cgstTaxAmount
	 *            the cgstTaxAmount to set
	 */
	public void setCgstTaxAmount(BigDecimal cgstTaxAmount) {
		this.cgstTaxAmount = cgstTaxAmount;
	}

	/**
	 * @return the sgstTaxAmount
	 */
	public BigDecimal getSgstTaxAmount() {
		return sgstTaxAmount;
	}

	/**
	 * @param sgstTaxAmount
	 *            the sgstTaxAmount to set
	 */
	public void setSgstTaxAmount(BigDecimal sgstTaxAmount) {
		this.sgstTaxAmount = sgstTaxAmount;
	}

	/**
	 * @return the igstTaxAmount
	 */
	public BigDecimal getIgstTaxAmount() {
		return igstTaxAmount;
	}

	/**
	 * @param igstTaxAmount
	 *            the igstTaxAmount to set
	 */
	public void setIgstTaxAmount(BigDecimal igstTaxAmount) {
		this.igstTaxAmount = igstTaxAmount;
	}

	/**
	 * @return the cgstActualTaxAmount
	 */
	public BigDecimal getCgstActualTaxAmount() {
		return cgstActualTaxAmount;
	}

	/**
	 * @param cgstActualTaxAmount
	 *            the cgstActualTaxAmount to set
	 */
	public void setCgstActualTaxAmount(BigDecimal cgstActualTaxAmount) {
		this.cgstActualTaxAmount = cgstActualTaxAmount;
	}

	/**
	 * @return the sgstActualTaxAmount
	 */
	public BigDecimal getSgstActualTaxAmount() {
		return sgstActualTaxAmount;
	}

	/**
	 * @param sgstActualTaxAmount
	 *            the sgstActualTaxAmount to set
	 */
	public void setSgstActualTaxAmount(BigDecimal sgstActualTaxAmount) {
		this.sgstActualTaxAmount = sgstActualTaxAmount;
	}

	/**
	 * @return the igstActualTaxAmount
	 */
	public BigDecimal getIgstActualTaxAmount() {
		return igstActualTaxAmount;
	}

	/**
	 * @param igstActualTaxAmount
	 *            the igstActualTaxAmount to set
	 */
	public void setIgstActualTaxAmount(BigDecimal igstActualTaxAmount) {
		this.igstActualTaxAmount = igstActualTaxAmount;
	}

	/**
	 * @return the taxAmount
	 */
	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	/**
	 * @param taxAmount
	 *            the taxAmount to set
	 */
	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

	/**
	 * @return the discountAmount
	 */
	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	/**
	 * @param discountAmount
	 *            the discountAmount to set
	 */
	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

	/**
	 * @return the returnReasonCodeId
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
	 * @return the orderId
	 */
	public BigInteger getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId
	 *            the orderId to set
	 */
	public void setOrderId(BigInteger orderId) {
		this.orderId = orderId;
	}

}
