package com.CCT.TripApplication.controller;

import java.util.List;
import java.util.Map;

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

import com.CCT.TripApplication.business.abstracts.IExpenseService;
import com.CCT.TripApplication.business.abstracts.IUserService;
import com.CCT.TripApplication.entities.Expense;
import com.CCT.TripApplication.entities.DTO.ExpenseDetail;
import com.CCT.TripApplication.core.JWTIssuer;
import com.CCT.TripApplication.core.results.IDataResult;
import com.CCT.TripApplication.core.results.IResult;

import io.jsonwebtoken.Claims;

/**
 * 
 * @author Diren
 *
 */
@RestController
@CrossOrigin("*")
public class ExpenseController {
	// Services
	private IExpenseService expenseService;
	private IUserService userService;

	// Constructor
	@Autowired
	public ExpenseController(IExpenseService expenseService, IUserService userService) {
		this.expenseService = expenseService;
		this.userService = userService;
	}

	/**
	 * Post method to add expense to the system. Before adding the expense checking
	 * the token
	 * 
	 * @param token   Token is key to access to system
	 * @param expense Expense is given by user to add the system
	 * @return HttpStatus with message
	 */
	@PostMapping("/expense/add")
	public ResponseEntity<String> addExpense(@RequestHeader(name = "Authorization", required = true) String token,
			@RequestBody(required = true) Expense expense) {
		// Checking token is valid or not
		if (checkingToken(token)) {
			// Sending expense to business layer to add to the database
			IResult result = expenseService.add(expense);
			// If result is successful, returns message and Ok status
			if (result.getSuccess()) {
				return new ResponseEntity<String>(result.getMessage(), HttpStatus.OK);
			}
			// If result is not successful, return error message and bad request
			return new ResponseEntity<String>(result.getMessage(), HttpStatus.BAD_REQUEST);
		}
		// If token is not valid return unauthorised status
		return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
	}

	/**
	 * Post method to delete the expense from the system. Method getting parameters
	 * by path and header
	 * 
	 * @param token    Token is key to access to system
	 * @param id       Id is primary key of expense to delete it
	 * @param tripname Trip name is for checking the trip is active or not
	 * @return HttpStatus with message
	 */
	@PostMapping("/expense/delete/{id}/{tripname}")
	public ResponseEntity<String> deleteExpense(@RequestHeader(name = "Authorization", required = true) String token,
			@PathVariable(name = "id") int id, @PathVariable(name = "tripname") String tripname) {
		// Checking token is valid or not
		if (checkingToken(token)) {
			// Sending expense's id and trip name to business layer to delete from the
			// system
			IResult result = expenseService.delete(id, tripname);
			// If result is successful, returns message and Ok status
			if (result.getSuccess()) {
				return new ResponseEntity<String>(result.getMessage(), HttpStatus.OK);
			}
			// If result is not successful, return error message and bad request
			return new ResponseEntity<String>(result.getMessage(), HttpStatus.BAD_REQUEST);
		}
		// If token is not valid return unauthorised status
		return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
	}

	/**
	 * Post method to update the expense.
	 * 
	 * @param token   Token is key to access to system
	 * @param expense Expense is changed one
	 * @return HttpStatus with message
	 */
	@PostMapping("/expense/update")
	public ResponseEntity<String> updateExpense(@RequestHeader(name = "Authorization", required = true) String token,
			@RequestBody Expense expense) {
		// Checking token is valid or not
		if (checkingToken(token)) {
			// Sending expense to business layer to update
			IResult result = expenseService.update(expense);
			// If result is successful, returns message and Ok status
			if (result.getSuccess()) {
				return new ResponseEntity<String>(result.getMessage(), HttpStatus.OK);
			}
			// If result is not successful, return error message and bad request
			return new ResponseEntity<String>(result.getMessage(), HttpStatus.BAD_REQUEST);
		}
		// If token is not valid return unauthorised status
		return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
	}

	/**
	 * Get method to gettin all of the Expense and giving as list of Expense
	 * 
	 * @param token Token is key to access to system
	 * @return Http status with data or just Http status
	 */
	@GetMapping("/expenses")
	public ResponseEntity<List<Expense>> getAll(@RequestHeader(name = "Authorization", required = true) String token) {
		// Checking token is valid or not
		if (checkingToken(token)) {
			// Getting result from the business layer
			IDataResult<List<Expense>> result = expenseService.getAll();
			// If result is successful and there is any expense, returns list of expense and
			// Ok status
			if (result.getSuccess()) {
				return new ResponseEntity<List<Expense>>(result.getData(), HttpStatus.OK);
			}
			// If result is not successful, return no content status
			return new ResponseEntity<List<Expense>>(HttpStatus.NO_CONTENT);
		}
		// If token is not valid return unauthorised status
		return new ResponseEntity<List<Expense>>(HttpStatus.UNAUTHORIZED);
	}

