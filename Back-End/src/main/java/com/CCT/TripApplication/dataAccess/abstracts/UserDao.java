package com.CCT.TripApplication.dataAccess.abstracts;

import java.util.List;

import com.CCT.TripApplication.entities.User;

/**
 * CRUD methods for the User class
 * 
 * @author Diren
 *
 */
public interface UserDao {
	/**
	 * Save method for User
	 * 
	 * @param user User who registered to the system
	 */
	void save(User user);

	/**
	 * Delete method for User
	 * 
	 * @param user User who wants to be deleted from the system
	 */
	void delete(User user);

	/**
	 * Update method for User
	 * 
	 * @param user User who wants to update their information in the system
	 */
	void update(User user);

	/**
	 * Return the all of the users
	 * 
	 * @return
	 */
	List<User> findAll();

	/**
	 * Return a User from the system
	 * 
	 * @param userId User id which is primary key of user
	 * @return
	 */
	User getOne(int userId);
}
