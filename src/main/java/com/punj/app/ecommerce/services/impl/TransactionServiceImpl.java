/**
 * 
 */
package com.punj.app.ecommerce.services.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
import com.punj.app.ecommerce.domains.customer.Customer;
import com.punj.app.ecommerce.domains.finance.DailyTotals;
import com.punj.app.ecommerce.domains.finance.LedgerJournal;
import com.punj.app.ecommerce.domains.finance.TenderMovement;
import com.punj.app.ecommerce.domains.inventory.ItemStockJournal;
import com.punj.app.ecommerce.domains.inventory.StockReason;
import com.punj.app.ecommerce.domains.item.Item;
import com.punj.app.ecommerce.domains.payment.AccountHead;
import com.punj.app.ecommerce.domains.payment.AccountJournal;
import com.punj.app.ecommerce.domains.supplier.Supplier;
import com.punj.app.ecommerce.domains.tender.Tender;
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
import com.punj.app.ecommerce.repositories.finance.TenderMovementRepository;
import com.punj.app.ecommerce.repositories.transaction.ReceiptItemTaxRepository;
import com.punj.app.ecommerce.repositories.transaction.SaleLineItemRepository;
import com.punj.app.ecommerce.repositories.transaction.TaxLineItemRepository;
import com.punj.app.ecommerce.repositories.transaction.TenderLineItemRepository;
import com.punj.app.ecommerce.repositories.transaction.TransactionCustomerRepository;
import com.punj.app.ecommerce.repositories.transaction.TransactionLineItemRepository;
import com.punj.app.ecommerce.repositories.transaction.TransactionReceiptRepository;
import com.punj.app.ecommerce.repositories.transaction.TransactionRepository;
import com.punj.app.ecommerce.services.AccountService;
import com.punj.app.ecommerce.services.FinanceService;
import com.punj.app.ecommerce.services.InventoryService;
import com.punj.app.ecommerce.services.PaymentAccountService;
import com.punj.app.ecommerce.services.SupplierService;
import com.punj.app.ecommerce.services.TransactionService;
import com.punj.app.ecommerce.services.common.CommonService;
import com.punj.app.ecommerce.services.common.ServiceConstants;
import com.punj.app.ecommerce.services.converter.TransactionConverter;
import com.punj.app.ecommerce.services.converter.TxnToLedgerConverter;
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
	private TransactionCustomerRepository txnCustomerRepository;
	private TenderMovementRepository tenderMovementRepository;
	private FinanceService financeService;
	private AccountService accountService;
	private SupplierService supplierService;
	private PaymentAccountService paymentAccountService;

	/**
	 * @param paymentAccountService
	 *            the paymentAccountService to set
	 */
	@Autowired
	public void setPaymentAccountService(PaymentAccountService paymentAccountService) {
		this.paymentAccountService = paymentAccountService;
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
	 * @param accountService
	 *            the accountService to set
	 */
	@Autowired
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

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
	 * @param txnCustomerRepository
	 *            the txnCustomerRepository to set
	 */
	@Autowired
	public void setTxnCustomerRepository(TransactionCustomerRepository txnCustomerRepository) {
		this.txnCustomerRepository = txnCustomerRepository;
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

		txnDetails.setStatus(ServiceConstants.TXN_STATUS_COMPLETED);
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
		Transaction tmpTxn = null;
		Integer registerId = null;
		List<Transaction> txnDetails = this.transactionRepository.getLastDailyRegisterTxns(locationId, txnTypes);
		for (Transaction txnDtl : txnDetails) {
			registerId = txnDtl.getTransactionId().getRegister();
			// Remove Multiple txns for a single register
			// Only keeps the latest one

			tmpTxn = txnMap.get(registerId);
			if (tmpTxn == null)
				txnMap.put(registerId, txnDtl);
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

		TransactionCustomer txnCustomer = txnDTO.getTxnCustomer();
		Customer customer = txnDTO.getCustomer();
		List<AccountHead> accountHeads = null;
		AccountHead accountHead = null;
		if (customer != null && customer.getCustomerId() == null) {
			accountHeads = this.accountService.createCustomer(customer);
			if (accountHeads != null && !accountHeads.isEmpty()) {
				for (AccountHead tmpAccount : accountHeads) {
					if (tmpAccount.getLocationId().equals(txnCustomer.getTransactionCustomerId().getLocationId())) {
						accountHead = tmpAccount;
						break;
					}
				}

				logger.info("The {} customer details has been saved successfully", accountHead.getEntityId());
				txnDTO.getTxnCustomer().getTransactionCustomerId().setCustomerId(accountHead.getEntityId());
			} else {
				logger.info("There was an issue while saving customer details");
			}

		} else if (txnCustomer != null && txnCustomer.getTransactionCustomerId() != null) {

			accountHead = this.paymentAccountService.retrievePaymentAccount(txnCustomer.getTransactionCustomerId().getCustomerType(),
					txnCustomer.getTransactionCustomerId().getCustomerId(), txnCustomer.getTransactionCustomerId().getLocationId());
			if (accountHead != null)
				logger.info("The account head details has been retrieved successfully");
		}

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

			if (txnCustomer != null && accountHead != null) {
				txnCustomer.getTransactionCustomerId().setTransactionSeq(txnId.getTransactionSeq());
				txnCustomer = this.txnCustomerRepository.save(txnCustomer);
				if (txnCustomer != null) {
					logger.info("The {} customer details for the customer has been saved successfully", txnCustomer.getTransactionCustomerId().getCustomerId());
				}
			}

			this.updateFinanceDetails(accountHead, txnDTO, txnHeader.getCreatedBy());
			logger.info("The credit tender details has been added to Customer account now");

			List<ItemStockJournal> itemStockDetails = this.createStockDetails(txnDTO.getSaleLineItems(), txnHeader.getCreatedBy());
			this.inventoryService.updateInventory(itemStockDetails);
			logger.info("The inventory updates for the {} items has been posted successfully", txnHeader.getTxnType());
		} else {
			logger.info("There is some issue while saving sale transaction header details");
		}

		return txnId;
	}

	private void updateFinanceDetails(AccountHead accountHead, TransactionDTO txnDTO, String username) {
		List<TenderLineItem> txnTenders = txnDTO.getTenderLineItems();
		List<TenderLineItem> txnCreditTenders = new ArrayList<>();

		Transaction txnDetails = txnDTO.getTxn();
		TransactionId txnId = txnDTO.getTxn().getTransactionId();

		Map<Integer, Tender> tenderMap = this.commonService.retrieveAllTendersAsMap(txnId.getLocationId());
		Integer tenderId = null;
		Tender tender = null;
		BigDecimal totalCreditAmount = BigDecimal.ZERO;
		if (txnTenders != null && !txnTenders.isEmpty() && tenderMap != null) {
			for (TenderLineItem tenderLineItem : txnTenders) {
				tenderId = tenderLineItem.getTenderId();
				tender = tenderMap.get(tenderId);
				if (tender.getName().equals(ServiceConstants.TENDER_CREDIT)) {
					totalCreditAmount = totalCreditAmount.add(tenderLineItem.getAmount());
					txnCreditTenders.add(tenderLineItem);
				}
			}

			if (!txnCreditTenders.isEmpty() && accountHead != null) {
				AccountJournal accountJournal = TransactionConverter.convertCreditToAJ(accountHead, txnCreditTenders, totalCreditAmount, username, txnDetails.getTxnType());
				accountJournal = this.paymentAccountService.savePayment(accountJournal, username);
				if (accountJournal != null)
					logger.info("The account journal details for credit tender has been saved successfully");
				else
					logger.info("There was error while saving credit tender details");
			}

		}

		LedgerJournal ledgerJournal = TxnToLedgerConverter.convertTxnToLedger(txnDTO, username);
		if (ledgerJournal != null) {
			ledgerJournal = this.financeService.saveLedgerDetails(ledgerJournal);
			if (ledgerJournal != null)
				logger.info("The ledger details for the txn {} has been saved successfully", txnId.toString());
			else
				logger.error("The was error saving ledger details for the txn {} ", txnId.toString());
		}

		DailyTotals dailyTotals = TransactionConverter.createDailyTotals(txnDTO.getTxn());
		DailyTotals registerTotals = this.financeService.updateDailyTotals(dailyTotals, txnDetails.getTxnType());
		if (registerTotals != null)
			logger.info("The register and store totals has been updated for the txn {} successfully", txnId.toString());
		else
			logger.error("The was error updating register and store totals for the txn {} ", txnId.toString());

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

			TransactionCustomerId txnCustomerId = new TransactionCustomerId();
			txnCustomerId.setBusinessDate(txnId.getBusinessDate());
			txnCustomerId.setLocationId(txnId.getLocationId());
			txnCustomerId.setRegister(txnId.getRegister());
			txnCustomerId.setTransactionSeq(txnId.getTransactionSeq());

			TransactionCustomer txnCustomerCriteria = new TransactionCustomer();
			txnCustomerCriteria.setTransactionCustomerId(txnCustomerId);

			TransactionCustomer txnCustomer = this.txnCustomerRepository.findOne(Example.of(txnCustomerCriteria));
			if (txnCustomer != null) {
				txnReceipt.setTxnCustomer(txnCustomer);
				String custType = txnCustomer.getTransactionCustomerId().getCustomerType();
				BigInteger custId = txnCustomer.getTransactionCustomerId().getCustomerId();
				if (custType.equals(ServiceConstants.CUSTOMER_TYPE_CLIENT)) {
					Customer customer = this.accountService.searchCustomerDetails(custId);
					txnReceipt.setCustomerDetails(customer);
					logger.info("The customer details has been updated successfully");
				} else if (custType.equals(ServiceConstants.CUSTOMER_TYPE_SUPPLIER)) {
					Supplier supplier = this.supplierService.searchSupplier(custId.intValue());
					txnReceipt.setSupplierDetails(supplier);
					logger.info("The supplier details has been updated successfully");
				}

				logger.info("The associated customer details has been retrieved successfully");

			} else {
				logger.info("There was no customer associated with this transaction");
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

		tenderMoveDetails = this.tenderMovementRepository.save(tenderMoveDetails);

		if (tenderMoveDetails != null)
			logger.info("The tender move details has been saved successfully");
		else
			logger.info("The tender move details were not saved due to some issues");

		return tenderMoveDetails;
	}

	@Override
	public TransactionReceipt retrieveLastTransaction(TransactionId txnIdCriteria) {

		TransactionReceipt txnReceipt = null;
		Transaction txnDetails = null;
		Integer txnNo = this.transactionRepository.getLastTransactionNo(txnIdCriteria.getLocationId(), txnIdCriteria.getRegister(), txnIdCriteria.getBusinessDate());
		if (txnNo != null) {
			txnIdCriteria.setTransactionSeq(txnNo);
			txnDetails = this.transactionRepository.findOne(txnIdCriteria);

			if (txnDetails != null) {
				logger.info("The transaction details has been retrieved successfully for last transaction");
				TransactionReceiptId txnRcptIdCriteria = new TransactionReceiptId();
				txnRcptIdCriteria.setReceiptType(ServiceConstants.RCPT_SALE_STORE);
				txnRcptIdCriteria.setBusinessDate(txnIdCriteria.getBusinessDate());
				txnRcptIdCriteria.setLocationId(txnIdCriteria.getLocationId());
				txnRcptIdCriteria.setRegister(txnIdCriteria.getRegister());
				txnRcptIdCriteria.setTransactionSeq(txnNo);
				txnReceipt = this.txnReceiptRepository.findOne(txnRcptIdCriteria);
				logger.info("The transaction receipt details has been retrieved successfully");
			} else {
				logger.info("There was no transaction found for the day before now()");
			}
		} else {
			logger.info("There was no transaction found for the day before now()");
		}
		return txnReceipt;
	}

	@Override
	public Boolean isTransactionAlllowed(Integer locationId, Integer registerId) {
		Boolean result = Boolean.FALSE;

		Set<String> txnTypes = new HashSet<>();
		txnTypes.add(ServiceConstants.TXN_CLOSE_STORE);
		txnTypes.add(ServiceConstants.TXN_OPEN_STORE);
		Transaction storeTxn = this.searchTxnByCriteria(locationId, txnTypes);
		if (storeTxn != null && storeTxn.getTxnType().equals(ServiceConstants.TXN_OPEN_STORE)) {
			txnTypes = new HashSet<>();
			txnTypes.add(ServiceConstants.TXN_CLOSE_REGISTER);
			txnTypes.add(ServiceConstants.TXN_OPEN_REGISTER);
			Map<Integer, Transaction> regTxns = this.searchRegisterTxnByCriteria(locationId, txnTypes);
			if (regTxns != null && !regTxns.isEmpty()) {
				Transaction regTxn = regTxns.get(registerId);
				if (regTxn != null && regTxn.getTxnType().equals(ServiceConstants.TXN_OPEN_REGISTER)) {
					result = Boolean.TRUE;
				} else {
					result = Boolean.FALSE;
				}
			} else {
				result = Boolean.FALSE;
			}

		}

		logger.info("The location {} and register {} has been validated if they are in OPEN status", locationId, registerId);

		return result;
	}

}
