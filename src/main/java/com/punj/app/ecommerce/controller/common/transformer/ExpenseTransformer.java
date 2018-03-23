/**
 * 
 */
package com.punj.app.ecommerce.controller.common.transformer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.domains.transaction.NoSaleTender;
import com.punj.app.ecommerce.domains.transaction.NoSaleTransaction;
import com.punj.app.ecommerce.domains.transaction.ids.NoSaleId;
import com.punj.app.ecommerce.domains.transaction.ids.TransactionId;
import com.punj.app.ecommerce.models.nosale.ExpenseBean;
import com.punj.app.ecommerce.models.nosale.ExpenseTenderBean;

/**
 * @author admin
 *
 */
public class ExpenseTransformer {
	private static final Logger logger = LogManager.getLogger();

	private ExpenseTransformer() {
		throw new IllegalStateException("ExpenseTransformer class");
	}

	public static NoSaleTransaction transformExpenseDetails(ExpenseBean expenseBean, String username) {
		NoSaleTransaction noSaleTxn = new NoSaleTransaction();
		TransactionId txnId = new TransactionId();
		txnId.setBusinessDate(expenseBean.getBusinessDate());
		txnId.setLocationId(expenseBean.getLocationId());
		txnId.setRegister(expenseBean.getRegisterId());
		txnId.setTransactionSeq(expenseBean.getTxnNo());

		noSaleTxn.setTransactionId(txnId);
		noSaleTxn.setAmount(expenseBean.getExpenseAmount());
		noSaleTxn.setCreatedBy(username);
		noSaleTxn.setCreatedDate(LocalDateTime.now());
		noSaleTxn.setNoSaleType(expenseBean.getExpenseType());
		noSaleTxn.setReasonCode(expenseBean.getExpenseType());
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

		noSaleTender.setNoSaleId(noSaleId);
		noSaleTender.setAmount(expenseTenderBean.getTenderAmount());
		noSaleTender.setCreatedBy(username);
		noSaleTender.setToAccountNo(expenseTenderBean.getAccountNo());
		noSaleTender.setToBankBranch(expenseTenderBean.getBankBranch());
		noSaleTender.setToBankName(expenseTenderBean.getBankName());
		noSaleTender.setToPayeeName(expenseTenderBean.getPayeeName());
		noSaleTender.setToPayeePhone(expenseTenderBean.getPayeePhone());

		logger.info("The expense tender details has been transformed to no sale tender");
		return noSaleTender;
	}

}
