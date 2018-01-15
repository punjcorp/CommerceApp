/**
 * 
 */
package com.punj.app.ecommerce.repositories.supplier;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;

import com.punj.app.ecommerce.domains.supplier.Supplier;
import com.punj.app.ecommerce.domains.supplier.SupplierDTO;
import com.punj.app.ecommerce.utils.Pager;

/**
 * @author admin
 *
 */
@Repository
@Transactional
public class SupplierSearchRepository {
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
	public SupplierDTO search(String text, Pager pager) {

		SupplierDTO supplierDTO = new SupplierDTO();
		// get the full text entity manager
		FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search
				.getFullTextEntityManager(entityManager);

		// create the query using Hibernate Search query DSL
		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder()
				.forEntity(Supplier.class).get();

		// a very basic query by keywords
		org.apache.lucene.search.Query query = queryBuilder.keyword().onFields("supplierId", "name", "phone1", "email",
				"addresses.address1", "addresses.address2", "addresses.city").matching(text).createQuery();

		// wrap Lucene query in an Hibernate Query object
		org.hibernate.search.jpa.FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query,
				Supplier.class);

		jpaQuery.setFirstResult(pager.getStartCount());
		jpaQuery.setMaxResults(pager.getPageSize());
		
		// execute search and return results (sorted by relevance as default)
		@SuppressWarnings("unchecked")
		List<Supplier> results = jpaQuery.getResultList();
		
		pager.setResultSize(jpaQuery.getResultSize());
		supplierDTO.setSuppliers(results);
		
		supplierDTO.setPager(pager);
		return supplierDTO;
	} // method search
}