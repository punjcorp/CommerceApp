package com.punj.app.ecommerce.domains.transaction.storeopen;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.punj.app.ecommerce.domains.transaction.storeopen.ids.LocationStatusId;

@Entity
@Table(name = "v_loc_status")
public class LocationStatus implements Serializable {

	private static final long serialVersionUID = -2647758730409748169L;

	@EmbeddedId
	private LocationStatusId locationStatusId;

	@Column(name = "store_open_exists")
	private Boolean storeOpenExists;

	@Column(name = "store_close_exists")
	private Boolean storeCloseExists;

	@Column(name = "sale_exists")
	private Boolean SaleExists;

	/**
	 * @return the locationStatusId
	 */
	public LocationStatusId getLocationStatusId() {
		return locationStatusId;
	}

	/**
	 * @param locationStatusId
	 *            the locationStatusId to set
	 */
	public void setLocationStatusId(LocationStatusId locationStatusId) {
		this.locationStatusId = locationStatusId;
	}

	/**
	 * @return the storeOpenExists
	 */
	public Boolean getStoreOpenExists() {
		return storeOpenExists;
	}

	/**
	 * @param storeOpenExists
	 *            the storeOpenExists to set
	 */
	public void setStoreOpenExists(Boolean storeOpenExists) {
		this.storeOpenExists = storeOpenExists;
	}

	/**
	 * @return the saleExists
	 */
	public Boolean getSaleExists() {
		return SaleExists;
	}

	/**
	 * @param saleExists
	 *            the saleExists to set
	 */
	public void setSaleExists(Boolean saleExists) {
		SaleExists = saleExists;
	}

	/**
	 * @return the storeCloseExists
	 */
	public Boolean getStoreCloseExists() {
		return storeCloseExists;
	}

	/**
	 * @param storeCloseExists
	 *            the storeCloseExists to set
	 */
	public void setStoreCloseExists(Boolean storeCloseExists) {
		this.storeCloseExists = storeCloseExists;
	}

}
