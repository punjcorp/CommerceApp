/**
 * 
 */
package com.punj.app.ecommerce.services.common.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.punj.app.ecommerce.domains.common.Location;
import com.punj.app.ecommerce.repositories.common.LocationRepository;
import com.punj.app.ecommerce.services.common.CommonService;

/**
 * @author admin
 *
 */
@Service
public class CommonServiceImpl implements CommonService {

	private static final Logger logger = LogManager.getLogger();
	private LocationRepository locationRepository;

	/**
	 * @return the locationRepository
	 */
	public LocationRepository getLocationRepository() {
		return locationRepository;
	}

	/**
	 * @param locationRepository
	 *            the locationRepository to set
	 */
	@Autowired
	public void setLocationRepository(LocationRepository locationRepository) {
		this.locationRepository = locationRepository;
	}

	@Override
	public List<Location> retrieveAllLocations() {
		logger.info("The method to retrieve all the locations has been called");
		return this.locationRepository.findAll();
	}

}
