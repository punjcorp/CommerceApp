/**
 * 
 */
package com.punj.app.ecommerce.repositories.customer;

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

import com.punj.app.ecommerce.domains.customer.Customer;
import com.punj.app.ecommerce.domains.customer.CustomerDTO;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
@Repository
@Transactional
public class CustomerSearchRepository {
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
	public CustomerDTO search(String text, Pager pager) {

		CustomerDTO customerDTO = new CustomerDTO();
		// get the full text entity manager
		FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(entityManager);

		// create the query using Hibernate Search query DSL
		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Customer.class).get();

		// a very basic query by keywords
		org.apache.lucene.search.Query query = queryBuilder.keyword()
				.onFields("customerId", "name", "phone", "email", "addresses.address1", "addresses.address2", "addresses.city").matching(text).createQuery();

		// wrap Lucene query in an Hibernate Query object
		org.hibernate.search.jpa.FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query, Customer.class);

		jpaQuery.setFirstResult(pager.getStartCount());
		jpaQuery.setMaxResults(pager.getPageSize());

		// execute search and return results (sorted by relevance as default)
		@SuppressWarnings("unchecked")
		List<Customer> results = jpaQuery.getResultList();

		pager.setResultSize(jpaQuery.getResultSize());
		customerDTO.setCustomers(results);

		customerDTO.setPager(pager);
		return customerDTO;
	} // method search
}
