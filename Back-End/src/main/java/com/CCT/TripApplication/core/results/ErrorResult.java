package com.CCT.TripApplication.core.results;

/**
 * This class is defined for unsuccessfully results. If result is unsuccessful
 * result returns false with/without message
 * 
 * @author Diren
 *
 */
public class ErrorResult extends Result {

	// Constructors
	public ErrorResult(String message) {
		this.message = message;
		this.success = false;
	}

	public ErrorResult() {
		this.success = false;
	}
}
