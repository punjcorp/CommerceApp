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
import com.punj.app.ecommerce.domains.payment.AccountHead;
import com.punj.app.ecommerce.domains.payment.AccountJournal;
import com.punj.app.ecommerce.repositories.payment.AccountHeadRepository;
import com.punj.app.ecommerce.repositories.payment.AccountJournalRepository;
import com.punj.app.ecommerce.services.PaymentAccountService;
import com.punj.app.ecommerce.services.common.CommonService;
import com.punj.app.ecommerce.services.common.ServiceConstants;

/**
 * @author admin
 *
 */
@Service
public class PaymentAccountServiceImpl implements PaymentAccountService {

	private static final Logger logger = LogManager.getLogger();
	private AccountHeadRepository accountHeadRepository;
	private AccountJournalRepository accountJournalRepository;
	private CommonService commonService;

	@Value("${commerce.list.max.perpage}")
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
	 * @param accountHeadRepository
	 *            the accountHeadRepository to set
	 */
	@Autowired
	public void setAccountHeadRepository(AccountHeadRepository accountHeadRepository) {
		this.accountHeadRepository = accountHeadRepository;
	}

	/**
	 * @param accountJournalRepository
	 *            the accountJournalRepository to set
	 */
	@Autowired
	public void setAccountJournalRepository(AccountJournalRepository accountJournalRepository) {
		this.accountJournalRepository = accountJournalRepository;
	}

	@Override
	public AccountHead retrievePaymentAccount(String accountType, BigInteger accountId, Integer locationId) {
		AccountHead accountHead = new AccountHead();
		accountHead.setEntityId(accountId);
		accountHead.setEntityType(accountType);
		accountHead.setLocationId(locationId);
		accountHead = this.accountHeadRepository.findOne(Example.of(accountHead));
		if (accountHead != null) {
			logger.info("The payment account for {} entity of type {} has been successfully retrieved.", accountId, accountType);
		} else {
			logger.info("There was no account found for {} entity of type {}.");
		}

		return accountHead;
	}

	@Override
	@Transactional
	public List<AccountHead> setupPaymentAccount(String accountType, BigInteger accountId, String username) {
		List<AccountHead> accountHeads = new ArrayList<>();
		AccountHead accountHead;

		List<Location> locations = this.commonService.retrieveAllLocations();
		for (Location location : locations) {
			accountHead = new AccountHead();
			accountHead.setEntityId(accountId);
			accountHead.setEntityType(accountType);
			accountHead.setLocationId(location.getLocationId());
			accountHead.setCreatedBy(username);
			accountHead.setCreatedDate(LocalDateTime.now());
			accountHead.setAdvanceAmount(BigDecimal.ZERO);
			accountHead.setDueAmount(BigDecimal.ZERO);
			accountHeads.add(accountHead);
		}
		logger.info("The account head objects for all locations are ready for save");
		accountHeads = this.accountHeadRepository.save(accountHeads);
		if (accountHeads != null && !accountHeads.isEmpty()) {
			logger.info("The payment accounts for all locations has been successfully setup for {} entity of type {}.", accountId, accountType);
		} else {
			logger.info("There was some issue with payment account setup");
		}
		return accountHeads;
	}

	@Override
	@Transactional
	public AccountJournal savePayment(AccountJournal journalDetails, Integer locationId) {
		AccountHead accountHead = this.retrievePaymentAccount("SUPPLIER", new BigInteger(journalDetails.getAccountId().toString()), locationId);
		journalDetails.setAccountId(accountHead.getAccountId());
		journalDetails = this.accountJournalRepository.save(journalDetails);
		if (journalDetails != null) {
			logger.info("The payment journal details has been successfully saved.");
			if (journalDetails.getJournalType().equals(ServiceConstants.PAYMENT_ADVANCE)) {
				accountHead.setAdvanceAmount(accountHead.getAdvanceAmount().add(journalDetails.getAmount()));
				accountHead = this.accountHeadRepository.save(accountHead);
				logger.info("The payment details has been updated in the account header");
			} else if (journalDetails.getJournalType().equals(ServiceConstants.PAYMENT_FULL)) {

			} else if (journalDetails.getJournalType().equals(ServiceConstants.PAYMENT_PART)) {

			}

		} else {
			logger.info("There was some issue while saving payment details");
		}
		return journalDetails;
	}

	@Override
	@Transactional
	public List<AccountHead> updateAccountsDue(List<AccountHead> accountHeads, String username) {
		List<AccountHead> updatedAccounts=new ArrayList<>(accountHeads.size());
		for(AccountHead accountHead:accountHeads) {
			accountHead=this.updateAccountDue(accountHead, username);
			updatedAccounts.add(accountHead);
		}
		logger.info("All the order account due amounts has been updated successfully");
		return updatedAccounts;
	}
	
	public AccountHead updateAccountDue(AccountHead accountHead, String username) {
		BigDecimal dueAmount=accountHead.getDueAmount();
		accountHead.setDueAmount(null);
		accountHead=this.accountHeadRepository.findOne(Example.of(accountHead));
		if(accountHead!=null) {
			logger.info("The account for the provided details has been retrieved successfully");
			accountHead.setDueAmount(accountHead.getDueAmount().add(dueAmount));
			accountHead.setModifiedBy(username);
			accountHead.setModifiedDate(LocalDateTime.now());
			accountHead=this.accountHeadRepository.save(accountHead);
			if(accountHead!=null) {
				logger.info("The due amount in account for the provided order has been updated successfully");
			}
		}else {
			logger.info("There is some problem while retrieving order account with provided details");
		}
		
		return accountHead;
		
	}

}
