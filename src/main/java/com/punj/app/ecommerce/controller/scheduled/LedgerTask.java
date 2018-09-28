/**
 * 
 */
package com.punj.app.ecommerce.controller.scheduled;

import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author admin
 *
 */
public class LedgerTask {

	@Scheduled(fixedDelay = 6000)
	public void processLedger() {

	}

}
