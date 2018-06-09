/**
 * 
 */
package com.punj.app.ecommerce.models.financials;

import com.punj.app.ecommerce.models.common.LocationBean;

/**
 * @author admin
 *
 */
public class AJReportingBean {

	private AccountHeadBean accountBean;
	private AccountJournalBean journalBean;
	private LocationBean locationBean;
	/**
	 * @return the accountBean
	 */
	public AccountHeadBean getAccountBean() {
		return accountBean;
	}
	/**
	 * @param accountBean the accountBean to set
	 */
	public void setAccountBean(AccountHeadBean accountBean) {
		this.accountBean = accountBean;
	}
	/**
	 * @return the journalBean
	 */
	public AccountJournalBean getJournalBean() {
		return journalBean;
	}
	/**
	 * @param journalBean the journalBean to set
	 */
	public void setJournalBean(AccountJournalBean journalBean) {
		this.journalBean = journalBean;
	}
	/**
	 * @return the locationBean
	 */
	public LocationBean getLocationBean() {
		return locationBean;
	}
	/**
	 * @param locationBean the locationBean to set
	 */
	public void setLocationBean(LocationBean locationBean) {
		this.locationBean = locationBean;
	}

	
	
}
