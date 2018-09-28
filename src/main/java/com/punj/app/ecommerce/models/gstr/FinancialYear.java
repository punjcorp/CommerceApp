/**
 * 
 */
package com.punj.app.ecommerce.models.gstr;

/**
 * @author admin
 *
 */
public class FinancialYear {

	private Integer finYearId;
	private String code;
	private String desc;

	/**
	 * @return the finYearId
	 */
	public Integer getFinYearId() {
		return finYearId;
	}

	/**
	 * @param finYearId
	 *            the finYearId to set
	 */
	public void setFinYearId(Integer finYearId) {
		this.finYearId = finYearId;
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
