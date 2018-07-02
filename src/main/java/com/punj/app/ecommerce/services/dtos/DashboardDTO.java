/**
 * 
 */
package com.punj.app.ecommerce.services.dtos;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

import com.punj.app.ecommerce.domains.reports.dashboard.DashboardReport;

/**
 * @author admin
 *
 */
public class DashboardDTO implements Serializable {

	private static final long serialVersionUID = -2210628942887342403L;

	private DashboardReport currentDayReport;
	private Map<LocalDateTime, DashboardReport> historicalReports;

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
	public Map<LocalDateTime, DashboardReport> getHistoricalReports() {
		return historicalReports;
	}

	/**
	 * @param historicalReports
	 *            the historicalReports to set
	 */
	public void setHistoricalReports(Map<LocalDateTime, DashboardReport> historicalReports) {
		this.historicalReports = historicalReports;
	}

}
