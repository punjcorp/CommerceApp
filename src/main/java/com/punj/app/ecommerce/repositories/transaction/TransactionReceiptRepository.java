/**
 * 
 */
package com.punj.app.ecommerce.repositories.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.transaction.TransactionReceipt;
import com.punj.app.ecommerce.domains.transaction.ids.TransactionReceiptId;

/**
 * @author admin
 *
 */
public interface TransactionReceiptRepository extends JpaRepository<TransactionReceipt, TransactionReceiptId> {

}
