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
import org.hibernate.search.annotations.IndexedEmbedded;

import com.punj.app.ecommerce.domains.common.Location;
import com.punj.app.ecommerce.domains.supplier.Supplier;

/**
 * @author admin
 *
 */

@Indexed
@Entity
@Table(name = "purchase_order")
public class Order implements Serializable {

	private static final long serialVersionUID = 4660114320145237621L;

	@Id
	@DocumentId
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "order_id", updatable = false, nullable = false)
	private BigInteger orderId;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "location_id")
	@IndexedEmbedded
	private Location location;

	@Field
	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "created_date")
	private LocalDateTime createdDate;

	@Column(name = "modified_by")
	private String modifiedBy;
	@Column(name = "modified_date")
	private LocalDateTime modifiedDate;

	@Column(name = "sub_total_cost")
	private BigDecimal estimatedCost = new BigDecimal("0.0");

	@Column(name = "discount_amount")
	private BigDecimal discountAmount = new BigDecimal("0.0");
	@Column(name = "tax_amount")
	private BigDecimal taxAmount = new BigDecimal("0.0");
	@Column(name = "total_amount")
	private BigDecimal totalAmount = new BigDecimal("0.0");
	@Column(name = "paid_amount")
	private BigDecimal paidAmount = new BigDecimal("0.0");

	@Column(name = "actual_sub_total_cost")
	private BigDecimal actualSubTotalCost = new BigDecimal("0.0");
	@Column(name = "actual_tax_amount")
	private BigDecimal actualTaxAmount = new BigDecimal("0.0");
	@Column(name = "actual_total_amount")
	private BigDecimal actualTotalAmount = new BigDecimal("0.0");

	@Field
	private String status;
	@Field
	private String comments;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "supplier_id")
	@IndexedEmbedded(depth = 1)
	private Supplier supplier;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	@IndexedEmbedded
	private List<OrderItem> orderItems;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderBill> orderBills;

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
	 * @return the orderItems
	 */
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	/**
	 * @param orderItems
	 *            the orderItems to set
	 */
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	/**
	 * @return the supplier
	 */
	public Supplier getSupplier() {
		return supplier;
	}

	/**
	 * @param supplier
	 *            the supplier to set
	 */
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
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
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
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
	 * @return the actualSubTotalCost
	 */
	public BigDecimal getActualSubTotalCost() {
		return actualSubTotalCost;
	}

	/**
	 * @param actualSubTotalCost
	 *            the actualSubTotalCost to set
	 */
	public void setActualSubTotalCost(BigDecimal actualSubTotalCost) {
		this.actualSubTotalCost = actualSubTotalCost;
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
	 * @return the actualTotalAmount
	 */
	public BigDecimal getActualTotalAmount() {
		return actualTotalAmount;
	}

	/**
	 * @param actualTotalAmount
	 *            the actualTotalAmount to set
	 */
	public void setActualTotalAmount(BigDecimal actualTotalAmount) {
		this.actualTotalAmount = actualTotalAmount;
	}

	/**
	 * @return the orderBills
	 */
	public List<OrderBill> getOrderBills() {
		return orderBills;
	}

	/**
	 * @param orderBills
	 *            the orderBills to set
	 */
	public void setOrderBills(List<OrderBill> orderBills) {
		this.orderBills = orderBills;
	}

}
