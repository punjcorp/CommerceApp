/**
 * 
 */
package com.punj.app.ecommerce.services;

import java.math.BigInteger;
import java.util.List;

import com.punj.app.ecommerce.domains.customer.Customer;
import com.punj.app.ecommerce.domains.customer.CustomerDTO;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
public interface CustomerService {

	public Customer createCustomer(Customer customer);

	public Customer updateCustomer(Customer customer);

	public CustomerDTO searchCustomer(String searchText, Pager pager);

	public List<Customer> searchCustomer(Customer custCriteria);

	public Customer searchCustomerDetails(BigInteger customerId);

	public void deleteCustomer(BigInteger customerId);

	public void deleteCustomers(List<BigInteger> customerIds, String username);
}
