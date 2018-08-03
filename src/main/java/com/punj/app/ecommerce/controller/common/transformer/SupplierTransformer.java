/**
 * 
 */
package com.punj.app.ecommerce.controller.common.transformer;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.punj.app.ecommerce.domains.payment.AccountHead;
import com.punj.app.ecommerce.domains.payment.AccountJournal;
import com.punj.app.ecommerce.domains.payment.JournalTender;
import com.punj.app.ecommerce.domains.payment.ids.JournalTenderId;
import com.punj.app.ecommerce.domains.supplier.Supplier;
import com.punj.app.ecommerce.domains.tender.Tender;
import com.punj.app.ecommerce.domains.user.Address;
import com.punj.app.ecommerce.models.common.AddressBean;
import com.punj.app.ecommerce.models.financials.AccountDTO;
import com.punj.app.ecommerce.models.financials.AccountHeadBean;
import com.punj.app.ecommerce.models.financials.AccountJournalBean;
import com.punj.app.ecommerce.models.financials.JournalTenderBean;
import com.punj.app.ecommerce.models.supplier.SupplierBean;

/**
 * @author admin
 *
 */
public class SupplierTransformer {

	private static final Logger logger = LogManager.getLogger();

	private SupplierTransformer() {
		throw new IllegalStateException("SupplierTransformer class");
	}

	/**
	 * This method is used to set the Domain objects so that the data can be persisted to the database
	 * 
	 * @param supplier
	 * @return The supplier bean object
	 */
	public static SupplierBean transformSupplier(Supplier supplier) {
		SupplierBean supplierBean = new SupplierBean();

		supplierBean.setSupplierId(supplier.getSupplierId());
		supplierBean.setName(supplier.getName());
		supplierBean.setEmail(supplier.getEmail());
		supplierBean.setPhone1(supplier.getPhone1());
		supplierBean.setPhone2(supplier.getPhone2());
		supplierBean.setGstNo(supplier.getGstNo());
		supplierBean.setCreatedBy(supplier.getCreatedBy());
		supplierBean.setCreatedDate(supplier.getCreatedDate());
		supplierBean.setModifiedBy(supplier.getModifiedBy());
		supplierBean.setModifiedDate(supplier.getModifiedDate());

		List<AddressBean> supplierAddresses = CommonMVCTransformer.transformAddresses(supplier.getAddresses());
		supplierBean.setAddresses(supplierAddresses);

		supplierBean.setPrimaryAddressIndex(CommonMVCTransformer.getPrimaryAddressIndex(supplierBean.getAddresses()));
		if (supplierBean.getPrimaryAddressIndex() > -1)
			supplierBean.setPrimaryAddress(CommonMVCTransformer.getPrimaryAddress(supplier.getAddresses()));

		logger.info("The supplier details has been updated in bean object now");
		return supplierBean;
	}

	public static Supplier transformSupplierBean(SupplierBean supplierBean) {
		Supplier supplier = new Supplier();

		supplier.setSupplierId(supplierBean.getSupplierId());
		supplier.setName(supplierBean.getName());
		supplier.setEmail(supplierBean.getEmail());
		supplier.setPhone1(supplierBean.getPhone1());
		supplier.setPhone2(supplierBean.getPhone2());
		supplier.setGstNo(supplierBean.getGstNo());

		supplier.setCreatedBy(supplierBean.getCreatedBy());
		supplier.setCreatedDate(supplierBean.getCreatedDate());

		List<AddressBean> supplierAddresses = supplierBean.getAddresses();
		AddressBean primaryAddress = supplierAddresses.get(supplierBean.getPrimaryAddressIndex());
		primaryAddress.setPrimary("Y");

		List<Address> supplierAddressList = CommonMVCTransformer.transformAddressList(supplierAddresses);
		supplier.setAddresses(supplierAddressList);
		logger.info("The supplier details has been updated in domain object now");
		return supplier;
	}

