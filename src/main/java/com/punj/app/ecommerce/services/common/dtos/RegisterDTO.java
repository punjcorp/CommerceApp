/**
 * 
 */
package com.punj.app.ecommerce.services.common.dtos;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.punj.app.ecommerce.domains.common.Register;
import com.punj.app.ecommerce.domains.transaction.Transaction;

/**
 * @author admin
 *
 */
public class RegisterDTO implements Serializable {

	private static final long serialVersionUID = -2515666549310180164L;

	private List<Register> registers;
	private Map<Integer, Transaction> lastTxnStatus;

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

}
