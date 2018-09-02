/**
 * 
 */
package com.punj.app.ecommerce.repositories.reports;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.punj.app.ecommerce.domains.reports.dashboard.DashboardReport;
import com.punj.app.ecommerce.domains.reports.dashboard.ids.DashboardReportId;

/**
 * @author admin
 *
 */
public interface DashboardReportRepository extends JpaRepository<DashboardReport, DashboardReportId> {

	@Query(value = "select sales_data.* from pi_pos_industry.v_location_sales_data sales_data where sales_data.location_id=?1 and sales_data.business_date<=?2 and sales_data.business_date> ?3 ", nativeQuery = true)
	List<DashboardReport> getDailyReportWeekWise(Integer locationId, String businessDate, String businessDateFromPastWeek);

}
