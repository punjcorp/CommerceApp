/**
 * 
 */
package com.punj.app.ecommerce.models.item;

import java.util.List;

import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
public class HierarchyBeanDTO {

	private List<HierarchyBean> hierarchies;
	private List<Integer> hierarchyIds;

	private Pager pager;

	/**
	 * @return the hierarchies
	 */
	public List<HierarchyBean> getHierarchies() {
		return hierarchies;
	}

	/**
	 * @param hierarchies
	 *            the hierarchies to set
	 */
	public void setHierarchies(List<HierarchyBean> hierarchies) {
		this.hierarchies = hierarchies;
	}

	/**
	 * @return the hierarchyIds
	 */
	public List<Integer> getHierarchyIds() {
		return hierarchyIds;
	}

	/**
	 * @param hierarchyIds
	 *            the hierarchyIds to set
	 */
	public void setHierarchyIds(List<Integer> hierarchyIds) {
		this.hierarchyIds = hierarchyIds;
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
