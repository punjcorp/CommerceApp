/**
 * 
 */
package com.punj.app.ecommerce.services;

import com.punj.app.ecommerce.domains.payment.AccountHead;
import com.punj.app.ecommerce.services.dtos.transaction.TransactionDTO;

/**
 * @author admin
 *
 */
public interface TransactionFinanceService {

	public void postFinancesReversal(AccountHead accountHead, TransactionDTO txnDTOBeforeChange, String username);

	public void updateFinanceDetails(AccountHead accountHead, TransactionDTO txnDTO, String username);

}
