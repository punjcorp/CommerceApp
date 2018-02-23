/**
 * 
 */
package com.punj.app.ecommerce.services.converter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.punj.app.ecommerce.domains.transaction.tender.TenderCount;
import com.punj.app.ecommerce.domains.transaction.tender.TenderDenomination;
import com.punj.app.ecommerce.domains.transaction.tender.ids.TenderCountId;
import com.punj.app.ecommerce.domains.transaction.tender.ids.TenderDenominationId;
import com.punj.app.ecommerce.services.common.ServiceConstants;
import com.punj.app.ecommerce.services.dtos.tender.DenominationDTO;
import com.punj.app.ecommerce.services.dtos.tender.TenderDTO;
import com.punj.app.ecommerce.services.dtos.transaction.TransactionIdDTO;

/**
 * @author admin
 *
 */
public class TenderCountDTOConverter {

	private static final Logger logger = LogManager.getLogger();

	private TenderCountDTOConverter() {
		throw new IllegalStateException("TenderCountDTOConverter class");
	}

	public static List<TenderCount> transformTenderList(List<TenderDTO> tenderDTOs, TransactionIdDTO txnIdDTO, String username) {
		List<TenderCount> tenders = new ArrayList<>(tenderDTOs.size());
		TenderCount tender;
		for (TenderDTO tenderDTO : tenderDTOs) {
			tender = TenderCountDTOConverter.transformTenderDTO(tenderDTO, txnIdDTO, username);
			tenders.add(tender);
		}
		logger.info("The tender detail list has been transformed successfully");
		return tenders;
	}

	private static TenderCount transformTenderDTO(TenderDTO tenderDTO, TransactionIdDTO txnIdDTO, String username) {
		TenderCount tenderCount = new TenderCount();

		TenderCountId tenderCountId = new TenderCountId();
		tenderCountId.setLocationId(txnIdDTO.getLocationId());
		tenderCountId.setBusinessDate(txnIdDTO.getBusinessDate());
		tenderCountId.setRegister(txnIdDTO.getRegister());
		tenderCountId.setTransactionSeq(txnIdDTO.getTxnNo());
		tenderCountId.setTenderId(tenderDTO.getTenderId());

		tenderCount.setTenderCountId(tenderCountId);
/*		BigDecimal amount = tenderDTO.getAmount();
		BigInteger mediaCount = tenderDTO.getMediaCount();
*/		
		BigDecimal amount = new BigDecimal("1");
		BigInteger mediaCount = new BigInteger("1");
		
		tenderCount.setTxnType(ServiceConstants.TXN_OPEN_STORE);
		tenderCount.setAmount(amount);
		tenderCount.setMediaCount(mediaCount);

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
		denominationId.setDenomination(denominationDTO.getDenomination());

		denomination.setTenderDenominationId(denominationId);
		denomination.setAmount(denominationDTO.getAmount());
		denomination.setMediaCount(denominationDTO.getMediaCount());

		denomination.setCreatedBy(username);
		denomination.setCreatedDate(LocalDateTime.now());
		
		logger.info("The denomination details from DTO has been transformed successfully");
		return denomination;
	}

}
