package com.CCT.TripApplication.business.abstracts;

import java.util.List;

import com.CCT.TripApplication.entities.Trip;
import com.CCT.TripApplication.core.results.*;

/**
 * This interface is for business layer of Trip
 * 
 * @author Diren
 *
 */
public interface ITripService {
	/**
	 * Saving trip method
	 * 
	 * @param trip Trip which created by user
	 * @return Success of result with/without message
	 */
	IResult add(Trip trip);

	/**
	 * Deleting trip method
	 * 
	 * @param tripname Trip name is given by user to delete from database
	 * @return Success of result with/without message
	 */
	IResult delete(String tripname);

	/**
	 * Updating trip method
	 * 
	 * @param trip Trip is given by user to update
	 * @return Success of result with/without message
	 */
	IResult update(Trip trip);

	/**
	 * Getting all the trips method
	 * 
	 * @return Success of result and List of Trip with/without message
	 */
	IDataResult<List<Trip>> getAll();

	/**
	 * Closing the trip method
	 * 
	 * @param tripname Trip name is given by user to close the trip
	 * @return Success of result with/without message
	 */
	IResult closeTrip(String tripname);

	/**
	 * Adding the user to the trip method
	 * 
	 * @param tripname Tripname which is given by user to join to the trip
	 * @param username Username which belong to user who wants to join the trip
	 * @return Success of result with/without message
	 */
	IResult joinTrip(String tripname, String username);
	/**
	 *  Checking trip is existed or not
	 * @param tripname Tripname which is given by user to check the trip
	 * @return Success of result with/without message
	 */
	IResult checkTrip(String tripname);
}
