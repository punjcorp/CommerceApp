package com.punj.app.ecommerce.controller.lookup;
/**
 * 
 */

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.AccountTransformer;
import com.punj.app.ecommerce.domains.customer.CustomerDTO;
import com.punj.app.ecommerce.models.common.validator.ValidationGroup;
import com.punj.app.ecommerce.models.customer.CustomerBean;
import com.punj.app.ecommerce.models.customer.CustomerBeanDTO;
import com.punj.app.ecommerce.services.AccountService;
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
	public List<CustomerBean> lookupCustomers(@RequestBody @Validated(ValidationGroup.ValidationGroupOne.class) CustomerBean customerBean,
			BindingResult bindingResult, Model model, HttpSession session) {
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

}
