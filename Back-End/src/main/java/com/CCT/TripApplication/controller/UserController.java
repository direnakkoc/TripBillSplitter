package com.CCT.TripApplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CCT.TripApplication.business.abstracts.IUserService;
import com.CCT.TripApplication.entities.User;
import com.CCT.TripApplication.core.JWTIssuer;
import com.CCT.TripApplication.core.results.IDataResult;
import com.CCT.TripApplication.core.results.IResult;

/**
 * 
 * @author Diren
 *
 */
@RestController
@CrossOrigin("*")
public class UserController {
	// Services
	private IUserService userService;

	// Constructor
	@Autowired
	public UserController(IUserService userService) {
		this.userService = userService;
	}

	/**
	 * Get method to login and get the token from the system
	 * 
	 * @param username Username which belong to user who wants to login
	 * @param password Password which belong to user who wants to login
	 * @return HttpStatus with token or message
	 */
	@GetMapping("/login")
	public ResponseEntity<String> login(@RequestParam(name = "username", required = true) String username,
			@RequestParam(name = "password", required = true) String password) {
		// Sending username and password to login the system and get token
		IResult result = userService.login(username, password);
		// If result is successful, return the token with Ok status
		if (result.getSuccess()) {
			// Creating token
			String token = JWTIssuer.createJWT(username, "trip-application", username, 8640000);
			return new ResponseEntity<String>(token, HttpStatus.OK);
		}
		// If result is not successful, return message with bad request status
		return new ResponseEntity<String>(result.getMessage(), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Post method to register to the system
	 * 
	 * @param username Username which belong to user who wants to register
	 * @param password Password which belong to user who wants to register
	 * @return HttpStatus with message
	 */
	@PostMapping("/users/add")
	public ResponseEntity<String> add(@RequestParam(name = "username", required = true) String username,
			@RequestParam(name = "password", required = true) String password) {
		// Sending username and password to get the result
		IResult result = userService.add(username, password);
		// If result is successful, return message and Ok status
		if (result.getSuccess()) {
			return new ResponseEntity<String>(result.getMessage(), HttpStatus.CREATED);
		}
		// If user can not register, return message and bad request status
		return new ResponseEntity<String>(result.getMessage(), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Getting all of the users
	 * 
	 * @return HttpStatus with message
	 */
	@GetMapping("/users")
	public ResponseEntity<List<User>> getAll() {
		// Getting all of the users
		IDataResult<List<User>> result = userService.getAll();
		// If result is successful, return all of the users and ok status
		if (result.getSuccess()) {
			return new ResponseEntity<List<User>>(result.getData(), HttpStatus.OK);
		}
		// If result is not successful, return no content status
		return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
	}

}
