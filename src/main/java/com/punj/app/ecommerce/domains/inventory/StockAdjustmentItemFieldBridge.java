/**
 * 
 */
package com.punj.app.ecommerce.domains.inventory;

import java.math.BigInteger;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.Field.TermVector;
import org.apache.lucene.index.IndexableField;
import org.hibernate.search.bridge.LuceneOptions;
import org.hibernate.search.bridge.TwoWayFieldBridge;

import com.punj.app.ecommerce.domains.inventory.ids.StockAdjustmentItemId;

/**
 * @author admin
 *
 */
public class StockAdjustmentItemFieldBridge implements TwoWayFieldBridge {

	@Override
	public void set(String name, Object value, Document document, LuceneOptions luceneOptions) {

		StockAdjustmentItemId stockAdjustmentItemId = (StockAdjustmentItemId) value;
		Store store = luceneOptions.getStore();
		Index index = luceneOptions.getIndex();
		TermVector termVector = luceneOptions.getTermVector();
		Float boost = luceneOptions.getBoost();

		Field field = new Field(name + ".itemId", String.valueOf(stockAdjustmentItemId.getItemId()), store, index,
				termVector);
		field.setBoost(boost);
		document.add(field);

		field = new Field(name + ".reasonCodeId", String.valueOf(stockAdjustmentItemId.getReasonCodeId()), store, index,
				termVector);
		field.setBoost(boost);
		document.add(field);

		field = new Field(name, objectToString(stockAdjustmentItemId), store, index, termVector);
		field.setBoost(boost);
		document.add(field);

	}

	@Override
	public Object get(String name, Document document) {
		IndexableField fieldItemId = document.getField(name + ".itemId");
		IndexableField fieldReasonCodeId = document.getField(name + ".reasonCodeId");

		StockAdjustmentItemId stockAdjustmentItemId = new StockAdjustmentItemId();
		stockAdjustmentItemId.setItemId(new BigInteger(fieldItemId.stringValue()));
		stockAdjustmentItemId.setReasonCodeId(new Integer(fieldReasonCodeId.stringValue()));
		return stockAdjustmentItemId;
	}

	@Override
	public String objectToString(Object object) {
		StockAdjustmentItemId stockAdjustmentItemId = (StockAdjustmentItemId) object;
		StringBuilder stringData = new StringBuilder();
		stringData.append(stockAdjustmentItemId.getItemId());
		stringData.append(" ");
		stringData.append(stockAdjustmentItemId.getReasonCodeId());
		return stringData.toString();
	}

}
