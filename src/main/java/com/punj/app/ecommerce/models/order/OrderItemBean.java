/**
 * 
 */
package com.punj.app.ecommerce.models.order;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import com.punj.app.ecommerce.models.common.validator.ValidationGroup;
import com.punj.app.ecommerce.models.supplier.SupplierItemBean;

/**
 * @author admin
 *
 */
public class OrderItemBean {

	private BigInteger orderItemId;

	@NotNull(message = "{commerce.error.string.empty}", groups = { ValidationGroup.VGAddOrder.class })
	private BigInteger itemId;

	private String itemDesc;

	private BigInteger orderId;
	private Integer taxGroupId;

	@NotNull(message = "{commerce.error.string.empty}", groups = { ValidationGroup.VGAddOrder.class })
	@Range(min = 1, max = 9999, message = "{commerce.error.nbr.range}", groups = { ValidationGroup.VGAddOrder.class })
	private BigDecimal orderedQty;

	@NotNull(message = "{commerce.error.string.empty}", groups = { ValidationGroup.VGAddOrder.class })
	@DecimalMin(value = "0.01", message = "{commerce.error.amt.range}", groups = { ValidationGroup.VGAddOrder.class })
	@DecimalMax(value = "9999999999.99", message = "{commerce.error.amt.range}", groups = { ValidationGroup.VGAddOrder.class })
	private BigDecimal unitCost = BigDecimal.ZERO;
	private BigDecimal costAmount = BigDecimal.ZERO;
	private BigDecimal totalCost = BigDecimal.ZERO;

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

	@NotNull(message = "{commerce.error.string.empty}", groups = { ValidationGroup.VGReceiveOrder.class })
	@Range(min = 1, max = 9999, message = "{commerce.error.nbr.range}", groups = { ValidationGroup.VGReceiveOrder.class })
	private BigDecimal delieveredQty;
	private LocalDateTime delieveredDate;

	private BigDecimal returnedQty;
	private Boolean isReturnAllowed=Boolean.FALSE;
	
	@NotNull(message = "{commerce.error.amount.empty}", groups = { ValidationGroup.VGReceiveOrder.class })
	@DecimalMin(value = "0.01", message = "{commerce.error.amt.range}", groups = { ValidationGroup.VGReceiveOrder.class })
	@DecimalMax(value = "9999999999.99", message = "{commerce.error.amt.range}", groups = { ValidationGroup.VGReceiveOrder.class })
	private BigDecimal actualUnitCost = BigDecimal.ZERO;
	private BigDecimal actualSuggestedPrice = BigDecimal.ZERO;
	private BigDecimal actualMaxRetailPrice = BigDecimal.ZERO;
	private BigDecimal actualCostAmount = BigDecimal.ZERO;
	private BigDecimal actualTotalCost = BigDecimal.ZERO;
	private BigDecimal actualDiscountAmount = BigDecimal.ZERO;
	private BigDecimal actualTaxAmount = BigDecimal.ZERO;

	private SupplierItemBean supplierItem;

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

	/**
	 * @return the orderedQty
	 */
	public BigDecimal getOrderedQty() {
		return orderedQty;
	}

