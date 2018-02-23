/**
 * 
 */
package com.punj.app.ecommerce.services.dtos.tender;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * @author admin
 *
 */
public class TenderDTO implements Serializable {

	private static final long serialVersionUID = 4975960172839022630L;

	private Integer tenderId;
	private String name;
	private String typeCode;

	private BigDecimal amount;
	private BigInteger mediaCount;

	private List<DenominationDTO> denominations;

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

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the typeCode
	 */
	public String getTypeCode() {
		return typeCode;
	}

	/**
	 * @param typeCode
	 *            the typeCode to set
	 */
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	/**
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * @return the mediaCount
	 */
	public BigInteger getMediaCount() {
		return mediaCount;
	}

	/**
	 * @param mediaCount
	 *            the mediaCount to set
	 */
	public void setMediaCount(BigInteger mediaCount) {
		this.mediaCount = mediaCount;
	}

	/**
	 * @return the denominations
	 */
	public List<DenominationDTO> getDenominations() {
		return denominations;
	}

	/**
	 * @param denominations
	 *            the denominations to set
	 */
	public void setDenominations(List<DenominationDTO> denominations) {
		this.denominations = denominations;
	}

}
