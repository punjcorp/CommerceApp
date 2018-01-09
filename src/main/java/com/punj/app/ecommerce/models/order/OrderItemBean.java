/**
 * 
 */
package com.punj.app.ecommerce.models.order;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

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
	private BigDecimal costAmount;
	private BigDecimal totalCost;
	private Integer delieveredQty;
	private LocalDateTime delieveredDate;

	private BigDecimal discountAmount;
	private BigDecimal totalDiscountAmount;

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
	 * @return the totalDiscountAmount
	 */
	public BigDecimal getTotalDiscountAmount() {
		return totalDiscountAmount;
	}

	/**
	 * @param totalDiscountAmount
	 *            the totalDiscountAmount to set
	 */
	public void setTotalDiscountAmount(BigDecimal totalDiscountAmount) {
		this.totalDiscountAmount = totalDiscountAmount;
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
