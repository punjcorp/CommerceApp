package com.punj.app.ecommerce.domains.transaction.ids;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SaleLineItemId implements Serializable {

	private static final long serialVersionUID = 3947002207687612916L;

	@Column(name = "location_id")
	private Integer locationId;

	@Column(name = "business_date")
	private LocalDateTime businessDate;

	@Column(name = "register")
	private Integer register;

	@Column(name = "txn_no")
	private Integer transactionSeq;

	@Column(name = "seq_no")
	private String lineItemSeq;

	@Column(name = "item_id")
	private BigInteger itemId;

	/**
	 * @return the locationId
	 */
	public Integer getLocationId() {
		return locationId;
	}

	/**
	 * @param locationId
	 *            the locationId to set
	 */
	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	/**
	 * @return the businessDate
	 */
	public LocalDateTime getBusinessDate() {
		return businessDate;
	}

	/**
	 * @param businessDate
	 *            the businessDate to set
	 */
	public void setBusinessDate(LocalDateTime businessDate) {
		this.businessDate = businessDate;
	}

	/**
	 * @return the register
	 */
	public Integer getRegister() {
		return register;
	}

	/**
	 * @param register
	 *            the register to set
	 */
	public void setRegister(Integer register) {
		this.register = register;
	}

	/**
	 * @return the transactionSeq
	 */
	public Integer getTransactionSeq() {
		return transactionSeq;
	}

	/**
	 * @param transactionSeq
	 *            the transactionSeq to set
	 */
	public void setTransactionSeq(Integer transactionSeq) {
		this.transactionSeq = transactionSeq;
	}

	/**
	 * @return the lineItemSeq
	 */
	public String getLineItemSeq() {
		return lineItemSeq;
	}

	/**
	 * @param lineItemSeq
	 *            the lineItemSeq to set
	 */
	public void setLineItemSeq(String lineItemSeq) {
		this.lineItemSeq = lineItemSeq;
	}

	/**
	 * @return the itemId
	 */
	public BigInteger getItemId() {
		return itemId;
	}

	/**
	 * @param itemId
	 *            the itemId to set
	 */
	public void setItemId(BigInteger itemId) {
		this.itemId = itemId;
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
		result = prime * result + ((businessDate == null) ? 0 : businessDate.hashCode());
		result = prime * result + ((itemId == null) ? 0 : itemId.hashCode());
		result = prime * result + ((lineItemSeq == null) ? 0 : lineItemSeq.hashCode());
		result = prime * result + ((locationId == null) ? 0 : locationId.hashCode());
		result = prime * result + ((register == null) ? 0 : register.hashCode());
		result = prime * result + ((transactionSeq == null) ? 0 : transactionSeq.hashCode());
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
		if (businessDate == null) {
			if (other.businessDate != null) {
				return false;
			}
		} else if (!businessDate.equals(other.businessDate)) {
			return false;
		}
		if (itemId == null) {
			if (other.itemId != null) {
				return false;
			}
		} else if (!itemId.equals(other.itemId)) {
			return false;
		}
		if (lineItemSeq == null) {
			if (other.lineItemSeq != null) {
				return false;
			}
		} else if (!lineItemSeq.equals(other.lineItemSeq)) {
			return false;
		}
		if (locationId == null) {
			if (other.locationId != null) {
				return false;
			}
		} else if (!locationId.equals(other.locationId)) {
			return false;
		}
		if (register == null) {
			if (other.register != null) {
				return false;
			}
		} else if (!register.equals(other.register)) {
			return false;
		}
		if (transactionSeq == null) {
			if (other.transactionSeq != null) {
				return false;
			}
		} else if (!transactionSeq.equals(other.transactionSeq)) {
			return false;
		}
		return true;
	}

}
