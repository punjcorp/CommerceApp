/**
 * 
 */
package com.punj.app.ecommerce.utils;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.punj.app.ecommerce.models.transaction.TenderLineItem;

/**
 * @author amit
 *
 */
public class JSONCreator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TenderLineItem orderTenderLineItem = new TenderLineItem();
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(System.out, orderTenderLineItem);
			String jsonString = mapper.writeValueAsString(orderTenderLineItem);

			mapper.writeValue(new File("D:/repos/PI-POS-SHOP/references/orderTenderLineItem.json"), orderTenderLineItem);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
