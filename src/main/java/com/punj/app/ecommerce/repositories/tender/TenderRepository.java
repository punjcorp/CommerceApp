/**
 * 
 */
package com.punj.app.ecommerce.repositories.tender;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.punj.app.ecommerce.domains.tender.Tender;

/**
 * @author admin
 *
 */
public interface TenderRepository extends JpaRepository<Tender, Integer> {

	@Query(value = "select tndr.* from pi_pos_industry.tender_master tndr, `pi_pos_industry`.`location_repo` loc_tndr\r\n" + 
			" where tndr.tender_id=loc_tndr.tender_id and loc_tndr.location_id= ?1", nativeQuery = true)
	List<Tender> getTendersByLocation(Integer locationId);	
	
	@Query(value = "select tndr.* from pi_pos_industry.tender_master tndr, `pi_pos_industry`.`location_repo` loc_tndr\r\n" + 
			" where tndr.tender_id=loc_tndr.tender_id and loc_tndr.location_id= ?1 and loc_tndr.reconcilation_flag=1", nativeQuery = true)
	List<Tender> getTendersForReconcilation(Integer locationId);		
	
}
