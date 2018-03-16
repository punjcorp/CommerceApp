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
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.punj.app.ecommerce.domains.common.IdGenerator;
import com.punj.app.ecommerce.domains.common.Location;
import com.punj.app.ecommerce.domains.common.Register;
import com.punj.app.ecommerce.domains.common.ids.RegisterId;
import com.punj.app.ecommerce.domains.tender.Tender;
import com.punj.app.ecommerce.domains.transaction.Transaction;
import com.punj.app.ecommerce.repositories.common.IdGeneratorRepository;
import com.punj.app.ecommerce.repositories.common.LocationRepository;
import com.punj.app.ecommerce.repositories.common.RegisterRepository;
import com.punj.app.ecommerce.repositories.tender.TenderRepository;
import com.punj.app.ecommerce.services.TransactionService;
import com.punj.app.ecommerce.services.common.CommonService;
import com.punj.app.ecommerce.services.common.ServiceConstants;
import com.punj.app.ecommerce.services.common.dtos.LocationDTO;
import com.punj.app.ecommerce.services.common.dtos.RegisterDTO;

/**
 * @author admin
 *
 */
@Service
public class CommonServiceImpl implements CommonService {

	private static final Logger logger = LogManager.getLogger();
	private LocationRepository locationRepository;
	private RegisterRepository registerRepository;
	private IdGeneratorRepository idGenRepository;
	private TenderRepository tenderRepository;
	private TransactionService txnService;

	/**
	 * @param txnService
	 *            the txnService to set
	 */
	@Autowired
	public void setRegisterRepository(RegisterRepository registerRepository) {
		this.registerRepository = registerRepository;
	}

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
		LocationDTO locationDTO = new LocationDTO();
		logger.info("The method to retrieve all the locations with last txn Status has been called");
		List<Location> locations = this.retrieveAllLocations();
		locationDTO.setLocations(locations);
		locationDTO.setLastTxnStatus(this.retrieveLocationsStoreTxnStatus(locations));
		logger.info("All the location details with daily status has been retrieved successfully");
		return locationDTO;
	}

	private Map<Integer, Transaction> retrieveLocationsStoreTxnStatus(List<Location> locations) {
		Map<Integer, Transaction> locationLastTxnMap = new HashMap<>();
		Set<String> txnTypes = new HashSet<>();
		txnTypes.add(ServiceConstants.TXN_CLOSE_STORE);
		txnTypes.add(ServiceConstants.TXN_OPEN_STORE);
		Transaction txnDetails;
		Integer locationId;
		for (Location location : locations) {
			locationId = location.getLocationId();
			txnDetails = this.txnService.searchTxnByCriteria(locationId, txnTypes);
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
			idGen = this.idGenRepository.save(idGen);
			result = idGen.getSeq();
			logger.info("The sequence {} retrieved for key {} successfully", result, name);
		} else {
			result = this.resetId(name);
			logger.info("The new sequence has been generated now for the key {}.", name);
		}
		return result;
	}

	@Override
	public BigInteger resetId(String name) {
		IdGenerator idGen = new IdGenerator();
		idGen.setName(name);
		idGen.setSeq(BigInteger.ONE);
		idGen = this.idGenRepository.save(idGen);
		BigInteger result = idGen.getSeq();
		logger.info("The new sequence for key {} has been generated with reset count", name);

		return result;
	}

	@Override
	public List<Tender> retrieveAllTenders(Integer locationId) {
		List<Tender> tenders = this.tenderRepository.getTendersByLocation(locationId);
		logger.info("The valid {} tenders for location {} has been retrieved successfully ", tenders.size(), locationId);
		return tenders;
	}

	@Override
	public BigInteger getNewTxn(Integer locationId, Integer register) {
		BigInteger txnNo = this.getId(locationId + "_" + register + "_" + ServiceConstants.TXN_SEQ);
		logger.info("The {} txn number has been generated for {} location and {} register ", txnNo, locationId, register);
		return txnNo;
	}

	public void resetAllRegisterTxnSeq(Integer locationId) {

		logger.info("All the transaction sequences has been reset for the store now");
	}

	@Override
	public RegisterDTO retrieveRegisterWithDailyStatus(Integer locationId) {
		logger.info("The method to retrieve all the locations with last txn Status has been called");
		RegisterDTO registerDTO = new RegisterDTO();
		Register register = new Register();
		RegisterId registerId = new RegisterId();
		registerId.setLocationId(locationId);
		register.setRegisterId(registerId);

		List<Register> registers = this.registerRepository.findAll(Example.of(register));
		registerDTO.setRegisters(registers);
		registerDTO.setLastTxnStatus(this.retrieveRegisterTxnStatus(locationId, registers));
		logger.info("All the register details with daily status has been retrieved successfully");
		return registerDTO;
	}

	private Map<Integer, Transaction> retrieveRegisterTxnStatus(Integer locationId, List<Register> registers) {
		Set<String> txnTypes = new HashSet<>();
		txnTypes.add(ServiceConstants.TXN_CLOSE_REGISTER);
		txnTypes.add(ServiceConstants.TXN_OPEN_REGISTER);
		Map<Integer, Transaction> registerLastTxnMap = this.txnService.searchRegisterTxnByCriteria(locationId, txnTypes);
		logger.info("The last daily deed txn status for all registers has been retrieved successfully");
		return registerLastTxnMap;
	}

	@Override
	public List<Tender> retrieveTendersForReconcilation(Integer locationId) {
		List<Tender> tenders = this.tenderRepository.getTendersForReconcilation(locationId);
		logger.info("The valid {} tenders for reconcilation has been retrieved successfully ", tenders.size(), locationId);
		return tenders;
	}

	@Override
	public Location retrieveLocationDetails(Integer locationId) {
		Location location=this.locationRepository.findOne(locationId);
		if(location!=null) {
			logger.info("The {} location details has been retrieved successfully");
		}else {
			logger.info("There is no valid {} location existing");
		}
		return location;
	}

}
