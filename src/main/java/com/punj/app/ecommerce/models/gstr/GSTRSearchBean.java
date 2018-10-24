/**
 * 
 */
package com.punj.app.ecommerce.models.gstr;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.punj.app.ecommerce.gst.r1.GSTR1;

/**
 * @author admin
 *
 */
public class GSTRSearchBean {
	@NotNull(message = "{commerce.error.option.empty}")
	private Integer locationId;
	@NotBlank(message = "{commerce.error.option.empty}")
	private String financialYear;
	@NotBlank(message = "{commerce.error.option.empty}")
	private String financialMonth;
	private GSTR1 gstrDetails;

	/**
	 * @return the financialYear
	 */
	public String getFinancialYear() {
		return financialYear;
	}

	/**
	 * @param financialYear
	 *            the financialYear to set
	 */
	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}

	/**
	 * @return the financialMonth
	 */
	public String getFinancialMonth() {
		return financialMonth;
	}

	/**
	 * @param financialMonth
	 *            the financialMonth to set
	 */
	public void setFinancialMonth(String financialMonth) {
		this.financialMonth = financialMonth;
	}

	/**
	 * @return the gstrDetails
	 */
	public GSTR1 getGstrDetails() {
		return gstrDetails;
	}

	/**
	 * @param gstrDetails
	 *            the gstrDetails to set
	 */
	public void setGstrDetails(GSTR1 gstrDetails) {
		this.gstrDetails = gstrDetails;
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

}
