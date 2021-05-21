package com.CCT.TripApplication.core.results;

/**
 * Define the results with their properties
 * 
 * @author Diren
 *
 */
public class Result implements IResult {

	// Properties
	public boolean success;
	public String message;

	// Constructors
	public Result(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public Result(boolean success) {
		this.success = success;
	}

	public Result() {

	}

	// Getter methods
	@Override
	public boolean getSuccess() {
		return this.success;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

}
