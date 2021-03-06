/**
 * 
 */
package com.punj.app.ecommerce.controller.common.transformer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.punj.app.ecommerce.domains.supplier.Supplier;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.domains.customer.Customer;
import com.punj.app.ecommerce.domains.transaction.ReceiptItemTax;
import com.punj.app.ecommerce.domains.transaction.SaleLineItem;
import com.punj.app.ecommerce.domains.transaction.TaxLineItem;
import com.punj.app.ecommerce.domains.transaction.TenderLineItem;
import com.punj.app.ecommerce.domains.transaction.Transaction;
import com.punj.app.ecommerce.domains.transaction.TransactionCustomer;
import com.punj.app.ecommerce.domains.transaction.TransactionLineItem;
import com.punj.app.ecommerce.domains.transaction.TransactionReceipt;
import com.punj.app.ecommerce.domains.transaction.ids.SaleLineItemId;
import com.punj.app.ecommerce.domains.transaction.ids.TransactionCustomerId;
import com.punj.app.ecommerce.domains.transaction.ids.TransactionId;
import com.punj.app.ecommerce.domains.transaction.ids.TransactionLineItemId;
import com.punj.app.ecommerce.domains.transaction.ids.TransactionReceiptId;
import com.punj.app.ecommerce.models.common.LocationBean;
import com.punj.app.ecommerce.models.customer.CustomerBean;
import com.punj.app.ecommerce.models.transaction.SaleReceiptLineItem;
import com.punj.app.ecommerce.models.transaction.SaleTransaction;
import com.punj.app.ecommerce.models.transaction.SaleTransactionReceipt;
import com.punj.app.ecommerce.models.transaction.TransactionHeader;
import com.punj.app.ecommerce.services.common.ServiceConstants;
import com.punj.app.ecommerce.services.dtos.transaction.SaleTransactionReceiptDTO;
import com.punj.app.ecommerce.services.dtos.transaction.TransactionDTO;
import com.punj.app.ecommerce.utils.NumberToWordConverter;

/**
 * @author admin
 *
 */
public class TransactionTransformer {
	private static final Logger logger = LogManager.getLogger();

	private TransactionTransformer() {
		throw new IllegalStateException("TransactionTransformer class");
	}

	public static TransactionDTO transformSaleTransaction(SaleTransaction saleTxn, String username) {
		TransactionDTO txnDTO = new TransactionDTO();

		Transaction txn = TransactionTransformer.transformTransactionDetails(saleTxn.getTransactionHeader(), MVCConstants.TXN_SALE_PARAM);
		txnDTO.setTxn(txn);

		TransactionId txnId = txn.getTransactionId();

		List<TransactionLineItem> txnLIs = TransactionTransformer.transformTxnLineItems(saleTxn, txnId);
		txnDTO.setTxnLineItems(txnLIs);

		List<TaxLineItem> taxLIs = new ArrayList<>();
		TransactionTransformer.transformSaleLineItems(txnDTO, saleTxn, taxLIs, txnId, MVCConstants.TXN_SALE_PARAM);

		List<TenderLineItem> tenderLIs = TransactionTransformer.transformTenderLineItems(saleTxn, saleTxn.getTxnTenderLineItems(), txnId);
		txnDTO.setTenderLineItems(tenderLIs);

		if (saleTxn.getCustomer() != null && StringUtils.isNotBlank(saleTxn.getCustomer().getName())) {
			TransactionCustomer txnCustomer = TransactionTransformer.transformCustomerBean(saleTxn.getCustomer(), txnId);
			txnDTO.setTxnCustomer(txnCustomer);
			if (txnCustomer.getTransactionCustomerId().getCustomerId() == null) {
				Customer customer = TransactionTransformer.transformCustomerBeanToCustomer(saleTxn.getCustomer(), username);
				txnDTO.setCustomer(customer);
			}
		}

		return txnDTO;
	}

