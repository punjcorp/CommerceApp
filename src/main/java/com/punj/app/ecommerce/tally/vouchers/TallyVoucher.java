/**
 * 
 */
package com.punj.app.ecommerce.tally.vouchers;

import com.punj.app.ecommerce.services.dtos.transaction.TransactionDTO;

/**
 * @author amitpunj
 *
 */
public interface TallyVoucher {

	public Object create(TransactionDTO txnDetails);

}