	public static List<SupplierBean> transformSuppliers(List<Supplier> supplierList, Map<BigInteger, List<AccountHead>> supplierAccounts) {

		List<SupplierBean> supplierBeanList = new ArrayList<>(supplierList.size());
		SupplierBean supplierBean;
		Boolean isSupplierAccountExisting=Boolean.FALSE;
		BigInteger supplierId=null;
		String supplierIdStr=null;
		List<AccountHeadBean> supplierAccountBeans=null;
		List<AccountHead> supplierAccountList=null;
		AccountHead supplierAccount=null;
		AccountHeadBean supplierAccountBean=null;
		if(supplierAccounts!=null && !supplierAccounts.isEmpty()) {
			isSupplierAccountExisting=Boolean.TRUE;
		}
		for (Supplier supplier : supplierList) {
			supplierBean = SupplierTransformer.transformSupplier(supplier);
			if(isSupplierAccountExisting) {
				supplierIdStr=supplier.getSupplierId().toString();
				supplierId=new BigInteger(supplierIdStr);
				supplierAccountList=supplierAccounts.get(supplierId);
				if(supplierAccountList!=null && !supplierAccountList.isEmpty()) {
					supplierAccountBeans=SupplierTransformer.transformAccountHeads(supplierAccountList, supplier.getName());
					supplierBean.setSupplierAccounts(supplierAccountBeans);
				}
			}
			supplierBeanList.add(supplierBean);
		}
		logger.info("The transformed supplier list details has been supplier bean list");
		return supplierBeanList;
	}

	public static List<SupplierBean> transformSuppliers(List<Supplier> supplierList) {

		List<SupplierBean> supplierBeanList = new ArrayList<>(supplierList.size());
		SupplierBean supplierBean;
		for (Supplier supplier : supplierList) {
			supplierBean = SupplierTransformer.transformSupplier(supplier);
			supplierBeanList.add(supplierBean);
		}
		logger.info("The transformed supplier list details has been supplier bean list");
		return supplierBeanList;
	}

	public static AccountDTO transformAccountHead(AccountHead paymentAccount) {
		AccountDTO accountDTO = new AccountDTO();
		accountDTO.setAccountId(paymentAccount.getAccountId());
		accountDTO.setEntityId(paymentAccount.getEntityId());
		accountDTO.setEntityType(paymentAccount.getEntityType());
		accountDTO.setDueAmt(paymentAccount.getDueAmount());
		accountDTO.setAdvanceAmt(paymentAccount.getAdvanceAmount());
		logger.info("The account head details has beem transformed successfully");
		return accountDTO;
	}

	public static List<AccountJournalBean> transformAccountJournals(List<AccountJournal> accountJournals, Map<Integer, Tender> tenderMap) {
		
		List<AccountJournalBean> accountJournalBeans=new ArrayList<>(accountJournals.size());
		AccountJournalBean accountJournalBean=null;
		
		for(AccountJournal accountJournal:accountJournals) {
			accountJournalBean=SupplierTransformer.transformAccountJournalBean(accountJournal, null, tenderMap);
			accountJournalBeans.add(accountJournalBean);
		}
		logger.info("The account journal details list has been transformed successfully");
		return accountJournalBeans;
		
	}
	
	public static AccountJournalBean transformAccountJournalBean(AccountJournal accountJournal, String username, Map<Integer, Tender> tenderMap) {
		AccountJournalBean journalBean = new AccountJournalBean();
		journalBean.setAccountId(accountJournal.getAccountId());
		journalBean.setAmount(accountJournal.getAmount());
		journalBean.setRemarks(accountJournal.getComments());
		journalBean.setCreatedBy(accountJournal.getCreatedBy());
		journalBean.setPrintedBy(journalBean.getCreatedBy());
		journalBean.setCreatedDate(accountJournal.getCreatedDate());
		journalBean.setJournalId(accountJournal.getJournalId());
		journalBean.setJournalType(accountJournal.getJournalType());

		List<JournalTenderBean> journalTenders = SupplierTransformer.transformJournalTender(accountJournal.getJournalTenders(), tenderMap);
		journalBean.setPaymentTenders(journalTenders);

		logger.info("The account journal details has been transformed successfully");

		return journalBean;
	}

