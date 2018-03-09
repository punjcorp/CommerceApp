/**
 * 
 */
package com.punj.app.ecommerce.models.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author admin
 *
 */
public class TransactionHeader {

	private Integer locationId;
	private Integer registerId;
	@JsonFormat(pattern = "dd-MMM-yy HH:mm:ss")
	private LocalDateTime businessDate;
	@NotNull(message = "{commerce.error.item.empty}")
	private Integer txnNo;

	private String txnType;
	private String status;

	private LocalDateTime startTime;
	private LocalDateTime endTime;

	private Boolean offlineFlag;
	private Boolean postVoidFlag;

	private String cancelReason;
	private String sessionId;

	private String createdBy;
	private LocalDateTime createdDate;
	private String modifiedBy;
	private LocalDateTime modifiedDate;

	private BigDecimal netTotalAmount;
	private BigDecimal totalDiscountAmt;
	private BigDecimal totalSGSTTaxAmt;
	private BigDecimal totalCGSTTaxAmt;
	private BigDecimal totalIGSTTaxAmt;
	private BigDecimal totalTaxAmt;
	private BigDecimal totalDueAmt;

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
	 * @return the txnNo
	 */
	public Integer getTxnNo() {
		return txnNo;
	}

	/**
	 * @param txnNo
	 *            the txnNo to set
	 */
	public void setTxnNo(Integer txnNo) {
		this.txnNo = txnNo;
	}

	/**
	 * @return the txnType
	 */
	public String getTxnType() {
		return txnType;
	}

	/**
	 * @param txnType
	 *            the txnType to set
	 */
	public void setTxnType(String txnType) {
		this.txnType = txnType;
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
	 * @return the startTime
	 */
	public LocalDateTime getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public LocalDateTime getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the offlineFlag
	 */
	public Boolean getOfflineFlag() {
		return offlineFlag;
	}

	/**
	 * @param offlineFlag
	 *            the offlineFlag to set
	 */
	public void setOfflineFlag(Boolean offlineFlag) {
		this.offlineFlag = offlineFlag;
	}

	/**
	 * @return the postVoidFlag
	 */
	public Boolean getPostVoidFlag() {
		return postVoidFlag;
	}

	/**
	 * @param postVoidFlag
	 *            the postVoidFlag to set
	 */
	public void setPostVoidFlag(Boolean postVoidFlag) {
		this.postVoidFlag = postVoidFlag;
	}

	/**
	 * @return the cancelReason
	 */
	public String getCancelReason() {
		return cancelReason;
	}

	/**
	 * @param cancelReason
	 *            the cancelReason to set
	 */
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	/**
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * @param sessionId
	 *            the sessionId to set
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
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
	 * @return the netTotalAmount
	 */
	public BigDecimal getNetTotalAmount() {
		return netTotalAmount;
	}

	/**
	 * @param netTotalAmount
	 *            the netTotalAmount to set
	 */
	public void setNetTotalAmount(BigDecimal netTotalAmount) {
		this.netTotalAmount = netTotalAmount;
	}

	/**
	 * @return the totalDiscountAmt
	 */
	public BigDecimal getTotalDiscountAmt() {
		return totalDiscountAmt;
	}

	/**
	 * @param totalDiscountAmt
	 *            the totalDiscountAmt to set
	 */
	public void setTotalDiscountAmt(BigDecimal totalDiscountAmt) {
		this.totalDiscountAmt = totalDiscountAmt;
	}

	/**
	 * @return the totalSGSTTaxAmt
	 */
	public BigDecimal getTotalSGSTTaxAmt() {
		return totalSGSTTaxAmt;
	}

	/**
	 * @param totalSGSTTaxAmt
	 *            the totalSGSTTaxAmt to set
	 */
	public void setTotalSGSTTaxAmt(BigDecimal totalSGSTTaxAmt) {
		this.totalSGSTTaxAmt = totalSGSTTaxAmt;
	}

	/**
	 * @return the totalCGSTTaxAmt
	 */
	public BigDecimal getTotalCGSTTaxAmt() {
		return totalCGSTTaxAmt;
	}

	/**
	 * @param totalCGSTTaxAmt
	 *            the totalCGSTTaxAmt to set
	 */
	public void setTotalCGSTTaxAmt(BigDecimal totalCGSTTaxAmt) {
		this.totalCGSTTaxAmt = totalCGSTTaxAmt;
	}

	/**
	 * @return the totalIGSTTaxAmt
	 */
	public BigDecimal getTotalIGSTTaxAmt() {
		return totalIGSTTaxAmt;
	}

	/**
	 * @param totalIGSTTaxAmt
	 *            the totalIGSTTaxAmt to set
	 */
	public void setTotalIGSTTaxAmt(BigDecimal totalIGSTTaxAmt) {
		this.totalIGSTTaxAmt = totalIGSTTaxAmt;
	}

	/**
	 * @return the totalTaxAmt
	 */
	public BigDecimal getTotalTaxAmt() {
		return totalTaxAmt;
	}

	/**
	 * @param totalTaxAmt
	 *            the totalTaxAmt to set
	 */
	public void setTotalTaxAmt(BigDecimal totalTaxAmt) {
		this.totalTaxAmt = totalTaxAmt;
	}

	/**
	 * @return the totalDueAmt
	 */
	public BigDecimal getTotalDueAmt() {
		return totalDueAmt;
	}

	/**
	 * @param totalDueAmt
	 *            the totalDueAmt to set
	 */
	public void setTotalDueAmt(BigDecimal totalDueAmt) {
		this.totalDueAmt = totalDueAmt;
	}

}
