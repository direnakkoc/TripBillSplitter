package com.CCT.TripApplication.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CCT.TripApplication.business.abstracts.ITripService;
import com.CCT.TripApplication.dataAccess.abstracts.TripDao;
import com.CCT.TripApplication.entities.Trip;
import com.CCT.TripApplication.core.Utils;
import com.CCT.TripApplication.business.messages.Message;
import com.CCT.TripApplication.core.results.*;

/**
 * Business layer to manage the Trip
 * 
 * @author Diren
 *
 */
@Service
public class TripManager implements ITripService {

	// Repository
	private TripDao tripDao;

	// Constructor
	@Autowired
	public TripManager(TripDao tripDao) {
		this.tripDao = tripDao;
	}

	/**
	 * Closing the trip method. Before closing the trip checking the criteria
	 * 
	 * @param tripname Trip name is given by user to close the trip
	 * @return Success of result with/without message
	 */
	@Override
	public IResult closeTrip(String tripname) {
		// Checking criteria. If there is any mismatching, result returns success is
		// false with/without message
		IResult result = Utils.BusinessRules(checkTripIsCreated(tripname));
		// If result is true returns close the trip
		if (result.getSuccess()) {
			// Getting the trip which will be changed
			Trip trip = tripDao.get(tripname.toUpperCase());
			// Setting the trip status false (closed)
			trip.setTripStatus(false);
			tripDao.update(trip);
			// Return successful result with message
			return new SuccessResult(Message.TripIsNotActive);
		}
		// If result is false, return error result with message
		return new ErrorResult(result.getMessage());
	}

	/**
	 * Saving trip method. Before saving the trip checking the criteria
	 * 
	 * @param trip Trip which created by user
	 * @return Success of result with/without message
	 */
	@Override
	public IResult add(Trip trip) {
		// Checking criteria. If there is any mismatching, result returns success is
		// false with/without message
		IResult result = Utils.BusinessRules(checkTripIsExisted(trip.getTripname()));
		// If method pass the criteria, add the trip to the system
		if (result.getSuccess()) {
			// All the trip names are saving as upper case to the system
			trip.setTripname(trip.getTripname().toUpperCase());
			tripDao.save(trip);
			// If result is true, return successful result with message
			return new SuccessResult(Message.TripSaved);
		}
		// If method does not pass the criteria, return error result with message
		return new ErrorResult(result.getMessage());
	}

	/**
	 * Deleting trip method. Before deleting the trip checking the criteria
	 * 
	 * @param tripname Trip name is given by user to delete from database
	 * @return Success of result with/without message
	 */
	@Override
	public IResult delete(String tripname) {
		// Checking criteria. If there is any mismatching, result returns success is
		// false with/without message
		IResult result = Utils.BusinessRules(checkTripIsCreated(tripname));
		// If method pass the criteria, delete the trip from the system
		if (result.getSuccess()) {
			// Getting the trip which will be deleted from the system
			Trip tripToDelete = tripDao.get(tripname);
			tripDao.delete(tripToDelete);
			// If result is true, return successful result with message
			return new SuccessResult(Message.ExpenseDeleted);
		}
		// If method does not pass the criteria, return error result with message
		return new ErrorResult(result.getMessage());
	}

	/**
	 * Updating trip method.Before updating the trip checking the criteria
	 * 
	 * @param trip Trip is given by user to update
	 * @return Success of result with/without message
	 */
	@Override
	public IResult update(Trip trip) {
		// Checking criteria. If there is any mismatching, result returns success is
		// false with/without message
		IResult result = Utils.BusinessRules(checkTripIsExisted(trip.getTripname()));
		// If method pass the criteria, update the trip
		if (result.getSuccess()) {
			// Getting the trip which will be updated
			Trip tripToUpdate = tripDao.get(trip.getTripname());
			// Updating data
			tripToUpdate.setTripname(trip.getTripname());
			tripToUpdate.setTripStatus(trip.isTripStatus());
			tripDao.update(tripToUpdate);
			// If result is true, return successful result with message
			return new SuccessResult(Message.ExpenseUpdated);
		}
		// If method does not pass the criteria, return error result with message
		return new ErrorResult(result.getMessage());
	}

