/**
 * 
 */
package com.punj.app.ecommerce.models.setup;

import java.util.List;

import com.punj.app.ecommerce.domains.common.State;
import com.punj.app.ecommerce.models.account.AccountBean;
import com.punj.app.ecommerce.models.common.LocationBean;
import com.punj.app.ecommerce.models.tender.TenderBean;

/**
 * @author admin
 *
 */
public class InitialSetupBean {

	private LocationBean location;
	private List<TenderBean> tenders;
	private List<State> states;
	private AccountBean account;
	private String[] selectedTenders;

	/**
	 * @return the location
	 */
	public LocationBean getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(LocationBean location) {
		this.location = location;
	}

	/**
	 * @return the tenders
	 */
	public List<TenderBean> getTenders() {
		return tenders;
	}

	/**
	 * @param tenders
	 *            the tenders to set
	 */
	public void setTenders(List<TenderBean> tenders) {
		this.tenders = tenders;
	}

	/**
	 * @return the states
	 */
	public List<State> getStates() {
		return states;
	}

	/**
	 * @param states
	 *            the states to set
	 */
	public void setStates(List<State> states) {
		this.states = states;
	}

	/**
	 * @return the account
	 */
	public AccountBean getAccount() {
		return account;
	}

	/**
	 * @param account
	 *            the account to set
	 */
	public void setAccount(AccountBean account) {
		this.account = account;
	}

	/**
	 * @return the selectedTenders
	 */
	public String[] getSelectedTenders() {
		return selectedTenders;
	}

	/**
	 * @param selectedTenders
	 *            the selectedTenders to set
	 */
	public void setSelectedTenders(String[] selectedTenders) {
		this.selectedTenders = selectedTenders;
	}

}
