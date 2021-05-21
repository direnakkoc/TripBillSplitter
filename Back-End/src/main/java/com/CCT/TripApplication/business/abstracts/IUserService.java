package com.CCT.TripApplication.business.abstracts;

import java.util.List;

import com.CCT.TripApplication.entities.User;
import com.CCT.TripApplication.core.results.*;

/**
 * This interface is for business layer of User
 * 
 * @author Diren
 *
 */
public interface IUserService {
	/**
	 * Saving user method
	 * @param username Username which belong to user who wants to register
	 * @param password Password which belong to user who wants to register
	 * @return Success of result with/without message
	 */
	IResult add(String username, String password);
	/**
	 * Deleting user method
	 * 
	 * @param userId User id to delete the user
	 * @return Success of result with/without message
	 */
	IResult delete(int userId);
	/**
	 * Updating user method
	 * 
	 * @param user User is given by user to update their information
	 * @return Success of result with/without message
	 */
	IResult update(User user);
	/**
	 * Getting all the users method
	 * 
	 * @return Success of result and List of User with/without message
	 */
	IDataResult<List<User>> getAll();
	/**
	 * Getting user by user's id
	 * 
	 * @param userId User id to get the user
	 * @return Success of result and an User with/without message
	 */
	IDataResult<User> getUserById(int userId);
	/**
	 * Login method
	 * @param username Username which belong to user who wants to login
	 * @param password Password which belong to user who wants to login
	 * @return Success of result with/without message
	 */
	IResult login(String username, String password);

	/**
	 * Checking user is in list or not method
	 * 
	 * @param username Username to check the database
	 * @return Success of result with/without message
	 */
	IResult checkUser(String username);

}
