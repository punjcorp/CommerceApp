/**
 * 
 */
package com.punj.app.ecommerce.services;

import com.punj.app.ecommerce.domains.transaction.NoSaleTransaction;

/**
 * @author admin
 *
 */
public interface NoSaleService {

	public NoSaleTransaction saveNoSaleTxn(NoSaleTransaction txnDetails);

}
