/**
 * 
 */
package com.punj.app.ecommerce.repositories.payment;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.payment.AccountHead;

/**
 * @author admin
 *
 */
public interface AccountHeadRepository extends JpaRepository<AccountHead, Integer> {

}
