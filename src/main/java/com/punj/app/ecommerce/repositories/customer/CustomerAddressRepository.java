/**
 * 
 */
package com.punj.app.ecommerce.repositories.customer;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punj.app.ecommerce.domains.customer.CustomerAddress;
import com.punj.app.ecommerce.domains.customer.ids.CustomerAddressId;

/**
 * @author admin
 *
 */
public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, CustomerAddressId> {

}
