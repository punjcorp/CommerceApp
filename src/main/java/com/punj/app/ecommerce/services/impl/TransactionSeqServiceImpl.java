/**
 * 
 */
package com.punj.app.ecommerce.services.impl;

import java.math.BigInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.punj.app.ecommerce.domains.transaction.Transaction;
import com.punj.app.ecommerce.domains.transaction.seqs.ExpenseVoucher;
import com.punj.app.ecommerce.domains.transaction.seqs.ReturnVoucher;
import com.punj.app.ecommerce.domains.transaction.seqs.SaleInvoice;
import com.punj.app.ecommerce.repositories.transaction.seqs.ExpenseVoucherRepository;
import com.punj.app.ecommerce.repositories.transaction.seqs.ReturnVoucherRepository;
import com.punj.app.ecommerce.repositories.transaction.seqs.SaleInvoiceRepository;
import com.punj.app.ecommerce.services.TransactionSeqService;
import com.punj.app.ecommerce.services.common.ServiceConstants;

/**
 * @author admin
 *
 */
@Service
public class TransactionSeqServiceImpl implements TransactionSeqService {

	private static final Logger logger = LogManager.getLogger();
	private SaleInvoiceRepository saleInvoiceRepository;
	private ReturnVoucherRepository returnVoucherRepository;
	private ExpenseVoucherRepository expenseVoucherRepository;

	/**
	 * @param saleInvoiceRepository
	 *            the saleInvoiceRepository to set
	 */
	@Autowired
	public void setSaleInvoiceRepository(SaleInvoiceRepository saleInvoiceRepository) {
		this.saleInvoiceRepository = saleInvoiceRepository;
	}

	/**
	 * @param returnVoucherRepository
	 *            the returnVoucherRepository to set
	 */
	@Autowired
	public void setReturnVoucherRepository(ReturnVoucherRepository returnVoucherRepository) {
		this.returnVoucherRepository = returnVoucherRepository;
	}

	/**
	 * @param expenseVoucherRepository
	 *            the expenseVoucherRepository to set
	 */
	@Autowired
	public void setExpenseVoucherRepository(ExpenseVoucherRepository expenseVoucherRepository) {
		this.expenseVoucherRepository = expenseVoucherRepository;
	}

	@Override
	public BigInteger saveTransactionSeqs(Transaction txnHeader) {
		BigInteger txnInvoiceNo = null;
		switch (txnHeader.getTxnType()) {
		case ServiceConstants.TXN_SALE:
			txnInvoiceNo=this.generateSaleInvoice(txnHeader);
			break;
		case ServiceConstants.TXN_RETURN:
			txnInvoiceNo=this.generateReturnVoucher(txnHeader);
			break;
		case ServiceConstants.TXN_NOSALE:
			txnInvoiceNo=this.generateExpenseVoucher(txnHeader);
			break;
		}
		return txnInvoiceNo;
	}
	
	private BigInteger generateSaleInvoice(Transaction txnHeader) {
		BigInteger txnInvoiceNo = null;
		SaleInvoice saleInvoice = new SaleInvoice();
		saleInvoice.setLocationId(txnHeader.getTransactionId().getLocationId());
		saleInvoice.setBusinessDate(txnHeader.getTransactionId().getBusinessDate());
		saleInvoice.setRegister(txnHeader.getTransactionId().getRegister());
		saleInvoice.setTransactionSeq(txnHeader.getTransactionId().getTransactionSeq());
		saleInvoice = this.saleInvoiceRepository.save(saleInvoice);
		if (saleInvoice != null) {
			txnInvoiceNo = saleInvoice.getInvoiceNo();
			logger.info("A unique invoice no {} for the sale transaction {} has been generated", txnInvoiceNo, txnHeader.getTransactionId());
		} else {
			logger.error("A unique invoice no generation for the sale transaction {} has failed!!", txnHeader.getTransactionId());
		}
		return txnInvoiceNo;
	}
	
	private BigInteger generateReturnVoucher(Transaction txnHeader) {
		BigInteger txnVoucherNo = null;
		ReturnVoucher returnVoucher = new ReturnVoucher();
		returnVoucher.setLocationId(txnHeader.getTransactionId().getLocationId());
		returnVoucher.setBusinessDate(txnHeader.getTransactionId().getBusinessDate());
		returnVoucher.setRegister(txnHeader.getTransactionId().getRegister());
		returnVoucher.setTransactionSeq(txnHeader.getTransactionId().getTransactionSeq());
		returnVoucher = this.returnVoucherRepository.save(returnVoucher);
		if (returnVoucher != null) {
			txnVoucherNo = returnVoucher.getVoucherNo();
			logger.info("A unique voucher no {} for the return transaction {} has been generated", txnVoucherNo, txnHeader.getTransactionId());
		} else {
			logger.error("A unique invoice no generation for the return transaction {} has failed!!", txnHeader.getTransactionId());
		}
		return txnVoucherNo;
	}
	
	private BigInteger generateExpenseVoucher(Transaction txnHeader) {
		BigInteger txnVoucherNo = null;
		ExpenseVoucher expenseVoucher = new ExpenseVoucher();
		expenseVoucher.setLocationId(txnHeader.getTransactionId().getLocationId());
		expenseVoucher.setBusinessDate(txnHeader.getTransactionId().getBusinessDate());
		expenseVoucher.setRegister(txnHeader.getTransactionId().getRegister());
		expenseVoucher.setTransactionSeq(txnHeader.getTransactionId().getTransactionSeq());
		expenseVoucher = this.expenseVoucherRepository.save(expenseVoucher);
		if (expenseVoucher != null) {
			txnVoucherNo = expenseVoucher.getVoucherNo();
			logger.info("A unique voucher no {} for the expense transaction {} has been generated", txnVoucherNo, txnHeader.getTransactionId());
		} else {
			logger.error("A unique invoice no generation for the expense transaction {} has failed!!", txnHeader.getTransactionId());
		}
		return txnVoucherNo;
	}
	
	public BigInteger retrieveSaleInvoiceNo(SaleInvoice saleInvoice) {
		BigInteger invoiceNo=null;
		saleInvoice=this.saleInvoiceRepository.findOne(Example.of(saleInvoice));
		if(saleInvoice!=null) {
			invoiceNo=saleInvoice.getInvoiceNo();
			logger.info("The sale invoice number has been successfully retrieved");
		}else {
			logger.error("The sale invoice number was not retrieved for the transaction");
		}
		return invoiceNo;
	}

}
