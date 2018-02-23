/**
 * 
 */
package com.punj.app.ecommerce.repositories.transaction.tender;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.transaction.tender.TenderDenomination;
import com.punj.app.ecommerce.domains.transaction.tender.ids.TenderDenominationId;

/**
 * @author admin
 *
 */
public interface TenderDenominationRepository extends JpaRepository<TenderDenomination, TenderDenominationId> {

}