	public static TransactionDTO transformReturnTransaction(SaleTransaction saleTxn, String username) {
		TransactionDTO txnDTO = new TransactionDTO();

		Transaction txn = TransactionTransformer.transformTransactionDetails(saleTxn.getTransactionHeader(), MVCConstants.TXN_RETURN_PARAM);
		txnDTO.setTxn(txn);

		TransactionId txnId = txn.getTransactionId();

		List<TransactionLineItem> txnLIs = TransactionTransformer.transformTxnLineItems(saleTxn, txnId);
		txnDTO.setTxnLineItems(txnLIs);

		List<TaxLineItem> taxLIs = new ArrayList<>();
		TransactionTransformer.transformSaleLineItems(txnDTO, saleTxn, taxLIs, txnId, MVCConstants.TXN_RETURN_PARAM);

		List<TenderLineItem> tenderLIs = TransactionTransformer.transformTenderLineItems(saleTxn, saleTxn.getTxnTenderLineItems(), txnId);
		txnDTO.setTenderLineItems(tenderLIs);

		if (saleTxn.getCustomer() != null && StringUtils.isNotBlank(saleTxn.getCustomer().getName())) {
			TransactionCustomer txnCustomer = TransactionTransformer.transformCustomerBean(saleTxn.getCustomer(), txnId);
			txnDTO.setTxnCustomer(txnCustomer);
			if (txnCustomer.getTransactionCustomerId().getCustomerId() == null) {
				Customer customer = TransactionTransformer.transformCustomerBeanToCustomer(saleTxn.getCustomer(), username);
				txnDTO.setCustomer(customer);
			}
		}
		
		return txnDTO;
	}

	public static Transaction transformTransactionDetails(TransactionHeader txnHeader, String txnType) {

		Transaction txn = new Transaction();

		TransactionId txnId = new TransactionId();
		txnId.setLocationId(txnHeader.getLocationId());
		txnId.setRegister(txnHeader.getRegisterId());
		txnId.setBusinessDate(txnHeader.getBusinessDate());
		txnId.setTransactionSeq(txnHeader.getTxnNo());

		txn.setTransactionId(txnId);

		txn.setCancelReason(txnHeader.getCancelReason());
		txn.setCreatedBy(txnHeader.getCreatedBy());
		txn.setCreatedDate(LocalDateTime.now());
		txn.setStartTime(txnHeader.getStartTime());
		txn.setEndTime(txnHeader.getEndTime());
		txn.setModifiedBy(txnHeader.getModifiedBy());
		txn.setModifiedDate(txnHeader.getModifiedDate());

		txn.setOfflineFlag(txnHeader.getOfflineFlag());
		txn.setPostVoidFlag(txnHeader.getPostVoidFlag());
		txn.setSessionId(txnHeader.getSessionId());
		txn.setStatus(ServiceConstants.TXN_STATUS_COMPLETED);
		txn.setSubTotalAmt(txnHeader.getSubTotalAmt());
		txn.setDiscountTotalAmt(txnHeader.getTotalDiscountAmt());

		txn.setTaxTotalAmt(txnHeader.getTotalCGSTTaxAmt().add(txnHeader.getTotalSGSTTaxAmt()));
		if(MVCConstants.TXN_RETURN_PARAM.equals(txnType)) {
			txn.setTotalAmt(txn.getSubTotalAmt().add(txn.getTaxTotalAmt()).add(txn.getDiscountTotalAmt()));
		}else {
			txn.setTotalAmt(txn.getSubTotalAmt().add(txn.getTaxTotalAmt()).subtract(txn.getDiscountTotalAmt()));
		}
		

		txn.setTxnType(txnType);
		logger.info("The transaction header details has been transformed now");
		return txn;
	}

