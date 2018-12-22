/**
 * 
 */
package com.punj.app.ecommerce.controller.common.transformer;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.domains.customer.Customer;
import com.punj.app.ecommerce.domains.item.ItemImage;
import com.punj.app.ecommerce.domains.supplier.Supplier;
import com.punj.app.ecommerce.domains.tender.Tender;
import com.punj.app.ecommerce.domains.transaction.ReceiptItemTax;
import com.punj.app.ecommerce.domains.transaction.SaleLineItem;
import com.punj.app.ecommerce.domains.transaction.TaxLineItem;
import com.punj.app.ecommerce.domains.transaction.TenderLineItem;
import com.punj.app.ecommerce.domains.transaction.Transaction;
import com.punj.app.ecommerce.domains.transaction.TransactionCustomer;
import com.punj.app.ecommerce.domains.transaction.TransactionLineItem;
import com.punj.app.ecommerce.domains.transaction.TransactionLookup;
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
import com.punj.app.ecommerce.models.transaction.TxnBean;
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

		if (StringUtils.isNotBlank(txnHeader.getApplicableTax()) && txnHeader.getApplicableTax().contentEquals("S"))
			txn.setTaxTotalAmt(txnHeader.getTotalCGSTTaxAmt().add(txnHeader.getTotalSGSTTaxAmt()));
		else if (StringUtils.isNotBlank(txnHeader.getApplicableTax()) && txnHeader.getApplicableTax().contentEquals("I"))
			txn.setTaxTotalAmt(txnHeader.getTotalIGSTTaxAmt());
		else if (StringUtils.isNotBlank(txnHeader.getApplicableTax()) && txnHeader.getApplicableTax().contentEquals("N"))
			txn.setTaxTotalAmt(BigDecimal.ZERO);
		if (MVCConstants.TXN_RETURN_PARAM.equals(txnType)) {
			BigDecimal totalAmountValue = txn.getSubTotalAmt().add(txn.getTaxTotalAmt()).add(txn.getDiscountTotalAmt());
			BigDecimal roundedTotalAmountValue = totalAmountValue.setScale(0, RoundingMode.HALF_UP);
			txn.setTotalAmt(roundedTotalAmountValue);
			txn.setRoundTotalAmt(roundedTotalAmountValue.subtract(totalAmountValue));
		} else {
			BigDecimal totalAmountValue = txn.getSubTotalAmt().add(txn.getTaxTotalAmt()).subtract(txn.getDiscountTotalAmt());
			BigDecimal roundedTotalAmountValue = totalAmountValue.setScale(0, RoundingMode.HALF_UP);
			txn.setTotalAmt(roundedTotalAmountValue);
			txn.setRoundTotalAmt(roundedTotalAmountValue.subtract(totalAmountValue));
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

	public static List<TransactionLineItem> transformTxnTaxItems(SaleTransaction saleTxn, List<com.punj.app.ecommerce.models.transaction.TaxLineItem> taxLineItems,
			TransactionId txnId) {
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

	public static List<TransactionLineItem> transformTxnTenderItems(SaleTransaction saleTxn, List<com.punj.app.ecommerce.models.transaction.TenderLineItem> tenderLineItems,
			TransactionId txnId) {
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

	public static SaleLineItem transformSaleLineItem(SaleTransaction saleTxn, com.punj.app.ecommerce.models.transaction.SaleLineItem saleLineItem, TransactionId txnId,
			String txnType) {

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

		saleLI.setItemDesc(saleLineItem.getItemName());
		saleLI.setHsnNo(saleLineItem.getHsnNo());
		
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
		if (MVCConstants.TXN_RETURN_PARAM.equals(txnType)) {

			saleLI.setTxnType(MVCConstants.TXN_RETURN_PARAM);
			saleLI.setInvAction(MVCConstants.ADD);

			saleLI.setExtendedAmt(saleLI.getBaseExtendedAmt().add(saleLI.getDiscountAmt()));

		} else if (MVCConstants.TXN_SALE_PARAM.equals(txnType)) {

			saleLI.setTxnType(MVCConstants.TXN_SALE_PARAM);
			saleLI.setInvAction(MVCConstants.SUBTRACT);

			saleLI.setExtendedAmt(saleLI.getBaseExtendedAmt().subtract(saleLI.getDiscountAmt()));

		}

		saleLI.setNetAmt(saleLI.getExtendedAmt());
		
		BigDecimal itemGrossAmount=saleLI.getExtendedAmt().add(saleLI.getTaxAmt());
		BigDecimal roundedGrossAmountValue = itemGrossAmount.setScale(0, RoundingMode.HALF_UP);
		saleLI.setGrossAmt(roundedGrossAmountValue);

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

	public static TaxLineItem transformTaxLineItem(SaleTransaction saleTxn, SaleLineItem saleLI, com.punj.app.ecommerce.models.transaction.TaxLineItem taxLineItem,
			TransactionId txnId) {

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

		taxLI.setTaxCode(taxLineItem.getTaxCode());
		
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

	public static List<TaxLineItem> transformTaxLineItems(SaleTransaction saleTxn, SaleLineItem saleLI, List<com.punj.app.ecommerce.models.transaction.TaxLineItem> taxLineItems,
			TransactionId txnId) {

		List<TaxLineItem> taxLIs = new ArrayList<>(taxLineItems.size());
		TaxLineItem taxLI;

		for (com.punj.app.ecommerce.models.transaction.TaxLineItem taxLineItem : taxLineItems) {
			taxLI = TransactionTransformer.transformTaxLineItem(saleTxn, saleLI, taxLineItem, txnId);
			taxLIs.add(taxLI);
		}

		logger.info("The Tax Line Items has been transformed now");
		return taxLIs;
	}

	public static TenderLineItem transformTenderLineItem(SaleTransaction saleTxn, com.punj.app.ecommerce.models.transaction.TenderLineItem tenderLineItem, TransactionId txnId) {

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

	public static List<TenderLineItem> transformTenderLineItems(SaleTransaction saleTxn, List<com.punj.app.ecommerce.models.transaction.TenderLineItem> tenderLineItems,
			TransactionId txnId) {
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
		if (StringUtils.isNotBlank(customerBean.getGstNo()) && customerBean.getGstNo().length() == 15)
			customerBean.setStateCode(customerBean.getGstNo().substring(0, 2));

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
		
		CustomerBean customerBean = null;
		if (receiptDetails.getTxnCustomer() != null) {
			customerBean = TransactionTransformer.tranformTransactionCustomer(receiptDetails.getTxnCustomer(), receiptDetails.getCustomerDetails(),
					receiptDetails.getSupplierDetails());
			txnReceipt.setCustomer(customerBean);
		}
		
		LocationBean locationBean = CommonMVCTransformer.transformLocationDomainPartially(receiptDetails.getLocation(), Boolean.FALSE);
		txnReceipt.setLocationDetails(locationBean);
		
		String taxType = null;
		if (customerBean != null) {
			taxType = TransactionTransformer.getTaxType(locationBean, customerBean);
		} else {
			taxType = "S";
		}
		txnReceipt.setApplicableTax(taxType);
		
		List<SaleReceiptLineItem> receiptItemList = TransactionTransformer.transformTxnItemDetails(receiptDetails.getTxnLineItems(), txnHeader, taxType);

		txnReceipt.setTransactionHeader(txnHeader);
		txnReceipt.setTxnSaleLineItems(receiptItemList);

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

	public static List<SaleReceiptLineItem> transformTxnItemDetails(List<ReceiptItemTax> receiptItemTaxList, TransactionHeader txnHeader, String applicableTax) {

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
			receiptItem.setApplicableTax(applicableTax);
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

	public static TransactionReceipt tranformReceiptDetails(TransactionId txnId, String username, String receiptType, byte[] pdfBytes, Boolean isExisting) {
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
		if(isExisting) {
			txnReceipt.setModifiedBy(username);
			txnReceipt.setModifiedDate(LocalDateTime.now());
		}
		/*
		 * This section will convert the PDF file in byte array which can be saved into Entity object
		 */
		txnReceipt.setReceiptData(pdfBytes);

		logger.info("The {} typeReceipt has been transformed successfully");

		return txnReceipt;
	}
	
	public static List<TransactionReceipt> getReturnReceipts(byte[] pdfBytes, TransactionId txnId, String username, Boolean isExisting) {
		List<TransactionReceipt> txnReceipts = new ArrayList<>();
		TransactionReceipt txnReceipt = TransactionTransformer.tranformReceiptDetails(txnId, username, MVCConstants.RCPT_SALE_STORE, pdfBytes, isExisting);
		txnReceipts.add(txnReceipt);
		logger.info("The transaction receipts has been transformed for saving in DB");

		return txnReceipts;
	}

	public static List<TransactionReceipt> getReceipts(byte[] pdfBytes, TransactionId txnId, String username, Boolean isOriginal, Boolean isExisting) {
		
		List<TransactionReceipt> txnReceipts = new ArrayList<>();
		String receiptType = null;
		if (isOriginal)
			receiptType = MVCConstants.RCPT_SALE_STORE;
		else
			receiptType = MVCConstants.RCPT_SALE_DUPLICATE;
		TransactionReceipt txnReceipt = TransactionTransformer.tranformReceiptDetails(txnId, username, receiptType, pdfBytes, isExisting);
		txnReceipts.add(txnReceipt);
		logger.info("The transaction receipts has been transformed for saving in DB");

		return txnReceipts;
	}

	public static CustomerBean tranformTransactionCustomer(TransactionCustomer txnCustomer, Customer customer, Supplier supplier) {
		CustomerBean customerBean = new CustomerBean();
		customerBean.setCustomerId(txnCustomer.getTransactionCustomerId().getCustomerId());
		customerBean.setCustomerType(txnCustomer.getTransactionCustomerId().getCustomerType());

		if (customer != null) {
			customerBean.setEmail(customer.getEmail());
			customerBean.setName(customer.getName());
			customerBean.setEmail(customer.getEmail());
			customerBean.setPhone(customer.getPhone());
			customerBean.setPhone2(customer.getPhone2());
			customerBean.setGstNo(customer.getGstNo());
			customerBean.setPanNo(customer.getPanNo());
			if (StringUtils.isNotBlank(customer.getGstNo()) && customer.getGstNo().length() == 15)
				customerBean.setStateCode(customer.getGstNo().substring(0, 2));
		} else if (supplier != null) {
			customerBean.setEmail(supplier.getEmail());
			customerBean.setName(supplier.getName());
			customerBean.setPhone(supplier.getPhone1());
		}

		logger.info("The customer details has been transformed successfully");
		return customerBean;
	}

	public static String getTaxType(LocationBean locationBean, CustomerBean customerBean) {
		String resultTax = "S";

		String storeStateCode = locationBean.getStateCode();
		String billingStateCode = customerBean.getStateCode();

		if (storeStateCode != null && billingStateCode != null) {
			if (storeStateCode.equals(billingStateCode))
				resultTax = "S";
			else
				resultTax = "I";
		} else if (storeStateCode != null && billingStateCode == null) {
			resultTax = "S";
		} else if (storeStateCode != null && billingStateCode == null) {
			resultTax = "N";
		}

		return resultTax;
	}

	public static List<TxnBean> transformTxnLookupList(List<TransactionLookup> txnLookupDetails) {
		List<TxnBean> txnBeans = new ArrayList<>(txnLookupDetails.size());
		TxnBean txnBean = null;

		for (TransactionLookup txnLookupDetail : txnLookupDetails) {
			txnBean = TransactionTransformer.transformTxnLookup(txnLookupDetail);
			txnBeans.add(txnBean);
		}

		logger.info("The transaction lookup details list has been transformed successfully");

		return txnBeans;
	}

	public static TxnBean transformTxnLookup(TransactionLookup txnLookupDetail) {
		TxnBean txnBean = new TxnBean();
		txnBean.setUniqueTxnNo(txnLookupDetail.getUniqueTxnNo());
		txnBean.setCreatedBy(txnLookupDetail.getCreatedBy());
		txnBean.setCreatedDate(txnLookupDetail.getCreatedDate());
		txnBean.setCustomerName(txnLookupDetail.getCustomerName());
		txnBean.setTotalAmount(txnLookupDetail.getTotalAmt());
		txnBean.setTotalTaxAmount(txnLookupDetail.getTaxTotalAmt());
		txnBean.setTxnType(txnLookupDetail.getTxnType());
		
		txnBean.setReasonCode(txnLookupDetail.getReasonCode());
		txnBean.setReasonName(txnLookupDetail.getReasonName());
		txnBean.setReasonType(txnLookupDetail.getReasonType());

		logger.info("The transaction lookup details has been transformed successfully");
		return txnBean;
	}

	public static SaleTransaction transformSaleTxn(TransactionDTO txnDTO, Map<Integer, Tender> tenderMap, Map<BigInteger, List<ItemImage>> itemImagesMap) {
		SaleTransaction saleTxn = new SaleTransaction();

		if (txnDTO.getTxn() != null) {
			TransactionHeader txnHeader = TransactionTransformer.transformTxnHeader(txnDTO.getTxn());
			saleTxn.setTransactionHeader(txnHeader);
		}

		if (txnDTO.getSaleLineItems() != null && !txnDTO.getSaleLineItems().isEmpty()) {
			List<SaleLineItem> txnItems = txnDTO.getSaleLineItems();
			List<com.punj.app.ecommerce.models.transaction.SaleLineItem> saleLineItems = TransactionTransformer.transformTxnLineItems(txnItems, itemImagesMap);

			if (txnDTO.getTaxLineItems() != null && !txnDTO.getTaxLineItems().isEmpty()) {
				List<TaxLineItem> txnTaxItems = txnDTO.getTaxLineItems();
				List<com.punj.app.ecommerce.models.transaction.TaxLineItem> taxLineItems = TransactionTransformer.transformTxnTaxLineItems(txnTaxItems, saleLineItems);
				// Set these in line items one by one
			}
			saleTxn.setTxnSaleLineItems(saleLineItems);

		}

		if (txnDTO.getTenderLineItems() != null && !txnDTO.getTenderLineItems().isEmpty()) {
			List<TenderLineItem> txnTenderItems = txnDTO.getTenderLineItems();
			List<com.punj.app.ecommerce.models.transaction.TenderLineItem> tenderLineItems = TransactionTransformer.transformTxnTenders(txnTenderItems, tenderMap);
			saleTxn.setTxnTenderLineItems(tenderLineItems);

		}

		if (txnDTO.getCustomer() != null && txnDTO.getTxnCustomer() != null) {
			CustomerBean customerBean = TransactionTransformer.transformCustomer(txnDTO.getCustomer(), txnDTO.getTxnCustomer());
			saleTxn.setCustomer(customerBean);
		}

		return saleTxn;
	}

	public static TransactionHeader transformTxnHeader(Transaction txn) {
		TransactionHeader txnHead = new TransactionHeader();

		txnHead.setCancelReason(txn.getCancelReason());
		txnHead.setComments(txn.getComments());
		txnHead.setCreatedBy(txn.getCreatedBy());
		txnHead.setCreatedDate(txn.getCreatedDate());
		txnHead.setTotalDiscountAmt(txn.getDiscountTotalAmt());
		txnHead.setEndTime(txn.getEndTime());
		txnHead.setOfflineFlag(txn.getOfflineFlag());
		txnHead.setModifiedBy(txn.getModifiedBy());
		txnHead.setModifiedDate(txn.getModifiedDate());
		txnHead.setPostVoidFlag(txn.getPostVoidFlag());

		txnHead.setRoundedTotalAmt(txn.getRoundTotalAmt());
		txnHead.setSessionId(txn.getSessionId());
		txnHead.setStartTime(txn.getStartTime());
		txnHead.setStatus(txn.getStatus());
		txnHead.setSubTotalAmt(txn.getSubTotalAmt());
		txnHead.setBusinessDate(txn.getTransactionId().getBusinessDate());
		txnHead.setLocationId(txn.getTransactionId().getLocationId());
		txnHead.setRegisterId(txn.getTransactionId().getRegister());
		txnHead.setTxnNo(txn.getTransactionId().getTransactionSeq());
		txnHead.setTotalTaxAmt(txn.getTaxTotalAmt());
		txnHead.setTotalDueAmt(txn.getTotalAmt());
		txnHead.setTxnType(txn.getTxnType());

		txnHead.setUniqueTxnNo(txn.getTransactionId().toString());
		
		logger.info("The transaction header details has been transformed successfully");
		return txnHead;
	}

	public static Set<BigInteger> retrieveItemIds(List<SaleLineItem> txnItems) {
		Set<BigInteger> itemIds = new HashSet<>(txnItems.size());
		for (SaleLineItem txnItem : txnItems) {
			itemIds.add(txnItem.getSaleLineItemId().getItemId());
		}

		return itemIds;
	}

	public static List<com.punj.app.ecommerce.models.transaction.SaleLineItem> transformTxnLineItems(List<SaleLineItem> txnItems, Map<BigInteger, List<ItemImage>> itemImagesMap) {
		List<com.punj.app.ecommerce.models.transaction.SaleLineItem> saleLineItems = new ArrayList<>(txnItems.size());
		com.punj.app.ecommerce.models.transaction.SaleLineItem saleLineItem = null;

		for (SaleLineItem txnItem : txnItems) {
			saleLineItem = TransactionTransformer.transformTxnLineItem(txnItem, itemImagesMap);
			saleLineItems.add(saleLineItem);
		}
		logger.info("The transaction item details list has been transformed successfully");
		return saleLineItems;
	}

	public static com.punj.app.ecommerce.models.transaction.SaleLineItem transformTxnLineItem(SaleLineItem txnItem, Map<BigInteger, List<ItemImage>> itemImagesMap) {
		com.punj.app.ecommerce.models.transaction.SaleLineItem saleLineItem = new com.punj.app.ecommerce.models.transaction.SaleLineItem();

		saleLineItem.setBaseExtendedPrice(txnItem.getBaseExtendedAmt());
		saleLineItem.setBaseUnitPrice(txnItem.getBaseUnitPrice());
		saleLineItem.setCreatedBy(txnItem.getCreatedBy());
		saleLineItem.setCreatedDate(txnItem.getCreatedDate());
		saleLineItem.setDiscount(txnItem.getDiscountAmt());
		saleLineItem.setDiscountPct(txnItem.getDiscountPct());
		saleLineItem.setEntryMethod(txnItem.getEntryMethod());
		saleLineItem.setExcludeFromSalesFlag(txnItem.getExcludeFlag());
		saleLineItem.setExtendedAmount(txnItem.getExtendedAmt());
		saleLineItem.setItemTotal(txnItem.getNetAmt());
		saleLineItem.setGrossAmount(txnItem.getGrossAmt());
		saleLineItem.setGrossQty(txnItem.getGrossQty());
		saleLineItem.setHsnNo(txnItem.getHsnNo());
		saleLineItem.setInvAction(txnItem.getInvAction());
		saleLineItem.setItemDesc(txnItem.getItemDesc());
		saleLineItem.setMaxRetailPrice(txnItem.getMaxRetailPrice());
		saleLineItem.setModifiedBy(txnItem.getModifiedBy());
		saleLineItem.setModifiedDate(txnItem.getModifiedDate());
		saleLineItem.setNetAmount(txnItem.getNetAmt());
		saleLineItem.setNewDescription(txnItem.getNewDescription());
		saleLineItem.setOrgBusinessDate(txnItem.getOrgBusinessDate());
		saleLineItem.setOrgLocationId(txnItem.getOrgLocationId());
		saleLineItem.setOrgRegisterId(txnItem.getOrgRegister());
		saleLineItem.setOrgTxnNo(txnItem.getOrgTransactionSeq());
		saleLineItem.setQty(txnItem.getQty());
		saleLineItem.setReceiptCount(txnItem.getReceiptCount());
		saleLineItem.setReturnComment(txnItem.getReturnComment());
		saleLineItem.setReturnedQty(txnItem.getReturnedQty());
		saleLineItem.setReturnFlag(txnItem.getReturnFlag());
		saleLineItem.setReturnReason(txnItem.getReturnReason());
		saleLineItem.setReturnTypeCode(txnItem.getReturnType());

		saleLineItem.setSuggestedPrice(txnItem.getSuggestedPrice());
		saleLineItem.setTaxAmount(txnItem.getTaxAmt());
		saleLineItem.setTxnType(txnItem.getTxnType());
		saleLineItem.setUnitPrice(txnItem.getUnitPrice());
		saleLineItem.setUpcNo(txnItem.getUpc());

		saleLineItem.setBusinessDate(txnItem.getSaleLineItemId().getBusinessDate());
		saleLineItem.setItemId(txnItem.getSaleLineItemId().getItemId());
		if (StringUtils.isNotBlank(txnItem.getSaleLineItemId().getLineItemSeq()))
			saleLineItem.setSeqNo(new Integer(txnItem.getSaleLineItemId().getLineItemSeq()));
		saleLineItem.setLocationId(txnItem.getSaleLineItemId().getLocationId());
		saleLineItem.setRegisterId(txnItem.getSaleLineItemId().getRegister());
		saleLineItem.setTxnNo(txnItem.getSaleLineItemId().getTransactionSeq());
		try {
			if (itemImagesMap != null && !itemImagesMap.isEmpty()) {
				List<ItemImage> itemImages = itemImagesMap.get(txnItem.getSaleLineItemId().getItemId());
				if (itemImages != null && !itemImages.isEmpty()) {
					byte[] imageData = itemImages.get(0).getImageData();
					byte[] encodeBase64 = Base64.encodeBase64(imageData);
					String base64Encoded;

					base64Encoded = new String(encodeBase64, "UTF-8");

					saleLineItem.setImageData(base64Encoded);
					saleLineItem.setImageType(itemImages.get(0).getImageType());
				}

			}
		} catch (UnsupportedEncodingException e) {
			logger.error("There is an error while converting image data to string during transaction retrieval", e);
		}

		logger.info("The transaction header details has been transformed successfully");
		return saleLineItem;
	}

	public static List<com.punj.app.ecommerce.models.transaction.TaxLineItem> transformTxnTaxLineItems(List<TaxLineItem> txnTaxItems,
			List<com.punj.app.ecommerce.models.transaction.SaleLineItem> saleLineItems) {
		List<com.punj.app.ecommerce.models.transaction.TaxLineItem> taxLineItems = new ArrayList<>(txnTaxItems.size());
		com.punj.app.ecommerce.models.transaction.TaxLineItem taxLineItem = null;

		for (TaxLineItem txnTaxItem : txnTaxItems) {
			for (com.punj.app.ecommerce.models.transaction.SaleLineItem saleLineItem : saleLineItems) {
				if (txnTaxItem.getItemId().equals(saleLineItem.getItemId())) {
					List<com.punj.app.ecommerce.models.transaction.TaxLineItem> taxLineItemList = saleLineItem.getTaxLineItems();
					if (taxLineItemList == null)
						taxLineItemList = new ArrayList<>();

					taxLineItem = TransactionTransformer.transformTxnTaxLineItem(txnTaxItem, saleLineItem);
					taxLineItemList.add(taxLineItem);
					saleLineItem.setTaxLineItems(taxLineItemList);
					break;
				}
			}
			taxLineItems.add(taxLineItem);
		}
		logger.info("The transaction tax details list has been transformed successfully");
		return taxLineItems;
	}

	public static com.punj.app.ecommerce.models.transaction.TaxLineItem transformTxnTaxLineItem(TaxLineItem txnTaxItem,
			com.punj.app.ecommerce.models.transaction.SaleLineItem saleLineItem) {
		com.punj.app.ecommerce.models.transaction.TaxLineItem taxLineItem = new com.punj.app.ecommerce.models.transaction.TaxLineItem();

		taxLineItem.setCreatedBy(txnTaxItem.getCreatedBy());
		taxLineItem.setCreatedDate(txnTaxItem.getCreatedDate());
		taxLineItem.setItemId(txnTaxItem.getItemId());
		taxLineItem.setOrgTaxableAmt(txnTaxItem.getOrgTaxableAmt());
		taxLineItem.setOrgTaxGroupId(txnTaxItem.getOrgTaxGroupId());
		taxLineItem.setTaxGroupId(txnTaxItem.getTaxGroupId());
		taxLineItem.setTaxOverrideAmt(txnTaxItem.getTaxOverrideAmt());
		taxLineItem.setTaxOverrideFlag(txnTaxItem.getTaxOverrideFlag());
		taxLineItem.setTaxOverrideRate(txnTaxItem.getTaxOverridePercentage());
		taxLineItem.setTaxRuleAmt(txnTaxItem.getTaxRuleAmt());
		taxLineItem.setTaxRuleRate(txnTaxItem.getTaxRulePercentage());
		taxLineItem.setTaxRuleRateId(txnTaxItem.getTaxRuleRateId());
		taxLineItem.setTotalTaxableAmt(txnTaxItem.getTotalTaxableAmt());
		taxLineItem.setTotalTaxAmt(txnTaxItem.getTotalTaxAmt());
		taxLineItem.setTotalTaxExemptAmt(txnTaxItem.getTotalTaxExemptAmt());
		taxLineItem.setVoidFlag(txnTaxItem.getVoidFlag());

		taxLineItem.setBusinessDate(txnTaxItem.getTransactionLineItemId().getBusinessDate());
		if (StringUtils.isNotBlank(txnTaxItem.getTransactionLineItemId().getLineItemSeq()))
			taxLineItem.setSeqNo(new Integer(txnTaxItem.getTransactionLineItemId().getLineItemSeq()));
		taxLineItem.setLocationId(txnTaxItem.getTransactionLineItemId().getLocationId());
		taxLineItem.setRegisterId(txnTaxItem.getTransactionLineItemId().getRegister());
		taxLineItem.setTxnNo(txnTaxItem.getTransactionLineItemId().getTransactionSeq());

		if (StringUtils.isNotBlank(txnTaxItem.getTaxCode()) && MVCConstants.TAX_CGST.equals(txnTaxItem.getTaxCode())) {
			saleLineItem.setCgstCode(txnTaxItem.getTaxCode());
			saleLineItem.setCgstTax(txnTaxItem.getTotalTaxAmt());
			saleLineItem.setCgstTaxRate(txnTaxItem.getTaxRulePercentage());

			saleLineItem.setCgstRateRuleId(txnTaxItem.getTaxRuleRateId());
			saleLineItem.setTaxGroupId(txnTaxItem.getTaxGroupId());

		} else if (StringUtils.isNotBlank(txnTaxItem.getTaxCode()) && MVCConstants.TAX_SGST.equals(txnTaxItem.getTaxCode())) {
			saleLineItem.setSgstCode(txnTaxItem.getTaxCode());
			saleLineItem.setSgstTax(txnTaxItem.getTotalTaxAmt());
			saleLineItem.setSgstTaxRate(txnTaxItem.getTaxRulePercentage());
			saleLineItem.setSgstRateRuleId(txnTaxItem.getTaxRuleRateId());
			saleLineItem.setTaxGroupId(txnTaxItem.getTaxGroupId());

		} else if (StringUtils.isNotBlank(txnTaxItem.getTaxCode()) && MVCConstants.TAX_IGST.equals(txnTaxItem.getTaxCode())) {
			saleLineItem.setIgstCode(txnTaxItem.getTaxCode());
			saleLineItem.setIgstTax(txnTaxItem.getTotalTaxAmt());
			saleLineItem.setIgstTaxRate(txnTaxItem.getTaxRulePercentage());
			saleLineItem.setIgstRateRuleId(txnTaxItem.getTaxRuleRateId());
			saleLineItem.setTaxGroupId(txnTaxItem.getTaxGroupId());

		}

		logger.info("The transaction tax details has been transformed successfully");
		return taxLineItem;
	}

	public static List<com.punj.app.ecommerce.models.transaction.TenderLineItem> transformTxnTenders(List<TenderLineItem> txnTenderItems, Map<Integer, Tender> tenderMap) {
		List<com.punj.app.ecommerce.models.transaction.TenderLineItem> txnTenders = new ArrayList<>();
		com.punj.app.ecommerce.models.transaction.TenderLineItem txnTender = null;

		for (TenderLineItem txnTenderItem : txnTenderItems) {
			txnTender = TransactionTransformer.transformTxnTender(txnTenderItem, tenderMap);
			txnTenders.add(txnTender);
		}

		logger.info("The transaction tax details list has been transformed successfully");
		return txnTenders;
	}

	public static com.punj.app.ecommerce.models.transaction.TenderLineItem transformTxnTender(TenderLineItem txnTenderItem, Map<Integer, Tender> tenderMap) {
		com.punj.app.ecommerce.models.transaction.TenderLineItem tenderLineItem = new com.punj.app.ecommerce.models.transaction.TenderLineItem();
		tenderLineItem.setActionCode(txnTenderItem.getAction());
		tenderLineItem.setAmount(txnTenderItem.getAmount());
		tenderLineItem.setBusinessDate(txnTenderItem.getTransactionLineItemId().getBusinessDate());
		tenderLineItem.setChangeFlag(txnTenderItem.getChangeFlag());
		tenderLineItem.setCreatedBy(txnTenderItem.getCreatedBy());
		tenderLineItem.setCreatedDate(txnTenderItem.getCreatedDate());
		tenderLineItem.setExchangeRate(txnTenderItem.getExchangeRate());
		tenderLineItem.setForeignAmount(txnTenderItem.getForeignAmount());
		tenderLineItem.setLocationId(txnTenderItem.getTransactionLineItemId().getLocationId());
		tenderLineItem.setName(tenderMap.get(txnTenderItem.getTenderId()).getName());
		tenderLineItem.setRegisterId(txnTenderItem.getTransactionLineItemId().getRegister());
		tenderLineItem.setTenderId(txnTenderItem.getTenderId());
		if (txnTenderItem.getTransactionLineItemId().getLineItemSeq() != null)
			tenderLineItem.setSeqNo(new Integer(txnTenderItem.getTransactionLineItemId().getLineItemSeq()));
		tenderLineItem.setTxnNo(txnTenderItem.getTransactionLineItemId().getTransactionSeq());
		tenderLineItem.setTypeCode(txnTenderItem.getType());

		logger.info("The transaction tender details has been transformed successfully");
		return tenderLineItem;
	}

	public static CustomerBean transformCustomer(Customer customer, TransactionCustomer txnCustomer) {
		CustomerBean customerBean = CustomerTransformer.transformCustomer(customer);
		logger.info("The transaction customer details has been transformed successfully");

		return customerBean;
	}
}
