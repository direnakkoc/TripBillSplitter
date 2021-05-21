package com.CCT.TripApplication.core.results;

/**
 * Interface for the specialize the results
 * This interface contents just get methods.
 * You can just read the given. You can not set anything
 * @author Diren
 *
 */
public interface IResult {

	/**
	 * Return the success situation
	 * 
	 * @return true or false
	 */
	boolean getSuccess();

	/**
	 * Return a message
	 * 
	 * @return message which is defined for result
	 */
	String getMessage();
}
