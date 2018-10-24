/**
 * 
 */
package com.punj.app.ecommerce.repositories.gstr;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.punj.app.ecommerce.domains.gstr.GSTHSN;
import com.punj.app.ecommerce.domains.gstr.GSTInvoice;
import com.punj.app.ecommerce.domains.transaction.ReceiptItemTax;

/**
 * @author admin
 *
 */
public interface GSTHSNRepository extends JpaRepository<GSTHSN, String> {

	@Query(value = "SELECT hsn_no, name, sum(qty) qty, sum(extended_amount) extended_amount, sum(discount_amount) discount_amount, sum(taxable_amount) taxable_amount, sum(tax_amount) tax_amount, sum(gross_amount) gross_amount, sum(SGST_amount) SGST_amount,sum(CGST_amount) CGST_amount,sum(IGST_amount) IGST_amount FROM commercedb.v_gstr_one_hsn WHERE location_id = ?1 AND business_date>= ?2 AND business_date<=?3 group by hsn_no, name ORDER BY hsn_no ASC", nativeQuery = true)
	public List<GSTHSN> retrieveHSNDetails(Integer locationId, String startDate, String endDate);

}
