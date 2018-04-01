/**
 * 
 */
package com.punj.app.ecommerce.services.common;

import java.math.BigInteger;
import java.util.List;

import com.punj.app.ecommerce.domains.common.Location;
import com.punj.app.ecommerce.domains.item.ItemLocationTax;
import com.punj.app.ecommerce.domains.tender.Tender;
import com.punj.app.ecommerce.services.common.dtos.LocationDTO;
import com.punj.app.ecommerce.services.common.dtos.ReasonDTO;
import com.punj.app.ecommerce.services.common.dtos.RegisterDTO;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
public interface CommonService {

	public Location retrieveLocationDetails(Integer locationId);

	public List<Location> retrieveAllLocations();

	public LocationDTO retrieveLocationWithDailyStatus();

	public RegisterDTO retrieveRegisterWithDailyStatus(Integer locationId);

	public List<Tender> retrieveAllTenders(Integer locationId);

	public List<Tender> retrieveTendersForReconcilation(Integer locationId);

	public BigInteger getId(String name);

	public BigInteger getNewTxn(Integer locationId, Integer register);

	public BigInteger resetId(String name);

	public ReasonDTO retrieveReasonCodes(String searchText, Pager pager);

	public ItemLocationTax retrieveItemDetails(Integer locationId, Integer supplierId, BigInteger itemId);

}
