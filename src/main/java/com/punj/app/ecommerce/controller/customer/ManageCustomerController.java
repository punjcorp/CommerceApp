package com.punj.app.ecommerce.controller.customer;
/**
 * 
 */

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.CommonMVCTransformer;
import com.punj.app.ecommerce.controller.common.transformer.CustomerTransformer;
import com.punj.app.ecommerce.domains.common.State;
import com.punj.app.ecommerce.domains.customer.Customer;
import com.punj.app.ecommerce.domains.customer.CustomerDTO;
import com.punj.app.ecommerce.models.common.CustomerAddressBean;
import com.punj.app.ecommerce.models.common.LocationBean;
import com.punj.app.ecommerce.models.common.SearchBean;
import com.punj.app.ecommerce.models.common.validator.ValidationGroup;
import com.punj.app.ecommerce.models.customer.CustomerBean;
import com.punj.app.ecommerce.models.customer.CustomerBeanDTO;
import com.punj.app.ecommerce.services.CustomerService;
import com.punj.app.ecommerce.services.common.CommonService;
import com.punj.app.ecommerce.services.common.dtos.LocationDTO;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class ManageCustomerController {
	private static final Logger logger = LogManager.getLogger();
	private CustomerService customerService;
	private CommonService commonService;
	private MessageSource messageSource;

	/**
	 * @param customerService
	 *            the customerService to set
	 */
	@Autowired
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	/**
	 * @param customerService
	 *            the customerService to set
	 */
	@Autowired
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	/**
	 * @param messageSource
	 *            the messageSource to set
	 */
	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@GetMapping(value = ViewPathConstants.ADD_CUSTOMER_URL)
	public String addCustomer(Model model, HttpSession session, Locale locale) {
		logger.info("The add method for new supplier has been called");
		try {
			CustomerBean customerBean = new CustomerBean();

			CustomerAddressBean addressBean = new CustomerAddressBean();
			List<CustomerAddressBean> addresses = new ArrayList<>();
			addresses.add(addressBean);

			customerBean.setAddresses(addresses);

			this.updateModel(model, customerBean);

			logger.info("The empty customer object bean has been created");
		} catch (Exception e) {
			model.addAttribute(MVCConstants.ALERT, messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			logger.error("An unknown error has occurred while creating empty customer.", e);
		}

		return ViewPathConstants.ADD_CUSTOMER_PAGE;

	}

	private void updateModel(Model model, CustomerBean customerBean) {

		model.addAttribute(MVCConstants.CUSTOMER_BEAN, customerBean);

		List<State> states = this.commonService.retrieveAllStates();
		model.addAttribute(MVCConstants.STATES_LIST, states);

		LocationDTO locationDTO = this.commonService.retrieveLocationWithDailyStatus();
		List<LocationBean> locations = CommonMVCTransformer.transformLocationDTO(locationDTO);

		model.addAttribute(MVCConstants.LOCATION_BEANS, locations);

		logger.info("The customer model object has been updated");
	}

	private void updateShortModel(Model model, CustomerBean customerBean) {

		model.addAttribute(MVCConstants.CUSTOMER_BEAN, customerBean);

		List<State> states = this.commonService.retrieveAllStates();
		model.addAttribute(MVCConstants.STATES_LIST, states);

		LocationDTO locationDTO = this.commonService.retrieveLocationWithDailyStatus();
		List<LocationBean> locations = CommonMVCTransformer.transformLocationDTO(locationDTO);

		model.addAttribute(MVCConstants.LOCATION_BEANS, locations);

		logger.info("The customer model object has been updated");
	}

	@PostMapping(value = ViewPathConstants.ADD_CUSTOMER_URL, params = { MVCConstants.SAVE_SHORT_CUSTOMER_PARAM })
	public String saveShortCustomer(@ModelAttribute("customerBean") @Validated(ValidationGroup.ValidationGroupTwo.class) CustomerBean customerBean, BindingResult bindingResult,
			Model model, HttpSession session, Locale locale, Authentication authentication) {
		if (bindingResult.hasErrors()) {
			this.updateShortModel(model, customerBean);
			return ViewPathConstants.ADD_CUSTOMER_PAGE;
		}
		try {

			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			Customer customer = CustomerTransformer.transformCustomerBean(customerBean);
			this.updateCustomerStatus(customer, MVCConstants.ACTION_NEW, userDetails.getUsername());
			customer = this.customerService.createCustomer(customer);

			CustomerBean customerGSTBean = CustomerTransformer.transformCustomer(customer);
			model.addAttribute(MVCConstants.SUCCESS,
					messageSource.getMessage("commerce.screen.customer.add.success", new Object[] { customer.getCustomerId(), customer.getName() }, locale));
			model.addAttribute(MVCConstants.CUSTOMER_BEAN, customerGSTBean);
			logger.info("The customer details has been created successfully.");
		} catch (Exception e) {
			model.addAttribute(MVCConstants.ALERT, messageSource.getMessage("commerce.screen.customer.add.failure", null, locale));
			logger.error("There is an error while updating customer details", e);
		}
		return ViewPathConstants.ADD_CUSTOMER_PAGE;
	}

	@PostMapping(value = ViewPathConstants.EDIT_CUSTOMER_URL, params = { MVCConstants.SAVE_SHORT_CUSTOMER_PARAM })
	public String saveShortCustomerAfterEdit(@ModelAttribute("customerBean") @Validated(ValidationGroup.ValidationGroupTwo.class) CustomerBean customerBean, BindingResult bindingResult, Model model, HttpSession session,
			Locale locale, Authentication authentication) {
		if (bindingResult.hasErrors()) {
			this.updateShortModel(model, customerBean);
			return ViewPathConstants.EDIT_CUSTOMER_PAGE;
		}
		try {

			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			Customer customer = CustomerTransformer.transformCustomerBean(customerBean);
			this.updateCustomerStatus(customer, MVCConstants.ACTION_EDIT, userDetails.getUsername());
			customer = this.customerService.updateCustomer(customer);

			model.addAttribute(MVCConstants.SUCCESS,
					messageSource.getMessage("commerce.screen.customer.add.success", new Object[] { customer.getCustomerId(), customer.getName() }, locale));
			customer = this.customerService.searchCustomerDetails(customer.getCustomerId());
			CustomerBean customerGSTBean = CustomerTransformer.transformCustomer(customer);
			this.updateModel(model, customerGSTBean);

			logger.info("The customer details has been saved after edit successfully.");
		} catch (Exception e) {
			model.addAttribute(MVCConstants.ALERT, messageSource.getMessage("commerce.screen.customer.edit.failure", null, locale));
			logger.error("There is an error while saving customer after editing", e);

		}
		return ViewPathConstants.EDIT_CUSTOMER_PAGE;
	}

	@PostMapping(value = ViewPathConstants.ADD_CUSTOMER_URL, params = { MVCConstants.SAVE_CUSTOMER_PARAM })
	public String saveCustomer(@ModelAttribute("customerBean") @Validated(ValidationGroup.ValidationGroupThree.class) CustomerBean customerBean, BindingResult bindingResult,
			Model model, HttpSession session, Locale locale, Authentication authentication) {
		if (bindingResult.hasErrors()) {
			this.updateModel(model, customerBean);
			return ViewPathConstants.ADD_CUSTOMER_PAGE;
		}
		try {

			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			Customer customer = CustomerTransformer.transformCustomerBean(customerBean);
			this.updateCustomerStatus(customer, MVCConstants.ACTION_NEW, userDetails.getUsername());
			customer = this.customerService.createCustomer(customer);

			customerBean.setCustomerId(customer.getCustomerId());
			model.addAttribute(MVCConstants.SUCCESS,
					messageSource.getMessage("commerce.screen.customer.add.success", new Object[] { customer.getCustomerId(), customer.getName() }, locale));
			model.addAttribute(MVCConstants.CUSTOMER_BEAN, customerBean);
			logger.info("The customer details has been created successfully.");
		} catch (Exception e) {
			model.addAttribute(MVCConstants.ALERT, messageSource.getMessage("commerce.screen.customer.add.failure", null, locale));
			logger.error("There is an error while updating customer details", e);
		}
		return ViewPathConstants.ADD_CUSTOMER_PAGE;
	}

	/**
	 * This method is used to set the status and other fields of customer based on operation performed
	 * 
	 * @param customer
	 */
	private Customer updateCustomerStatus(Customer customer, String operation, String username) {

		if (operation.equals(MVCConstants.ACTION_NEW)) {
			customer.setCreatedBy(username);
			customer.setCreatedDate(LocalDateTime.now());

		} else if (operation.equals(MVCConstants.ACTION_EDIT)) {

			customer.setModifiedBy(username);
			customer.setModifiedDate(LocalDateTime.now());
		}

		logger.info("The customer details has been updated in domain object now");
		return customer;
	}

	@GetMapping(ViewPathConstants.EDIT_CUSTOMER_URL)
	public String editCustomer(Model model, HttpSession session, final HttpServletRequest req, Locale locale) {
		try {
			BigInteger customerId = new BigInteger(req.getParameter(MVCConstants.CUSTOMER_ID_PARAM));

			Customer customer = this.customerService.searchCustomerDetails(customerId);
			logger.info("The requested customer details retrieved successfully");

			CustomerBean customerBean = CustomerTransformer.transformCustomer(customer);
			if(customerBean.getShortFlag().equals(MVCConstants.FLAG_TRUE)) {
				List<CustomerAddressBean> addresses=new ArrayList<>();
				addresses.add(new CustomerAddressBean());
				customerBean.setAddresses(addresses);
			}
			this.updateModel(model, customerBean);
			logger.info("The requested customer for updation has been set in bean object for display");
		} catch (Exception e) {
			model.addAttribute(MVCConstants.ALERT, messageSource.getMessage(MVCConstants.ERROR_MSG, null, locale));
			logger.error("There is an error while retrieving customer for editing", e);
		}
		return ViewPathConstants.EDIT_CUSTOMER_PAGE;
	}

	@PostMapping(value = ViewPathConstants.EDIT_CUSTOMER_URL, params = { MVCConstants.SAVE_CUSTOMER_PARAM })
	public String saveCustomerAfterEdit(@ModelAttribute("customerBean") @Validated(ValidationGroup.ValidationGroupThree.class)  CustomerBean customerBean, BindingResult bindingResult, Model model, HttpSession session,
			Locale locale, Authentication authentication) {
		if (bindingResult.hasErrors()) {
			this.updateModel(model, customerBean);
			return ViewPathConstants.EDIT_CUSTOMER_PAGE;
		}
		try {

			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			Customer customer = CustomerTransformer.transformCustomerBean(customerBean);
			this.updateCustomerStatus(customer, MVCConstants.ACTION_EDIT, userDetails.getUsername());
			customer = this.customerService.updateCustomer(customer);

			model.addAttribute(MVCConstants.SUCCESS,
					messageSource.getMessage("commerce.screen.customer.add.success", new Object[] { customer.getCustomerId(), customer.getName() }, locale));
			customer = this.customerService.searchCustomerDetails(customer.getCustomerId());
			customerBean = CustomerTransformer.transformCustomer(customer);
			this.updateModel(model, customerBean);

			logger.info("The customer details has been saved after edit successfully.");
		} catch (Exception e) {
			model.addAttribute(MVCConstants.ALERT, messageSource.getMessage("commerce.screen.customer.edit.failure", null, locale));
			logger.error("There is an error while saving customer after editing", e);

		}
		return ViewPathConstants.EDIT_CUSTOMER_PAGE;
	}

	@GetMapping(ViewPathConstants.DELETE_CUSTOMER_URL)
	public String deleteCustomer(Model model, HttpSession session, RedirectAttributes redirectAttrs, final HttpServletRequest req, Locale locale) {
		try {
			BigInteger customerId = new BigInteger(req.getParameter(MVCConstants.CUSTOMER_ID_PARAM));

			this.customerService.deleteCustomer(customerId);

			redirectAttrs.addFlashAttribute(MVCConstants.SUCCESS, messageSource.getMessage("commerce.screen.customer.delete.success", new Object[] { customerId }, locale));
			req.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);

			logger.info("The requested customer has been deleted successfully");
		} catch (Exception e) {
			redirectAttrs.addFlashAttribute(MVCConstants.ALERT, messageSource.getMessage("commerce.screen.customer.delete.failure", null, locale));
			logger.error("There is an error while deleteing customer", e);
		}
		return ViewPathConstants.REDIRECT_URL + ViewPathConstants.MANAGE_CUSTOMER_URL;
	}

	@GetMapping(value = ViewPathConstants.MANAGE_CUSTOMER_URL)
	public String manageSupplier(Model model, HttpSession session, final HttpServletRequest req) {
		logger.info("The manage customer method for customer management has been called.");
		model.addAttribute("searchBean", new SearchBean());
		return ViewPathConstants.MANAGE_CUSTOMER_PAGE;

	}

	@PostMapping(ViewPathConstants.MANAGE_CUSTOMER_URL)
	public String searchCustomerDetails(@ModelAttribute @Valid SearchBean searchBean, BindingResult bindingResult, @RequestParam(MVCConstants.PAGE_PARAM) Optional<Integer> page,
			Model model, Locale locale) {
		if (bindingResult.hasErrors() && !model.containsAttribute(MVCConstants.SUCCESS)) {
			return ViewPathConstants.MANAGE_CUSTOMER_PAGE;
		}
		try {
			if (!model.containsAttribute(MVCConstants.SUCCESS)) {

				Pager pager = new Pager();
				if (!page.isPresent()) {
					pager.setCurrentPageNo(1);
				} else {
					pager.setCurrentPageNo(page.get());
				}

				CustomerDTO customerDTO = this.customerService.searchCustomer(searchBean.getSearchText(), pager);
				List<Customer> customersList = customerDTO.getCustomers();

				if (customersList != null && !customersList.isEmpty()) {
					CustomerBeanDTO customerBeanDTO = CustomerTransformer.transformCustomerDTO(customerDTO);

					Pager tmpPager = customerDTO.getPager();
					pager = new Pager(tmpPager.getResultSize(), tmpPager.getPageSize(), tmpPager.getCurrentPageNo(), tmpPager.getMaxDisplayPage(),
							ViewPathConstants.MANAGE_CUSTOMER_URL);

					model.addAttribute(MVCConstants.CUSTOMERS_BEAN, customerBeanDTO);
					model.addAttribute(MVCConstants.PAGER, pager);
					model.addAttribute(MVCConstants.SUCCESS, messageSource.getMessage("commerce.screen.common.search.success",
							new Object[] { pager.getResultSize(), pager.getCurrentPageNo(), pager.getNoOfPages() }, locale));
					logger.info("The customer details has been retrieved successfully.");
				}

			} else {
				model.addAttribute("org.springframework.validation.BindingResult.searchBean", null);
			}
			model.addAttribute(MVCConstants.CUSTOMER_BEAN, searchBean);
		} catch (Exception e) {
			model.addAttribute(MVCConstants.ALERT, "There is some error while retrieving customers");
			logger.error("There is an error while searching for customers", e);
		}
		return ViewPathConstants.MANAGE_CUSTOMER_PAGE;
	}

	@PostMapping(value = ViewPathConstants.BULK_CUSTOMER_URL, params = { MVCConstants.DELETE_CUSTOMERS_PARAM })
	public String deleteOrders(@ModelAttribute CustomerBeanDTO customers, RedirectAttributes redirectAttrs, final HttpServletRequest req, Locale locale,
			Authentication authentication) {
		try {
			req.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			List<BigInteger> customerIds = CustomerTransformer.retrieveEligibleCustomers(customers);
			if (customerIds != null && !customerIds.isEmpty()) {
				this.customerService.deleteCustomers(customerIds, userDetails.getUsername());
				redirectAttrs.addFlashAttribute(MVCConstants.SUCCESS, messageSource.getMessage("commerce.screen.customer.delete.all.success", null, locale));
				logger.info("The bulk delete operation for customers is completed.");
			} else {
				redirectAttrs.addFlashAttribute(MVCConstants.ALERT, messageSource.getMessage("commerce.screen.customer.delete.no.records", null, locale));
				logger.info("The bulk delete operation for customers is completed.");
			}

		} catch (Exception e) {
			redirectAttrs.addFlashAttribute(MVCConstants.ALERT, messageSource.getMessage("commerce.screen.customer.delete.failure", null, locale));
			logger.error("There is an error while deleting bulk customers", e);
		}

		return ViewPathConstants.REDIRECT_URL + ViewPathConstants.MANAGE_CUSTOMER_URL;
	}

}
