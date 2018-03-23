/**
 * 
 */
package com.punj.app.ecommerce.services.common.dtos;

import java.io.Serializable;
import java.util.List;

import com.punj.app.ecommerce.domains.common.ReasonCode;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
public class ReasonDTO implements Serializable {

	private static final long serialVersionUID = 4948054483912995457L;
	List<ReasonCode> reasonCodes;
	private Pager pager;

	/**
	 * @return the reasonCodes
	 */
	public List<ReasonCode> getReasonCodes() {
		return reasonCodes;
	}

	/**
	 * @param reasonCodes
	 *            the reasonCodes to set
	 */
	public void setReasonCodes(List<ReasonCode> reasonCodes) {
		this.reasonCodes = reasonCodes;
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
