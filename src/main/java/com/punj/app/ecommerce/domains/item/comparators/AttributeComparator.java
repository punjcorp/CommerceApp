/**
 * 
 */
package com.punj.app.ecommerce.domains.item.comparators;

import java.util.Comparator;

import com.punj.app.ecommerce.domains.item.Attribute;

/**
 * @author admin
 *
 */
public class AttributeComparator implements Comparator<Attribute> {

	@Override
	public int compare(Attribute attribute1, Attribute attribute2) {

		// all comparison
		int code = attribute1.getCode().compareTo(attribute2.getCode());
		int seqNo = attribute1.getValSeqNo().compareTo(attribute2.getValSeqNo());

		if (code == 0) {
			return seqNo;
		} else {
			return code;
		}
	}

}
