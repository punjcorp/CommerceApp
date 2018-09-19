/**
 * 
 */
package com.punj.app.ecommerce.models.utils;

import java.math.BigInteger;

import javax.validation.constraints.NotNull;

/**
 * @author admin
 *
 */
public class ReceiptBean {

	@NotNull(message = "{commerce.error.string.empty}")
	private BigInteger startingInvoiceNo;
	private BigInteger endingInvoiceNo;
	@NotNull(message = "{commerce.error.string.empty}")
	private Integer noOfCopies;

	/**
	 * @return the startingInvoiceNo
	 */
	public BigInteger getStartingInvoiceNo() {
		return startingInvoiceNo;
	}

	/**
	 * @param startingInvoiceNo
	 *            the startingInvoiceNo to set
	 */
	public void setStartingInvoiceNo(BigInteger startingInvoiceNo) {
		this.startingInvoiceNo = startingInvoiceNo;
	}

	/**
	 * @return the endingInvoiceNo
	 */
	public BigInteger getEndingInvoiceNo() {
		return endingInvoiceNo;
	}

	/**
	 * @param endingInvoiceNo
	 *            the endingInvoiceNo to set
	 */
	public void setEndingInvoiceNo(BigInteger endingInvoiceNo) {
		this.endingInvoiceNo = endingInvoiceNo;
	}

	/**
	 * @return the noOfCopies
	 */
	public Integer getNoOfCopies() {
		return noOfCopies;
	}

	/**
	 * @param noOfCopies
	 *            the noOfCopies to set
	 */
	public void setNoOfCopies(Integer noOfCopies) {
		this.noOfCopies = noOfCopies;
	}

}
