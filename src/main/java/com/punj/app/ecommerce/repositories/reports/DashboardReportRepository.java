/**
 * 
 */
package com.punj.app.ecommerce.repositories.reports;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.reports.dashboard.DashboardReport;
import com.punj.app.ecommerce.domains.reports.dashboard.ids.DashboardReportId;

/**
 * @author admin
 *
 */
public interface DashboardReportRepository extends JpaRepository<DashboardReport, DashboardReportId> {

}
