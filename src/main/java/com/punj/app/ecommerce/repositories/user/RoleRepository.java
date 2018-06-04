/**
 * 
 */
package com.punj.app.ecommerce.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.user.Role;

/**
 * @author admin
 *
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
