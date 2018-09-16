/**
 * 
 */
package com.punj.app.ecommerce.services.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.punj.app.ecommerce.domains.finance.DailyTotals;
import com.punj.app.ecommerce.domains.finance.LedgerJournal;
import com.punj.app.ecommerce.domains.payment.AccountHead;
import com.punj.app.ecommerce.domains.payment.AccountJournal;
import com.punj.app.ecommerce.domains.tender.Tender;
import com.punj.app.ecommerce.domains.transaction.TenderLineItem;
import com.punj.app.ecommerce.domains.transaction.Transaction;
import com.punj.app.ecommerce.domains.transaction.ids.TransactionId;
import com.punj.app.ecommerce.services.AccountService;
import com.punj.app.ecommerce.services.FinanceService;
import com.punj.app.ecommerce.services.PaymentAccountService;
import com.punj.app.ecommerce.services.TransactionAuditService;
import com.punj.app.ecommerce.services.TransactionFinanceService;
import com.punj.app.ecommerce.services.common.CommonService;
import com.punj.app.ecommerce.services.common.ServiceConstants;
import com.punj.app.ecommerce.services.converter.TransactionConverter;
import com.punj.app.ecommerce.services.dtos.transaction.TransactionDTO;

/**
 * @author admin
 *
 */
@Service
public class TransactionFinanceServiceImpl implements TransactionFinanceService {

	private static final Logger logger = LogManager.getLogger();
	private CommonService commonService;
	private FinanceService financeService;
	private AccountService accountService;
	private PaymentAccountService paymentAccountService;
	private TransactionAuditService txnAuditService;

	/**
	 * @param commonService
	 *            the commonService to set
	 */
	@Autowired
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	/**
	 * @param financeService
	 *            the financeService to set
	 */
	@Autowired
	public void setFinanceService(FinanceService financeService) {
		this.financeService = financeService;
	}

	/**
	 * @param accountService
	 *            the accountService to set
	 */
	@Autowired
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	/**
	 * @param paymentAccountService
	 *            the paymentAccountService to set
	 */
	@Autowired
	public void setPaymentAccountService(PaymentAccountService paymentAccountService) {
		this.paymentAccountService = paymentAccountService;
	}

	/**
	 * @param txnAuditService
	 *            the txnAuditService to set
	 */
	@Autowired
	public void setTxnAuditService(TransactionAuditService txnAuditService) {
		this.txnAuditService = txnAuditService;
	}

	/**
	 * This method is used to post the finance reversals for a transaction which has been modified
	 */
	@Override	
	public void postFinancesReversal(AccountHead accountHead, TransactionDTO txnDTOBeforeChange, String username) {
		
		Transaction txnDetails = txnDTOBeforeChange.getTxn();
		TransactionId txnId = txnDTOBeforeChange.getTxn().getTransactionId();
		
		Integer tenderId = null;
		Tender tender = null;
		BigDecimal totalCreditAmount = BigDecimal.ZERO;
		List<TenderLineItem> txnCreditTenders = new ArrayList<>();
		
		
		Map<Integer, Tender> tenderMap = this.commonService.retrieveAllTendersAsMap(txnId.getLocationId());
		
		List<TenderLineItem> txnTenders = txnDTOBeforeChange.getTenderLineItems();
		if (txnTenders != null && !txnTenders.isEmpty() && tenderMap != null) {
			for (TenderLineItem tenderLineItem : txnTenders) {
				tenderId = tenderLineItem.getTenderId();
				tender = tenderMap.get(tenderId);
				if (tender.getName().equals(ServiceConstants.TENDER_CREDIT)) {
					totalCreditAmount = totalCreditAmount.add(tenderLineItem.getAmount());
					txnCreditTenders.add(tenderLineItem);
				}
			}

			if (!txnCreditTenders.isEmpty() && accountHead != null && totalCreditAmount.compareTo(BigDecimal.ZERO)>0) {
				this.postCreditCustomerAccountReversal(accountHead, txnCreditTenders, totalCreditAmount, username, txnDetails.getTxnType());
				logger.info("The customer credit reversal has been completed");
			}

		}

		this.postLedgerReversal(txnDTOBeforeChange, username, txnId);
		logger.info("The ledger journal details has been completed");
		

		this.postDailyTotalsReversal(txnDTOBeforeChange, username, txnId);
		logger.info("The daily total reversal has been completed");
		
		
	}
	
