/**
 * 
 */
package com.punj.app.ecommerce.repositories.transaction.tender;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.transaction.tender.TenderCount;
import com.punj.app.ecommerce.domains.transaction.tender.ids.TenderCountId;

/**
 * @author admin
 *
 */
public interface TenderCountRepository extends JpaRepository<TenderCount, TenderCountId> {

}
