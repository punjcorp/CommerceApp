/**
 * 
 */
package com.punj.app.ecommerce.repositories.user;

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

import com.punj.app.ecommerce.controller.common.MVCConstants;
import com.punj.app.ecommerce.domains.user.User;
import com.punj.app.ecommerce.domains.user.UserDTO;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
@Repository
@Transactional
public class UserSearchRepository {
	// Spring will inject here the entity manager object
	@PersistenceContext
	private EntityManager entityManager;

	@AnalyzerDef(name = "edgeNGram_query", tokenizer = @TokenizerDef(factory = WhitespaceTokenizerFactory.class), filters = {
			@TokenFilterDef(factory = LowerCaseFilterFactory.class) // Lowercase all characters
	})

	/**
	 * A basic search for the entity User. The search is done by exact match per keywords on fields
	 * 
	 * @param text
	 *            The query text.
	 */
	public UserDTO search(String text, Pager pager) {

		UserDTO userDTO = new UserDTO();
		// get the full text entity manager
		FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(entityManager);

		// create the query using Hibernate Search query DSL
		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(User.class).get();

		// a very basic query by keywords
		org.apache.lucene.search.Query query = queryBuilder.keyword().onFields("username", "firstname", "lastname", "createdBy", "phone1", "email")
				.matching(text).createQuery();

		// wrap Lucene query in an Hibernate Query object
		org.hibernate.search.jpa.FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query, User.class);

		jpaQuery.setFirstResult(pager.getStartCount());
		jpaQuery.setMaxResults(pager.getPageSize());

		// execute search and return results (sorted by relevance as default)
		@SuppressWarnings("unchecked")
		List<User> results = jpaQuery.getResultList();

		pager.setResultSize(jpaQuery.getResultSize());
		userDTO.setUsers(results);

		userDTO.setPager(pager);
		return userDTO;
	} // method search

	/**
	 * This method will return only the approved users from the database
	 * 
	 * @param text
	 * @param pager
	 * @return
	 */
	public UserDTO searchApprovedUsers(String text, Pager pager) {

		UserDTO userDTO = new UserDTO();
		// get the full text entity manager
		FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(entityManager);

		// create the query using Hibernate Search query DSL
		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(User.class).get();

		// a very basic query by keywords
		org.apache.lucene.search.Query query = queryBuilder.bool()
				.must(queryBuilder.keyword().onFields("username", "firstname", "lastname", "createdBy", "phone1", "email").matching(text).createQuery())
				.must(queryBuilder.keyword().onFields("status").matching(MVCConstants.STATUS_APPROVED).createQuery()).createQuery();

		// wrap Lucene query in an Hibernate Query object
		org.hibernate.search.jpa.FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query, User.class);

		jpaQuery.setFirstResult(pager.getStartCount());
		jpaQuery.setMaxResults(pager.getPageSize());

		// execute search and return results (sorted by relevance as default)
		@SuppressWarnings("unchecked")
		List<User> results = jpaQuery.getResultList();

		pager.setResultSize(jpaQuery.getResultSize());
		userDTO.setUsers(results);

		userDTO.setPager(pager);
		return userDTO;
	} // method search
}
