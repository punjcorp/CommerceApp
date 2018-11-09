package com.punj.app.ecommerce.controller.lookup;
/**
 * 
 */

import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.punj.app.ecommerce.common.web.CommerceConstants;
import com.punj.app.ecommerce.common.web.CommerceContext;
import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.AccountTransformer;
import com.punj.app.ecommerce.controller.common.transformer.CommonMVCTransformer;
import com.punj.app.ecommerce.controller.common.transformer.SupplierTransformer;
import com.punj.app.ecommerce.domains.common.Location;
import com.punj.app.ecommerce.domains.customer.Customer;
import com.punj.app.ecommerce.domains.customer.CustomerDTO;
import com.punj.app.ecommerce.domains.payment.AccountHead;
import com.punj.app.ecommerce.domains.payment.AccountJournal;
import com.punj.app.ecommerce.domains.tender.Tender;
import com.punj.app.ecommerce.models.common.LocationBean;
import com.punj.app.ecommerce.models.common.validator.ValidationGroup;
import com.punj.app.ecommerce.models.customer.CustomerBean;
import com.punj.app.ecommerce.models.customer.CustomerBeanDTO;
import com.punj.app.ecommerce.models.customer.CustomerLookupDTO;
import com.punj.app.ecommerce.models.financials.AccountHeadBean;
import com.punj.app.ecommerce.models.financials.AccountJournalBean;
import com.punj.app.ecommerce.services.AccountService;
import com.punj.app.ecommerce.services.PaymentAccountService;
import com.punj.app.ecommerce.services.common.CommonService;
import com.punj.app.ecommerce.services.common.ConfigService;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class CustomerLookupController {
	private static final Logger logger = LogManager.getLogger();
	private AccountService accountService;
	private PaymentAccountService paymentAccountService;
	private CommonService commonService;
	private CommerceContext commerceContext;
	private ConfigService configService;

	@Value("${commerce.default.location}")
	private Integer defaultLocation;

	/**
	 * @param configService
	 *            the configService to set
	 */
	@Autowired
	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}

	/**
	 * @param accountService
	 *            the accountService to set
	 */
	@Autowired
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	/**
	 * @param accountService
	 *            the accountService to set
	 */
	@Autowired
	public void setPaymentAccountService(PaymentAccountService paymentAccountService) {
		this.paymentAccountService = paymentAccountService;
	}

	/**
	 * @param commerceContext
	 *            the commerceContext to set
	 */
	@Autowired
	public void setCommerceContext(CommerceContext commerceContext) {
		this.commerceContext = commerceContext;
	}

	/**
	 * @param accountService
	 *            the accountService to set
	 */
	@Autowired
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	@PostMapping(value = ViewPathConstants.CUSTOMER_LOOKUP_URL, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public List<CustomerBean> lookupCustomers(@RequestBody @Validated(ValidationGroup.ValidationGroupOne.class) CustomerBean customerBean, BindingResult bindingResult, Model model,
			HttpSession session) {
		List<CustomerBean> customerBeans = null;
		if (bindingResult.hasErrors())
			return customerBeans;
		try {

			Pager pager = new Pager();
			pager.setCurrentPageNo(1);

			CustomerDTO customerDTO = this.accountService.searchCustomer(customerBean.getCustomerSearchText(), pager);
			if (customerDTO != null) {
				CustomerBeanDTO customerBeanDTO = AccountTransformer.transformCustomerDTO(customerDTO);
				customerBeans = customerBeanDTO.getCustomers();
				logger.info("The customer list based on the keyword has been retrieved");
			}

		} catch (Exception e) {
			logger.error("There is an error while retrieving customer for transaction screen", e);
			return customerBeans;
		}
		return customerBeans;
	}

	@PostMapping(value = ViewPathConstants.CUSTOMER_LOOKUP_ADMIN_URL, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public List<CustomerBean> lookupCustomersWithAccounts(@RequestBody @Validated(ValidationGroup.ValidationGroupOne.class) CustomerBean customerBean, BindingResult bindingResult,
			Model model, HttpSession session) {
		List<CustomerBean> customerBeans = null;
		if (bindingResult.hasErrors())
			return customerBeans;
		try {

			Pager pager = new Pager();
			pager.setCurrentPageNo(1);

			CustomerDTO customerDTO = this.accountService.searchCustomer(customerBean.getCustomerSearchText(), pager);

			if (customerDTO != null && customerBean.getSearchAccount() != null && customerBean.getSearchAccount()) {
				List<Customer> customerDtls = customerDTO.getCustomers();
				Map<BigInteger, List<AccountHead>> accounts = null;

				if (customerDtls != null && !customerDtls.isEmpty()) {
					Set<BigInteger> customerEntityIds = new HashSet<>();
					for (Customer customerDtl : customerDtls) {
						customerEntityIds.add(customerDtl.getCustomerId());
					}

					accounts = this.paymentAccountService.retrieveAccounts(customerEntityIds, MVCConstants.CUSTOMER);
					if (accounts != null && !accounts.isEmpty()) {
						customerBeans = AccountTransformer.transformCustomersWithAccounts(customerDtls, accounts);
						logger.info("The customer list based on the keyword has been retrieved with account details");
					} else {
						logger.info("The customer accounts were not retrieved for the provided customers");
					}

				} else {
					logger.info("There were no customers found for the provided searchText");
				}
			} else {
				logger.info("There were no customers found for the provided searchText");
			}

		} catch (Exception e) {
			logger.error("There is an error while retrieving customer for transaction screen", e);
			return customerBeans;
		}
		return customerBeans;
	}

	@GetMapping(value = ViewPathConstants.CUSTOMER_LOOKUP_ACCOUNT_URL)
	public String lookupCustomerAccount(Model model) {
		try {
			CustomerLookupDTO accountDTO = new CustomerLookupDTO();

			List<Location> locations = this.commonService.retrieveAllLocations();
			if (locations != null && !locations.isEmpty()) {
				List<LocationBean> locationBeans = CommonMVCTransformer.transformLocationList(locations, Boolean.TRUE);
				accountDTO.setLocations(locationBeans);
			}

			model.addAttribute(MVCConstants.ACCOUNT_DTO, accountDTO);
			logger.info("The Customer Account lookup screen is ready for the tran");
		} catch (Exception e) {
			logger.error("There is an error while preparing Customer account lookup screen for display", e);
		}
		return ViewPathConstants.CUSTOMER_LOOKUP_ACCOUNT_PAGE;
	}

	@PostMapping(value = ViewPathConstants.CUSTOMER_LOOKUP_ACCOUNT_URL)
	public String getCustomerAccountJournals(@ModelAttribute CustomerLookupDTO accountDTO, final BindingResult bindingResult, Model model, Locale locale) {
		try {

			if (accountDTO != null && accountDTO.getCustomerId() != null && accountDTO.getCustomer() != null) {

				Set<BigInteger> entityIds = new HashSet<>();
				BigInteger entityId = new BigInteger(accountDTO.getCustomerId().toString());
				entityIds.add(entityId);

				Map<BigInteger, List<AccountHead>> accountHeads = this.paymentAccountService.retrieveAccounts(entityIds, MVCConstants.CUSTOMER);
				if (accountHeads != null && !accountHeads.isEmpty()) {
					List<AccountHead> customerAccounts = accountHeads.get(entityId);
					if (customerAccounts != null && !customerAccounts.isEmpty()) {
						List<AccountHeadBean> customerAccountBeans = SupplierTransformer.transformAccountHeads(customerAccounts, accountDTO.getCustomer().getName());
						accountDTO.getCustomer().setCustomerAccounts(customerAccountBeans);
						Set<Integer> accountIds = new HashSet<>();
						for (AccountHead customerAccount : customerAccounts) {
							accountIds.add(customerAccount.getAccountId());
						}
						List<AccountJournal> accountJournals = this.paymentAccountService.retrievePaymentAccountJournals(accountIds);
						if (accountJournals != null && !accountJournals.isEmpty()) {
							String locationIdStr = this.configService.getAppConfigByKey(MVCConstants.APP_DEFAULT_LOCATION);
							this.defaultLocation = new Integer(locationIdStr);
							if (this.defaultLocation == null) {
								this.defaultLocation = (Integer) commerceContext.getStoreSettings(CommerceConstants.OPEN_LOC_ID);
							}
							Map<Integer, Tender> tenderMap = this.commonService.retrieveAllTendersAsMap(this.defaultLocation);
							List<AccountJournalBean> ajBeans = SupplierTransformer.transformAccountJournals(accountJournals, tenderMap, customerAccounts,
									accountDTO.getCustomer().getName());
							accountDTO.setJournals(ajBeans);
						}

					}

				}

			}

			List<Location> locations = this.commonService.retrieveAllLocations();
			if (locations != null && !locations.isEmpty()) {
				List<LocationBean> locationBeans = CommonMVCTransformer.transformLocationList(locations, Boolean.TRUE);
				accountDTO.setLocations(locationBeans);
			}

			model.addAttribute(MVCConstants.ACCOUNT_DTO, accountDTO);
			logger.info("The Customer Account lookup screen is ready for the tran");
		} catch (Exception e) {
			logger.error("There is an error while preparing Customer account lookup screen for display", e);
		}
		return ViewPathConstants.CUSTOMER_LOOKUP_ACCOUNT_PAGE;
	}

}
