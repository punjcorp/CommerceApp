/**
 * 
 */
package com.punj.app.ecommerce.models.lookup;

import com.punj.app.ecommerce.models.common.SearchBean;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
public class TransactionLookupDTO {

	private SearchBean searchBean;
	private Pager pager;
	/**
	 * @return the searchBean
	 */
	public SearchBean getSearchBean() {
		return searchBean;
	}
	/**
	 * @param searchBean the searchBean to set
	 */
	public void setSearchBean(SearchBean searchBean) {
		this.searchBean = searchBean;
	}
	/**
	 * @return the pager
	 */
	public Pager getPager() {
		return pager;
	}
	/**
	 * @param pager the pager to set
	 */
	public void setPager(Pager pager) {
		this.pager = pager;
	}
	
	
	
}
