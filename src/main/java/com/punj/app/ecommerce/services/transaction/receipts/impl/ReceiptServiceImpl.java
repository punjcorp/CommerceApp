/**
 * 
 */
package com.punj.app.ecommerce.services.transaction.receipts.impl;

import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.controller.common.transformer.TransactionTransformer;
import com.punj.app.ecommerce.domains.common.Location;
import com.punj.app.ecommerce.domains.customer.Customer;
import com.punj.app.ecommerce.domains.supplier.Supplier;
import com.punj.app.ecommerce.domains.transaction.ReceiptItemTax;
import com.punj.app.ecommerce.domains.transaction.TransactionCustomer;
import com.punj.app.ecommerce.domains.transaction.TransactionReceipt;
import com.punj.app.ecommerce.domains.transaction.ids.SaleLineItemId;
import com.punj.app.ecommerce.domains.transaction.ids.TransactionId;
import com.punj.app.ecommerce.domains.transaction.ids.TransactionReceiptId;
import com.punj.app.ecommerce.domains.transaction.seqs.SaleInvoice;
import com.punj.app.ecommerce.domains.transaction.shipment.Shipment;
import com.punj.app.ecommerce.models.transaction.SaleTransactionReceipt;
import com.punj.app.ecommerce.repositories.transaction.ReceiptItemTaxRepository;
import com.punj.app.ecommerce.repositories.transaction.TransactionReceiptRepository;
import com.punj.app.ecommerce.services.CustomerService;
import com.punj.app.ecommerce.services.SupplierService;
import com.punj.app.ecommerce.services.TransactionSeqService;
import com.punj.app.ecommerce.services.TransactionService;
import com.punj.app.ecommerce.services.common.CommonService;
import com.punj.app.ecommerce.services.common.ServiceConstants;
import com.punj.app.ecommerce.services.dtos.transaction.SaleTransactionReceiptDTO;
import com.punj.app.ecommerce.services.dtos.transaction.TransactionDTO;
import com.punj.app.ecommerce.services.transaction.receipts.ReceiptService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * @author admin
 *
 */
@Service
public class ReceiptServiceImpl implements ReceiptService {

	private static final Logger logger = LogManager.getLogger();
	private CommonService commonService;
	private TransactionService txnService;
	private TransactionSeqService txnSeqService;
	private SupplierService supplierService;
	private CustomerService customerService;

	private ReceiptItemTaxRepository receiptItemTaxRepository;
	private TransactionReceiptRepository txnReceiptRepository;

	@Value("${commerce.resource.bundle.base}")
	private String resourceBundleBase;

	@Value("${commerce.txn.receipt.copies}")
	private Integer receiptCopies;

	/**
	 * @param commonService
	 *            the commonService to set
	 */
	@Autowired
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	/**
	 * @param txnService
	 *            the txnService to set
	 */
	@Autowired
	public void setTxnService(TransactionService txnService) {
		this.txnService = txnService;
	}

	/**
	 * @param txnSeqService
	 *            the txnSeqService to set
	 */
	@Autowired
	public void setTxnSeqService(TransactionSeqService txnSeqService) {
		this.txnSeqService = txnSeqService;
	}

	/**
	 * @param supplierService
	 *            the supplierService to set
	 */
	@Autowired
	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	/**
	 * @param customerService
	 *            the customerService to set
	 */
	@Autowired
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	/**
	 * @param receiptItemTaxRepository
	 *            the receiptItemTaxRepository to set
	 */
	@Autowired
	public void setReceiptItemTaxRepository(ReceiptItemTaxRepository receiptItemTaxRepository) {
		this.receiptItemTaxRepository = receiptItemTaxRepository;
	}

	/**
	 * @param txnReceiptRepository
	 *            the txnReceiptRepository to set
	 */
	@Autowired
	public void setTxnReceiptRepository(TransactionReceiptRepository txnReceiptRepository) {
		this.txnReceiptRepository = txnReceiptRepository;
	}

	private List<ReceiptItemTax> retrieveReceiptLineItems(TransactionId txnId) {
		ReceiptItemTax receiptItemTaxCriteria = new ReceiptItemTax();
		SaleLineItemId saleLineItemId = new SaleLineItemId();
		saleLineItemId.setLocationId(txnId.getLocationId());
		saleLineItemId.setRegister(txnId.getRegister());
		saleLineItemId.setBusinessDate(txnId.getBusinessDate());
		saleLineItemId.setTransactionSeq(txnId.getTransactionSeq());
		receiptItemTaxCriteria.setSaleLineItemId(saleLineItemId);

		List<ReceiptItemTax> receiptItems = this.receiptItemTaxRepository.findAll(Example.of(receiptItemTaxCriteria), new Sort(Sort.Direction.ASC, "saleLineItemId.lineItemSeq"));

		if (receiptItems != null && !receiptItems.isEmpty()) {
			logger.info("The receipt items has been retrieved successfully");
		} else {
			logger.info("There were no items found for the transaction");
		}
		return receiptItems;
	}

