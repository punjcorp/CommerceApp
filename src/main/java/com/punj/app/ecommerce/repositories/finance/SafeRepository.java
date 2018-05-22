/**
 * 
 */
package com.punj.app.ecommerce.repositories.finance;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.finance.SafeMaster;

/**
 * @author admin
 *
 */
public interface SafeRepository extends JpaRepository<SafeMaster, Integer> {

}
