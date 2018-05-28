/**
 * 
 */
package com.punj.app.ecommerce.services;

import com.punj.app.ecommerce.domains.transaction.NoSaleTransaction;
import com.punj.app.ecommerce.domains.transaction.Transaction;
import com.punj.app.ecommerce.domains.transaction.ids.TransactionId;

/**
 * @author admin
 *
 */
public interface NoSaleService {

	public NoSaleTransaction saveNoSaleTxn(Transaction txnHeaderDetails, NoSaleTransaction txnDetails);

	public NoSaleTransaction retrieveNoSaleTxn(TransactionId txnId);

}
