/**
 * 
 */
package com.punj.app.ecommerce.models.supplier;

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
public class SupplierLookupDTO {

	private SearchBean searchBean;
	private Integer selectedLocation = 0;
	private Integer supplierId;
	private List<LocationBean> locations;
	private SupplierBean supplier;
	private AccountHeadBean accountHead;
	private List<AccountJournalBean> journals;
	private Pager pager;

	/**
	 * @return the supplier
	 */
	public SupplierBean getSupplier() {
		return supplier;
	}

	/**
	 * @param supplier
	 *            the supplier to set
	 */
	public void setSupplier(SupplierBean supplier) {
		this.supplier = supplier;
	}

	/**
	 * @return the accountHead
	 */
	public AccountHeadBean getAccountHead() {
		return accountHead;
	}

	/**
	 * @param accountHead
	 *            the accountHead to set
	 */
	public void setAccountHead(AccountHeadBean accountHead) {
		this.accountHead = accountHead;
	}

	/**
	 * @return the journals
	 */
	public List<AccountJournalBean> getJournals() {
		return journals;
	}

	/**
	 * @param journals
	 *            the journals to set
	 */
	public void setJournals(List<AccountJournalBean> journals) {
		this.journals = journals;
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

	/**
	 * @return the selectedLocation
	 */
	public Integer getSelectedLocation() {
		return selectedLocation;
	}

	/**
	 * @param selectedLocation
	 *            the selectedLocation to set
	 */
	public void setSelectedLocation(Integer selectedLocation) {
		this.selectedLocation = selectedLocation;
	}

	/**
	 * @return the locations
	 */
	public List<LocationBean> getLocations() {
		return locations;
	}

	/**
	 * @param locations
	 *            the locations to set
	 */
	public void setLocations(List<LocationBean> locations) {
		this.locations = locations;
	}

	/**
	 * @return the supplierId
	 */
	public Integer getSupplierId() {
		return supplierId;
	}

	/**
	 * @param supplierId
	 *            the supplierId to set
	 */
	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

}
