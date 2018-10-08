/**
 * 
 */
package com.punj.app.ecommerce.domains.tax;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.punj.app.ecommerce.domains.tax.ids.TaxLocationMapId;

/**
 * @author admin
 *
 */
@Entity
@Table(name = "tax_location_mapping")
public class TaxLocationMap implements Serializable {

	@EmbeddedId
	private TaxLocationMapId taxLocationMapId;

	/**
	 * @return the taxLocationMapId
	 */
	public TaxLocationMapId getTaxLocationMapId() {
		return taxLocationMapId;
	}

	/**
	 * @param taxLocationMapId
	 *            the taxLocationMapId to set
	 */
	public void setTaxLocationMapId(TaxLocationMapId taxLocationMapId) {
		this.taxLocationMapId = taxLocationMapId;
	}

}
