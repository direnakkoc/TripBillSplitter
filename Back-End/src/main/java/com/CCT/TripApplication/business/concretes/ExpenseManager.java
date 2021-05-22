package com.CCT.TripApplication.business.concretes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CCT.TripApplication.business.abstracts.IExpenseService;
import com.CCT.TripApplication.dataAccess.abstracts.ExpenseDao;
import com.CCT.TripApplication.dataAccess.abstracts.TripDao;
import com.CCT.TripApplication.entities.Expense;
import com.CCT.TripApplication.entities.Trip;
import com.CCT.TripApplication.entities.DTO.ExpenseDetail;
import com.CCT.TripApplication.core.Utils;
import com.CCT.TripApplication.business.messages.Message;
import com.CCT.TripApplication.core.results.*;

/**
 * Business layer to manage the Expenses
 * 
 * @author Diren
 *
 */
@Service
public class ExpenseManager implements IExpenseService {

	// Repositories
	private ExpenseDao expenseDao;
	private TripDao tripDao;

	// Constructor
	@Autowired
	public ExpenseManager(ExpenseDao expenseDao, TripDao tripDao) {
		this.expenseDao = expenseDao;
		this.tripDao = tripDao;
	}

	/**
	 * Saving expense method. Before saving the expense checking the criteria
	 * 
	 * @param expense Expense which user spent and add the system
	 * @return Success of result with/without message
	 */
	@Override
	public IResult add(Expense expense) {
		// Checking criteria. If there is any mismatching, result returns success is
		// false with/without message
		IResult result = Utils.BusinessRules(checkTripIsExisted(expense.getTrip().getTripname()),
				checkTripsStatus(expense.getTrip().getTripname()),
				checkUserInTrip(expense.getUser().getUsername(), expense.getTrip().getTripname().toUpperCase()));
		// If result is true save the expense
		if (result.getSuccess()) {
			expenseDao.save(expense);
			// return successful result with defines message
			return new SuccessResult(Message.ExpenseSaved);
		}
		// if result is false return error result with defined message
		return new ErrorResult(result.getMessage());
	}

	/**
	 * Deleting expense method.Before deleting the expense checking the criteria
	 * 
	 * @param id       Expense's id is given by user to delete from database
	 * @param tripname Trip name to check the trip is active or not
	 * @return Success of result with/without message
	 */
	@Override
	public IResult delete(int id, String tripname) {
		// Checking criteria. If there is any mismatching, result returns success is
		// false with/without message
		IResult result = Utils.BusinessRules(checkExpenseExisted(id), checkTripsStatus(tripname));
		// If result is true delete the expense
		if (result.getSuccess()) {
			Expense expenseToDelete = expenseDao.get(id);
			expenseDao.delete(expenseToDelete);
			return new SuccessResult(Message.ExpenseDeleted); // return successful result with defined message
		}
		return new ErrorResult(result.getMessage());// if result is false return error result with defined message
	}

	/**
	 * Updating expense method.Before deleting the expense checking the criteria
	 * 
	 * @param expense Expense is given by user to update
	 * @return Success of result with/without message
	 */
	@Override
	public IResult update(Expense expense) {
		// Checking criteria. If there is any mismatching, result returns success is
		// false with/without message
		IResult result = Utils.BusinessRules(checkExpenseExisted(expense.getExpenseId()),
				checkTripIsExisted(expense.getTrip().getTripname()), checkTripsStatus(expense.getTrip().getTripname()));
		// If result is true update the expense
		if (result.getSuccess()) {
			expenseDao.update(expense);
			return new SuccessResult(Message.ExpenseUpdated);// return successful result with defined message
		}
		return new ErrorResult(result.getMessage());// if result is false return error result with defined message
	}

	/**
	 * Getting all the expenses method
	 * 
	 * @return Success of result and List of Expense with/without message
	 */
	@Override
	public IDataResult<List<Expense>> getAll() {
		// Getting all the expenses
		List<Expense> result = expenseDao.findAll();
		// If there are any expenses return true
		if (!result.isEmpty()) {
			// return successful result and data with defined message
			return new SuccessDataResult<List<Expense>>(result);
		}
		// if result is false return error result with defined message
		return new ErrorDataResult<List<Expense>>(Message.ExpensesNotFound);
	}

