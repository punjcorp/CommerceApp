/**
 * 
 */
package com.punj.app.ecommerce.services.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author admin
 *
 */
public class SaleItem implements Serializable {

	private static final long serialVersionUID = -7569779611406105207L;

	private BigInteger itemId;
	private String hsnNo;
	private String name;
	private String longDesc;
	private String imagePath;

	private BigDecimal unitCostAmt;
	private BigDecimal suggestedPrice;
	private BigDecimal maxRetailPrice;
	private BigDecimal priceAmt;
	private BigDecimal discountAmt;
	private BigDecimal taxAmt;
	private Double qty = 1.0;
	private BigDecimal totalAmt;

	private SaleItemTax sgstTax;
	private SaleItemTax cgstTax;
	private SaleItemTax igstTax;

	private String imageData;
	private String imageType;

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
	 * @return the imagePath
	 */
	public String getImagePath() {
		return imagePath;
	}

	/**
	 * @param imagePath
	 *            the imagePath to set
	 */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	/**
	 * @return the priceAmt
	 */
	public BigDecimal getPriceAmt() {
		return priceAmt;
	}

	/**
	 * @param priceAmt
	 *            the priceAmt to set
	 */
	public void setPriceAmt(BigDecimal priceAmt) {
		this.priceAmt = priceAmt;
	}

	/**
	 * @return the discountAmt
	 */
	public BigDecimal getDiscountAmt() {
		return discountAmt;
	}

	/**
	 * @param discountAmt
	 *            the discountAmt to set
	 */
	public void setDiscountAmt(BigDecimal discountAmt) {
		this.discountAmt = discountAmt;
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
	 * @return the qty
	 */
	public Double getQty() {
		return qty;
	}

	/**
	 * @param qty
	 *            the qty to set
	 */
	public void setQty(Double qty) {
		this.qty = qty;
	}

	/**
	 * @return the totalAmt
	 */
	public BigDecimal getTotalAmt() {
		return totalAmt;
	}

	/**
	 * @param totalAmt
	 *            the totalAmt to set
	 */
	public void setTotalAmt(BigDecimal totalAmt) {
		this.totalAmt = totalAmt;
	}

	/**
	 * @return the sgstTax
	 */
	public SaleItemTax getSgstTax() {
		return sgstTax;
	}

	/**
	 * @param sgstTax
	 *            the sgstTax to set
	 */
	public void setSgstTax(SaleItemTax sgstTax) {
		this.sgstTax = sgstTax;
	}

	/**
	 * @return the cgstTax
	 */
	public SaleItemTax getCgstTax() {
		return cgstTax;
	}

	/**
	 * @param cgstTax
	 *            the cgstTax to set
	 */
	public void setCgstTax(SaleItemTax cgstTax) {
		this.cgstTax = cgstTax;
	}

	/**
	 * @return the igstTax
	 */
	public SaleItemTax getIgstTax() {
		return igstTax;
	}

	/**
	 * @param igstTax
	 *            the igstTax to set
	 */
	public void setIgstTax(SaleItemTax igstTax) {
		this.igstTax = igstTax;
	}

	/**
	 * @return the unitCostAmt
	 */
	public BigDecimal getUnitCostAmt() {
		return unitCostAmt;
	}

	/**
	 * @param unitCostAmt
	 *            the unitCostAmt to set
	 */
	public void setUnitCostAmt(BigDecimal unitCostAmt) {
		this.unitCostAmt = unitCostAmt;
	}

	/**
	 * @return the imageData
	 */
	public String getImageData() {
		return imageData;
	}

	/**
	 * @param imageData
	 *            the imageData to set
	 */
	public void setImageData(String imageData) {
		this.imageData = imageData;
	}

	/**
	 * @return the imageType
	 */
	public String getImageType() {
		return imageType;
	}

	/**
	 * @param imageType
	 *            the imageType to set
	 */
	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	/**
	 * @return the suggestedPrice
	 */
	public BigDecimal getSuggestedPrice() {
		return suggestedPrice;
	}

	/**
	 * @param suggestedPrice
	 *            the suggestedPrice to set
	 */
	public void setSuggestedPrice(BigDecimal suggestedPrice) {
		this.suggestedPrice = suggestedPrice;
	}

	/**
	 * @return the maxRetailPrice
	 */
	public BigDecimal getMaxRetailPrice() {
		return maxRetailPrice;
	}

	/**
	 * @param maxRetailPrice
	 *            the maxRetailPrice to set
	 */
	public void setMaxRetailPrice(BigDecimal maxRetailPrice) {
		this.maxRetailPrice = maxRetailPrice;
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

}
