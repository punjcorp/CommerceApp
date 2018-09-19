/**
 * 
 */
package com.punj.app.ecommerce.services.transaction.receipts;

import java.math.BigInteger;
import java.util.List;
import java.util.Locale;

import com.punj.app.ecommerce.domains.transaction.TransactionReceipt;
import com.punj.app.ecommerce.domains.transaction.ids.TransactionId;
import com.punj.app.ecommerce.services.dtos.transaction.SaleTransactionReceiptDTO;

import net.sf.jasperreports.engine.JasperPrint;

/**
 * @author admin
 *
 */
public interface ReceiptService {

	public SaleTransactionReceiptDTO generateTransactionReceipt(TransactionId txnId);

	public List<JasperPrint> generateTxnJasperReports(TransactionId txnId, Integer receiptCount, Locale locale, String username, BigInteger invoiceNo);

	public void saveTxnReceipts(List<JasperPrint> jasperPrints, String username, TransactionId txnId, Boolean isExisting);

	public List<TransactionReceipt> searchTransactionReceipt(TransactionId txnId, Integer receiptCount);

	public void regenerateReceipts(BigInteger startingInvoiceNo, BigInteger endingInvoiceNo, Integer receiptCopies, Locale locale, String username);

}
