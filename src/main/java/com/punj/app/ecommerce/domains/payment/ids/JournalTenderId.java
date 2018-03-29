/**
 * 
 */
package com.punj.app.ecommerce.domains.payment.ids;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.punj.app.ecommerce.domains.payment.AccountJournal;

/**
 * @author admin
 *
 */

@Embeddable
public class JournalTenderId implements Serializable {

	private static final long serialVersionUID = -719473087701536330L;
	
	@ManyToOne
	@JoinColumn(name = "journal_id")
	private AccountJournal accountJournal;
	@Column(name = "tender_id")
	private Integer tenderId;

	
	/**
	 * @return the accountJournal
	 */
	public AccountJournal getAccountJournal() {
		return accountJournal;
	}

	/**
	 * @param accountJournal the accountJournal to set
	 */
	public void setAccountJournal(AccountJournal accountJournal) {
		this.accountJournal = accountJournal;
	}

	/**
	 * @return the tenderId
	 */
	public Integer getTenderId() {
		return tenderId;
	}

	/**
	 * @param tenderId
	 *            the tenderId to set
	 */
	public void setTenderId(Integer tenderId) {
		this.tenderId = tenderId;
	}

}
