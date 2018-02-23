/**
 * 
 */
package com.punj.app.ecommerce.repositories.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.transaction.Transaction;
import com.punj.app.ecommerce.domains.transaction.ids.TransactionId;

/**
 * @author admin
 *
 */
public interface TransactionRepository extends JpaRepository<Transaction, TransactionId> {

}
