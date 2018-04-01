/**
 * 
 */
package com.punj.app.ecommerce.domains.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Indexed;

import com.punj.app.ecommerce.domains.order.ids.OrderItemId;

/**
 * @author admin
 *
 */
@Entity
@Indexed
@Table(name = "purchase_order_items")
public class OrderItem implements Serializable {

	private static final long serialVersionUID = 7080804777591754919L;

	@EmbeddedId
	@FieldBridge(impl = OrderItemFieldBridge.class)
	@DocumentId
	private OrderItemId orderItemId;

	@Column(name = "ordered_qty")
	private BigDecimal orderedQty;

	// Estimated Amount
	@Column(name = "unit_cost")
	private BigDecimal unitCost = BigDecimal.ZERO;
	@Column(name = "cost_amount")
	private BigDecimal costAmount = BigDecimal.ZERO;
	@Column(name = "tax_amount")
	private BigDecimal taxAmount = BigDecimal.ZERO;
	@Column(name = "total_cost")
	private BigDecimal totalCost = BigDecimal.ZERO;

	@Column(name = "actual_unit_cost")
	private BigDecimal actualUnitCost = BigDecimal.ZERO;

	@Column(name = "delievered_qty")
	private BigDecimal delieveredQty = BigDecimal.ZERO;
	@Column(name = "delievered_date")
	private LocalDateTime delieveredDate;

	// Actual Amounts
	@Column(name = "actual_cost_amount")
	private BigDecimal actualCostAmount = BigDecimal.ZERO;

	@Column(name = "actual_discount_amount")
	private BigDecimal actualDiscountAmount = BigDecimal.ZERO;
	@Column(name = "actual_tax_amount")
	private BigDecimal actualTaxAmount = BigDecimal.ZERO;
	@Column(name = "actual_total_cost")
	private BigDecimal actualTotalCost = BigDecimal.ZERO;

	/**
	 * @return the orderItemId
	 */
	public OrderItemId getOrderItemId() {
		return orderItemId;
	}

	/**
	 * @param orderItemId
	 *            the orderItemId to set
	 */
	public void setOrderItemId(OrderItemId orderItemId) {
		this.orderItemId = orderItemId;
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

}
