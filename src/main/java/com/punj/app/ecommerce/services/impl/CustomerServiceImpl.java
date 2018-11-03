/**
 * 
 */
package com.punj.app.ecommerce.services.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.punj.app.ecommerce.domains.common.Location;
import com.punj.app.ecommerce.domains.customer.Customer;
import com.punj.app.ecommerce.domains.customer.CustomerAddress;
import com.punj.app.ecommerce.domains.customer.CustomerDTO;
import com.punj.app.ecommerce.domains.customer.ids.CustomerAddressId;
import com.punj.app.ecommerce.domains.payment.AccountHead;
import com.punj.app.ecommerce.domains.user.Address;
import com.punj.app.ecommerce.repositories.AddressRepository;
import com.punj.app.ecommerce.repositories.customer.CustomerAddressRepository;
import com.punj.app.ecommerce.repositories.customer.CustomerRepository;
import com.punj.app.ecommerce.repositories.customer.CustomerSearchRepository;
import com.punj.app.ecommerce.services.CustomerService;
import com.punj.app.ecommerce.services.PaymentAccountService;
import com.punj.app.ecommerce.services.common.CommonService;
import com.punj.app.ecommerce.services.common.ServiceConstants;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
@Service
public class CustomerServiceImpl implements CustomerService {

	private static final Logger logger = LogManager.getLogger();
	private CustomerRepository customerRepository;
	private CustomerAddressRepository customerAddressRepository;
	private AddressRepository addressRepository;
	private CustomerSearchRepository customerSearchRepository;
	private PaymentAccountService customerAccountService;
	private CommonService commonService;

	@Value("${commerce.list.max.perpage}")
	private Integer maxResultPerPage;

	@Value("${commerce.list.max.pageno}")
	private Integer maxPageBtns;

	/**
	 * @param addressRepository
	 *            the addressRepository to set
	 */
	@Autowired
	public void setAddressRepository(AddressRepository addressRepository) {
		this.addressRepository = addressRepository;
	}

	/**
	 * @param customerAddressRepository
	 *            the customerAddressRepository to set
	 */
	@Autowired
	public void setCustomerAddressRepository(CustomerAddressRepository customerAddressRepository) {
		this.customerAddressRepository = customerAddressRepository;
	}