	/**
	 * Getting all the expenses which is given tripname method. Before getting
	 * expenses checking the criteria
	 * 
	 * @param username Username to check user is in trip or not
	 * @param tripname Tripname to get that trip's expenses
	 * @return Success of result and List of Expense with/without message
	 */
	@Override
	public IDataResult<List<Expense>> getAllByTrip(String username, String tripname) {
		// Checking criteria. If there is any mismatching, result returns success is
		// false with/without message
		IResult result = Utils.BusinessRules(checkUserInTrip(username, tripname), checkTripIsExisted(tripname));
		// If result is true get the expenses
		if (result.getSuccess()) {
			// Getting all the expenses
			List<Expense> expenses = expenseDao.findAll();
			List<Expense> tripExpenses = new ArrayList<>(); // Creating an instance of expense class
			// Loops for all the expenses
			for (Expense expense : expenses) {
				// If trip names matches add to instance
				if (expense.getTrip().getTripname().equalsIgnoreCase(tripname)) {
					tripExpenses.add(expense);
				}
			}
			// If method pass the criteria return successful result and data
			return new SuccessDataResult<List<Expense>>(tripExpenses);
		}
		// If result is false return error result with defined message
		return new ErrorDataResult<List<Expense>>(Message.ExpensesNotFound);
	}

	/**
	 * Getting all the expenses which belongs to user method. Before getting
	 * expenses checking the criteria.
	 * 
	 * @param username Username which belongs user
	 * @return Success of result and List of Expense with/without message
	 */
	@Override
	public IDataResult<List<Expense>> getAllByUser(String username) {
		// Getting all the expenses
		List<Expense> expenses = expenseDao.findAll();
		List<Expense> result = new ArrayList<>();// Creating an instance of expense class
		// Loops for all the expenses
		for (Expense expense : expenses) {
			// If trip names matches add to instance
			if (expense.getUser().getUsername().equals(username)) {
				result.add(expense);
			}
		}
		// Return successful result and data
		return new SuccessDataResult<List<Expense>>(result);
	}

	/**
	 * Getting trip's result
	 * 
	 * @param tripname Trip name to get the that trip's result
	 * @param username Username which belongs to user who wants to get result
	 * @return Success of result and List of Expense with/without message
	 */
	@Override
	public IDataResult<List<ExpenseDetail>> getResult(String username, String tripname) {
		// Checking criteria. If there is any mismatching, result returns success is
		// false with/without message
		IResult result = Utils.BusinessRules(checkUserInTrip(username, tripname), checkTripIsExisted(tripname));
		// If result is true get the result
		if (result.getSuccess()) {
			// Get all expenses in the trip
			List<Expense> tripExpenses = getAllByTripname(tripname);
			// Get all users in the trip
			List<String> users = getUsernamesInTrip(tripname);
			// Creating an instance of list of expense detail class
			List<ExpenseDetail> details = new ArrayList<>();
			// Getting per person expense result
			double perPerson = getTotalAmount(tripname) / (users.size());
			// Loop for users
			for (String u : users) {
				double spent = 0; // Assume user spent nothing
				// Loop for expenses
				for (Expense e : tripExpenses) {
					// If usernames are matched add to spent
					if (e.getUser().getUsername().equals(u)) {
						spent = spent + e.getExpenseAmount();
					}
				}
				// Result of the expenses, who is in debt or not
				double last = spent - perPerson;
				// Creating an instance of expense detail class and add all parameters
				ExpenseDetail expenseDetail = new ExpenseDetail(u, spent, last);
				details.add(expenseDetail); // Adding the expenseDetail to the list
			}
			// If method pass the criteria return successful result and data
			return new SuccessDataResult<List<ExpenseDetail>>(details);
		}
		// If result is false return error result with defined message
		return new ErrorDataResult<List<ExpenseDetail>>(result.getMessage());
	}

