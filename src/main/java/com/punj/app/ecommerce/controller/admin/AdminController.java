package com.punj.app.ecommerce.controller.admin;
/**
 * 
 */

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.CommonMVCTransformer;
import com.punj.app.ecommerce.controller.common.transformer.DashboardTransformer;
import com.punj.app.ecommerce.domains.common.Location;
import com.punj.app.ecommerce.models.common.LocationBean;
import com.punj.app.ecommerce.models.order.OrderBeanDTO;
import com.punj.app.ecommerce.services.ReportingService;
import com.punj.app.ecommerce.services.common.CommonService;
import com.punj.app.ecommerce.services.dtos.DashboardDTO;
import com.punj.app.ecommerce.services.dtos.UserPrincipal;

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
	private CommonService commonService;

	/**
	 * @param commonService
	 *            the commonService to set
	 */
	@Autowired
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

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
	public String login(Model model, Authentication authentication, Locale locale) {
		logger.info("===================================");
		logger.info("WELCOME TO THE ADMIN DASHBOARD PAGE");
		logger.info("===================================");

		return this.processAdminDashboardRequest(null, model, authentication, locale);
		

	}
	
	public String processAdminDashboardRequest(Integer locationId, Model model, Authentication authentication, Locale locale) {
		UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
		Integer defaultLocationId = null;
		if(locationId!=null)
			defaultLocationId = locationId;
		else
			defaultLocationId = userDetails.getDefaultLocationId();

		LocalDate localDate = LocalDateTime.now().toLocalDate();
		LocalDateTime businessDate = LocalDateTime.of(localDate.getYear(), localDate.getMonth(), localDate.getDayOfMonth(), 0, 0);
		List<Location> locations = this.commonService.retrieveAllLocations();
		if (locations != null && !locations.isEmpty()) {
			List<LocationBean> locationBeans = CommonMVCTransformer.transformLocationList(locations, Boolean.TRUE);

			if (defaultLocationId == null) {
				defaultLocationId = locations.get(0).getLocationId();
			}

			DashboardDTO dashboardDTO = this.reportingService.retrieveTotalsByWeek(defaultLocationId, businessDate);
			if (dashboardDTO != null) {
				dashboardDTO.setLocations(locationBeans);
				dashboardDTO.setDefaultLocation(defaultLocationId);
				dashboardDTO.setLocationId(defaultLocationId);

				Map<String, Object[]> dashboardData = DashboardTransformer.transformDashboardData(dashboardDTO.getHistoricalReports(), dashboardDTO.getDates());
				dashboardDTO.setDashboardData(dashboardData);
				model.addAttribute(MVCConstants.DASHBOARD_REPORTS_BEAN, dashboardDTO);
				logger.info("The dashboard report details has been updated in the model for display successfully");

			} else {
				logger.error("There is some issue while retrieving admin widget details.");
				model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage("commerce.screen.home.dashboard.error.no.data", null, locale));
			}

		} else {
			logger.error("There were not locations found to display the data!!");
			model.addAttribute(MVCConstants.ALERT, this.messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
		}

		return ViewPathConstants.ADMIN_DASHBOARD_PAGE;
	}
	
	@PostMapping(value = ViewPathConstants.ADMIN_HOME_URL)
	public String processDashboardInput(@ModelAttribute DashboardDTO dashboardDTO, final BindingResult bindingResult, Model model, Locale locale,
			Authentication authentication) {
		
		Integer locationId=dashboardDTO.getLocationId();
		return this.processAdminDashboardRequest(locationId, model, authentication, locale);
		
	}
	

}
