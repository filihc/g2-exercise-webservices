package com.g2.exercise.ws.exception;

/**
 * Custom Business Exception
 * @author fhernand
 *
 */
public class BusinessException extends Exception{
	
	/**
	 * Constructor
	 * 
	 * @param message the error message
	 */
	public BusinessException(final String message) {
		super(message);
	}

}
