/**
 * 
 */
package com.punj.app.ecommerce.services.dtos.tender;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author admin
 *
 */
public class DenominationDTO implements Serializable {

	private static final long serialVersionUID = 2221756121534912702L;

	private Integer denominationId;
	private BigDecimal amount;
	private BigInteger mediaCount;

	/**
	 * @return the denominationId
	 */
	public Integer getDenominationId() {
		return denominationId;
	}

	/**
	 * @param denominationId
	 *            the denominationId to set
	 */
	public void setDenominationId(Integer denominationId) {
		this.denominationId = denominationId;
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

}
