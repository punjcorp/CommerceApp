package com.punj.app.ecommerce.domains.item;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.punj.app.ecommerce.domains.item.ids.SKUCounterId;

@Entity
@Table(name = "sku_generator")
public class SKUCounter implements Serializable {

	@EmbeddedId
	private SKUCounterId skuCounterId;
	private String status;

	public SKUCounter() {
	}

	public SKUCounter(SKUCounterId skuCounterId, String status) {
		this.skuCounterId = skuCounterId;
		this.status = status;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the skuCounterId
	 */
	public SKUCounterId getSkuCounterId() {
		return skuCounterId;
	}

	/**
	 * @param skuCounterId
	 *            the skuCounterId to set
	 */
	public void setSkuCounterId(SKUCounterId skuCounterId) {
		this.skuCounterId = skuCounterId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new StringBuffer().append(skuCounterId.getStyleCounter().getStyleId())
				.append(String.format("%02d", skuCounterId.getColor()))
				.append(String.format("%02d", skuCounterId.getSize())).toString();
	}

}
