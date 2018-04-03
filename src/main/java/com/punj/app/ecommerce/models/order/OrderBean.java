/**
 * 
 */
package com.punj.app.ecommerce.models.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.punj.app.ecommerce.models.supplier.SupplierBean;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
public class OrderBean implements Serializable {

	private static final long serialVersionUID = -4016931635705372146L;

	private BigInteger orderId;
	@NotNull(message = "{commerce.error.string.empty}")
	private Integer supplierId;
	@NotNull(message = "{commerce.error.string.empty}")
	private Integer locationId;

	private SupplierBean supplier;

	@Valid
	private List<OrderItemBean> orderItems;

	private List<OrderItemTaxBean> orderItemTaxes;

	private String createdBy;
	private LocalDateTime createdDate;

	private BigDecimal estimatedCost = BigDecimal.ZERO;
	private BigDecimal discountAmount = BigDecimal.ZERO;
	private BigDecimal taxAmount = BigDecimal.ZERO;
	private BigDecimal cgstTaxAmount = BigDecimal.ZERO;
	private BigDecimal sgstTaxAmount = BigDecimal.ZERO;
	private BigDecimal igstTaxAmount = BigDecimal.ZERO;
	private BigDecimal totalAmount = BigDecimal.ZERO;
	private BigDecimal paidAmount = BigDecimal.ZERO;

	private String status;

	private Pager pager;

	public OrderBean() {
		this.orderItems = new ArrayList<>();
		this.orderItems.add(new OrderItemBean());

	}

	public OrderBean(OrderBean newOrderBean) {

		this.orderItems = newOrderBean.getOrderItems();
		this.orderId = newOrderBean.getOrderId();
		this.supplierId = newOrderBean.getSupplierId();
		this.locationId = newOrderBean.getLocationId();
		this.supplier = newOrderBean.getSupplier();
		this.createdBy = newOrderBean.getCreatedBy();
		this.createdDate = newOrderBean.getCreatedDate();
		this.estimatedCost = newOrderBean.getEstimatedCost();
		this.discountAmount = newOrderBean.getDiscountAmount();
		this.taxAmount = newOrderBean.getTaxAmount();
		this.cgstTaxAmount = newOrderBean.getCgstTaxAmount();
		this.sgstTaxAmount = newOrderBean.getSgstTaxAmount();
		this.igstTaxAmount = newOrderBean.getIgstTaxAmount();
		this.totalAmount = newOrderBean.getTotalAmount();
		this.paidAmount = newOrderBean.getPaidAmount();

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
	 * @return the supplier
	 */
	public SupplierBean getSupplier() {
		return supplier;
	}

	/**
	 * @param supplier
	 *            the supplier to set
	 */
	public void setSupplier(SupplierBean supplier) {
		this.supplier = supplier;
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
	 * @return the estimatedCost
	 */
	public BigDecimal getEstimatedCost() {
		return estimatedCost;
	}

	/**
	 * @param estimatedCost
	 *            the estimatedCost to set
	 */
	public void setEstimatedCost(BigDecimal estimatedCost) {
		this.estimatedCost = estimatedCost;
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
	 * @return the orderItemTaxes
	 */
	public List<OrderItemTaxBean> getOrderItemTaxes() {
		return orderItemTaxes;
	}

	/**
	 * @param orderItemTaxes
	 *            the orderItemTaxes to set
	 */
	public void setOrderItemTaxes(List<OrderItemTaxBean> orderItemTaxes) {
		this.orderItemTaxes = orderItemTaxes;
	}

}
