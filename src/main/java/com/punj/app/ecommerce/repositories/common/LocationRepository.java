/**
 * 
 */
package com.punj.app.ecommerce.repositories.common;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.common.Location;

/**
 * @author admin
 *
 */
public interface LocationRepository extends JpaRepository<Location, Integer> {

}
