/**
 * 
 */
package com.punj.app.ecommerce.domains.order.returns;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import com.punj.app.ecommerce.domains.order.Order;

/**
 * @author admin
 *
 */

@Indexed
@Entity
@Table(name = "po_return")
public class OrderReturn implements Serializable {

	private static final long serialVersionUID = -4082259643918443060L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "order_return_id", updatable = false, nullable = false)
	private BigInteger orderReturnId;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "order_id")
	private Order order;

	@Field
	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "created_date")
	private LocalDateTime createdDate;

	@Column(name = "modified_by")
	private String modifiedBy;
	@Column(name = "modified_date")
	private LocalDateTime modifiedDate;

	@Column(name = "sub_total_amount")
	private BigDecimal subTotalAmount = new BigDecimal("0.0");
	@Column(name = "discount_amount")
	private BigDecimal discountAmount = new BigDecimal("0.0");
	@Column(name = "tax_amount")
	private BigDecimal taxAmount = new BigDecimal("0.0");
	@Column(name = "total_amount")
	private BigDecimal totalAmount = new BigDecimal("0.0");
	@Column(name = "received_refund_amount")
	private BigDecimal refundAmount = new BigDecimal("0.0");

	@Field
	private String status;
	@Field
	private String comments;
	@Column(name = "reason_code_id")
	private Integer reasonCodeId;

	@OneToMany(mappedBy = "orderReturn", cascade = CascadeType.ALL)
	@IndexedEmbedded(includeEmbeddedObjectId = true)
	private List<OrderReturnItem> orderReturnItems;

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
	 * @return the modifiedBy
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * @param modifiedBy
	 *            the modifiedBy to set
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * @return the modifiedDate
	 */
	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	/**
	 * @param modifiedDate
	 *            the modifiedDate to set
	 */
	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/**
	 * @return the subTotalAmount
	 */
	public BigDecimal getSubTotalAmount() {
		return subTotalAmount;
	}

	/**
	 * @param subTotalAmount
	 *            the subTotalAmount to set
	 */
	public void setSubTotalAmount(BigDecimal subTotalAmount) {
		this.subTotalAmount = subTotalAmount;
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
	 * @return the orderReturnItems
	 */
	public List<OrderReturnItem> getOrderReturnItems() {
		return orderReturnItems;
	}

	/**
	 * @param orderReturnItems
	 *            the orderReturnItems to set
	 */
	public void setOrderReturnItems(List<OrderReturnItem> orderReturnItems) {
		this.orderReturnItems = orderReturnItems;
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

}
