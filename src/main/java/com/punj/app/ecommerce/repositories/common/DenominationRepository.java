/**
 * 
 */
package com.punj.app.ecommerce.repositories.common;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.common.Denomination;

/**
 * @author admin
 *
 */
public interface DenominationRepository extends JpaRepository<Denomination, Integer> {

}
