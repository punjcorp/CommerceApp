/**
 * 
 */
package com.punj.app.ecommerce.services;

import java.math.BigInteger;
import java.util.List;

import com.punj.app.ecommerce.domains.payment.AccountHead;
import com.punj.app.ecommerce.domains.payment.AccountJournal;

/**
 * @author admin
 *
 */
public interface PaymentAccountService {

	public AccountHead retrievePaymentAccount(String accountType, BigInteger accountId, Integer locationId);
	
	public AccountHead retrievePaymentAccount(Integer accountId);

	public List<AccountHead> setupPaymentAccount(String accountType, BigInteger accountId, String username);

	public AccountJournal savePayment(AccountJournal journalDetails, Integer locationId, String username);

	public List<AccountHead> updateAccountsDue(List<AccountHead> accountHead, String username);
	
	public AccountHead updateAccountDue(AccountHead accountHead, String username);

	public AccountHead recordOrderAmount(AccountJournal journalDetails, BigInteger supplierId, Integer locationId, String username);
}
