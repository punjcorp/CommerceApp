package com.punj.app.ecommerce.domains.transaction.tender.ids;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Embeddable;

@Embeddable
public class TenderDenominationId implements Serializable {
	private static final long serialVersionUID = -7369553731019603487L;

	private TenderCountId tenderCountId;

	private BigDecimal denomination;

	/**
	 * @return the tenderCountId
	 */
	public TenderCountId getTenderCountId() {
		return tenderCountId;
	}

	/**
	 * @param tenderCountId
	 *            the tenderCountId to set
	 */
	public void setTenderCountId(TenderCountId tenderCountId) {
		this.tenderCountId = tenderCountId;
	}

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

}
