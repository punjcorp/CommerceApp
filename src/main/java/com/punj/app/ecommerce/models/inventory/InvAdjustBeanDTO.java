/**
 * 
 */
package com.punj.app.ecommerce.models.inventory;

import java.util.List;

import com.punj.app.ecommerce.models.common.SearchBean;

/**
 * @author admin
 *
 */
public class InvAdjustBeanDTO {

	private List<InvAdjustBean> invAdjusts;
	private List<String> invAdjustIds;
	private SearchBean searchBean;

	/**
	 * @return the invAdjusts
	 */
	public List<InvAdjustBean> getInvAdjusts() {
		return invAdjusts;
	}

	/**
	 * @param invAdjusts
	 *            the invAdjusts to set
	 */
	public void setInvAdjusts(List<InvAdjustBean> invAdjusts) {
		this.invAdjusts = invAdjusts;
	}

	/**
	 * @return the invAdjustIds
	 */
	public List<String> getInvAdjustIds() {
		return invAdjustIds;
	}

	/**
	 * @param invAdjustIds
	 *            the invAdjustIds to set
	 */
	public void setInvAdjustIds(List<String> invAdjustIds) {
		this.invAdjustIds = invAdjustIds;
	}

	/**
	 * @return the searchBean
	 */
	public SearchBean getSearchBean() {
		return searchBean;
	}

	/**
	 * @param searchBean
	 *            the searchBean to set
	 */
	public void setSearchBean(SearchBean searchBean) {
		this.searchBean = searchBean;
	}

}