	public static List<TransactionLineItem> transformTxnLineItems(SaleTransaction saleTxn, TransactionId txnId) {

		List<TransactionLineItem> txnLIs = new ArrayList<>();
		TransactionLineItem txnLI;

		List<com.punj.app.ecommerce.models.transaction.TaxLineItem> taxLineItems;
		List<TransactionLineItem> taxTxnLIs;
		List<TransactionLineItem> tenderTxnLIs;

		List<com.punj.app.ecommerce.models.transaction.SaleLineItem> saleLineItems = saleTxn.getTxnSaleLineItems();
		for (com.punj.app.ecommerce.models.transaction.SaleLineItem saleLineItem : saleLineItems) {
			txnLI = new TransactionLineItem();
			TransactionLineItemId txnLIId = new TransactionLineItemId();
			txnLIId.setLocationId(txnId.getLocationId());
			txnLIId.setRegister(txnId.getRegister());
			txnLIId.setTransactionSeq(txnId.getTransactionSeq());
			txnLIId.setBusinessDate(txnId.getBusinessDate());
			txnLIId.setTransactionSeq(saleLineItem.getSeqNo());
			txnLIId.setLineItemSeq(saleLineItem.getSeqNo().toString());

			txnLI.setTransactionLineItemId(txnLIId);

			txnLI.setCreatedBy(saleTxn.getTransactionHeader().getCreatedBy());
			txnLI.setCreatedDate(LocalDateTime.now());
			txnLI.setEndTime(saleTxn.getTransactionHeader().getEndTime());
			txnLI.setStartTime(saleTxn.getTransactionHeader().getStartTime());
			txnLI.setLineItemType(MVCConstants.SALE_ITEM_PARAM);
			txnLI.setStatus(MVCConstants.COMPLETED);
			txnLI.setVoidFlag(Boolean.FALSE);
			txnLI.setVoidReason(null);

			txnLIs.add(txnLI);

			taxLineItems = saleLineItem.getTaxLineItems();
			taxTxnLIs = TransactionTransformer.transformTxnTaxItems(saleTxn, taxLineItems, txnId);
			txnLIs.addAll(taxTxnLIs);
		}
		tenderTxnLIs = TransactionTransformer.transformTxnTenderItems(saleTxn, saleTxn.getTxnTenderLineItems(), txnId);
		txnLIs.addAll(tenderTxnLIs);
		logger.info("The transaction line item details has been transformed now");
		return txnLIs;
	}

	public static List<TransactionLineItem> transformTxnTaxItems(SaleTransaction saleTxn,
			List<com.punj.app.ecommerce.models.transaction.TaxLineItem> taxLineItems, TransactionId txnId) {
		List<TransactionLineItem> txnLIs = new ArrayList<>();
		TransactionLineItem txnLI;
		for (com.punj.app.ecommerce.models.transaction.TaxLineItem taxLineItem : taxLineItems) {
			txnLI = new TransactionLineItem();
			TransactionLineItemId txnLIId = new TransactionLineItemId();
			txnLIId.setLocationId(txnId.getLocationId());
			txnLIId.setRegister(txnId.getRegister());
			txnLIId.setTransactionSeq(txnId.getTransactionSeq());
			txnLIId.setBusinessDate(txnId.getBusinessDate());
			txnLIId.setTransactionSeq(taxLineItem.getSeqNo());
			txnLIId.setLineItemSeq(taxLineItem.getSeqNo().toString());

			txnLI.setTransactionLineItemId(txnLIId);

			txnLI.setCreatedBy(saleTxn.getTransactionHeader().getCreatedBy());
			txnLI.setCreatedDate(LocalDateTime.now());
			txnLI.setEndTime(saleTxn.getTransactionHeader().getEndTime());
			txnLI.setStartTime(saleTxn.getTransactionHeader().getStartTime());
			txnLI.setLineItemType(MVCConstants.TAX_ITEM_PARAM);
			txnLI.setStatus(MVCConstants.COMPLETED);
			txnLI.setVoidFlag(Boolean.FALSE);
			txnLI.setVoidReason(null);

			txnLIs.add(txnLI);
		}
		logger.info("The transaction line item details for tax items has been transformed now");
		return txnLIs;
	}

	public static List<TransactionLineItem> transformTxnTenderItems(SaleTransaction saleTxn,
			List<com.punj.app.ecommerce.models.transaction.TenderLineItem> tenderLineItems, TransactionId txnId) {
		List<TransactionLineItem> txnLIs = new ArrayList<>();
		TransactionLineItem txnLI;
		for (com.punj.app.ecommerce.models.transaction.TenderLineItem tenderLineItem : tenderLineItems) {
			txnLI = new TransactionLineItem();
			TransactionLineItemId txnLIId = new TransactionLineItemId();
			txnLIId.setLocationId(txnId.getLocationId());
			txnLIId.setRegister(txnId.getRegister());
			txnLIId.setTransactionSeq(txnId.getTransactionSeq());
			txnLIId.setBusinessDate(txnId.getBusinessDate());
			txnLIId.setTransactionSeq(tenderLineItem.getSeqNo());
			txnLIId.setLineItemSeq(tenderLineItem.getSeqNo().toString());

			txnLI.setTransactionLineItemId(txnLIId);

			txnLI.setCreatedBy(saleTxn.getTransactionHeader().getCreatedBy());
			txnLI.setCreatedDate(LocalDateTime.now());
			txnLI.setEndTime(saleTxn.getTransactionHeader().getEndTime());
			txnLI.setStartTime(saleTxn.getTransactionHeader().getStartTime());
			txnLI.setLineItemType(MVCConstants.TENDER_ITEM_PARAM);
			txnLI.setStatus(MVCConstants.COMPLETED);
			txnLI.setVoidFlag(Boolean.FALSE);
			txnLI.setVoidReason(null);

			txnLIs.add(txnLI);
		}
		logger.info("The transaction line item details for tender items has been transformed now");
		return txnLIs;
	}

