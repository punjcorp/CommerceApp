/**
 * 
 */
package com.punj.app.ecommerce.domains.finance;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author admin
 *
 */
@Entity
@Table(name = "daily_totals")
public class DailyTotals implements Serializable {

	private static final long serialVersionUID = -2269799462383262177L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "daily_totals_id", updatable = false, nullable = false)
	private BigInteger dailyTotalsId;

	@Column(name = "location_id")
	private Integer locationId;
	@Column(name = "register_id")
	private Integer registerId;

	@Column(name = "business_date")
	private LocalDateTime businessDate;

	@Column(name = "total_txn_count")
	private Integer totalTxnCount;
	@Column(name = "total_sales_count")
	private Integer totalSalesCount;
	@Column(name = "total_returns_count")
	private Integer totalReturnCount;

	@Column(name = "total_txn_amount")
	private BigDecimal totalTxnAmount;
	@Column(name = "total_sales_amount")
	private BigDecimal totalSalesamount;
	@Column(name = "total_returns_amount")
	private BigDecimal totalReturnsamount;

	/**
	 * @return the dailyTotalsId
	 */
	public BigInteger getDailyTotalsId() {
		return dailyTotalsId;
	}

	/**
	 * @param dailyTotalsId
	 *            the dailyTotalsId to set
	 */
	public void setDailyTotalsId(BigInteger dailyTotalsId) {
		this.dailyTotalsId = dailyTotalsId;
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
	 * @return the registerId
	 */
	public Integer getRegisterId() {
		return registerId;
	}

	/**
	 * @param registerId
	 *            the registerId to set
	 */
	public void setRegisterId(Integer registerId) {
		this.registerId = registerId;
	}

	/**
	 * @return the businessDate
	 */
	public LocalDateTime getBusinessDate() {
		return businessDate;
	}

	/**
	 * @param businessDate
	 *            the businessDate to set
	 */
	public void setBusinessDate(LocalDateTime businessDate) {
		this.businessDate = businessDate;
	}

	/**
	 * @return the totalTxnCount
	 */
	public Integer getTotalTxnCount() {
		return totalTxnCount;
	}

	/**
	 * @param totalTxnCount
	 *            the totalTxnCount to set
	 */
	public void setTotalTxnCount(Integer totalTxnCount) {
		this.totalTxnCount = totalTxnCount;
	}

	/**
	 * @return the totalSalesCount
	 */
	public Integer getTotalSalesCount() {
		return totalSalesCount;
	}

	/**
	 * @param totalSalesCount
	 *            the totalSalesCount to set
	 */
	public void setTotalSalesCount(Integer totalSalesCount) {
		this.totalSalesCount = totalSalesCount;
	}

	/**
	 * @return the totalReturnCount
	 */
	public Integer getTotalReturnCount() {
		return totalReturnCount;
	}

	/**
	 * @param totalReturnCount
	 *            the totalReturnCount to set
	 */
	public void setTotalReturnCount(Integer totalReturnCount) {
		this.totalReturnCount = totalReturnCount;
	}

	/**
	 * @return the totalTxnAmount
	 */
	public BigDecimal getTotalTxnAmount() {
		return totalTxnAmount;
	}

	/**
	 * @param totalTxnAmount
	 *            the totalTxnAmount to set
	 */
	public void setTotalTxnAmount(BigDecimal totalTxnAmount) {
		this.totalTxnAmount = totalTxnAmount;
	}

	/**
	 * @return the totalSalesamount
	 */
	public BigDecimal getTotalSalesamount() {
		return totalSalesamount;
	}

	/**
	 * @param totalSalesamount
	 *            the totalSalesamount to set
	 */
	public void setTotalSalesamount(BigDecimal totalSalesamount) {
		this.totalSalesamount = totalSalesamount;
	}

	/**
	 * @return the totalReturnsamount
	 */
	public BigDecimal getTotalReturnsamount() {
		return totalReturnsamount;
	}

	/**
	 * @param totalReturnsamount
	 *            the totalReturnsamount to set
	 */
	public void setTotalReturnsamount(BigDecimal totalReturnsamount) {
		this.totalReturnsamount = totalReturnsamount;
	}

}
