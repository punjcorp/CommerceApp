/**
 * 
 */
package com.punj.app.ecommerce.domains.order;

import java.math.BigInteger;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.Field.TermVector;
import org.apache.lucene.index.IndexableField;
import org.hibernate.search.bridge.LuceneOptions;
import org.hibernate.search.bridge.TwoWayFieldBridge;

import com.punj.app.ecommerce.domains.order.ids.OrderItemId;

/**
 * @author admin
 *
 */
public class OrderItemFieldBridge implements TwoWayFieldBridge {

	@Override
	public void set(String name, Object value, Document document, LuceneOptions luceneOptions) {

		OrderItemId orderItemId = (OrderItemId) value;
		Store store = luceneOptions.getStore();
		Index index = luceneOptions.getIndex();
		TermVector termVector = luceneOptions.getTermVector();
		Float boost = luceneOptions.getBoost();

		Field field = new Field(name + ".locationId", String.valueOf(orderItemId.getLocation()), // store each sub
																									// property in a
																									// field
				store, index, termVector);
		field.setBoost(boost);
		document.add(field);

		field = new Field(name + ".itemId", String.valueOf(orderItemId.getItemId()), store, index, termVector);
		field.setBoost(boost);
		document.add(field);

		field = new Field(name, objectToString(orderItemId), // store unique representation in named field
				store, index, termVector);
		field.setBoost(boost);
		document.add(field);

		// set location
		// luceneOptions.addFieldToDocument(name + ".locationId",
		// String.valueOf(orderItemId.getLocation()), document);
		// set item
		// luceneOptions.addFieldToDocument(name + ".itemId",
		// String.valueOf(orderItemId.getItemId()), document);
	}

	@Override
	public Object get(String name, Document document) {
		IndexableField fieldLocation = document.getField(name + ".locationId");
		IndexableField fieldItemId = document.getField(name + ".itemId");

		OrderItemId orderItemId = new OrderItemId();
		orderItemId.setLocation(new Integer(fieldLocation.stringValue()));
		orderItemId.setItemId(new BigInteger(fieldItemId.stringValue()));
		return orderItemId;
	}

	@Override
	public String objectToString(Object object) {
		OrderItemId orderItemId = (OrderItemId) object;
		StringBuilder stringData = new StringBuilder();
		stringData.append(orderItemId.getLocation());
		stringData.append(" ");
		stringData.append(orderItemId.getItemId());
		return stringData.toString();
	}

}
