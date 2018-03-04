/**
 * 
 */
package com.punj.app.ecommerce.services.common;

import java.math.BigInteger;
import java.util.List;

import com.punj.app.ecommerce.domains.common.Location;
import com.punj.app.ecommerce.domains.tender.Tender;
import com.punj.app.ecommerce.services.common.dtos.LocationDTO;
import com.punj.app.ecommerce.services.common.dtos.RegisterDTO;

/**
 * @author admin
 *
 */
public interface CommonService {

	public List<Location> retrieveAllLocations();

	public LocationDTO retrieveLocationWithDailyStatus();

	public RegisterDTO retrieveRegisterWithDailyStatus(Integer locationId);

	public List<Tender> retrieveAllTenders(Integer locationId);
	
	public List<Tender> retrieveTendersForReconcilation(Integer locationId);

	public BigInteger getId(String name);

	public BigInteger getNewTxn(Integer locationId, Integer register);

	public BigInteger resetId(String name);

}
