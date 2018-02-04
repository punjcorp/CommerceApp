/**
 * 
 */
package com.punj.app.ecommerce.models.common;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
public class SearchBean implements Serializable {

	@NotBlank(message = "{commerce.error.string.empty}")
	private String searchText;
	private Integer page;
	private Pager pager;

	/**
	 * @return the searchText
	 */
	public String getSearchText() {
		return searchText;
	}

	/**
	 * @param searchText
	 *            the searchText to set
	 */
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	/**
	 * @return the pager
	 */
	public Pager getPager() {
		return pager;
	}

	/**
	 * @return the page
	 */
	public Integer getPage() {
		return page;
	}

	/**
	 * @param page
	 *            the page to set
	 */
	public void setPage(Integer page) {
		this.page = page;
	}

	/**
	 * @param pager
	 *            the pager to set
	 */
	public void setPager(Pager pager) {
		this.pager = pager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pager == null) ? 0 : pager.hashCode());
		result = prime * result + ((searchText == null) ? 0 : searchText.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		SearchBean other = (SearchBean) obj;
		if (pager == null) {
			if (other.pager != null) {
				return false;
			}
		} else if (!pager.equals(other.pager)) {
			return false;
		}
		if (searchText == null) {
			if (other.searchText != null) {
				return false;
			}
		} else if (!searchText.equals(other.searchText)) {
			return false;
		}
		return true;
	}

}