	public static SaleLineItem transformSaleLineItem(SaleTransaction saleTxn, com.punj.app.ecommerce.models.transaction.SaleLineItem saleLineItem,
			TransactionId txnId, String txnType) {

		SaleLineItem saleLI = new SaleLineItem();

		SaleLineItemId saleLIId = new SaleLineItemId();
		saleLIId.setLocationId(txnId.getLocationId());
		saleLIId.setRegister(txnId.getRegister());
		saleLIId.setTransactionSeq(txnId.getTransactionSeq());
		saleLIId.setBusinessDate(txnId.getBusinessDate());
		saleLIId.setTransactionSeq(saleLineItem.getSeqNo());
		saleLIId.setItemId(saleLineItem.getItemId());
		saleLIId.setLineItemSeq(saleLineItem.getSeqNo().toString());

		saleLI.setSaleLineItemId(saleLIId);

		

		saleLI.setUpc(saleLineItem.getItemId().toString());

		saleLI.setQty(saleLineItem.getQty());
		// Change this to multiply with pack item later on
		saleLI.setGrossQty(saleLineItem.getQty());
		saleLI.setDiscountAmt(saleLineItem.getDiscount());
		saleLI.setDiscountPct(saleLineItem.getDiscountPct());
		saleLI.setUnitPrice(saleLineItem.getUnitPrice());
		saleLI.setBaseUnitPrice(saleLI.getUnitPrice());
		saleLI.setSuggestedPrice(saleLineItem.getSuggestedPrice());
		saleLI.setMaxRetailPrice(saleLineItem.getMaxRetailPrice());

		if (saleLineItem.getIgstTax() != null) {
			saleLI.setTaxAmt(saleLineItem.getIgstTax());
		} else {
			saleLI.setTaxAmt(saleLineItem.getSgstTax().add(saleLineItem.getCgstTax()));
		}

		
		// All the amounts are recalculated
		saleLI.setBaseExtendedAmt(saleLI.getUnitPrice().multiply(saleLI.getQty()));
		
		/**
		 * The inventory should be subtracted for all the sale line items
		 */
		if(MVCConstants.TXN_RETURN_PARAM.equals(txnType)) {
			
			saleLI.setTxnType(MVCConstants.TXN_RETURN_PARAM);
			saleLI.setInvAction(MVCConstants.ADD);
			
			saleLI.setExtendedAmt(saleLI.getBaseExtendedAmt().add(saleLI.getDiscountAmt()));
		
			
		}else if(MVCConstants.TXN_SALE_PARAM.equals(txnType)) {
			
			saleLI.setTxnType(MVCConstants.TXN_SALE_PARAM);
			saleLI.setInvAction(MVCConstants.SUBTRACT);
			
			saleLI.setExtendedAmt(saleLI.getBaseExtendedAmt().subtract(saleLI.getDiscountAmt()));
			
		}
		
		saleLI.setNetAmt(saleLI.getExtendedAmt());
		saleLI.setGrossAmt(saleLI.getExtendedAmt().add(saleLI.getTaxAmt()));

		saleLI.setEntryMethod(MVCConstants.MANUAL);
		saleLI.setExcludeFlag(Boolean.FALSE);
		saleLI.setReceiptCount(1);

		saleLI.setReturnComment(null);
		saleLI.setReturnedQty(null);
		saleLI.setReturnFlag(Boolean.FALSE);
		saleLI.setReturnReason(null);
		saleLI.setReturnType(null);

		saleLI.setNewDescription(null);
		saleLI.setOrgLocationId(null);
		saleLI.setOrgBusinessDate(null);
		saleLI.setOrgRegister(null);
		saleLI.setOrgTransactionSeq(null);

		saleLI.setCreatedBy(saleTxn.getTransactionHeader().getCreatedBy());
		saleLI.setCreatedDate(LocalDateTime.now());

		logger.info("The Sale Line Item has been transformed now");
		return saleLI;
	}

