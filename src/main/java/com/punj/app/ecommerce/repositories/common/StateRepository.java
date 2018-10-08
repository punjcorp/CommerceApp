/**
 * 
 */
package com.punj.app.ecommerce.repositories.common;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.common.State;

/**
 * @author admin
 *
 */
public interface StateRepository extends JpaRepository<State, Integer> {

}
