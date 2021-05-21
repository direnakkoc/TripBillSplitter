package com.CCT.TripApplication.dataAccess.concretes.memory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.CCT.TripApplication.dataAccess.abstracts.UserDao;
import com.CCT.TripApplication.entities.User;

/**
 * Data access layer for in memory of the User class
 * 
 * @author Diren
 *
 */
@Repository
public class MemoryUserDao implements UserDao {

	private List<User> users; // List of User to save users in memory
	private int userId; // primary key of user class

	// The 3 best teachers :)
	User david = new User("David", "david1234");
	User amilcar = new User("Amilcar", "amilcar1234");
	User greg = new User("Greg", "greg1234");

	// Constructor
	public MemoryUserDao() {
		this.users = new ArrayList<>();
		this.userId = 0;
		save(greg);
		save(amilcar);
		save(david);
	}

	/**
	 * Method to save the user to in memory database system
	 * 
	 * @param user User who registered to the system
	 */
	@Override
	public void save(User user) {
		// Setting the user's primary key
		user.setId(userId + 1);
		// Adding user to the list of users
		this.users.add(user);
		userId++;

	}

	/**
	 * Method to delete an user from the memory database system
	 * 
	 * @param user User who wants to be deleted from the system
	 */
	@Override
	public void delete(User user) {
		User userToDelete = new User(); // Creating an instance of user class
		// Loops for all the users
		for (User u : users) {
			// If userids are matched delete that expense
			if (u.getId() == user.getId()) {
				userToDelete = u;
				users.remove(userToDelete); // Remove from the database
				return; // Complete the delete method
			}
		}

	}

	/**
	 * Method to update an user from the memory database system
	 * 
	 * @param user User who wants to update their information in the system
	 */
	@Override
	public void update(User user) {
		User userToUpdate = new User(); // Creating an instance of user class
		// Loops for all the users
		for (User u : users) {
			// If userids are matched delete that expense
			if (u.getId() == user.getId()) {
				userToUpdate = u;
				// Setting username which is given by use
				userToUpdate.setUsername(user.getUsername());
				// Setting users password which is given by user
				userToUpdate.setPassword(user.getPassword());
				return;
			}
		}
	}

	/**
	 * Return the all of the users
	 */
	@Override
	public List<User> findAll() {
		return this.users;
	}

	/**
	 * Return an user
	 * 
	 * @param userId User id which is primary key of user
	 */
	@Override
	public User getOne(int userId) {
		User user = new User(); // Creating an instance of user class
		// Loops for all the users
		for (User u : users) {
			// If userids are matched delete that expense
			if (user.getId() == userId) {
				user = u;
				return user;
			}
		}
		return user;
	}
}
