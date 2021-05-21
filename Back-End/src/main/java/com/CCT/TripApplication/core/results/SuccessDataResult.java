package com.CCT.TripApplication.core.results;

/**
 * This class is defined for successfully results which include data If result
 * is successful result returns true, data with/without message
 * 
 * @author Diren
 *
 * @param <T> parameter which is defined for methods
 */
public class SuccessDataResult<T> extends DataResult<T> {

	// Constructors
	public SuccessDataResult(T data, String message) {
		this.data = data;
		this.message = message;
		this.success = true;
	}

	public SuccessDataResult(T data) {
		this.data = data;
		this.success = true;
	}

	public SuccessDataResult(String message) {
		this.success = true;
		this.message = message;
	}

	public SuccessDataResult() {
		this.success = true;
	}
}
