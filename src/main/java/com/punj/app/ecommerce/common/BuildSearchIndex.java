/**
 * 
 */
package com.punj.app.ecommerce.common;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author admin
 *
 */
@Component
public class BuildSearchIndex implements ApplicationListener<ApplicationReadyEvent> {

	private static final Logger logger = LogManager.getLogger();
	
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Create an initial Lucene index for the data already present in the database.
	 * This method is called when Spring's startup.
	 */
	@Override
	public void onApplicationEvent(final ApplicationReadyEvent event) {
		try {
			FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
			fullTextEntityManager.createIndexer().startAndWait();
		} catch (InterruptedException e) {
			logger.error("An error occurred trying to build the serach index: " ,e);
			// Restore interrupted state...
		    Thread.currentThread().interrupt();

		}
		return;
	}

}