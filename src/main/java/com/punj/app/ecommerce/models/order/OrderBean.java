/**
 * 
 */
package com.punj.app.ecommerce.models.order;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
public class OrderBean {

	private BigInteger orderId;
	@NotNull
	private Integer supplierId;

	@Valid
	private List<OrderItemBean> orderItems;

	private String createdBy;
	private LocalDateTime createdDate;

	private BigDecimal estimatedCost = new BigDecimal("0.0");
	private BigDecimal discountAmount = new BigDecimal("0.0");
	private BigDecimal taxAmount = new BigDecimal("0.0");
	private BigDecimal totalAmount = new BigDecimal("0.0");
	private BigDecimal paidAmount = new BigDecimal("0.0");
	
	private String status;

	private Pager pager;

	public OrderBean() {
		orderItems = new ArrayList<OrderItemBean>();
		orderItems.add(new OrderItemBean());

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
	 * @return the supplierId
	 */
	public Integer getSupplierId() {
		return supplierId;
	}

	/**
	 * @param supplierId
	 *            the supplierId to set
	 */
	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	/**
	 * @return the orderItems
	 */
	public List<OrderItemBean> getOrderItems() {
		return orderItems;
	}

	/**
	 * @param orderItems
	 *            the orderItems to set
	 */
	public void setOrderItems(List<OrderItemBean> orderItems) {
		this.orderItems = orderItems;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the createdDate
	 */
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate
	 *            the createdDate to set
	 */
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the totalAmount
	 */
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount
	 *            the totalAmount to set
	 */
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @return the paidAmount
	 */
	public BigDecimal getPaidAmount() {
		return paidAmount;
	}

	/**
	 * @param paidAmount
	 *            the paidAmount to set
	 */
	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
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
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the pager
	 */
	public Pager getPager() {
		return pager;
	}

	/**
	 * @param pager
	 *            the pager to set
	 */
	public void setPager(Pager pager) {
		this.pager = pager;
	}

	/**
	 * @return the estimatedCost
	 */
	public BigDecimal getEstimatedCost() {
		return estimatedCost;
	}

	/**
	 * @param estimatedCost the estimatedCost to set
	 */
	public void setEstimatedCost(BigDecimal estimatedCost) {
		this.estimatedCost = estimatedCost;
	}


}