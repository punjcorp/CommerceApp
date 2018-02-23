/**
 * 
 */
package com.punj.app.ecommerce.models.tender;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.validation.constraints.NotNull;

/**
 * @author admin
 *
 */
public class DenominationBean {

	@NotNull(message = "{commerce.error.select.empty}")
	private BigDecimal denomination;
	@NotNull(message = "{commerce.error.count.min}")
	private BigInteger mediaCount;
	@NotNull(message = "{commerce.error.amount.empty}")
	private BigDecimal amount;

	/**
	 * @return the denomination
	 */
	public BigDecimal getDenomination() {
		return denomination;
	}

	/**
	 * @param denomination
	 *            the denomination to set
	 */
	public void setDenomination(BigDecimal denomination) {
		this.denomination = denomination;
	}

	/**
	 * @return the mediaCount
	 */
	public BigInteger getMediaCount() {
		return mediaCount;
	}

	/**
	 * @param mediaCount
	 *            the mediaCount to set
	 */
	public void setMediaCount(BigInteger mediaCount) {
		this.mediaCount = mediaCount;
	}

	/**
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
