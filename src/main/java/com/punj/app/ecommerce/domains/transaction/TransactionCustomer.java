package com.punj.app.ecommerce.domains.transaction;

import java.io.Serializable;

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

}
