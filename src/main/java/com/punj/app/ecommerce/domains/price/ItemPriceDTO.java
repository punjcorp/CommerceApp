package com.punj.app.ecommerce.domains.price;

import java.io.Serializable;
import java.util.List;

import com.punj.app.ecommerce.utils.Pager;

public class ItemPriceDTO implements Serializable {

	private static final long serialVersionUID = -4708915344882753505L;

	private List<ItemPrice> itemPriceList;
	Pager pager;

	/**
	 * @return the itemPriceList
	 */
	public List<ItemPrice> getItemPriceList() {
		return itemPriceList;
	}

	/**
	 * @param itemPriceList
	 *            the itemPriceList to set
	 */
	public void setItemPriceList(List<ItemPrice> itemPriceList) {
		this.itemPriceList = itemPriceList;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((itemPriceList == null) ? 0 : itemPriceList.hashCode());
		result = prime * result + ((pager == null) ? 0 : pager.hashCode());
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
		ItemPriceDTO other = (ItemPriceDTO) obj;
		if (itemPriceList == null) {
			if (other.itemPriceList != null) {
				return false;
			}
		} else if (!itemPriceList.equals(other.itemPriceList)) {
			return false;
		}
		if (pager == null) {
			if (other.pager != null) {
				return false;
			}
		} else if (!pager.equals(other.pager)) {
			return false;
		}
		return true;
	}

}
