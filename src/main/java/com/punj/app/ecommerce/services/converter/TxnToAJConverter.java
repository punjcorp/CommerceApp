/**
 * 
 */
package com.punj.app.ecommerce.services.converter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.punj.app.ecommerce.domains.order.Order;
import com.punj.app.ecommerce.domains.order.returns.OrderReturn;
import com.punj.app.ecommerce.domains.payment.AccountJournal;
import com.punj.app.ecommerce.domains.payment.JournalTender;
import com.punj.app.ecommerce.domains.payment.ids.JournalTenderId;
import com.punj.app.ecommerce.services.common.ServiceConstants;

/**
 * @author admin
 *
 */
public class TxnToAJConverter {

	private static final Logger logger = LogManager.getLogger();

	private TxnToAJConverter() {
		throw new IllegalStateException("TxnToAJConverter class");
	}

	public static AccountJournal convertOrderReturnAmounts(OrderReturn orderReturn, Integer accountId, Integer tenderId, String username) {
		AccountJournal accountJournal = new AccountJournal();
		accountJournal.setAccountId(accountId);
		accountJournal.setAmount(orderReturn.getTotalAmount());

		accountJournal.setCreatedBy(username);
		accountJournal.setCreatedDate(LocalDateTime.now());
		accountJournal.setJournalType(ServiceConstants.PAYMENT_CREDIT);

		accountJournal.setComments("Order : " + orderReturn.getOrderReturnId() + " Received on: " + LocalDateTime.now());

		List<JournalTender> journalTenders = new ArrayList<>();
		JournalTender journalTender = new JournalTender();

		JournalTenderId journalTenderId = new JournalTenderId();
		journalTenderId.setAccountJournal(accountJournal);
		journalTenderId.setTenderId(tenderId);
		journalTender.setJournalTenderId(journalTenderId);
		journalTender.setAmount(orderReturn.getTotalAmount());
		journalTender.setCreatedBy(accountJournal.getCreatedBy());
		journalTender.setCreatedDate(LocalDateTime.now());
		journalTender.setDescription("Order Tender is Cash By Default");

		journalTenders.add(journalTender);
		accountJournal.setJournalTenders(journalTenders);

		logger.info("The account journal object has been created from order return details successfully");

		return accountJournal;
	}

	public static AccountJournal convertReceiveOrderAmounts(Order order, Integer accountId, Integer tenderId) {

		AccountJournal accountJournal = new AccountJournal();
		accountJournal.setAccountId(accountId);
		accountJournal.setAmount(order.getActualTotalAmount());

		if (StringUtils.isNotBlank(order.getModifiedBy()))
			accountJournal.setCreatedBy(order.getModifiedBy());
		accountJournal.setCreatedDate(LocalDateTime.now());
		accountJournal.setJournalType(ServiceConstants.PAYMENT_DUE);

		accountJournal.setComments("Order :" + order.getOrderId() + " Received on: " + order.getModifiedDate());

		List<JournalTender> journalTenders = new ArrayList<>();
		JournalTender journalTender = new JournalTender();

		JournalTenderId journalTenderId = new JournalTenderId();
		journalTenderId.setAccountJournal(accountJournal);
		journalTenderId.setTenderId(tenderId);
		journalTender.setJournalTenderId(journalTenderId);
		journalTender.setAmount(order.getActualTotalAmount());
		journalTender.setCreatedBy(accountJournal.getCreatedBy());
		journalTender.setCreatedDate(LocalDateTime.now());
		journalTender.setDescription("Order Tender is Cash By Default");

		journalTenders.add(journalTender);
		accountJournal.setJournalTenders(journalTenders);

		logger.info("The account journal object has been created from order details successfully");

		return accountJournal;
	}

}