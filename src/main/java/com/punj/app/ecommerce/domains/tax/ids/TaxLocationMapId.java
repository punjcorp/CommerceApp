package com.punj.app.ecommerce.domains.tax.ids;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TaxLocationMapId implements Serializable {

	private static final long serialVersionUID = 7498729736138983523L;

	@Column(name = "location_id")
	private Integer locationId;

	@Column(name = "tax_location_id")
	private Integer taxLocationId;

	/**
	 * @return the locationId
	 */
	public Integer getLocationId() {
		return locationId;
	}

	/**
	 * @param locationId
	 *            the locationId to set
	 */
	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	/**
	 * @return the taxLocationId
	 */
	public Integer getTaxLocationId() {
		return taxLocationId;
	}

	/**
	 * @param taxLocationId
	 *            the taxLocationId to set
	 */
	public void setTaxLocationId(Integer taxLocationId) {
		this.taxLocationId = taxLocationId;
	}

}
