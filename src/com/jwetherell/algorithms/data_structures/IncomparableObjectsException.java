package com.jwetherell.algorithms.data_structures;

/**
 * @author Massimiliano Altieri
 *
 */
public class IncomparableObjectsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public IncomparableObjectsException() {
		super("Cannot compare object.");
	}
}
