/**
 * 
 */
package com.punj.app.ecommerce.services.dtos;

import java.io.Serializable;
import java.util.List;

import com.punj.app.ecommerce.services.dtos.tender.TenderDTO;
import com.punj.app.ecommerce.services.dtos.transaction.TransactionIdDTO;

/**
 * @author admin
 *
 */
public class StoreOpenTransaction implements Serializable {

	private static final long serialVersionUID = 3737692312819734561L;

	private TransactionIdDTO transactionId;
	private List<TenderDTO> tenders;

	/**
	 * @return the transactionId
	 */
	public TransactionIdDTO getTransactionId() {
		return transactionId;
	}

	/**
	 * @param transactionId
	 *            the transactionId to set
	 */
	public void setTransactionId(TransactionIdDTO transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * @return the tenders
	 */
	public List<TenderDTO> getTenders() {
		return tenders;
	}

	/**
	 * @param tenders
	 *            the tenders to set
	 */
	public void setTenders(List<TenderDTO> tenders) {
		this.tenders = tenders;
	}

}