	/**
	 * This method post the reversal for Credit amount related to Customer
	 * @param accountHead
	 * @param txnCreditTenders
	 * @param totalCreditAmount
	 * @param username
	 * @param txnType
	 */
	private void postCreditCustomerAccountReversal(AccountHead accountHead,List<TenderLineItem> txnCreditTenders,BigDecimal totalCreditAmount, String username, String txnType) {
		AccountJournal accountJournal = TransactionConverter.convertCreditToAJReversal(accountHead, txnCreditTenders, totalCreditAmount, username, txnType);
		accountJournal = this.paymentAccountService.savePayment(accountJournal, username);
		if (accountJournal != null)
			logger.info("The account journal reversal details for credit tender has been saved successfully");
		else
			logger.info("There was error while saving credit tender details for reversal");
	}
	
	/**
	 * This method post the reversal for ledger amount for the transaction
	 * @param txnDTOBeforeChange
	 * @param username
	 * @param txnId
	 */
	private void postLedgerReversal(TransactionDTO txnDTOBeforeChange, String username, TransactionId txnId) {
		LedgerJournal ledgerJournal = TransactionConverter.convertTxnToLedgerReversal(txnDTOBeforeChange, username);
		if (ledgerJournal != null) {
			ledgerJournal = this.financeService.saveLedgerDetails(ledgerJournal);
			if (ledgerJournal != null)
				logger.info("The ledger reversal details for the txn {} has been saved successfully", txnId.toString());
			else
				logger.error("The was error saving ledger reversal  details for the txn {} ", txnId.toString());
		}
	}
	
	/**
	 * This method is to post the daily total reversals
	 * @param txnDTOBeforeChange
	 * @param username
	 * @param txnId
	 */
	private void postDailyTotalsReversal(TransactionDTO txnDTOBeforeChange, String username, TransactionId txnId) {
		DailyTotals dailyTotals = TransactionConverter.createDailyTotalsReversal(txnDTOBeforeChange.getTxn());
		DailyTotals registerTotals = this.financeService.postDailyTotalReversal(dailyTotals, txnDTOBeforeChange.getTxn().getTxnType());
		if (registerTotals != null)
			logger.info("The register and store totals reversal has been updated for the txn {} successfully", txnId.toString());
		else
			logger.error("The was error updating register and store totals reversal for the txn {} ", txnId.toString());
	}
	
	
	
	/**
	 * This method is to post the financial details related to a transaction
	 */
	public void updateFinanceDetails(AccountHead accountHead, TransactionDTO txnDTO, String username) {
		List<TenderLineItem> txnTenders = txnDTO.getTenderLineItems();
		List<TenderLineItem> txnCreditTenders = new ArrayList<>();

		Transaction txnDetails = txnDTO.getTxn();
		TransactionId txnId = txnDTO.getTxn().getTransactionId();

		Map<Integer, Tender> tenderMap = this.commonService.retrieveAllTendersAsMap(txnId.getLocationId());
		Integer tenderId = null;
		Tender tender = null;
		BigDecimal totalCreditAmount = BigDecimal.ZERO;
		if (txnTenders != null && !txnTenders.isEmpty() && tenderMap != null) {
			for (TenderLineItem tenderLineItem : txnTenders) {
				tenderId = tenderLineItem.getTenderId();
				tender = tenderMap.get(tenderId);
				if (tender.getName().equals(ServiceConstants.TENDER_CREDIT)) {
					totalCreditAmount = totalCreditAmount.add(tenderLineItem.getAmount());
					txnCreditTenders.add(tenderLineItem);
				}
			}

			if (!txnCreditTenders.isEmpty() && accountHead != null) {
				AccountJournal accountJournal = TransactionConverter.convertCreditToAJ(accountHead, txnCreditTenders, totalCreditAmount, username, txnDetails.getTxnType());
				accountJournal = this.paymentAccountService.savePayment(accountJournal, username);
				if (accountJournal != null)
					logger.info("The account journal details for credit tender has been saved successfully");
				else
					logger.info("There was error while saving credit tender details");
			}

		}

		LedgerJournal ledgerJournal = TransactionConverter.convertTxnToLedger(txnDTO, username);
		if (ledgerJournal != null) {
			ledgerJournal = this.financeService.saveLedgerDetails(ledgerJournal);
			if (ledgerJournal != null)
				logger.info("The ledger details for the txn {} has been saved successfully", txnId.toString());
			else
				logger.error("The was error saving ledger details for the txn {} ", txnId.toString());
		}

		DailyTotals dailyTotals = TransactionConverter.createDailyTotals(txnDTO.getTxn());
		DailyTotals registerTotals = this.financeService.updateDailyTotals(dailyTotals, txnDetails.getTxnType());
		if (registerTotals != null)
			logger.info("The register and store totals has been updated for the txn {} successfully", txnId.toString());
		else
			logger.error("The was error updating register and store totals for the txn {} ", txnId.toString());

	}

}
