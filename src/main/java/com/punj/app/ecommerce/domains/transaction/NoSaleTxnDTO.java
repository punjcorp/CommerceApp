package com.punj.app.ecommerce.domains.transaction;

import java.io.Serializable;
import java.math.BigInteger;

public class NoSaleTxnDTO implements Serializable {

	private static final long serialVersionUID = 1532182113824531376L;

	private NoSaleTransaction noSaleTxn;
	private BigInteger voucherNo;

	/**
	 * @return the noSaleTxn
	 */
	public NoSaleTransaction getNoSaleTxn() {
		return noSaleTxn;
	}

	/**
	 * @param noSaleTxn
	 *            the noSaleTxn to set
	 */
	public void setNoSaleTxn(NoSaleTransaction noSaleTxn) {
		this.noSaleTxn = noSaleTxn;
	}

	/**
	 * @return the voucherNo
	 */
	public BigInteger getVoucherNo() {
		return voucherNo;
	}

	/**
	 * @param voucherNo
	 *            the voucherNo to set
	 */
	public void setVoucherNo(BigInteger voucherNo) {
		this.voucherNo = voucherNo;
	}

}
