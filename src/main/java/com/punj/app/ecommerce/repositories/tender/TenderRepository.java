/**
 * 
 */
package com.punj.app.ecommerce.repositories.tender;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.tender.Tender;

/**
 * @author admin
 *
 */
public interface TenderRepository extends JpaRepository<Tender, Integer> {

}
