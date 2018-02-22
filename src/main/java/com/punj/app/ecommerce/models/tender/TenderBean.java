/**
 * 
 */
package com.punj.app.ecommerce.models.tender;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.punj.app.ecommerce.controller.common.MVCConstants;

/**
 * @author admin
 *
 */
public class TenderBean {

	private Integer tenderId;
	private String name;
	private String tndrType = MVCConstants.TNDR_CASH;
	private String description;
	private Integer subTenderId;

	private BigDecimal tenderAmount;
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
	 * @return the tenderAmount
	 */
	public BigDecimal getTenderAmount() {
		return tenderAmount;
	}

	/**
	 * @param tenderAmount
	 *            the tenderAmount to set
	 */
	public void setTenderAmount(BigDecimal tenderAmount) {
		this.tenderAmount = tenderAmount;
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
