/**
 * 
 */
package com.punj.app.ecommerce.models.dailydeeds;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.punj.app.ecommerce.models.tender.TenderBean;

/**
 * @author admin
 *
 */
public class ConcilationBean {

	private Integer locationId;
	private Integer register;
	private LocalDateTime businessDate;
	private BigDecimal expectedTotalAmt;
	private BigDecimal actualTotalAmt;

	private List<TenderBean> tenders;

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
	 * @return the expectedTotalAmt
	 */
	public BigDecimal getExpectedTotalAmt() {
		return expectedTotalAmt;
	}

	/**
	 * @param expectedTotalAmt
	 *            the expectedTotalAmt to set
	 */
	public void setExpectedTotalAmt(BigDecimal expectedTotalAmt) {
		this.expectedTotalAmt = expectedTotalAmt;
	}

	/**
	 * @return the actualTotalAmt
	 */
	public BigDecimal getActualTotalAmt() {
		return actualTotalAmt;
	}

	/**
	 * @param actualTotalAmt
	 *            the actualTotalAmt to set
	 */
	public void setActualTotalAmt(BigDecimal actualTotalAmt) {
		this.actualTotalAmt = actualTotalAmt;
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

}
