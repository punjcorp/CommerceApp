package com.punj.app.ecommerce.domains.item;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "item_hierarchy")
public class Hierarchy implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "hierarchy_id", updatable = false, nullable = false)
	private Integer hierarchyId;
	@Column(name = "level_code")
	private String code;
	@Column(name = "description")
	private String description;
	@Column(name = "sort_order")
	private Integer sortOrder;
	@Column(name = "hidden_flag")
	private String hiddenFlag;

	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "created_date")
	private LocalDateTime createdDate;

	@OneToMany(mappedBy = "parentHierarchy")
	private List<Hierarchy> childHierarchyList;

	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "parent_id")
	private Hierarchy parentHierarchy;

	public Hierarchy() {
	}

	public Hierarchy(String code, String description, Integer sortOrder, String hiddenFlag, String createdBy,
			LocalDateTime createdDate) {
		this.code = code;
		this.description = description;
		this.sortOrder = sortOrder;
		this.hiddenFlag = hiddenFlag;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
	}

	/**
	 * @return the hierarchyId
	 */
	public Integer getHierarchyId() {
		return hierarchyId;
	}

	/**
	 * @param hierarchyId
	 *            the hierarchyId to set
	 */
	public void setHierarchyId(Integer hierarchyId) {
		this.hierarchyId = hierarchyId;
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
	 * @return the sortOrder
	 */
	public Integer getSortOrder() {
		return sortOrder;
	}

	/**
	 * @param sortOrder
	 *            the sortOrder to set
	 */
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	/**
	 * @return the hiddenFlag
	 */
	public String getHiddenFlag() {
		return hiddenFlag;
	}

	/**
	 * @param hiddenFlag
	 *            the hiddenFlag to set
	 */
	public void setHiddenFlag(String hiddenFlag) {
		this.hiddenFlag = hiddenFlag;
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
	 * @return the itemHierarchyList
	 */
	public List<Hierarchy> getItemHierarchyList() {
		return childHierarchyList;
	}

	/**
	 * @param itemHierarchyList
	 *            the itemHierarchyList to set
	 */
	public void setItemHierarchyList(List<Hierarchy> childHierarchyList) {
		this.childHierarchyList = childHierarchyList;
	}

	/**
	 * @return the itemHierarchy
	 */
	public Hierarchy getItemHierarchy() {
		return parentHierarchy;
	}

	/**
	 * @param itemHierarchy
	 *            the itemHierarchy to set
	 */
	public void setItemHierarchy(Hierarchy parentHierarchy) {
		this.parentHierarchy = parentHierarchy;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hierarchyId == null) ? 0 : hierarchyId.hashCode());
		result = prime * result + ((parentHierarchy == null) ? 0 : parentHierarchy.hashCode());
		return result;
	}

	/* (non-Javadoc)
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
		Hierarchy other = (Hierarchy) obj;
		if (hierarchyId == null) {
			if (other.hierarchyId != null) {
				return false;
			}
		} else if (!hierarchyId.equals(other.hierarchyId)) {
			return false;
		}
		if (parentHierarchy == null) {
			if (other.parentHierarchy != null) {
				return false;
			}
		} else if (!parentHierarchy.equals(other.parentHierarchy)) {
			return false;
		}
		return true;
	}

	
	
}
