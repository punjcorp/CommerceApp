/**
 * 
 */
package com.punj.app.ecommerce.repositories.transaction.audit;

import java.math.BigInteger;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 * @author admin
 *
 */
@Repository
public class TransactionAuditRepository {
	private static final Logger logger = LogManager.getLogger();
	@PersistenceContext
	private EntityManager entityManager;

	public Boolean createTxnAuditVersion(Integer locationId, String businessDate, Integer registerId, Integer txnNo) {

		// "p_create_txn_audit_version" this is the name of procedure
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("p_create_txn_audit_version");

		// Declare the parameters in the same order
		query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(4, Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(5, Boolean.class, ParameterMode.OUT);
		query.registerStoredProcedureParameter(6, BigInteger.class, ParameterMode.OUT);

		// Pass the parameter values
		query.setParameter(1, locationId);
		query.setParameter(2, businessDate);
		query.setParameter(3, registerId);
		query.setParameter(4, txnNo);

		// Execute query
		query.execute();
		logger.info("The procedure 'p_create_txn_audit_version' has been executed with input values {},-{},-{},-{}", locationId, businessDate, registerId, txnNo);
		// Get output parameters
		Boolean outCode = (Boolean) query.getOutputParameterValue(5);
		BigInteger auditVersion = (BigInteger) query.getOutputParameterValue(6);

		if (outCode != null && outCode && auditVersion.intValue() > 0)
			return true;
		else
			return false;
	}

	public Boolean createDailyTotalsAuditVersion(Integer locationId, String businessDate, Integer registerId) {

		// "p_create_txn_audit_version" this is the name of procedure
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("p_create_daily_totals_audit_version");

		// Declare the parameters in the same order
		query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(4, Boolean.class, ParameterMode.OUT);
		query.registerStoredProcedureParameter(5, BigInteger.class, ParameterMode.OUT);

		// Pass the parameter values
		query.setParameter(1, locationId);
		query.setParameter(2, businessDate);
		query.setParameter(3, registerId);

		// Execute query
		query.execute();
		logger.info("The procedure 'p_create_daily_totals_audit_version' has been executed with input values {},-{},-{}", locationId, businessDate, registerId);

		// Get output parameters
		Boolean outCode = (Boolean) query.getOutputParameterValue(4);
		BigInteger auditVersion = (BigInteger) query.getOutputParameterValue(5);

		if (outCode != null && outCode && auditVersion.intValue() > 0)
			return true;
		else
			return false;
	}

}
