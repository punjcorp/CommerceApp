/**
 * 
 */
package com.punj.app.ecommerce.services.impl;

import java.math.BigInteger;
import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibm.icu.math.BigDecimal;
import com.punj.app.ecommerce.domains.finance.DailyTotals;
import com.punj.app.ecommerce.domains.finance.LedgerJournal;
import com.punj.app.ecommerce.domains.transaction.NoSaleTransaction;
import com.punj.app.ecommerce.domains.transaction.NoSaleTxnDTO;
import com.punj.app.ecommerce.domains.transaction.Transaction;
import com.punj.app.ecommerce.domains.transaction.ids.TransactionId;
import com.punj.app.ecommerce.repositories.transaction.NoSaleTenderRepository;
import com.punj.app.ecommerce.repositories.transaction.NoSaleTxnRepository;
import com.punj.app.ecommerce.services.FinanceService;
import com.punj.app.ecommerce.services.NoSaleService;
import com.punj.app.ecommerce.services.TransactionSeqService;
import com.punj.app.ecommerce.services.TransactionService;
import com.punj.app.ecommerce.services.common.CommonService;
import com.punj.app.ecommerce.services.common.ServiceConstants;

/**
 * @author admin
 *
 */
@Service
public class NoSaleServiceImpl implements NoSaleService {

	private static final Logger logger = LogManager.getLogger();
	private CommonService commonService;
	private TransactionService txnService;
	private NoSaleTxnRepository noSaleTxnRepository;
	private NoSaleTenderRepository noSaleTenderTxnRepository;
	private FinanceService financeService;
	private TransactionSeqService txnSeqService;

	/**
	 * @param txnSeqService
	 *            the txnSeqService to set
	 */
	@Autowired
	public void setTxnSeqService(TransactionSeqService txnSeqService) {
		this.txnSeqService = txnSeqService;
	}

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
	 * @param txnService
	 *            the txnService to set
	 */
	@Autowired
	public void setTxnService(TransactionService txnService) {
		this.txnService = txnService;
	}

	/**
	 * @param noSaleTxnRepository
	 *            the noSaleTxnRepository to set
	 */
	@Autowired
	public void setNoSaleTxnRepository(NoSaleTxnRepository noSaleTxnRepository) {
		this.noSaleTxnRepository = noSaleTxnRepository;
	}

	/**
	 * @param noSaleTenderTxnRepository
	 *            the noSaleTenderTxnRepository to set
	 */
	@Autowired
	public void setNoSaleTenderRepository(NoSaleTenderRepository noSaleTenderTxnRepository) {
		this.noSaleTenderTxnRepository = noSaleTenderTxnRepository;
	}

	@Override
	@Transactional
	public NoSaleTxnDTO saveNoSaleTxn(Transaction txnHeaderDetails, NoSaleTransaction txnDetails) {
		NoSaleTxnDTO noSaleTxnDTO = new NoSaleTxnDTO();
		txnHeaderDetails = this.txnService.saveTransaction(txnHeaderDetails);
		if (txnHeaderDetails != null) {
			logger.info("The base transaction details has been saved for no sale transaction");
			txnDetails = this.noSaleTxnRepository.save(txnDetails);
			if (txnDetails != null) {
				
				noSaleTxnDTO.setNoSaleTxn(txnDetails);
				
				TransactionId txnId = txnHeaderDetails.getTransactionId();
				BigInteger invoiceNo = this.txnSeqService.saveTransactionSeqs(txnHeaderDetails);
				if (invoiceNo != null && txnId != null) {
					noSaleTxnDTO.setVoucherNo(invoiceNo);
				}

				DailyTotals dailyTotals = this.createDailyTotals(txnDetails);
				this.financeService.updateDailyTotals(dailyTotals, ServiceConstants.ACTION_EXPENSE);
				logger.info("The no sale transaction with tender details has been saved.");

				LedgerJournal ledgerJournal = this.createLedgerDetails(txnHeaderDetails);
				this.financeService.saveLedgerDetails(ledgerJournal);
				logger.info("The ledger journal details has been saved.");
			} else {
				logger.info("There was some issue while saving the no sale txn details");
			}
		} else {
			logger.info("There was some issue while saving the no sale txn header details");
		}
		return noSaleTxnDTO;
	}

	private DailyTotals createDailyTotals(NoSaleTransaction txnDetails) {
		DailyTotals dailyTotals = new DailyTotals();

		dailyTotals.setBusinessDate(txnDetails.getTransactionId().getBusinessDate());
		dailyTotals.setLocationId(txnDetails.getTransactionId().getLocationId());
		dailyTotals.setRegisterId(txnDetails.getTransactionId().getRegister());

		dailyTotals.setTotalTxnAmount(txnDetails.getAmount());
		dailyTotals.setTotalTxnCount(BigDecimal.ONE.intValue());
		logger.info("The daily totals has been created for posting successfully");
		return dailyTotals;
	}

	private LedgerJournal createLedgerDetails(Transaction txnDetails) {
		LedgerJournal ledgerJournal = new LedgerJournal();
		ledgerJournal.setActionType(ServiceConstants.LEDGER_ACTION_EXPENSE_FROM_REGISTER);
		ledgerJournal.setAmount(txnDetails.getTotalAmt());
		ledgerJournal.setBusinessDate(txnDetails.getTransactionId().getBusinessDate());
		ledgerJournal.setCreatedBy(txnDetails.getCreatedBy());
		ledgerJournal.setCreatedDate(LocalDateTime.now());
		ledgerJournal.setLocationId(txnDetails.getTransactionId().getLocationId());
		ledgerJournal.setTxnNo(txnDetails.getTransactionId().toString());
		ledgerJournal.setTxnType(txnDetails.getTxnType());
		logger.info("The ledger details has been created for posting successfully");
		return ledgerJournal;
	}

	@Override
	public NoSaleTransaction retrieveNoSaleTxn(TransactionId txnId) {
		NoSaleTransaction noSaleTxn = this.noSaleTxnRepository.findOne(txnId);
		if (noSaleTxn != null)
			logger.info("The no sale txn {} ot type {} details has been retrieved successfully", txnId.toString(), noSaleTxn.getNoSaleType());
		else
			logger.info("The {} no sale txn was not found ", txnId.toString());
		return noSaleTxn;
	}

}
