/**
 * 
 */
package com.punj.app.ecommerce.services.common.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.punj.app.ecommerce.domains.common.Location;
import com.punj.app.ecommerce.domains.common.Register;
import com.punj.app.ecommerce.domains.common.ids.RegisterId;
import com.punj.app.ecommerce.domains.finance.LocationSafe;
import com.punj.app.ecommerce.domains.tax.TaxLocation;
import com.punj.app.ecommerce.domains.tax.TaxLocationMap;
import com.punj.app.ecommerce.domains.tax.ids.TaxLocationMapId;
import com.punj.app.ecommerce.domains.tender.Tender;
import com.punj.app.ecommerce.domains.user.Password;
import com.punj.app.ecommerce.domains.user.Role;
import com.punj.app.ecommerce.domains.user.UserRole;
import com.punj.app.ecommerce.domains.user.ids.UserRoleId;
import com.punj.app.ecommerce.repositories.common.LocationRepository;
import com.punj.app.ecommerce.repositories.common.RegisterRepository;
import com.punj.app.ecommerce.repositories.common.StateRepository;
import com.punj.app.ecommerce.repositories.finance.LocationSafeRepository;
import com.punj.app.ecommerce.repositories.tax.TaxLocationMapRepository;
import com.punj.app.ecommerce.repositories.tax.TaxLocationRepository;
import com.punj.app.ecommerce.repositories.tender.TenderRepository;
import com.punj.app.ecommerce.services.UserService;
import com.punj.app.ecommerce.services.common.ConfigService;
import com.punj.app.ecommerce.services.common.ServiceConstants;
import com.punj.app.ecommerce.services.common.SetupService;

/**
 * @author admin
 *
 */
@Service
public class SetupServiceImpl implements SetupService {

	private static final Logger logger = LogManager.getLogger();
	private LocationRepository locationRepository;
	private TenderRepository tenderRepository;
	private StateRepository stateRepository;
	private TaxLocationRepository taxLocationRepository;
	private TaxLocationMapRepository taxLocationMapRepository;
	private LocationSafeRepository locationSafeRepository;
	private RegisterRepository registerRepository;
	private UserService userService;
	private ConfigService configService;

