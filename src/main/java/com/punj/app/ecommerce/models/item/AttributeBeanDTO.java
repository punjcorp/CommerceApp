/**
 * 
 */
package com.punj.app.ecommerce.models.item;

import java.math.BigInteger;
import java.util.List;

import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
public class AttributeBeanDTO {

	private List<AttributeBean> attributes;
	private List<BigInteger> attributeIds;

	private Pager pager;

	/**
	 * @return the attributes
	 */
	public List<AttributeBean> getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes
	 *            the attributes to set
	 */
	public void setAttributes(List<AttributeBean> attributes) {
		this.attributes = attributes;
	}

	/**
	 * @return the attributeIds
	 */
	public List<BigInteger> getAttributeIds() {
		return attributeIds;
	}

	/**
	 * @param attributeIds
	 *            the attributeIds to set
	 */
	public void setAttributeIds(List<BigInteger> attributeIds) {
		this.attributeIds = attributeIds;
	}

	/**
	 * @return the pager
	 */
	public Pager getPager() {
		return pager;
	}

	/**
	 * @param pager
	 *            the pager to set
	 */
	public void setPager(Pager pager) {
		this.pager = pager;
	}

}
