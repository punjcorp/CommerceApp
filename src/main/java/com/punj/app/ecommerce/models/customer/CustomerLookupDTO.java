/**
 * 
 */
package com.punj.app.ecommerce.models.customer;

import java.util.List;

import com.punj.app.ecommerce.models.common.LocationBean;
import com.punj.app.ecommerce.models.common.SearchBean;
import com.punj.app.ecommerce.models.financials.AccountHeadBean;
import com.punj.app.ecommerce.models.financials.AccountJournalBean;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
public class CustomerLookupDTO {

	private SearchBean searchBean;
	private Integer selectedLocation = 0;
	private Integer customerId;
	private List<LocationBean> locations;
	private CustomerBean customer;
	private AccountHeadBean accountHead;
	private List<AccountJournalBean> journals;
	private Pager pager;

	public SearchBean getSearchBean() {
		return searchBean;
	}

	public void setSearchBean(SearchBean searchBean) {
		this.searchBean = searchBean;
	}

	public Integer getSelectedLocation() {
		return selectedLocation;
	}

	public void setSelectedLocation(Integer selectedLocation) {
		this.selectedLocation = selectedLocation;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public List<LocationBean> getLocations() {
		return locations;
	}

	public void setLocations(List<LocationBean> locations) {
		this.locations = locations;
	}

	public CustomerBean getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerBean customer) {
		this.customer = customer;
	}

	public AccountHeadBean getAccountHead() {
		return accountHead;
	}

	public void setAccountHead(AccountHeadBean accountHead) {
		this.accountHead = accountHead;
	}

	public List<AccountJournalBean> getJournals() {
		return journals;
	}

	public void setJournals(List<AccountJournalBean> journals) {
		this.journals = journals;
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

}
