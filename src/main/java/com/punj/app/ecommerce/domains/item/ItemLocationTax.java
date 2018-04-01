/**
 * 
 */
package com.punj.app.ecommerce.domains.item;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.punj.app.ecommerce.domains.item.ids.ItemLocationTaxId;

/**
 * @author admin
 *
 */
@Entity
@Table(name = "v_item_location_tax")
public class ItemLocationTax implements Serializable {

	private static final long serialVersionUID = -3620171348318929507L;

	@EmbeddedId
	private ItemLocationTaxId itemLocationTaxId;

	private String name;
	@Column(name = "long_desc")
	private String longDesc;

	@Column(name = "base_unit_cost")
	private BigDecimal baseUnitCost;

	@Column(name = "tax_group_id")
	private Integer taxGRoupId;
	@Column(name = "tax_group_name")
	private String taxGroupName;

	@Column(name = "sgst_amount")
	private BigDecimal sgstAmount;
	@Column(name = "sgst_rate")
	private BigDecimal sgstRate;
	@Column(name = "sgst_code")
	private String sgstCode;

	@Column(name = "cgst_amount")
	private BigDecimal cgstAmount;
	@Column(name = "cgst_rate")
	private BigDecimal cgstRate;
	@Column(name = "cgst_code")
	private String cgstCode;

	@Column(name = "igst_amount")
	private BigDecimal igstAmount;
	@Column(name = "igst_rate")
	private BigDecimal igstRate;
	@Column(name = "igst_code")
	private String igstCode;

	/**
	 * @return the itemLocationTaxId
	 */
	public ItemLocationTaxId getItemLocationTaxId() {
		return itemLocationTaxId;
	}

	/**
	 * @param itemLocationTaxId
	 *            the itemLocationTaxId to set
	 */
	public void setItemLocationTaxId(ItemLocationTaxId itemLocationTaxId) {
		this.itemLocationTaxId = itemLocationTaxId;
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
	 * @return the baseUnitCost
	 */
	public BigDecimal getBaseUnitCost() {
		return baseUnitCost;
	}

	/**
	 * @param baseUnitCost
	 *            the baseUnitCost to set
	 */
	public void setBaseUnitCost(BigDecimal baseUnitCost) {
		this.baseUnitCost = baseUnitCost;
	}


	/**
	 * @return the taxGRoupId
	 */
	public Integer getTaxGRoupId() {
		return taxGRoupId;
	}

	/**
	 * @param taxGRoupId
	 *            the taxGRoupId to set
	 */
	public void setTaxGRoupId(Integer taxGRoupId) {
		this.taxGRoupId = taxGRoupId;
	}

	/**
	 * @return the taxGroupName
	 */
	public String getTaxGroupName() {
		return taxGroupName;
	}

	/**
	 * @param taxGroupName
	 *            the taxGroupName to set
	 */
	public void setTaxGroupName(String taxGroupName) {
		this.taxGroupName = taxGroupName;
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
	 * @return the sgstCode
	 */
	public String getSgstCode() {
		return sgstCode;
	}

	/**
	 * @param sgstCode
	 *            the sgstCode to set
	 */
	public void setSgstCode(String sgstCode) {
		this.sgstCode = sgstCode;
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
	 * @return the cgstCode
	 */
	public String getCgstCode() {
		return cgstCode;
	}

	/**
	 * @param cgstCode
	 *            the cgstCode to set
	 */
	public void setCgstCode(String cgstCode) {
		this.cgstCode = cgstCode;
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
	 * @return the igstCode
	 */
	public String getIgstCode() {
		return igstCode;
	}

	/**
	 * @param igstCode
	 *            the igstCode to set
	 */
	public void setIgstCode(String igstCode) {
		this.igstCode = igstCode;
	}

}
