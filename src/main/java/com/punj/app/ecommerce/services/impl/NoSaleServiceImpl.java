/**
 * 
 */
package com.punj.app.ecommerce.services.impl;

import java.math.BigInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.punj.app.ecommerce.domains.transaction.NoSaleTransaction;
import com.punj.app.ecommerce.domains.transaction.Transaction;
import com.punj.app.ecommerce.domains.transaction.ids.TransactionId;
import com.punj.app.ecommerce.repositories.transaction.NoSaleTenderRepository;
import com.punj.app.ecommerce.repositories.transaction.NoSaleTxnRepository;
import com.punj.app.ecommerce.services.NoSaleService;
import com.punj.app.ecommerce.services.TransactionService;
import com.punj.app.ecommerce.services.common.CommonService;
import com.punj.app.ecommerce.services.converter.TransactionConverter;

/**
 * @author admin
 *
 */
@Service
public class NoSaleServiceImpl implements NoSaleService {

	private static final Logger logger = LogManager.getLogger();
	private CommonService commonService;
	private TransactionService txnService;
	private NoSaleTxnRepository noSaleTxnRepository;
	private NoSaleTenderRepository noSaleTenderTxnRepository;

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
	 * @param noSaleTxnRepository
	 *            the noSaleTxnRepository to set
	 */
	@Autowired
	public void setNoSaleTxnRepository(NoSaleTxnRepository noSaleTxnRepository) {
		this.noSaleTxnRepository = noSaleTxnRepository;
	}

	/**
	 * @param noSaleTenderTxnRepository
	 *            the noSaleTenderTxnRepository to set
	 */
	@Autowired
	public void setNoSaleTenderRepository(NoSaleTenderRepository noSaleTenderTxnRepository) {
		this.noSaleTenderTxnRepository = noSaleTenderTxnRepository;
	}

	@Override
	@Transactional
	public NoSaleTransaction saveNoSaleTxn(NoSaleTransaction txnDetails) {

		Transaction baseTxn = TransactionConverter.convertNoSaleToTxn(txnDetails);
		baseTxn=this.txnService.saveTransaction(baseTxn);
		if(baseTxn!=null) {
			logger.info("The base transaction details has been saved for no sale transaction");
			txnDetails = this.noSaleTxnRepository.save(txnDetails);
			if (txnDetails != null) {
				logger.info("The no sale transaction with tender details has been saved.");
			} else {
				logger.info("There was some issue while saving the no sale txn details");
			}
		}else {
			logger.info("There was some issue while saving the no sale txn header details");
		}
		return txnDetails;
	}

}
