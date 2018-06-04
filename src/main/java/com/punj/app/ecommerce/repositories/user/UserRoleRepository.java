/**
 * 
 */
package com.punj.app.ecommerce.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.user.UserRole;
import com.punj.app.ecommerce.domains.user.ids.UserRoleId;

/**
 * @author admin
 *
 */
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {

}
