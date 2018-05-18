/**
 * 
 */
package com.punj.app.ecommerce.controller.common.transformer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.punj.app.ecommerce.domains.common.Denomination;
import com.punj.app.ecommerce.domains.transaction.Transaction;
import com.punj.app.ecommerce.domains.transaction.ids.TransactionId;
import com.punj.app.ecommerce.domains.transaction.tender.TenderCount;
import com.punj.app.ecommerce.domains.transaction.tender.TenderDenomination;
import com.punj.app.ecommerce.models.common.BaseDenominationBean;
import com.punj.app.ecommerce.models.dailydeeds.DailyDeedBean;
import com.punj.app.ecommerce.models.tender.DenominationBean;
import com.punj.app.ecommerce.models.tender.TenderBean;
import com.punj.app.ecommerce.services.dtos.DailyOpenTransaction;
import com.punj.app.ecommerce.services.dtos.tender.DenominationDTO;
import com.punj.app.ecommerce.services.dtos.tender.TenderDTO;
import com.punj.app.ecommerce.services.dtos.transaction.TransactionIdDTO;

/**
 * @author admin
 *
 */
public class DailyDeedTransformer {

	private static final Logger logger = LogManager.getLogger();

	private DailyDeedTransformer() {
		throw new IllegalStateException("DailyDeedTransformer class");
	}

	public static DailyOpenTransaction transformOpenTxnDetails(DailyDeedBean dailyDeedBean) {
		DailyOpenTransaction storeOpenTxn = new DailyOpenTransaction();

		TransactionIdDTO transactionId = DailyDeedTransformer.transformTransactionDetails(dailyDeedBean.getBusinessDate(), dailyDeedBean.getLocationId(),
				dailyDeedBean.getRegister());
		storeOpenTxn.setTransactionId(transactionId);

		List<TenderDTO> tenders = DailyDeedTransformer.transformTenderList(dailyDeedBean.getTenders());
		storeOpenTxn.setTenders(tenders);

		logger.info("The selected ids and list index from management page has been seperated");
		return storeOpenTxn;
	}

	public static TransactionIdDTO transformTransactionDetails(LocalDateTime businessDate, Integer locationId, Integer register) {
		TransactionIdDTO transactionId = new TransactionIdDTO();
		transactionId.setBusinessDate(businessDate);
		transactionId.setLocationId(locationId);
		transactionId.setRegister(register);
		return transactionId;
	}

	public static List<TenderDTO> transformTenderList(List<TenderBean> tenderBeans) {
		List<TenderDTO> tenders = new ArrayList<>(tenderBeans.size());
		TenderDTO tender;
		for (TenderBean tenderBean : tenderBeans) {
			tender = DailyDeedTransformer.transformTenderBean(tenderBean);
			tenders.add(tender);
		}
		logger.info("The tender detail list has been transformed successfully");
		return tenders;
	}

	private static TenderDTO transformTenderBean(TenderBean tenderBean) {
		TenderDTO tender = new TenderDTO();
		tender.setTenderId(tenderBean.getTenderId());
		tender.setName(tenderBean.getName());
		tender.setTypeCode(tenderBean.getTndrType());
		tender.setAmount(tenderBean.getCalTAmount());
		tender.setMediaCount(tenderBean.getCalMCount());

		List<DenominationDTO> denominations = DailyDeedTransformer.transformDenominationList(tenderBean.getDenominations());
		tender.setDenominations(denominations);

		logger.info("The tender details has been transformed successfully");

		return tender;
	}

	public static List<DenominationDTO> transformDenominationList(List<DenominationBean> denominationBeans) {
		List<DenominationDTO> denominations = new ArrayList<>(denominationBeans.size());
		DenominationDTO denomination;
		for (DenominationBean denominationBean : denominationBeans) {
			denomination = DailyDeedTransformer.transformDenomination(denominationBean);
			denominations.add(denomination);
		}
		logger.info("The denomination details list has been transformed successfully");
		return denominations;
	}

	public static DenominationDTO transformDenomination(DenominationBean denominationBean) {
		DenominationDTO denomination = new DenominationDTO();

		denomination.setDenominationId(denominationBean.getDenominationId());
		denomination.setAmount(denominationBean.getAmount());
		denomination.setMediaCount(denominationBean.getMediaCount());

		logger.info("The denomination details has been transformed successfully");
		return denomination;
	}

