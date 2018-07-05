/**
 * 
 */
package com.punj.app.ecommerce.controller.common.transformer;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.punj.app.ecommerce.domains.common.Denomination;
import com.punj.app.ecommerce.domains.common.Location;
import com.punj.app.ecommerce.domains.common.Register;
import com.punj.app.ecommerce.domains.finance.DailyTotals;
import com.punj.app.ecommerce.domains.tender.Tender;
import com.punj.app.ecommerce.domains.transaction.Transaction;
import com.punj.app.ecommerce.domains.transaction.tender.TenderCount;
import com.punj.app.ecommerce.domains.transaction.tender.TenderDenomination;
import com.punj.app.ecommerce.domains.user.Address;
import com.punj.app.ecommerce.models.common.AddressBean;
import com.punj.app.ecommerce.models.common.BaseDenominationBean;
import com.punj.app.ecommerce.models.common.LocationBean;
import com.punj.app.ecommerce.models.common.RegisterBean;
import com.punj.app.ecommerce.models.dailydeeds.ConcilationBean;
import com.punj.app.ecommerce.models.tender.DenominationBean;
import com.punj.app.ecommerce.models.tender.TenderBean;
import com.punj.app.ecommerce.services.common.ServiceConstants;
import com.punj.app.ecommerce.services.common.dtos.LocationDTO;
import com.punj.app.ecommerce.services.common.dtos.RegisterDTO;

/**
 * @author admin
 *
 */
public class CommonMVCTransformer {

	private static final Logger logger = LogManager.getLogger();

	private CommonMVCTransformer() {
		throw new IllegalStateException("CommonBeanTransformer class");
	}

	public static List<BaseDenominationBean> transformDenominations(List<Denomination> denominations) {
		List<BaseDenominationBean> denomBeans = new ArrayList<>(denominations.size());
		BaseDenominationBean denomBean;
		for (Denomination denomination : denominations) {
			denomBean = new BaseDenominationBean();
			denomBean.setDenominationId(denomination.getDenominationId());
			denomBean.setCode(denomination.getCode());
			denomBean.setDenomValue(denomination.getDenomValue());
			denomBean.setDescription(denomination.getDescription());
			denomBean.setCurrencyCode(denomination.getCurrencyCode());
			denomBeans.add(denomBean);
		}
		logger.info("The denominations has been transformed successfully");
		return denomBeans;
	}

