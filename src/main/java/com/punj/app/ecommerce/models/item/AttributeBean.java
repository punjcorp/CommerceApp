/**
 * 
 */
package com.punj.app.ecommerce.models.item;

import java.math.BigInteger;

/**
 * @author admin
 *
 */
public class AttributeBean {

	private BigInteger attributeId;
	private String code;
	private String name;
	private String description;
	private String valCode;
	private String valName;
	private String valDescription;
	private Integer valSeqNo;

	/**
	 * @return the attributeId
	 */
	public BigInteger getAttributeId() {
		return attributeId;
	}

	/**
	 * @param attributeId
	 *            the attributeId to set
	 */
	public void setAttributeId(BigInteger attributeId) {
		this.attributeId = attributeId;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the valCode
	 */
	public String getValCode() {
		return valCode;
	}

	/**
	 * @param valCode
	 *            the valCode to set
	 */
	public void setValCode(String valCode) {
		this.valCode = valCode;
	}

	/**
	 * @return the valName
	 */
	public String getValName() {
		return valName;
	}

	/**
	 * @param valName
	 *            the valName to set
	 */
	public void setValName(String valName) {
		this.valName = valName;
	}

	/**
	 * @return the valDescription
	 */
	public String getValDescription() {
		return valDescription;
	}

	/**
	 * @param valDescription
	 *            the valDescription to set
	 */
	public void setValDescription(String valDescription) {
		this.valDescription = valDescription;
	}

	/**
	 * @return the valSeqNo
	 */
	public Integer getValSeqNo() {
		return valSeqNo;
	}

	/**
	 * @param valSeqNo
	 *            the valSeqNo to set
	 */
	public void setValSeqNo(Integer valSeqNo) {
		this.valSeqNo = valSeqNo;
	}

}
