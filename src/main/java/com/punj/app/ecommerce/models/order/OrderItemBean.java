/**
 * 
 */
package com.punj.app.ecommerce.models.order;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

import javax.money.MonetaryAmount;
import javax.validation.constraints.NotNull;

import com.punj.app.ecommerce.models.supplier.SupplierItemBean;

/**
 * @author admin
 *
 */
public class OrderItemBean {
	@NotNull
	private BigInteger itemId;

	private BigInteger orderId;

	@NotNull
	private Integer orderedQty;

	private BigDecimal costAmount = BigDecimal.ZERO;
	private BigDecimal totalCost = BigDecimal.ZERO;

	private Integer delieveredQty;
	private LocalDateTime delieveredDate;

	private BigDecimal costActualAmount = BigDecimal.ZERO;
	private BigDecimal totalActualCost = BigDecimal.ZERO;
	private BigDecimal discountAmount = BigDecimal.ZERO;
	private BigDecimal taxAmount = BigDecimal.ZERO;
	private BigDecimal totalActualAmount = BigDecimal.ZERO;

	private SupplierItemBean supplierItem;

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

	/**
	 * @return the orderId
	 */
	public BigInteger getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(BigInteger orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the orderedQty
	 */
	public Integer getOrderedQty() {
		return orderedQty;
	}

	/**
	 * @param orderedQty the orderedQty to set
	 */
	public void setOrderedQty(Integer orderedQty) {
		this.orderedQty = orderedQty;
	}

	/**
	 * @return the costAmount
	 */
	public BigDecimal getCostAmount() {
		return costAmount;
	}

	/**
	 * @param costAmount the costAmount to set
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
	 * @param totalCost the totalCost to set
	 */
	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}

	/**
	 * @return the delieveredQty
	 */
	public Integer getDelieveredQty() {
		return delieveredQty;
	}

	/**
	 * @param delieveredQty the delieveredQty to set
	 */
	public void setDelieveredQty(Integer delieveredQty) {
		this.delieveredQty = delieveredQty;
	}

	/**
	 * @return the delieveredDate
	 */
	public LocalDateTime getDelieveredDate() {
		return delieveredDate;
	}

	/**
	 * @param delieveredDate the delieveredDate to set
	 */
	public void setDelieveredDate(LocalDateTime delieveredDate) {
		this.delieveredDate = delieveredDate;
	}

	/**
	 * @return the costActualAmount
	 */
	public BigDecimal getCostActualAmount() {
		return costActualAmount;
	}

	/**
	 * @param costActualAmount the costActualAmount to set
	 */
	public void setCostActualAmount(BigDecimal costActualAmount) {
		this.costActualAmount = costActualAmount;
	}

	/**
	 * @return the totalActualCost
	 */
	public BigDecimal getTotalActualCost() {
		return totalActualCost;
	}

	/**
	 * @param totalActualCost the totalActualCost to set
	 */
	public void setTotalActualCost(BigDecimal totalActualCost) {
		this.totalActualCost = totalActualCost;
	}

	/**
	 * @return the discountAmount
	 */
	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	/**
	 * @param discountAmount the discountAmount to set
	 */
	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

	/**
	 * @return the taxAmount
	 */
	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	/**
	 * @param taxAmount the taxAmount to set
	 */
	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

	/**
	 * @return the totalActualAmount
	 */
	public BigDecimal getTotalActualAmount() {
		return totalActualAmount;
	}

	/**
	 * @param totalActualAmount the totalActualAmount to set
	 */
	public void setTotalActualAmount(BigDecimal totalActualAmount) {
		this.totalActualAmount = totalActualAmount;
	}

	/**
	 * @return the supplierItem
	 */
	public SupplierItemBean getSupplierItem() {
		return supplierItem;
	}

	/**
	 * @param supplierItem the supplierItem to set
	 */
	public void setSupplierItem(SupplierItemBean supplierItem) {
		this.supplierItem = supplierItem;
	}

}
