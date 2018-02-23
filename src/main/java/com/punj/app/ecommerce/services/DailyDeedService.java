/**
 * 
 */
package com.punj.app.ecommerce.services;

import com.punj.app.ecommerce.services.dtos.StoreOpenTransaction;

/**
 * @author admin
 *
 */
public interface DailyDeedService {

	public Boolean saveStoreOpenTxn(StoreOpenTransaction storeOpenDetails, String username);

}
