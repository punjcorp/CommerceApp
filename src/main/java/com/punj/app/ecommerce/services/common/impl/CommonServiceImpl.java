/**
 * 
 */
package com.punj.app.ecommerce.services.common.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.punj.app.ecommerce.domains.common.IdGenerator;
import com.punj.app.ecommerce.domains.common.Location;
import com.punj.app.ecommerce.domains.common.Register;
import com.punj.app.ecommerce.domains.common.ids.RegisterId;
import com.punj.app.ecommerce.domains.item.Hierarchy;
import com.punj.app.ecommerce.domains.item.HierarchyDTO;
import com.punj.app.ecommerce.domains.item.ItemLocationTax;
import com.punj.app.ecommerce.domains.item.ids.ItemLocationTaxId;
import com.punj.app.ecommerce.domains.supplier.SupplierItem;
import com.punj.app.ecommerce.domains.supplier.ids.SupplierItemId;
import com.punj.app.ecommerce.domains.tax.TaxGroup;
import com.punj.app.ecommerce.domains.tender.Tender;
import com.punj.app.ecommerce.domains.transaction.Transaction;
import com.punj.app.ecommerce.repositories.common.IdGeneratorRepository;
import com.punj.app.ecommerce.repositories.common.LocationRepository;
import com.punj.app.ecommerce.repositories.common.ReasonSearchRepository;
import com.punj.app.ecommerce.repositories.common.RegisterRepository;
import com.punj.app.ecommerce.repositories.item.HierarchyRepository;
import com.punj.app.ecommerce.repositories.item.HierarchySearchRepository;
import com.punj.app.ecommerce.repositories.item.ItemLocTaxRepository;
import com.punj.app.ecommerce.repositories.supplier.SupplierItemRepository;
import com.punj.app.ecommerce.repositories.tax.TaxGroupRepository;
import com.punj.app.ecommerce.repositories.tender.TenderRepository;
import com.punj.app.ecommerce.services.TransactionService;
import com.punj.app.ecommerce.services.common.CommonService;
import com.punj.app.ecommerce.services.common.ServiceConstants;
import com.punj.app.ecommerce.services.common.dtos.LocationDTO;
import com.punj.app.ecommerce.services.common.dtos.ReasonDTO;
import com.punj.app.ecommerce.services.common.dtos.RegisterDTO;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
@Service
public class CommonServiceImpl implements CommonService {

	private static final Logger logger = LogManager.getLogger();
	private LocationRepository locationRepository;
	private HierarchyRepository hierarchyRepository;
	private HierarchySearchRepository hierarchySearchRepository;
	private RegisterRepository registerRepository;
	private IdGeneratorRepository idGenRepository;
	private TenderRepository tenderRepository;
	private ReasonSearchRepository reasonRepository;
	private ItemLocTaxRepository itemLocTaxRepository;
	private SupplierItemRepository supItemRepository;
	private TaxGroupRepository taxGroupRepository;
	private TransactionService txnService;

	@Value("${commerce.list.max.perpage}")
	private Integer maxResultPerPage;

	@Value("${commerce.list.max.pageno}")
	private Integer maxPageBtns;

	/**
	 * @param hierarchySearchRepository
	 *            the hierarchySearchRepository to set
	 */
	@Autowired
	public void setHierarchySearchRepository(HierarchySearchRepository hierarchySearchRepository) {
		this.hierarchySearchRepository = hierarchySearchRepository;
	}

	/**
	 * @param supItemRepository
	 *            the supItemRepository to set
	 */
	@Autowired
	public void setSupplierItemRepository(SupplierItemRepository supItemRepository) {
		this.supItemRepository = supItemRepository;
	}

	/**
	 * @param hierarchyRepository
	 *            the hierarchyRepository to set
	 */
	@Autowired
	public void setHierarchyRepository(HierarchyRepository hierarchyRepository) {
		this.hierarchyRepository = hierarchyRepository;
	}

	/**
	 * @param taxGroupRepository
	 *            the taxGroupRepository to set
	 */
	@Autowired
	public void setTaxGroupRepository(TaxGroupRepository taxGroupRepository) {
		this.taxGroupRepository = taxGroupRepository;
	}

