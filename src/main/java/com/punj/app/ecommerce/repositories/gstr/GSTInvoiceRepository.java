/**
 * 
 */
package com.punj.app.ecommerce.repositories.gstr;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.punj.app.ecommerce.domains.gstr.GSTInvoice;
import com.punj.app.ecommerce.domains.gstr.ids.GSTInvoiceId;
import com.punj.app.ecommerce.domains.transaction.ReceiptItemTax;

/**
 * @author admin
 *
 */
public interface GSTInvoiceRepository extends JpaRepository<GSTInvoice, GSTInvoiceId> {
	
	@Query(value = "SELECT * FROM pi_pos_industry.v_gstr_one_invoice where location_id=?1 and business_date between ?2 and ?3 ORDER BY gst_no ASC, location_id ASC , business_date ASC , register ASC , txn_no ASC , created_date ASC , rate ASC", nativeQuery = true)
	public List<GSTInvoice> retrieveInvoiceDetails(Integer locationId, String startDate, String endDate);
	
}
