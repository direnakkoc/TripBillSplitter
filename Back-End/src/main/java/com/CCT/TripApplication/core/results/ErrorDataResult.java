package com.CCT.TripApplication.core.results;

/**
 * This class is defined for unsuccessfully results which include data If result
 * is not successful result returns false, data with/without message
 * 
 * @author Diren
 *
 * @param <T> parameter which is defined for methods
 */
public class ErrorDataResult<T> extends DataResult<T> {

	// Constructors
	public ErrorDataResult(T data, String message) {
		this.data = data;
		this.message = message;
		this.success = false;
	}

	public ErrorDataResult(T data) {
		this.data = data;
		this.success = false;
	}

	public ErrorDataResult(String message) {
		this.success = false;
		this.message = message;
	}

	public ErrorDataResult() {
		this.success = false;
	}
}
