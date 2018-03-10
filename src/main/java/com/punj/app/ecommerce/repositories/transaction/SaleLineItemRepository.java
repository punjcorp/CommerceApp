/**
 * 
 */
package com.punj.app.ecommerce.repositories.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.transaction.SaleLineItem;
import com.punj.app.ecommerce.domains.transaction.ids.SaleLineItemId;

/**
 * @author admin
 *
 */
public interface SaleLineItemRepository extends JpaRepository<SaleLineItem, SaleLineItemId> {

}
