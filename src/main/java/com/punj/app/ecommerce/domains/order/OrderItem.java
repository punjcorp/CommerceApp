/**
 * 
 */
package com.punj.app.ecommerce.domains.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

/**
 * @author admin
 *
 */
@Entity
@Indexed
@Table(name = "purchase_order_items")
public class OrderItem implements Serializable {

	private static final long serialVersionUID = 7080804777591754919L;

	@Id
	@DocumentId
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "order_item_id", updatable = false, nullable = false)
	private BigInteger orderItemId;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "order_id")
	private Order order;

	@Field
	@Column(name = "item_id")
	private BigInteger itemId;

	@Field
	@Column(name = "item_name")
	private String itemDesc;

	@Field
	@Column(name = "hsn_no")
	private String hsnNo;

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

	@Column(name = "actual_suggested_price")
	private BigDecimal actualSuggestedPrice = BigDecimal.ZERO;
	@Column(name = "actual_max_retail_price")
	private BigDecimal actualMaxRetailPrice = BigDecimal.ZERO;

	@Column(name = "delievered_qty")
	private BigDecimal delieveredQty = BigDecimal.ZERO;
	@Column(name = "delievered_date")
	private LocalDateTime delieveredDate;

	@Column(name = "returned_qty")
	private BigDecimal returnedQty = BigDecimal.ZERO;

	// Actual Amounts
	@Column(name = "actual_cost_amount")
	private BigDecimal actualCostAmount = BigDecimal.ZERO;

	@Column(name = "actual_discount_amount")
	private BigDecimal actualDiscountAmount = BigDecimal.ZERO;
	@Column(name = "actual_tax_amount")
	private BigDecimal actualTaxAmount = BigDecimal.ZERO;
	@Column(name = "actual_total_cost")
	private BigDecimal actualTotalCost = BigDecimal.ZERO;

	@OneToMany(mappedBy = "orderItem", cascade = CascadeType.ALL)
	private List<OrderItemTax> orderItemTaxes;

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

	/**
	 * @return the orderItemTaxes
	 */
	public List<OrderItemTax> getOrderItemTaxes() {
		return orderItemTaxes;
	}

	/**
	 * @param orderItemTaxes
	 *            the orderItemTaxes to set
	 */
	public void setOrderItemTaxes(List<OrderItemTax> orderItemTaxes) {
		this.orderItemTaxes = orderItemTaxes;
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
	 * @return the order
	 */
	public Order getOrder() {
		return order;
	}

	/**
	 * @param order
	 *            the order to set
	 */
	public void setOrder(Order order) {
		this.order = order;
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
	 * @param returnedQty
	 *            the returnedQty to set
	 */
	public void setReturnedQty(BigDecimal returnedQty) {
		this.returnedQty = returnedQty;
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

}
