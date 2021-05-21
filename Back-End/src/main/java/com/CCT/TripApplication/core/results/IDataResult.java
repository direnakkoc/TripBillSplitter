package com.CCT.TripApplication.core.results;

/**
 * Interface for the specialize the results If results includes data return
 * result with data
 * 
 * @author Diren
 *
 * @param <T> parameter which is defined for methods
 */
public interface IDataResult<T> extends IResult {

	/**
	 * Return the data which is result of method
	 * 
	 * @return
	 */
	T getData();
}
