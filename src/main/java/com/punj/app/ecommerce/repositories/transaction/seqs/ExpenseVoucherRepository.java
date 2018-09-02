/**
 * 
 */
package com.punj.app.ecommerce.repositories.transaction.seqs;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.transaction.seqs.ExpenseVoucher;

/**
 * @author admin
 *
 */
public interface ExpenseVoucherRepository extends JpaRepository<ExpenseVoucher, BigInteger> {

}
