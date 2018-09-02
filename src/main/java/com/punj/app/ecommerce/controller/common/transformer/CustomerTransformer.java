/**
 * 
 */
package com.punj.app.ecommerce.controller.common.transformer;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.punj.app.ecommerce.domains.customer.Customer;
import com.punj.app.ecommerce.domains.customer.CustomerDTO;
import com.punj.app.ecommerce.domains.payment.AccountHead;
import com.punj.app.ecommerce.domains.user.Address;
import com.punj.app.ecommerce.models.common.AddressBean;
import com.punj.app.ecommerce.models.customer.CustomerBean;
import com.punj.app.ecommerce.models.customer.CustomerBeanDTO;
import com.punj.app.ecommerce.models.financials.AccountHeadBean;

/**
 * @author admin
 *
 */
public class CustomerTransformer {

	private static final Logger logger = LogManager.getLogger();

	private CustomerTransformer() {
		throw new IllegalStateException("CustomerTransformer class");
	}

	public static CustomerBeanDTO transformCustomerDTO(CustomerDTO customerDTO) {

		CustomerBeanDTO customerBeanDTO = new CustomerBeanDTO();

		List<Customer> customers = customerDTO.getCustomers();
		List<CustomerBean> customerBeans = new ArrayList<>(customers.size());
		CustomerBean customerBean = null;

		for (Customer customer : customers) {
			customerBean = CustomerTransformer.transformCustomer(customer);
			customerBeans.add(customerBean);
		}
		customerBeanDTO.setCustomers(customerBeans);

		logger.info("The customer details from database has been transformed successfully");
		return customerBeanDTO;
	}

	/**
	 * This method is used to set the Domain objects so that the data can be persisted to the database
	 * 
	 * @param customer
	 * @return The customer bean object
	 */
	public static CustomerBean transformCustomer(Customer customer) {
		CustomerBean customerBean = new CustomerBean();

		customerBean.setCustomerId(customer.getCustomerId());
		customerBean.setName(customer.getName());
		customerBean.setEmail(customer.getEmail());
		customerBean.setPhone(customer.getPhone());
		customerBean.setPhone2(customer.getPhone2());
		customerBean.setGstNo(customer.getGstNo());
		customerBean.setPanNo(customer.getPanNo());
		
		if(customer.getGstNo()!=null && customer.getGstNo().length()==15) {
			customerBean.setStateCode(customer.getGstNo().substring(0, 2));
		}
		
		customerBean.setCreatedBy(customer.getCreatedBy());
		customerBean.setCreatedDate(customer.getCreatedDate());
		customerBean.setModifiedBy(customer.getModifiedBy());
		customerBean.setModifiedDate(customer.getModifiedDate());

		List<AddressBean> customerAddresses = CommonMVCTransformer.transformAddresses(customer.getAddresses());
		customerBean.setAddresses(customerAddresses);

		customerBean.setPrimaryAddressIndex(CommonMVCTransformer.getPrimaryAddressIndex(customerBean.getAddresses()));
		if (customerBean.getPrimaryAddressIndex() > -1)
			customerBean.setPrimaryAddress(CommonMVCTransformer.getPrimaryAddress(customer.getAddresses()));

		logger.info("The customer details has been updated in bean object now");
		return customerBean;
	}

	public static Customer transformCustomerBean(CustomerBean customerBean) {
		Customer customer = new Customer();

		customer.setCustomerId(customerBean.getCustomerId());
		customer.setName(customerBean.getName());
		customer.setEmail(customerBean.getEmail());
		customer.setPhone(customerBean.getPhone());
		customer.setPhone2(customerBean.getPhone2());
		customer.setGstNo(customerBean.getGstNo());
		customer.setPanNo(customerBean.getPanNo());

		customer.setCreatedBy(customerBean.getCreatedBy());
		customer.setCreatedDate(customerBean.getCreatedDate());

		List<AddressBean> customerAddresses = customerBean.getAddresses();
		AddressBean primaryAddress = customerAddresses.get(customerBean.getPrimaryAddressIndex());
		primaryAddress.setPrimary("Y");

		List<Address> customerAddressList = CommonMVCTransformer.transformAddressList(customerAddresses);
		customer.setAddresses(customerAddressList);
		logger.info("The customer details has been updated in domain object now");
		return customer;
	}

	public static List<CustomerBean> transformCustomers(List<Customer> customerList, Map<BigInteger, List<AccountHead>> customerAccounts) {

		List<CustomerBean> customerBeanList = new ArrayList<>(customerList.size());
		CustomerBean customerBean;
		Boolean isCustomerAccountExisting = Boolean.FALSE;
		BigInteger customerId = null;
		String customerIdStr = null;
		List<AccountHeadBean> customerAccountBeans = null;
		List<AccountHead> customerAccountList = null;
		AccountHead customerAccount = null;
		AccountHeadBean customerAccountBean = null;
		if (customerAccounts != null && !customerAccounts.isEmpty()) {
			isCustomerAccountExisting = Boolean.TRUE;
		}
		for (Customer customer : customerList) {
			customerBean = CustomerTransformer.transformCustomer(customer);
			if (isCustomerAccountExisting) {
				customerIdStr = customer.getCustomerId().toString();
				customerId = new BigInteger(customerIdStr);
				customerAccountList = customerAccounts.get(customerId);
				if (customerAccountList != null && !customerAccountList.isEmpty()) {
					customerAccountBeans = SupplierTransformer.transformAccountHeads(customerAccountList, customer.getName());
					customerBean.setCustomerAccounts(customerAccountBeans);
				}
			}
			customerBeanList.add(customerBean);
		}
		logger.info("The transformed customer list details has been customer bean list");
		return customerBeanList;
	}

	public static List<CustomerBean> transformCustomers(List<Customer> customerList) {

		List<CustomerBean> customerBeanList = new ArrayList<>(customerList.size());
		CustomerBean customerBean;
		for (Customer customer : customerList) {
			customerBean = CustomerTransformer.transformCustomer(customer);
			customerBeanList.add(customerBean);
		}
		logger.info("The transformed customer list details has been customer bean list");
		return customerBeanList;
	}

	public static List<BigInteger> retrieveEligibleCustomers(CustomerBeanDTO customerDTO) {
		List<BigInteger> finalCustomerIds = new ArrayList<>();
		List<String> selectedIds = customerDTO.getCustomerIds();
		BigInteger customerId = null;
		Integer customerIndex = null;
		CustomerBean customerBean = null;
		for (String selectedId : selectedIds) {
			String[] splittedVals = selectedId.split("_");
			customerId = new BigInteger(splittedVals[0]);
			customerIndex = new Integer(splittedVals[1]);
			finalCustomerIds.add(customerId);
		}
		logger.info("The select customer ids and index for bulk operations has been transformed successfully");
		return finalCustomerIds;
	}

}