	/**
	 * @param configService
	 *            the configService to set
	 */
	@Autowired
	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}

	/**
	 * @param registerRepository
	 *            the registerRepository to set
	 */
	@Autowired
	public void setRegisterRepository(RegisterRepository registerRepository) {
		this.registerRepository = registerRepository;
	}

	/**
	 * @param locationSafeRepository
	 *            the locationSafeRepository to set
	 */
	@Autowired
	public void setLocationSafeRepository(LocationSafeRepository locationSafeRepository) {
		this.locationSafeRepository = locationSafeRepository;
	}

	/**
	 * @param taxLocationMapRepository
	 *            the taxLocationMapRepository to set
	 */
	@Autowired
	public void setTaxLocationMapRepository(TaxLocationMapRepository taxLocationMapRepository) {
		this.taxLocationMapRepository = taxLocationMapRepository;
	}

	/**
	 * @param taxLocationRepository
	 *            the taxLocationRepository to set
	 */
	@Autowired
	public void setTaxLocationRepository(TaxLocationRepository taxLocationRepository) {
		this.taxLocationRepository = taxLocationRepository;
	}

	/**
	 * @param stateRepository
	 *            the stateRepository to set
	 */
	@Autowired
	public void setStateRepository(StateRepository stateRepository) {
		this.stateRepository = stateRepository;
	}

	/**
	 * @return the locationRepository
	 */
	public LocationRepository getLocationRepository() {
		return locationRepository;
	}

	/**
	 * @param tenderRepository
	 *            the tenderRepository to set
	 */
	@Autowired
	public void setTenderRepository(TenderRepository tenderRepository) {
		this.tenderRepository = tenderRepository;
	}

	/**
	 * @param locationRepository
	 *            the locationRepository to set
	 */
	@Autowired
	public void setLocationRepository(LocationRepository locationRepository) {
		this.locationRepository = locationRepository;
	}

	/**
	 * @param userService
	 *            the userService to set
	 */
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	@Transactional
	public Boolean saveInitialSetup(Location location, String[] selectedTenders, Password password) {
		Boolean result = Boolean.TRUE;
		location.setCreatedBy("ADMIN");
		location.setCreatedDate(LocalDateTime.now());
		location.setStatus(ServiceConstants.STATUS_APPROVED);
		location = this.locationRepository.save(location);
		if (location != null) {
			Integer locationId = location.getLocationId();
			logger.info("The location details has been saved successfully");

			Register register = this.setupRegister(locationId);
			if (register != null) {
				logger.info("The register has been saved successfully");

			} else {
				logger.error("There was some issue while saving the register details!!!");
				result = Boolean.FALSE;
			}

			password = this.userService.updatePassword(null, password, password.getPassword(), "ADMIN");
			if (password != null) {
				logger.info("The admin user password has been saved successfully");

			} else {
				logger.error("There was some issue while saving the admin password!!!");
				result = Boolean.FALSE;
			}

			List<UserRole> userRoles = this.saveUserRoleDetails(locationId);
			if (userRoles != null && !userRoles.isEmpty()) {
				logger.info("The user roles has been saved successfully");

			} else {
				logger.error("There was some issue while saving the user roles!!!");
				result = Boolean.FALSE;
			}

			List<TaxLocationMap> taxLocationMaps = this.saveTaxLocationMapping(locationId);
			if (taxLocationMaps != null && !taxLocationMaps.isEmpty()) {
				logger.info("The tax location mapping for {} location was successful!", locationId);
			} else {
				logger.error("There was error while saving tax location mapping!!");
				result = Boolean.FALSE;
			}

			List<LocationSafe> locRepos = this.createLocationRepositeries(selectedTenders, locationId);
			if (locRepos != null && !locRepos.isEmpty()) {
				this.initiateSetup(locationId);
				logger.info("The location repositeries creation for {} location was successful!", locationId);
			} else {
				logger.error("There was error while creating location repositories!!");
				result = Boolean.FALSE;
			}

		} else {
			logger.error("There was some issue while saving the location details during initial setup!!!");
			result = Boolean.FALSE;
		}

		return result;
	}

	private Register setupRegister(Integer locationId) {
		Register register = new Register();
		register.setCreatedBy("ADMIN");
		register.setCreatedDate(LocalDateTime.now());

		register.setName("Register 1");

		RegisterId registerId = new RegisterId();
		registerId.setRegister(1);
		registerId.setLocationId(locationId);

		register.setRegisterId(registerId);

		register = this.registerRepository.save(register);
		logger.info("The initial register has been setup successfully");

		return register;

	}

	private List<LocationSafe> createLocationRepositeries(String[] tenders, Integer locationId) {
		List<LocationSafe> locRepos = null;
		LocationSafe locRepo = null;
		Map<String, Tender> tenderMap = null;
		
		List<Tender> tenderList = this.tenderRepository.findAll();
		if (tenderList != null && !tenderList.isEmpty()) {
			tenderMap = new HashMap<>();
			for (Tender tender : tenderList) {
				tenderMap.put(tender.getType(), tender);
			}
			locRepos = new ArrayList<>();
			
			List<String> tendersList=new ArrayList<>(Arrays.asList(tenders));
			tendersList.add(ServiceConstants.TENDER_CHANGE_CODE);

			for (String tenderCode : tendersList) {
				Tender tender = tenderMap.get(tenderCode);
				if (tender != null) {
					locRepo = new LocationSafe();
					locRepo.setCreatedBy("ADMIN");
					locRepo.setCreatedDate(LocalDateTime.now());
					locRepo.setLocationId(locationId);
					if (tender.getName().equals(ServiceConstants.TENDER_CASH))
						locRepo.setReconcilationFlag(Boolean.TRUE);
					else
						locRepo.setReconcilationFlag(Boolean.FALSE);
					// This has to be dynamic at some point..for now proceed with hardcode
					locRepo.setRepositoryId(1);
					locRepo.setTenderId(tender.getTenderId());

					locRepos.add(locRepo);
				}
			}
			locRepos = this.locationSafeRepository.save(locRepos);

			logger.info("The location safe records has been created successfully!!");
		}

		return locRepos;

	}

	private List<UserRole> saveUserRoleDetails(Integer locationId) {
		List<UserRole> userRoles = new ArrayList<>();
		UserRole adminRole = null;
		UserRoleId adminRoleId = null;
		List<Role> roles = this.userService.getAllUserRoles();
		if (roles != null && !roles.isEmpty()) {
			for (Role role : roles) {
				adminRole = new UserRole();
				adminRoleId = new UserRoleId();
				adminRoleId.setLocationId(locationId);
				adminRoleId.setRole(role);
				adminRoleId.setUsername("admin");

				adminRole.setUserRoleId(adminRoleId);
				adminRole.setCreatedBy("ADMIN");
				adminRole.setCreatedDate(LocalDateTime.now());
				adminRole.setBeginDate(LocalDateTime.now());
				adminRole.setEndDate(LocalDateTime.now());

				userRoles.add(adminRole);
			}
		}
		userRoles = this.userService.addUserRoles(userRoles);
		logger.info("The user roles has been saved successfully!!");

		return userRoles;
	}

	private List<TaxLocationMap> saveTaxLocationMapping(Integer locationId) {
		List<TaxLocationMap> taxLocationMaps = null;
		List<TaxLocation> taxLocations = this.taxLocationRepository.findAll();
		TaxLocationMap taxLocationMap = null;
		TaxLocationMapId taxLocationMapId = null;
		if (taxLocations != null && !taxLocations.isEmpty()) {
			taxLocationMaps = new ArrayList<>(taxLocations.size());
			for (TaxLocation taxLocation : taxLocations) {
				taxLocationMap = new TaxLocationMap();
				taxLocationMapId = new TaxLocationMapId();
				taxLocationMapId.setLocationId(locationId);
				taxLocationMapId.setTaxLocationId(taxLocation.getTaxLocationId());

				taxLocationMap.setTaxLocationMapId(taxLocationMapId);

				taxLocationMaps.add(taxLocationMap);

			}
			taxLocationMaps = this.taxLocationMapRepository.save(taxLocationMaps);

			logger.info("The tax location mapping processing has completed");

		}

		return taxLocationMaps;
	}

	private void initiateSetup(Integer locationId) {
		LocalDate currentDate = LocalDate.now();
		String utilString = RandomStringUtils.random(142, Boolean.TRUE, Boolean.TRUE);
		String monthDay = String.format("%02d", currentDate.getDayOfMonth());
		String month = String.format("%02d", currentDate.getMonthValue());
		int year = currentDate.getYear();

		StringBuffer finalResult = new StringBuffer();
		finalResult.append(utilString.substring(0, 27));
		finalResult.append(monthDay);
		finalResult.append(utilString.substring(28, 37));
		finalResult.append(month);
		finalResult.append(utilString.substring(38, 119));
		finalResult.append(year);
		finalResult.append(utilString.substring(120, 141));

		Boolean result = this.configService.updateAppConfigByKey(ServiceConstants.APP_CONF_UTIL_STRING, finalResult.toString());
		logger.info("The utility string has been updated successfully");

		result = this.configService.updateAppConfigByKey(ServiceConstants.APP_DEFAULT_LOCATION, locationId.toString());
		logger.info("The default location for installation has been successful!");

	}

	public Boolean isSetupGood() {
		String utilString = this.configService.getAppConfigByKey(ServiceConstants.APP_CONF_UTIL_STRING);
		if (StringUtils.isNotBlank(utilString)) {
			LocalDate currentDate = LocalDate.now();
			LocalDate originalDate = LocalDate.of(new Integer(utilString.substring(121, 125)).intValue(), new Integer(utilString.substring(38, 40)).intValue(),
					new Integer(utilString.substring(27, 29)).intValue());
			return currentDate.isBefore(originalDate) || currentDate.isEqual(originalDate);
		}

		return Boolean.FALSE;
	}

}
