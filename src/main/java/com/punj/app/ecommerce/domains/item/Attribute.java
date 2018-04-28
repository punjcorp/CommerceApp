package com.punj.app.ecommerce.domains.item;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

@Entity
@Indexed
@Table(name = "attribute_master")
public class Attribute implements Serializable {
	private static final long serialVersionUID = -171529527192692470L;

	@Id
	@DocumentId
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "attribute_id", updatable = false, nullable = false)
	private BigInteger attributeId;

	@Field
	@Column(name = "attr_code")
	private String code;

	@Field
	@Column(name = "attr_name")
	private String name;

	@Field
	@Column(name = "attr_description")
	private String description;

	@Field
	@Column(name = "value_code")
	private String valCode;

	@Field
	@Column(name = "value_name")
	private String valName;

	@Field
	@Column(name = "value_description")
	private String valDesc;

	@Column(name = "value_seq_no")
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
	 * @return the valDesc
	 */
	public String getValDesc() {
		return valDesc;
	}

	/**
	 * @param valDesc
	 *            the valDesc to set
	 */
	public void setValDesc(String valDesc) {
		this.valDesc = valDesc;
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
