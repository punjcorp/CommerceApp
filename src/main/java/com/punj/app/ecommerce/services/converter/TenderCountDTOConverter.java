/**
 * 
 */
package com.punj.app.ecommerce.services.converter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.punj.app.ecommerce.domains.tender.Tender;
import com.punj.app.ecommerce.domains.transaction.ids.TransactionId;
import com.punj.app.ecommerce.domains.transaction.tender.TenderCount;
import com.punj.app.ecommerce.domains.transaction.tender.TenderDenomination;
import com.punj.app.ecommerce.domains.transaction.tender.ids.TenderCountId;
import com.punj.app.ecommerce.domains.transaction.tender.ids.TenderDenominationId;
import com.punj.app.ecommerce.services.common.ServiceConstants;
import com.punj.app.ecommerce.services.dtos.tender.DenominationDTO;
import com.punj.app.ecommerce.services.dtos.tender.TenderDTO;

/**
 * @author admin
 *
 */
public class TenderCountDTOConverter {

	private static final Logger logger = LogManager.getLogger();

	private TenderCountDTOConverter() {
		throw new IllegalStateException("TenderCountDTOConverter class");
	}

	public static List<TenderCount> transformTenderList(List<TenderDTO> tenderDTOs, TransactionId txnId, String username, String txnType) {
		List<TenderCount> tenders = new ArrayList<>(tenderDTOs.size());
		TenderCount tender;
		for (TenderDTO tenderDTO : tenderDTOs) {
			tender = TenderCountDTOConverter.transformTenderDTO(tenderDTO, txnId, username, txnType);
			tenders.add(tender);
		}
		logger.info("The tender detail list has been transformed successfully");
		return tenders;
	}

	private static TenderCount transformTenderDTO(TenderDTO tenderDTO, TransactionId txnId, String username, String txnType) {
		TenderCount tenderCount = new TenderCount();

		TenderCountId tenderCountId = new TenderCountId();
		tenderCountId.setLocationId(txnId.getLocationId());
		tenderCountId.setBusinessDate(txnId.getBusinessDate());
		tenderCountId.setRegister(txnId.getRegister());
		tenderCountId.setTransactionSeq(txnId.getTransactionSeq());

		Tender tender = new Tender();
		tender.setTenderId(tenderDTO.getTenderId());

		tenderCountId.setTender(tender);

		tenderCount.setTenderCountId(tenderCountId);
		tenderCount.setTxnType(txnType);

		tenderCount.setAmount(tenderDTO.getAmount());
		tenderCount.setMediaCount(tenderDTO.getMediaCount());
		
		tenderCount.setActualAmount(tenderDTO.getActualAmount());
		tenderCount.setActualMediaCount(tenderDTO.getActualMediaCount());

		tenderCount.setCreatedBy(username);
		tenderCount.setCreatedDate(LocalDateTime.now());

		List<TenderDenomination> denominations = TenderCountDTOConverter.transformDenominationList(tenderDTO.getDenominations(), tenderCountId, username);
		tenderCount.setDenominations(denominations);

		logger.info("The tender count details from DTO has been transformed successfully");

		return tenderCount;
	}

	public static List<TenderDenomination> transformDenominationList(List<DenominationDTO> denominationDTOs, TenderCountId tenderCountId, String username) {
		List<TenderDenomination> denominations = new ArrayList<>(denominationDTOs.size());
		TenderDenomination denomination;
		for (DenominationDTO denominationDTO : denominationDTOs) {
			denomination = TenderCountDTOConverter.transformDenomination(denominationDTO, tenderCountId, username);
			denominations.add(denomination);
		}
		logger.info("The denomination details list from DTOs has been transformed successfully");
		return denominations;
	}

	public static TenderDenomination transformDenomination(DenominationDTO denominationDTO, TenderCountId tenderCountId, String username) {
		TenderDenomination denomination = new TenderDenomination();

		TenderDenominationId denominationId = new TenderDenominationId();
		denominationId.setTenderCountId(tenderCountId);
		denominationId.setDenominationId(denominationDTO.getDenominationId());

		denomination.setTenderDenominationId(denominationId);
		denomination.setAmount(denominationDTO.getAmount());
		denomination.setMediaCount(denominationDTO.getMediaCount());

		denomination.setCreatedBy(username);
		denomination.setCreatedDate(LocalDateTime.now());

		logger.info("The denomination details from DTO has been transformed successfully");
		return denomination;
	}

}
