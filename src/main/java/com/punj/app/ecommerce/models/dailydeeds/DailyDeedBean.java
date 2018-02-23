/**
 * 
 */
package com.punj.app.ecommerce.models.dailydeeds;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.money.MonetaryAmount;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.punj.app.ecommerce.models.tender.TenderBean;
import com.punj.app.ecommerce.utils.Utils;

/**
 * @author admin
 *
 */
public class DailyDeedBean {

	@NotNull(message = "{commerce.error.location.empty}")
	private Integer locationId;
	@NotNull(message = "{commerce.error.date.empty}")
	private LocalDateTime businessDate;
	private Integer register;
	private Integer txnNo;
	private Integer selectedTenderId;
	private Integer selectedDenominationId;

	@Valid
	private List<TenderBean> tenders;

	private List<MonetaryAmount> denominationList;

	public DailyDeedBean() {
		tenders = new ArrayList<>();
		tenders.add(new TenderBean());
		denominationList = Utils.getDenominations();
	}

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
	 * @return the txnNo
	 */
	public Integer getTxnNo() {
		return txnNo;
	}

	/**
	 * @param txnNo
	 *            the txnNo to set
	 */
	public void setTxnNo(Integer txnNo) {
		this.txnNo = txnNo;
	}

	/**
	 * @return the tenders
	 */
	public List<TenderBean> getTenders() {
		return tenders;
	}

	/**
	 * @param tenders
	 *            the tenders to set
	 */
	public void setTenders(List<TenderBean> tenders) {
		this.tenders = tenders;
	}

	/**
	 * @return the denominationList
	 */
	public List<MonetaryAmount> getDenominationList() {
		return denominationList;
	}

	/**
	 * @param denominationList
	 *            the denominationList to set
	 */
	public void setDenominationList(List<MonetaryAmount> denominationList) {
		this.denominationList = denominationList;
	}

	/**
	 * @return the selectedTenderId
	 */
	public Integer getSelectedTenderId() {
		return selectedTenderId;
	}

	/**
	 * @param selectedTenderId
	 *            the selectedTenderId to set
	 */
	public void setSelectedTenderId(Integer selectedTenderId) {
		this.selectedTenderId = selectedTenderId;
	}

	/**
	 * @return the selectedDenominationId
	 */
	public Integer getSelectedDenominationId() {
		return selectedDenominationId;
	}

	/**
	 * @param selectedDenominationId
	 *            the selectedDenominationId to set
	 */
	public void setSelectedDenominationId(Integer selectedDenominationId) {
		this.selectedDenominationId = selectedDenominationId;
	}

}
