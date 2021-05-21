package com.CCT.TripApplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.CCT.TripApplication.business.abstracts.ITripService;
import com.CCT.TripApplication.business.abstracts.IUserService;
import com.CCT.TripApplication.entities.Trip;
import com.CCT.TripApplication.core.JWTIssuer;

import io.jsonwebtoken.Claims;

/**
 * 
 * @author Diren
 *
 */
@RestController
@CrossOrigin("*")
public class TripController {
	// Services
	ITripService tripService;
	IUserService userService;

	// Constructor
	@Autowired
	public TripController(ITripService tripService, IUserService userService) {
		this.tripService = tripService;
		this.userService = userService;
	}

	/**
	 * Post method to add trip to the system. Before adding the trip checking the
	 * token
	 * 
	 * @param token Token is key to access to system
	 * @param trip  Trip is given by user to add the system
	 * @return HttpStatus with message
	 */
	@PostMapping("/trip/add")
	public ResponseEntity<String> add(@RequestHeader(name = "Authorization", required = true) String token,
			@RequestBody Trip trip) {
		// Checking token is valid or not
		if (checkingToken(token)) {
			// Sending trip to business layer to add to the database
			var result = tripService.add(trip);
			// If result is successful, returns message and Ok status
			if (result.getSuccess()) {
				return new ResponseEntity<>(result.getMessage(), HttpStatus.OK);
			}
			// If result is not successful, return error message and bad request
			return new ResponseEntity<>(result.getMessage(), HttpStatus.BAD_REQUEST);
		}
		// If token is not valid return unauthorised status
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	/**
	 * Getting method to learn all of the trips
	 * 
	 * @param token Token is key to access to system
	 * @return Http status with data or just Http status
	 */
	@GetMapping("/trips")
	public ResponseEntity<List<Trip>> getAll(@RequestHeader(name = "Authorization", required = true) String token) {
		// Checking token is valid or not
		if (checkingToken(token)) {
			var result = tripService.getAll();
			// If result is successful and there is any trip, returns list of trip and
			// Ok status
			if (result.getSuccess()) {
				return new ResponseEntity<List<Trip>>(result.getData(), HttpStatus.OK);
			}
			// If result is not successful, return no content status
			return new ResponseEntity<List<Trip>>(HttpStatus.NO_CONTENT);
		}
		// If token is not valid return unauthorised status
		return new ResponseEntity<List<Trip>>(HttpStatus.UNAUTHORIZED);

	}

	/**
	 * Post method to change trip status so trip will be closed
	 * 
	 * @param token    Token is key to access to system
	 * @param tripname Trip is given by user to close
	 * @return HttpStatus with message
	 */
	@PostMapping("/{tripname}/close")
	public ResponseEntity<String> closeTrip(@RequestHeader(name = "Authorization", required = true) String token,
			@PathVariable(name = "tripname") String tripname) {
		// Checking token is valid or not
		if (checkingToken(token)) {
			var result = tripService.closeTrip(tripname);
			// If result is successful, returns message and Ok status
			if (result.getSuccess()) {
				return new ResponseEntity<>(result.getMessage(), HttpStatus.OK);
			}
			// If result is not successful, return error message and bad request
			return new ResponseEntity<>(result.getMessage(), HttpStatus.BAD_REQUEST);
		}
		// If token is not valid return unauthorised status
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	/**
	 * Post method to add the user to the defined trip If user does not join trip,
	 * user is not allowed to do anything unless joined the trip. The reason of
	 * creating the join method is even though a user does not spent anything, the
	 * user has to pay so expenses should be split equally
	 * 
	 * @param token    Token is key to access to system
	 * @param tripname Trip is given by user to be joined
	 * @param username Username which belongs user who wants to join the trip
	 * @return HttpStatus with message
	 */
	@PostMapping("/{tripname}/join/{username}")
	public ResponseEntity<String> joinTrip(@RequestHeader(name = "Authorization", required = true) String token,
			@PathVariable(name = "tripname") String tripname, @PathVariable(name = "username") String username) {
		// Checking token is valid or not
		if (checkingToken(token)) {
			var result = tripService.joinTrip(tripname, username);
			// If result is successful, returns message and Ok status
			if (result.getSuccess()) {
				return new ResponseEntity<>(result.getMessage(), HttpStatus.OK);
			}
			// If result is not successful, return error message and bad request
			return new ResponseEntity<>(result.getMessage(), HttpStatus.BAD_REQUEST);
		}
		// If token is not valid return unauthorised status
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	/**
	 * Checking trip is existed or not
	 * @param token Token is key to access to system
	 * @param tripname Trip is given by user to check
	 * @return
	 */
	@GetMapping("/{tripname}/check")
	ResponseEntity<String> checkTripIsExisted(@RequestHeader(name = "Authorization", required = true) String token,
			@PathVariable(name = "tripname") String tripname) {
		// Checking token is valid or not
		if (checkingToken(token)) {
			var result = tripService.checkTrip(tripname);
			// If result is successful, returns message and Ok status
			if (result.getSuccess()) {
				return new ResponseEntity<>(result.getMessage(), HttpStatus.OK);
			}
			// If result is not successful, return error message and bad request
			return new ResponseEntity<>(result.getMessage(), HttpStatus.BAD_REQUEST);
		}
		// If token is not valid return unauthorised status
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	/**
	 * Checking token is valid or not
	 * 
	 * @param token Token is key to access to system
	 * @return if it is valid true, otherwise false
	 */
	private boolean checkingToken(String token) {
		Claims claims = JWTIssuer.decodeJWT(token.split(" ")[1]);
		String subClaim = claims.get("sub", String.class);
		if (userService.checkUser(subClaim).getSuccess()) {
			return true;
		}
		return false;
	}
}
