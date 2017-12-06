/**
 * 
 */
package com.punj.app.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.Card;
import com.punj.app.ecommerce.domains.ids.CardId;

/**
 * @author admin
 *
 */
public interface CardRepository extends JpaRepository<Card, CardId> {

}