	public static TransactionDTO transformSaleLineItems(TransactionDTO txnDTO, SaleTransaction saleTxn, List<TaxLineItem> taxLIs, TransactionId txnId, String txnType) {

		List<com.punj.app.ecommerce.models.transaction.SaleLineItem> saleLineItems = saleTxn.getTxnSaleLineItems();
		List<SaleLineItem> saleLIs = new ArrayList<>(saleLineItems.size());
		SaleLineItem saleLI;

		taxLIs = new ArrayList<>();
		List<TaxLineItem> tmpTaxLIs;

		for (com.punj.app.ecommerce.models.transaction.SaleLineItem saleLineItem : saleLineItems) {
			saleLI = TransactionTransformer.transformSaleLineItem(saleTxn, saleLineItem, txnId, txnType);
			saleLIs.add(saleLI);

			tmpTaxLIs = TransactionTransformer.transformTaxLineItems(saleTxn, saleLI, saleLineItem.getTaxLineItems(), txnId);
			taxLIs.addAll(tmpTaxLIs);
		}
		txnDTO.setSaleLineItems(saleLIs);
		txnDTO.setTaxLineItems(taxLIs);

		logger.info("All the Sale Line Items has been transformed now");
		return txnDTO;
	}

	public static TaxLineItem transformTaxLineItem(SaleTransaction saleTxn, SaleLineItem saleLI,
			com.punj.app.ecommerce.models.transaction.TaxLineItem taxLineItem, TransactionId txnId) {

		TaxLineItem taxLI = new TaxLineItem();

		TransactionLineItemId taxLIId = new TransactionLineItemId();
		taxLIId.setLocationId(txnId.getLocationId());
		taxLIId.setRegister(txnId.getRegister());
		taxLIId.setTransactionSeq(txnId.getTransactionSeq());
		taxLIId.setBusinessDate(txnId.getBusinessDate());
		taxLIId.setTransactionSeq(taxLineItem.getSeqNo());
		taxLIId.setLineItemSeq(taxLineItem.getSeqNo().toString());

		taxLI.setTransactionLineItemId(taxLIId);

		taxLI.setItemId(taxLineItem.getItemId());
		taxLI.setCreatedBy(saleTxn.getTransactionHeader().getCreatedBy());
		taxLI.setCreatedDate(LocalDateTime.now());
		taxLI.setOrgTaxableAmt(taxLineItem.getOrgTaxableAmt());
		taxLI.setOrgTaxGroupId(taxLineItem.getOrgTaxGroupId());
		taxLI.setTaxGroupId(taxLineItem.getTaxGroupId());
		taxLI.setTaxOverrideAmt(taxLineItem.getTaxOverrideAmt());
		taxLI.setTaxOverrideFlag(taxLineItem.getTaxOverrideFlag());
		taxLI.setTaxOverridePercentage(taxLineItem.getTaxOverrideRate());

		taxLI.setTaxRuleRateId(taxLineItem.getTaxRuleRateId());

		taxLI.setTotalTaxableAmt(saleLI.getNetAmt());
		if (taxLineItem.getTaxRuleRate() != null) {
			taxLI.setTaxRulePercentage(taxLineItem.getTaxRuleRate());
			taxLI.setTotalTaxAmt(taxLI.getTotalTaxableAmt().multiply(taxLI.getTaxRulePercentage()).divide(new BigDecimal(100)));
		} else {
			taxLI.setTaxRuleAmt(taxLineItem.getTaxRuleAmt());
			taxLI.setTotalTaxAmt(taxLI.getTotalTaxableAmt().subtract(taxLI.getTaxRuleAmt().multiply(saleLI.getQty())));
		}

		taxLI.setTotalTaxExemptAmt(taxLineItem.getTotalTaxExemptAmt());

		taxLI.setVoidFlag(taxLineItem.getVoidFlag());

		logger.info("The Tax Line Item has been transformed successfully");
		return taxLI;

	}

