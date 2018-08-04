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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.punj.app.ecommerce.common.web.CommerceConstants;
import com.punj.app.ecommerce.common.web.CommerceContext;
import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.ViewPathConstants;
import com.punj.app.ecommerce.controller.common.transformer.CommonMVCTransformer;
import com.punj.app.ecommerce.controller.common.transformer.SupplierTransformer;
import com.punj.app.ecommerce.domains.common.Location;
import com.punj.app.ecommerce.domains.payment.AccountHead;
import com.punj.app.ecommerce.domains.payment.AccountJournal;
import com.punj.app.ecommerce.domains.tender.Tender;
import com.punj.app.ecommerce.models.common.LocationBean;
import com.punj.app.ecommerce.models.financials.AccountHeadBean;
import com.punj.app.ecommerce.models.financials.AccountJournalBean;
import com.punj.app.ecommerce.models.supplier.SupplierLookupDTO;
import com.punj.app.ecommerce.services.PaymentAccountService;
import com.punj.app.ecommerce.services.common.CommonService;

/**
 * @author admin
 *
 */
@Controller
@EnableAutoConfiguration
public class AccountLookupController {
	private static final Logger logger = LogManager.getLogger();
	private PaymentAccountService accountService;
	private CommonService commonService;
	private CommerceContext commerceContext;

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
	public void setPaymentAccountService(PaymentAccountService accountService) {
		this.accountService = accountService;
	}
	
	/**
	 * @param commerceContext
	 *            the commerceContext to set
	 */
	@Autowired
	public void setCommerceContext(CommerceContext commerceContext) {
		this.commerceContext = commerceContext;
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

	@PostMapping(value = ViewPathConstants.LOOKUP_ACCOUNT_URL)
	public String getSupplierAccountJournals(@ModelAttribute SupplierLookupDTO accountDTO, final BindingResult bindingResult, Model model, Locale locale) {
		try {

			if (accountDTO != null && accountDTO.getSupplierId() != null && accountDTO.getSupplier()!=null) {

				Set<BigInteger> entityIds = new HashSet<>();
				BigInteger entityId = new BigInteger(accountDTO.getSupplierId().toString());
				entityIds.add(entityId);

				Map<BigInteger, List<AccountHead>> accountHeads = this.accountService.retrieveAccounts(entityIds, MVCConstants.SUPPLIER);
				if (accountHeads != null && !accountHeads.isEmpty()) {
					List<AccountHead> supplierAccounts = accountHeads.get(entityId);
					if (supplierAccounts != null && !supplierAccounts.isEmpty()) {
						List<AccountHeadBean> supplierAccountBeans = SupplierTransformer.transformAccountHeads(supplierAccounts, accountDTO.getSupplier().getName());
						accountDTO.getSupplier().setSupplierAccounts(supplierAccountBeans);
						Set<Integer> accountIds = new HashSet<>();
						for (AccountHead supplierAccount : supplierAccounts) {
							accountIds.add(supplierAccount.getAccountId());
						}
						List<AccountJournal> accountJournals = this.accountService.retrievePaymentAccountJournals(accountIds);
						if(accountJournals!=null && !accountJournals.isEmpty()) {
							Integer openLocationId = (Integer) commerceContext.getStoreSettings(CommerceConstants.OPEN_LOC_ID);
							Map<Integer, Tender> tenderMap = this.commonService.retrieveAllTendersAsMap(openLocationId);
							List<AccountJournalBean> ajBeans = SupplierTransformer.transformAccountJournals(accountJournals, tenderMap,supplierAccounts,accountDTO.getSupplier().getName());
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
			logger.info("The Supplier Account lookup screen is ready for the tran");
		} catch (Exception e) {
			logger.error("There is an error while preparing Supplier account lookup screen for display", e);
		}
		return ViewPathConstants.LOOKUP_ACCOUNT_PAGE;
	}

}
