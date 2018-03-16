/**
 * 
 */
package com.punj.app.ecommerce.repositories.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.transaction.ReceiptItemTax;
import com.punj.app.ecommerce.domains.transaction.ids.SaleLineItemId;

/**
 * @author admin
 *
 */
public interface ReceiptItemTaxRepository extends JpaRepository<ReceiptItemTax, SaleLineItemId> {



}
