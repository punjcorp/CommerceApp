package com.punj.app.ecommerce.models.dailydeeds.validator;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.domains.transaction.tender.TenderCount;
import com.punj.app.ecommerce.models.dailydeeds.DailyDeedBean;
import com.punj.app.ecommerce.models.tender.TenderBean;
import com.punj.app.ecommerce.services.DailyDeedService;
import com.punj.app.ecommerce.services.FinanceService;

@Component
public class RegisterOpenValidator implements Validator {

	private static final Logger logger = LogManager.getLogger();
	private FinanceService financeService;
	private DailyDeedService dailyDeedService;

	/**
	 * @param dailyDeedService
	 *            the dailyDeedService to set
	 */
	@Autowired
	public void setDailyDeedService(DailyDeedService dailyDeedService) {
		this.dailyDeedService = dailyDeedService;
	}

	/**
	 * @param financeService
	 *            the financeService to set
	 */
	@Autowired
	public void setFinanceService(FinanceService financeService) {
		this.financeService = financeService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return DailyDeedBean.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		logger.info("The Register open validation has started");
		DailyDeedBean dailyDeedBean = (DailyDeedBean) obj;

		List<TenderCount> tenderCounts = this.dailyDeedService.searchTxnTenderCounts(dailyDeedBean.getLocationId(), dailyDeedBean.getRegister(),
				dailyDeedBean.getBusinessDate(), MVCConstants.TXN_OPEN_STORE);

		int counter = 0;

		List<TenderBean> tenderList = dailyDeedBean.getTenders();
		for (TenderBean tenderBean : tenderList) {

			for (TenderCount tenderCount : tenderCounts) {
				if (tenderBean.getTenderId().equals(tenderCount.getTenderCountId().getTender().getTenderId())) {
					if (tenderBean.getCalTAmount().compareTo(tenderCount.getAmount()) > 0) {
						errors.rejectValue("tenders[" + counter + "].calTAmount", "commerce.error.register.open.greater.store.amounts",
								new Object[] { tenderCount.getAmount(), tenderCount.getTenderCountId().getTender().getName() },
								"commerce.error.register.open.greater.store.amounts");
						logger.info("The Register open validation has found an error");
					}

				}
				counter++;
			}

		}

		logger.info("The Register open validation has ended");

	}

}