	/**
	 * @param orderedQty
	 *            the orderedQty to set
	 */
	public void setOrderedQty(BigDecimal orderedQty) {
		this.orderedQty = orderedQty;
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
	 * @return the delieveredQty
	 */
	public BigDecimal getDelieveredQty() {
		return delieveredQty;
	}

	/**
	 * @param delieveredQty
	 *            the delieveredQty to set
	 */
	public void setDelieveredQty(BigDecimal delieveredQty) {
		this.delieveredQty = delieveredQty;
	}

	/**
	 * @return the delieveredDate
	 */
	public LocalDateTime getDelieveredDate() {
		return delieveredDate;
	}

	/**
	 * @param delieveredDate
	 *            the delieveredDate to set
	 */
	public void setDelieveredDate(LocalDateTime delieveredDate) {
		this.delieveredDate = delieveredDate;
	}

	/**
	 * @return the actualUnitCost
	 */
	public BigDecimal getActualUnitCost() {
		return actualUnitCost;
	}

	/**
	 * @param actualUnitCost
	 *            the actualUnitCost to set
	 */
	public void setActualUnitCost(BigDecimal actualUnitCost) {
		this.actualUnitCost = actualUnitCost;
	}

	/**
	 * @return the actualCostAmount
	 */
	public BigDecimal getActualCostAmount() {
		return actualCostAmount;
	}

	/**
	 * @param actualCostAmount
	 *            the actualCostAmount to set
	 */
	public void setActualCostAmount(BigDecimal actualCostAmount) {
		this.actualCostAmount = actualCostAmount;
	}

	/**
	 * @return the actualTotalCost
	 */
	public BigDecimal getActualTotalCost() {
		return actualTotalCost;
	}

	/**
	 * @param actualTotalCost
	 *            the actualTotalCost to set
	 */
	public void setActualTotalCost(BigDecimal actualTotalCost) {
		this.actualTotalCost = actualTotalCost;
	}

	/**
	 * @return the actualDiscountAmount
	 */
	public BigDecimal getActualDiscountAmount() {
		return actualDiscountAmount;
	}

	/**
	 * @param actualDiscountAmount
	 *            the actualDiscountAmount to set
	 */
	public void setActualDiscountAmount(BigDecimal actualDiscountAmount) {
		this.actualDiscountAmount = actualDiscountAmount;
	}

	/**
	 * @return the actualTaxAmount
	 */
	public BigDecimal getActualTaxAmount() {
		return actualTaxAmount;
	}

	/**
	 * @param actualTaxAmount
	 *            the actualTaxAmount to set
	 */
	public void setActualTaxAmount(BigDecimal actualTaxAmount) {
		this.actualTaxAmount = actualTaxAmount;
	}

	/**
	 * @return the supplierItem
	 */
	public SupplierItemBean getSupplierItem() {
		return supplierItem;
	}

	/**
	 * @param supplierItem
	 *            the supplierItem to set
	 */
	public void setSupplierItem(SupplierItemBean supplierItem) {
		this.supplierItem = supplierItem;
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
	 * @return the orderItemId
	 */
	public BigInteger getOrderItemId() {
		return orderItemId;
	}

	/**
	 * @param orderItemId
	 *            the orderItemId to set
	 */
	public void setOrderItemId(BigInteger orderItemId) {
		this.orderItemId = orderItemId;
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
	 * @return the actualSuggestedPrice
	 */
	public BigDecimal getActualSuggestedPrice() {
		return actualSuggestedPrice;
	}

	/**
	 * @param actualSuggestedPrice
	 *            the actualSuggestedPrice to set
	 */
	public void setActualSuggestedPrice(BigDecimal actualSuggestedPrice) {
		this.actualSuggestedPrice = actualSuggestedPrice;
	}

	/**
	 * @return the actualMaxRetailPrice
	 */
	public BigDecimal getActualMaxRetailPrice() {
		return actualMaxRetailPrice;
	}

	/**
	 * @param actualMaxRetailPrice
	 *            the actualMaxRetailPrice to set
	 */
	public void setActualMaxRetailPrice(BigDecimal actualMaxRetailPrice) {
		this.actualMaxRetailPrice = actualMaxRetailPrice;
	}

	/**
	 * @return the returnedQty
	 */
	public BigDecimal getReturnedQty() {
		return returnedQty;
	}

	/**
	 * @param returnedQty the returnedQty to set
	 */
	public void setReturnedQty(BigDecimal returnedQty) {
		this.returnedQty = returnedQty;
	}

	/**
	 * @return the isReturnAllowed
	 */
	public Boolean getIsReturnAllowed() {
		return isReturnAllowed;
	}

	/**
	 * @param isReturnAllowed the isReturnAllowed to set
	 */
	public void setIsReturnAllowed(Boolean isReturnAllowed) {
		this.isReturnAllowed = isReturnAllowed;
	}

}
