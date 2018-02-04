package com.jwetherell.algorithms.data_structures;

/**
 * This node is unrelated with his parent.
 * @author Massimiliano Altieri
 *
 */
public class UnrelatedParentNodeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * @param parent
	 */
	public UnrelatedParentNodeException(String parent) {
		super("Yikes! I'm not related to my parent. " + parent.toString());
	}

}
