/**
 * 
 */
package com.punj.app.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.user.Password;
import com.punj.app.ecommerce.domains.user.ids.PasswordId;

/**
 * @author admin
 *
 */
public interface PasswordRepository extends JpaRepository<Password, PasswordId> {

}
