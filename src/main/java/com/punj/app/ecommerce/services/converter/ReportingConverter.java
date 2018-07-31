/**
 * 
 */
package com.punj.app.ecommerce.services.converter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.punj.app.ecommerce.domains.reports.dashboard.DashboardReport;
import com.punj.app.ecommerce.domains.reports.dashboard.ids.DashboardReportId;

/**
 * @author admin
 *
 */
public class ReportingConverter {

	private static final Logger logger = LogManager.getLogger();

	private ReportingConverter() {
		throw new IllegalStateException("ReportingConverter class");
	}

	public static DashboardReport createBlankDashboardReport(Integer locationId, LocalDateTime businessDate) {
		DashboardReport dashboardReport = new DashboardReport();
		DashboardReportId dashboardReportId = new DashboardReportId();
		dashboardReportId.setBusinessDate(businessDate);
		dashboardReportId.setLocationId(locationId);
		
		dashboardReport.setDashboardReportId(dashboardReportId);
		dashboardReport.setBasketAmount(new BigDecimal("0.00"));
		dashboardReport.setDiscountAmount(new BigDecimal("0.00"));
		dashboardReport.setDiscountPercentage(new BigDecimal("0.00"));
		dashboardReport.setRevenueAmount(new BigDecimal("0.00"));
		dashboardReport.setBasketSize(new BigDecimal("0.00"));
		dashboardReport.setCustomerCount(0);
		dashboardReport.setTxnCount(0);

		logger.info("The Dashboard Blank report has been created successfully");
		return dashboardReport;
	}

}
