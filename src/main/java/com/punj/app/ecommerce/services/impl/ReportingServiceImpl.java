/**
 * 
 */
package com.punj.app.ecommerce.services.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.punj.app.ecommerce.domains.reports.dashboard.DashboardReport;
import com.punj.app.ecommerce.domains.reports.dashboard.ids.DashboardReportId;
import com.punj.app.ecommerce.repositories.reports.DashboardReportRepository;
import com.punj.app.ecommerce.repositories.transaction.TransactionCustomerRepository;
import com.punj.app.ecommerce.services.AccountService;
import com.punj.app.ecommerce.services.FinanceService;
import com.punj.app.ecommerce.services.InventoryService;
import com.punj.app.ecommerce.services.PaymentAccountService;
import com.punj.app.ecommerce.services.ReportingService;
import com.punj.app.ecommerce.services.common.CommonService;
import com.punj.app.ecommerce.services.converter.ReportingConverter;
import com.punj.app.ecommerce.services.dtos.DashboardDTO;

/**
 * @author admin
 *
 */
@Service
public class ReportingServiceImpl implements ReportingService {

	private static final Logger logger = LogManager.getLogger();
	private CommonService commonService;
	private DashboardReportRepository dashboardReportRepository;
	private InventoryService inventoryService;
	private TransactionCustomerRepository txnCustomerRepository;
	private FinanceService financeService;
	private AccountService accountService;
	private PaymentAccountService paymentAccountService;

	/**
	 * @param dashboardReportRepository
	 *            the dashboardReportRepository to set
	 */
	@Autowired
	public void setDashboardReportRepository(DashboardReportRepository dashboardReportRepository) {
		this.dashboardReportRepository = dashboardReportRepository;
	}

	/**
	 * @param paymentAccountService
	 *            the paymentAccountService to set
	 */
	@Autowired
	public void setPaymentAccountService(PaymentAccountService paymentAccountService) {
		this.paymentAccountService = paymentAccountService;
	}

	/**
	 * @param accountService
	 *            the accountService to set
	 */
	@Autowired
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	/**
	 * @param financeService
	 *            the financeService to set
	 */
	@Autowired
	public void setFinanceService(FinanceService financeService) {
		this.financeService = financeService;
	}

	/**
	 * @param txnReceiptRepository
	 *            the txnReceiptRepository to set
	 */
	@Autowired
	public void setTxnCustomerRepository(TransactionCustomerRepository txnCustomerRepository) {
		this.txnCustomerRepository = txnCustomerRepository;
	}

	/**
	 * @param inventoryService
	 *            the inventoryService to set
	 */
	@Autowired
	public void setInventoryService(InventoryService inventoryService) {
		this.inventoryService = inventoryService;
	}

	/**
	 * @param commonService
	 *            the commonService to set
	 */
	@Autowired
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	@Override
	public DashboardDTO retrieveTotalsByDate(Integer locationId, LocalDateTime businessDate) {
		DashboardDTO dashboardDTO = null;
		DashboardReportId dashboardReportId = new DashboardReportId();
		dashboardReportId.setLocationId(locationId);
		dashboardReportId.setBusinessDate(businessDate);

		DashboardReport dashboardReport = this.dashboardReportRepository.findOne(dashboardReportId);
		if (dashboardReport != null) {
			dashboardDTO = new DashboardDTO();
			dashboardDTO.setCurrentDayReport(dashboardReport);

			logger.info("The location {} totals for the date {} has been retrieved successfully", locationId, businessDate);
		} else {
			logger.info("There was no record found for the searched location {} and business date {}", locationId, businessDate);
		}

		return dashboardDTO;
	}

	@Override
	public DashboardDTO retrieveTotalsByWeek(Integer locationId, LocalDateTime businessDate) {
		DashboardDTO dashboardDTO = null;
		LocalDateTime pastWeekDate=businessDate.minusDays(6);

		List<DashboardReport> dashboardReports = this.dashboardReportRepository.getDailyReportWeekWise(locationId, businessDate.toString(),pastWeekDate.toString());
		if (dashboardReports != null && !dashboardReports.isEmpty()) {
			logger.info("The location {} totals for the date {} has been retrieved successfully", locationId, businessDate);
			dashboardDTO = new DashboardDTO();
			Map<LocalDateTime, DashboardReport> weeklyReports = new HashMap<>();
			for (DashboardReport dashboardReport : dashboardReports) {
				weeklyReports.put(dashboardReport.getDashboardReportId().getBusinessDate(), dashboardReport);
			}

			LocalDateTime tmpDate = businessDate.minusDays(6);
			Map<String, DashboardReport> historicalReports = new HashMap<>();
			DashboardReport dashboardReport = null;
			List<String> bDates=new ArrayList<>();
			String tmpDateStr=null;
			while (tmpDate.isBefore(businessDate) || tmpDate.isEqual(businessDate)) {
				dashboardReport = weeklyReports.get(tmpDate);
				tmpDateStr=tmpDate.toLocalDate().toString();
				if (dashboardReport != null)
					historicalReports.put(tmpDateStr, dashboardReport);
				else
					historicalReports.put(tmpDateStr, ReportingConverter.createBlankDashboardReport(locationId, businessDate));

				bDates.add(tmpDateStr);

				tmpDate = tmpDate.plusDays(1);

			}

			logger.info("The weekly map for the dashboard has been created successfully");

			dashboardDTO.setDates(bDates);
			dashboardDTO.setHistoricalReports(historicalReports);
			dashboardDTO.setCurrentDayReport(historicalReports.get(businessDate.toLocalDate().toString()));
			logger.info("The location {} totals for the date {} has been retrieved successfully", locationId, businessDate);
		} else {
			logger.info("There was no record found for the searched location {} and business date {}", locationId, businessDate);
		}

		return dashboardDTO;
	}

	@Override
	public DashboardDTO retrieveTotalsByMonth(Integer locationId, LocalDateTime businessDate) {
		// TODO Auto-generated method stub
		return null;
	}

}