	/**
	 * Adding the user to the trip method
	 * 
	 * @param tripname Tripname which is given by user to join to the trip
	 * @param username Username which belong to user who wants to join the trip
	 * @return Success of result with/without message
	 */
	@Override
	public IResult joinTrip(String tripname, String username) {
		// Checking criteria. If there is any mismatching, result returns success is
		// false with/without message
		IResult result = Utils.BusinessRules(checkTripIsCreated(tripname), checkUserIsInTrip(tripname, username),
				checkTripIsActive(tripname));
		// If method pass the criteria, user will be added to the system
		if (result.getSuccess()) {
			tripDao.join(tripname, username);
			// If result is true, return successful result with message
			return new SuccessResult(Message.Joined);
		}
		// If method does not pass the criteria, return error result with message
		return new ErrorResult(result.getMessage());
	}

	/**
	 * Getting all the trips
	 * 
	 * @return Success of result and List of Trip with/without message
	 */
	@Override
	public IDataResult<List<Trip>> getAll() {
		// Getting all trips in the system
		List<Trip> result = tripDao.findAll();
		// If there is no trip , return error result with message
		if (result.isEmpty()) {
			return new ErrorDataResult<List<Trip>>(Message.ExpensesNotFound);
		}
		// If there is any trip, return successful result , trips and message
		return new SuccessDataResult<List<Trip>>(result, Message.ExpensesAreListed);
	}
	/**
	 * Checking trip is existed or not(for adding to the system) if trip is existed
	 * method should return error result because user can not add same trip name in
	 * the system
	 * 
	 * @param tripname Trip name which user wants to add to the system
	 * @return Success of result with/without message
	 */
	@Override
	public IResult checkTrip(String tripname) {
		// Getting all trips
		List<Trip> trips = getAll().getData();
		// If there is any trip, check trip names
		if (trips != null) {
			// Loop for trips
			for (Trip trip : trips) {
				// If tripnames matches, return error result with message
				if (trip.getTripname().equalsIgnoreCase(tripname)) {
					return new SuccessResult();
				}
			}
		}
		// If there is noting matching, return successful result
		return new ErrorResult(Message.TripsNotFound);
	}

	// Validation
	/**
	 * Checking trip is existed or not(for adding to the system) if trip is existed
	 * method should return error result because user can not add same trip name in
	 * the system
	 * 
	 * @param tripname Trip name which user wants to add to the system
	 * @return Success of result with/without message
	 */
	public IResult checkTripIsExisted(String tripname) {
		// Getting all trips
		List<Trip> trips = getAll().getData();
		// If there is any trip, check trip names
		if (trips != null) {
			// Loop for trips
			for (Trip trip : trips) {
				// If tripnames matches, return error result with message
				if (trip.getTripname().equalsIgnoreCase(tripname)) {
					return new ErrorResult(Message.ThisTripNameUsed);
				}
			}
		}
		// If there is noting matching, return successful result
		return new SuccessResult();
	}

	/**
	 * Checking trip is existed or not.
	 * 
	 * @param tripname Trip name which is given by user
	 * @return Success of result with/without message
	 */
	private IResult checkTripIsCreated(String tripname) {
		// Getting all trips
		List<Trip> trips = getAll().getData();
		// If there is any trip, check trip names
		if (trips != null) {
			// Loop for trips
			for (Trip trip : trips) {
				// If tripnames matches, return successful
				if (trip.getTripname().equalsIgnoreCase(tripname)) {
					return new SuccessResult();
				}
			}
			// If there are trips but names does not match, return error result with message
			return new ErrorResult(Message.TripIsNotExisted);
		}
		// If there is no trip, return error result with message
		return new ErrorResult(Message.TripsNotFound);
	}

	/**
	 * Checking the user is in the trip or not
	 * 
	 * @param tripname Trip name which user wants to be added
	 * @param username User name which belong to user who wants to be added to the
	 *                 trip
	 * @return Success of result with/without message
	 */
	private IResult checkUserIsInTrip(String tripname, String username) {
		// Getting usernames which are in trip
		List<String> tripUsernames = tripDao.get(tripname.toUpperCase()).getUsernames();
		// Loop for usernames
		for (String u : tripUsernames) {
			// If usernames are match, return error result with message
			if (u.equals(username)) {
				return new ErrorResult("Error: User is in the trip.");
			}
		}
		// If username is not in trip, return successful result
		return new SuccessResult();
	}

	/**
	 * Checking trip is closed or not
	 * 
	 * @param tripname Trip name which user wants to check
	 * @return Success of result with/without message
	 */
	private IResult checkTripIsActive(String tripname) {
		// Getting trip which user wants
		Trip trip = tripDao.get(tripname.toUpperCase());
		// If trip is active, return successful result
		if (trip.isTripStatus()) {
			return new SuccessResult();
		}
		// If trip is not active, return error result with message.
		return new ErrorResult(Message.TripIsNotActive);
	}
}
