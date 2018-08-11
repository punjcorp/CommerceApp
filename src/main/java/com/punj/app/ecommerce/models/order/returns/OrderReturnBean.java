/**
 * 
 */
package com.punj.app.ecommerce.models.order.returns;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import com.punj.app.ecommerce.models.order.OrderBean;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
public class OrderReturnBean implements Serializable {

	private static final long serialVersionUID = -2850831538217022879L;

	private BigInteger orderReturnId;
	private BigInteger orderId;
	private OrderBean order;

	@Valid
	private List<OrderReturnItemBean> orderReturnItems;

	private List<OrderReturnItemTaxBean> orderReturnItemTaxes;

	private String createdBy;
	private LocalDateTime createdDate;

	private BigDecimal estimatedCost = BigDecimal.ZERO;
	private BigDecimal discountAmount = BigDecimal.ZERO;
	private BigDecimal taxAmount = BigDecimal.ZERO;
	private BigDecimal cgstTaxAmount = BigDecimal.ZERO;
	private BigDecimal sgstTaxAmount = BigDecimal.ZERO;
	private BigDecimal igstTaxAmount = BigDecimal.ZERO;
	private BigDecimal totalAmount = BigDecimal.ZERO;
	private BigDecimal refundAmount = BigDecimal.ZERO;

	private String status;
	private String displayStatus;
	private String comments;
	private Integer reasonCodeId;
	private String reasonCode;

	private Pager pager;

	/**
	 * @return the orderReturnId
	 */
	public BigInteger getOrderReturnId() {
		return orderReturnId;
	}

	/**
	 * @param orderReturnId
	 *            the orderReturnId to set
	 */
	public void setOrderReturnId(BigInteger orderReturnId) {
		this.orderReturnId = orderReturnId;
	}

	/**
	 * @return the order
	 */
	public OrderBean getOrder() {
		return order;
	}

	/**
	 * @param order
	 *            the order to set
	 */
	public void setOrder(OrderBean order) {
		this.order = order;
	}

	/**
	 * @return the orderReturnItems
	 */
	public List<OrderReturnItemBean> getOrderReturnItems() {
		return orderReturnItems;
	}

	/**
	 * @param orderReturnItems
	 *            the orderReturnItems to set
	 */
	public void setOrderReturnItems(List<OrderReturnItemBean> orderReturnItems) {
		this.orderReturnItems = orderReturnItems;
	}

	/**
	 * @return the orderReturnItemTaxes
	 */
	public List<OrderReturnItemTaxBean> getOrderReturnItemTaxes() {
		return orderReturnItemTaxes;
	}

	/**
	 * @param orderReturnItemTaxes
	 *            the orderReturnItemTaxes to set
	 */
	public void setOrderReturnItemTaxes(List<OrderReturnItemTaxBean> orderReturnItemTaxes) {
		this.orderReturnItemTaxes = orderReturnItemTaxes;
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
	 * @return the refundAmount
	 */
	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	/**
	 * @param refundAmount
	 *            the refundAmount to set
	 */
	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
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
	 * @return the displayStatus
	 */
	public String getDisplayStatus() {
		return displayStatus;
	}

	/**
	 * @param displayStatus
	 *            the displayStatus to set
	 */
	public void setDisplayStatus(String displayStatus) {
		this.displayStatus = displayStatus;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments
	 *            the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
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
	 * @return the reasonCode
	 */
	public String getReasonCode() {
		return reasonCode;
	}

	/**
	 * @param reasonCode
	 *            the reasonCode to set
	 */
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

}
