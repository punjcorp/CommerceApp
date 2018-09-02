/**
 * 
 */
package com.punj.app.ecommerce.models.customer.or;

import java.math.BigInteger;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author admin
 *
 */
public class CustomerORBean {

	private BigInteger customerORId;
	private String customerOrderNo;
	@JsonFormat(pattern = "dd-MMM-yy HH:mm:ss")
	private LocalDateTime orderDate;

	/**
	 * @return the customerORId
	 */
	public BigInteger getCustomerORId() {
		return customerORId;
	}

	/**
	 * @param customerORId
	 *            the customerORId to set
	 */
	public void setCustomerORId(BigInteger customerORId) {
		this.customerORId = customerORId;
	}

	/**
	 * @return the customerOrderNo
	 */
	public String getCustomerOrderNo() {
		return customerOrderNo;
	}

	/**
	 * @param customerOrderNo
	 *            the customerOrderNo to set
	 */
	public void setCustomerOrderNo(String customerOrderNo) {
		this.customerOrderNo = customerOrderNo;
	}

	/**
	 * @return the orderDate
	 */
	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	/**
	 * @param orderDate
	 *            the orderDate to set
	 */
	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

}
