package com.jwetherell.algorithms.data_structures;

/**
 * @author Massimiliano Altieri
 *
 */
public class NonSquareMatrixException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public NonSquareMatrixException() {
		super("Matrix should be a square");
	}
}
