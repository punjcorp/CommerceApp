/**
 * 
 */
package com.punj.app.ecommerce.domains.gstr;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.punj.app.ecommerce.domains.gstr.ids.GSTInvoiceId;

/**
 * @author admin
 *
 */
@Entity
@Table(name = "v_gstr_one_invoice")
public class GSTInvoice implements Serializable {
	private static final long serialVersionUID = -6702116980889883753L;

	@EmbeddedId
	private GSTInvoiceId gstInvoiceId;

	@Column(name = "invoice_no")
	private BigInteger invoiceNo;

	@Column(name = "subtotal")
	private BigDecimal subTotalAmount;
	@Column(name = "taxable_amount")
	private BigDecimal taxableAmount;
	@Column(name = "tax_total")
	private BigDecimal taxAmount;
	@Column(name = "total")
	private BigDecimal grossAmount;

	@Column(name = "sgst_amount")
	private BigDecimal sgstAmount;
	@Column(name = "cgst_amount")
	private BigDecimal cgstAmount;
	@Column(name = "igst_amount")
	private BigDecimal igstAmount;

	@Column(name = "customer_id")
	private BigInteger customerId;
	@Column(name = "name")
	private String customerName;

	@Column(name = "gst_no")
	private String customerGSTNo;

	/**
	 * @return the gstInvoiceId
	 */
	public GSTInvoiceId getGstInvoiceId() {
		return gstInvoiceId;
	}

	/**
	 * @param gstInvoiceId
	 *            the gstInvoiceId to set
	 */
	public void setGstInvoiceId(GSTInvoiceId gstInvoiceId) {
		this.gstInvoiceId = gstInvoiceId;
	}

	/**
	 * @return the invoiceNo
	 */
	public BigInteger getInvoiceNo() {
		return invoiceNo;
	}

	/**
	 * @param invoiceNo
	 *            the invoiceNo to set
	 */
	public void setInvoiceNo(BigInteger invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	/**
	 * @return the subTotalAmount
	 */
	public BigDecimal getSubTotalAmount() {
		return subTotalAmount;
	}

	/**
	 * @param subTotalAmount
	 *            the subTotalAmount to set
	 */
	public void setSubTotalAmount(BigDecimal subTotalAmount) {
		this.subTotalAmount = subTotalAmount;
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

	/**
	 * @return the customerId
	 */
	public BigInteger getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId
	 *            the customerId to set
	 */
	public void setCustomerId(BigInteger customerId) {
		this.customerId = customerId;
	}

	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param customerName
	 *            the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @return the customerGSTNo
	 */
	public String getCustomerGSTNo() {
		return customerGSTNo;
	}

	/**
	 * @param customerGSTNo
	 *            the customerGSTNo to set
	 */
	public void setCustomerGSTNo(String customerGSTNo) {
		this.customerGSTNo = customerGSTNo;
	}

}
