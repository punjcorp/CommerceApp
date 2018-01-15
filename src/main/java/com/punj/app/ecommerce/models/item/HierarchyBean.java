/**
 * 
 */
package com.punj.app.ecommerce.models.item;

import java.util.List;

/**
 * @author admin
 *
 */
public class HierarchyBean {

	private Integer hierarchyId;
	private String name;
	private String code;
	private Integer sortOrder;
	private String hiddenFlag;
	private String createdBy;
	private List<HierarchyBean> childHierarchies;

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
	 * @return the childHierarchies
	 */
	public List<HierarchyBean> getChildHierarchies() {
		return childHierarchies;
	}

	/**
	 * @param childHierarchies
	 *            the childHierarchies to set
	 */
	public void setChildHierarchies(List<HierarchyBean> childHierarchies) {
		this.childHierarchies = childHierarchies;
	}

}
