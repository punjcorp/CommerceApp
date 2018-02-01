/**
 * 
 */
package com.punj.app.ecommerce.repositories.inventory;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;

import com.punj.app.ecommerce.domains.inventory.StockAdjustment;
import com.punj.app.ecommerce.domains.inventory.StockDTO;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
@Repository
@Transactional
public class StockSearchRepository {
	// Spring will inject here the entity manager object
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * A basic search for the entity User. The search is done by exact match per
	 * keywords on fields name, city and email.
	 * 
	 * @param text
	 *            The query text.
	 */
	public StockDTO search(String text, Pager pager) {

		StockDTO stockDTO = new StockDTO();
		// get the full text entity manager
		FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search
				.getFullTextEntityManager(entityManager);

		// create the query using Hibernate Search query DSL
		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder()
				.forEntity(StockAdjustment.class).get();

		// a very basic query by keywords
		org.apache.lucene.search.Query query = queryBuilder.keyword()
				.onFields("stockAdjustId", "description", "createdBy")
				.matching(text).createQuery();

		// wrap Lucene query in an Hibernate Query object
		org.hibernate.search.jpa.FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query,
				StockAdjustment.class);

		jpaQuery.setFirstResult(pager.getStartCount());
		jpaQuery.setMaxResults(pager.getPageSize());

		// execute search and return results (sorted by relevance as default)
		@SuppressWarnings("unchecked")
		List<StockAdjustment> results = jpaQuery.getResultList();

		pager.setResultSize(jpaQuery.getResultSize());
		stockDTO.setStockAdjustments(results);

		stockDTO.setPager(pager);
		return stockDTO;
	} // method search
}
