package com.CCT.TripApplication.core.results;

/**
 * This class is defined for successfully results If result is successful result
 * returns true with/without message
 * 
 * @author Diren
 *
 */
public class SuccessResult extends Result {

	// Constructors
	public SuccessResult(String message) {
		this.success = true;
		this.message = message;
	}

	public SuccessResult() {
		this.success = true;
	}
}
