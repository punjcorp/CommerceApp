/**
 * 
 */
package com.punj.app.ecommerce.repositories.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.transaction.NoSaleTender;
import com.punj.app.ecommerce.domains.transaction.ids.NoSaleId;

/**
 * @author admin
 *
 */
public interface NoSaleTenderRepository extends JpaRepository<NoSaleTender, NoSaleId> {

}
