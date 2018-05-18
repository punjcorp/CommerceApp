/**
 * 
 */
package com.punj.app.ecommerce.models.tender;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import com.punj.app.ecommerce.models.common.validator.ValidationGroup;

/**
 * @author admin
 *
 */
public class DenominationBean {

	@NotNull(message = "{commerce.error.select.empty}", groups = { ValidationGroup.ValidationGroupStoreOpen.class,
			ValidationGroup.ValidationGroupRegOpen.class })
	private Integer denominationId;
	private BigDecimal denomValue;
	@NotNull(message = "{commerce.error.count.min}", groups = { ValidationGroup.ValidationGroupStoreOpen.class, ValidationGroup.ValidationGroupRegOpen.class })
	@Range(min = 1, max = 9999, message = "{commerce.error.media.count}", groups = { ValidationGroup.ValidationGroupStoreOpen.class,
			ValidationGroup.ValidationGroupRegOpen.class })
	private BigInteger mediaCount;
	@NotNull(message = "{commerce.error.amount.empty}", groups = { ValidationGroup.ValidationGroupStoreOpen.class,
			ValidationGroup.ValidationGroupRegOpen.class })
	@DecimalMin(value = "0.01", message = "{commerce.error.amt.range}", groups = { ValidationGroup.ValidationGroupStoreOpen.class,
			ValidationGroup.ValidationGroupRegOpen.class })
	@DecimalMax(value = "9999999999.99", message = "{commerce.error.amt.range}", groups = { ValidationGroup.ValidationGroupStoreOpen.class,
			ValidationGroup.ValidationGroupRegOpen.class })
	private BigDecimal amount;

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