	public static List<TaxLineItem> transformTaxLineItems(SaleTransaction saleTxn, SaleLineItem saleLI,
			List<com.punj.app.ecommerce.models.transaction.TaxLineItem> taxLineItems, TransactionId txnId) {

		List<TaxLineItem> taxLIs = new ArrayList<>(taxLineItems.size());
		TaxLineItem taxLI;

		for (com.punj.app.ecommerce.models.transaction.TaxLineItem taxLineItem : taxLineItems) {
			taxLI = TransactionTransformer.transformTaxLineItem(saleTxn, saleLI, taxLineItem, txnId);
			taxLIs.add(taxLI);
		}

		logger.info("The Tax Line Items has been transformed now");
		return taxLIs;
	}

	public static TenderLineItem transformTenderLineItem(SaleTransaction saleTxn, com.punj.app.ecommerce.models.transaction.TenderLineItem tenderLineItem,
			TransactionId txnId) {

		TenderLineItem tndrLI = new TenderLineItem();

		TransactionLineItemId txnLineItemId = new TransactionLineItemId();
		txnLineItemId.setLocationId(txnId.getLocationId());
		txnLineItemId.setRegister(txnId.getRegister());
		txnLineItemId.setTransactionSeq(txnId.getTransactionSeq());
		txnLineItemId.setBusinessDate(txnId.getBusinessDate());
		txnLineItemId.setLineItemSeq(tenderLineItem.getSeqNo().toString());
		tndrLI.setTransactionLineItemId(txnLineItemId);

		tndrLI.setTenderId(tenderLineItem.getTenderId());
		tndrLI.setAction(tenderLineItem.getActionCode());
		tndrLI.setAmount(tenderLineItem.getAmount());
		tndrLI.setType(tenderLineItem.getTypeCode());
		tndrLI.setCreatedBy(saleTxn.getTransactionHeader().getCreatedBy());
		tndrLI.setCreatedDate(LocalDateTime.now());
		tndrLI.setExchangeRate(tenderLineItem.getExchangeRate());
		tndrLI.setForeignAmount(tenderLineItem.getForeignAmount());
		tndrLI.setChangeFlag(tenderLineItem.getChangeFlag());
		logger.info("The tender line item has been transformed now");
		return tndrLI;
	}

	public static List<TenderLineItem> transformTenderLineItems(SaleTransaction saleTxn,
			List<com.punj.app.ecommerce.models.transaction.TenderLineItem> tenderLineItems, TransactionId txnId) {
		List<TenderLineItem> tenderLIs = new ArrayList<>(tenderLineItems.size());
		TenderLineItem tenderLI;
		for (com.punj.app.ecommerce.models.transaction.TenderLineItem tenderLineItem : tenderLineItems) {
			tenderLI = TransactionTransformer.transformTenderLineItem(saleTxn, tenderLineItem, txnId);
			tenderLIs.add(tenderLI);
		}
		logger.info("All the tender line items has been transformed now");
		return tenderLIs;
	}

	public static TransactionCustomer transformCustomerBean(CustomerBean customerBean, TransactionId txnId) {
		TransactionCustomer txnCustomer = new TransactionCustomer();

		TransactionCustomerId txnCustomerId = new TransactionCustomerId();

		txnCustomerId.setCustomerId(customerBean.getCustomerId());
		txnCustomerId.setCustomerType(customerBean.getCustomerType());

		txnCustomerId.setBusinessDate(txnId.getBusinessDate());
		txnCustomerId.setLocationId(txnId.getLocationId());
		txnCustomerId.setRegister(txnId.getRegister());
		txnCustomerId.setTransactionSeq(txnId.getTransactionSeq());

		txnCustomer.setTransactionCustomerId(txnCustomerId);

		logger.info("The customer details has been transformed");
		return txnCustomer;
	}

