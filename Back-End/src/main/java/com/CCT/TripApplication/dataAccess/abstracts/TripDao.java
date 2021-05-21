package com.CCT.TripApplication.dataAccess.abstracts;

import java.util.List;

import com.CCT.TripApplication.entities.Trip;

/**
 * CRUD methods for the Trip class
 * 
 * @author Diren
 *
 */
public interface TripDao {

	/**
	 * Save method for Trip
	 * 
	 * @param trip Trip which an user involved
	 */
	void save(Trip trip);

	/**
	 * Delete method for Trip
	 * 
	 * @param trip Trip which an user wants to delete from the system
	 */
	void delete(Trip trip);

	/**
	 * Update method for Trip
	 * 
	 * @param trip Trip which an user wants to update
	 */
	void update(Trip trip);

	/**
	 * Return the all of the trips
	 * 
	 * @return
	 */
	List<Trip> findAll();

	/**
	 * Return a Trip from the system
	 * 
	 * @param id Trip id which is primary key of trip
	 * @return
	 */
	Trip get(String id);

	/**
	 * Adding an user to the trip
	 * 
	 * @param tripname Trip name which an user will be joined
	 * @param username Username which an user will join to the trip
	 */
	void join(String tripname, String username);

}
