/**
 * 
 */
package com.punj.app.ecommerce.services.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.punj.app.ecommerce.domains.payment.AccountHead;
import com.punj.app.ecommerce.domains.supplier.Supplier;
import com.punj.app.ecommerce.domains.supplier.SupplierDTO;
import com.punj.app.ecommerce.domains.supplier.SupplierItem;
import com.punj.app.ecommerce.domains.supplier.ids.SupplierAddressId;
import com.punj.app.ecommerce.domains.supplier.ids.SupplierItemId;
import com.punj.app.ecommerce.domains.user.Address;
import com.punj.app.ecommerce.repositories.AddressRepository;
import com.punj.app.ecommerce.repositories.supplier.SupplierAddressRepository;
import com.punj.app.ecommerce.repositories.supplier.SupplierItemRepository;
import com.punj.app.ecommerce.repositories.supplier.SupplierRepository;
import com.punj.app.ecommerce.repositories.supplier.SupplierSearchRepository;
import com.punj.app.ecommerce.services.PaymentAccountService;
import com.punj.app.ecommerce.services.SupplierService;
import com.punj.app.ecommerce.services.common.CommonService;
import com.punj.app.ecommerce.services.common.ServiceConstants;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
@Service
public class SupplierServiceImpl implements SupplierService {

	private static final Logger logger = LogManager.getLogger();
	private SupplierRepository supplierRepository;
	private SupplierItemRepository supplierItemRepository;
	private SupplierSearchRepository supplierSearchRepository;
	private AddressRepository addressRepository;
	private SupplierAddressRepository supplierAddressRepository;
	private PaymentAccountService paymentService;
	private CommonService commonService;

	@Value("${commerce.list.max.perpage}")
	private Integer maxResultPerPage;

	@Value("${commerce.list.max.pageno}")
	private Integer maxPageBtns;

	/**
	 * @param paymentService
	 *            the paymentService to set
	 */
	@Autowired
	public void setPaymentService(PaymentAccountService paymentService) {
		this.paymentService = paymentService;
	}

	/**
	 * @param commonService
	 *            the commonService to set
	 */
	@Autowired
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	/**
	 * @param supplierAddressRepository
	 *            the supplierAddressRepository to set
	 */
	@Autowired
	public void setSupplierAddressRepository(SupplierAddressRepository supplierAddressRepository) {
		this.supplierAddressRepository = supplierAddressRepository;
	}

	/**
	 * @param supplierItemRepository
	 *            the supplierItemRepository to set
	 */
	@Autowired
	public void setSupplierItemRepository(SupplierItemRepository supplierItemRepository) {
		this.supplierItemRepository = supplierItemRepository;
	}

	/**
	 * @param supplierRepository
	 *            the supplierRepository to set
	 */
	@Autowired
	public void setSupplierRepository(SupplierRepository supplierRepository) {
		this.supplierRepository = supplierRepository;
	}

	/**
	 * @param supplierSearchRepository
	 *            the supplierSearchRepository to set
	 */
	@Autowired
	public void setSupplierSearchRepository(SupplierSearchRepository supplierSearchRepository) {
		this.supplierSearchRepository = supplierSearchRepository;
	}

	/**
	 * @param addressRepository
	 *            the addressRepository to set
	 */
	@Autowired
	public void setAddressRepository(AddressRepository addressRepository) {
		this.addressRepository = addressRepository;
	}

	@Override
	@Transactional
	public Supplier createSupplier(Supplier supplier) {

		supplier = this.supplierRepository.save(supplier);
		if (supplier != null) {

			List<AccountHead> accountHeads = this.paymentService.setupPaymentAccount(ServiceConstants.ACCOUNT_TYPE_SUPPLIER,
					new BigInteger(supplier.getSupplierId().toString()), supplier.getCreatedBy());
			if (accountHeads != null && !accountHeads.isEmpty()) {
				logger.info("The supplier {} with all location account setup has been created successfully.", supplier.getSupplierId());
			} else {
				logger.info("There was some issue while setting up the supplier account for all the locations.");
			}

		} else {
			logger.info("There was some issue while creating a new supplier record");
		}

		return supplier;
	}

