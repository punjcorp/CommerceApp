/**
 * 
 */
package com.punj.app.ecommerce.repositories.item;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.item.ItemLocationTax;
import com.punj.app.ecommerce.domains.item.ids.ItemLocationTaxId;

/**
 * @author admin
 *
 */
public interface ItemLocTaxRepository extends JpaRepository<ItemLocationTax, ItemLocationTaxId> {

}
