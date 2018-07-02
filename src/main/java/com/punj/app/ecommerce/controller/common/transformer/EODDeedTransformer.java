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

import com.punj.app.ecommerce.domains.tender.Tender;
import com.punj.app.ecommerce.domains.transaction.tender.TenderCount;
import com.punj.app.ecommerce.domains.transaction.tender.TenderDenomination;
import com.punj.app.ecommerce.models.dailydeeds.DailyDeedBean;
import com.punj.app.ecommerce.models.tender.DenominationBean;
import com.punj.app.ecommerce.models.tender.TenderBean;
import com.punj.app.ecommerce.services.dtos.DailyTransaction;
import com.punj.app.ecommerce.services.dtos.tender.TenderDTO;
import com.punj.app.ecommerce.services.dtos.transaction.TransactionIdDTO;

/**
 * @author admin
 *
 */
public class EODDeedTransformer {

	private static final Logger logger = LogManager.getLogger();

	private EODDeedTransformer() {
		throw new IllegalStateException("EODDeedTransformer class");
	}

	public static DailyTransaction transformCloseTxnDetails(DailyDeedBean dailyDeedBean) {
		DailyTransaction registerCloseTxn = new DailyTransaction();

		TransactionIdDTO transactionId = DailyDeedTransformer.transformTransactionDetails(dailyDeedBean.getBusinessDate(), dailyDeedBean.getLocationId(),
				dailyDeedBean.getRegister());
		registerCloseTxn.setTransactionId(transactionId);

		List<TenderDTO> tenders = DailyDeedTransformer.transformTenderList(dailyDeedBean.getTenders(), dailyDeedBean.getConcilationBean());
		registerCloseTxn.setTenders(tenders);

		logger.info("The register close transaction details are transformed and ready for processing");
		return registerCloseTxn;
	}

	public static Map<Integer, Map<Integer, TenderDenomination>> transformRegisterCloseTenderCounts(List<TenderCount> tenderCounts) {
		List<TenderDenomination> tenderDenominations = null;
		Integer tenderId = null;
		Integer denomId = null;
		TenderDenomination denomination = null;

		Map<Integer, Map<Integer, TenderDenomination>> tenderMap = new HashMap<>();
		Map<Integer, TenderDenomination> tenderDenomMap = null;
		if (tenderCounts != null && !tenderCounts.isEmpty()) {
			for (TenderCount tenderCount : tenderCounts) {
				tenderDenominations = tenderCount.getDenominations();
				tenderId = tenderCount.getTenderCountId().getTender().getTenderId();
				tenderDenomMap = tenderMap.get(tenderId);
				if (tenderDenomMap == null)
					tenderDenomMap = new HashMap<>();
				if (tenderDenominations != null && !tenderDenominations.isEmpty()) {
					for (TenderDenomination tenderDenomination : tenderDenominations) {
						denomId = tenderDenomination.getTenderDenominationId().getDenominationId();
						denomination = tenderDenomMap.get(denomId);
						if (denomination != null) {
							denomination.setAmount(denomination.getAmount().add(tenderDenomination.getAmount()));
							denomination.setMediaCount(denomination.getMediaCount().add(tenderDenomination.getMediaCount()));
						} else {
							tenderDenomMap.put(denomId, tenderDenomination);
						}
					}
				}
				tenderMap.put(tenderId, tenderDenomMap);
			}
		}
		logger.info("All the register close tender counts has been transformed successfully");
		return tenderMap;
	}

	public static List<TenderBean> tranformTenders(List<Tender> tenders, Map<Integer, Map<Integer, TenderDenomination>> tenderMap) {
		List<TenderBean> tenderBeans = new ArrayList<>(tenders.size());
		TenderBean tenderBean = null;
		Integer tenderId = null;
		Map<Integer, TenderDenomination> tenderDenominations = null;
		List<DenominationBean> denominationList = null;

		for (Tender tender : tenders) {
			tenderBean = new TenderBean();
			tenderId = tender.getTenderId();
			tenderBean.setTenderId(tender.getTenderId());
			tenderBean.setName(tender.getName());
			tenderBean.setTndrType(tender.getType());
			tenderBean.setDescription(tender.getDescription());
			tenderBeans.add(tenderBean);
			tenderDenominations = tenderMap.get(tenderId);
			if (tenderDenominations != null && tenderDenominations.size() > 0) {
				denominationList = EODDeedTransformer.transformTenderDenominations(tenderDenominations);
				EODDeedTransformer.updateTenderBeanAmount(denominationList, tenderBean);
				tenderBean.setDenominations(denominationList);
			}
		}
		logger.info("The tenders has been transformed successfully");
		return tenderBeans;
	}

	public static void updateTenderBeanAmount(List<DenominationBean> denominationList, TenderBean tenderBean) {
		BigDecimal totatAmount = BigDecimal.ZERO;
		BigInteger totatMediaCount = BigInteger.ZERO;
		for (DenominationBean denominationBean : denominationList) {
			totatAmount = totatAmount.add(denominationBean.getAmount());
			totatMediaCount = totatMediaCount.add(denominationBean.getMediaCount());
		}
		tenderBean.setCalMCount(totatMediaCount);
		tenderBean.setCalTAmount(totatAmount);
		logger.info("The tenders total amount and media count has been updated successfully");
	}

	public static List<DenominationBean> transformTenderDenominations(Map<Integer, TenderDenomination> tenderDenominations) {
		List<DenominationBean> denominationList = new ArrayList<>();
		DenominationBean denominationBean = null;
		TenderDenomination tenderDenomination = null;
		for (Integer denomIdId : tenderDenominations.keySet()) {
			tenderDenomination = tenderDenominations.get(denomIdId);
			denominationBean = new DenominationBean();

			denominationBean.setDenominationId(tenderDenomination.getTenderDenominationId().getDenominationId());
			denominationBean.setDenomValue(tenderDenomination.getAmount().divide(new BigDecimal(tenderDenomination.getMediaCount())));
			denominationBean.setMediaCount(tenderDenomination.getMediaCount());
			denominationBean.setAmount(tenderDenomination.getAmount());

			denominationList.add(denominationBean);
		}
		logger.info("The denominations for the provided tender has been transformed successfully");
		return denominationList;
	}

}
