package com.jwetherell.algorithms.data_structures;

/**
 * Grandparent should have at least one non-NULL child which should be the node's parent.
 * @author Massimiliano Altieri
 *
 */
public class UnrelatedGrandparentNodeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * @param parent
	 */
	public UnrelatedGrandparentNodeException() {
		super("YIKES! Grandparent should have at least one non-NULL child which should be my parent.");
	}
}
