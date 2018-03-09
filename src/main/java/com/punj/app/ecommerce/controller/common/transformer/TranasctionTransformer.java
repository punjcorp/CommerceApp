/**
 * 
 */
package com.punj.app.ecommerce.controller.common.transformer;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.punj.app.ecommerce.domains.item.Item;
import com.punj.app.ecommerce.domains.transaction.SaleLineItem;
import com.punj.app.ecommerce.domains.transaction.TaxLineItem;
import com.punj.app.ecommerce.domains.transaction.TenderLineItem;
import com.punj.app.ecommerce.domains.transaction.Transaction;
import com.punj.app.ecommerce.domains.transaction.TransactionLineItem;
import com.punj.app.ecommerce.domains.transaction.ids.SaleLineItemId;
import com.punj.app.ecommerce.domains.transaction.ids.TransactionId;
import com.punj.app.ecommerce.domains.transaction.ids.TransactionLineItemId;
import com.punj.app.ecommerce.models.transaction.TransactionHeader;

/**
 * @author admin
 *
 */
public class TranasctionTransformer {

	private static final Logger logger = LogManager.getLogger();

	private TranasctionTransformer() {
		throw new IllegalStateException("TranasctionTransformer class");
	}

	public static Transaction transformTransactionDetails(TransactionHeader txnHeader) {

		Transaction txn = new Transaction();

		TransactionId txnId = new TransactionId();
		txnId.setLocationId(txnHeader.getLocationId());
		txnId.setRegister(txnHeader.getRegisterId());
		txnId.setBusinessDate(txnHeader.getBusinessDate());
		txnId.setTransactionSeq(txnHeader.getTxnNo());

		txn.setTransactionId(txnId);

		txn.setCancelReason(txnHeader.getCancelReason());
		txn.setCreatedBy(txnHeader.getCreatedBy());
		txn.setCreatedDate(txnHeader.getCreatedDate());
		txn.setStartTime(txnHeader.getStartTime());
		txn.setEndTime(txnHeader.getEndTime());
		txn.setModifiedBy(txnHeader.getModifiedBy());
		txn.setModifiedDate(txnHeader.getModifiedDate());
		// txnHeader.getNetTotalAmount();
		txn.setOfflineFlag(txnHeader.getOfflineFlag());
		txn.setPostVoidFlag(txnHeader.getPostVoidFlag());
		txn.setSessionId(txnHeader.getSessionId());
		txn.setStatus(txnHeader.getStatus());
		txn.setTaxTotalAmt(txnHeader.getTotalCGSTTaxAmt().add(txnHeader.getTotalSGSTTaxAmt()));
		// txnHeader.getTotalIGSTTaxAmt();
		// txnHeader.getTotalDiscountAmt();
		txn.setTotalAmt(txnHeader.getTotalDueAmt());
		txn.setTaxTotalAmt(txnHeader.getTotalTaxAmt());
		txn.setTxnType(txnHeader.getTxnType());
		logger.info("The transaction header details has been transformed now");
		return txn;
	}

	public static TransactionLineItem transformTxnLineItem(com.punj.app.ecommerce.models.transaction.SaleLineItem saleLineItem, TransactionId txnId) {

		TransactionLineItem txnLI = new TransactionLineItem();
		
		TransactionLineItemId txnLIId=new TransactionLineItemId();
		txnLIId.setBusinessDate(txnId.getBusinessDate());
		txnLIId.setLocationId(txnId.getLocationId());
		txnLIId.setRegister(txnId.getRegister());
		txnLIId.setTransactionSeq(txnId.getTransactionSeq());
		txnLIId.setLineItemSeq("1");
		
		txnLI.setTransactionLineItemId(txnLIId);
		
		txnLI.setLineItemType("SALE_LINE_ITEM");
		txnLI.setStatus("APPROVED");
		
		txnLI.setCreatedBy(saleLineItem.getCreatedBy());
		txnLI.setCreatedDate(saleLineItem.getCreatedDate());
		
		//txnLI.setStartTime();
		//txnLI.setEndTime();
		//txnLI.setVoidFlag(Boolean.FALSE);
		//txnLI.setVoidReason(null);
		
		logger.info("The transaction header details has been transformed now");
		return txnLI;
	}


	public static SaleLineItem transformSaleLineItem(com.punj.app.ecommerce.models.transaction.SaleLineItem saleLineItem, TransactionLineItem txnLI) {

		SaleLineItem saleLI = new SaleLineItem();

		SaleLineItemId saleLIId=new SaleLineItemId();
		Item item=new Item();
		item.setItemId(saleLineItem.getItemId());
		
		saleLIId.setItem(item);
		saleLIId.setTxnLineItem(txnLI);
		
		logger.info("The Sale Line Item has been transformed now");
		return saleLI;
	}

