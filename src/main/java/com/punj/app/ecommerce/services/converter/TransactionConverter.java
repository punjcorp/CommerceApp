/**
 * 
 */
package com.punj.app.ecommerce.services.converter;

import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.punj.app.ecommerce.domains.transaction.NoSaleTransaction;
import com.punj.app.ecommerce.domains.transaction.Transaction;
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

}
