/**
 * 
 */
package com.punj.app.ecommerce.services.converter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.punj.app.ecommerce.domains.finance.LedgerJournal;
import com.punj.app.ecommerce.domains.order.Order;
import com.punj.app.ecommerce.domains.order.returns.OrderReturn;
import com.punj.app.ecommerce.domains.payment.AccountJournal;
import com.punj.app.ecommerce.domains.payment.JournalTender;
import com.punj.app.ecommerce.domains.payment.ids.JournalTenderId;
import com.punj.app.ecommerce.domains.transaction.Transaction;
import com.punj.app.ecommerce.domains.transaction.ids.TransactionId;
import com.punj.app.ecommerce.services.common.ServiceConstants;
import com.punj.app.ecommerce.services.dtos.transaction.TransactionDTO;
import com.punj.app.ecommerce.utils.Utils;

/**
 * @author admin
 *
 */
public class TxnToLedgerConverter {

	private static final Logger logger = LogManager.getLogger();

	private TxnToLedgerConverter() {
		throw new IllegalStateException("Static class TxnToLedgerConverter object cannot be created");
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
	
	
	public static LedgerJournal convertSupplierPaymentToLedger(AccountJournal supplierPayment, String username) {

		LedgerJournal ledgerJournal = new LedgerJournal();
		
		String txnType=Utils.showSupplierTxn(supplierPayment.getJournalType());
		
		ledgerJournal.setActionType(Utils.showLedgerAction(txnType));
		ledgerJournal.setAmount(supplierPayment.getAmount());
		ledgerJournal.setBusinessDate(supplierPayment.getCreatedDate());
		ledgerJournal.setCreatedBy(username);
		ledgerJournal.setCreatedDate(LocalDateTime.now());
		//ledgerJournal.setLocationId(supplierPayment.getAccountId().getLocationId());
		ledgerJournal.setTxnNo(supplierPayment.getJournalId().toString());
		ledgerJournal.setTxnType(txnType);

		logger.info("The ledger has been created with the supplier payment details successfully");
		return ledgerJournal;
	}
	
	
}