/**
 * 
 */
package com.punj.app.ecommerce.services.gst.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.punj.app.ecommerce.domains.customer.Customer;
import com.punj.app.ecommerce.domains.gstr.GSTHSN;
import com.punj.app.ecommerce.domains.gstr.GSTInvoice;
import com.punj.app.ecommerce.domains.transaction.ReceiptItemTax;
import com.punj.app.ecommerce.domains.transaction.ids.TransactionId;
import com.punj.app.ecommerce.repositories.gstr.GSTHSNRepository;
import com.punj.app.ecommerce.repositories.gstr.GSTInvoiceRepository;
import com.punj.app.ecommerce.services.common.CommonService;
import com.punj.app.ecommerce.services.gst.GSTRService;
import com.punj.app.ecommerce.utils.Utils;

/**
 * @author admin
 *
 */
@Service
public class GSTRServiceImpl implements GSTRService {

	private static final Logger logger = LogManager.getLogger();
	private GSTHSNRepository gstHSNRepository;
	private GSTInvoiceRepository gstInvoiceRepository;
	private CommonService commonService;

	@Value("${commerce.list.max.customer.perpage}")
	private Integer maxResultPerPage;

	@Value("${commerce.list.max.pageno}")
	private Integer maxPageBtns;

	/**
	 * @param gstHSNRepository
	 *            the gstHSNRepository to set
	 */
	@Autowired
	public void setGSTHSNRepository(GSTHSNRepository gstHSNRepository) {
		this.gstHSNRepository = gstHSNRepository;
	}

	/**
	 * @param gstInvoiceRepository
	 *            the gstInvoiceRepository to set
	 */
	@Autowired
	public void setGSTInvoiceRepository(GSTInvoiceRepository gstInvoiceRepository) {
		this.gstInvoiceRepository = gstInvoiceRepository;
	}

	/**
	 * @param commonService
	 *            the commonService to set
	 */
	@Autowired
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	@Override
	public List<GSTHSN> retrieveGSTRHSNData(Integer locationId, String finMonth) {
		LocalDateTime startDate = Utils.convertMonthToStartDate(new Integer(finMonth));
		LocalDateTime endDate = Utils.convertMonthToEndDate(new Integer(finMonth));

		List<GSTHSN> hsnDetails = this.gstHSNRepository.retrieveHSNDetails(locationId, startDate.toString(), endDate.toString());
		logger.info("The HSN details for the invoices has been successfully retrieved");
		return hsnDetails;
	}

	@Override
	public Map<Customer, Map<String, List<GSTInvoice>>> retrieveGSTRInvoiceData(Integer locationId, String finMonth) {
		LocalDateTime startDate = Utils.convertMonthToStartDate(new Integer(finMonth));
		LocalDateTime endDate = Utils.convertMonthToEndDate(new Integer(finMonth));

		Map<Customer, Map<String, List<GSTInvoice>>> customerInvoiceMap = null;
		Map<String, List<GSTInvoice>> invoiceItemMap = null;

		List<GSTInvoice> itemTaxList = null;

		String customerGSTNo = null;
		Customer customer = null;
		List<GSTInvoice> invoiceDetails = this.gstInvoiceRepository.retrieveInvoiceDetails(locationId, startDate.toString(), endDate.toString());
		if (invoiceDetails != null && !invoiceDetails.isEmpty()) {

			customerInvoiceMap = new HashMap<>();
			TransactionId txnId = null;

			for (GSTInvoice gstInvoice : invoiceDetails) {
				if (customerGSTNo == null || !customerGSTNo.equals(gstInvoice.getCustomerGSTNo())) {
					customer = new Customer();
					customer.setCustomerId(gstInvoice.getCustomerId());
					customer.setName(gstInvoice.getCustomerName());
					customer.setGstNo(gstInvoice.getCustomerGSTNo());

					txnId = new TransactionId();
					txnId.setBusinessDate(gstInvoice.getGstInvoiceId().getBusinessDate());
					txnId.setLocationId(gstInvoice.getGstInvoiceId().getLocationId());
					txnId.setRegister(gstInvoice.getGstInvoiceId().getRegister());
					txnId.setTransactionSeq(gstInvoice.getGstInvoiceId().getTransactionSeq());

					invoiceItemMap = new HashMap<>();
					itemTaxList = new ArrayList<>();
					itemTaxList.add(gstInvoice);

					invoiceItemMap.put(txnId.toString(), itemTaxList);
					customerInvoiceMap.put(customer, invoiceItemMap);

					customerGSTNo = gstInvoice.getCustomerGSTNo();

				} else {

					invoiceItemMap = customerInvoiceMap.get(customer);
					txnId = new TransactionId();
					txnId.setBusinessDate(gstInvoice.getGstInvoiceId().getBusinessDate());
					txnId.setLocationId(gstInvoice.getGstInvoiceId().getLocationId());
					txnId.setRegister(gstInvoice.getGstInvoiceId().getRegister());
					txnId.setTransactionSeq(gstInvoice.getGstInvoiceId().getTransactionSeq());

					itemTaxList = invoiceItemMap.get(txnId.toString());
					if (itemTaxList != null && !itemTaxList.isEmpty()) {
						itemTaxList.add(gstInvoice);
						invoiceItemMap.put(txnId.toString(), itemTaxList);
					} else {
						itemTaxList = new ArrayList<>();
						itemTaxList.add(gstInvoice);
						invoiceItemMap.put(txnId.toString(), itemTaxList);
						customerInvoiceMap.put(customer, invoiceItemMap);
					}

					customerGSTNo = gstInvoice.getCustomerGSTNo();

				}
			}

		}
		logger.info("The Invoices details for provided period has been successfully retrieved");
		return customerInvoiceMap;
	}

}
