/**
 * 
 */
package com.punj.app.ecommerce.controller.common.transformer;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.punj.app.ecommerce.domains.payment.AccountHead;
import com.punj.app.ecommerce.domains.payment.AccountJournal;
import com.punj.app.ecommerce.domains.payment.JournalTender;
import com.punj.app.ecommerce.domains.payment.ids.JournalTenderId;
import com.punj.app.ecommerce.domains.supplier.Supplier;
import com.punj.app.ecommerce.domains.user.Address;
import com.punj.app.ecommerce.models.common.AddressBean;
import com.punj.app.ecommerce.models.financials.AccountDTO;
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

		List<Address> supplierAddressList = supplier.getAddresses();
		List<AddressBean> supplierAddresses = new ArrayList<>();

		AddressBean addressBean = null;
		if (supplierAddressList != null && !supplierAddressList.isEmpty()) {
			for (Address address : supplierAddressList) {

				addressBean = new AddressBean();

				addressBean.setAddressId(address.getAddressId());
				addressBean.setPrimary(address.getPrimary());
				addressBean.setAddress1(address.getAddress1());
				addressBean.setAddress2(address.getAddress2());
				addressBean.setCity(address.getCity());
				addressBean.setState(address.getState());
				addressBean.setCountry(address.getCountry());
				addressBean.setPincode(address.getPincode());
				addressBean.setAddressType(address.getAddressType());

				supplierAddresses.add(addressBean);
			}
		}
		supplierBean.setAddresses(supplierAddresses);

		logger.info("The supplier details has been updated in bean object now");
		return supplierBean;
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

	public static AccountJournal transformAccountJournal(AccountJournalBean journalBean, String username) {
		AccountJournal accountJournal=new AccountJournal();
		accountJournal.setAccountId(journalBean.getAccountId());
		accountJournal.setAmount(journalBean.getAmount());
		accountJournal.setComments(journalBean.getRemarks());
		accountJournal.setCreatedBy(username);
		accountJournal.setCreatedDate(LocalDateTime.now());
		accountJournal.setJournalId(journalBean.getJournalId());
		accountJournal.setJournalType(journalBean.getJournalType());
		
		List<JournalTender> journalTenders=SupplierTransformer.transformJournalTender(journalBean.getPaymentTenders(),username,accountJournal);
		accountJournal.setJournalTenders(journalTenders);		
		
		logger.info("The account journal details has been transformed successfully");
		
		return accountJournal;
	}
	
	public static List<JournalTender> transformJournalTender(List<JournalTenderBean> tenderBeans, String username, AccountJournal accountJournal) {
		List<JournalTender> journalTenders=new ArrayList<>();
		JournalTender journalTender=null;
		JournalTenderId journalTenderId=null;
		
		for(JournalTenderBean tenderBean:tenderBeans) {
			journalTender=new JournalTender();
			journalTenderId=new JournalTenderId();
			
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
