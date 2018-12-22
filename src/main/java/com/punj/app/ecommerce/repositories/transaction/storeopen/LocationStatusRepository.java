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

	@Query(value = "select result_data.* from commercedb.v_loc_status result_data where result_data.location_id=?1 and result_data.business_date>=?2", nativeQuery = true)
	List<LocationStatus> getLocationStatusSinceDate(Integer locationId, String businessDate);

	
	@Query(value = "SELECT result.* FROM commercedb.v_loc_status result where result.location_id=?1 and result.business_date>=(SELECT max(ic.business_date) FROM commercedb.v_loc_status ic where ic.sale_exists=1 and ic.location_id=?1) order by result.business_date asc;", nativeQuery = true)
	List<LocationStatus> getLocationStatusForMinDate(Integer locationId);

	
}
