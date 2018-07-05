/**
 * 
 */
package com.punj.app.ecommerce.services.common.dtos;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.punj.app.ecommerce.domains.common.Register;
import com.punj.app.ecommerce.domains.finance.DailyTotals;
import com.punj.app.ecommerce.domains.transaction.Transaction;
import com.punj.app.ecommerce.domains.transaction.tender.TenderCount;

/**
 * @author admin
 *
 */
public class RegisterDTO implements Serializable {

	private static final long serialVersionUID = -2515666549310180164L;

	private List<Register> registers;
	private Map<Integer, Transaction> lastTxnStatus;
	private Map<Integer, DailyTotals> regTotals;
	private Map<Integer, List<TenderCount>> regTenderTotals;
	private Boolean allRegisterClosed;

	/**
	 * @return the registers
	 */
	public List<Register> getRegisters() {
		return registers;
	}

	/**
	 * @param registers
	 *            the registers to set
	 */
	public void setRegisters(List<Register> registers) {
		this.registers = registers;
	}

	/**
	 * @return the lastTxnStatus
	 */
	public Map<Integer, Transaction> getLastTxnStatus() {
		return lastTxnStatus;
	}

	/**
	 * @param lastTxnStatus
	 *            the lastTxnStatus to set
	 */
	public void setLastTxnStatus(Map<Integer, Transaction> lastTxnStatus) {
		this.lastTxnStatus = lastTxnStatus;
	}

	/**
	 * @return the regTotals
	 */
	public Map<Integer, DailyTotals> getRegTotals() {
		return regTotals;
	}

	/**
	 * @param regTotals
	 *            the regTotals to set
	 */
	public void setRegTotals(Map<Integer, DailyTotals> regTotals) {
		this.regTotals = regTotals;
	}

	/**
	 * @return the regTenderTotals
	 */
	public Map<Integer, List<TenderCount>> getRegTenderTotals() {
		return regTenderTotals;
	}

	/**
	 * @param regTenderTotals
	 *            the regTenderTotals to set
	 */
	public void setRegTenderTotals(Map<Integer, List<TenderCount>> regTenderTotals) {
		this.regTenderTotals = regTenderTotals;
	}

	/**
	 * @return the allRegisterClosed
	 */
	public Boolean getAllRegisterClosed() {
		return allRegisterClosed;
	}

	/**
	 * @param allRegisterClosed the allRegisterClosed to set
	 */
	public void setAllRegisterClosed(Boolean allRegisterClosed) {
		this.allRegisterClosed = allRegisterClosed;
	}

}
