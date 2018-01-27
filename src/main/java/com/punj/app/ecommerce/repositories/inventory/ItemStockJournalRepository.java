/**
 * 
 */
package com.punj.app.ecommerce.repositories.inventory;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.inventory.ItemStockJournal;

/**
 * @author admin
 *
 */
public interface ItemStockJournalRepository extends JpaRepository<ItemStockJournal, BigInteger> {

}
