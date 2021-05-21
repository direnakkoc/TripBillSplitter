package com.CCT.TripApplication.dataAccess.concretes.memory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.CCT.TripApplication.dataAccess.abstracts.TripDao;
import com.CCT.TripApplication.entities.Trip;

/**
 * Data access layer for in memory of the Trip class
 * 
 * @author Diren
 *
 */
@Repository
public class MemoryTripDao implements TripDao {

	private List<Trip> trips; // List of Trip to save trips in memory

	// Constructor
	public MemoryTripDao() {
		this.trips = new ArrayList<>();
	}

	/**
	 * Method to save the trips to in memory database system
	 * 
	 * @param trip Trip which an user involved
	 */
	@Override
	public void save(Trip trip) {
		// setting the tripname to upper case and trim around the spaces
		trip.setTripname(trip.getTripname().trim());
		trips.add(trip);
	}

	/**
	 * Method to delete an Expense from the memory database system
	 * 
	 * @param trip Trip which an user wants to delete from the system
	 */
	@Override
	public void delete(Trip trip) {
		Trip tripToDelete = new Trip(); // Creating an instance of trip class
		// Loops for all the trips
		for (Trip t : trips) {
			// If tripnames are matched delete that expense
			if (t.getTripname().equals(trip.getTripname())) {
				tripToDelete = t;
				trips.remove(tripToDelete); // Remove from the database
				return; // Close the delete method
			}
		}
	}

	/**
	 * Method to update an Expense from the memory database system
	 * 
	 * @param trip Trip which an user wants to update
	 */
	@Override
	public void update(Trip trip) {
		Trip tripToUpdate = new Trip(); // Creating an instance of trip class
		// Loops for all the trips
		for (Trip t : trips) {
			// If tripnames are matched update that expense
			if (t.getTripname().equals(trip.getTripname())) {
				tripToUpdate = t;
				// Setting trip status which is given by user
				tripToUpdate.setTripStatus(trip.isTripStatus());
				return; // Close the delete method
			}
		}
	}

	/**
	 * Return all the expenses which are in the memory database
	 */
	@Override
	public List<Trip> findAll() {
		return this.trips;
	}

	/**
	 * Return an trip which tripname is given by user
	 * 
	 * @param id Trip id which is primary key of trip
	 */
	@Override
	public Trip get(String tripname) {
		Trip trip = new Trip(); // Creating an instance of trip class
		// Loops for all the trips
		for (Trip t : trips) {
			// If tripnames are matched update that expense
			if (t.getTripname().equals(tripname)) {
				trip = t;
				return trip;
			}
		}
		return trip;
	}

	/**
	 * Adding an user to the trip
	 * 
	 * @param tripname Trip name which an user will be joined
	 * @param username Username which an user will be joined to the trip
	 */
	@Override
	public void join(String tripname, String username) {
		var trip = get(tripname.toUpperCase()); // Bringing the trip which user will join
		trip.getUsernames().add(username); // Add the user to the trip
	}

}
