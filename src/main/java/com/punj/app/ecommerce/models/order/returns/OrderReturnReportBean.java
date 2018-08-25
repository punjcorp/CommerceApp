/**
 * 
 */
package com.punj.app.ecommerce.models.order.returns;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import com.punj.app.ecommerce.models.common.AddressBean;
import com.punj.app.ecommerce.models.common.LocationBean;
import com.punj.app.ecommerce.models.order.OrderBean;
import com.punj.app.ecommerce.models.supplier.SupplierBean;

/**
 * @author admin
 *
 */
public class OrderReturnReportBean implements Serializable {

	private static final long serialVersionUID = 4733611538903252369L;

	private BigInteger orderReturnId;
	private String username;
	private List<OrderReturnBean> orderReturn;
	private List<OrderBean> order;
	private List<AddressBean> delieveryLocation;
	private LocationBean locationDetails;
	private List<SupplierBean> supplier;
	/**
	 * @return the orderReturnId
	 */
	public BigInteger getOrderReturnId() {
		return orderReturnId;
	}
	/**
	 * @param orderReturnId the orderReturnId to set
	 */
	public void setOrderReturnId(BigInteger orderReturnId) {
		this.orderReturnId = orderReturnId;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the orderReturn
	 */
	public List<OrderReturnBean> getOrderReturn() {
		return orderReturn;
	}
	/**
	 * @param orderReturn the orderReturn to set
	 */
	public void setOrderReturn(List<OrderReturnBean> orderReturn) {
		this.orderReturn = orderReturn;
	}
	/**
	 * @return the order
	 */
	public List<OrderBean> getOrder() {
		return order;
	}
	/**
	 * @param order the order to set
	 */
	public void setOrder(List<OrderBean> order) {
		this.order = order;
	}
	/**
	 * @return the delieveryLocation
	 */
	public List<AddressBean> getDelieveryLocation() {
		return delieveryLocation;
	}
	/**
	 * @param delieveryLocation the delieveryLocation to set
	 */
	public void setDelieveryLocation(List<AddressBean> delieveryLocation) {
		this.delieveryLocation = delieveryLocation;
	}
	/**
	 * @return the locationDetails
	 */
	public LocationBean getLocationDetails() {
		return locationDetails;
	}
	/**
	 * @param locationDetails the locationDetails to set
	 */
	public void setLocationDetails(LocationBean locationDetails) {
		this.locationDetails = locationDetails;
	}
	/**
	 * @return the supplier
	 */
	public List<SupplierBean> getSupplier() {
		return supplier;
	}
	/**
	 * @param supplier the supplier to set
	 */
	public void setSupplier(List<SupplierBean> supplier) {
		this.supplier = supplier;
	}

		
}
