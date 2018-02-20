/**
 * 
 */
package com.punj.app.ecommerce.controller.common.transformer;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.punj.app.ecommerce.domains.common.Location;
import com.punj.app.ecommerce.models.common.LocationBean;
import com.punj.app.ecommerce.models.price.PriceBean;

/**
 * @author admin
 *
 */
public class CommonMVCTransformer {

	private static final Logger logger = LogManager.getLogger();

	private CommonMVCTransformer() {
		throw new IllegalStateException("CommonBeanTransformer class");
	}

	public static Map<BigInteger, Integer> transformSelectedIds(List<String> selectedIds) {
		Map<BigInteger, Integer> idIndex = new HashMap<>(selectedIds.size());
		String[] splittedData = null;
		for (String id : selectedIds) {
			splittedData = id.split("_");
			idIndex.put(new BigInteger(splittedData[0]), new Integer(splittedData[1]));
		}

		logger.info("The selected ids and list index from management page has been seperated");
		return idIndex;
	}

	public static List<LocationBean> transformLocationListDomainPartially(List<Location> locationList) {
		LocationBean locationBean = null;
		List<LocationBean> locations = null;
		if (locationList != null && !locationList.isEmpty()) {
			locations = new ArrayList<>(locationList.size());
			for (Location location : locationList) {
				locationBean=CommonMVCTransformer.transformLocationDomainPartially(location);
				locations.add(locationBean);
			}
		}
		logger.info("All the locations from list has been transformed into location bean list");
		return locations;
	}
	
	public static LocationBean transformLocationDomainPartially(Location location) {
		LocationBean locationBean=new LocationBean();
		locationBean.setLocationId(location.getLocationId());
		locationBean.setLocationType(location.getStatus());
		locationBean.setName(location.getName());
		logger.info("The locations details has been partially transformed into location bean");		
		return locationBean;
	}
	
}
