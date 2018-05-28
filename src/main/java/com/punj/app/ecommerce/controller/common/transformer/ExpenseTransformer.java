/**
 * 
 */
package com.punj.app.ecommerce.controller.common.transformer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.domains.common.ReasonCode;
import com.punj.app.ecommerce.domains.tender.Tender;
import com.punj.app.ecommerce.domains.transaction.NoSaleTender;
import com.punj.app.ecommerce.domains.transaction.NoSaleTransaction;
import com.punj.app.ecommerce.domains.transaction.Transaction;
import com.punj.app.ecommerce.domains.transaction.ids.NoSaleId;
import com.punj.app.ecommerce.domains.transaction.ids.TransactionId;
import com.punj.app.ecommerce.models.nosale.ExpenseBean;
import com.punj.app.ecommerce.models.nosale.ExpenseTenderBean;
import com.punj.app.ecommerce.services.common.ServiceConstants;

/**
 * @author admin
 *
 */
public class ExpenseTransformer {
	private static final Logger logger = LogManager.getLogger();

	private ExpenseTransformer() {
		throw new IllegalStateException("ExpenseTransformer class");
	}

	public static Transaction transformExpenseToTxn(ExpenseBean expenseBean, String username) {
		Transaction txn = new Transaction();
		TransactionId txnId = new TransactionId();
		txnId.setBusinessDate(expenseBean.getBusinessDate());
		txnId.setLocationId(expenseBean.getLocationId());
		txnId.setRegister(expenseBean.getRegisterId());
		txnId.setTransactionSeq(expenseBean.getTxnNo());

		txn.setTransactionId(txnId);

		txn.setCreatedBy(username);
		txn.setCreatedDate(LocalDateTime.now());

		txn.setStartTime(expenseBean.getStartTime());
		txn.setEndTime(expenseBean.getEndTime());

		txn.setStatus(ServiceConstants.TXN_STATUS_COMPLETED);
		txn.setDiscountTotalAmt(BigDecimal.ZERO);
		txn.setTaxTotalAmt(BigDecimal.ZERO);

		txn.setSubTotalAmt(expenseBean.getExpenseAmount());
		txn.setTotalAmt(expenseBean.getExpenseAmount());
		txn.setComments(expenseBean.getRemarks());

		txn.setTxnType(ServiceConstants.TXN_NOSALE);
		logger.info("The transaction object has been successfully created from Expense Details");
		return txn;
	}

	public static NoSaleTransaction transformExpenseDetails(ExpenseBean expenseBean, TransactionId txnId, String username) {
		NoSaleTransaction noSaleTxn = new NoSaleTransaction();

		noSaleTxn.setTransactionId(txnId);
		noSaleTxn.setAmount(expenseBean.getExpenseAmount());
		noSaleTxn.setCreatedBy(username);
		noSaleTxn.setCreatedDate(LocalDateTime.now());
		noSaleTxn.setNoSaleType(expenseBean.getExpenseType());

		ReasonCode reasonCode = new ReasonCode();
		reasonCode.setReasonCodeId(expenseBean.getExpenseId());

		noSaleTxn.setReasonCode(reasonCode);
		noSaleTxn.setStatus(MVCConstants.COMPLETED);

		List<NoSaleTender> noSaleTenders = ExpenseTransformer.transformExpenseTenders(expenseBean.getExpenseTenders(), txnId, username);
		noSaleTxn.setNoSaleTenders(noSaleTenders);
		logger.info("The expense details has been transformed to no sale transaction");
		return noSaleTxn;
	}

	public static List<NoSaleTender> transformExpenseTenders(List<ExpenseTenderBean> expenseTenderBeans, TransactionId txnId, String username) {
		List<NoSaleTender> noSaleTenders = new ArrayList<>(expenseTenderBeans.size());
		NoSaleTender noSaleTender = null;
		for (ExpenseTenderBean expenseTenderBean : expenseTenderBeans) {
			noSaleTender = ExpenseTransformer.transformExpenseTenderDetails(expenseTenderBean, txnId, username);
			noSaleTenders.add(noSaleTender);
		}
		logger.info("The expense tender detail list has been transformed to no sale tender list");
		return noSaleTenders;
	}

