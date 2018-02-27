/**
 * 
 */
package com.punj.app.ecommerce.services.common.impl;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.punj.app.ecommerce.domains.common.IdGenerator;
import com.punj.app.ecommerce.domains.common.Location;
import com.punj.app.ecommerce.domains.tender.Tender;
import com.punj.app.ecommerce.domains.transaction.Transaction;
import com.punj.app.ecommerce.repositories.common.IdGeneratorRepository;
import com.punj.app.ecommerce.repositories.common.LocationRepository;
import com.punj.app.ecommerce.repositories.tax.LocationTaxRepository;
import com.punj.app.ecommerce.repositories.tender.TenderRepository;
import com.punj.app.ecommerce.services.TransactionService;
import com.punj.app.ecommerce.services.common.CommonService;
import com.punj.app.ecommerce.services.common.ServiceConstants;
import com.punj.app.ecommerce.services.common.dtos.LocationDTO;

/**
 * @author admin
 *
 */
@Service
public class CommonServiceImpl implements CommonService {

	private static final Logger logger = LogManager.getLogger();
	private LocationRepository locationRepository;
	private IdGeneratorRepository idGenRepository;
	private TenderRepository tenderRepository;
	private TransactionService txnService;

	
	/**
	 * @param txnService
	 *            the txnService to set
	 */
	@Autowired
	public void setTransactionService(TransactionService txnService) {
		this.txnService = txnService;
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
	 * @param idGenRepository
	 *            the idGenRepository to set
	 */
	@Autowired
	public void setIdGenRepository(IdGeneratorRepository idGenRepository) {
		this.idGenRepository = idGenRepository;
	}

	@Override
	public List<Location> retrieveAllLocations() {
		logger.info("The method to retrieve all the locations has been called");
		return this.locationRepository.findAll();
	}
	
	@Override
	public LocationDTO retrieveLocationWithDailyStatus() {
		LocationDTO locationDTO=new LocationDTO();
		logger.info("The method to retrieve all the locations with last txn Status has been called");
		List<Location> locations=this.retrieveAllLocations();
		locationDTO.setLocations(locations);
		locationDTO.setLastTxnStatus(this.retrieveLocationsStoreTxnStatus(locations));
		logger.info("All the location details with daily status has been retrieved successfully");
		return locationDTO;
	}	
	
	
	private Map<Integer, Transaction> retrieveLocationsStoreTxnStatus(List<Location> locations) {
		Map<Integer, Transaction> locationLastTxnMap=new HashMap<>();
		Set<String> txnTypes=new HashSet<>();
		txnTypes.add(ServiceConstants.TXN_CLOSE_STORE);
		txnTypes.add(ServiceConstants.TXN_OPEN_STORE);
		Transaction txnDetails;
		Integer locationId;
		for(Location location:locations){
			locationId=location.getLocationId();
			txnDetails=this.txnService.searchTxnByCriteria(locationId, txnTypes);
			locationLastTxnMap.put(locationId, txnDetails);
		}
		logger.info("The last daily deed txn status for all location has been retrieved successfully");
		return locationLastTxnMap;
	}	

	@Override
	@Transactional
	public BigInteger getId(String name) {
		BigInteger result;
		IdGenerator idGen = this.idGenRepository.findOne(name);
		if (idGen != null) {
			idGen.setSeq(idGen.getSeq().add(BigInteger.ONE));
			idGen=this.idGenRepository.save(idGen);
			result = idGen.getSeq();
			logger.info("The sequence {} retrieved for key {} successfully", result, name);
		} else {
			idGen = new IdGenerator();
			idGen.setName(name);
			idGen.setSeq(new BigInteger("1"));
			idGen = this.idGenRepository.save(idGen);
			result = idGen.getSeq();
			logger.info("The new sequence has been generated now for the key {}.", name);
		}
		return result;
	}

	@Override
	public BigInteger resetId(String name) {

		IdGenerator idGen = this.idGenRepository.findOne(name);

		if (idGen != null) {
			this.idGenRepository.delete(name);
			logger.info("The old sequence has been deleted now");
		}
		idGen = new IdGenerator();
		idGen.setName(name);
		idGen.setSeq(new BigInteger("1"));
		idGen = this.idGenRepository.save(idGen);
		BigInteger result = idGen.getSeq();
		logger.info("The new sequence for key {} has been generated with reset count", name);

		return result;
	}

	@Override
	public List<Tender> retrieveAllTenders() {
		List<Tender> tenders = this.tenderRepository.findAll();
		logger.info("The {} tenders has been retrieved successfully ", tenders.size());
		return tenders;
	}

}
