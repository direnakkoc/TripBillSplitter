package com.CCT.TripApplication.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CCT.TripApplication.business.abstracts.IUserService;
import com.CCT.TripApplication.dataAccess.abstracts.UserDao;
import com.CCT.TripApplication.entities.User;
import com.CCT.TripApplication.core.Utils;
import com.CCT.TripApplication.business.messages.Message;
import com.CCT.TripApplication.core.results.*;

/**
 * Business layer to manage the User
 * 
 * @author Diren
 *
 */
@Service
public class UserManager implements IUserService {
	// Repository
	private UserDao userDao;

	// Constructor
	@Autowired
	public UserManager(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * Login method.
	 * 
	 * @param username Username which belong to user who wants to login
	 * @param password Password which belong to user who wants to login
	 * @return Success of result with/without message
	 */
	@Override
	public IResult login(String username, String password) {
		// Getting all of the users
		List<User> users = userDao.findAll();
		// Loop for users to check
		for (User u : users) {
			// If usernames match and user passwords match, return successful result
			if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
				return new SuccessResult();
			}
		}
		// If usernames and passwords do not match, return error result and message.
		return new ErrorResult("Error");

	}

	/**
	 * Saving user method. Before register the system checking the criteria
	 * 
	 * @param username Username which belong to user who wants to register
	 * @param password Password which belong to user who wants to register
	 * @return Success of result with/without message
	 */
	@Override
	public IResult add(String username, String password) {
		// Checking some criteria to register
		IResult result = Utils.BusinessRules(checkUserExisted(username), checkUsernameMoreThan2Letter(username),
				checkPasswordIsMoreThan6Char(password));
		// If result is successful, add the user to the system
		if (result.getSuccess()) {
			User user = new User(username, password);
			userDao.save(user);
			// After adding user, return successful result with message
			return new SuccessResult(Message.UserSaved);
		}
		// If user does not pass the criteria, return error result with message
		return new ErrorResult(result.getMessage());
	}

	/**
	 * Deleting user method. I have not used this method in my project so far. But I
	 * wanted to implement CRUD operation. Maybe in the future I will need to update
	 * the project
	 * 
	 * @param userId User id to delete the user
	 * @return Success of result with/without message
	 */
	@Override
	public IResult delete(int userId) {
		// Getting user by userId to delete
		User userToDelete = userDao.getOne(userId);
		if (userToDelete.getId() > 0) {
			userDao.delete(userToDelete);
			// After deleting user return successful result with message
			return new SuccessResult(Message.UserDeleted);

		}
		// If method does not find the user return error result with message
		return new ErrorResult(Message.UserNotFound);
	}

	/**
	 * Updating user method.I have not used this method in my project so far. But I
	 * wanted to implement CRUD operation. Maybe in the future I will need to update
	 * the project
	 * 
	 * @param user User is given by user to update their information
	 * @return Success of result with/without message
	 */
	@Override
	public IResult update(User user) {
		// Getting user by userId to update
		User userToUpdate = userDao.getOne(user.getId());
		if (userToUpdate.getId() > 0) {
			userDao.update(userToUpdate);
			// After updating user return successful result with message
			return new SuccessResult(Message.UserUpdated);
		}
		// If method does not find the user return error result with message
		return new ErrorResult(Message.UserNotFound);
	}

	/**
	 * Getting all the users method
	 * 
	 * @return Success of result and List of User with/without message
	 */
	@Override
	public IDataResult<List<User>> getAll() {
		// Getting list of user
		List<User> result = userDao.findAll();
		// If list is empty, return error result with message
		if (result.isEmpty()) {
			return new ErrorDataResult<List<User>>(Message.usersNotFound);
		}
		// If list is not empty, return successful result, list of user with message
		return new SuccessDataResult<List<User>>(result, Message.usersAreListed);
	}

	/**
	 * Getting user by user's id
	 * 
	 * @param userId User id to get the user
	 * @return Success of result and an User with/without message
	 */
	@Override
	public IDataResult<User> getUserById(int userId) {
		// Getting user by userId
		User user = userDao.getOne(userId);
		// If there is user return successful result and user
		if (user != null) {
			return new SuccessDataResult<User>(user);
		}
		// If there is no user with that user id , return error result and message
		return new ErrorDataResult<User>(Message.UserNotFound);
	}

	/**
	 * Checking user is in list or not method This method to login the system
	 * 
	 * @param username Username to check the database
	 * @return Success of result with/without message
	 */
	@Override
	public IResult checkUser(String username) {
		// Getting all of the users
		List<User> users = userDao.findAll();
		// Loop for users
		for (User user : users) {
			// If usernames match, return successful result
			if (user.getUsername().equals(username)) {
				return new SuccessResult();
			}
		}
		// If usernames does not match, return error result
		return new ErrorResult();
	}

	// Validation
	/**
	 * Checking user , if user is in the system, return error result
	 * 
	 * @param username Username to check the database
	 * @return Success of result with/without message
	 */
	private IResult checkUserExisted(String username) {
		// Getting all of the users
		List<User> users = userDao.findAll();
		// Loop for users
		for (User user : users) {
			// If usernames match, return error result with message
			if (user.getUsername().equals(username)) {
				return new ErrorResult(Message.UserIsExisted);
			}
		}
		// If user is not in system, return successful result
		return new SuccessResult();
	}

	/**
	 * Checking the username is valid or not for system
	 * 
	 * @param username Username which belongs to user who wants to register
	 * @return Success of result with/without message
	 */
	private IResult checkUsernameMoreThan2Letter(String username) {
		// If username is more than 2 character, return successful result
		if (username.length() > 2) {
			return new SuccessResult();
		}
		// If username is less than 2 or equals to 2 character, return error result with
		// message
		return new ErrorResult(Message.UsernameIsTooShort);
	}

	/**
	 * Checking the password is valid or not for system
	 * 
	 * @param password Password which belongs to user who wants to register
	 * @return Success of result with/without message
	 */
	private IResult checkPasswordIsMoreThan6Char(String password) {
		// If password is more than 6 or equals to 6 character, return successful result
		if (password.length() >= 6) {
			return new SuccessResult();
		}
		// If password is less than 6 character, return error result with message
		return new ErrorResult(Message.PasswordIsTooShort);
	}

}
