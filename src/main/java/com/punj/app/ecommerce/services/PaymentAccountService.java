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

	public List<AccountHead> setupPaymentAccount(String accountType, BigInteger accountId, String username);

	public AccountJournal savePayment(AccountJournal journalDetails, Integer locationId);

	public List<AccountHead> updateAccountsDue(List<AccountHead> accountHead, String username);
	
	public AccountHead updateAccountDue(AccountHead accountHead, String username);
	
}
