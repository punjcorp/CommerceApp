/**
 * 
 */
package com.punj.app.ecommerce.repositories.common;

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

import com.punj.app.ecommerce.domains.common.ReasonCode;
import com.punj.app.ecommerce.domains.item.Item;
import com.punj.app.ecommerce.domains.item.ItemDTO;
import com.punj.app.ecommerce.services.common.dtos.ReasonDTO;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
@Repository
@Transactional
public class ReasonSearchRepository {
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
	public ReasonDTO search(String text, Pager pager) {

		ReasonDTO reasonDTO = new ReasonDTO();
		// get the full text entity manager
		FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(entityManager);

		// create the query using Hibernate Search query DSL
		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(ReasonCode.class).get();

		// a very basic query by keywords
		org.apache.lucene.search.Query query = queryBuilder.keyword().onFields("name", "type", "description").matching(text).createQuery();

		// wrap Lucene query in an Hibernate Query object
		org.hibernate.search.jpa.FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query, ReasonCode.class);

		jpaQuery.setFirstResult(pager.getStartCount());
		jpaQuery.setMaxResults(pager.getPageSize());

		// execute search and return results (sorted by relevance as default)
		@SuppressWarnings("unchecked")
		List<ReasonCode> results = jpaQuery.getResultList();

		pager.setResultSize(jpaQuery.getResultSize());
		reasonDTO.setReasonCodes(results);

		reasonDTO.setPager(pager);
		return reasonDTO;
	} // method search

}