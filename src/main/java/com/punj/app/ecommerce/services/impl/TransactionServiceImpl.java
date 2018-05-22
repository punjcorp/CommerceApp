/**
 * 
 */
package com.punj.app.ecommerce.services.impl;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.punj.app.ecommerce.domains.common.Location;
import com.punj.app.ecommerce.domains.finance.TenderMovement;
import com.punj.app.ecommerce.domains.inventory.ItemStockJournal;
import com.punj.app.ecommerce.domains.inventory.StockReason;
import com.punj.app.ecommerce.domains.item.Item;
import com.punj.app.ecommerce.domains.transaction.ReceiptItemTax;
import com.punj.app.ecommerce.domains.transaction.SaleLineItem;
import com.punj.app.ecommerce.domains.transaction.TaxLineItem;
import com.punj.app.ecommerce.domains.transaction.TenderLineItem;
import com.punj.app.ecommerce.domains.transaction.Transaction;
import com.punj.app.ecommerce.domains.transaction.TransactionLineItem;
import com.punj.app.ecommerce.domains.transaction.TransactionReceipt;
import com.punj.app.ecommerce.domains.transaction.ids.SaleLineItemId;
import com.punj.app.ecommerce.domains.transaction.ids.TransactionId;
import com.punj.app.ecommerce.domains.transaction.ids.TransactionLineItemId;
import com.punj.app.ecommerce.repositories.finance.TenderMovementRepository;
import com.punj.app.ecommerce.repositories.transaction.ReceiptItemTaxRepository;
import com.punj.app.ecommerce.repositories.transaction.SaleLineItemRepository;
import com.punj.app.ecommerce.repositories.transaction.TaxLineItemRepository;
import com.punj.app.ecommerce.repositories.transaction.TenderLineItemRepository;
import com.punj.app.ecommerce.repositories.transaction.TransactionLineItemRepository;
import com.punj.app.ecommerce.repositories.transaction.TransactionReceiptRepository;
import com.punj.app.ecommerce.repositories.transaction.TransactionRepository;
import com.punj.app.ecommerce.services.FinanceService;
import com.punj.app.ecommerce.services.InventoryService;
import com.punj.app.ecommerce.services.TransactionService;
import com.punj.app.ecommerce.services.common.CommonService;
import com.punj.app.ecommerce.services.common.ServiceConstants;
import com.punj.app.ecommerce.services.dtos.transaction.SaleTransactionReceiptDTO;
import com.punj.app.ecommerce.services.dtos.transaction.TransactionDTO;
import com.punj.app.ecommerce.services.dtos.transaction.TransactionIdDTO;

/**
 * @author admin
 *
 */
@Service
public class TransactionServiceImpl implements TransactionService {

	private static final Logger logger = LogManager.getLogger();
	private CommonService commonService;
	private InventoryService inventoryService;
	private TransactionRepository transactionRepository;
	private TransactionLineItemRepository transactionLineItemRepository;
	private SaleLineItemRepository saleLineItemRepository;
	private TaxLineItemRepository taxLineItemRepository;
	private TenderLineItemRepository tenderLineItemRepository;
	private ReceiptItemTaxRepository receiptItemTaxRepository;
	private TransactionReceiptRepository txnReceiptRepository;
	private TenderMovementRepository tenderMovementRepository;
	private FinanceService financeService;

	/**
	 * @param financeService
	 *            the financeService to set
	 */
	@Autowired
	public void setFinanceService(FinanceService financeService) {
		this.financeService = financeService;
	}

	/**
	 * @param tenderMovementRepository
	 *            the tenderMovementRepository to set
	 */
	@Autowired
	public void setTenderMovementRepository(TenderMovementRepository tenderMovementRepository) {
		this.tenderMovementRepository = tenderMovementRepository;
	}

	/**
	 * @param txnReceiptRepository
	 *            the txnReceiptRepository to set
	 */
	@Autowired
	public void setTxnReceiptRepository(TransactionReceiptRepository txnReceiptRepository) {
		this.txnReceiptRepository = txnReceiptRepository;
	}

	/**
	 * @param inventoryService
	 *            the inventoryService to set
	 */
	@Autowired
	public void setInventoryService(InventoryService inventoryService) {
		this.inventoryService = inventoryService;
	}

