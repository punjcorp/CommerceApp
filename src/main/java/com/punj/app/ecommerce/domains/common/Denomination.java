/**
 * 
 */
package com.punj.app.ecommerce.domains.common;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author admin
 *
 */
@Entity
@Table(name = "denomination_master")
public class Denomination implements Serializable {

	private static final long serialVersionUID = -3964397954643172463L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "denomination_id", updatable = false, nullable = false)
	private Integer denominationId;

	@Column(name = "currency_code")
	private String currencyCode;
	private String code;
	private String description;
	@Column(name = "value")
	private BigDecimal denomValue;

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

}
