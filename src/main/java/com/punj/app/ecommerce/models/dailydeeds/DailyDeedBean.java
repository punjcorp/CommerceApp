/**
 * 
 */
package com.punj.app.ecommerce.models.dailydeeds;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.punj.app.ecommerce.models.common.BaseDenominationBean;
import com.punj.app.ecommerce.models.common.validator.ValidationGroup;
import com.punj.app.ecommerce.models.common.validator.ValidationGroup.ValidationGroupSkipStoreOpen;
import com.punj.app.ecommerce.models.tender.TenderBean;

/**
 * @author admin
 *
 */
public class DailyDeedBean {

	@NotNull(message = "{commerce.error.location.empty}", groups = { ValidationGroup.ValidationGroupStoreOpen.class, ValidationGroupSkipStoreOpen.class })
	private Integer locationId;
	private String locationName;
	private String gstNo;
	private String defaultTender;

	private Boolean skipClosingProcess;

	private String locationStatus;

	@NotNull(message = "{commerce.error.date.empty}", groups = { ValidationGroup.ValidationGroupStoreOpen.class, ValidationGroupSkipStoreOpen.class })
	private LocalDateTime businessDate;

	@NotNull(message = "{commerce.error.register.empty}", groups = { ValidationGroup.ValidationGroupRegOpen.class })
	private Integer register;
	private Integer txnNo;
	private Integer selectedTenderId;
	private Integer selectedDenominationId;

	private Integer registerId;
	private String registerName;

	private String referrerURL;

	private Boolean allRegClosedFlag;
	private Boolean allRegOpenedFlag;

	@Valid
	private List<TenderBean> tenders;

	private List<TenderBean> availableTenders;

	private List<BaseDenominationBean> denominationList;

	private ConcilationBean concilationBean;

	public DailyDeedBean() {
		tenders = new ArrayList<>();
		tenders.add(new TenderBean());
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

	/**
	 * @return the defaultTender
	 */
	public String getDefaultTender() {
		return defaultTender;
	}

	/**
	 * @param defaultTender
	 *            the defaultTender to set
	 */
	public void setDefaultTender(String defaultTender) {
		this.defaultTender = defaultTender;
	}

	/**
	 * @return the denominationList
	 */
	public List<BaseDenominationBean> getDenominationList() {
		return denominationList;
	}

	/**
	 * @param denominationList
	 *            the denominationList to set
	 */
	public void setDenominationList(List<BaseDenominationBean> denominationList) {
		this.denominationList = denominationList;
	}

	/**
	 * @return the referrerURL
	 */
	public String getReferrerURL() {
		return referrerURL;
	}

	/**
	 * @param referrerURL
	 *            the referrerURL to set
	 */
	public void setReferrerURL(String referrerURL) {
		this.referrerURL = referrerURL;
	}

	/**
	 * @return the concilationBean
	 */
	public ConcilationBean getConcilationBean() {
		return concilationBean;
	}

	/**
	 * @param concilationBean
	 *            the concilationBean to set
	 */
	public void setConcilationBean(ConcilationBean concilationBean) {
		this.concilationBean = concilationBean;
	}

	/**
	 * @return the locationStatus
	 */
	public String getLocationStatus() {
		return locationStatus;
	}

	/**
	 * @param locationStatus
	 *            the locationStatus to set
	 */
	public void setLocationStatus(String locationStatus) {
		this.locationStatus = locationStatus;
	}

	/**
	 * @return the allRegClosedFlag
	 */
	public Boolean getAllRegClosedFlag() {
		return allRegClosedFlag;
	}

	/**
	 * @param allRegClosedFlag
	 *            the allRegClosedFlag to set
	 */
	public void setAllRegClosedFlag(Boolean allRegClosedFlag) {
		this.allRegClosedFlag = allRegClosedFlag;
	}

	/**
	 * @return the allRegOpenedFlag
	 */
	public Boolean getAllRegOpenedFlag() {
		return allRegOpenedFlag;
	}

	/**
	 * @param allRegOpenedFlag
	 *            the allRegOpenedFlag to set
	 */
	public void setAllRegOpenedFlag(Boolean allRegOpenedFlag) {
		this.allRegOpenedFlag = allRegOpenedFlag;
	}

	/**
	 * @return the gstNo
	 */
	public String getGstNo() {
		return gstNo;
	}

	/**
	 * @param gstNo
	 *            the gstNo to set
	 */
	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	/**
	 * @return the skipClosingProcess
	 */
	public Boolean getSkipClosingProcess() {
		return skipClosingProcess;
	}

	/**
	 * @param skipClosingProcess
	 *            the skipClosingProcess to set
	 */
	public void setSkipClosingProcess(Boolean skipClosingProcess) {
		this.skipClosingProcess = skipClosingProcess;
	}

}
