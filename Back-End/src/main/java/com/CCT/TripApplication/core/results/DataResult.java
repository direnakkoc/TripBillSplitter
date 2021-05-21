package com.CCT.TripApplication.core.results;

/**
 * Define the results with their properties and datas
 * 
 * @author Diren
 *
 * @param <T> parameter which is defined for methods
 */
public class DataResult<T> extends Result implements IDataResult<T> {

	// Properties
	public T data;

	// Constructors
	public DataResult(T data, String message, boolean success) {
		this.data = data;
		this.message = message;
		this.success = success;
	}

	public DataResult(T data, boolean success) {
		this.data = data;
		this.success = success;
	}

	public DataResult() {

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

	@Override
	public T getData() {
		return this.data;
	}

}
