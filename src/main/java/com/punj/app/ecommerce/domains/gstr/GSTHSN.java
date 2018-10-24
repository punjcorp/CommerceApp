/**
 * 
 */
package com.punj.app.ecommerce.domains.gstr;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author admin
 *
 */
@Entity
@Table(name = "v_gstr_one_hsn")
public class GSTHSN implements Serializable {

	private static final long serialVersionUID = -4350167879090628075L;

	@Id
	@Column(name = "hsn_no", updatable = false, nullable = false)
	private String hsnNo;

	@Column(name = "name")
	private String itemDesc;

	@Column(name = "qty")
	private BigDecimal qty;

	@Column(name = "extended_amount")
	private BigDecimal extendedAmount;
	@Column(name = "discount_amount")
	private BigDecimal discountAmount;

	@Column(name = "taxable_amount")
	private BigDecimal taxableAmount;

	@Column(name = "tax_amount")
	private BigDecimal taxAmount;
	@Column(name = "gross_amount")
	private BigDecimal grossAmount;

	@Column(name = "sgst_amount")
	private BigDecimal sgstAmount;
	@Column(name = "cgst_amount")
	private BigDecimal cgstAmount;
	@Column(name = "igst_amount")
	private BigDecimal igstAmount;

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
	 * @return the taxableAmount
	 */
	public BigDecimal getTaxableAmount() {
		return taxableAmount;
	}

	/**
	 * @param taxableAmount
	 *            the taxableAmount to set
	 */
	public void setTaxableAmount(BigDecimal taxableAmount) {
		this.taxableAmount = taxableAmount;
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
	 * @return the sgstAmount
	 */
	public BigDecimal getSgstAmount() {
		return sgstAmount;
	}

	/**
	 * @param sgstAmount
	 *            the sgstAmount to set
	 */
	public void setSgstAmount(BigDecimal sgstAmount) {
		this.sgstAmount = sgstAmount;
	}

	/**
	 * @return the cgstAmount
	 */
	public BigDecimal getCgstAmount() {
		return cgstAmount;
	}

	/**
	 * @param cgstAmount
	 *            the cgstAmount to set
	 */
	public void setCgstAmount(BigDecimal cgstAmount) {
		this.cgstAmount = cgstAmount;
	}

	/**
	 * @return the igstAmount
	 */
	public BigDecimal getIgstAmount() {
		return igstAmount;
	}

	/**
	 * @param igstAmount
	 *            the igstAmount to set
	 */
	public void setIgstAmount(BigDecimal igstAmount) {
		this.igstAmount = igstAmount;
	}

}
