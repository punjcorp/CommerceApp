/**
 * 
 */
package com.punj.app.ecommerce.services;

import java.time.LocalDateTime;

import com.punj.app.ecommerce.services.dtos.DashboardDTO;

/**
 * @author admin
 *
 */
public interface ReportingService {

	public DashboardDTO retrieveTotalsByDate(Integer locationId, LocalDateTime businessDate);

	public DashboardDTO retrieveTotalsByWeek(Integer locationId, LocalDateTime businessDate);

	public DashboardDTO retrieveTotalsByMonth(Integer locationId, LocalDateTime businessDate);

}
