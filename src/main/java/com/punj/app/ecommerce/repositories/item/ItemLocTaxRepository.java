/**
 * 
 */
package com.punj.app.ecommerce.repositories.item;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.punj.app.ecommerce.domains.item.ItemLocationTax;
import com.punj.app.ecommerce.domains.item.ids.ItemLocationTaxId;

/**
 * @author admin
 *
 */
public interface ItemLocTaxRepository extends JpaRepository<ItemLocationTax, ItemLocationTaxId> {


}
