/**
 * 
 */
package com.punj.app.ecommerce.models.transaction;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author admin
 *
 */
public class SaleLineItem {

	private Integer locationId;
	private Integer registerId;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime businessDate;
	private Integer txnNo;

	private Integer seqNo;
	private BigInteger itemId;
	// this is temp due to JS
	private String itemName;
	private BigDecimal qty;
	private BigDecimal grossQty;

	private BigDecimal unitPrice;
	private BigDecimal extendedAmount;
	private BigDecimal taxAmount;

	private Boolean returnFlag;
	private Boolean excludeFromSalesFlag;

	private String entryMethod;
	private String status;

	private BigDecimal netAmount;
	private BigDecimal grossAmount;
	// this is temp due to JS
	private BigDecimal price;

	private BigDecimal returnedQty;
	private String upcNo;
	private String txnType;
	private String invAction;

	private Integer orgLocationId;
	private Integer orgRegisterId;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime orgBusinessDate;
	private Integer orgTxnNo;

	private String returnReason;
	private String returnComment;
	private String returnTypeCode;

	private Integer receiptCount;

	private BigDecimal baseUnitPrice;
	private BigDecimal baseExtendedPrice;
	private String newDescription;
	// this is temp due to JS
	private String itemDesc;

	private String createdBy;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdDate;
	private String modifiedBy;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime modifiedDate;

	/**
	 * This section is for tax and discount information this might be changes to different classes later on
	 */
	private BigDecimal discount;
	private BigDecimal sgstTax;
	private BigDecimal cgstTax;
	private BigDecimal igstTax;
	private BigDecimal sgstTaxRate;
	private BigDecimal cgstTaxRate;
	private BigDecimal igstTaxRate;

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
	 * @return the seqNo
	 */
	public Integer getSeqNo() {
		return seqNo;
	}

