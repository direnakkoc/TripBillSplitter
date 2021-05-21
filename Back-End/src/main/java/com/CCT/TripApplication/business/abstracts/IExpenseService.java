package com.CCT.TripApplication.business.abstracts;

import java.util.List;
import java.util.Map;

import com.CCT.TripApplication.entities.Expense;
import com.CCT.TripApplication.entities.DTO.ExpenseDetail;
import com.CCT.TripApplication.core.results.*;

/**
 * This interface is for business layer of Expense
 * 
 * @author Diren
 *
 */
public interface IExpenseService {

	/**
	 * Saving expense method
	 * 
	 * @param expense Expense which user spent and add the system
	 * @return Success of result with/without message
	 */
	IResult add(Expense expense);

	/**
	 * Deleting expense method
	 * 
	 * @param id       Expense's id is given by user to delete from database
	 * @param tripname Trip name to check the trip is active or not
	 * @return Success of result with/without message
	 */
	IResult delete(int id, String tripname);

	/**
	 * Updating expense method
	 * 
	 * @param expense Expense is given by user to update
	 * @return Success of result with/without message
	 */
	IResult update(Expense expense);

	/**
	 * Getting all the expenses method
	 * 
	 * @return Success of result and List of Expense with/without message
	 */
	IDataResult<List<Expense>> getAll();

	/**
	 * Getting all the expenses which is given tripname method
	 * 
	 * @param username Username to check user is in trip or not
	 * @param tripname Tripname to get that trip's expenses
	 * @return Success of result and List of Expense with/without message
	 */
	IDataResult<List<Expense>> getAllByTrip(String username, String tripname);

	/**
	 * Getting all the expenses which belongs to user method
	 * 
	 * @param username Username which belongs user
	 * @return Success of result and List of Expense with/without message
	 */
	IDataResult<List<Expense>> getAllByUser(String username);

	/**
	 * Getting trip's result
	 * 
	 * @param tripname Trip name to get the that trip's result
	 * @param username Username which belongs to user who wants to get result
	 * @return Success of result and List of Expense with/without message
	 */
	IDataResult<List<ExpenseDetail>> getResult(String username, String tripname);

	/**
	 * Getting some of statistic
	 * 
	 * @param tripname Trip name to get that trip's statistic
	 * @param username Username which belongs to user who wants to get summary
	 * @return Success of result and map of statistics with/without message
	 */
	IDataResult<Map<String, Double>> getSummary(String username,String tripname);

	/**
	 * Getting user's total of purchases
	 * 
	 * @param username Username which belongs user
	 * @return Success of result and User's total of purchases with/without message
	 */
	IDataResult<Double> getUserSummary(String username);

}
