/**
 * 
 */
package com.punj.app.ecommerce.repositories.order.returns;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.WhitespaceTokenizerFactory;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;

import com.punj.app.ecommerce.domains.order.Order;
import com.punj.app.ecommerce.domains.order.returns.OrderReturn;
import com.punj.app.ecommerce.domains.order.returns.OrderReturnDTO;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
@Repository
@Transactional
public class ReturnSearchRepository {
	// Spring will inject here the entity manager object
	@PersistenceContext
	private EntityManager entityManager;

	@AnalyzerDef(name = "edgeNGram_query", tokenizer = @TokenizerDef(factory = WhitespaceTokenizerFactory.class), filters = {
			@TokenFilterDef(factory = LowerCaseFilterFactory.class) // Lowercase all characters
	})

	/**
	 * A basic search for the entity User. The search is done by exact match per keywords on fields name, city and email.
	 * 
	 * @param text
	 *            The query text.
	 */
	public OrderReturnDTO search(String text, Pager pager) {

		OrderReturnDTO orderReturnDTO = new OrderReturnDTO();
		// get the full text entity manager
		FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(entityManager);

		// create the query using Hibernate Search query DSL
		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(OrderReturn.class).get();

		// a very basic query by keywords
		org.apache.lucene.search.Query query = queryBuilder.keyword().onFields("orderReturnId", "comments", "createdBy", "order.supplier.supplierId", "order.supplier.name", "order.orderId", "order.location.locationId", "order.location.name", "orderReturnItems.itemId", "orderReturnItems.itemDesc").matching(text).createQuery();

		// wrap Lucene query in an Hibernate Query object
		org.hibernate.search.jpa.FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query, OrderReturn.class);

		jpaQuery.setFirstResult(pager.getStartCount());
		jpaQuery.setMaxResults(pager.getPageSize());

		// execute search and return results (sorted by relevance as default)
		@SuppressWarnings("unchecked")
		List<OrderReturn> results = jpaQuery.getResultList();

		pager.setResultSize(jpaQuery.getResultSize());
		orderReturnDTO.setOrderReturns(results);

		orderReturnDTO.setPager(pager);
		return orderReturnDTO;
	} // method search
}