	/**
	 * Getting some of statistic
	 * 
	 * @param tripname Trip name to get that trip's statistic
	 * @param username Username which belongs to user who wants to get summary
	 * @return Success of result and map of statistics with/without message
	 */
	@Override
	public IDataResult<Map<String, Double>> getSummary(String username, String tripname) {
		// Checking criteria. If there is any mismatching, result returns success is
		// false with/without message
		IResult result = Utils.BusinessRules(checkUserInTrip(username, tripname), checkTripIsExisted(tripname),checkExpense(tripname));
		
		// If result is true get the result
		if (result.getSuccess()) {
			tripname = tripname.toUpperCase();
			// Creating an instance of map to add statistics
			Map<String, Double> summary = new HashMap<>();
			summary.put("lowest", getLowestExpense(tripname));
			summary.put("highest", getHighestExpense(tripname));
			summary.put("avarage", getAverageExpense(tripname));
			summary.put("total", getTotalAmount(tripname));
			summary.put("numberOfPurchase", getNumberOfPurchases(tripname));
			// If method pass the criteria return successful result and data
			return new SuccessDataResult<Map<String, Double>>(summary);
		}
		// If result is false return error result with defined message
		return new ErrorDataResult<Map<String, Double>>(result.getMessage());
	}

	/**
	 * Getting user's total of purchases
	 * 
	 * @param username Username which belongs user
	 * @return Success of result and User's total of purchases with/without message
	 */
	@Override
	public IDataResult<Double> getUserSummary(String username) {
		// Getting all of the expense
		List<Expense> expenses = expenseDao.findAll();
		double total = 0; // Assuming total is 0
		// Loop for expenses
		for (Expense expense : expenses) {
			// If usernames matches in expense list, add expense amount to the total
			if (expense.getUser().getUsername().equals(username)) {
				total = total + expense.getExpenseAmount();
			}
		}
		// Return successful result and data
		return new SuccessDataResult<Double>(total);
	}

	// Helper methods

	/**
	 * Getting highest expense amount of the trip
	 * 
	 * @param tripname Tripname which is wanted by user
	 * @return highest price
	 */
	private double getHighestExpense(String tripname) {
		// Getting all of the expenses in the trip
		List<Expense> tripExpenses = getAllByTripname(tripname);
		// Assuming highest is 0
		double highest = 0;
		// Loop for expenses
		for (Expense expense : tripExpenses) {
			// If expense's amount is bigger than highest,new highest is this expense
			if (expense.getExpenseAmount() > highest) {
				highest = expense.getExpenseAmount();
			}
		}
		return highest;
	}

	/**
	 * Getting total amount of the trip
	 * 
	 * @param tripname Tripname which is wanted by user
	 * @return total amount
	 */
	private double getTotalAmount(String tripname) {
		// Getting all of the expenses in the trip
		List<Expense> tripExpenses = getAllByTripname(tripname);
		// Assuming total amount is 0
		double totalAmount = 0;
		// Loop for expenses
		for (Expense expense : tripExpenses) {
			// Adding all the expense amount to total
			totalAmount = totalAmount + expense.getExpenseAmount();
		}
		return totalAmount;
	}

	/**
	 * Getting number of purchases in the trip
	 * 
	 * @param tripname Tripname which is wanted by user
	 * @return number of purchases
	 */
	private double getNumberOfPurchases(String tripname) {
		// Getting all of the expenses in the trip
		List<Expense> tripExpenses = getAllByTripname(tripname);
		// Getting size of the expense list
		double purchases = tripExpenses.size();
		return purchases;
	}

	/**
	 * Gettingg lowest amount of the trip
	 * 
	 * @param tripname Tripname which is wanted by user
	 * @return lowest price in the trip
	 */
	private double getLowestExpense(String tripname) {
		// Getting all of the expenses in the trip
		List<Expense> tripExpenses = getAllByTripname(tripname);
		// Assuming lowest price is the first of the list
		double lowestExpense = tripExpenses.get(0).getExpenseAmount();
		// Loop for expenses
		for (int i = 0; i < tripExpenses.size(); i++) {
			// If expense amount is lower than expense which I assume, new lowest is this
			// expense
			if (lowestExpense > tripExpenses.get(i).getExpenseAmount()) {
				lowestExpense = tripExpenses.get(i).getExpenseAmount();
			}
		}
		return lowestExpense;
	}

