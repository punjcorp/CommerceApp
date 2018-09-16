/**
 * 
 */
package com.punj.app.ecommerce.services.impl;

import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.punj.app.ecommerce.domains.transaction.ids.TransactionId;
import com.punj.app.ecommerce.repositories.transaction.audit.TransactionAuditRepository;
import com.punj.app.ecommerce.services.TransactionAuditService;
import com.punj.app.ecommerce.services.TransactionService;
import com.punj.app.ecommerce.services.common.CommonService;

/**
 * @author admin
 *
 */
@Service
public class TransactionAuditServiceImpl implements TransactionAuditService {

	private static final Logger logger = LogManager.getLogger();
	private CommonService commonService;
	private TransactionService txnService;
	private TransactionAuditRepository txnAuditRepository;

	/**
	 * @param commonService
	 *            the commonService to set
	 */
	@Autowired
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	/**
	 * @param txnService
	 *            the txnService to set
	 */
	@Autowired
	public void setTxnService(TransactionService txnService) {
		this.txnService = txnService;
	}

	/**
	 * @param txnAuditRepository
	 *            the txnAuditRepository to set
	 */
	@Autowired
	public void setTxnAuditRepository(TransactionAuditRepository txnAuditRepository) {
		this.txnAuditRepository = txnAuditRepository;
	}

	@Override
	public Boolean createTransactionAuditVersion(TransactionId txnId) {

		Boolean result = this.txnAuditRepository.createTxnAuditVersion(txnId.getLocationId(), txnId.getBusinessDate().toString(), txnId.getRegister(), txnId.getTransactionSeq());
		logger.info("The transaction {} audit version has been created successfully", txnId.toString());
		return result;
	}

	@Override
	public Boolean createDailyTotalAuditVersion(Integer locationId, LocalDateTime businessDate, Integer registerId) {
		Boolean result = this.txnAuditRepository.createDailyTotalsAuditVersion(locationId, businessDate.toString(), registerId);
		logger.info("The daily totals audit version has been created successfully");
		return result;
	}

}