	/**
	 * @param reasonRepository
	 *            the reasonRepository to set
	 */
	@Autowired
	public void setReasonRepository(ReasonSearchRepository reasonRepository) {
		this.reasonRepository = reasonRepository;
	}

	/**
	 * @param itemLocTaxRepository
	 *            the itemLocTaxRepository to set
	 */
	@Autowired
	public void setItemLocTaxRepository(ItemLocTaxRepository itemLocTaxRepository) {
		this.itemLocTaxRepository = itemLocTaxRepository;
	}

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
		Location location = this.locationRepository.findOne(locationId);
		if (location != null) {
			logger.info("The {} location details has been retrieved successfully");
		} else {
			logger.info("There is no valid {} location existing");
		}
		return location;
	}

	@Override
	public ReasonDTO retrieveReasonCodes(String searchText, Pager pager) {

		int startCount = (pager.getCurrentPageNo() - 1) * maxResultPerPage;
		pager.setPageSize(maxResultPerPage);
		pager.setStartCount(startCount);
		pager.setMaxDisplayPage(maxPageBtns);

		ReasonDTO reasonDTO = this.reasonRepository.search(searchText, pager);
		if (reasonDTO != null && reasonDTO.getReasonCodes() != null && !reasonDTO.getReasonCodes().isEmpty()) {
			logger.info("The reason codes for {} type has been retrieved successfully", searchText);
		} else {
			logger.info("The {} type does not have any reason codes", searchText);
		}
		return reasonDTO;
	}

	@Override
	public ItemLocationTax retrieveItemDetails(Integer locationId, Integer supplierId, BigInteger itemId) {
		ItemLocationTaxId itemLocTaxId = new ItemLocationTaxId();
		itemLocTaxId.setLocationId(locationId);
		itemLocTaxId.setItemId(itemId);

		ItemLocationTax itemLocTax = this.itemLocTaxRepository.findOne(itemLocTaxId);
		if (itemLocTax != null) {

			SupplierItemId supplierItemId = new SupplierItemId();
			supplierItemId.setItemId(itemId);
			supplierItemId.setSupplierId(supplierId);

			SupplierItem supplierItem = this.supItemRepository.findOne(supplierItemId);
			if (supplierItem != null) {
				if (supplierItem.getUnitCost() != null && supplierItem.getUnitCost().compareTo(BigDecimal.ZERO) == 1) {
					itemLocTax.setBaseUnitCost(supplierItem.getUnitCost());
					logger.info("The supplier unit cost was set for this item");
				}
			}
			logger.info("The item details has been successfully retrieved");
		} else {
			logger.info("There was some error while retrieving the item details");
		}
		return itemLocTax;
	}

	@Override
	public List<TaxGroup> retrieveAllTaxGroups() {
		List<TaxGroup> taxGroupList = this.taxGroupRepository.findAll();

		if (taxGroupList != null && !taxGroupList.isEmpty()) {
			logger.info("The {} no of tax groups has been retrieved successfully", taxGroupList.size());
		} else {
			logger.info("There was no tax group found!!");
		}
		return taxGroupList;
	}

	@Override
	public Hierarchy retrieveHierarchy(Integer hierarchyId) {
		Hierarchy hierarchy = this.hierarchyRepository.findOne(hierarchyId);
		if (hierarchy != null) {
			logger.info("The {} item hierarchy has been retrieved successfully", hierarchyId);
		} else {
			logger.info("The item hierarchy {} was not found!!", hierarchyId);
		}

		return hierarchy;
	}

	@Override
	public HierarchyDTO retrieveHierarchyByText(String searchText, Pager pager) {
		int startCount = (pager.getCurrentPageNo() - 1) * maxResultPerPage;
		pager.setPageSize(this.maxResultPerPage);
		pager.setStartCount(startCount);
		pager.setMaxDisplayPage(this.maxPageBtns);

		
		HierarchyDTO hierarchyDTO= this.hierarchySearchRepository.search(searchText, pager);
		if (hierarchyDTO != null && hierarchyDTO.getHierarchies()!=null && !hierarchyDTO.getHierarchies().isEmpty())
			logger.info("The item hierarchy has been retrieved based on searched keyword");
		else
			logger.info("There was no item hierarchy found based on searched keyword");
		return hierarchyDTO;
	}

}
