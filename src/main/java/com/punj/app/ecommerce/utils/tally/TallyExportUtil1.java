package com.punj.app.ecommerce.utils.tally;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.domains.customer.Customer;
import com.punj.app.ecommerce.domains.transaction.TenderLineItem;
import com.punj.app.ecommerce.services.dtos.transaction.TransactionDTO;
import com.punj.app.ecommerce.tally.export.voucher.ENVELOPE.BODY;
import com.punj.app.ecommerce.tally.export.voucher.ENVELOPE.HEADER;
import com.punj.app.ecommerce.tally.export.voucher.ENVELOPE.BODY.DATA;
import com.punj.app.ecommerce.tally.export.voucher.ENVELOPE.BODY.DESC;
import com.punj.app.ecommerce.tally.export.voucher.ENVELOPE.BODY.DATA.TALLYMESSAGE;
import com.punj.app.ecommerce.tally.export.voucher.ENVELOPE.BODY.DATA.TALLYMESSAGE.VOUCHER;
import com.punj.app.ecommerce.tally.export.voucher.ENVELOPE.BODY.DATA.TALLYMESSAGE.VOUCHER.ALLLEDGERENTRIESLIST;
import com.punj.app.ecommerce.tally.export.voucher.ENVELOPE.BODY.DESC.STATICVARIABLES;
import com.punj.app.ecommerce.tally.export.voucher.ENVELOPE;
import com.punj.app.ecommerce.tally.export.voucher.ObjectFactory;

public class TallyExportUtil1 {

	private static final Logger logger = LogManager.getLogger();

	private TallyExportUtil1() {
		throw new IllegalStateException("TallyExportUtil class");
	}
	
	public static void createExpenseVoucher(TransactionDTO txnDetails) {


		ObjectFactory voucherFactory = new ObjectFactory();
		ENVELOPE envelope = voucherFactory.createENVELOPE();

		HEADER voucherHeader = voucherFactory.createENVELOPEHEADER();
		voucherHeader.setVERSION(1);
		voucherHeader.setTALLYREQUEST("Import");
		voucherHeader.setTYPE("Data");
		voucherHeader.setID("Vouchers");
		envelope.setHEADER(voucherHeader);

		BODY voucherBody = voucherFactory.createENVELOPEBODY();

		/**
		 * Voucher Body Desc updation
		 */
		DESC voucherBodyDesc = voucherFactory.createENVELOPEBODYDESC();
		STATICVARIABLES staticVariables = voucherFactory.createENVELOPEBODYDESCSTATICVARIABLES();
		staticVariables.setIMPORTDUPS("@@DUPCOMBINE");
		voucherBodyDesc.setSTATICVARIABLES(staticVariables);
		voucherBody.setDESC(voucherBodyDesc);
		
		/**
		 * Voucher Body DATA updation starts
		 */
		DATA voucherBodyData = voucherFactory.createENVELOPEBODYDATA();
		TALLYMESSAGE tallyMsg = voucherFactory.createENVELOPEBODYDATATALLYMESSAGE();
		List<VOUCHER> vouchers = tallyMsg.getVOUCHER();

		VOUCHER voucherData = voucherFactory.createENVELOPEBODYDATATALLYMESSAGEVOUCHER();
		TallyExportUtil1.createVoucherHeaderDetails(voucherData, txnDetails);
		vouchers.add(voucherData);
		
		voucherBodyData.setTALLYMESSAGE(tallyMsg);
		voucherBody.setDATA(voucherBodyData);
		
		/**
		 * Voucher Body DATA updation ends
		 */
		
		envelope.setBODY(voucherBody);

		logger.info("The transaction details has been converted to the Tally XML export successfully");

	
	}
	
	public static ENVELOPE createVoucher(TransactionDTO txnDetails) {


		ObjectFactory voucherFactory = new ObjectFactory();
		ENVELOPE envelope = voucherFactory.createENVELOPE();

		HEADER voucherHeader = voucherFactory.createENVELOPEHEADER();
		voucherHeader.setVERSION(1);
		voucherHeader.setTALLYREQUEST("Import");
		voucherHeader.setTYPE("Data");
		voucherHeader.setID("Vouchers");
		envelope.setHEADER(voucherHeader);

		BODY voucherBody = voucherFactory.createENVELOPEBODY();

		/**
		 * Voucher Body Desc updation
		 */
		DESC voucherBodyDesc = voucherFactory.createENVELOPEBODYDESC();
		STATICVARIABLES staticVariables = voucherFactory.createENVELOPEBODYDESCSTATICVARIABLES();
		staticVariables.setIMPORTDUPS("@@DUPCOMBINE");
		voucherBodyDesc.setSTATICVARIABLES(staticVariables);
		voucherBody.setDESC(voucherBodyDesc);
		
		/**
		 * Voucher Body DATA updation starts
		 */
		DATA voucherBodyData = voucherFactory.createENVELOPEBODYDATA();
		TALLYMESSAGE tallyMsg = voucherFactory.createENVELOPEBODYDATATALLYMESSAGE();
		List<VOUCHER> vouchers = tallyMsg.getVOUCHER();

		VOUCHER voucherData = voucherFactory.createENVELOPEBODYDATATALLYMESSAGEVOUCHER();
		TallyExportUtil1.createVoucherHeaderDetails(voucherData, txnDetails);
		vouchers.add(voucherData);
		
		voucherBodyData.setTALLYMESSAGE(tallyMsg);
		voucherBody.setDATA(voucherBodyData);
		
		/**
		 * Voucher Body DATA updation ends
		 */
		
		envelope.setBODY(voucherBody);

		logger.info("The transaction details has been converted to the Tally XML export successfully");
		return envelope;
	
	}
	