	/**
	 * @param seqNo
	 *            the seqNo to set
	 */
	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}

	/**
	 * @return the itemId
	 */
	public BigInteger getItemId() {
		return itemId;
	}

	/**
	 * @param itemId
	 *            the itemId to set
	 */
	public void setItemId(BigInteger itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * @param itemName
	 *            the itemName to set
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * @return the qty
	 */
	public BigDecimal getQty() {
		return qty;
	}

	/**
	 * @param qty
	 *            the qty to set
	 */
	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}

	/**
	 * @return the grossQty
	 */
	public BigDecimal getGrossQty() {
		return grossQty;
	}

	/**
	 * @param grossQty
	 *            the grossQty to set
	 */
	public void setGrossQty(BigDecimal grossQty) {
		this.grossQty = grossQty;
	}

	/**
	 * @return the unitPrice
	 */
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	/**
	 * @param unitPrice
	 *            the unitPrice to set
	 */
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	/**
	 * @return the extendedAmount
	 */
	public BigDecimal getExtendedAmount() {
		return extendedAmount;
	}

	/**
	 * @param extendedAmount
	 *            the extendedAmount to set
	 */
	public void setExtendedAmount(BigDecimal extendedAmount) {
		this.extendedAmount = extendedAmount;
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
	 * @return the returnFlag
	 */
	public Boolean getReturnFlag() {
		return returnFlag;
	}

	/**
	 * @param returnFlag
	 *            the returnFlag to set
	 */
	public void setReturnFlag(Boolean returnFlag) {
		this.returnFlag = returnFlag;
	}

	/**
	 * @return the excludeFromSalesFlag
	 */
	public Boolean getExcludeFromSalesFlag() {
		return excludeFromSalesFlag;
	}

	/**
	 * @param excludeFromSalesFlag
	 *            the excludeFromSalesFlag to set
	 */
	public void setExcludeFromSalesFlag(Boolean excludeFromSalesFlag) {
		this.excludeFromSalesFlag = excludeFromSalesFlag;
	}

	/**
	 * @return the entryMethod
	 */
	public String getEntryMethod() {
		return entryMethod;
	}

	/**
	 * @param entryMethod
	 *            the entryMethod to set
	 */
	public void setEntryMethod(String entryMethod) {
		this.entryMethod = entryMethod;
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
	 * @return the netAmount
	 */
	public BigDecimal getNetAmount() {
		return netAmount;
	}

	/**
	 * @param netAmount
	 *            the netAmount to set
	 */
	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}

	/**
	 * @return the grossAmount
	 */
	public BigDecimal getGrossAmount() {
		return grossAmount;
	}

	/**
	 * @param grossAmount
	 *            the grossAmount to set
	 */
	public void setGrossAmount(BigDecimal grossAmount) {
		this.grossAmount = grossAmount;
	}

	/**
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * @return the returnedQty
	 */
	public BigDecimal getReturnedQty() {
		return returnedQty;
	}

	/**
	 * @param returnedQty
	 *            the returnedQty to set
	 */
	public void setReturnedQty(BigDecimal returnedQty) {
		this.returnedQty = returnedQty;
	}

	/**
	 * @return the upcNo
	 */
	public String getUpcNo() {
		return upcNo;
	}

	/**
	 * @param upcNo
	 *            the upcNo to set
	 */
	public void setUpcNo(String upcNo) {
		this.upcNo = upcNo;
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
	 * @return the invAction
	 */
	public String getInvAction() {
		return invAction;
	}

	/**
	 * @param invAction
	 *            the invAction to set
	 */
	public void setInvAction(String invAction) {
		this.invAction = invAction;
	}

	/**
	 * @return the orgLocationId
	 */
	public Integer getOrgLocationId() {
		return orgLocationId;
	}

	/**
	 * @param orgLocationId
	 *            the orgLocationId to set
	 */
	public void setOrgLocationId(Integer orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	/**
	 * @return the orgRegisterId
	 */
	public Integer getOrgRegisterId() {
		return orgRegisterId;
	}

	/**
	 * @param orgRegisterId
	 *            the orgRegisterId to set
	 */
	public void setOrgRegisterId(Integer orgRegisterId) {
		this.orgRegisterId = orgRegisterId;
	}

	/**
	 * @return the orgBusinessDate
	 */
	public LocalDateTime getOrgBusinessDate() {
		return orgBusinessDate;
	}

	/**
	 * @param orgBusinessDate
	 *            the orgBusinessDate to set
	 */
	public void setOrgBusinessDate(LocalDateTime orgBusinessDate) {
		this.orgBusinessDate = orgBusinessDate;
	}

	/**
	 * @return the orgTxnNo
	 */
	public Integer getOrgTxnNo() {
		return orgTxnNo;
	}

	/**
	 * @param orgTxnNo
	 *            the orgTxnNo to set
	 */
	public void setOrgTxnNo(Integer orgTxnNo) {
		this.orgTxnNo = orgTxnNo;
	}

	/**
	 * @return the returnReason
	 */
	public String getReturnReason() {
		return returnReason;
	}

	/**
	 * @param returnReason
	 *            the returnReason to set
	 */
	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}

	/**
	 * @return the returnComment
	 */
	public String getReturnComment() {
		return returnComment;
	}

	/**
	 * @param returnComment
	 *            the returnComment to set
	 */
	public void setReturnComment(String returnComment) {
		this.returnComment = returnComment;
	}

	/**
	 * @return the returnTypeCode
	 */
	public String getReturnTypeCode() {
		return returnTypeCode;
	}

	/**
	 * @param returnTypeCode
	 *            the returnTypeCode to set
	 */
	public void setReturnTypeCode(String returnTypeCode) {
		this.returnTypeCode = returnTypeCode;
	}

	/**
	 * @return the receiptCount
	 */
	public Integer getReceiptCount() {
		return receiptCount;
	}

	/**
	 * @param receiptCount
	 *            the receiptCount to set
	 */
	public void setReceiptCount(Integer receiptCount) {
		this.receiptCount = receiptCount;
	}

	/**
	 * @return the baseUnitPrice
	 */
	public BigDecimal getBaseUnitPrice() {
		return baseUnitPrice;
	}

	/**
	 * @param baseUnitPrice
	 *            the baseUnitPrice to set
	 */
	public void setBaseUnitPrice(BigDecimal baseUnitPrice) {
		this.baseUnitPrice = baseUnitPrice;
	}

	/**
	 * @return the baseExtendedPrice
	 */
	public BigDecimal getBaseExtendedPrice() {
		return baseExtendedPrice;
	}

	/**
	 * @param baseExtendedPrice
	 *            the baseExtendedPrice to set
	 */
	public void setBaseExtendedPrice(BigDecimal baseExtendedPrice) {
		this.baseExtendedPrice = baseExtendedPrice;
	}

	/**
	 * @return the newDescription
	 */
	public String getNewDescription() {
		return newDescription;
	}

	/**
	 * @param newDescription
	 *            the newDescription to set
	 */
	public void setNewDescription(String newDescription) {
		this.newDescription = newDescription;
	}

	/**
	 * @return the itemDesc
	 */
	public String getItemDesc() {
		return itemDesc;
	}

	/**
	 * @param itemDesc
	 *            the itemDesc to set
	 */
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
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
	 * @return the discount
	 */
	public BigDecimal getDiscount() {
		return discount;
	}

	/**
	 * @param discount
	 *            the discount to set
	 */
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	/**
	 * @return the sgstTax
	 */
	public BigDecimal getSgstTax() {
		return sgstTax;
	}

	/**
	 * @param sgstTax
	 *            the sgstTax to set
	 */
	public void setSgstTax(BigDecimal sgstTax) {
		this.sgstTax = sgstTax;
	}

	/**
	 * @return the cgstTax
	 */
	public BigDecimal getCgstTax() {
		return cgstTax;
	}

	/**
	 * @param cgstTax
	 *            the cgstTax to set
	 */
	public void setCgstTax(BigDecimal cgstTax) {
		this.cgstTax = cgstTax;
	}

	/**
	 * @return the igstTax
	 */
	public BigDecimal getIgstTax() {
		return igstTax;
	}

	/**
	 * @param igstTax
	 *            the igstTax to set
	 */
	public void setIgstTax(BigDecimal igstTax) {
		this.igstTax = igstTax;
	}

	/**
	 * @return the sgstTaxRate
	 */
	public BigDecimal getSgstTaxRate() {
		return sgstTaxRate;
	}

	/**
	 * @param sgstTaxRate
	 *            the sgstTaxRate to set
	 */
	public void setSgstTaxRate(BigDecimal sgstTaxRate) {
		this.sgstTaxRate = sgstTaxRate;
	}

	/**
	 * @return the cgstTaxRate
	 */
	public BigDecimal getCgstTaxRate() {
		return cgstTaxRate;
	}

	/**
	 * @param cgstTaxRate
	 *            the cgstTaxRate to set
	 */
	public void setCgstTaxRate(BigDecimal cgstTaxRate) {
		this.cgstTaxRate = cgstTaxRate;
	}

	/**
	 * @return the igstTaxRate
	 */
	public BigDecimal getIgstTaxRate() {
		return igstTaxRate;
	}

	/**
	 * @param igstTaxRate
	 *            the igstTaxRate to set
	 */
	public void setIgstTaxRate(BigDecimal igstTaxRate) {
		this.igstTaxRate = igstTaxRate;
	}

}
