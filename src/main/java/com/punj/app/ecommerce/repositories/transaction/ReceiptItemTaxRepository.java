/**
 * 
 */
package com.punj.app.ecommerce.repositories.transaction;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.punj.app.ecommerce.domains.transaction.ReceiptItemTax;
import com.punj.app.ecommerce.domains.transaction.ids.SaleLineItemId;

/**
 * @author admin
 *
 */
public interface ReceiptItemTaxRepository extends JpaRepository<ReceiptItemTax, SaleLineItemId> {

	

}
