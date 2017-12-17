/**
 * 
 */
package com.punj.app.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.user.Card;
import com.punj.app.ecommerce.domains.user.ids.CardId;

/**
 * @author admin
 *
 */
public interface CardRepository extends JpaRepository<Card, CardId> {

}
