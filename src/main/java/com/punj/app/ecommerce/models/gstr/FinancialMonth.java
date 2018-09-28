/**
 * 
 */
package com.punj.app.ecommerce.models.gstr;

/**
 * @author admin
 *
 */
public class FinancialMonth {

	private Integer finMonthId;
	private String code;
	private String desc;

	/**
	 * @return the finMonthId
	 */
	public Integer getFinMonthId() {
		return finMonthId;
	}

	/**
	 * @param finMonthId
	 *            the finMonthId to set
	 */
	public void setFinMonthId(Integer finMonthId) {
		this.finMonthId = finMonthId;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc
	 *            the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

}
