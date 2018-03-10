/**
 * 
 */
package com.punj.app.ecommerce.repositories.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.transaction.TransactionLineItem;
import com.punj.app.ecommerce.domains.transaction.ids.TransactionLineItemId;

/**
 * @author admin
 *
 */
public interface TransactionLineItemRepository extends JpaRepository<TransactionLineItem, TransactionLineItemId> {

}
