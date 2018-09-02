package com.punj.app.ecommerce.domains.transaction;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.punj.app.ecommerce.domains.transaction.ids.TransactionCustomerId;

@Entity
@Table(name = "txn_customer")
public class TransactionCustomer implements Serializable {

	private static final long serialVersionUID = -9119412229360860930L;

	@EmbeddedId
	private TransactionCustomerId transactionCustomerId;

	@Column(name = "billing_address_id")
	private BigInteger billingAddressId;
	@Column(name = "shipping_address_id")
	private BigInteger shippingAddressId;

	/**
	 * @return the transactionCustomerId
	 */
	public TransactionCustomerId getTransactionCustomerId() {
		return transactionCustomerId;
	}

	/**
	 * @param transactionCustomerId
	 *            the transactionCustomerId to set
	 */
	public void setTransactionCustomerId(TransactionCustomerId transactionCustomerId) {
		this.transactionCustomerId = transactionCustomerId;
	}

	/**
	 * @return the billingAddressId
	 */
	public BigInteger getBillingAddressId() {
		return billingAddressId;
	}

	/**
	 * @param billingAddressId
	 *            the billingAddressId to set
	 */
	public void setBillingAddressId(BigInteger billingAddressId) {
		this.billingAddressId = billingAddressId;
	}

	/**
	 * @return the shippingAddressId
	 */
	public BigInteger getShippingAddressId() {
		return shippingAddressId;
	}

	/**
	 * @param shippingAddressId
	 *            the shippingAddressId to set
	 */
	public void setShippingAddressId(BigInteger shippingAddressId) {
		this.shippingAddressId = shippingAddressId;
	}

}