	/**
	 * Getting average of expenses in the trip
	 * 
	 * @param tripname Tripname which is wanted by user
	 * @return average expenses
	 */
	private double getAverageExpense(String tripname) {
		// Getting total amount to count the average
		Double totalAmount = getTotalAmount(tripname);
		// Getting number of purchase to count the average
		Double numberOfPurchases = getNumberOfPurchases(tripname);
		return totalAmount / numberOfPurchases;
	}

	/**
	 * Getting usernames in the trip
	 * 
	 * @param tripname Tripname which is wanted by user
	 * @return list of username which is from defined tripname
	 */
	private ArrayList<String> getUsernamesInTrip(String tripname) {
		// Getting the trip by tripname
		Trip trip = tripDao.get(tripname.toUpperCase());
		return trip.getUsernames();
	}

	/**
	 * Getting all the expenses which is given tripname
	 * 
	 * @param tripname Tripname which is wanted by user
	 * @return list of expenses which is from defined tripname
	 */
	private List<Expense> getAllByTripname(String tripname) {
		// Getting all the expenses
		List<Expense> expenses = expenseDao.findAll();
		// Creating an instance of list of expense class
		List<Expense> tripExpenses = new ArrayList<>();
		// Loop for expenses
		for (Expense expense : expenses) {
			// If expense's tripname matches with tripname which user defined, add expense
			// to the list
			if (expense.getTrip().getTripname().equalsIgnoreCase(tripname)) {
				tripExpenses.add(expense);
			}
		}
		return tripExpenses;
	}

	// Validation

	/**
	 * Checking trip status is active or not
	 * 
	 * @param tripname Tripname which is wanted by user
	 * @return Success of result with/without message
	 */
	private IResult checkTripsStatus(String tripname) {
		// Getting status of the trip
		boolean result = tripDao.get(tripname.toUpperCase()).isTripStatus();
		// If status is false , return error result with message
		if (!result) {
			return new ErrorResult(Message.TripIsNotActive);
		}
		// If status is true, return successful result
		return new SuccessResult();
	}

	/**
	 * Checking expense is existed or not
	 * 
	 * @param expenseId Expense id which is defined by user
	 * @return Success of result with/without message
	 */
	private IResult checkExpenseExisted(int expenseId) {
		// Getting all of the expenses
		List<Expense> expenses = getAll().getData();
		// Loop for expenses
		for (Expense expense : expenses) {
			// If expense's id matches with expenseId which comes from user, return
			// successful result
			if (expense.getExpenseId() == expenseId) {
				return new SuccessResult();
			}
		}
		// If expenseId never match, return error result with message
		return new ErrorResult(Message.ExpenseIsNotExisted);
	}

	/**
	 * Checking trip is created or not
	 * 
	 * @param tripname Trip name to check it is created or not
	 * @return Success of result with/without message
	 */
	private IResult checkTripIsExisted(String tripname) {
		// Get all of the trips
		List<Trip> trips = tripDao.findAll();
		// Loop for trips
		for (Trip trip : trips) {
			// If tripname matches with tripname which user defined, return successful
			// result
			if (trip.getTripname().equalsIgnoreCase(tripname)) {
				return new SuccessResult();
			}
		}
		// If tripname never match, return error result with message
		return new ErrorResult(Message.TripIsNotExisted);
	}

	/**
	 * Checking trip to learn user is in or not
	 * 
	 * @param username Username which belongs to user
	 * @param tripname Trip name to check user is in or not
	 * @return Success of result with/without message
	 */
	private IResult checkUserInTrip(String username, String tripname) {
		// Getting usernames in trip which user defined
		List<String> usernames = tripDao.get(tripname.toUpperCase()).getUsernames();
		// Loop for usernames
		for (String u : usernames) {
			// If usernames match, return successful result
			if (u.equals(username)) {
				return new SuccessResult();
			}
		}
		// If usernames do not match return error result with message
		return new ErrorResult("Error: User is not in the trip");
	}
	/**
	 * checking trip, is there any expense inside
	 * @param tripname Trip name to check expenses
	 * @return Success of result with/without message
	 */
	private IResult checkExpense(String tripname) {
		List<Expense> expenses = getAllByTripname(tripname);
		if(expenses.size()>0) {
			return new SuccessResult();
		}
		return new ErrorResult("Error: There is no expense");
	}
	
}
