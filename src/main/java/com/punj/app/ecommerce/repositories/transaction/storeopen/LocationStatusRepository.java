/**
 * 
 */
package com.punj.app.ecommerce.repositories.transaction.storeopen;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.punj.app.ecommerce.domains.transaction.storeopen.LocationStatus;
import com.punj.app.ecommerce.domains.transaction.storeopen.ids.LocationStatusId;

/**
 * @author admin
 *
 */
public interface LocationStatusRepository extends JpaRepository<LocationStatus, LocationStatusId> {

	@Query(value = "select result_data.* from pi_pos_industry.v_loc_status result_data where result_data.location_id=?1 and result_data.business_date>=?2", nativeQuery = true)
	List<LocationStatus> getLocationStatusSinceDate(Integer locationId, String businessDate);

}
