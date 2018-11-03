/**
 * 
 */
package com.punj.app.ecommerce.controller.common.transformer;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.domains.customer.Customer;
import com.punj.app.ecommerce.domains.customer.CustomerDTO;
import com.punj.app.ecommerce.domains.payment.AccountHead;
import com.punj.app.ecommerce.domains.user.Address;
import com.punj.app.ecommerce.models.common.CustomerAddressBean;
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

		if (StringUtils.isBlank(customer.getGstNo())) {
			customerBean.setShortFlag(MVCConstants.FLAG_TRUE);
		}
		else {
			customerBean.setShortFlag(MVCConstants.FLAG_FALSE);
			customerBean.setPhone2(customer.getPhone2());
			customerBean.setGstNo(customer.getGstNo());
			customerBean.setPanNo(customer.getPanNo());
		}
		if (StringUtils.isNotBlank(customer.getGstNo()) && customer.getGstNo().length() == 15) {
			customerBean.setStateCode(customer.getGstNo().substring(0, 2));
		}

		customerBean.setCreatedBy(customer.getCreatedBy());
		customerBean.setCreatedDate(customer.getCreatedDate());
		customerBean.setModifiedBy(customer.getModifiedBy());
		customerBean.setModifiedDate(customer.getModifiedDate());

		if (customer.getAddresses() != null && !customer.getAddresses().isEmpty()) {
			List<CustomerAddressBean> customerAddresses = CustomerTransformer.transformAddresses(customer.getAddresses());
			customerBean.setAddresses(customerAddresses);

			customerBean.setPrimaryAddressIndex(CustomerTransformer.getPrimaryAddressIndex(customerBean.getAddresses()));
			if (customerBean.getPrimaryAddressIndex() > -1)
				customerBean.setPrimaryAddress(CommonMVCTransformer.getPrimaryAddress(customer.getAddresses()));
		}

		logger.info("The customer details has been updated in bean object now");
		return customerBean;
	}

	public static int getPrimaryAddressIndex(List<CustomerAddressBean> addressBeanList) {
		int result = -1;
		for (int i = 0; i < addressBeanList.size(); i++) {
			if ("Y".equals(addressBeanList.get(i).getPrimary())) {
				result = i;
			}
		}
		return result;

	}

	public static List<CustomerAddressBean> transformAddresses(List<Address> addressList) {
		List<CustomerAddressBean> addressBeanList = new ArrayList<>(addressList.size());
		CustomerAddressBean addressBean;
		for (Address address : addressList) {
			addressBean = CustomerTransformer.transformAddress(address);
			addressBeanList.add(addressBean);
		}

		logger.info("The address list has been transformed into address bean list successfully");
		return addressBeanList;
	}

	public static CustomerAddressBean transformAddress(Address address) {
		CustomerAddressBean addressBean = new CustomerAddressBean();

		addressBean.setAddressId(address.getAddressId());
		addressBean.setPrimary(address.getPrimary());
		addressBean.setAddress1(address.getAddress1());
		addressBean.setAddress2(address.getAddress2());
		addressBean.setCity(address.getCity());
		addressBean.setState(address.getState());
		addressBean.setCountry(address.getCountry());
		addressBean.setPincode(address.getPincode());
		addressBean.setAddressType(address.getAddressType());

		logger.info("The address has been transformed into address bean successfully");
		return addressBean;
	}

	public static Customer transformCustomerBean(CustomerBean customerBean) {
		Customer customer = new Customer();

		customer.setCustomerId(customerBean.getCustomerId());
		customer.setName(customerBean.getName());
		customer.setEmail(customerBean.getEmail());
		customer.setPhone(customerBean.getPhone());
		if(StringUtils.isNotBlank(customerBean.getShortFlag()) && customerBean.getShortFlag().equals(MVCConstants.FLAG_FALSE)) {
			customer.setPhone2(customerBean.getPhone2());
			customer.setGstNo(customerBean.getGstNo());
			customer.setPanNo(customerBean.getPanNo());
			
			List<CustomerAddressBean> customerAddresses = customerBean.getAddresses();
			if (customerAddresses != null && !customerAddresses.isEmpty()) {
				CustomerAddressBean primaryAddress = customerAddresses.get(customerBean.getPrimaryAddressIndex());
				primaryAddress.setPrimary("Y");

				List<Address> customerAddressList = CustomerTransformer.transformAddressList(customerAddresses);
				customer.setAddresses(customerAddressList);
			}
			
		}
		

		customer.setCreatedBy(customerBean.getCreatedBy());
		customer.setCreatedDate(customerBean.getCreatedDate());
		customer.setModifiedBy(customerBean.getModifiedBy());
		customer.setModifiedDate(customerBean.getModifiedDate());
		
		logger.info("The customer details has been updated in domain object now");
		return customer;
	}

	public static Address transformAddress(CustomerAddressBean addressBean) {
		Address address = new Address();

		address.setPrimary(addressBean.getPrimary());
		address.setAddressId(addressBean.getAddressId());
		address.setAddress1(addressBean.getAddress1());
		address.setAddress2(addressBean.getAddress2());
		address.setCity(addressBean.getCity());
		address.setState(addressBean.getState());
		address.setCountry(addressBean.getCountry());
		address.setPincode(addressBean.getPincode());
		address.setAddressType(addressBean.getAddressType());

		logger.info("The address bean has been transformed into address object successfully");
		return address;
	}

	public static List<Address> transformAddressList(List<CustomerAddressBean> addressBeanList) {
		List<Address> addressList = new ArrayList<>(addressBeanList.size());
		Address address;
		for (CustomerAddressBean addressBean : addressBeanList) {
			address = CustomerTransformer.transformAddress(addressBean);
			addressList.add(address);
		}

		logger.info("The address beans list has been transformed into address list successfully");
		return addressList;
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
