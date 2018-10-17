/**
 * 
 */
package com.punj.app.ecommerce.repositories.common;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.common.AppConfig;

/**
 * @author admin
 *
 */
public interface AppConfigRepository extends JpaRepository<AppConfig, Integer> {

}
