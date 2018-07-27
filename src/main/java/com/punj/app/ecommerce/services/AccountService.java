/**
 * 
 */
package com.punj.app.ecommerce.services;

import java.math.BigInteger;
import java.util.List;

import com.punj.app.ecommerce.domains.customer.Customer;
import com.punj.app.ecommerce.domains.customer.CustomerDTO;
import com.punj.app.ecommerce.domains.payment.AccountHead;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
public interface AccountService {

	public AccountHead createCustomer(Customer customer, Integer locationId);

	public CustomerDTO searchCustomer(String searchText, Pager pager);

	public List<Customer> searchCustomer(Customer custCriteria);

	public Customer searchCustomerDetails(BigInteger customerId);

	public void addCustomerCreditDetails();

}
