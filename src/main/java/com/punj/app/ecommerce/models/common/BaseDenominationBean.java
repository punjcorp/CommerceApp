/**
 * 
 */
package com.punj.app.ecommerce.models.common;

import java.math.BigDecimal;

/**
 * @author admin
 *
 */
public class BaseDenominationBean {

	private Integer denominationId;
	private String code;
	private String currencyCode;

	private BigDecimal denomValue;
	private String description;

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
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @param currencyCode
	 *            the currencyCode to set
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * @return the denomValue
	 */
	public BigDecimal getDenomValue() {
		return denomValue;
	}

	/**
	 * @param denomValue
	 *            the denomValue to set
	 */
	public void setDenomValue(BigDecimal denomValue) {
		this.denomValue = denomValue;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
