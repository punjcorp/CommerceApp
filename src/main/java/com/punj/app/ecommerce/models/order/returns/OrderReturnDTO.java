/**
 * 
 */
package com.punj.app.ecommerce.models.order.returns;

import java.util.List;

import javax.validation.Valid;

import com.punj.app.ecommerce.domains.common.ReasonCode;
import com.punj.app.ecommerce.models.common.LocationBean;

/**
 * @author admin
 *
 */
public class OrderReturnDTO {

	@Valid
	private OrderReturnBean orderReturn;
	private List<LocationBean> locations;
	private List<ReasonCode> reasonCodes;

	/**
	 * @return the orderReturn
	 */
	public OrderReturnBean getOrderReturn() {
		return orderReturn;
	}

	/**
	 * @param orderReturn
	 *            the orderReturn to set
	 */
	public void setOrderReturn(OrderReturnBean orderReturn) {
		this.orderReturn = orderReturn;
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

}
