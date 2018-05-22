/**
 * 
 */
package com.punj.app.ecommerce.repositories.finance;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.finance.TenderMovement;
import com.punj.app.ecommerce.domains.transaction.ids.TransactionId;

/**
 * @author admin
 *
 */
public interface TenderMovementRepository extends JpaRepository<TenderMovement, TransactionId> {

}
