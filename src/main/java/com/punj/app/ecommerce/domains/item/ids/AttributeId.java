package com.punj.app.ecommerce.domains.item.ids;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Embeddable
public class AttributeId implements Serializable {

	private static final long serialVersionUID = -8788715483005378087L;
	
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "attribute_id", updatable = false, nullable = false)
	private BigInteger attrId;
	private String value;

	/**
	 * @return the attributeId
	 */
	public BigInteger getAttributeId() {
		return attrId;
	}

	/**
	 * @param attributeId
	 *            the attributeId to set
	 */
	public void setAttributeId(BigInteger attrId) {
		this.attrId = attrId;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attrId == null) ? 0 : attrId.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		AttributeId other = (AttributeId) obj;
		if (attrId == null) {
			if (other.attrId != null) {
				return false;
			}
		} else if (!attrId.equals(other.attrId)) {
			return false;
		}
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}

}