	public static List<TenderBean> cloneTenderList(List<TenderBean> orgTenderBeans) {
		List<TenderBean> tenders = new ArrayList<>(orgTenderBeans.size());
		TenderBean tender;
		for (TenderBean orgTenderBean : orgTenderBeans) {
			tender = DailyDeedTransformer.cloneTenderBean(orgTenderBean);
			tenders.add(tender);
		}
		logger.info("The tender detail list has been cloned successfully");
		return tenders;
	}

	private static TenderBean cloneTenderBean(TenderBean orgTenderBean) {
		TenderBean tender = new TenderBean();
		tender.setTenderId(orgTenderBean.getTenderId());
		tender.setName(orgTenderBean.getName());
		tender.setTndrType(orgTenderBean.getTndrType());
		tender.setCalTAmount(orgTenderBean.getCalTAmount());
		tender.setCalMCount(orgTenderBean.getCalMCount());

		List<DenominationBean> denominations = DailyDeedTransformer.cloneDenominationList(orgTenderBean.getDenominations());
		tender.setDenominations(denominations);

		logger.info("The tender details has been cloned successfully");

		return tender;
	}

	public static List<DenominationBean> cloneDenominationList(List<DenominationBean> orgDenominationBeans) {
		List<DenominationBean> denominations = new ArrayList<>(orgDenominationBeans.size());
		DenominationBean denomination;
		for (DenominationBean orgDenominationBean : orgDenominationBeans) {
			denomination = DailyDeedTransformer.cloneDenomination(orgDenominationBean);
			denominations.add(denomination);
		}
		logger.info("The denomination details list has been cloned successfully");
		return denominations;
	}

	public static DenominationBean cloneDenomination(DenominationBean orgDenominationBean) {
		DenominationBean denomination = new DenominationBean();

		denomination.setDenominationId(orgDenominationBean.getDenominationId());
		denomination.setAmount(orgDenominationBean.getAmount());
		denomination.setMediaCount(orgDenominationBean.getMediaCount());

		logger.info("The denomination details has been cloned successfully");
		return denomination;
	}

	public static DailyDeedBean transformDailyTxn(Transaction txnDetails, TenderCount tenderCountDetails, List<Denomination> denominations) {

		List<BaseDenominationBean> denominationBeans = CommonMVCTransformer.transformDenominations(denominations);

		DailyDeedBean dailyDeedBean = new DailyDeedBean();
		dailyDeedBean.setDenominationList(denominationBeans);

		TransactionId txnId = txnDetails.getTransactionId();
		dailyDeedBean.setLocationId(txnId.getLocationId());
		dailyDeedBean.setBusinessDate(txnId.getBusinessDate());
		dailyDeedBean.setRegister(txnId.getRegister());

		if (tenderCountDetails != null) {
			List<TenderBean> tenders = DailyDeedTransformer.transformTenderCounts(tenderCountDetails);
			dailyDeedBean.setTenders(tenders);
		}

		logger.info("The daily transaction has been transformed to daily deed bean successfully");
		return dailyDeedBean;
	}

	public static List<TenderBean> transformTenderCounts(TenderCount tenderCountDetails) {
		List<TenderBean> tenders = new ArrayList<>(1);

		TenderBean tenderBean = new TenderBean();
		tenderBean.setTenderId(tenderCountDetails.getTenderCountId().getTenderId());
		tenderBean.setCalTAmount(tenderCountDetails.getAmount());
		tenderBean.setCalMCount(tenderCountDetails.getMediaCount());

		List<DenominationBean> denominations = DailyDeedTransformer.transformTenderDenominations(tenderCountDetails.getDenominations());
		tenderBean.setDenominations(denominations);

		tenders.add(tenderBean);

		logger.info("The txn tender Count has been transformed to tender bean successfully");
		return tenders;
	}

	public static List<DenominationBean> transformTenderDenominations(List<TenderDenomination> tenderDenomDetails) {
		List<DenominationBean> denominations = new ArrayList<>(tenderDenomDetails.size());
		DenominationBean denomination = null;
		for (TenderDenomination tenderDenomination : tenderDenomDetails) {
			denomination = new DenominationBean();
			denomination.setAmount(tenderDenomination.getAmount());
			denomination.setMediaCount(tenderDenomination.getMediaCount());
			denomination.setDenominationId(tenderDenomination.getTenderDenominationId().getDenominationId());
			denominations.add(denomination);
		}
		logger.info("The txn tender denominations has been transformed to denominations  bean successfully");

		return denominations;
	}
}