	public static List<TenderBean> tranformTenders(List<Tender> tenders) {
		List<TenderBean> tenderBeans = new ArrayList<>(tenders.size());
		TenderBean tenderBean;
		for (Tender tender : tenders) {
			tenderBean = new TenderBean();
			tenderBean.setTenderId(tender.getTenderId());
			tenderBean.setName(tender.getName());
			tenderBean.setTndrType(tender.getType());
			tenderBean.setDescription(tender.getDescription());
			tenderBeans.add(tenderBean);
		}
		logger.info("The tenders has been transformed successfully");
		return tenderBeans;
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

	public static List<LocationBean> transformLocationList(List<Location> locationList, Boolean partial) {
		LocationBean locationBean = null;
		List<LocationBean> locations = null;
		if (locationList != null && !locationList.isEmpty()) {
			locations = new ArrayList<>(locationList.size());
			for (Location location : locationList) {
				locationBean = CommonMVCTransformer.transformLocationDomainPartially(location, partial);
				locations.add(locationBean);
			}
		}
		logger.info("All the locations from list has been transformed into location bean list");
		return locations;
	}

	public static List<LocationBean> transformLocationDTO(LocationDTO locationDTO) {
		LocationBean locationBean = null;
		List<LocationBean> locations = null;
		Transaction txnDetails;

		Map<Integer, Transaction> lastTxnStatusTxns = locationDTO.getLastTxnStatus();

		List<Location> locationList = locationDTO.getLocations();
		if (locationList != null && !locationList.isEmpty()) {
			locations = new ArrayList<>(locationList.size());
			for (Location location : locationList) {
				locationBean = CommonMVCTransformer.transformLocationDomainPartially(location, Boolean.FALSE);
				txnDetails = lastTxnStatusTxns.get(locationBean.getLocationId());
				locationBean = CommonMVCTransformer.updateLocationTxnStatus(locationBean, txnDetails);
				locations.add(locationBean);
			}
		}
		logger.info("All the locations from list has been transformed into location bean list");
		return locations;
	}

	public static LocationBean updateLocationTxnStatus(LocationBean locationBean, Transaction txnDetails) {
		if (txnDetails != null) {
			String txnType = txnDetails.getTxnType();
			locationBean.setLastBusinessDate(txnDetails.getTransactionId().getBusinessDate());
			locationBean.setLastCreatedDate(txnDetails.getStartTime());
			locationBean.setLastStatus(txnType);
			if (txnDetails.getTxnType().equals(ServiceConstants.TXN_OPEN_STORE)) {
				locationBean.setEligibleForStoreOpen(Boolean.FALSE);
			} else {
				locationBean.setEligibleForStoreOpen(Boolean.TRUE);
			}
		}
		return locationBean;
	}

	public static LocationBean transformLocationDomainPartially(Location location, Boolean partial) {
		LocationBean locationBean = new LocationBean();
		locationBean.setLocationId(location.getLocationId());
		locationBean.setLocationType(location.getLocationType());
		locationBean.setName(location.getName());
		locationBean.setDefaultTender(location.getDefaultTender());
		locationBean.setGstNo(location.getGstNo());
		if (!partial) {
			locationBean.setAddress1(location.getAddress1());
			locationBean.setAddress2(location.getAddress2());
			locationBean.setCity(location.getCity());
			locationBean.setState(location.getState());
			locationBean.setCountry(location.getCountry());
			locationBean.setPincode(location.getPincode());
			locationBean.setEmail(location.getEmail());
			locationBean.setManager(location.getManager());
			locationBean.setTelephone1(location.getTelephone1());
			locationBean.setTelephone2(location.getTelephone2());
		}
		logger.info("The locations details has been partially transformed into location bean");
		return locationBean;
	}

	public static List<RegisterBean> transformRegisterDTO(RegisterDTO registerDTO) {
		RegisterBean registerBean = null;
		List<RegisterBean> registers = null;
		Integer regId = null;
		Transaction txnDetails;
		DailyTotals regTotals = null;
		List<TenderCount> regCounts=null;
		ConcilationBean concilationBean = null;
		

		Map<Integer, Transaction> lastTxnStatusTxns = registerDTO.getLastTxnStatus();
		Map<Integer, List<TenderCount>> regTenderCounts = registerDTO.getRegTenderTotals();
		Map<Integer, DailyTotals> regDailyTotals = registerDTO.getRegTotals();

		List<Register> registerList = registerDTO.getRegisters();
		if (registerList != null && !registerList.isEmpty()) {
			registers = new ArrayList<>(registerList.size());
			for (Register register : registerList) {
				regId = register.getRegisterId().getRegister();
				registerBean = CommonMVCTransformer.transformRegisterDomain(register);
				
				if(lastTxnStatusTxns!=null && !lastTxnStatusTxns.isEmpty()) {
					txnDetails = lastTxnStatusTxns.get(regId);
					CommonMVCTransformer.updateRegisterTxnStatus(registerBean, txnDetails);
				}
				if(regDailyTotals!=null && regTenderCounts!=null) {
					regTotals = regDailyTotals.get(regId);
					regCounts = regTenderCounts.get(regId);
					if(regTotals!=null && regCounts!=null) {
						concilationBean = CommonMVCTransformer.transformRegTotal(regTotals, regCounts);
						registerBean.setConcilationDtls(concilationBean);
					}
				}
				
				registers.add(registerBean);
			}
		}
		logger.info("All the registers from list has been transformed into register bean list");
		return registers;
	}

	public static ConcilationBean transformRegTotal(DailyTotals regTotals, List<TenderCount> regTenderCounts) {
		ConcilationBean concilationBean = new ConcilationBean();
		concilationBean.setActualTotalAmt(regTotals.getEndOfDayAmount());
		concilationBean.setBusinessDate(regTotals.getBusinessDate());
		concilationBean.setExpectedTotalAmt(regTotals.getTotalTxnAmount());
		concilationBean.setLocationId(regTotals.getLocationId());
		concilationBean.setRegister(regTotals.getRegisterId());

		if(regTenderCounts!=null && !regTenderCounts.isEmpty()) {
			List<TenderBean> tenders=CommonMVCTransformer.transformTenderCounts(regTenderCounts);
			concilationBean.setTenders(tenders);
		}
		
		return concilationBean;
	}

	public static List<TenderBean> transformTenderCounts(List<TenderCount> tenderCounts) {
		List<TenderBean> tenders=new ArrayList<>(tenderCounts.size());
		TenderBean tenderBean = null;
		for(TenderCount tenderCount: tenderCounts) {
			tenderBean= CommonMVCTransformer.transformTenderCount(tenderCount);
			tenders.add(tenderBean);
		}
		logger.info("The tender details for all the tender counts has been transformed successfully");
		return tenders;
	}	
	
	public static TenderBean transformTenderCount(TenderCount tenderCount) {
		TenderBean tenderBean = new TenderBean();
		tenderBean.setCalMCount(tenderCount.getMediaCount());
		tenderBean.setCalTAmount(tenderCount.getAmount());
		tenderBean.setDescription(tenderCount.getTenderCountId().getTender().getDescription());
		tenderBean.setName(tenderCount.getTenderCountId().getTender().getName());
		tenderBean.setTenderId(tenderCount.getTenderCountId().getTender().getTenderId());
		tenderBean.setTndrType(tenderCount.getTenderCountId().getTender().getType());
		List<TenderDenomination> denominations = tenderCount.getDenominations();
		if(denominations!=null && !denominations.isEmpty()) {
			List<DenominationBean> denomList =CommonMVCTransformer.transformDenominationList(denominations);
			tenderBean.setDenominations(denomList);
		}
		logger.info("The tender details has been transformed successfully");
		
		return tenderBean;

	}

	public static List<DenominationBean> transformDenominationList(List<TenderDenomination> denominations) {
		DenominationBean denomBean = null;
		List<DenominationBean> denomList = new ArrayList<>(denominations.size());
		for (TenderDenomination tenderDenom : denominations) {
			denomBean = CommonMVCTransformer.transformDenominationDomain(tenderDenom);
			denomList.add(denomBean);
		}
		logger.info("The denomination bean list has the transformed tender denomination domain objects now");
		
		return denomList;
	}

	public static DenominationBean transformDenominationDomain(TenderDenomination denomination) {
		DenominationBean denomBean = new DenominationBean();
		denomBean.setAmount(denomination.getAmount());
		denomBean.setDenominationId(denomination.getTenderDenominationId().getDenominationId());
		denomBean.setDenomValue(denomination.getAmount().divide(new BigDecimal(denomination.getMediaCount())));
		denomBean.setMediaCount(denomination.getMediaCount());

		logger.info("The denomination bean object has the transformed values now");

		return denomBean;
	}

	public static RegisterBean transformRegisterDomain(Register register) {
		RegisterBean registerBean = new RegisterBean();
		registerBean.setLocationId(register.getRegisterId().getLocationId());
		registerBean.setRegisterId(register.getRegisterId().getRegister());
		registerBean.setName(register.getName());
		registerBean.setCreatedBy(register.getCreatedBy());
		registerBean.setCreatedDate(register.getCreatedDate());

		logger.info("The register details has been transformed into register bean");
		return registerBean;
	}

	public static void updateRegisterTxnStatus(RegisterBean registerBean, Transaction txnDetails) {
		if (txnDetails != null) {
			String txnType = txnDetails.getTxnType();
			registerBean.setLastBusinessDate(txnDetails.getTransactionId().getBusinessDate());
			registerBean.setLastCreatedDate(txnDetails.getStartTime());
			registerBean.setLastStatus(txnType);
			if (txnDetails.getTxnType().equals(ServiceConstants.TXN_OPEN_REGISTER)) {
				registerBean.setEligibleForRegisterOpen(Boolean.FALSE);
				registerBean.setLastOpenedBy(txnDetails.getCreatedBy());
			} else {
				registerBean.setEligibleForRegisterOpen(Boolean.TRUE);
			}
		}
		logger.info("The register open transaction details has been updated");
	}

	public static AddressBean transformAddress(Address address) {
		AddressBean addressBean = new AddressBean();

		addressBean.setAddressId(address.getAddressId());
		addressBean.setPrimary(address.getPrimary());
		addressBean.setAddress1(address.getAddress1());
		addressBean.setAddress2(address.getAddress2());
		addressBean.setCity(address.getCity());
		addressBean.setState(address.getState());
		addressBean.setCountry(address.getCountry());
		addressBean.setPincode(address.getPincode());
		addressBean.setAddressType(address.getAddressType());

		logger.info("The address has been transformed into address bean successfully");
		return addressBean;
	}

	public static Address transformAddress(AddressBean addressBean) {
		Address address = new Address();

		address.setPrimary(addressBean.getPrimary());
		address.setAddressId(addressBean.getAddressId());
		address.setAddress1(addressBean.getAddress1());
		address.setAddress2(addressBean.getAddress2());
		address.setCity(addressBean.getCity());
		address.setState(addressBean.getState());
		address.setCountry(addressBean.getCountry());
		address.setPincode(addressBean.getPincode());
		address.setAddressType(addressBean.getAddressType());

		logger.info("The address bean has been transformed into address object successfully");
		return address;
	}

	public static List<Address> transformAddressList(List<AddressBean> addressBeanList) {
		List<Address> addressList = new ArrayList<>(addressBeanList.size());
		Address address;
		for (AddressBean addressBean : addressBeanList) {
			address = CommonMVCTransformer.transformAddress(addressBean);
			addressList.add(address);
		}

		logger.info("The address beans list has been transformed into address list successfully");
		return addressList;
	}

	public static List<AddressBean> transformAddresses(List<Address> addressList) {
		List<AddressBean> addressBeanList = new ArrayList<>(addressList.size());
		AddressBean addressBean;
		for (Address address : addressList) {
			addressBean = CommonMVCTransformer.transformAddress(address);
			addressBeanList.add(addressBean);
		}

		logger.info("The address list has been transformed into address bean list successfully");
		return addressBeanList;
	}

	public static int getPrimaryAddressIndex(List<AddressBean> addressBeanList) {
		int result = -1;
		for (int i = 0; i < addressBeanList.size(); i++) {
			if ("Y".equals(addressBeanList.get(i).getPrimary())) {
				result = i;
			}
		}
		return result;

	}

	public static AddressBean getPrimaryAddress(List<Address> addressList) {
		AddressBean addressBean = null;
		for (Address address : addressList) {
			if ("Y".equals(address.getPrimary())) {
				addressBean = CommonMVCTransformer.transformAddress(address);
			}
		}
		return addressBean;

	}

}