	public static void createVoucherHeaderDetails(VOUCHER voucherData, TransactionDTO txnDetails) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		voucherData.setDATE(txnDetails.getTxn().getTransactionId().getBusinessDate().format(formatter));

		Customer customer = txnDetails.getCustomer();
		if (customer != null) {
			String custName = customer.getName();
			voucherData.setNARRATION("Transaction for customer - "+custName);
		} else {
			voucherData.setNARRATION("");
		}

		voucherData.setVOUCHERNUMBER(txnDetails.getTxn().getTransactionId().toString());
		if(txnDetails.getTxn().getTxnType().equals(MVCConstants.TXN_SALE_PARAM)) {
			voucherData.setVOUCHERTYPENAME(MVCConstants.TALLY_VOUCHER_GST);
			List<ALLLEDGERENTRIESLIST> ledgerList = voucherData.getALLLEDGERENTRIESLIST();
			TallyExportUtil1.createVoucherLedgers(ledgerList, txnDetails);
		}
		else if(txnDetails.getTxn().getTxnType().equals(MVCConstants.TXN_RETURN_PARAM)) {
			voucherData.setVOUCHERTYPENAME(MVCConstants.TALLY_VOUCHER_DEBIT);
			List<ALLLEDGERENTRIESLIST> ledgerList = voucherData.getALLLEDGERENTRIESLIST();
			TallyExportUtil1.createDebitVoucherLedgers(ledgerList, txnDetails);
		}
		else if(txnDetails.getTxn().getTxnType().equals(MVCConstants.TXN_NOSALE_PARAM )) {
			voucherData.setVOUCHERTYPENAME(MVCConstants.TALLY_VOUCHER_PAYMENT);
			List<ALLLEDGERENTRIESLIST> ledgerList = voucherData.getALLLEDGERENTRIESLIST();
			TallyExportUtil1.createDebitVoucherLedgers(ledgerList, txnDetails);
		}
			
		
	}

	public static void createVoucherLedgers(List<ALLLEDGERENTRIESLIST> ledgers, TransactionDTO txnDetails) {
		ObjectFactory voucherFactory = new ObjectFactory();
		ALLLEDGERENTRIESLIST ledgerEntry = null;
		Customer customer = txnDetails.getCustomer();
		List<TenderLineItem> tenderLineItems = txnDetails.getTenderLineItems();
		if (tenderLineItems != null && !tenderLineItems.isEmpty()) {
			logger.info("There are multiple tenders in the transactions");
			if (customer != null) {
				logger.info("The tenders must be ledgered against the provided customer ({}) in the transaction", customer.getName());
				for (TenderLineItem tender : tenderLineItems) {
					switch (tender.getType()) {
					case MVCConstants.TNDR_CASH:
						ledgerEntry = voucherFactory.createENVELOPEBODYDATATALLYMESSAGEVOUCHERALLLEDGERENTRIESLIST();
						ledgerEntry.setLEDGERNAME(MVCConstants.TALLY_LEDGER_CASH);
						ledgerEntry.setISDEEMEDPOSITIVE(MVCConstants.TALLY_NEGATIVE_AMT_YES);
						ledgerEntry.setAMOUNT(txnDetails.getTxn().getTotalAmt().multiply(new BigDecimal("-1")));
						ledgers.add(ledgerEntry);

						ledgerEntry = voucherFactory.createENVELOPEBODYDATATALLYMESSAGEVOUCHERALLLEDGERENTRIESLIST();
						ledgerEntry.setLEDGERNAME(customer.getName());
						ledgerEntry.setISDEEMEDPOSITIVE(MVCConstants.TALLY_NEGATIVE_AMT_NO);
						ledgerEntry.setAMOUNT(txnDetails.getTxn().getTotalAmt());
						ledgers.add(ledgerEntry);
						logger.info("The CASH tender ledgers created successfully!!");
						break;
						
					case MVCConstants.TNDR_CREDIT:
						ledgerEntry = voucherFactory.createENVELOPEBODYDATATALLYMESSAGEVOUCHERALLLEDGERENTRIESLIST();
						ledgerEntry.setLEDGERNAME(customer.getName());
						ledgerEntry.setISDEEMEDPOSITIVE(MVCConstants.TALLY_NEGATIVE_AMT_YES);
						ledgerEntry.setAMOUNT(txnDetails.getTxn().getTotalAmt().multiply(new BigDecimal("-1")));
						ledgers.add(ledgerEntry);

						ledgerEntry = voucherFactory.createENVELOPEBODYDATATALLYMESSAGEVOUCHERALLLEDGERENTRIESLIST();
						ledgerEntry.setLEDGERNAME(MVCConstants.TALLY_LEDGER_CASH);
						ledgerEntry.setISDEEMEDPOSITIVE(MVCConstants.TALLY_NEGATIVE_AMT_NO);
						ledgerEntry.setAMOUNT(txnDetails.getTxn().getTotalAmt());
						ledgers.add(ledgerEntry);
						logger.info("The CREDIT tender ledgers created successfully!!");
						break;
					}
				}
			} else {
				for (TenderLineItem tender : tenderLineItems) {
					switch (tender.getType()) {
					case MVCConstants.TNDR_CASH:
						ledgerEntry = voucherFactory.createENVELOPEBODYDATATALLYMESSAGEVOUCHERALLLEDGERENTRIESLIST();
						ledgerEntry.setLEDGERNAME(MVCConstants.TALLY_LEDGER_CASH);
						ledgerEntry.setISDEEMEDPOSITIVE(MVCConstants.TALLY_NEGATIVE_AMT_YES);
						ledgerEntry.setAMOUNT(txnDetails.getTxn().getTotalAmt().multiply(new BigDecimal("-1")));
						ledgers.add(ledgerEntry);

						ledgerEntry = voucherFactory.createENVELOPEBODYDATATALLYMESSAGEVOUCHERALLLEDGERENTRIESLIST();
						ledgerEntry.setLEDGERNAME(MVCConstants.TALLY_LEDGER_BANK);
						ledgerEntry.setISDEEMEDPOSITIVE(MVCConstants.TALLY_NEGATIVE_AMT_NO);
						ledgerEntry.setAMOUNT(txnDetails.getTxn().getTotalAmt());
						ledgers.add(ledgerEntry);
						logger.info("The CASH tender ledgers created successfully!!");
						break;
										
					}
				}
			}

		}

		logger.info("The voucher ledger details has been created successfully!!");

	}
	
	
	public static void createDebitVoucherLedgers(List<ALLLEDGERENTRIESLIST> ledgers, TransactionDTO txnDetails) {
		ObjectFactory voucherFactory = new ObjectFactory();
		ALLLEDGERENTRIESLIST ledgerEntry = null;
		Customer customer = txnDetails.getCustomer();
		List<TenderLineItem> tenderLineItems = txnDetails.getTenderLineItems();
		if (tenderLineItems != null && !tenderLineItems.isEmpty()) {
			logger.info("There are multiple tenders in the transactions");
			if (customer != null) {
				logger.info("The tenders must be ledgered against the provided customer ({}) in the transaction", customer.getName());
				for (TenderLineItem tender : tenderLineItems) {
					switch (tender.getType()) {
					case MVCConstants.TNDR_CASH:
						ledgerEntry = voucherFactory.createENVELOPEBODYDATATALLYMESSAGEVOUCHERALLLEDGERENTRIESLIST();
						ledgerEntry.setLEDGERNAME(customer.getName());
						ledgerEntry.setISDEEMEDPOSITIVE(MVCConstants.TALLY_NEGATIVE_AMT_YES);
						ledgerEntry.setAMOUNT(txnDetails.getTxn().getTotalAmt());
						ledgers.add(ledgerEntry);

						ledgerEntry = voucherFactory.createENVELOPEBODYDATATALLYMESSAGEVOUCHERALLLEDGERENTRIESLIST();
						ledgerEntry.setLEDGERNAME(MVCConstants.TALLY_LEDGER_CASH);
						ledgerEntry.setISDEEMEDPOSITIVE(MVCConstants.TALLY_NEGATIVE_AMT_NO);
						ledgerEntry.setAMOUNT(txnDetails.getTxn().getTotalAmt().multiply(new BigDecimal("-1")));
						ledgers.add(ledgerEntry);
						logger.info("The CASH tender ledgers created successfully!!");
						break;
						
					case MVCConstants.TNDR_CREDIT:
						ledgerEntry = voucherFactory.createENVELOPEBODYDATATALLYMESSAGEVOUCHERALLLEDGERENTRIESLIST();
						ledgerEntry.setLEDGERNAME(MVCConstants.TALLY_LEDGER_CASH);
						ledgerEntry.setISDEEMEDPOSITIVE(MVCConstants.TALLY_NEGATIVE_AMT_YES);
						ledgerEntry.setAMOUNT(txnDetails.getTxn().getTotalAmt());
						ledgers.add(ledgerEntry);

						ledgerEntry = voucherFactory.createENVELOPEBODYDATATALLYMESSAGEVOUCHERALLLEDGERENTRIESLIST();
						ledgerEntry.setLEDGERNAME(customer.getName());
						ledgerEntry.setISDEEMEDPOSITIVE(MVCConstants.TALLY_NEGATIVE_AMT_NO);
						ledgerEntry.setAMOUNT(txnDetails.getTxn().getTotalAmt().multiply(new BigDecimal("-1")));
						ledgers.add(ledgerEntry);
						logger.info("The CREDIT tender ledgers created successfully!!");
						break;
					}
				}
			} else {
				for (TenderLineItem tender : tenderLineItems) {
					switch (tender.getType()) {
					case MVCConstants.TNDR_CASH:
						ledgerEntry = voucherFactory.createENVELOPEBODYDATATALLYMESSAGEVOUCHERALLLEDGERENTRIESLIST();
						ledgerEntry.setLEDGERNAME(MVCConstants.TALLY_LEDGER_BANK);
						ledgerEntry.setISDEEMEDPOSITIVE(MVCConstants.TALLY_NEGATIVE_AMT_YES);
						ledgerEntry.setAMOUNT(txnDetails.getTxn().getTotalAmt());
						ledgers.add(ledgerEntry);

						ledgerEntry = voucherFactory.createENVELOPEBODYDATATALLYMESSAGEVOUCHERALLLEDGERENTRIESLIST();
						ledgerEntry.setLEDGERNAME(MVCConstants.TALLY_LEDGER_CASH);
						ledgerEntry.setISDEEMEDPOSITIVE(MVCConstants.TALLY_NEGATIVE_AMT_NO);
						ledgerEntry.setAMOUNT(txnDetails.getTxn().getTotalAmt().multiply(new BigDecimal("-1")));
						ledgers.add(ledgerEntry);
						logger.info("The CASH tender ledgers created successfully!!");
						break;
										
					}
				}
			}

		}

		logger.info("The voucher ledger details has been created successfully!!");

	}
	
	
	public static void createExpenseVoucherLedgers(List<ALLLEDGERENTRIESLIST> ledgers, TransactionDTO txnDetails) {
		ObjectFactory voucherFactory = new ObjectFactory();
		ALLLEDGERENTRIESLIST ledgerEntry = null;
		Customer customer = txnDetails.getCustomer();
		List<TenderLineItem> tenderLineItems = txnDetails.getTenderLineItems();
		if (tenderLineItems != null && !tenderLineItems.isEmpty()) {
			logger.info("There are multiple tenders in the transactions");
			if (customer != null) {
				logger.error("The no sales transaction should never have a customer associated");
			} else {
				for (TenderLineItem tender : tenderLineItems) {
					switch (tender.getType()) {
					case MVCConstants.TNDR_CASH:
						ledgerEntry = voucherFactory.createENVELOPEBODYDATATALLYMESSAGEVOUCHERALLLEDGERENTRIESLIST();
						ledgerEntry.setLEDGERNAME(txnDetails.getNoSaleTxn().getReasonCode().getName());
						ledgerEntry.setISDEEMEDPOSITIVE(MVCConstants.TALLY_NEGATIVE_AMT_YES);
						ledgerEntry.setAMOUNT(txnDetails.getTxn().getTotalAmt().multiply(new BigDecimal("-1")));
						ledgers.add(ledgerEntry);

						ledgerEntry = voucherFactory.createENVELOPEBODYDATATALLYMESSAGEVOUCHERALLLEDGERENTRIESLIST();
						ledgerEntry.setLEDGERNAME(MVCConstants.TALLY_LEDGER_CASH);
						ledgerEntry.setISDEEMEDPOSITIVE(MVCConstants.TALLY_NEGATIVE_AMT_NO);
						ledgerEntry.setAMOUNT(txnDetails.getTxn().getTotalAmt());
						ledgers.add(ledgerEntry);
						logger.info("The CASH tender ledgers created successfully!!");
						break;
										
					}
				}
			}

		}

		logger.info("The voucher ledger details has been created successfully!!");

	}

}
