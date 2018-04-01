/**
 * 
 */
package com.punj.app.ecommerce.models.order;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import com.punj.app.ecommerce.models.common.LocationBean;
import com.punj.app.ecommerce.models.supplier.SupplierBean;

/**
 * @author admin
 *
 */
public class OrderReportBean implements Serializable {

	private static final long serialVersionUID = 2222042071826466527L;

	private BigInteger orderId;
	private String username;
	private List<OrderBean> order;
	private List<OrderItemBean> orderItems;
	private List<LocationBean> delieveryLocation;
	private LocationBean locationDetails;
	private List<SupplierBean> supplier;

	/**
	 * @return the orderId
	 */
	public BigInteger getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId
	 *            the orderId to set
	 */
	public void setOrderId(BigInteger orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the order
	 */
	public List<OrderBean> getOrder() {
		return order;
	}

	/**
	 * @param order
	 *            the order to set
	 */
	public void setOrder(List<OrderBean> order) {
		this.order = order;
	}

	/**
	 * @return the orderItems
	 */
	public List<OrderItemBean> getOrderItems() {
		return orderItems;
	}

	/**
	 * @param orderItems
	 *            the orderItems to set
	 */
	public void setOrderItems(List<OrderItemBean> orderItems) {
		this.orderItems = orderItems;
	}

	/**
	 * @return the delieveryLocation
	 */
	public List<LocationBean> getDelieveryLocation() {
		return delieveryLocation;
	}

	/**
	 * @param delieveryLocation
	 *            the delieveryLocation to set
	 */
	public void setDelieveryLocation(List<LocationBean> delieveryLocation) {
		this.delieveryLocation = delieveryLocation;
	}

	/**
	 * @return the locationDetails
	 */
	public LocationBean getLocationDetails() {
		return locationDetails;
	}

	/**
	 * @param locationDetails
	 *            the locationDetails to set
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
	 * @param supplier
	 *            the supplier to set
	 */
	public void setSupplier(List<SupplierBean> supplier) {
		this.supplier = supplier;
	}

}
