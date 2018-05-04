/**
 * 
 */
package com.punj.app.ecommerce.services.converter;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.punj.app.ecommerce.domains.item.Attribute;

/**
 * @author admin
 *
 */
public class AttributeConverter {

	private static final Logger logger = LogManager.getLogger();

	private AttributeConverter() {
		throw new IllegalStateException("AttributeConverter class");
	}

	public static List<Attribute> convertDistinctAttributes(List<Attribute> attributeList) {
		List<Attribute> distinctAttributes = new ArrayList<>();
		List<String> distinctAttributeCodes = new ArrayList<>();
		Attribute distinctAttribute;
		for (Attribute attribute : attributeList) {
			if (!distinctAttributeCodes.contains(attribute.getCode())) {
				distinctAttributes.add(attribute);
				distinctAttributeCodes.add(attribute.getCode());
			}
		}
		logger.info("The duplicate attributes has been filtered successfully");
		return distinctAttributes;
	}

}
