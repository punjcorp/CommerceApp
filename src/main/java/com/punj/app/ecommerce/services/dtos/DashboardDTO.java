/**
 * 
 */
package com.punj.app.ecommerce.services.dtos;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.punj.app.ecommerce.domains.reports.dashboard.DashboardReport;

/**
 * @author admin
 *
 */
public class DashboardDTO implements Serializable {

	private static final long serialVersionUID = -2210628942887342403L;

	private DashboardReport currentDayReport;
	private Map<String, DashboardReport> historicalReports;
	private Map<String, Object[]> dashboardData;
	private List<String> dates;

	/**
	 * @return the currentDayReport
	 */
	public DashboardReport getCurrentDayReport() {
		return currentDayReport;
	}

	/**
	 * @param currentDayReport
	 *            the currentDayReport to set
	 */
	public void setCurrentDayReport(DashboardReport currentDayReport) {
		this.currentDayReport = currentDayReport;
	}

	/**
	 * @return the historicalReports
	 */
	public Map<String, DashboardReport> getHistoricalReports() {
		return historicalReports;
	}

	/**
	 * @param historicalReports
	 *            the historicalReports to set
	 */
	public void setHistoricalReports(Map<String, DashboardReport> historicalReports) {
		this.historicalReports = historicalReports;
	}

	/**
	 * @return the dates
	 */
	public List<String> getDates() {
		return dates;
	}

	/**
	 * @param dates
	 *            the dates to set
	 */
	public void setDates(List<String> dates) {
		this.dates = dates;
	}

	/**
	 * @return the dashboardData
	 */
	public Map<String, Object[]> getDashboardData() {
		return dashboardData;
	}

	/**
	 * @param dashboardData
	 *            the dashboardData to set
	 */
	public void setDashboardData(Map<String, Object[]> dashboardData) {
		this.dashboardData = dashboardData;
	}

}
