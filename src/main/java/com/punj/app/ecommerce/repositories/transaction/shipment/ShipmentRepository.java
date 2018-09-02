/**
 * 
 */
package com.punj.app.ecommerce.repositories.transaction.shipment;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.transaction.ids.TransactionId;
import com.punj.app.ecommerce.domains.transaction.shipment.Shipment;

/**
 * @author admin
 *
 */
public interface ShipmentRepository extends JpaRepository<Shipment, TransactionId> {

}
