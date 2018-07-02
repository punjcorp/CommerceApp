/**
 * 
 */
package com.punj.app.ecommerce.services.converter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.punj.app.ecommerce.domains.finance.DailyTotals;
import com.punj.app.ecommerce.domains.finance.LedgerJournal;
import com.punj.app.ecommerce.domains.payment.AccountHead;
import com.punj.app.ecommerce.domains.payment.AccountJournal;
import com.punj.app.ecommerce.domains.payment.JournalTender;
import com.punj.app.ecommerce.domains.payment.ids.JournalTenderId;
import com.punj.app.ecommerce.domains.transaction.NoSaleTransaction;
import com.punj.app.ecommerce.domains.transaction.TenderLineItem;
import com.punj.app.ecommerce.domains.transaction.Transaction;
import com.punj.app.ecommerce.domains.transaction.ids.TransactionId;
import com.punj.app.ecommerce.services.common.ServiceConstants;
import com.punj.app.ecommerce.services.dtos.transaction.TransactionDTO;
import com.punj.app.ecommerce.utils.Utils;

/**
 * @author admin
 *
 */
public class TransactionConverter {

	private static final Logger logger = LogManager.getLogger();

	private TransactionConverter() {
		throw new IllegalStateException("TransactionConverter class");
	}

	public static Transaction convertNoSaleToTxn(NoSaleTransaction txnDetails) {
		Transaction txn = new Transaction();

		txn.setTransactionId(txnDetails.getTransactionId());

		txn.setCreatedBy(txnDetails.getCreatedBy());
		txn.setCreatedDate(txnDetails.getCreatedDate());
		// Fix this from screen level
		txn.setStartTime(txnDetails.getCreatedDate());
		txn.setEndTime(txnDetails.getCreatedDate());

		txn.setStatus(ServiceConstants.TXN_STATUS_COMPLETED);
		txn.setDiscountTotalAmt(BigDecimal.ZERO);
		txn.setTaxTotalAmt(BigDecimal.ZERO);

		txn.setSubTotalAmt(txnDetails.getAmount());
		txn.setTotalAmt(txnDetails.getAmount());

		txn.setTxnType(ServiceConstants.TXN_NOSALE);
		logger.info("The transaction object has been successfully created from No Sale transaction");
		return txn;
	}

	public static AccountJournal convertCreditToAJ(AccountHead accountHead, List<TenderLineItem> txnCreditTenders, BigDecimal totalCreditAmt, String username, String txnType) {
		AccountJournal accountJournal = new AccountJournal();

		if(ServiceConstants.TXN_SALE.equals(txnType))
			accountJournal.setJournalType(ServiceConstants.JOURNAL_CREDIT);
		else if(ServiceConstants.TXN_RETURN.equals(txnType))
			accountJournal.setJournalType(ServiceConstants.JOURNAL_CREDIT_RETURN);
		
		accountJournal.setAccountId(accountHead.getAccountId());
		accountJournal.setAmount(totalCreditAmt);
		accountJournal.setComments("Transaction " + txnCreditTenders.get(0).getTransactionLineItemId().toString() + "Credit related entries");
		accountJournal.setCreatedBy(username);
		accountJournal.setCreatedDate(LocalDateTime.now());

		JournalTender journalTender = new JournalTender();
		JournalTenderId journalTenderId = new JournalTenderId();

		journalTenderId.setTenderId(txnCreditTenders.get(0).getTenderId());
		journalTenderId.setAccountJournal(accountJournal);

		journalTender.setJournalTenderId(journalTenderId);
		journalTender.setAmount(totalCreditAmt);
		journalTender.setDescription("The Credit tender amounts has been summed up");
		journalTender.setCreatedBy(username);
		journalTender.setCreatedDate(LocalDateTime.now());

		List<JournalTender> journalTenders = new ArrayList<>();
		journalTenders.add(journalTender);

		accountJournal.setJournalTenders(journalTenders);

		logger.info("The journal details for tender Credit has been created successfully");
		return accountJournal;

	}

	public static LedgerJournal convertTxnToLedger(TransactionDTO txnDTO, String username) {

		Transaction txn = txnDTO.getTxn();
		TransactionId txnId = txn.getTransactionId();

		LedgerJournal ledgerJournal = new LedgerJournal();
		ledgerJournal.setActionType(Utils.showLedgerAction(txn.getTxnType()));
		ledgerJournal.setAmount(txn.getTotalAmt());
		ledgerJournal.setBusinessDate(txnId.getBusinessDate());
		ledgerJournal.setCreatedBy(username);
		ledgerJournal.setCreatedDate(LocalDateTime.now());
		ledgerJournal.setLocationId(txnId.getLocationId());
		ledgerJournal.setTxnNo(txnId.toString());
		ledgerJournal.setTxnType(txn.getTxnType());

		logger.info("The ledger has the txn details saved successfully");
		return ledgerJournal;
	}
	
	public static DailyTotals createDailyTotals(Transaction txnDetails) {
		DailyTotals dailyTotals = new DailyTotals();

		dailyTotals.setBusinessDate(txnDetails.getTransactionId().getBusinessDate());
		dailyTotals.setLocationId(txnDetails.getTransactionId().getLocationId());
		dailyTotals.setRegisterId(txnDetails.getTransactionId().getRegister());
		
		dailyTotals.setTotalTxnAmount(txnDetails.getTotalAmt());
		dailyTotals.setTotalTxnCount(BigDecimal.ONE.intValue());
		
		logger.info("The daily totals has been created for posting related to the transaction successfully");
		return dailyTotals;
	}

}
