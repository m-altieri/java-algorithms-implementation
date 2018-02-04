package com.jwetherell.algorithms.data_structures;

/**
 * @author Massimiliano Altieri
 *
 */
public class MatchingKeysException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public MatchingKeysException() {
		super("Yikes! Found two keys which match exactly.");
	}
}
