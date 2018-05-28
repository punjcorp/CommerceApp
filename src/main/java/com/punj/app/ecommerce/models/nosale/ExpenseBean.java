/**
 * 
 */
package com.punj.app.ecommerce.models.nosale;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.punj.app.ecommerce.models.common.LocationBean;

/**
 * @author admin
 *
 */
public class ExpenseBean {

	private LocationBean locationBean;

	private Integer locationId;
	private Integer registerId;
	@JsonFormat(pattern = "dd-MMM-yy HH:mm:ss")
	private LocalDateTime businessDate;
	@NotNull(message = "{commerce.error.item.empty}")
	private Integer txnNo;

	private String locationName;
	private String registerName;

	private Integer expenseId;
	private String expenseType;
	private BigDecimal expenseAmount;

	private String remarks;
	private String createdBy;
	private String printedBy;
	private LocalDateTime createdDate;

	@JsonFormat(pattern = "dd-MMM-yy HH:mm:ss")
	private LocalDateTime startTime;
	@JsonFormat(pattern = "dd-MMM-yy HH:mm:ss")
	private LocalDateTime endTime;

	private String uniqueTxnNo;
	private byte pdfbytes[];
	private String defaultTender;

	// Temporary

	private Integer tenderId;
	private String tenderName;

	private BigDecimal tenderAmount;

	private String toPayeeName;
	private String toPayeePhone;
	private String toBankName;
	private String toBankBranch;
	private String toAccountNo;
	private String tenderDetails;

	private List<ExpenseTenderBean> expenseTenders;

	/**
	 * @return the expenseType
	 */
	public String getExpenseType() {
		return expenseType;
	}

	/**
	 * @param expenseType
	 *            the expenseType to set
	 */
	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType;
	}

	/**
	 * @return the expenseAmount
	 */
	public BigDecimal getExpenseAmount() {
		return expenseAmount;
	}

	/**
	 * @param expenseAmount
	 *            the expenseAmount to set
	 */
	public void setExpenseAmount(BigDecimal expenseAmount) {
		this.expenseAmount = expenseAmount;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	 * @return the expenseTenders
	 */
	public List<ExpenseTenderBean> getExpenseTenders() {
		return expenseTenders;
	}

	/**
	 * @param expenseTenders
	 *            the expenseTenders to set
	 */
	public void setExpenseTenders(List<ExpenseTenderBean> expenseTenders) {
		this.expenseTenders = expenseTenders;
	}

	/**
	 * @return the defaultTender
	 */
	public String getDefaultTender() {
		return defaultTender;
	}

	/**
	 * @param defaultTender
	 *            the defaultTender to set
	 */
	public void setDefaultTender(String defaultTender) {
		this.defaultTender = defaultTender;
	}

	/**
	 * @return the tenderId
	 */
	public Integer getTenderId() {
		return tenderId;
	}

	/**
	 * @param tenderId
	 *            the tenderId to set
	 */
	public void setTenderId(Integer tenderId) {
		this.tenderId = tenderId;
	}

	/**
	 * @return the tenderName
	 */
	public String getTenderName() {
		return tenderName;
	}

	/**
	 * @param tenderName
	 *            the tenderName to set
	 */
	public void setTenderName(String tenderName) {
		this.tenderName = tenderName;
	}

	/**
	 * @return the tenderAmount
	 */
	public BigDecimal getTenderAmount() {
		return tenderAmount;
	}

	/**
	 * @param tenderAmount
	 *            the tenderAmount to set
	 */
	public void setTenderAmount(BigDecimal tenderAmount) {
		this.tenderAmount = tenderAmount;
	}

	/**
	 * @return the toPayeeName
	 */
	public String getToPayeeName() {
		return toPayeeName;
	}

	/**
	 * @param toPayeeName
	 *            the toPayeeName to set
	 */
	public void setToPayeeName(String toPayeeName) {
		this.toPayeeName = toPayeeName;
	}

	/**
	 * @return the toPayeePhone
	 */
	public String getToPayeePhone() {
		return toPayeePhone;
	}

	/**
	 * @param toPayeePhone
	 *            the toPayeePhone to set
	 */
	public void setToPayeePhone(String toPayeePhone) {
		this.toPayeePhone = toPayeePhone;
	}

	/**
	 * @return the toBankName
	 */
	public String getToBankName() {
		return toBankName;
	}

	/**
	 * @param toBankName
	 *            the toBankName to set
	 */
	public void setToBankName(String toBankName) {
		this.toBankName = toBankName;
	}

	/**
	 * @return the toBankBranch
	 */
	public String getToBankBranch() {
		return toBankBranch;
	}

	/**
	 * @param toBankBranch
	 *            the toBankBranch to set
	 */
	public void setToBankBranch(String toBankBranch) {
		this.toBankBranch = toBankBranch;
	}

	/**
	 * @return the toAccountNo
	 */
	public String getToAccountNo() {
		return toAccountNo;
	}

	/**
	 * @param toAccountNo
	 *            the toAccountNo to set
	 */
	public void setToAccountNo(String toAccountNo) {
		this.toAccountNo = toAccountNo;
	}

	/**
	 * @return the tenderDetails
	 */
	public String getTenderDetails() {
		return tenderDetails;
	}

	/**
	 * @param tenderDetails
	 *            the tenderDetails to set
	 */
	public void setTenderDetails(String tenderDetails) {
		this.tenderDetails = tenderDetails;
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
	 * @return the uniqueTxnNo
	 */
	public String getUniqueTxnNo() {
		return uniqueTxnNo;
	}

	/**
	 * @param uniqueTxnNo
	 *            the uniqueTxnNo to set
	 */
	public void setUniqueTxnNo(String uniqueTxnNo) {
		this.uniqueTxnNo = uniqueTxnNo;
	}

	/**
	 * @return the pdfbytes
	 */
	public byte[] getPdfbytes() {
		return pdfbytes;
	}

	/**
	 * @param pdfbytes
	 *            the pdfbytes to set
	 */
	public void setPdfbytes(byte[] pdfbytes) {
		this.pdfbytes = pdfbytes;
	}

	/**
	 * @return the expenseId
	 */
	public Integer getExpenseId() {
		return expenseId;
	}

	/**
	 * @param expenseId
	 *            the expenseId to set
	 */
	public void setExpenseId(Integer expenseId) {
		this.expenseId = expenseId;
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
	 * @return the locationName
	 */
	public String getLocationName() {
		return locationName;
	}

	/**
	 * @param locationName
	 *            the locationName to set
	 */
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	/**
	 * @return the registerName
	 */
	public String getRegisterName() {
		return registerName;
	}

	/**
	 * @param registerName
	 *            the registerName to set
	 */
	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	/**
	 * @return the locationBean
	 */
	public LocationBean getLocationBean() {
		return locationBean;
	}

	/**
	 * @param locationBean
	 *            the locationBean to set
	 */
	public void setLocationBean(LocationBean locationBean) {
		this.locationBean = locationBean;
	}

	/**
	 * @return the printedBy
	 */
	public String getPrintedBy() {
		return printedBy;
	}

	/**
	 * @param printedBy
	 *            the printedBy to set
	 */
	public void setPrintedBy(String printedBy) {
		this.printedBy = printedBy;
	}

}
