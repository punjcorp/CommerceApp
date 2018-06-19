/**
 * 
 */
package com.punj.app.ecommerce.repositories.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.transaction.TransactionCustomer;
import com.punj.app.ecommerce.domains.transaction.ids.TransactionCustomerId;

/**
 * @author admin
 *
 */
public interface TransactionCustomerRepository extends JpaRepository<TransactionCustomer, TransactionCustomerId> {

}
