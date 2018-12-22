/**
 * 
 */
package com.punj.app.ecommerce.tally;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.services.dtos.transaction.TransactionDTO;
import com.punj.app.ecommerce.tally.export.voucher.ENVELOPE;
import com.punj.app.ecommerce.tally.export.voucher.ENVELOPE.BODY.DATA.TALLYMESSAGE.VOUCHER;
import com.punj.app.ecommerce.tally.vouchers.TallyGenericVoucher;
import com.punj.app.ecommerce.tally.vouchers.TallyPaymentVoucher;
import com.punj.app.ecommerce.tally.vouchers.TallyReceiptVoucher;
import com.punj.app.ecommerce.tally.vouchers.TallyReturnVoucher;
import com.punj.app.ecommerce.tally.vouchers.TallySaleVoucher;
import com.punj.app.ecommerce.tally.vouchers.TallyVoucher;

/**
 * @author amitpunj
 *
 */
public class TallyVoucherBuilder {

	private static final Logger logger = LogManager.getLogger();

	public static Object createTallyVoucher(TransactionDTO txnDetails, String txnType) {
		Object envelope = null;
		TallyVoucher voucher = null;

		switch (txnType) {
		case MVCConstants.TXN_SALE_PARAM:
			voucher = new TallySaleVoucher();
			break;
		case MVCConstants.TXN_RETURN_PARAM:
			voucher = new TallyReturnVoucher();
			break;
		case MVCConstants.TXN_NOSALE_PARAM:
			voucher = new TallyPaymentVoucher();
			break;
		case MVCConstants.TXN_CUST_PAYMENT_PARAM:
			voucher = new TallyReceiptVoucher();
			break;
		case MVCConstants.TXN_SUP_PAYMENT_PARAM:
			voucher = new TallyPaymentVoucher();
			break;
		}

		if (voucher != null) {
			envelope = voucher.create(txnDetails);
			logger.info("The voucher with provided details has been created successfully");
		}

		return envelope;
	}

	public static ENVELOPE createTallyVoucherExport(List<Object> envelopes) {
		ENVELOPE finalEnvelope = null;
		TallyVoucher voucher = null;

		TallyVoucher genericVoucher = new TallyGenericVoucher();
		finalEnvelope =(ENVELOPE)genericVoucher.create(null);
		List<VOUCHER> finalVouchers= finalEnvelope.getBODY().getDATA().getTALLYMESSAGE().getVOUCHER();
		
		for (Object envelope : envelopes) {
			finalVouchers.addAll(((ENVELOPE)envelope).getBODY().getDATA().getTALLYMESSAGE().getVOUCHER());
		}

		logger.info("The voucher with provided details has been created successfully");

		return finalEnvelope;
	}

}
