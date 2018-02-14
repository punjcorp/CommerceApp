/**
 * 
 */
package com.punj.app.ecommerce.controller.common.transformer;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author admin
 *
 */
public class CommonMVCTransformer {

	private static final Logger logger = LogManager.getLogger();

	private CommonMVCTransformer() {
		throw new IllegalStateException("CommonBeanTransformer class");
	}

	public static Map<BigInteger, Integer> transformSelectedIds(List<String> selectedIds) {
		Map<BigInteger, Integer> idIndex = new HashMap<>(selectedIds.size());
		String[] splittedData = null;
		for (String id : selectedIds) {
			splittedData = id.split("_");
			idIndex.put(new BigInteger(splittedData[0]), new Integer(splittedData[1]));
		}

		logger.info("The selected ids and list index from management page has been seperated");
		return idIndex;
	}

}
