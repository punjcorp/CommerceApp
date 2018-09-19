/**
 * 
 */
package com.punj.app.ecommerce.repositories.transaction.seqs;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.punj.app.ecommerce.domains.transaction.seqs.SaleInvoice;

/**
 * @author admin
 *
 */
public interface SaleInvoiceRepository extends JpaRepository<SaleInvoice, BigInteger> {

	@Query(value = "select * from pi_pos_industry.sale_txn_invoices where invoice_no between ?1 and ?2 order by invoice_no asc", nativeQuery = true)
	public List<SaleInvoice> getInvoiceFromRange(BigInteger startingInvoice, BigInteger endingInvoice);
	
	@Query(value = "select max(invoice_no) from pi_pos_industry.sale_txn_invoices", nativeQuery = true)
	public BigInteger getMaxInvoiceNo();
}
