/**
 * 
 */
package com.punj.app.ecommerce.repositories.payment;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.payment.AJReceipt;

/**
 * @author admin
 *
 */
public interface AJReceiptRepository extends JpaRepository<AJReceipt, BigInteger> {

}
