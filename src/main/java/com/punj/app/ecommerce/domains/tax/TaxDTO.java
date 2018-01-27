/**
 * 
 */
package com.punj.app.ecommerce.domains.tax;

import java.util.List;

import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
public class TaxDTO {
	List<TaxGroup> taxes;
	Pager pager;

	/**
	 * @return the taxes
	 */
	public List<TaxGroup> getTaxes() {
		return taxes;
	}

	/**
	 * @param taxes
	 *            the taxes to set
	 */
	public void setTaxes(List<TaxGroup> taxes) {
		this.taxes = taxes;
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

}