	/**
	 * @param commonService
	 *            the commonService to set
	 */
	@Autowired
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	/**
	 * @param transactionRepository
	 *            the transactionRepository to set
	 */
	@Autowired
	public void setTransactionRepository(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	/**
	 * @param transactionLineItemRepository
	 *            the transactionLineItemRepository to set
	 */
	@Autowired
	public void setTransactionLineItemRepository(TransactionLineItemRepository transactionLineItemRepository) {
		this.transactionLineItemRepository = transactionLineItemRepository;
	}

	/**
	 * @param saleLineItemRepository
	 *            the saleLineItemRepository to set
	 */
	@Autowired
	public void setSaleLineItemRepository(SaleLineItemRepository saleLineItemRepository) {
		this.saleLineItemRepository = saleLineItemRepository;
	}

	/**
	 * @param taxLineItemRepository
	 *            the taxLineItemRepository to set
	 */
	@Autowired
	public void setTaxLineItemRepository(TaxLineItemRepository taxLineItemRepository) {
		this.taxLineItemRepository = taxLineItemRepository;
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
	 * @param tenderLineItemRepository
	 *            the tenderLineItemRepository to set
	 */
	@Autowired
	public void setTenderLineItemRepository(TenderLineItemRepository tenderLineItemRepository) {
		this.tenderLineItemRepository = tenderLineItemRepository;
	}

	@Override
	public Transaction saveTransaction(Transaction txnDetails) {
		BigInteger txnNo = this.commonService
				.getId(txnDetails.getTransactionId().getLocationId() + "_" + txnDetails.getTransactionId().getRegister() + "_" + ServiceConstants.TXN_SEQ);
		if (txnNo != null) {
			logger.info("A new txn number {} has been generated now.", txnNo);
			txnDetails.getTransactionId().setTransactionSeq(txnNo.intValue());
			txnDetails = this.transactionRepository.save(txnDetails);
			logger.info("The transaction has been created based on provided details successfully.");
		} else {
			logger.info("There was some issue generating a new transaction for the details");
		}
		return txnDetails;
	}

	@Override
	public Transaction createTransactionInstance(TransactionIdDTO txnIdDTO, String username, String txnType) {
		TransactionId txnId = new TransactionId();
		txnId.setTransactionSeq(txnIdDTO.getTxnNo());
		txnId.setLocationId(txnIdDTO.getLocationId());
		txnId.setRegister(txnIdDTO.getRegister());
		txnId.setBusinessDate(txnIdDTO.getBusinessDate());

		Transaction txnDetails = new Transaction();
		txnDetails.setTransactionId(txnId);
		txnDetails.setStartTime(LocalDateTime.now());
		txnDetails.setCreatedDate(LocalDateTime.now());
		txnDetails.setCreatedBy(username);

		txnDetails.setStatus(ServiceConstants.TXN_STATUS_STARTED);
		txnDetails.setTxnType(txnType);

		logger.info("The transaction {} instance has been created based on provided details", txnType);

		return txnDetails;
	}

	@Override
	public Transaction searchTxnByCriteria(Integer locationId, Set<String> txnTypes) {
		Transaction txnDetails = this.transactionRepository.getTop1ByCriteriaAndSort(locationId, txnTypes);
		if (txnDetails != null)
			logger.info("The last transaction {} details retrieved successfully", txnDetails.getTransactionId());
		return txnDetails;
	}

	@Override
	public Map<Integer, Transaction> searchRegisterTxnByCriteria(Integer locationId, Set<String> txnTypes) {
		Map<Integer, Transaction> txnMap = new HashMap<>();
		List<Transaction> txnDetails = this.transactionRepository.getLastDailyRegisterTxns(locationId, txnTypes);
		for (Transaction txnDtl : txnDetails) {
			txnMap.put(txnDtl.getTransactionId().getRegister(), txnDtl);
		}
		logger.info("The last transaction details for location {} regitsters has been etrieved successfully", locationId);
		return txnMap;
	}

	private Map<Integer, Transaction> searchTxnByCriteria(List<Integer> locationIds, Set<String> txnTypes) {
		Sort sort = new Sort(Sort.Direction.ASC, "transactionId.locationId");
		sort.and(new Sort(Sort.Direction.DESC, "transactionId.businessDate"));
		sort.and(new Sort(Sort.Direction.DESC, "startTime"));
		List<Transaction> txnDetails = null;
		return null;
	}

	@Override
	public Transaction searchLocationOpenTxn(Integer locationId, LocalDateTime businessDate) {
		Transaction txnCriteria = new Transaction();
		TransactionId txnId = new TransactionId();
		txnId.setLocationId(locationId);
		txnId.setBusinessDate(businessDate);
		txnCriteria.setTransactionId(txnId);
		txnCriteria.setTxnType(ServiceConstants.TXN_OPEN_STORE);
		Transaction txnDetails = this.transactionRepository.findOne(Example.of(txnCriteria));
		if (txnDetails != null)
			logger.info("The last location open txn for location {} and business date {} has been retrieved succefully", locationId, businessDate);
		else
			logger.info("The last location open txn retrieval for location {} and business date {} has failed", locationId, businessDate);

		return txnDetails;
	}

	@Override
	@Transactional
	public TransactionId saveSaleTransaction(TransactionDTO txnDTO) {
		TransactionId txnId = null;
		Transaction txnDetails = txnDTO.getTxn();
		Transaction txnHeader = this.saveTransaction(txnDetails);
		if (txnHeader != null) {
			logger.info("The {} transaction header details has been saved successfully", txnHeader.getTxnType());
			txnId = txnHeader.getTransactionId();
			Boolean txnLISaveResult = this.saveTransactionLineItems(txnDTO, txnId);
			if (!txnLISaveResult) {
				txnId = null;
			}
			List<ItemStockJournal> itemStockDetails = this.createStockDetails(txnDTO.getSaleLineItems(), txnHeader.getCreatedBy());
			this.inventoryService.updateInventory(itemStockDetails);
			logger.info("The inventory updates for the {} items has been posted successfully", txnHeader.getTxnType());
		} else {
			logger.info("There is some issue while saving sale transaction header details");
		}

		return txnId;
	}

	private Boolean saveTransactionLineItems(TransactionDTO txnDTO, TransactionId txnId) {
		Boolean result = Boolean.FALSE;

		List<TransactionLineItem> txnLineItems = txnDTO.getTxnLineItems();
		for (TransactionLineItem txnLineItem : txnLineItems) {
			txnLineItem.getTransactionLineItemId().setTransactionSeq(txnId.getTransactionSeq());
		}

		txnLineItems = this.transactionLineItemRepository.save(txnLineItems);

		if (txnLineItems != null) {
			logger.info("There transaction line item details has been saved successfully");

			List<SaleLineItem> saleLineItems = txnDTO.getSaleLineItems();
			for (SaleLineItem saleLineItem : saleLineItems) {
				saleLineItem.getSaleLineItemId().setTransactionSeq(txnId.getTransactionSeq());
			}
			saleLineItems = this.saleLineItemRepository.save(saleLineItems);
			if (saleLineItems != null) {
				logger.info("The sale line item details were saved successfully");

				List<TaxLineItem> taxLineItems = txnDTO.getTaxLineItems();
				for (TaxLineItem taxLineItem : taxLineItems) {
					taxLineItem.getTransactionLineItemId().setTransactionSeq(txnId.getTransactionSeq());
				}
				Boolean taxSaveResult = this.saveTaxLineItems(taxLineItems);

				List<TenderLineItem> tenderLineItems = txnDTO.getTenderLineItems();
				for (TenderLineItem tenderLineItem : tenderLineItems) {
					tenderLineItem.getTransactionLineItemId().setTransactionSeq(txnId.getTransactionSeq());
				}
				Boolean tenderSaveResult = this.saveTenderLineItems(tenderLineItems);

				if (taxSaveResult && tenderSaveResult) {
					result = Boolean.TRUE;
				}

			} else {
				logger.info("There is a failure while saving sale line item details");
			}

		} else {
			logger.info("The transaction line item details had issue while saving");
		}

		return result;
	}

	private Boolean saveTaxLineItems(List<TaxLineItem> taxLineItems) {
		Boolean result = Boolean.FALSE;

		taxLineItems = this.taxLineItemRepository.save(taxLineItems);
		if (taxLineItems != null) {
			logger.info("The tax line item details were saved successfully");
			result = Boolean.TRUE;
		} else {
			logger.info("There was issue while saving tax line item details");
		}

		return result;
	}

	private Boolean saveTenderLineItems(List<TenderLineItem> tenderLineItems) {
		Boolean result = Boolean.FALSE;

		tenderLineItems = this.tenderLineItemRepository.save(tenderLineItems);
		if (tenderLineItems != null) {
			logger.info("Thetender line item details were saved successfully");
			result = Boolean.TRUE;
		} else {
			logger.info("There was issue while saving tender line item details");
		}

		return result;
	}

	@Override
	public SaleTransactionReceiptDTO generateTransactionReceipt(TransactionId txnId) {

		SaleTransactionReceiptDTO txnReceipt = null;

		Transaction txnDetails = this.transactionRepository.findOne(txnId);

		if (txnDetails != null) {
			logger.info("The txn receipt details has been retreived successfully");
			txnReceipt = new SaleTransactionReceiptDTO();
			txnReceipt.setTxn(txnDetails);

			Location location = this.commonService.retrieveLocationDetails(txnId.getLocationId());
			txnReceipt.setLocation(location);

			ReceiptItemTax receiptItemTaxCriteria = new ReceiptItemTax();
			SaleLineItemId saleLineItemId = new SaleLineItemId();
			saleLineItemId.setLocationId(txnId.getLocationId());
			saleLineItemId.setRegister(txnId.getRegister());
			saleLineItemId.setBusinessDate(txnId.getBusinessDate());
			saleLineItemId.setTransactionSeq(txnId.getTransactionSeq());

			receiptItemTaxCriteria.setSaleLineItemId(saleLineItemId);

			List<ReceiptItemTax> receiptItems = this.receiptItemTaxRepository.findAll(Example.of(receiptItemTaxCriteria),
					new Sort(Sort.Direction.ASC, "saleLineItemId.lineItemSeq"));

			if (receiptItems != null && !receiptItems.isEmpty()) {

				TransactionLineItemId txnLineItemId = new TransactionLineItemId();
				txnLineItemId.setLocationId(txnId.getLocationId());
				txnLineItemId.setRegister(txnId.getRegister());
				txnLineItemId.setBusinessDate(txnId.getBusinessDate());
				txnLineItemId.setTransactionSeq(txnId.getTransactionSeq());

				TenderLineItem tenderCriteria = new TenderLineItem();
				tenderCriteria.setTransactionLineItemId(txnLineItemId);

				List<TenderLineItem> tenders = this.tenderLineItemRepository.findAll(Example.of(tenderCriteria));

				txnReceipt.setTxnLineItems(receiptItems);
				txnReceipt.setTenderLineItems(tenders);

				logger.info("The receipt line items were retrieved successfully ");
			} else {
				logger.info("The receipt line items were not found for the provided transaction");
			}

		} else {
			logger.info("There was no details found for the provided transaction details");
		}

		return txnReceipt;
	}

	public Boolean saveTransactionReceipt(List<TransactionReceipt> txnReceipts) {
		Boolean result = Boolean.FALSE;
		txnReceipts = this.txnReceiptRepository.save(txnReceipts);
		logger.info("The transaction receipt details has been saved in the database now");
		result = Boolean.TRUE;
		return result;
	}

	private List<ItemStockJournal> createStockDetails(List<SaleLineItem> saleLineItems, String username) {

		List<ItemStockJournal> itemStockDetails = new ArrayList<>(saleLineItems.size());
		ItemStockJournal itemStockJournal = null;
		Item item = null;

		int itemQty = 0;
		for (SaleLineItem saleLineItem : saleLineItems) {
			itemStockJournal = new ItemStockJournal();
			itemStockJournal.setCreatedBy(username);
			itemStockJournal.setCreatedDate(LocalDateTime.now());

			itemStockJournal.setLocationId(saleLineItem.getSaleLineItemId().getLocationId());

			item = new Item();
			item.setItemId(saleLineItem.getSaleLineItemId().getItemId());
			itemStockJournal.setItemId(item.getItemId());

			itemQty = saleLineItem.getQty().intValue();
			StockReason stockReason = new StockReason();
			if (itemQty < 0) {
				stockReason.setReasonCode(ServiceConstants.INV_REASON_STKIN);
				itemStockJournal.setFunctionality(ServiceConstants.RETURN_TXN_FUNCTIONALITY);
			} else {
				stockReason.setReasonCode(ServiceConstants.INV_REASON_STKOUT);
				itemStockJournal.setFunctionality(ServiceConstants.SALE_TXN_FUNCTIONALITY);
			}

			itemStockJournal.setReasonCode(stockReason);
			itemStockJournal.setQty(itemQty);

			itemStockDetails.add(itemStockJournal);
		}

		logger.info("The stock details has been created from Sale Txn Line Item details");
		return itemStockDetails;

	}

	@Override
	public TenderMovement saveTenderMoveTxn(TenderMovement tenderMoveDetails) {

		tenderMoveDetails=this.tenderMovementRepository.save(tenderMoveDetails);
		
		if (tenderMoveDetails != null)
			logger.info("The tender move details has been saved successfully");
		else
			logger.info("The tender move details were not saved due to some issues");

		return tenderMoveDetails;
	}

}
