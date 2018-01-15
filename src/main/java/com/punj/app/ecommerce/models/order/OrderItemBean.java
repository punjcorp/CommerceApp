/**
 * 
 */
package com.punj.app.ecommerce.models.order;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

import javax.validation.constraints.Digits;
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
	private Integer locationId;

	@NotNull
	private Integer orderedQty;

	@Digits(integer = 8, fraction = 2)
	private BigDecimal costAmount = new BigDecimal("0.0");
	private BigDecimal totalCost = new BigDecimal("0.0");

	private Integer delieveredQty;
	private LocalDateTime delieveredDate;

	private BigDecimal costActualAmount = new BigDecimal("0.0");
	private BigDecimal totalActualCost = new BigDecimal("0.0");
	private BigDecimal discountAmount = new BigDecimal("0.0");
	private BigDecimal taxAmount = new BigDecimal("0.0");
	private BigDecimal totalActualAmount = new BigDecimal("0.0");

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
	 * @return the orderedQty
	 */
	public Integer getOrderedQty() {
		return orderedQty;
	}

	/**
	 * @param orderedQty
	 *            the orderedQty to set
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
	 * @return the delieveredQty
	 */
	public Integer getDelieveredQty() {
		return delieveredQty;
	}

	/**
	 * @param delieveredQty
	 *            the delieveredQty to set
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
	 * @param delieveredDate
	 *            the delieveredDate to set
	 */
	public void setDelieveredDate(LocalDateTime delieveredDate) {
		this.delieveredDate = delieveredDate;
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
	 * @return the totalActualAmount
	 */
	public BigDecimal getTotalActualAmount() {
		return totalActualAmount;
	}

	/**
	 * @param totalActualAmount
	 *            the totalActualAmount to set
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
	 * @param supplierItem
	 *            the supplierItem to set
	 */
	public void setSupplierItem(SupplierItemBean supplierItem) {
		this.supplierItem = supplierItem;
	}

	/**
	 * @return the costActualAmount
	 */
	public BigDecimal getCostActualAmount() {
		return costActualAmount;
	}

	/**
	 * @param costActualAmount
	 *            the costActualAmount to set
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
	 * @param totalActualCost
	 *            the totalActualCost to set
	 */
	public void setTotalActualCost(BigDecimal totalActualCost) {
		this.totalActualCost = totalActualCost;
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

}