	public static Customer transformCustomerBeanToCustomer(CustomerBean customerBean, String username) {
		Customer customer = new Customer();

		customer.setCustomerId(customerBean.getCustomerId());
		customer.setCreatedBy(username);
		customer.setCreatedDate(LocalDateTime.now());
		customer.setEmail(customerBean.getEmail());
		customer.setName(customerBean.getName());
		customer.setPhone(customerBean.getPhone());

		logger.info("The customer details from bean has been transformed to customer object");
		return customer;
	}

	public static SaleTransactionReceipt transformReceiptDetails(SaleTransactionReceiptDTO receiptDetails, String username) {

		SaleTransactionReceipt txnReceipt = new SaleTransactionReceipt();

		TransactionHeader txnHeader = TransactionTransformer.transformTxnDetails(receiptDetails.getTxn());
		txnHeader.setPrintedBy(username);
		List<SaleReceiptLineItem> receiptItemList = TransactionTransformer.transformTxnItemDetails(receiptDetails.getTxnLineItems(), txnHeader);

		if(receiptDetails.getTxnCustomer()!=null){
			CustomerBean customerBean=TransactionTransformer.tranformTransactionCustomer(receiptDetails.getTxnCustomer(),receiptDetails.getCustomerDetails(),receiptDetails.getSupplierDetails());
			txnReceipt.setCustomer(customerBean);
		}


		txnReceipt.setTransactionHeader(txnHeader);
		txnReceipt.setTxnSaleLineItems(receiptItemList);

		LocationBean locationBean = CommonMVCTransformer.transformLocationDomainPartially(receiptDetails.getLocation(), Boolean.FALSE);
		txnReceipt.setLocationDetails(locationBean);



		logger.info("All the tender line items has been transformed now");
		return txnReceipt;
	}

	public static TransactionHeader transformTxnDetails(Transaction txnDetails) {
		TransactionHeader txnHeader = new TransactionHeader();
		txnHeader.setLocationId(txnDetails.getTransactionId().getLocationId());
		txnHeader.setRegisterId(txnDetails.getTransactionId().getRegister());
		txnHeader.setTxnType(txnDetails.getTxnType());
		txnHeader.setBusinessDate(txnDetails.getTransactionId().getBusinessDate());
		txnHeader.setTxnNo(txnDetails.getTransactionId().getTransactionSeq());

		txnHeader.setCreatedBy(txnDetails.getCreatedBy());
		txnHeader.setSubTotalAmt(txnDetails.getSubTotalAmt());
		txnHeader.setTotalDiscountAmt(txnDetails.getDiscountTotalAmt());
		txnHeader.setTotalTaxAmt(txnDetails.getTaxTotalAmt());

		txnHeader.setTotalDueAmt(txnDetails.getTotalAmt());
		txnHeader.setTotalDueAmtWords(NumberToWordConverter.convertBigDecimalToWords(txnHeader.getTotalDueAmt()));

		txnHeader.setUniqueTxnNo(txnDetails.getTransactionId().toString());

		logger.info("The receipt transaction header details has been transformed now");
		return txnHeader;

	}

