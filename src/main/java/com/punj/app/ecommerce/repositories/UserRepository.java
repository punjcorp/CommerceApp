/**
 * 
 */
package com.punj.app.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.user.User;

/**
 * @author admin
 *
 */
public interface UserRepository extends JpaRepository<User, String> {

}
