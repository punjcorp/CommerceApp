/**
 * 
 */
package com.punj.app.ecommerce.models.nosale;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author admin
 *
 */
public class ExpenseTenderBean {
	private Integer tenderId;
	private String Name;
	private String typeCode;

	private BigDecimal tenderAmount;

	private String payeeName;
	private String payeePhone;
	private String bankName;
	private String bankBranch;
	private String accountNo;
	private String tenderDetails;

	private String createdBy;
	private LocalDateTime createdDate;

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
	 * @return the name
	 */
	public String getName() {
		return Name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		Name = name;
	}

	/**
	 * @return the typeCode
	 */
	public String getTypeCode() {
		return typeCode;
	}

	/**
	 * @param typeCode
	 *            the typeCode to set
	 */
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
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
	 * @return the payeeName
	 */
	public String getPayeeName() {
		return payeeName;
	}

	/**
	 * @param payeeName
	 *            the payeeName to set
	 */
	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	/**
	 * @return the payeePhone
	 */
	public String getPayeePhone() {
		return payeePhone;
	}

	/**
	 * @param payeePhone
	 *            the payeePhone to set
	 */
	public void setPayeePhone(String payeePhone) {
		this.payeePhone = payeePhone;
	}

	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param bankName
	 *            the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * @return the bankBranch
	 */
	public String getBankBranch() {
		return bankBranch;
	}

	/**
	 * @param bankBranch
	 *            the bankBranch to set
	 */
	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	/**
	 * @return the accountNo
	 */
	public String getAccountNo() {
		return accountNo;
	}

	/**
	 * @param accountNo
	 *            the accountNo to set
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
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

}
