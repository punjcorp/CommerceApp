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

import com.punj.app.ecommerce.domains.common.Location;
import com.punj.app.ecommerce.domains.customer.Customer;
import com.punj.app.ecommerce.domains.customer.CustomerDTO;
import com.punj.app.ecommerce.domains.payment.AccountHead;
import com.punj.app.ecommerce.repositories.customer.CustomerRepository;
import com.punj.app.ecommerce.repositories.customer.CustomerSearchRepository;
import com.punj.app.ecommerce.services.AccountService;
import com.punj.app.ecommerce.services.PaymentAccountService;
import com.punj.app.ecommerce.services.common.CommonService;
import com.punj.app.ecommerce.services.common.ServiceConstants;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
@Service
public class AccountServiceImpl implements AccountService {

	private static final Logger logger = LogManager.getLogger();
	private CustomerRepository customerRepository;
	private CustomerSearchRepository customerSearchRepository;
	private PaymentAccountService customerAccountService;
	private CommonService commonService;

	@Value("${commerce.list.max.customer.perpage}")
	private Integer maxResultPerPage;

	@Value("${commerce.list.max.pageno}")
	private Integer maxPageBtns;

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
	public AccountHead createCustomer(Customer customer, Integer locationId) {
		AccountHead customerAccount = null;
		customer = this.customerRepository.save(customer);
		if (customer != null) {
			logger.info("The customer details has been saved successfully");
			logger.info("The customer account creation process has started");
			customerAccount = new AccountHead();
			customerAccount.setAdvanceAmount(BigDecimal.ZERO);
			customerAccount.setCreatedBy(customer.getCreatedBy());
			customerAccount.setCreatedDate(LocalDateTime.now());
			customerAccount.setDueAmount(BigDecimal.ZERO);
			customerAccount.setEntityId(customer.getCustomerId());
			customerAccount.setEntityType(ServiceConstants.ACCOUNT_TYPE_CUSTOMER);
			customerAccount.setLocationId(locationId);
			customerAccount = this.customerAccountService.setupPaymentAccount(customerAccount);
			if (customerAccount != null)
				logger.info("The customer account creation process was successful");
			else
				logger.info("There was some issue while setting up customer account");
		} else {
			logger.info("There were no details saved");
		}
		return customerAccount;
	}

	@Override
	public List<AccountHead> createCustomer(Customer customer) {
		List<AccountHead> customerAccounts = new ArrayList<>();
		AccountHead customerAccount = null;
		customer = this.customerRepository.save(customer);
		if (customer != null) {
			logger.info("The customer details has been saved successfully");
			logger.info("The customer account creation process has started");

			List<Location> locations = this.commonService.retrieveAllLocations();
			if (locations != null && !locations.isEmpty()) {
				for (Location location : locations) {
					customerAccount = new AccountHead();
					customerAccount.setAdvanceAmount(BigDecimal.ZERO);
					customerAccount.setCreatedBy(customer.getCreatedBy());
					customerAccount.setCreatedDate(LocalDateTime.now());
					customerAccount.setDueAmount(BigDecimal.ZERO);
					customerAccount.setEntityId(customer.getCustomerId());
					customerAccount.setEntityType(ServiceConstants.ACCOUNT_TYPE_CUSTOMER);
					customerAccount.setLocationId(location.getLocationId());
					customerAccount = this.customerAccountService.setupPaymentAccount(customerAccount);
					if (customerAccount != null) {
						customerAccounts.add(customerAccount);
						logger.info("The customer account creation for location {} process was successful", location.getLocationId());
					} else {
						logger.info("There was some issue while setting up customer account");
					}
				}
			}

		} else {
			logger.info("There were no details saved");
		}
		return customerAccounts;
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
	public void addCustomerCreditDetails() {
		// TODO Auto-generated method stub

	}

}
