package com.punj.app.ecommerce.controller.lookup;
/**
 * 
 */

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.CommonMVCTransformer;
import com.punj.app.ecommerce.domains.common.Location;
import com.punj.app.ecommerce.models.common.LocationBean;
import com.punj.app.ecommerce.models.supplier.SupplierLookupDTO;
import com.punj.app.ecommerce.services.AccountService;
import com.punj.app.ecommerce.services.common.CommonService;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class AccountLookupController {
	private static final Logger logger = LogManager.getLogger();
	private AccountService accountService;
	private CommonService commonService;

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
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	@GetMapping(value = ViewPathConstants.LOOKUP_ACCOUNT_URL)
	public String lookupSupplierAccount(Model model) {
		try {
			SupplierLookupDTO accountDTO = new SupplierLookupDTO();
			
			List<Location> locations = this.commonService.retrieveAllLocations();
			if (locations != null && !locations.isEmpty()) {
				List<LocationBean> locationBeans = CommonMVCTransformer.transformLocationList(locations, Boolean.TRUE);
				accountDTO.setLocations(locationBeans);
			}
			
			model.addAttribute(MVCConstants.ACCOUNT_DTO, accountDTO);
			logger.info("The Supplier Account lookup screen is ready for the tran");
		} catch (Exception e) {
			logger.error("There is an error while preparing Supplier account lookup screen for display", e);
		}
		return ViewPathConstants.LOOKUP_ACCOUNT_PAGE;
	}

}
