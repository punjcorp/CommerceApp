package com.punj.app.ecommerce.domains.transaction.tender.ids;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TenderDenominationId implements Serializable {
	private static final long serialVersionUID = -7369553731019603487L;

	private TenderCountId tenderCountId;

	@Column(name = "denomination_id")
	private Integer denominationId;

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

}
