/**
 * 
 */
package com.punj.app.ecommerce.repositories.payment;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.payment.JournalTender;
import com.punj.app.ecommerce.domains.payment.ids.JournalTenderId;

/**
 * @author admin
 *
 */
public interface JournalTenderRepository extends JpaRepository<JournalTender, JournalTenderId> {

}
