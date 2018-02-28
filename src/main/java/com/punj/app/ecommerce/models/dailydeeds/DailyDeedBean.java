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
	private String locationName;
	@NotNull(message = "{commerce.error.date.empty}")
	private LocalDateTime businessDate;
	private Integer register;
	private Integer txnNo;
	private Integer selectedTenderId;
	private Integer selectedDenominationId;

	private Integer registerId;
	private String registerName;

	@Valid
	private List<TenderBean> tenders;

	private List<TenderBean> availableTenders;

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

	/**
	 * @return the locationName
	 */
	public String getLocationName() {
		return locationName;
	}

	/**
	 * @param locationName
	 *            the locationName to set
	 */
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	/**
	 * @return the availableTenders
	 */
	public List<TenderBean> getAvailableTenders() {
		return availableTenders;
	}

	/**
	 * @param availableTenders
	 *            the availableTenders to set
	 */
	public void setAvailableTenders(List<TenderBean> availableTenders) {
		this.availableTenders = availableTenders;
	}

	/**
	 * @return the registerId
	 */
	public Integer getRegisterId() {
		return registerId;
	}

	/**
	 * @param registerId
	 *            the registerId to set
	 */
	public void setRegisterId(Integer registerId) {
		this.registerId = registerId;
	}

	/**
	 * @return the registerName
	 */
	public String getRegisterName() {
		return registerName;
	}

	/**
	 * @param registerName
	 *            the registerName to set
	 */
	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

}