	public static List<JournalTenderBean> transformJournalTender(List<JournalTender> tenders, Map<Integer, Tender> tenderMap) {
		List<JournalTenderBean> journalTenderBeans = new ArrayList<>();
		JournalTenderBean journalTenderBean = null;
		Tender tender = null;

		int counter = 1;

		for (JournalTender journalTender : tenders) {
			journalTenderBean = new JournalTenderBean();
			tender = tenderMap.get(journalTender.getJournalTenderId().getTenderId());
			if (tender != null) {
				journalTenderBean.setTenderId(tender.getTenderId());
				journalTenderBean.setTenderName(tender.getName());
			}
			journalTenderBean.setSeqNo(counter);
			counter++;

			journalTenderBean.setAccountNo(journalTender.getAccountNo());
			journalTenderBean.setTenderAmount(journalTender.getAmount());
			journalTenderBean.setBankBranch(journalTender.getBankBranch());
			journalTenderBean.setBankName(journalTender.getBankName());
			journalTenderBean.setCreatedBy(journalTender.getCreatedBy());
			journalTenderBean.setCreatedDate(journalTender.getCreatedDate());
			journalTenderBean.setDescription(journalTender.getDescription());

			journalTenderBeans.add(journalTenderBean);

		}

		logger.info("The journal tender details has been transformed successfully");
		return journalTenderBeans;
	}

	public static AccountJournal transformAccountJournal(AccountJournalBean journalBean, String username) {
		AccountJournal accountJournal = new AccountJournal();
		accountJournal.setAccountId(journalBean.getAccountId());
		accountJournal.setAmount(journalBean.getAmount());
		accountJournal.setComments(journalBean.getRemarks());
		accountJournal.setCreatedBy(username);
		accountJournal.setCreatedDate(LocalDateTime.now());
		accountJournal.setJournalId(journalBean.getJournalId());
		accountJournal.setJournalType(journalBean.getJournalType());

		List<JournalTender> journalTenders = SupplierTransformer.transformJournalTender(journalBean.getPaymentTenders(), username, accountJournal);
		accountJournal.setJournalTenders(journalTenders);

		logger.info("The account journal details has been transformed successfully");

		return accountJournal;
	}

	public static List<AccountHeadBean> transformAccountHeads(List<AccountHead> accountHeads, String entityName) {
		List<AccountHeadBean> accountHeadBeans=new ArrayList<>();
		AccountHeadBean accountHeadBean=null;
		for(AccountHead accountHead: accountHeads) {
			accountHeadBean=SupplierTransformer.transformAccountHead(accountHead, entityName);
			accountHeadBeans.add(accountHeadBean);
		}
		logger.info("The account head detail list has been transformed successfully");
		
		return accountHeadBeans;
	}
	
	public static AccountHeadBean transformAccountHead(AccountHead accountHead, String entityName) {
		AccountHeadBean accountHeadBean = new AccountHeadBean();
		accountHeadBean.setAccountId(accountHead.getAccountId());
		accountHeadBean.setAdvanceAmt(accountHead.getAdvanceAmount());
		accountHeadBean.setCreatedBy(accountHead.getCreatedBy());
		accountHeadBean.setCreatedDate(accountHead.getCreatedDate());
		accountHeadBean.setDueAmt(accountHead.getDueAmount());
		accountHeadBean.setEntityId(accountHead.getEntityId());
		accountHeadBean.setEntityName(entityName);
		accountHeadBean.setEntityType(accountHead.getEntityType());
		accountHeadBean.setLocationId(accountHead.getLocationId());
		accountHeadBean.setModifiedBy(accountHead.getModifiedBy());
		accountHeadBean.setModifiedDate(accountHead.getModifiedDate());

		logger.info("The account head details has been transformed successfully");

		return accountHeadBean;
	}

	public static List<JournalTender> transformJournalTender(List<JournalTenderBean> tenderBeans, String username, AccountJournal accountJournal) {
		List<JournalTender> journalTenders = new ArrayList<>();
		JournalTender journalTender = null;
		JournalTenderId journalTenderId = null;

		for (JournalTenderBean tenderBean : tenderBeans) {
			journalTender = new JournalTender();
			journalTenderId = new JournalTenderId();

			journalTender.setJournalTenderId(journalTenderId);
			journalTenderId.setAccountJournal(accountJournal);
			journalTenderId.setTenderId(tenderBean.getTenderId());
			journalTender.setAccountNo(tenderBean.getAccountNo());
			journalTender.setAmount(tenderBean.getTenderAmount());
			journalTender.setBankBranch(tenderBean.getBankBranch());
			journalTender.setBankName(tenderBean.getBankName());
			journalTender.setCreatedBy(username);
			journalTender.setCreatedDate(LocalDateTime.now());
			journalTender.setDescription(tenderBean.getDescription());

			journalTenders.add(journalTender);

		}

		logger.info("The journal tender details has been transformed successfully");
		return journalTenders;
	}

}
