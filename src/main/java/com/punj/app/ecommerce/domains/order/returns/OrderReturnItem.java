/**
 * 
 */
package com.punj.app.ecommerce.domains.order.returns;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

/**
 * @author admin
 *
 */
@Entity
@Indexed
@Table(name = "po_return_items")
public class OrderReturnItem implements Serializable {

	private static final long serialVersionUID = -8729421999627853005L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "order_return_item_id", updatable = false, nullable = false)
	private BigInteger orderReturnItemId;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "order_return_id")
	private OrderReturn orderReturn;

	@Field
	@Column(name = "item_id")
	private BigInteger itemId;

	@Field
	@Column(name = "item_name")
	private String itemDesc;

	@Column(name = "reason_code_id")
	private Integer reasonCodeId;

	@Column(name = "return_qty")
	private BigDecimal returnQty;

	@Column(name = "actual_unit_cost")
	private BigDecimal actualUnitCost = BigDecimal.ZERO;

	// Actual Amounts
	@Column(name = "actual_cost_amount")
	private BigDecimal actualCostAmount = BigDecimal.ZERO;

	@Column(name = "actual_discount_amount")
	private BigDecimal actualDiscountAmount = BigDecimal.ZERO;
	@Column(name = "actual_tax_amount")
	private BigDecimal actualTaxAmount = BigDecimal.ZERO;
	@Column(name = "actual_total_cost")
	private BigDecimal actualTotalCost = BigDecimal.ZERO;

	@OneToMany(mappedBy = "orderReturnItem", cascade = CascadeType.ALL)
	private List<OrderReturnItemTax> orderReturnItemTaxes;

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
	 * @return the orderReturn
	 */
	public OrderReturn getOrderReturn() {
		return orderReturn;
	}

	/**
	 * @param orderReturn
	 *            the orderReturn to set
	 */
	public void setOrderReturn(OrderReturn orderReturn) {
		this.orderReturn = orderReturn;
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
	 * @return the reasonCodeId
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
	 * @return the returnQty
	 */
	public BigDecimal getReturnQty() {
		return returnQty;
	}

	/**
	 * @param returnQty
	 *            the returnQty to set
	 */
	public void setReturnQty(BigDecimal returnQty) {
		this.returnQty = returnQty;
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
	 * @return the orderReturnItemTaxes
	 */
	public List<OrderReturnItemTax> getOrderReturnItemTaxes() {
		return orderReturnItemTaxes;
	}

	/**
	 * @param orderReturnItemTaxes
	 *            the orderReturnItemTaxes to set
	 */
	public void setOrderReturnItemTaxes(List<OrderReturnItemTax> orderReturnItemTaxes) {
		this.orderReturnItemTaxes = orderReturnItemTaxes;
	}

}
