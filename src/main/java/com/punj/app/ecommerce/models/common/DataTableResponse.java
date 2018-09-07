/**
 * 
 */
package com.punj.app.ecommerce.models.common;

import java.util.List;

/**
 * @author admin
 *
 */
public class DataTableResponse {

	private Integer draw;
	private Integer recordTotal;
	private Integer recordFiltered;
	private List<Object> data;

	/**
	 * @return the draw
	 */
	public Integer getDraw() {
		return draw;
	}

	/**
	 * @param draw
	 *            the draw to set
	 */
	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	/**
	 * @return the recordTotal
	 */
	public Integer getRecordTotal() {
		return recordTotal;
	}

	/**
	 * @param recordTotal
	 *            the recordTotal to set
	 */
	public void setRecordTotal(Integer recordTotal) {
		this.recordTotal = recordTotal;
	}

	/**
	 * @return the recordFiltered
	 */
	public Integer getRecordFiltered() {
		return recordFiltered;
	}

	/**
	 * @param recordFiltered
	 *            the recordFiltered to set
	 */
	public void setRecordFiltered(Integer recordFiltered) {
		this.recordFiltered = recordFiltered;
	}

	/**
	 * @return the data
	 */
	public List<Object> getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(List<Object> data) {
		this.data = data;
	}

}
