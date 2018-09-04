/**
 * 
 */
package com.punj.app.ecommerce.models.transaction;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author admin
 *
 */
public class SaleReceiptLineItem {

	private Integer seqNo;
	private BigInteger itemId;
	private String itemName;
	private String longDesc;
	private String hsnNo;

	private BigDecimal qty;
	private BigDecimal grossQty;

	private BigDecimal unitPrice;
	private BigDecimal extendedAmount;
	private BigDecimal taxAmount;
	private BigDecimal discount;

	private BigDecimal netAmount;
	private BigDecimal grossAmount;
	private BigDecimal itemTotal;

	private String upcNo;

	private BigDecimal sgstTaxRate;
	private BigDecimal sgstTaxAmount;
	private BigDecimal cgstTaxRate;
	private BigDecimal cgstTaxAmount;

	private BigDecimal igstTaxRate;
	private BigDecimal igstTaxAmount;

	private String applicableTax;

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
	 * @return the longDesc
	 */
	public String getLongDesc() {
		return longDesc;
	}

	/**
	 * @param longDesc
	 *            the longDesc to set
	 */
	public void setLongDesc(String longDesc) {
		this.longDesc = longDesc;
	}

	/**
	 * @return the hsnNo
	 */
	public String getHsnNo() {
		return hsnNo;
	}

	/**
	 * @param hsnNo
	 *            the hsnNo to set
	 */
	public void setHsnNo(String hsnNo) {
		this.hsnNo = hsnNo;
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
	 * @return the itemTotal
	 */
	public BigDecimal getItemTotal() {
		return itemTotal;
	}

	/**
	 * @param itemTotal
	 *            the itemTotal to set
	 */
	public void setItemTotal(BigDecimal itemTotal) {
		this.itemTotal = itemTotal;
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
	 * @return the applicableTax
	 */
	public String getApplicableTax() {
		return applicableTax;
	}

	/**
	 * @param applicableTax
	 *            the applicableTax to set
	 */
	public void setApplicableTax(String applicableTax) {
		this.applicableTax = applicableTax;
	}

}
