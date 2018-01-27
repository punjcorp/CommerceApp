package com.punj.app.ecommerce.domains.transaction.ids;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.punj.app.ecommerce.domains.item.Item;
import com.punj.app.ecommerce.domains.transaction.TransactionLineItem;

@Embeddable
public class SaleLineItemId implements Serializable {

	private static final long serialVersionUID = 3947002207687612916L;

	@MapsId("transactionLineItemId")
	@JoinColumns({ @JoinColumn(name = "location_id", referencedColumnName = "location_id"),
			@JoinColumn(name = "business_date", referencedColumnName = "business_date"),
			@JoinColumn(name = "register", referencedColumnName = "register"),
			@JoinColumn(name = "txn_no", referencedColumnName = "txn_no"),
			@JoinColumn(name = "seq_no", referencedColumnName = "seq_no") })
	@OneToOne
	private TransactionLineItem txnLineItem;

	@ManyToOne
	@JoinColumn(name = "item_id")
	private Item item;

	/**
	 * @return the txnLineItem
	 */
	public TransactionLineItem getTxnLineItem() {
		return txnLineItem;
	}

	/**
	 * @param txnLineItem
	 *            the txnLineItem to set
	 */
	public void setTxnLineItem(TransactionLineItem txnLineItem) {
		this.txnLineItem = txnLineItem;
	}

	/**
	 * @return the item
	 */
	public Item getItem() {
		return item;
	}

	/**
	 * @param item
	 *            the item to set
	 */
	public void setItem(Item item) {
		this.item = item;
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
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		result = prime * result + ((txnLineItem == null) ? 0 : txnLineItem.hashCode());
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
		SaleLineItemId other = (SaleLineItemId) obj;
		if (item == null) {
			if (other.item != null) {
				return false;
			}
		} else if (!item.equals(other.item)) {
			return false;
		}
		if (txnLineItem == null) {
			if (other.txnLineItem != null) {
				return false;
			}
		} else if (!txnLineItem.equals(other.txnLineItem)) {
			return false;
		}
		return true;
	}

}
