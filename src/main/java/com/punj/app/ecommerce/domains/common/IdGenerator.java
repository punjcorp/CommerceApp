package com.punj.app.ecommerce.domains.common;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "seq_generator")
public class IdGenerator implements Serializable {

	private static final long serialVersionUID = 2080206729912733173L;

	@Id
	private String name;
	@Column(name = "value")
	private BigInteger seq;

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
	 * @return the seq
	 */
	public BigInteger getSeq() {
		return seq;
	}

	/**
	 * @param seq
	 *            the seq to set
	 */
	public void setSeq(BigInteger seq) {
		this.seq = seq;
	}

}
