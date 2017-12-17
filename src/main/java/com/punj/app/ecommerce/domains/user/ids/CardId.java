/**
 * 
 */
package com.punj.app.ecommerce.domains.user.ids;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author admin
 *
 */
@Embeddable
public class CardId implements Serializable {

	private String username;
	@Column(name = "card_no")
	private String cardNo;

	public CardId() {

	}

	public CardId(String username, String cardNo) {
		this.username = username;
		this.cardNo = cardNo;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the cardNo
	 */
	public String getCardNo() {
		return cardNo;
	}

	/**
	 * @param cardNo the cardNo to set
	 */
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}


}
