/**
 * 
 */
package com.punj.app.ecommerce.controller.common;

/**
 * @author admin
 *
 */
public class TransformerException extends Exception {

	public TransformerException(String message) {
		super(message);
	}

	public TransformerException(String message, Exception error) {
		super(message, error);
	}

}