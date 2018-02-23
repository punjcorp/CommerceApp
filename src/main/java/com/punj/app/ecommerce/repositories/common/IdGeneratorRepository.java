/**
 * 
 */
package com.punj.app.ecommerce.repositories.common;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.common.IdGenerator;

/**
 * @author admin
 *
 */
public interface IdGeneratorRepository extends JpaRepository<IdGenerator, String> {

}
