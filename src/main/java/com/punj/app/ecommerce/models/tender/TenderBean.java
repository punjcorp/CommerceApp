/**
 * 
 */
package com.punj.app.ecommerce.models.tender;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

/**
 * @author admin
 *
 */
public class TenderBean {

	private Integer tenderId;
	private String name;
	private String tndrType;
	private String description;
	private Integer subTenderId;

	private BigDecimal calTAmount;
	private BigInteger calMCount;
	@Valid
	private List<DenominationBean> denominations;

	public TenderBean() {
		denominations = new ArrayList<>();
		denominations.add(new DenominationBean());
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
	 * @return the tndrType
	 */
	public String getTndrType() {
		return tndrType;
	}

	/**
	 * @param tndrType
	 *            the tndrType to set
	 */
	public void setTndrType(String tndrType) {
		this.tndrType = tndrType;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the subTenderId
	 */
	public Integer getSubTenderId() {
		return subTenderId;
	}

	/**
	 * @param subTenderId
	 *            the subTenderId to set
	 */
	public void setSubTenderId(Integer subTenderId) {
		this.subTenderId = subTenderId;
	}

	/**
	 * @return the calTAmount
	 */
	public BigDecimal getCalTAmount() {
		return calTAmount;
	}

	/**
	 * @param calTAmount
	 *            the calTAmount to set
	 */
	public void setCalTAmount(BigDecimal calTAmount) {
		this.calTAmount = calTAmount;
	}

	/**
	 * @return the calMCount
	 */
	public BigInteger getCalMCount() {
		return calMCount;
	}

	/**
	 * @param calMCount
	 *            the calMCount to set
	 */
	public void setCalMCount(BigInteger calMCount) {
		this.calMCount = calMCount;
	}

	/**
	 * @return the denominations
	 */
	public List<DenominationBean> getDenominations() {
		return denominations;
	}

	/**
	 * @param denominations
	 *            the denominations to set
	 */
	public void setDenominations(List<DenominationBean> denominations) {
		this.denominations = denominations;
	}

}
