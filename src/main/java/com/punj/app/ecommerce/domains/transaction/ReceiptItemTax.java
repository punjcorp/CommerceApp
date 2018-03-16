/**
 * 
 */
package com.punj.app.ecommerce.domains.transaction;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.punj.app.ecommerce.domains.transaction.ids.SaleLineItemId;

/**
 * @author admin
 *
 */
@Entity
@Table(name = "v_receipt_li_item")
public class ReceiptItemTax implements Serializable {

	private static final long serialVersionUID = 7430609694989308165L;

	@EmbeddedId
	private SaleLineItemId saleLineItemId;
	@Column(name = "name")
	private String name;
	@Column(name = "long_Desc")
	private String longDesc;
	@Column(name = "upc_no")
	private String upcNo;
	@Column(name = "hsn_no")
	private String hsnNo;

	private BigDecimal qty;
	@Column(name = "unit_price")
	private BigDecimal unitPrice;
	@Column(name = "extended_amount")
	private BigDecimal extendedAmount;
	@Column(name = "discount_amount")
	private BigDecimal discountAmount;
	@Column(name = "tax_amount")
	private BigDecimal taxAmount;
	@Column(name = "gross_amount")
	private BigDecimal grossAmount;

	@Column(name = "sgst_tax_group_id")
	private Integer sgstGroupId;
	@Column(name = "sgst_tax_rule_rate_id")
	private Integer sgstRuleId;
	@Column(name = "sgst_percentage")
	private BigDecimal sgstRate;
	@Column(name = "sgst_amount")
	private BigDecimal sgstAmount;

	@Column(name = "cgst_tax_group_id")
	private Integer cgstGroupId;
	@Column(name = "cgst_tax_rule_rate_id")
	private Integer cgstRuleId;
	@Column(name = "cgst_percentage")
	private BigDecimal cgstRate;
	@Column(name = "cgst_amount")
	private BigDecimal cgstAmount;

	@Column(name = "igst_tax_group_id")
	private Integer igstGroupId;
	@Column(name = "igst_tax_rule_rate_id")
	private Integer igstRuleId;
	@Column(name = "igst_percentage")
	private BigDecimal igstRate;
	@Column(name = "igst_amount")
	private BigDecimal igstAmount;

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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the upcNo
	 */
	public String getUpcNo() {
		return upcNo;
	}

	/**
	 * @param upcNo the upcNo to set
	 */
	public void setUpcNo(String upcNo) {
		this.upcNo = upcNo;
	}

	/**
	 * @return the hsnNo
	 */
	public String getHsnNo() {
		return hsnNo;
	}

	/**
	 * @param hsnNo the hsnNo to set
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
	 * @return the sgstGroupId
	 */
	public Integer getSgstGroupId() {
		return sgstGroupId;
	}

	/**
	 * @param sgstGroupId
	 *            the sgstGroupId to set
	 */
	public void setSgstGroupId(Integer sgstGroupId) {
		this.sgstGroupId = sgstGroupId;
	}

	/**
	 * @return the sgstRuleId
	 */
	public Integer getSgstRuleId() {
		return sgstRuleId;
	}

	/**
	 * @param sgstRuleId
	 *            the sgstRuleId to set
	 */
	public void setSgstRuleId(Integer sgstRuleId) {
		this.sgstRuleId = sgstRuleId;
	}

	/**
	 * @return the sgstRate
	 */
	public BigDecimal getSgstRate() {
		return sgstRate;
	}

	/**
	 * @param sgstRate
	 *            the sgstRate to set
	 */
	public void setSgstRate(BigDecimal sgstRate) {
		this.sgstRate = sgstRate;
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
	 * @return the cgstGroupId
	 */
	public Integer getCgstGroupId() {
		return cgstGroupId;
	}

	/**
	 * @param cgstGroupId
	 *            the cgstGroupId to set
	 */
	public void setCgstGroupId(Integer cgstGroupId) {
		this.cgstGroupId = cgstGroupId;
	}

	/**
	 * @return the cgstRuleId
	 */
	public Integer getCgstRuleId() {
		return cgstRuleId;
	}

	/**
	 * @param cgstRuleId
	 *            the cgstRuleId to set
	 */
	public void setCgstRuleId(Integer cgstRuleId) {
		this.cgstRuleId = cgstRuleId;
	}

	/**
	 * @return the cgstRate
	 */
	public BigDecimal getCgstRate() {
		return cgstRate;
	}

	/**
	 * @param cgstRate
	 *            the cgstRate to set
	 */
	public void setCgstRate(BigDecimal cgstRate) {
		this.cgstRate = cgstRate;
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
	 * @return the igstGroupId
	 */
	public Integer getIgstGroupId() {
		return igstGroupId;
	}

	/**
	 * @param igstGroupId
	 *            the igstGroupId to set
	 */
	public void setIgstGroupId(Integer igstGroupId) {
		this.igstGroupId = igstGroupId;
	}

	/**
	 * @return the igstRuleId
	 */
	public Integer getIgstRuleId() {
		return igstRuleId;
	}

	/**
	 * @param igstRuleId
	 *            the igstRuleId to set
	 */
	public void setIgstRuleId(Integer igstRuleId) {
		this.igstRuleId = igstRuleId;
	}

	/**
	 * @return the igstRate
	 */
	public BigDecimal getIgstRate() {
		return igstRate;
	}

	/**
	 * @param igstRate
	 *            the igstRate to set
	 */
	public void setIgstRate(BigDecimal igstRate) {
		this.igstRate = igstRate;
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
