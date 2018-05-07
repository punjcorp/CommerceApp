package com.punj.app.ecommerce.domains.item.ids;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import com.punj.app.ecommerce.domains.item.StyleCounter;

@Embeddable
public class SKUCounterId implements Serializable {

	private static final long serialVersionUID = -3763829412400209829L;
	@Column(name = "sku_id")
	private BigInteger skuId;

	@Column(name = "style_id")
	private BigInteger styleId;

	/**
	 * @return the skuId
	 */
	public BigInteger getSkuId() {
		return skuId;
	}

	/**
	 * @param skuId the skuId to set
	 */
	public void setSkuId(BigInteger skuId) {
		this.skuId = skuId;
	}

	/**
	 * @return the styleId
	 */
	public BigInteger getStyleId() {
		return styleId;
	}

	/**
	 * @param styleId the styleId to set
	 */
	public void setStyleId(BigInteger styleId) {
		this.styleId = styleId;
	}

	

}
