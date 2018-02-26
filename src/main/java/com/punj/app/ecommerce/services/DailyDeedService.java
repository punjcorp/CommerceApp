/**
 * 
 */
package com.punj.app.ecommerce.services;

import com.punj.app.ecommerce.services.dtos.DailyOpenTransaction;

/**
 * @author admin
 *
 */
public interface DailyDeedService {

	public Boolean saveStoreOpenTxn(DailyOpenTransaction storeOpenDetails, String username);

	public Boolean saveRegisterOpenTxn(DailyOpenTransaction storeOpenDetails, String username);

}