	private void updateCustomerDetails(SaleTransactionReceiptDTO txnReceipt, TransactionCustomer txnCustomer) {
		String custType = txnCustomer.getTransactionCustomerId().getCustomerType();
		BigInteger custId = txnCustomer.getTransactionCustomerId().getCustomerId();
		if (custType.equals(ServiceConstants.CUSTOMER_TYPE_CLIENT)) {
			Customer customer = this.customerService.searchCustomerDetails(custId);
			txnReceipt.setCustomerDetails(customer);
			logger.info("The customer details has been updated successfully");
		} else if (custType.equals(ServiceConstants.CUSTOMER_TYPE_SUPPLIER)) {
			Supplier supplier = this.supplierService.searchSupplier(custId.intValue());
			txnReceipt.setSupplierDetails(supplier);
			logger.info("The supplier details has been updated successfully");
		}
	}

	@Override
	public SaleTransactionReceiptDTO generateTransactionReceipt(TransactionId txnId) {
		SaleTransactionReceiptDTO txnReceipt = null;
		TransactionDTO txnDTO = this.txnService.searchTransactionDetails(txnId.toString());
		if (txnDTO != null) {
			txnReceipt = new SaleTransactionReceiptDTO();

			// Update Transaction Header Details
			txnReceipt.setTxn(txnDTO.getTxn());

			// Update Location Details for transaction
			Location location = this.commonService.retrieveLocationDetails(txnId.getLocationId());
			txnReceipt.setLocation(location);

			// Update Receipt Item details
			List<ReceiptItemTax> receiptItems = this.retrieveReceiptLineItems(txnId);
			txnReceipt.setTxnLineItems(receiptItems);

			// Update Receipt Tender details
			txnReceipt.setTenderLineItems(txnDTO.getTenderLineItems());

			// Update Transaction Shipment Details
			Shipment shipment = this.txnService.retrieveTxnShipment(txnId);
			txnReceipt.setShipmentDetails(shipment);

			// Update Transaction Customer Details
			TransactionCustomer txnCustomer = this.txnService.retrieveTxnCustomer(txnId);
			txnReceipt.setTxnCustomer(txnCustomer);
			if (txnCustomer != null)
				this.updateCustomerDetails(txnReceipt, txnCustomer);

		} else {
			logger.info("There was no details found for the provided transaction details");
		}

		return txnReceipt;
	}

	@Override
	public List<TransactionReceipt> searchTransactionReceipt(TransactionId txnId, Integer receiptCount) {
		List<TransactionReceipt> finalReceipts = new ArrayList<>(receiptCount.intValue());

		TransactionReceipt txnReceipt = new TransactionReceipt();
		TransactionReceiptId txnReceiptId = new TransactionReceiptId();
		txnReceiptId.setBusinessDate(txnId.getBusinessDate());
		txnReceiptId.setLocationId(txnId.getLocationId());
		txnReceiptId.setRegister(txnId.getRegister());
		txnReceiptId.setTransactionSeq(txnId.getTransactionSeq());
		txnReceipt.setTransactionReceiptId(txnReceiptId);

		List<TransactionReceipt> txnReceipts = this.txnReceiptRepository.findAll(Example.of(txnReceipt));

		if (txnReceipts != null && !txnReceipts.isEmpty()) {
			for (TransactionReceipt txnReceiptData : txnReceipts) {
				for (int count = 0; count < receiptCount; count++) {
					if (txnReceiptData.getTransactionReceiptId().getReceiptType().equals(ServiceConstants.RCPT_ARRAY[count])) {
						finalReceipts.add(txnReceiptData);
						break;
					}

				}

			}
			logger.info("The transaction receipts has been retrieved successfully");
		} else {
			logger.info("There were no receipts found for the transactions");
		}

		return finalReceipts;

	}

	private List<JRBeanCollectionDataSource> prepareReceiptsArray(SaleTransactionReceiptDTO receiptDetails, String username, Integer receiptCopies) {
		String receiptType = null;
		String receiptMsg = null;
		SaleTransactionReceipt txnReceipt = null;

		List<JRBeanCollectionDataSource> txnReceipts = new ArrayList<>(receiptCopies);

		List<SaleTransactionReceipt> txnList = null;
		JRBeanCollectionDataSource txnDS = null;
		for (int i = 0; i < receiptCopies; i++) {
			receiptType = ServiceConstants.RCPT_DISPLAY_ARRAY[i];

			txnReceipt = TransactionTransformer.transformReceiptDetails(receiptDetails, username);
			txnReceipt.setReceiptType(receiptType);
			txnReceipt.setReceiptMsg(receiptMsg);

			txnList = new ArrayList<>(1);
			txnList.add(txnReceipt);
			txnDS = new JRBeanCollectionDataSource(txnList);

			txnReceipts.add(txnDS);
		}

		return txnReceipts;
	}

