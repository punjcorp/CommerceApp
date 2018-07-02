/**
 * 
 */
package com.punj.app.ecommerce.domains.reports.dashboard;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.punj.app.ecommerce.domains.reports.dashboard.ids.DashboardReportId;

/**
 * @author admin
 *
 */
@Entity
@Table(name = "v_location_sales_data")
public class DashboardReport implements Serializable {

	private static final long serialVersionUID = 8654691542057729678L;

	@EmbeddedId
	private DashboardReportId dashboardReportId;

	@Column(name = "revenue")
	private BigDecimal revenueAmount;
	// private BigDecimal grossProfitAmount;
	@Column(name = "discount_amount")
	private BigDecimal discountAmount;
	@Column(name = "discount_percent")
	private BigDecimal discountPercentage;
	@Column(name = "basket_amount")
	private BigDecimal basketAmount;

	@Column(name = "txn_count")
	private Integer txnCount;
	@Column(name = "customer_count")
	private Integer customerCount;
	/*
	 * @Column(name = "sale_txn_count") private Integer saleTxnCount;
	 * 
	 * @Column(name = "return_txn_count") private Integer returnTxnCount;
	 */
	@Column(name = "basket_size")
	private BigDecimal basketSize;

	/**
	 * @return the dashboardReportId
	 */
	public DashboardReportId getDashboardReportId() {
		return dashboardReportId;
	}

	/**
	 * @param dashboardReportId
	 *            the dashboardReportId to set
	 */
	public void setDashboardReportId(DashboardReportId dashboardReportId) {
		this.dashboardReportId = dashboardReportId;
	}

	/**
	 * @return the revenueAmount
	 */
	public BigDecimal getRevenueAmount() {
		return revenueAmount;
	}

	/**
	 * @param revenueAmount
	 *            the revenueAmount to set
	 */
	public void setRevenueAmount(BigDecimal revenueAmount) {
		this.revenueAmount = revenueAmount;
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
	 * @return the discountPercentage
	 */
	public BigDecimal getDiscountPercentage() {
		return discountPercentage;
	}

	/**
	 * @param discountPercentage
	 *            the discountPercentage to set
	 */
	public void setDiscountPercentage(BigDecimal discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	/**
	 * @return the basketAmount
	 */
	public BigDecimal getBasketAmount() {
		return basketAmount;
	}

	/**
	 * @param basketAmount
	 *            the basketAmount to set
	 */
	public void setBasketAmount(BigDecimal basketAmount) {
		this.basketAmount = basketAmount;
	}

	/**
	 * @return the txnCount
	 */
	public Integer getTxnCount() {
		return txnCount;
	}

	/**
	 * @param txnCount
	 *            the txnCount to set
	 */
	public void setTxnCount(Integer txnCount) {
		this.txnCount = txnCount;
	}

	/**
	 * @return the customerCount
	 */
	public Integer getCustomerCount() {
		return customerCount;
	}

	/**
	 * @param customerCount
	 *            the customerCount to set
	 */
	public void setCustomerCount(Integer customerCount) {
		this.customerCount = customerCount;
	}

	/**
	 * @return the basketSize
	 */
	public BigDecimal getBasketSize() {
		return basketSize;
	}

	/**
	 * @param basketSize
	 *            the basketSize to set
	 */
	public void setBasketSize(BigDecimal basketSize) {
		this.basketSize = basketSize;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
