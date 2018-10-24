package com.punj.app.ecommerce.utils.gst;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ibm.icu.math.BigDecimal;
import com.punj.app.ecommerce.domains.customer.Customer;
import com.punj.app.ecommerce.domains.gstr.GSTHSN;
import com.punj.app.ecommerce.domains.gstr.GSTInvoice;
import com.punj.app.ecommerce.gst.r1.B2b;
import com.punj.app.ecommerce.gst.r1.Datum;
import com.punj.app.ecommerce.gst.r1.GSTR1;
import com.punj.app.ecommerce.gst.r1.Hsn;
import com.punj.app.ecommerce.gst.r1.Inv;
import com.punj.app.ecommerce.gst.r1.Itm;
import com.punj.app.ecommerce.gst.r1.ItmDet;

public class GSTROneUtil {

	private static final Logger logger = LogManager.getLogger();

	private GSTROneUtil() {
		throw new IllegalStateException("GSTROneUtil class");
	}

	public static String convertFinPeriod(String financialYear, String financialMonth) {
		String finPeriod = null;

		return finPeriod;
	}

	public static GSTR1 generateGSTR1(String financialYear, String financialMonth, String gstNo, List<GSTHSN> saleItemDetails,
			Map<Customer, Map<String, List<GSTInvoice>>> invoiceTxns) {
		GSTR1 gstr1 = new GSTR1();

		gstr1.setFp(GSTROneUtil.convertFinPeriod(financialYear, financialMonth));
		gstr1.setGstin(gstNo);

		Hsn hsnDetails = GSTROneUtil.convertItemDetailsToHSN(saleItemDetails);
		gstr1.setHsn(hsnDetails);

		List<B2b> b2bList = GSTROneUtil.convertInvoiceToB2b(invoiceTxns);
		gstr1.setB2b(b2bList);

		return gstr1;
	}

	public static List<B2b> convertInvoiceToB2b(Map<Customer, Map<String, List<GSTInvoice>>> invoiceTxns) {
		List<B2b> b2bList = new ArrayList<>();
		Map<String, List<GSTInvoice>> txnDetailsMap = null;
		for (Customer customer : invoiceTxns.keySet()) {
			txnDetailsMap = invoiceTxns.get(customer);
			B2b customerInvoices = GSTROneUtil.generateInvoice(txnDetailsMap);
			customerInvoices.setCtin(customer.getGstNo());
			b2bList.add(customerInvoices);
		}
		return b2bList;
	}

	public static B2b generateInvoice(Map<String, List<GSTInvoice>> invoiceTxns) {

		B2b businessToBusiness = null;
		if (invoiceTxns != null && !invoiceTxns.isEmpty()) {
			businessToBusiness = new B2b();
			List<Inv> invList = new ArrayList<>();
			Inv invoiceDetails = null;
			for (String txnId : invoiceTxns.keySet()) {
				invoiceDetails = GSTROneUtil.generateInvoiceDetails(invoiceTxns.get(txnId));
				invList.add(invoiceDetails);
			}
			businessToBusiness.setInv(invList);
		}
		return businessToBusiness;
	}

	public static Inv generateInvoiceDetails(List<GSTInvoice> gstInvoiceList) {
		Inv invoiceDetails = new Inv();

		GSTInvoice gstInvoice = gstInvoiceList.get(0);

		// invoice no
		invoiceDetails.setInum(gstInvoice.getInvoiceNo().toString());
		invoiceDetails.setIdt(gstInvoice.getGstInvoiceId().getBusinessDate().toString());
		invoiceDetails.setVal(gstInvoice.getGrossAmount().doubleValue());
		// what is this no
		invoiceDetails.setPos("03");
		invoiceDetails.setRchrg("N");
		invoiceDetails.setInvTyp("R");
		invoiceDetails.setAdditionalProperty("diff_percent", 0.65);

		List<Itm> itemList = GSTROneUtil.generateItemDetails(gstInvoiceList);

		invoiceDetails.setItms(itemList);

		return invoiceDetails;

	}

	public static List<Itm> generateItemDetails(List<GSTInvoice> gstInvoiceList) {
		List<Itm> itemList = new ArrayList<>();
		Itm item = null;
		int count = 1;
		ItmDet itemDetails = null;
		for (GSTInvoice gstInvoice : gstInvoiceList) {
			item = new Itm();
			String seqNum = gstInvoice.getGstInvoiceId().getTaxRate().toBigInteger().toString() + count;
			// unique seq
			item.setNum(new Integer(seqNum));

			itemDetails = new ItmDet();
			// Tax rate
			itemDetails.setRt(gstInvoice.getGstInvoiceId().getTaxRate().intValue());
			// Taxable value
			itemDetails.setTxval(gstInvoice.getTaxableAmount().doubleValue());

			if (gstInvoice.getSgstAmount() != null)
				itemDetails.setSamt(gstInvoice.getSgstAmount().doubleValue());
			else
				itemDetails.setSamt(BigDecimal.ZERO.doubleValue());
			if (gstInvoice.getCgstAmount() != null)
				itemDetails.setCamt(gstInvoice.getCgstAmount().doubleValue());
			else
				itemDetails.setCamt(BigDecimal.ZERO.doubleValue());
			if (gstInvoice.getIgstAmount() != null)
				itemDetails.setIamt(gstInvoice.getIgstAmount().doubleValue());
			else
				itemDetails.setIamt(BigDecimal.ZERO.doubleValue());
			item.setItmDet(itemDetails);
			itemList.add(item);
		}

		return itemList;
	}

	public static Datum generateHSNDetails(int seqNo, GSTHSN saleItem) {

		Datum hsnDetails = new Datum();

		// unique seq for all the hsns
		hsnDetails.setNum(seqNo);

		hsnDetails.setHsnSc(saleItem.getHsnNo());
		hsnDetails.setDesc(saleItem.getItemDesc());
		hsnDetails.setUqc("NOS");
		hsnDetails.setQty(saleItem.getQty().intValue());
		hsnDetails.setVal(saleItem.getGrossAmount().doubleValue());
		hsnDetails.setTxval(saleItem.getTaxableAmount().doubleValue());
		if (saleItem.getIgstAmount() != null)
			hsnDetails.setIamt(saleItem.getIgstAmount().doubleValue());
		else
			hsnDetails.setIamt(BigDecimal.ZERO.doubleValue());
		if (saleItem.getCgstAmount() != null) {
			hsnDetails.setCamt(saleItem.getCgstAmount().doubleValue());
			hsnDetails.setSamt(saleItem.getSgstAmount().doubleValue());
		} else {
			hsnDetails.setCamt(BigDecimal.ZERO.doubleValue());
			hsnDetails.setSamt(BigDecimal.ZERO.doubleValue());
		}

		hsnDetails.setCsamt(BigDecimal.ZERO.doubleValue());

		logger.info("The receipt item details has been converted to GSTR HSN details successfully");

		return hsnDetails;

	}

	public static Hsn convertItemDetailsToHSN(List<GSTHSN> saleItemDetails) {
		Hsn hsn = new Hsn();
		List<Datum> hsnDetails = new ArrayList<>(saleItemDetails.size());

		int counter = 1;
		for (GSTHSN itemDetails : saleItemDetails) {
			Datum data = GSTROneUtil.generateHSNDetails(counter, itemDetails);
			hsnDetails.add(data);
			counter++;
		}
		logger.info("The hsn details has been created successfully");
		hsn.setData(hsnDetails);

		return hsn;
	}

}
