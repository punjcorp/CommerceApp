/**
 * 
 */
package com.punj.app.ecommerce.services;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

import com.punj.app.ecommerce.services.dtos.SaleItem;

/**
 * @author admin
 *
 */
public interface SaleItemService {

	public SaleItem getItem(BigInteger itemId, Integer locationId)  throws UnsupportedEncodingException;

}
