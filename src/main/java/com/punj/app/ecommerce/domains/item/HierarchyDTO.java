/**
 * 
 */
package com.punj.app.ecommerce.domains.item;

import java.util.List;

import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
public class HierarchyDTO {
	private List<Hierarchy> hierarchies;
	private Pager pager;

	/**
	 * @return the hierarchies
	 */
	public List<Hierarchy> getHierarchies() {
		return hierarchies;
	}

	/**
	 * @param hierarchies
	 *            the hierarchies to set
	 */
	public void setHierarchies(List<Hierarchy> hierarchies) {
		this.hierarchies = hierarchies;
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
