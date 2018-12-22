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
import com.punj.app.ecommerce.domains.transaction.TenderLineItem;
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
public class TallyGenericVoucher implements TallyVoucher {

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

		// add logic for getting the voucher list
		
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

}
