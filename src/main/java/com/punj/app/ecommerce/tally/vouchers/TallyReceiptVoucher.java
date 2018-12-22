/**
 * 
 */
package com.punj.app.ecommerce.tally.vouchers;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.domains.customer.Customer;
import com.punj.app.ecommerce.domains.payment.AccountJournal;
import com.punj.app.ecommerce.domains.payment.JournalTender;
import com.punj.app.ecommerce.domains.supplier.Supplier;
import com.punj.app.ecommerce.services.dtos.transaction.TransactionDTO;
import com.punj.app.ecommerce.tally.export.voucher.ENVELOPE;
import com.punj.app.ecommerce.tally.export.voucher.ENVELOPE.BODY;
import com.punj.app.ecommerce.tally.export.voucher.ENVELOPE.BODY.DATA;
import com.punj.app.ecommerce.tally.export.voucher.ENVELOPE.BODY.DATA.TALLYMESSAGE;
import com.punj.app.ecommerce.tally.export.voucher.ENVELOPE.BODY.DATA.TALLYMESSAGE.VOUCHER;
import com.punj.app.ecommerce.tally.export.voucher.ENVELOPE.BODY.DATA.TALLYMESSAGE.VOUCHER.ALLLEDGERENTRIESLIST;
import com.punj.app.ecommerce.tally.export.voucher.ENVELOPE.BODY.DESC;
import com.punj.app.ecommerce.tally.export.voucher.ENVELOPE.BODY.DESC.STATICVARIABLES;
import com.punj.app.ecommerce.tally.export.voucher.ENVELOPE.HEADER;
import com.punj.app.ecommerce.tally.export.voucher.ObjectFactory;

/**
 * @author amitpunj
 *
 */
public class TallyReceiptVoucher implements TallyVoucher {

	private static final Logger logger = LogManager.getLogger();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.punj.app.ecommerce.tally.util.TallyVoucher#create()
	 */
	@Override
	public Object create(TransactionDTO txnDetails) {
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
		AccountJournal accountJournal = txnDetails.getJournalDetails();
		this.createVoucherHeaderDetails(voucherData, txnDetails, accountJournal);
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

	private void createVoucherHeaderDetails(VOUCHER voucherData, TransactionDTO txnDetails, AccountJournal accountJournal) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		voucherData.setDATE(accountJournal.getCreatedDate().format(formatter));

		voucherData.setNARRATION(accountJournal.getComments());

		voucherData.setVOUCHERNUMBER(txnDetails.getJournalDetails().getJournalId().toString());
		voucherData.setVOUCHERTYPENAME(MVCConstants.TALLY_VOUCHER_RECEIPT);
		List<ALLLEDGERENTRIESLIST> ledgerList = voucherData.getALLLEDGERENTRIESLIST();
		this.createVoucherLedgers(ledgerList, txnDetails, accountJournal);

	}

	private void createVoucherLedgers(List<ALLLEDGERENTRIESLIST> ledgers, TransactionDTO txnDetails, AccountJournal accountJournal) {

		List<JournalTender> journalTenders = accountJournal.getJournalTenders();

		ObjectFactory voucherFactory = new ObjectFactory();
		ALLLEDGERENTRIESLIST ledgerEntry = null;

		Customer customer = txnDetails.getCustomer();
		Supplier supplier = txnDetails.getSupplier();
		List<JournalTender> tenderLineItems = accountJournal.getJournalTenders();

		if (tenderLineItems != null && !tenderLineItems.isEmpty()) {
			logger.info("There are multiple tenders in the transactions");

			logger.info("The tenders must be ledgered against the provided customer ({}) in the transaction", customer.getName());
			for (JournalTender tender : tenderLineItems) {

				ledgerEntry = voucherFactory.createENVELOPEBODYDATATALLYMESSAGEVOUCHERALLLEDGERENTRIESLIST();
				ledgerEntry.setLEDGERNAME(customer.getName());
				ledgerEntry.setISDEEMEDPOSITIVE(MVCConstants.TALLY_NEGATIVE_AMT_NO);
				ledgerEntry.setAMOUNT(accountJournal.getAmount());
				ledgers.add(ledgerEntry);

				ledgerEntry = voucherFactory.createENVELOPEBODYDATATALLYMESSAGEVOUCHERALLLEDGERENTRIESLIST();
				ledgerEntry.setLEDGERNAME(MVCConstants.TALLY_LEDGER_CASH);
				ledgerEntry.setISDEEMEDPOSITIVE(MVCConstants.TALLY_NEGATIVE_AMT_YES);
				ledgerEntry.setAMOUNT(accountJournal.getAmount().multiply(new BigDecimal("-1")));
				ledgers.add(ledgerEntry);
				logger.info("The CASH tender ledgers created successfully!!");
				break;

			}

		}

		logger.info("The voucher ledger details has been created successfully!!");

	}

}
