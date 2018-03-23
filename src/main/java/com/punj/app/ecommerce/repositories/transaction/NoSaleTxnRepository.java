/**
 * 
 */
package com.punj.app.ecommerce.repositories.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.transaction.NoSaleTransaction;
import com.punj.app.ecommerce.domains.transaction.ids.TransactionId;

/**
 * @author admin
 *
 */
public interface NoSaleTxnRepository extends JpaRepository<NoSaleTransaction, TransactionId> {

}