	public static NoSaleTender transformExpenseTenderDetails(ExpenseTenderBean expenseTenderBean, TransactionId txnId, String username) {
		NoSaleTender noSaleTender = new NoSaleTender();

		NoSaleId noSaleId = new NoSaleId();
		noSaleId.setCreatedDate(LocalDateTime.now());

		noSaleId.setTenderId(expenseTenderBean.getTenderId());

		noSaleId.setTxnId(txnId);

		noSaleTender.setSeqNo(expenseTenderBean.getSeqNo());

		noSaleTender.setNoSaleId(noSaleId);
		noSaleTender.setAmount(expenseTenderBean.getTenderAmount());
		noSaleTender.setCreatedBy(username);

		noSaleTender.setToAccountNo(expenseTenderBean.getAccountNo());
		noSaleTender.setToBankBranch(expenseTenderBean.getBankBranch());
		noSaleTender.setToBankName(expenseTenderBean.getBankName());
		noSaleTender.setToPayeeName(expenseTenderBean.getPayeeName());
		noSaleTender.setToPayeePhone(expenseTenderBean.getPayeePhone());

		noSaleTender.setToDetails(expenseTenderBean.getTenderDetails());

		logger.info("The expense tender details has been transformed to no sale tender");
		return noSaleTender;
	}

	public static ExpenseBean transformExpenseNoSaleTransaction(NoSaleTransaction noSaleTxn, Map<Integer, Tender> tenderMap, String comments) {
		ExpenseBean expenseBean = new ExpenseBean();

		expenseBean.setLocationId(noSaleTxn.getTransactionId().getLocationId());
		expenseBean.setRegisterId(noSaleTxn.getTransactionId().getRegister());
		expenseBean.setBusinessDate(noSaleTxn.getTransactionId().getBusinessDate());
		expenseBean.setTxnNo(noSaleTxn.getTransactionId().getTransactionSeq());
		expenseBean.setUniqueTxnNo(noSaleTxn.getTransactionId().toString());

		expenseBean.setExpenseAmount(noSaleTxn.getAmount());
		expenseBean.setExpenseId(noSaleTxn.getReasonCode().getReasonCodeId());
		expenseBean.setExpenseType(noSaleTxn.getReasonCode().getName());
		expenseBean.setRemarks(comments);

		expenseBean.setCreatedBy(noSaleTxn.getCreatedBy());
		expenseBean.setCreatedDate(noSaleTxn.getCreatedDate());

		List<ExpenseTenderBean> expenseTenders = ExpenseTransformer.transformNoSaleTenders(noSaleTxn.getNoSaleTenders(), tenderMap, expenseBean);
		expenseBean.setExpenseTenders(expenseTenders);

		logger.info("The expense No Sale Txn details has been transformed to expense bean successfully");
		return expenseBean;
	}

	public static List<ExpenseTenderBean> transformNoSaleTenders(List<NoSaleTender> noSaleTenders, Map<Integer, Tender> tenderMap, ExpenseBean expenseBean) {
		List<ExpenseTenderBean> expenseTenders = new ArrayList<>(noSaleTenders.size());
		ExpenseTenderBean expenseTenderBean = null;
		String payeeName = null;
		String payeePhone = null;
		for (NoSaleTender noSaleTender : noSaleTenders) {
			expenseTenderBean = ExpenseTransformer.transformNoSaleTender(noSaleTender, tenderMap);
			expenseTenders.add(expenseTenderBean);
			payeeName = expenseTenderBean.getPayeeName();
			payeePhone = expenseTenderBean.getPayeePhone();
		}
		expenseBean.setToPayeeName(payeeName);
		expenseBean.setToPayeePhone(payeePhone);
		logger.info("The expense No Sale Tender list details has been transformed to expense tender bean list successfully");
		return expenseTenders;
	}

	public static ExpenseTenderBean transformNoSaleTender(NoSaleTender noSaleTender, Map<Integer, Tender> tenderMap) {
		ExpenseTenderBean expenseTenderBean = new ExpenseTenderBean();

		Tender tender=tenderMap.get(noSaleTender.getNoSaleId().getTenderId());
		
		expenseTenderBean.setTenderId(noSaleTender.getNoSaleId().getTenderId());
		expenseTenderBean.setName(tender.getName());
		expenseTenderBean.setTypeCode(tender.getType());
		expenseTenderBean.setSeqNo(noSaleTender.getSeqNo());

		expenseTenderBean.setPayeeName(noSaleTender.getToPayeeName());
		expenseTenderBean.setPayeePhone(noSaleTender.getToPayeePhone());
		expenseTenderBean.setTenderAmount(noSaleTender.getAmount());
		expenseTenderBean.setTenderDetails(noSaleTender.getToDetails());

		expenseTenderBean.setAccountNo(noSaleTender.getToAccountNo());
		expenseTenderBean.setBankBranch(noSaleTender.getToAccountNo());
		expenseTenderBean.setBankName(noSaleTender.getToBankName());
		expenseTenderBean.setCreatedBy(noSaleTender.getCreatedBy());
		expenseTenderBean.setCreatedDate(noSaleTender.getNoSaleId().getCreatedDate());

		logger.info("The expense No Sale Tender details has been transformed to expense tender bean successfully");
		return expenseTenderBean;
	}

}