	@Override
	public void saveTxnReceipts(List<JasperPrint> jasperPrints, String username, TransactionId txnId, Boolean isExisting) {
		int count = 1;
		Boolean isOriginal = Boolean.FALSE;
		try {
			List<TransactionReceipt> finalTxnReceipts = new ArrayList<>();
			for (JasperPrint jasperPrint : jasperPrints) {
				byte pdfBytes[] = JasperExportManager.exportReportToPdf(jasperPrint);
				if (count == 1)
					isOriginal = Boolean.TRUE;
				else
					isOriginal = Boolean.FALSE;

				// This section will save the receipt in database
				List<TransactionReceipt> txnReceipts = TransactionTransformer.getReceipts(pdfBytes, txnId, username, isOriginal, isExisting);
				finalTxnReceipts.addAll(txnReceipts);
				count++;
			}
			this.txnReceiptRepository.save(finalTxnReceipts);
			logger.info("The transaction receipts has been saved in DB successfully");
		} catch (JRException e) {
			logger.error("There is an error while generating receipt for txn", e);
		}

	}

	@Override
	public List<JasperPrint> generateTxnJasperReports(TransactionId txnId, Integer receiptCount, Locale locale, String username, BigInteger invoiceNo) {
		List<JasperPrint> jasperPrints = null;
		try {
			SaleTransactionReceiptDTO txnReceiptDTO = this.generateTransactionReceipt(txnId);
			if (txnReceiptDTO != null) {
				txnReceiptDTO.setInvoiceNo(invoiceNo);
				List<JRBeanCollectionDataSource> txnReceiptDSs = this.prepareReceiptsArray(txnReceiptDTO, username, receiptCount);

				if (txnReceiptDSs != null & !txnReceiptDSs.isEmpty()) {
					InputStream txnReceiptReportStream = getClass().getResourceAsStream(ServiceConstants.CREATIVE_TXN_RECEIPT_REPORT);
					JasperReport jasperReport = (JasperReport) JRLoader.loadObject(txnReceiptReportStream);
					logger.debug("The parent receipt report has been compiled now");

					InputStream txnReceiptStreamChild = getClass().getResourceAsStream(ServiceConstants.CREATIVE_TXN_RECEIPT_ITEMS_REPORT);
					JasperReport jasperReportChild = (JasperReport) JRLoader.loadObject(txnReceiptStreamChild);
					logger.debug("The child receipt report has been compiled now");

					ResourceBundle resourceBundle = ResourceBundle.getBundle(this.resourceBundleBase);
					logger.debug("The receipt data source and resource bundle is ready now");

					Map<String, Object> paramMap = new HashMap<>();
					paramMap.put(MVCConstants.SUB_REPORT_DIR, jasperReportChild);
					paramMap.put(JRParameter.REPORT_RESOURCE_BUNDLE, resourceBundle);
					paramMap.put(JRParameter.REPORT_LOCALE, locale);
					logger.debug("The parameter set for setting receipt data is ready");

					jasperPrints = new ArrayList<>(txnReceiptDSs.size());
					JasperPrint jasperPrint = null;
					for (JRBeanCollectionDataSource txnDS : txnReceiptDSs) {
						jasperPrint = JasperFillManager.fillReport(jasperReport, paramMap, txnDS);
						jasperPrints.add(jasperPrint);
					}

				} else {
					logger.error("There were no receipt data sources found for {} txn", txnId.toString());
				}
			} else {
				logger.error("There were no receipts found for {} txn", txnId.toString());
			}
		} catch (JRException e) {
			logger.error("There has been error while generating jasper receipts for the transactions", e);
		}
		return jasperPrints;

	}

	private void regenerateReceipt(TransactionId txnId, Integer receiptCopies, Locale locale, String username, BigInteger invoiceNo) {
		List<JasperPrint> jasperReports = this.generateTxnJasperReports(txnId, receiptCopies, locale, username, invoiceNo);
		if (jasperReports != null && !jasperReports.isEmpty()) {
			this.saveTxnReceipts(jasperReports, username, txnId, Boolean.TRUE);
		} else {
			logger.error("The transaction jaspe reports preparation has failed");
		}
	}

	@Override
	@Transactional
	public void regenerateReceipts(BigInteger startingInvoiceNo, BigInteger endingInvoiceNo, Integer receiptCopies, Locale locale, String username) {

		List<SaleInvoice> invoiceList = this.txnSeqService.retrieveSaleInvoices(startingInvoiceNo, endingInvoiceNo);

		TransactionId txnId = null;

		for (SaleInvoice invoice : invoiceList) {
			txnId = new TransactionId();
			txnId.setBusinessDate(invoice.getBusinessDate());
			txnId.setLocationId(invoice.getLocationId());
			txnId.setRegister(invoice.getRegister());
			txnId.setTransactionSeq(invoice.getTransactionSeq());
			this.regenerateReceipt(txnId, receiptCopies, locale, username, invoice.getInvoiceNo());

		}

		logger.info("The regeneration of receipts has been successful!!");

	}

}
