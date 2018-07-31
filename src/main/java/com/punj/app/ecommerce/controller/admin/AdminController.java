package com.punj.app.ecommerce.controller.admin;
/**
 * 
 */

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.DashboardTransformer;
import com.punj.app.ecommerce.services.ReportingService;
import com.punj.app.ecommerce.services.dtos.DashboardDTO;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class AdminController {
	private static final Logger logger = LogManager.getLogger();
	private MessageSource messageSource;
	private ReportingService reportingService;

	/**
	 * @param reportingService
	 *            the reportingService to set
	 */
	@Autowired
	public void setReportingService(ReportingService reportingService) {
		this.reportingService = reportingService;
	}

	/**
	 * @param messageSource
	 *            the messageSource to set
	 */
	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@GetMapping(ViewPathConstants.ADMIN_HOME_URL)
	public String login(Model model) {
		logger.info("===================================");
		logger.info("WELCOME TO THE ADMIN DASHBOARD PAGE");
		logger.info("===================================");

		LocalDate localDate = LocalDateTime.now().toLocalDate();
		//LocalDateTime businessDate = LocalDateTime.of(localDate.getYear(), localDate.getMonth(), 30, 0, 0);
		LocalDateTime businessDate = LocalDateTime.of(localDate.getYear(), localDate.getMonth(), localDate.getDayOfMonth(), 0, 0);

		DashboardDTO dashboardDTO = this.reportingService.retrieveTotalsByWeek(new Integer("27"), businessDate);
		if (dashboardDTO != null) {
			
			Map<String, Object[]> dashboardData=DashboardTransformer.transformDashboardData(dashboardDTO.getHistoricalReports(), dashboardDTO.getDates());
			dashboardDTO.setDashboardData(dashboardData);
			model.addAttribute(MVCConstants.DASHBOARD_REPORTS_BEAN, dashboardDTO);
			logger.info("The dashboard report details has been updated in the model for display successfully");
		} else {
			logger.error("There was some error while retrieving dashboard report data!!");
		}

		return ViewPathConstants.ADMIN_DASHBOARD_PAGE;

	}

}
