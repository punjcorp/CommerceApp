/**
 * 
 */
package com.punj.app.ecommerce.models.tender;

import java.math.BigDecimal;

/**
 * @author admin
 *
 */
public class DenominationBean {

	private BigDecimal denomination;
	private Integer mediaCount;
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
	public Integer getMediaCount() {
		return mediaCount;
	}

	/**
	 * @param mediaCount
	 *            the mediaCount to set
	 */
	public void setMediaCount(Integer mediaCount) {
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
