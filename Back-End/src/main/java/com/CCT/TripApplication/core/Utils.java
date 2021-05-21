package com.CCT.TripApplication.core;

import com.CCT.TripApplication.core.results.*;
/**
 *  Utilities for the project.
 * @author Diren
 *
 */
public class Utils {

	/**
	 * Checking business validation rules and responding with result
	 * 
	 * @param rules Rules which is defined for business layer
	 * @return Success or Error result which are including boolean with/without message
	 */
	public static IResult BusinessRules(IResult... rules) {
		// Loop for given rules
		for (var r : rules) {
			// If result is false we should return proper error with it's message
			if (!r.getSuccess()) {
				return new ErrorResult(r.getMessage());
			}
		}
		// If there is no violation for rules return success which is true
		return new SuccessResult();
	}

}