	@Override
	public List<Supplier> updateSupplier(List<Supplier> suppliers) {

		List<Supplier> finalSuppliers = new ArrayList<Supplier>(suppliers.size());
		Supplier actualSupplier = null;
		for (Supplier supplier : suppliers) {
			Integer supplierId = supplier.getSupplierId();
			if (supplierId != null) {
				actualSupplier = this.supplierRepository.findOne(supplierId);
				actualSupplier.setName(supplier.getName());
				actualSupplier.setPhone1(supplier.getPhone1());
				actualSupplier.setPhone2(supplier.getPhone2());
				actualSupplier.setEmail(supplier.getEmail());

			} else {
				actualSupplier = supplier;
			}

			finalSuppliers.add(this.supplierRepository.save(actualSupplier));
			logger.info("The {} supplier has been updated", supplier.getSupplierId());
		}
		return finalSuppliers;
	}

	@Override
	public Supplier searchSupplier(Supplier supplier) {

		supplier = this.supplierRepository.findOne(Example.of(supplier));
		logger.info("The retreived supplier successfully.");
		return supplier;
	}

	@Override
	public List<Supplier> getAll() {

		List<Supplier> suppliers = this.supplierRepository.findAll();
		logger.info("All the suppliers retreived successfully.");
		return suppliers;
	}

	@Override
	@Transactional
	public void delete(Integer supplierId) {

		Supplier supplier = this.supplierRepository.findOne(supplierId);

		List<Address> addresses = supplier.getAddresses();
		BigInteger addressId = null;
		for (Address address : addresses) {
			addressId = address.getAddressId();
			this.addressRepository.delete(addressId);
			logger.info("The provided supplier {} address {} has been deleted successfully", supplierId, addressId);
		}

		this.supplierRepository.delete(supplierId);
		logger.info("The provided supplier {} has been deleted successfully", supplierId);

	}

	@Override
	@Transactional
	public void deleteSuppliers(Set<Integer> supplierIds) {

		for (Integer supplierId : supplierIds) {
			this.delete(supplierId);
			logger.info("The provided supplier {} has been deleted successfully", supplierId);
		}

		logger.info("All provided {} suppliers has been deleted successfully", supplierIds.size());

	}

	public void deleteAddress(Address supplierAddress, Integer supplierId) {
		SupplierAddressId supAddressId = new SupplierAddressId();
		supAddressId.setSupplierId(supplierId);
		supAddressId.setAddressId(supplierAddress.getAddressId());

		this.supplierAddressRepository.delete(supAddressId);
		logger.info("The provided address supplier relationship has been deleted successfully");
		this.addressRepository.delete(supplierAddress);
		logger.info("The provided address has been deleted from address master table");
	}

	@Override
	public SupplierDTO searchSupplier(String text, Pager pager) {
		int startCount = (pager.getCurrentPageNo() - 1) * maxResultPerPage;
		pager.setPageSize(maxResultPerPage);
		pager.setStartCount(startCount);
		pager.setMaxDisplayPage(maxPageBtns);

		SupplierDTO suppliers = this.supplierSearchRepository.search(text, pager);
		logger.info("The suppliers has been retrieved based on searched keyword");
		return suppliers;
	}

	@Override
	public SupplierItem getSupplierItem(BigInteger itemId, Integer supplierId) {

		SupplierItemId supplierItemId = new SupplierItemId();
		supplierItemId.setItemId(itemId);
		supplierItemId.setSupplierId(supplierId);

		SupplierItem supplierItem = this.supplierItemRepository.findOne(supplierItemId);
		logger.info("The supplier item was retreived successfully");
		return supplierItem;
	}

}