	/**
	 * @param commonService
	 *            the commonService to set
	 */
	@Autowired
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	/**
	 * @param customerRepository
	 *            the customerRepository to set
	 */
	@Autowired
	public void setCustomerRepository(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	/**
	 * @param customerSearchRepository
	 *            the customerSearchRepository to set
	 */
	@Autowired
	public void setCustomerSearchRepository(CustomerSearchRepository customerSearchRepository) {
		this.customerSearchRepository = customerSearchRepository;
	}

	/**
	 * @param customerAccountService
	 *            the customerAccountService to set
	 */
	@Autowired
	public void setPaymentAccountService(PaymentAccountService customerAccountService) {
		this.customerAccountService = customerAccountService;
	}

	@Override
	@Transactional
	public Customer createCustomer(Customer customer) {
		AccountHead customerAccount = null;
		customer = this.customerRepository.save(customer);
		if (customer != null) {
			logger.info("The customer details has been saved successfully");
			List<Location> locations = this.commonService.retrieveAllLocations();
			for (Location location : locations) {
				logger.info("The customer account creation process has started");
				customerAccount = new AccountHead();
				customerAccount.setAdvanceAmount(BigDecimal.ZERO);
				customerAccount.setCreatedBy(customer.getCreatedBy());
				customerAccount.setCreatedDate(LocalDateTime.now());
				customerAccount.setDueAmount(BigDecimal.ZERO);
				customerAccount.setEntityId(customer.getCustomerId());
				customerAccount.setEntityType(ServiceConstants.ACCOUNT_TYPE_CUSTOMER);
				customerAccount.setLocationId(location.getLocationId());
				customerAccount = this.customerAccountService.setupPaymentAccount(customerAccount);
				if (customerAccount != null)
					logger.info("The customer account creation for location {} process was successful", location.getLocationId());
				else
					logger.info("There was some issue while setting up customer account");
			}

		} else {
			logger.info("There were no details saved");
		}
		return customer;
	}

	@Override
	public Customer updateCustomer(Customer customer) {

		if(customer.getAddresses()!=null && !customer.getAddresses().isEmpty())
			this.deleteCustomerAddresses(customer);

		customer = this.customerRepository.save(customer);
		if (customer != null) {
			logger.info("The customer details has been saved successfully");

		} else {
			logger.info("There were no details saved");
		}
		return customer;
	}

	@Transactional
	private void deleteCustomerAddresses(Customer customer) {
		CustomerAddress filterCriteria = new CustomerAddress();
		CustomerAddressId filterCriteriaId = new CustomerAddressId();
		filterCriteriaId.setCustomerId(customer.getCustomerId());
		filterCriteria.setCustomerAddressId(filterCriteriaId);

		List<CustomerAddress> customerAddresses = this.customerAddressRepository.findAll(Example.of(filterCriteria));
		if (customerAddresses != null && !customerAddresses.isEmpty()) {
			
			List<CustomerAddress> filteredList =this.filterCustomerAddresses(customer.getAddresses(), customerAddresses);
			
			this.customerAddressRepository.delete(filteredList);
			logger.info("The filtered customer address details has been deleted successfully");
			
			for(CustomerAddress customerAddress : filteredList) {
				this.addressRepository.delete(customerAddress.getCustomerAddressId().getAddress());
			}
			logger.info("The filtered orphan address details has been deleted successfully");
		}
	}

	private List<CustomerAddress> filterCustomerAddresses(List<Address> newAddresses, List<CustomerAddress> customerAddresses) {
		List<CustomerAddress> filteredList = new ArrayList<>();
		Boolean isAddable = Boolean.FALSE;
		for (CustomerAddress customerAddress : customerAddresses) {
			isAddable = Boolean.FALSE;
			for (Address address : newAddresses) {
				if (customerAddress.getCustomerAddressId().getAddress().getAddressId().equals(address.getAddressId())) {
					isAddable = Boolean.TRUE;
					break;
				}
			}
			if (!isAddable) {
				filteredList.add(customerAddress);
			}
		}
		logger.info("The customer addresses for deletion has been filtered successfully");

		return filteredList;
	}

	@Override
	public CustomerDTO searchCustomer(String searchText, Pager pager) {
		int startCount = (pager.getCurrentPageNo() - 1) * maxResultPerPage;
		pager.setPageSize(maxResultPerPage);
		pager.setStartCount(startCount);
		pager.setMaxDisplayPage(maxPageBtns);

		CustomerDTO customerDTO = this.customerSearchRepository.search(searchText, pager);
		if (customerDTO != null) {
			logger.info("The customers has been retrieved based on text criteria successfully");
		} else {
			logger.info("There were no customers found based on text criteria");
		}
		return customerDTO;
	}

	@Override
	public List<Customer> searchCustomer(Customer custCriteria) {
		List<Customer> customerList = this.customerRepository.findAll(Example.of(custCriteria));
		if (customerList != null && !customerList.isEmpty()) {
			logger.info("The customers has been retrieved successfully");
		} else {
			logger.info("There were no customers found based on criteria");
		}
		return customerList;
	}

	@Override
	public Customer searchCustomerDetails(BigInteger customerId) {
		Customer customer = this.customerRepository.findOne(customerId);
		if (customer != null)
			logger.info("The customer details were found for customer id -> ", customerId);
		else
			logger.info("There is no customer existing for the provided id");
		return customer;
	}

	@Override
	public void deleteCustomer(BigInteger customerId) {

		this.customerRepository.delete(customerId);
		logger.info("The customer with customer id has been deleted successfully-> ", customerId);
	}

	@Override
	@Transactional
	public void deleteCustomers(List<BigInteger> customerIds, String username) {

		for(BigInteger customerId: customerIds) {
			this.deleteCustomer(customerId);
			
		}
		logger.info("All the provided customers has been deleted successfully ");
	}	
	
}
