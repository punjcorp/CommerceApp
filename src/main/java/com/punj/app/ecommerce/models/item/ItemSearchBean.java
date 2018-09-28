/**
 * 
 */
package com.punj.app.ecommerce.models.item;

import java.io.Serializable;
import java.math.BigInteger;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
public class ItemSearchBean implements Serializable {

	@NotBlank(message = "{commerce.error.string.empty}")
	private String searchText;
	private Integer page;
	@NotNull(message = "{commerce.error.string.empty}")
	private BigInteger itemId;
	@NotBlank(message = "{commerce.error.option.empty}")
	private String locationId;
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

	/**
	 * @return the locationId
	 */
	public String getLocationId() {
		return locationId;
	}

	/**
	 * @param locationId
	 *            the locationId to set
	 */
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	/**
	 * @return the itemId
	 */
	public BigInteger getItemId() {
		return itemId;
	}

	/**
	 * @param itemId
	 *            the itemId to set
	 */
	public void setItemId(BigInteger itemId) {
		this.itemId = itemId;
	}

}
