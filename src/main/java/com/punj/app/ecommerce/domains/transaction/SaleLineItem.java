package com.punj.app.ecommerce.domains.transaction;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.punj.app.ecommerce.domains.transaction.ids.SaleLineItemId;

@Entity
@Table(name = "txn_li_item")
public class SaleLineItem implements Serializable {

	private static final long serialVersionUID = 2176836962765011664L;

	@EmbeddedId
	private SaleLineItemId saleLineItemId;

	private BigDecimal qty;
	@Column(name = "gross_qty")
	private BigDecimal grossQty;

	@Column(name = "unit_price")
	private BigDecimal unitPrice;
	@Column(name = "extended_amount")
	private BigDecimal extendedAmt;
	@Column(name = "tax_amount")
	private BigDecimal taxAmt;

	@Column(name = "net_amount")
	private BigDecimal netAmt;
	@Column(name = "gross_amount")
	private BigDecimal grossAmt;
	@Column(name = "returned_qty")
	private Integer returnedQty;

	@Column(name = "return_flag")
	private Boolean returnFlag;
	@Column(name = "entry_method")
	private String entryMethod;

	@Column(name = "upc_no")
	private String upc;
	@Column(name = "txn_type")
	private String txnType;
	@Column(name = "inv_action_code")
	private String invAction;

	@Column(name = "org_location_id")
	private Integer orgLocationId;
	@Column(name = "org_business_date")
	private LocalDateTime orgBusinessDate;
	@Column(name = "org_register")
	private Integer orgRegister;
	@Column(name = "org_txn_no")
	private Integer orgTransactionSeq;

	@Column(name = "return_reason_code")
	private String returnReason;
	@Column(name = "return_comment")
	private String returnComment;

	@Column(name = "return_type_code")
	private String returnType;
	@Column(name = "rcpt_count")
	private Integer receiptCount;

	@Column(name = "base_unit_price")
	private BigDecimal baseUnitPrice;
	@Column(name = "base_extended_price")
	private BigDecimal baseExtendedAmt;
	@Column(name = "new_description")
	private String newDescription;

	@Column(name = "exclude_from_sales_flag")
	private Boolean excludeFlag;

	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "created_date")
	private LocalDateTime createdDate;
	@Column(name = "modified_by")
	private String modifiedBy;
	@Column(name = "modified_date")
	private LocalDateTime modifiedDate;

	/**
	 * @return the saleLineItemId
	 */
	public SaleLineItemId getSaleLineItemId() {
		return saleLineItemId;
	}

	/**
	 * @param saleLineItemId
	 *            the saleLineItemId to set
	 */
	public void setSaleLineItemId(SaleLineItemId saleLineItemId) {
		this.saleLineItemId = saleLineItemId;
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
	 * @return the extendedAmt
	 */
	public BigDecimal getExtendedAmt() {
		return extendedAmt;
	}

	/**
	 * @param extendedAmt
	 *            the extendedAmt to set
	 */
	public void setExtendedAmt(BigDecimal extendedAmt) {
		this.extendedAmt = extendedAmt;
	}

	/**
	 * @return the taxAmt
	 */
	public BigDecimal getTaxAmt() {
		return taxAmt;
	}

	/**
	 * @param taxAmt
	 *            the taxAmt to set
	 */
	public void setTaxAmt(BigDecimal taxAmt) {
		this.taxAmt = taxAmt;
	}

	/**
	 * @return the netAmt
	 */
	public BigDecimal getNetAmt() {
		return netAmt;
	}

	/**
	 * @param netAmt
	 *            the netAmt to set
	 */
	public void setNetAmt(BigDecimal netAmt) {
		this.netAmt = netAmt;
	}

	/**
	 * @return the grossAmt
	 */
	public BigDecimal getGrossAmt() {
		return grossAmt;
	}

	/**
	 * @param grossAmt
	 *            the grossAmt to set
	 */
	public void setGrossAmt(BigDecimal grossAmt) {
		this.grossAmt = grossAmt;
	}

	/**
	 * @return the returnedQty
	 */
	public Integer getReturnedQty() {
		return returnedQty;
	}

	/**
	 * @param returnedQty
	 *            the returnedQty to set
	 */
	public void setReturnedQty(Integer returnedQty) {
		this.returnedQty = returnedQty;
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
	 * @return the upc
	 */
	public String getUpc() {
		return upc;
	}

	/**
	 * @param upc
	 *            the upc to set
	 */
	public void setUpc(String upc) {
		this.upc = upc;
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
	 * @return the orgRegister
	 */
	public Integer getOrgRegister() {
		return orgRegister;
	}

	/**
	 * @param orgRegister
	 *            the orgRegister to set
	 */
	public void setOrgRegister(Integer orgRegister) {
		this.orgRegister = orgRegister;
	}

	/**
	 * @return the orgTransactionSeq
	 */
	public Integer getOrgTransactionSeq() {
		return orgTransactionSeq;
	}

	/**
	 * @param orgTransactionSeq
	 *            the orgTransactionSeq to set
	 */
	public void setOrgTransactionSeq(Integer orgTransactionSeq) {
		this.orgTransactionSeq = orgTransactionSeq;
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
	 * @return the returnType
	 */
	public String getReturnType() {
		return returnType;
	}

	/**
	 * @param returnType
	 *            the returnType to set
	 */
	public void setReturnType(String returnType) {
		this.returnType = returnType;
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
	 * @return the baseExtendedAmt
	 */
	public BigDecimal getBaseExtendedAmt() {
		return baseExtendedAmt;
	}

	/**
	 * @param baseExtendedAmt
	 *            the baseExtendedAmt to set
	 */
	public void setBaseExtendedAmt(BigDecimal baseExtendedAmt) {
		this.baseExtendedAmt = baseExtendedAmt;
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
	 * @return the excludeFlag
	 */
	public Boolean getExcludeFlag() {
		return excludeFlag;
	}

	/**
	 * @param excludeFlag
	 *            the excludeFlag to set
	 */
	public void setExcludeFlag(Boolean excludeFlag) {
		this.excludeFlag = excludeFlag;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((saleLineItemId == null) ? 0 : saleLineItemId.hashCode());
		result = prime * result + ((txnType == null) ? 0 : txnType.hashCode());
		result = prime * result + ((upc == null) ? 0 : upc.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		SaleLineItem other = (SaleLineItem) obj;
		if (saleLineItemId == null) {
			if (other.saleLineItemId != null) {
				return false;
			}
		} else if (!saleLineItemId.equals(other.saleLineItemId)) {
			return false;
		}
		if (txnType == null) {
			if (other.txnType != null) {
				return false;
			}
		} else if (!txnType.equals(other.txnType)) {
			return false;
		}
		if (upc == null) {
			if (other.upc != null) {
				return false;
			}
		} else if (!upc.equals(other.upc)) {
			return false;
		}
		return true;
	}

}
