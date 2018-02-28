/**
 * 
 */
package com.punj.app.ecommerce.repositories.common;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.common.Register;
import com.punj.app.ecommerce.domains.common.ids.RegisterId;

/**
 * @author admin
 *
 */
public interface RegisterRepository extends JpaRepository<Register, RegisterId> {

}
