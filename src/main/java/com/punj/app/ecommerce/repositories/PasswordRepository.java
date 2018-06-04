/**
 * 
 */
package com.punj.app.ecommerce.repositories;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.user.Password;

/**
 * @author admin
 *
 */
public interface PasswordRepository extends JpaRepository<Password, BigInteger> {

}
