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
public class SKUDtlDTO {
	private List<LocSKUDetails> skus;
	private Pager pager;
	/**
	 * @return the skus
	 */
	public List<LocSKUDetails> getSkus() {
		return skus;
	}
	/**
	 * @param skus the skus to set
	 */
	public void setSkus(List<LocSKUDetails> skus) {
		this.skus = skus;
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
