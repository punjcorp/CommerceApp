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
import com.punj.app.ecommerce.domains.common.Register;
import com.punj.app.ecommerce.domains.tender.Tender;
import com.punj.app.ecommerce.domains.transaction.Transaction;
import com.punj.app.ecommerce.models.common.LocationBean;
import com.punj.app.ecommerce.models.common.RegisterBean;
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
		Transaction txnDetails;

		Map<Integer, Transaction> lastTxnStatusTxns = registerDTO.getLastTxnStatus();

		List<Register> registerList = registerDTO.getRegisters();
		if (registerList != null && !registerList.isEmpty()) {
			registers = new ArrayList<>(registerList.size());
			for (Register register : registerList) {
				registerBean = CommonMVCTransformer.transformRegisterDomain(register);
				txnDetails = lastTxnStatusTxns.get(registerBean.getRegisterId());
				registerBean = CommonMVCTransformer.updateRegisterTxnStatus(registerBean, txnDetails);
				registers.add(registerBean);
			}
		}
		logger.info("All the registers from list has been transformed into register bean list");
		return registers;
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

	public static RegisterBean updateRegisterTxnStatus(RegisterBean registerBean, Transaction txnDetails) {
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
		return registerBean;
	}

}
