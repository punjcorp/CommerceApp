/**
 * 
 */
package com.punj.app.ecommerce.repositories.item;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.item.Hierarchy;

/**
 * @author admin
 *
 */
public interface HierarchyRepository extends JpaRepository<Hierarchy, Integer> {

}