	public static List<SaleLineItem> transformSaleLineItems(List<com.punj.app.ecommerce.models.transaction.SaleLineItem> saleLineItems, TransactionId txnId) {
		List<TransactionLineItem> txnLIs = new ArrayList<>(saleLineItems.size());
		TransactionLineItem txnLI;

		List<SaleLineItem> saleLIs = new ArrayList<>(saleLineItems.size());
		SaleLineItem saleLI;
		
		List<TaxLineItem> taxLIs;
		
		for (com.punj.app.ecommerce.models.transaction.SaleLineItem saleLineItem : saleLineItems) {
			txnLI = TranasctionTransformer.transformTxnLineItem(saleLineItem, txnId);
			txnLIs.add(txnLI);
			
			saleLI = TranasctionTransformer.transformSaleLineItem(saleLineItem, txnLI);
			saleLIs.add(saleLI);
			
			taxLIs = TranasctionTransformer.transformTaxLineItem(saleLineItem, txnLI);
			
		}

		return saleLIs;
	}

	public static List<TaxLineItem> transformTaxLineItem(com.punj.app.ecommerce.models.transaction.SaleLineItem saleLineItem, TransactionLineItem txnLI) {
		
		List<TaxLineItem> taxLIs = new ArrayList<>();
		
		TaxLineItem taxLI = new TaxLineItem();
		taxLI.setTransactionLineItemId(txnLI.getTransactionLineItemId());
		
		//taxLI.setTaxGroupId();
		
		taxLI.setCreatedBy(saleLineItem.getCreatedBy());
		taxLI.setCreatedDate(saleLineItem.getCreatedDate());
		taxLI.setTaxRuleAmt(saleLineItem.getSgstTax());
		taxLI.setTaxRulePercentage(saleLineItem.getSgstTaxRate());
		
		//taxLI.setTaxRuleRateId();
		
		taxLI.setTotalTaxableAmt(saleLineItem.getExtendedAmount().subtract(saleLineItem.getDiscount()));
		taxLI.setTotalTaxAmt(saleLineItem.getSgstTax());
		
		//taxLI.setVoidFlag(Boolean.FALSE);
		
		taxLIs.add(taxLI);
		
		
		
		taxLI = new TaxLineItem();
		taxLI.setTransactionLineItemId(txnLI.getTransactionLineItemId());
		
		//taxLI.setTaxGroupId();
		
		taxLI.setCreatedBy(saleLineItem.getCreatedBy());
		taxLI.setCreatedDate(saleLineItem.getCreatedDate());
		taxLI.setTaxRuleAmt(saleLineItem.getCgstTax());
		taxLI.setTaxRulePercentage(saleLineItem.getCgstTaxRate());
		
		//taxLI.setTaxRuleRateId();
		
		taxLI.setTotalTaxableAmt(saleLineItem.getExtendedAmount().subtract(saleLineItem.getDiscount()));
		taxLI.setTotalTaxAmt(saleLineItem.getCgstTax());
		
		//taxLI.setVoidFlag(Boolean.FALSE);		
		
		taxLIs.add(taxLI);
		
		
		

		logger.info("The Tax Line Items has been transformed now");
		return taxLIs;
	}

	public static TenderLineItem transformTenderLineItem(com.punj.app.ecommerce.models.transaction.TenderLineItem tenderLineItem) {

		TenderLineItem tndrLI = new TenderLineItem();
		TransactionLineItemId txnLineItemId=new TransactionLineItemId();
		txnLineItemId.setBusinessDate(tenderLineItem.getBusinessDate());
		txnLineItemId.setLocationId(tenderLineItem.getLocationId());
		txnLineItemId.setRegister(tenderLineItem.getRegisterId());
		txnLineItemId.setTransactionSeq(tenderLineItem.getTxnNo());
		txnLineItemId.setLineItemSeq(tenderLineItem.getSeqNo()+"");

		//tndrLI.setTransactionLineItemId();
		tndrLI.setAction(tenderLineItem.getActionCode());
		tndrLI.setAmount(tenderLineItem.getAmount());
		tndrLI.setType(tenderLineItem.getTypeCode());
		tndrLI.setCreatedBy(tenderLineItem.getCreatedBy());
		tndrLI.setCreatedDate(tenderLineItem.getCreatedDate());
		tndrLI.setExchangeRate(tenderLineItem.getExchangeRate());
		tndrLI.setForeignAmount(tenderLineItem.getForeignAmount());
		tndrLI.setType(tenderLineItem.getTypeCode());
		
		logger.info("The tender line item has been transformed now");
		return tndrLI;
	}

	public static List<TenderLineItem> transformTenderLineItems(List<com.punj.app.ecommerce.models.transaction.TenderLineItem> tenderLineItems, TransactionId txnId) {
		List<TenderLineItem> tenderLIs = new ArrayList<>(tenderLineItems.size());
		TenderLineItem tenderLI;
		for (com.punj.app.ecommerce.models.transaction.TenderLineItem tenderLineItem : tenderLineItems) {
			tenderLI = TranasctionTransformer.transformTenderLineItem(tenderLineItem, txnId);
			tenderLIs.add(tenderLI);
		}

		return tenderLIs;
	}

}
