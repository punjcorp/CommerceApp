/**
 * 
 */
package com.punj.app.ecommerce.services.converter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.punj.app.ecommerce.domains.finance.DailyTotals;
import com.punj.app.ecommerce.domains.payment.AccountHead;
import com.punj.app.ecommerce.domains.payment.AccountJournal;
import com.punj.app.ecommerce.domains.payment.JournalTender;
import com.punj.app.ecommerce.domains.payment.ids.JournalTenderId;
import com.punj.app.ecommerce.domains.transaction.NoSaleTransaction;
import com.punj.app.ecommerce.domains.transaction.TenderLineItem;
import com.punj.app.ecommerce.domains.transaction.Transaction;
import com.punj.app.ecommerce.domains.transaction.ids.TransactionId;
import com.punj.app.ecommerce.services.common.ServiceConstants;

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

		if (ServiceConstants.TXN_SALE.equals(txnType))
			accountJournal.setJournalType(ServiceConstants.CUST_JOURNAL_CREDIT);
		else if (ServiceConstants.TXN_RETURN.equals(txnType))
			accountJournal.setJournalType(ServiceConstants.CUST_JOURNAL_CREDIT_RETURN);

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

	public static TransactionId convertUniqueTxnToId(String uniqueTxnNo) {
		TransactionId txnId = null;

		if (StringUtils.isNotBlank(uniqueTxnNo) && uniqueTxnNo.trim().length() == 18) {

			txnId = new TransactionId();
			txnId.setLocationId(new Integer(uniqueTxnNo.substring(0, 4)));
			txnId.setRegister(new Integer(uniqueTxnNo.substring(4, 7)));
			txnId.setTransactionSeq(new Integer(uniqueTxnNo.substring(7, 12)));
			String bDate = null;

			bDate = uniqueTxnNo.substring(12, 18);

			bDate = bDate + " 00:00";
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyy HH:mm");
			LocalDateTime bDateTime = LocalDateTime.parse(bDate, formatter);

			txnId.setBusinessDate(bDateTime);

			logger.info("The unique transction number has been successfully transformed to txn Id");
		} else if (StringUtils.isNotBlank(uniqueTxnNo) && uniqueTxnNo.trim().length() == 20) {

			txnId = new TransactionId();
			txnId.setLocationId(new Integer(uniqueTxnNo.substring(0, 4)));
			txnId.setRegister(new Integer(uniqueTxnNo.substring(4, 7)));
			txnId.setTransactionSeq(new Integer(uniqueTxnNo.substring(7, 12)));
			String bDate = uniqueTxnNo.substring(12, 20);

			bDate = bDate + " 00:00";
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy HH:mm");
			LocalDateTime bDateTime = LocalDateTime.parse(bDate, formatter);

			txnId.setBusinessDate(bDateTime);

			logger.info("The unique transction number has been successfully transformed to txn Id");
		}

		return txnId;
	}

	public static BigInteger convertUniqueTxnToPaymentId(String uniqueTxnNo) {
		BigInteger journalId=null;
		if (StringUtils.isNotBlank(uniqueTxnNo) && uniqueTxnNo.trim().length() >= 18) {
			journalId=new BigInteger(uniqueTxnNo.substring(7, 12));
			logger.info("The unique transction number has been successfully transformed to journal Id");
		}
		return journalId;
	}

}
