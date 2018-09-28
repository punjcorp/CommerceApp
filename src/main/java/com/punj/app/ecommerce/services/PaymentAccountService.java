/**
 * 
 */
package com.punj.app.ecommerce.services;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.punj.app.ecommerce.domains.payment.AJReceipt;
import com.punj.app.ecommerce.domains.payment.AccountHead;
import com.punj.app.ecommerce.domains.payment.AccountJournal;

/**
 * @author admin
 *
 */
public interface PaymentAccountService {

	public AccountHead retrievePaymentAccount(String accountType, BigInteger accountId, Integer locationId);

	public AccountHead retrievePaymentAccount(Integer accountId);

	public Map<BigInteger, List<AccountHead>> retrieveAccounts(Set<BigInteger> entityIds, String entityType);

	public List<AccountHead> setupPaymentAccount(String accountType, BigInteger accountId, String username);

	public List<AccountJournal> retrievePaymentAccountJournals(Set<Integer> accountIds);

	public AccountHead setupPaymentAccount(AccountHead accountHead);

	public AccountJournal savePayment(AccountJournal journalDetails, String username);

	public BigInteger savePaymentReceipt(AJReceipt ajReceipt);

	public List<AccountHead> updateAccountsDue(List<AccountHead> accountHead, String username);

	public AccountHead updateAccountDue(AccountHead accountHead, String username);

	public AccountHead recordOrderAmount(AccountJournal journalDetails, BigInteger supplierId, Integer locationId, String username);
	
	public List<AccountJournal> retrieveSupplierPaymentsForProcessing();
	public List<AccountJournal> retrieveCustomerPaymentsForProcessing();
	
}
