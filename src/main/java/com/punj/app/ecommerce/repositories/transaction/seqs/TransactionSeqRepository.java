/**
 * 
 */
package com.punj.app.ecommerce.repositories.transaction.seqs;

import java.math.BigInteger;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.punj.app.ecommerce.services.common.ServiceConstants;

/**
 * @author admin
 *
 */
@Repository
public class TransactionSeqRepository {
	private static final Logger logger = LogManager.getLogger();
	@PersistenceContext
	private EntityManager entityManager;

	public Boolean decrementInvoiceNos(BigInteger invoiceNo) {

		// "p_create_txn_audit_version" this is the name of procedure
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("p_decrement_invoice_nos");

		// Declare the parameters in the same order
		query.registerStoredProcedureParameter(1, BigInteger.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(2, Boolean.class, ParameterMode.OUT);

		// Pass the parameter values
		query.setParameter(1, invoiceNo);

		// Execute query
		query.execute();
		logger.info("The p_decrement_invoice_nos procedure has been executed with {} invoice no", invoiceNo);
		// Get output parameters
		Boolean outCode = (Boolean) query.getOutputParameterValue(2);

		return outCode;
	}

	public Boolean incrementInvoiceNos(BigInteger invoiceNo) {

		// "p_create_txn_audit_version" this is the name of procedure
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("p_increment_invoice_nos");

		// Declare the parameters in the same order
		query.registerStoredProcedureParameter(1, BigInteger.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(2, Boolean.class, ParameterMode.OUT);

		// Pass the parameter values
		query.setParameter(1, invoiceNo);

		// Execute query
		query.execute();
		logger.info("The p_increment_invoice_nos procedure has been executed with {} invoice no", invoiceNo);
		// Get output parameters
		Boolean outCode = (Boolean) query.getOutputParameterValue(2);

		return outCode;
	}
	
	
	public Boolean alterInvoiceNoSequencer() {

		// "p_create_txn_audit_version" this is the name of procedure
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("p_alter_auto_increment");

		// Declare the parameters in the same order
		query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(2, Boolean.class, ParameterMode.OUT);
		// Pass the parameter values
		query.setParameter(1, ServiceConstants.TABLE_SALES_INVOICE);

		// Execute query
		query.execute();
		logger.info("The p_alter_auto_increment table auto increment has been altered successfully");
		// Get output parameters
		Boolean outCode = (Boolean) query.getOutputParameterValue(2);

		return outCode;
	}

}
