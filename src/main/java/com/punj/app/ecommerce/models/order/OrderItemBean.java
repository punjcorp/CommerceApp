/**
 * 
 */
package com.punj.app.ecommerce.models.order;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.punj.app.ecommerce.models.supplier.SupplierItemBean;

/**
 * @author admin
 *
 */
public class OrderItemBean {
	@NotNull(message = "{commerce.error.string.empty}")
	private BigInteger itemId;

	private BigInteger orderId;

	@NotNull(message = "{commerce.error.string.empty}")
	private BigDecimal orderedQty;

	@NotNull(message = "{commerce.error.string.empty}")
	private BigDecimal unitCost = BigDecimal.ZERO;
	private BigDecimal costAmount = BigDecimal.ZERO;
	private BigDecimal totalCost = BigDecimal.ZERO;

	private BigDecimal cgstRate = BigDecimal.ZERO;
	private BigDecimal sgstRate = BigDecimal.ZERO;
	private BigDecimal igstRate = BigDecimal.ZERO;
	private BigDecimal cgstTaxAmount = BigDecimal.ZERO;
	private BigDecimal sgstTaxAmount = BigDecimal.ZERO;
	private BigDecimal igstTaxAmount = BigDecimal.ZERO;
	private BigDecimal taxAmount = BigDecimal.ZERO;

	private BigDecimal delieveredQty;
	private LocalDateTime delieveredDate;

	private BigDecimal actualUnitCost = BigDecimal.ZERO;
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

}
