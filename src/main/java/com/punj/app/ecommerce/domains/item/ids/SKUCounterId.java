package com.punj.app.ecommerce.domains.item.ids;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.punj.app.ecommerce.domains.item.StyleCounter;

@Embeddable
public class SKUCounterId implements Serializable {
	
	private static final long serialVersionUID = -3763829412400209829L;
	private Integer color;
	private Integer size;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "style_id")
	private StyleCounter styleCounter;

	public SKUCounterId() {
	}

	public SKUCounterId(Integer color, Integer size, StyleCounter styleCounter) {
		this.color = color;
		this.size = size;
		this.styleCounter = styleCounter;
	}

	/**
	 * @return the color
	 */
	public Integer getColor() {
		return color;
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColor(Integer color) {
		this.color = color;
	}

	/**
	 * @return the size
	 */
	public Integer getSize() {
		return size;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public void setSize(Integer size) {
		this.size = size;
	}

	/**
	 * @return the styleCounter
	 */
	public StyleCounter getStyleCounter() {
		return styleCounter;
	}

	/**
	 * @param styleCounter
	 *            the styleCounter to set
	 */
	public void setStyleCounter(StyleCounter styleCounter) {
		this.styleCounter = styleCounter;
	}

}