	public static List<SaleReceiptLineItem> transformTxnItemDetails(List<ReceiptItemTax> receiptItemTaxList, TransactionHeader txnHeader) {

		List<SaleReceiptLineItem> receiptItemList = new ArrayList<>(receiptItemTaxList.size());
		SaleReceiptLineItem receiptItem;
		int seqCount = 1;

		BigDecimal sgstTaxTotal = new BigDecimal("0");
		BigDecimal cgstTaxTotal = new BigDecimal("0");
		BigDecimal igstTaxTotal = new BigDecimal("0");

		for (ReceiptItemTax receiptItemTax : receiptItemTaxList) {
			receiptItem = new SaleReceiptLineItem();
			receiptItem.setCgstTaxAmount(receiptItemTax.getCgstAmount());
			receiptItem.setCgstTaxRate(receiptItemTax.getCgstRate());
			receiptItem.setSgstTaxAmount(receiptItemTax.getSgstAmount());
			receiptItem.setSgstTaxRate(receiptItemTax.getCgstRate());
			receiptItem.setIgstTaxAmount(receiptItemTax.getIgstAmount());
			receiptItem.setIgstTaxRate(receiptItemTax.getIgstRate());

			if (receiptItemTax.getSgstAmount() != null)
				sgstTaxTotal = sgstTaxTotal.add(receiptItemTax.getSgstAmount());
			if (receiptItemTax.getCgstAmount() != null)
				cgstTaxTotal = cgstTaxTotal.add(receiptItemTax.getCgstAmount());
			if (receiptItemTax.getIgstAmount() != null)
				igstTaxTotal = igstTaxTotal.add(receiptItemTax.getIgstAmount());

			receiptItem.setDiscount(receiptItemTax.getDiscountAmount());
			receiptItem.setExtendedAmount(receiptItemTax.getExtendedAmount());
			receiptItem.setGrossAmount(receiptItemTax.getGrossAmount());
			receiptItem.setGrossQty(receiptItemTax.getQty());
			receiptItem.setHsnNo(receiptItemTax.getHsnNo());
			receiptItem.setItemId(receiptItemTax.getSaleLineItemId().getItemId());
			receiptItem.setItemName(receiptItemTax.getName());
			receiptItem.setItemTotal(receiptItemTax.getGrossAmount());
			receiptItem.setLongDesc(receiptItemTax.getLongDesc());
			receiptItem.setNetAmount(receiptItemTax.getGrossAmount());
			receiptItem.setQty(receiptItemTax.getQty());
			receiptItem.setSeqNo(seqCount);
			seqCount++;
			receiptItem.setTaxAmount(receiptItemTax.getTaxAmount());
			receiptItem.setUnitPrice(receiptItemTax.getUnitPrice());
			receiptItem.setUpcNo(receiptItemTax.getUpcNo());

			receiptItemList.add(receiptItem);
		}

		txnHeader.setTotalSGSTTaxAmt(sgstTaxTotal);
		txnHeader.setTotalCGSTTaxAmt(cgstTaxTotal);
		txnHeader.setTotalIGSTTaxAmt(igstTaxTotal);

		logger.info("The receipt transaction header details has been transformed now");
		return receiptItemList;

	}

	public static TransactionReceipt tranformReceiptDetails(TransactionId txnId, String username, String receiptType, byte[] pdfBytes) {
		TransactionReceipt txnReceipt = new TransactionReceipt();

		TransactionReceiptId txnReceiptId = new TransactionReceiptId();
		txnReceiptId.setBusinessDate(txnId.getBusinessDate());
		txnReceiptId.setLocationId(txnId.getLocationId());
		txnReceiptId.setTransactionSeq(txnId.getTransactionSeq());
		txnReceiptId.setRegister(txnId.getRegister());
		txnReceiptId.setReceiptType(receiptType);

		txnReceipt.setTransactionReceiptId(txnReceiptId);
		txnReceipt.setCreatedBy(username);
		txnReceipt.setCreatedDate(LocalDateTime.now());

		/*
		 * This section will convert the PDF file in byte array which can be saved into Entity object
		 */
		txnReceipt.setReceiptData(pdfBytes);

		logger.info("The {} typeReceipt has been transformed successfully");

		return txnReceipt;
	}

	public static List<TransactionReceipt> getReceipts(byte[] pdfBytes, TransactionId txnId, String username) {
		List<TransactionReceipt> txnReceipts = new ArrayList<>();
		TransactionReceipt txnReceipt = TransactionTransformer.tranformReceiptDetails(txnId, username, MVCConstants.RCPT_SALE_STORE, pdfBytes);
		txnReceipts.add(txnReceipt);
		logger.info("The transaction receipts has been transformed for saving in DB");

		return txnReceipts;
	}

	public static CustomerBean tranformTransactionCustomer(TransactionCustomer txnCustomer, Customer customer, Supplier supplier) {
		CustomerBean customerBean=new CustomerBean();
		customerBean.setCustomerId(txnCustomer.getTransactionCustomerId().getCustomerId());
		customerBean.setCustomerType(txnCustomer.getTransactionCustomerId().getCustomerType());

		if(customer!=null){
			customerBean.setEmail(customer.getEmail());
			customerBean.setName(customer.getName());
			customerBean.setPhone(customer.getPhone());
		} else if(supplier!=null){
			customerBean.setEmail(supplier.getEmail());
			customerBean.setName(supplier.getName());
			customerBean.setPhone(supplier.getPhone1());
		}

		logger.info("The customer details has been transformed successfully");
		return customerBean;
	}

}
