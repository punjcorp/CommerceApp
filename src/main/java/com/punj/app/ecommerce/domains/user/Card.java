package com.punj.app.ecommerce.domains.user;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.punj.app.ecommerce.domains.user.ids.CardId;

@Entity
@Table(name = "user_credit_debit")
public class Card {
	@EmbeddedId
	private CardId cardId;
	@OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
	private Address address;
	@Column(name = "name_on_card")
	private String nameOnCard;
	@Column(name = "expiry_date")
	private String expiryDate;
	@Column(name = "cvv_no")
	private Integer cvvNo;
	
//	@Column(name = "address_id")
//	private BigInteger addressId;

	public Card() {
		this.cardId = new CardId();
	}

	public Card(CardId cardId, String nameOnCard, String expiryDate, Integer cvvNo) {
		this.cardId = cardId;
		this.nameOnCard = nameOnCard;
		this.expiryDate = expiryDate;
		this.cvvNo = cvvNo;
	}

	/**
	 * @return the cardId
	 */
	public CardId getCardId() {
		return cardId;
	}

	/**
	 * @param cardId
	 *            the cardId to set
	 */
	public void setCardId(CardId cardId) {
		this.cardId = cardId;
	}

	/**
	 * @return the nameOnCard
	 */
	public String getNameOnCard() {
		return nameOnCard;
	}

	/**
	 * @param nameOnCard
	 *            the nameOnCard to set
	 */
	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}

	/**
	 * @return the expiryDate
	 */
	public String getExpiryDate() {
		return expiryDate;
	}

	/**
	 * @param expiryDate
	 *            the expiryDate to set
	 */
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	/**
	 * @return the cvvNo
	 */
	public Integer getCvvNo() {
		return cvvNo;
	}

	/**
	 * @param cvvNo
	 *            the cvvNo to set
	 */
	public void setCvvNo(Integer cvvNo) {
		this.cvvNo = cvvNo;
	}

	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

/*	*//**
	 * @return the addressId
	 *//*
	public BigInteger getAddressId() {
		return addressId;
	}

	*//**
	 * @param addressId the addressId to set
	 *//*
	public void setAddressId(BigInteger addressId) {
		this.addressId = addressId;
	}
*/	
	

}
