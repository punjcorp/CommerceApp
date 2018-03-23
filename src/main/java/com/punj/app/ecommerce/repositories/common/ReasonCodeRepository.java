/**
 * 
 */
package com.punj.app.ecommerce.repositories.common;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.common.ReasonCode;

/**
 * @author admin
 *
 */
public interface ReasonCodeRepository extends JpaRepository<ReasonCode, Integer> {

}
