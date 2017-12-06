/**
 * 
 */
package com.punj.app.ecommerce.repositories;

import org.springframework.data.repository.CrudRepository;

import com.punj.app.ecommerce.domains.User;

/**
 * @author admin
 *
 */
public interface UserRepository extends CrudRepository<User, String> {

}
