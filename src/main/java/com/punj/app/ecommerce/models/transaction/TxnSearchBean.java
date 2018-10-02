/**
 * 
 */
package com.punj.app.ecommerce.models.transaction;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

/**
 * @author admin
 *
 */
public class TxnSearchBean {

	@NotNull(message="{commerce.error.date.empty}")
	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;
	private String txnType;

	/**
	 * @return the startDateTime
	 */
	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}

	/**
	 * @param startDateTime
	 *            the startDateTime to set
	 */
	public void setStartDateTime(LocalDateTime startDateTime) {
		this.startDateTime = startDateTime;
	}

	/**
	 * @return the endDateTime
	 */
	public LocalDateTime getEndDateTime() {
		return endDateTime;
	}

	/**
	 * @param endDateTime
	 *            the endDateTime to set
	 */
	public void setEndDateTime(LocalDateTime endDateTime) {
		this.endDateTime = endDateTime;
	}

	/**
	 * @return the txnType
	 */
	public String getTxnType() {
		return txnType;
	}

	/**
	 * @param txnType
	 *            the txnType to set
	 */
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

}
