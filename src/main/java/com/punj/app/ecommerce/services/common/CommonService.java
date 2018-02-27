/**
 * 
 */
package com.punj.app.ecommerce.services.common;

import java.math.BigInteger;
import java.util.List;

import com.punj.app.ecommerce.domains.common.Location;
import com.punj.app.ecommerce.domains.tender.Tender;
import com.punj.app.ecommerce.services.common.dtos.LocationDTO;

/**
 * @author admin
 *
 */
public interface CommonService {

	public List<Location> retrieveAllLocations();
	
	public LocationDTO retrieveLocationWithDailyStatus();

	public List<Tender> retrieveAllTenders();

	public BigInteger getId(String name);

	public BigInteger resetId(String name);

}
