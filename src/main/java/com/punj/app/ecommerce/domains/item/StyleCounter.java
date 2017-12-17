package com.punj.app.ecommerce.domains.item;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "style_generator")
public class StyleCounter {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "style_id", updatable = false, nullable = false)
	private BigInteger styleId;
	private String status;

	public StyleCounter() {
	}

	public StyleCounter(String status) {
		this.status = status;
	}

	/**
	 * @return the styleId
	 */
	public BigInteger getStyleId() {
		return styleId;
	}

	/**
	 * @param styleId
	 *            the styleId to set
	 */
	public void setStyleId(BigInteger styleId) {
		this.styleId = styleId;
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

}
