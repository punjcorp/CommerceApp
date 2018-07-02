/**
 * 
 */
package com.punj.app.ecommerce.models.dailydeeds;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

}