	/**
	 * Getting list of expenses which is in defined trip.
	 * 
	 * @param token    Token is key to access to system
	 * @param tripname Trip name is wanted by user to learn which expenses inside
	 * @param username Username which belongs user who wants to get list of expenses
	 * @return Http status with data or just Http status
	 */
	@GetMapping("/expenses/{tripname}/{username}")
	public ResponseEntity<List<Expense>> getAllByTrip(
			@RequestHeader(name = "Authorization", required = true) String token,
			@PathVariable(name = "tripname") String tripname, @PathVariable(name = "username") String username) {
		// Checking token is valid or not
		if (checkingToken(token)) {
			// Getting result from the business layer
			IDataResult<List<Expense>> result = expenseService.getAllByTrip(username, tripname);
			// If result is successful and there is any expense in trip, returns list of
			// expense and Ok status
			if (result.getSuccess()) {
				return new ResponseEntity<List<Expense>>(result.getData(), HttpStatus.OK);
			}
			// If result is not successful, return no content status
			return new ResponseEntity<List<Expense>>(HttpStatus.NO_CONTENT);
		}
		// If token is not valid return unauthorised status
		return new ResponseEntity<List<Expense>>(HttpStatus.UNAUTHORIZED);
	}

	/**
	 * Get method to see result of the trip.
	 * 
	 * @param token    Token is key to access to system
	 * @param username Username which belongs user who wants to get result of trip
	 * @param tripname Trip name is wanted by user to get result
	 * @return Http status with data or just Http status
	 */
	@GetMapping("/{tripname}/result/{username}")
	public ResponseEntity<List<ExpenseDetail>> getResult(
			@RequestHeader(name = "Authorization", required = true) String token,
			@PathVariable(name = "username") String username, @PathVariable(name = "tripname") String tripname) {
		// Checking token is valid or not
		if (checkingToken(token)) {
			// Getting result from the business layer
			IDataResult<List<ExpenseDetail>> result = expenseService.getResult(username, tripname);
			// If result is successful, returns list of expense detail and Ok status
			if (result.getSuccess()) {
				return new ResponseEntity<List<ExpenseDetail>>(result.getData(), HttpStatus.OK);
			}
			// If result is not successful, return no content status
			return new ResponseEntity<List<ExpenseDetail>>(HttpStatus.NO_CONTENT);
		}
		// If token is not valid return unauthorised status
		return new ResponseEntity<List<ExpenseDetail>>(HttpStatus.UNAUTHORIZED);

	}

	/**
	 * Getting user's list of expenses method
	 * 
	 * @param token    Token is key to access to system
	 * @param username Username which belongs user who wants to get own expenses
	 * @return Http status with data or just Http status
	 */
	@GetMapping("/expenses/{username}/user")
	public ResponseEntity<List<Expense>> getAllByUser(
			@RequestHeader(name = "Authorization", required = true) String token,
			@PathVariable(name = "username") String username) {
		// Checking token is valid or not
		if (checkingToken(token)) {
			// Getting result from the business layer
			IDataResult<List<Expense>> result = expenseService.getAllByUser(username);
			// If result is successful, returns list of expense and Ok status
			if (result.getSuccess()) {
				return new ResponseEntity<List<Expense>>(result.getData(), HttpStatus.OK);
			}
			// If result is not successful, return no content status
			return new ResponseEntity<List<Expense>>(HttpStatus.NO_CONTENT);
		}
		// If token is not valid return unauthorised status
		return new ResponseEntity<List<Expense>>(HttpStatus.UNAUTHORIZED);
	}

	/**
	 * Getting summary of trip
	 * 
	 * @param token    Token is key to access to system
	 * @param username Username which belongs user who wants to get summary of the
	 *                 trip
	 * @param tripname Trip name is wanted by user to get summary
	 * @return Http status with data or just Http status
	 */
	@GetMapping("/{tripname}/summary/{username}")
	public ResponseEntity<Map<String, Double>> getSummary(
			@RequestHeader(name = "Authorization", required = true) String token,
			@PathVariable(name = "username") String username, @PathVariable(name = "tripname") String tripname) {
		// Checking token is valid or not
		if (checkingToken(token)) {
			// Getting result from the business layer
			IDataResult<Map<String, Double>> result = expenseService.getSummary(username, tripname);
			// If result is successful, returns map which includes summary of the trip and
			// Ok status
			if (result.getSuccess()) {
				return new ResponseEntity<Map<String, Double>>(result.getData(), HttpStatus.OK);
			}
			// If result is not successful, return no content status
			return new ResponseEntity<Map<String, Double>>(HttpStatus.NO_CONTENT);
		}
		// If token is not valid return unauthorised status
		return new ResponseEntity<Map<String, Double>>(HttpStatus.UNAUTHORIZED);
	}

	/**
	 * Getting user's total of expenses
	 * 
	 * @param token    Token is key to access to system
	 * @param username Username which belongs user who wants to get own total
	 *                 expenses
	 * @return Http status with data or just Http status
	 */
	@GetMapping("/{username}/summary/user")
	public ResponseEntity<Double> getUserSummary(@RequestHeader(name = "Authorization", required = true) String token,
			@PathVariable(name = "username") String username) {
		// Checking token is valid or not
		if (checkingToken(token)) {
			// Getting result from the business layer
			IDataResult<Double> result = expenseService.getUserSummary(username);
			// If result is successful, returns total of user's expenses and Ok status
			if (result.getSuccess()) {
				return new ResponseEntity<Double>(result.getData(), HttpStatus.OK);
			}
			// If result is not successful, return no content status
			return new ResponseEntity<Double>(HttpStatus.NO_CONTENT);
		}
		// If token is not valid return unauthorised status
		return new ResponseEntity<Double>(HttpStatus.UNAUTHORIZED);
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
