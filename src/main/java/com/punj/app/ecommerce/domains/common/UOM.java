/**
 * 
 */
package com.punj.app.ecommerce.domains.common;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author admin
 *
 */
@Entity
@Table(name = "uom_master")
public class UOM implements Serializable {

	private static final long serialVersionUID = -7514835685346151345L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "uom_id", updatable = false, nullable = false)
	private Integer uomId;

	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "created_date")
	private LocalDateTime createdDate;

	private String type;
	private String code;
	private String description;
	private String name;
	@Column(name = "formula_to_parent_uom")
	private String formulaToParent;
	@Column(name = "is_primary")
	private Boolean isPrimary;

	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "parent_uom_id")
	private UOM parentUOM;

	/**
	 * @return the uomId
	 */
	public Integer getUomId() {
		return uomId;
	}

	/**
	 * @param uomId
	 *            the uomId to set
	 */
	public void setUomId(Integer uomId) {
		this.uomId = uomId;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the createdDate
	 */
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate
	 *            the createdDate to set
	 */
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
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
	 * @return the formulaToParent
	 */
	public String getFormulaToParent() {
		return formulaToParent;
	}

	/**
	 * @param formulaToParent
	 *            the formulaToParent to set
	 */
	public void setFormulaToParent(String formulaToParent) {
		this.formulaToParent = formulaToParent;
	}

	/**
	 * @return the isPrimary
	 */
	public Boolean getIsPrimary() {
		return isPrimary;
	}

	/**
	 * @param isPrimary
	 *            the isPrimary to set
	 */
	public void setIsPrimary(Boolean isPrimary) {
		this.isPrimary = isPrimary;
	}

	/**
	 * @return the parentUOM
	 */
	public UOM getParentUOM() {
		return parentUOM;
	}

	/**
	 * @param parentUOM
	 *            the parentUOM to set
	 */
	public void setParentUOM(UOM parentUOM) {
		this.parentUOM = parentUOM;
	}

}
